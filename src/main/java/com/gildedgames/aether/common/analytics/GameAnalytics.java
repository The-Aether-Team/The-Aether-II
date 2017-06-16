package com.gildedgames.aether.common.analytics;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.analytics.events.GAEvent;
import com.gildedgames.aether.common.analytics.responses.GAInitResponse;
import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.zip.GZIPOutputStream;

/***
 * Basic implementation of GameAnalytic's REST API based on the official docs here: http://restapidocs.gameanalytics.com
 *
 * This implementation batches events and uses GZIP compression when uploading. {@link GameAnalytics#setup()} must
 * be called before using to setup the reporter.
 *
 * {@link GAStorage} is used to store the data of this reporter. Queued events are saved to disk in the event
 * a crash occurs or network outage, and will be uploaded during the next available chance.
 *
 * @author JellySquid
 */
public class GameAnalytics implements GAReporter
{
	private static final String SDK_VERSION = "rest api v2";

	private static final int API_VERSION = 2;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

	private final Gson gson = new Gson();

	private final HttpClient client = HttpClientBuilder.create().build();

	private final String url;

	private final GASecret secret;

	private final String platform = GAEnvironment.getPlatform(),
			device = GAEnvironment.getJavaEnvironment(),
			manufacturer = GAEnvironment.getJavaVendor(),
			operatingSystem = GAEnvironment.getOperatingSystem();

	private final GAStorage storage = new GAStorage();

	private final Queue<GAEvent> scheduled = new ArrayDeque<>();

	private final boolean sandboxed;

	private GAState state = GAState.UNLOADED;

	private ScheduledFuture<?> uploadTask, saveTask;

	private long tsOffset = 0;

	private int consecutiveFailures = 0;

	/**
	 * Creates a sandbox GameAnalytics reporter that will record and upload events, but to
	 * a dummy sandbox service.
	 */
	public GameAnalytics()
	{
		// It seems GameAnalytics doesn't enable HTTPS for their sandbox environment, which is odd...
		this.url = "http://sandbox-api.gameanalytics.com/v2/5c6bcb5402204249437fb5a7a80a4959";
		this.secret = new GASecret("16813a12f718bc5c620f56944e1abc3ea13ccbac");

		this.sandboxed = true;
	}

	/**
	 * Creates a production GameAnalytics reporter with the specified key and secret.
	 *
	 * @param key The game's key
	 * @param secret The game's secret token
	 */
	public GameAnalytics(String key, String secret)
	{
		this.url = "https://api.gameanalytics.com/v2/" + key;
		this.secret = new GASecret(secret);

		this.sandboxed = false;
	}

	@Override
	public void setup()
	{
		this.storage.load(this.getLocalFile());

		CompletableFuture.runAsync(() ->
		{
			GAInitResponse result;

			try
			{
				JsonObject payload = new JsonObject();
				payload.addProperty("platform", this.platform);
				payload.addProperty("os_version", this.operatingSystem);
				payload.addProperty("sdk_version", SDK_VERSION);

				HttpPost request = this.createRequest("/init", payload);
				HttpResponse response = this.client.execute(request);

				try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent()))
				{
					result = this.gson.fromJson(reader, GAInitResponse.class);
				}
			}
			catch (JsonParseException | IOException e)
			{
				AetherCore.LOGGER.error("Failed to contact GameAnalytics server");
				return;
			}

			if (result.enabled)
			{
				this.consecutiveFailures = 0;
				this.tsOffset = result.serverTimestamp - (System.currentTimeMillis() / 1000);

				this.saveTask = this.scheduler.scheduleAtFixedRate(this::flush, 0L, 120L, TimeUnit.SECONDS);
				this.uploadTask = this.scheduler.scheduleAtFixedRate(this::upload, 0L, 20L, TimeUnit.SECONDS);

				this.setState(GAState.ACTIVE);
			}
			else
			{
				this.setState(GAState.INACTIVE);
			}
		}, this.scheduler);
	}

	@Override
	public void disable()
	{
		this.setState(GAState.DISABLED);
		this.storage.clearQueued();

		this.shutdown();
	}

	@Override
	public void flush()
	{
		this.processScheduled();

		this.storage.save(this.getLocalFile(), false);
	}

	/**
	 * Updates the state of this reporter.
	 * @param state The new state
	 */
	private void setState(GAState state)
	{
		AetherCore.LOGGER.info("Setting GameAnalytics state to '{}'", state.name());

		this.state = state;
	}

	/**
	 * Shuts down this reporter and attempts to gracefully wait for currently executing tasks to finish.
	 */
	private void shutdown()
	{
		if (this.state != GAState.ACTIVE)
		{
			return;
		}

		this.state = GAState.UNLOADED;

		this.uploadTask.cancel(false);
		this.saveTask.cancel(false);

		this.storage.save(this.getLocalFile(), true);
	}

	/**
	 * Creates a {@link HttpPost} that can be executed by {@link HttpClient} later. This compresses
	 * the payload using GZIP and generates the HMAC authorization header for the request.
	 *
	 * @param endpoint The endpoint to send to, prefixed with "
	 * @param payload The JSON payload as a {@link JsonElement}
	 * @return An {@link HttpPost} that can be executed later
	 * @throws IOException If an error occurs during the compression or HMAC hashing of the payload
	 */
	private HttpPost createRequest(String endpoint, JsonElement payload) throws IOException
	{
		byte[] upload = this.gzipPayload(payload);

		HttpPost post = new HttpPost(this.url + endpoint);
		post.setHeader("Content-Encoding", "gzip");
		post.setHeader("Content-Type", "application/json");
		post.setHeader("Authorization", this.secret.signMessage(upload));

		post.setEntity(new ByteArrayEntity(upload));

		return post;
	}

	/**
	 * Serializes an {@link JsonElement} with GZIP compression.
	 *
	 * @param tree The {@link JsonElement} to serialize
	 * @return The resultant GZIP compressed byte array
	 * @throws IOException If an I/O error has occurred
	 */
	private byte[] gzipPayload(JsonElement tree) throws IOException
	{
		try (ByteArrayOutputStream output = new ByteArrayOutputStream())
		{
			try (OutputStreamWriter stream = new OutputStreamWriter(new GZIPOutputStream(output)))
			{
				try (JsonWriter writer = new JsonWriter(stream))
				{
					this.gson.toJson(tree, writer);
				}
			}

			output.flush();

			return output.toByteArray();
		}
	}

	private void processScheduled()
	{
		synchronized (this.scheduled)
		{
			while (!this.scheduled.isEmpty())
			{
				GAEvent event = this.scheduled.remove();

				JsonObject data = event.serialize();
				data.addProperty("v", API_VERSION);
				data.addProperty("sdk_version", SDK_VERSION);

				data.addProperty("user_id", this.getUser().getPersistentId().toString());
				data.addProperty("session_id", this.getUser().getSessionId().toString());
				data.addProperty("session_num", this.getUser().getSessionCount());

				data.addProperty("device", this.device);
				data.addProperty("manufacturer", this.manufacturer);
				data.addProperty("platform", this.platform);
				data.addProperty("os_version", this.operatingSystem);

				this.storage.queueEvent(data);
			}
		}
	}

	private void upload()
	{
		final Collection<JsonElement> batch = this.storage.drainQueuedEvents(1000);

		if (batch.size() <= 0)
		{
			return;
		}

		CompletableFuture.runAsync(() ->
		{
			try
			{
				JsonArray array = new JsonArray();
				batch.forEach(array::add);

				HttpPost post = this.createRequest("/events", array);
				HttpResponse response = this.client.execute(post);

				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != 200)
				{
					Collection<JsonElement> failed = new ArrayList<>();

					try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent()))
					{
						JsonArray tree = new JsonParser().parse(reader).getAsJsonArray();

						// GameAnalytics returns a list of events that failed processing
						for (JsonElement error : tree)
						{
							JsonElement event = error.getAsJsonObject().get("event");
							failed.add(event);
						}
					}

					AetherCore.LOGGER.warn("GameAnalytics server rejected {} events, dropping", failed.size());
				}

				this.consecutiveFailures = 0;
			}
			catch (JsonParseException | IOException e)
			{
				AetherCore.LOGGER.error("Failed to submit GA events", e);

				// Re-schedule the events for later, assuming they haven't reached the remote server
				for (JsonElement event : batch)
				{
					this.storage.queueEvent(event);
				}

				this.consecutiveFailures++;

				if (this.consecutiveFailures > 1 && this.state.isScheduling())
				{
					long delay = Math.min(300L, 60L * this.consecutiveFailures);

					this.uploadTask.cancel(false);
					this.uploadTask = this.scheduler.scheduleAtFixedRate(this::upload, delay, 20L, TimeUnit.SECONDS);

					AetherCore.LOGGER.warn("Entering turtle mode for {} seconds due to network errors whilst uploading GA events", delay);
				}
			}

		}, this.scheduler);
	}

	private File getLocalFile()
	{
		return new File(AetherCore.PROXY.getConfigDir(), (this.sandboxed ? "analytics-sandbox" : "analytics") + ".json.gz");
	}

	@Override
	public void schedule(GAEvent event)
	{
		if (!this.state.canProcessEvents())
		{
			return;
		}

		synchronized (this.scheduled)
		{
			this.scheduled.add(event);
		}
	}

	@Override
	public GAUser getUser()
	{
		return this.storage.getUser();
	}

	@Override
	public long getEpochTimestamp()
	{
		return (System.currentTimeMillis() / 1000) + this.tsOffset;
	}

	private enum GAState
	{
		// Executor can receive and process events
		ACTIVE,
		// Executor was remotely disabled
		INACTIVE,
		// Executor was disabled by user
		DISABLED,
		// Executor is not loaded yet
		UNLOADED;

		public boolean canProcessEvents()
		{
			return this == ACTIVE;
		}

		public boolean isScheduling()
		{
			return this == ACTIVE;
		}
	}
}
