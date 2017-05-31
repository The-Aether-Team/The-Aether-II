package com.gildedgames.aether.common.analytics;

import akka.japi.pf.Match;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.analytics.events.GAEvent;
import com.gildedgames.aether.common.util.HMACUtil;
import com.google.common.util.concurrent.*;
import com.google.gson.*;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

/***
 * Basic implementation of GameAnalytic's REST API based on the official docs here: http://restapidocs.gameanalytics.com
 *
 * This implementation batches events and uses GZIP compression when uploading. {@link GameAnalytics#init()} must
 * be called before using to setup the reporter.
 *
 * // TODO: Handle analytics when offline
 * // TODO: Prevent event discards when network errors occur
 * // TODO: Schedule event batch uploads reliably
 *
 * @author JellySquid
 */
public class GameAnalytics
{
	private static final int API_VERSION = 2;

	private final ListeningExecutorService executor;

	private final Queue<GAEvent> queuedEvents = new ArrayDeque<>();

	private final Gson gson = new Gson();

	private final JsonParser parser = new JsonParser();

	private final String url, secret;

	private final String platform = this.getPlatform(),
			device = this.getDevice(),
			manufacturer = this.getManufacturer(),
			operatingSystem = this.getOsVersion();

	private boolean enabled = false;

	private long tsOffset = 0;

	/**
	 * Creates a sandbox GameAnalytics reporter that will record and upload events, but to
	 * a dummy sandbox service.
	 */
	public GameAnalytics()
	{
		this.executor = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());

		// It seems GameAnalytics doesn't enable HTTPS for their sandbox environment, which is odd...
		this.url = "http://sandbox-api.gameanalytics.com/v2/5c6bcb5402204249437fb5a7a80a4959";
		this.secret = "16813a12f718bc5c620f56944e1abc3ea13ccbac";
	}

	/**
	 * Creates a production GameAnalytics reporter with the specified key and secret.
	 * @param key The game's key
	 * @param secret The game's secret token
	 */
	public GameAnalytics(String key, String secret)
	{
		this.executor = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());

		this.url = "https://api.gameanalytics.com/v2/" + key;
		this.secret = secret;
	}

	/**
	 * Initializes and configures the analytics reporter.
	 */
	public void init()
	{
		AetherCore.LOGGER.info("Initializing GameAnalytics");

		ListenableFuture<JsonObject> future = this.executor.submit(() -> {
			JsonObject payload = new JsonObject();
			payload.addProperty("platform", this.platform);
			payload.addProperty("os_version", this.operatingSystem);
			payload.addProperty("sdk_version", this.getSdkVersion());

			return this.post("/init", payload).getAsJsonObject();
		});

		Futures.addCallback(future, new FutureCallback<JsonObject>()
		{
			@Override
			public void onSuccess(@Nullable JsonObject response)
			{
				if (response == null)
				{
					return;
				}

				long serverTime = response.get("server_ts").getAsLong();
				long tsOffset = serverTime - (System.currentTimeMillis() / 1000);

				if (tsOffset != 0)
				{
					AetherCore.LOGGER.warn("GameAnalytics' server time is different from ours, is the system clock accurate? (offsetting timestamps by {} seconds)", tsOffset);
				}

				boolean enabled = response.get("enabled").getAsBoolean();

				if (enabled)
				{
					AetherCore.LOGGER.info("GameAnalytics are now available for this launch");
				}
				else
				{
					AetherCore.LOGGER.warn("GameAnalytics has been remotely disabled. Is this client out of date?");
				}

				GameAnalytics.this.enabled = enabled;
				GameAnalytics.this.tsOffset = tsOffset;
			}

			@Override
			public void onFailure(Throwable t)
			{
				AetherCore.LOGGER.error("Failed to initialize GameAnalytics", t);
			}
		});
	}

	/**
	 * Sends a HTTP POST with a JSON payload to the specified endpoint.
	 * @param endpoint The endpoint to send to
	 * @param payload The JSON payload
	 * @return The server's response, null if none
	 * @throws IOException If an network exception occurs
	 */
	private JsonElement post(String endpoint, JsonElement payload) throws IOException
	{
		HttpClient client = HttpClientBuilder.create().build();

		byte[] upload = this.gzip(this.gson.toJson(payload).getBytes("UTF-8"));

		HttpPost post = new HttpPost(this.url + endpoint);

		try
		{
			post.setHeader("Authorization", HMACUtil.hmacSha256(this.secret.getBytes("UTF-8"), upload));
		}
		catch (GeneralSecurityException e)
		{
			throw new IOException("Failed to create HMAC", e);
		}

		post.setHeader("Content-Encoding", "gzip");
		post.setHeader("Content-Type", "application/json");
		post.setEntity(new ByteArrayEntity(upload));

		HttpResponse response = client.execute(post);

		JsonElement download;

		try (InputStream stream = response.getEntity().getContent())
		{
			try (InputStreamReader reader = new InputStreamReader(stream))
			{
				download = this.parser.parse(reader);
			}
		}

		return download;
	}

	/**
	 * Compresses a byte array using GZIP.
	 * @param bytes The bytes to compress
	 * @return The resultant GZIP compressed byte array
	 * @throws IOException If an I/O error has occurred
	 */
	private byte[] gzip(byte[] bytes) throws IOException
	{
		byte[] data;

		try (ByteArrayOutputStream bout = new ByteArrayOutputStream())
		{
			try (GZIPOutputStream gzip = new GZIPOutputStream(bout))
			{
				gzip.write(bytes);
			}

			bout.flush();

			data = bout.toByteArray();
		}

		return data;
	}

	/**
	 * Creates a new user and starts a session.
	 */
	public GAUser createUser()
	{
		return new GAUser(this, UUID.randomUUID());
	}

	/**
	 * Creates a user from a {@link NBTTagCompound} and starts a session.
	 */
	public GAUser loadUser(NBTTagCompound nbt)
	{
		return new GAUser(this, nbt);
	}

	/**
	 * Records and schedules an event for uploading later if this reporter
	 * is enabled.
	 * @param event The event to report
	 */
	public void event(GAEvent event)
	{
		if (!this.enabled)
		{
			return;
		}

		this.queuedEvents.add(event);
	}

	/**
	 * Uploads currently queued events to GameAnalytics.
	 */
	public void uploadPending()
	{
		if (this.queuedEvents.isEmpty())
		{
			return;
		}

		final JsonArray array = new JsonArray();

		while (array.size() < 200 && !this.queuedEvents.isEmpty())
		{
			GAEvent event = this.queuedEvents.remove();

			JsonObject obj = event.serialize();
			obj.addProperty("v", API_VERSION);

			obj.addProperty("device", this.device);
			obj.addProperty("manufacturer", this.manufacturer);
			obj.addProperty("platform", this.platform);
			obj.addProperty("os_version", this.operatingSystem);
			obj.addProperty("sdk_version", this.getSdkVersion());

			obj.addProperty("user_id", event.getUser().getPersistentId().toString());
			obj.addProperty("session_id", event.getUser().getSessionId().toString());
			obj.addProperty("session_num", event.getUser().getSessionCount());

			obj.addProperty("client_ts", this.getAdjustedEpoch(event.getTimestamp()));

			array.add(obj);
		}

		ListenableFuture<JsonElement> future = this.executor.submit(() -> this.post("/events", array));

		Futures.addCallback(future, new FutureCallback<JsonElement>()
		{
			@Override
			public void onSuccess(@Nullable JsonElement result)
			{
				AetherCore.LOGGER.debug("Delivered {} GameAnalytics events", array.size());
			}

			@Override
			public void onFailure(Throwable t)
			{
				AetherCore.LOGGER.error("Failed to submit GameAnalytics events, events may have been lost", t);
			}
		});
	}

	/**
	 * @return The system's epoch
	 */
	public long getSystemEpoch()
	{
		return (System.currentTimeMillis() / 1000);
	}

	/**
	 * Adjusts the system's epoch for event tracking, which may apply an offset
	 * if the system's clock is not synchronized with the remote server's clock.
	 * @param time An epoch from the system's clock
	 * @return The adjusted epoch for remote synchronization
	 */
	public long getAdjustedEpoch(long time)
	{
		return time + this.tsOffset;
	}

	/**
	 * Returns whether or not this reporter is enabled. This may be false
	 * if the remote server requests we disable reporting.
	 * @return True if the reporter is accepting and uploading events, otherwise false.
	 */
	public boolean isEnabled()
	{
		return this.enabled;
	}

	/**
	 * Returns the device name this reporter is running on. This should be cached to
	 * improve performance.
	 * @return A short name of the device
	 */
	private String getDevice()
	{
		return "Java " + System.getProperty("java.version") + " (" + System.getProperty("sun.arch.data.model") + "bit)";
	}

	/**
	 * Returns the manufacturer of the device this reporter is running on. This should be
	 * cached to improve performance.
	 * @return The manufacturer's name
	 */
	private String getManufacturer()
	{
		String name = System.getProperty("java.vendor");

		if (name.length() > 64)
		{
			return name.substring(0, 63);
		}

		return name;
	}

	/**
	 * Returns the platform of the device this reporter is running on. This should be
	 * cached to improve performance.
	 * @return The platform's name
	 */
	private String getPlatform()
	{
		String name = System.getProperty("os.name").toLowerCase();

		if (name.startsWith("windows"))
		{
			return "windows";
		}
		else if (name.startsWith("mac"))
		{
			return "mac_osx";
		}
		else
		{
			return "linux";
		}
	}

	/**
	 * Returns the operating system on the device this reporter is running on. This should
	 * be cached to improve performance.
	 * @return The operating system's name and version
	 */
	private String getOsVersion()
	{
		// We can only have so many significant digits, and we need to strip extra guff
		Matcher matcher = Pattern.compile("[0-9]{0,5}(\\.[0-9]{0,5}){0,2}").matcher(System.getProperty("os.version"));

		// We default to an empty space just to pass GameAnalytic's regex weirdness
		String version = " ";

		// Find the most important group of digits
		if (matcher.find())
		{
			version += matcher.group(0);
		}

		return this.getPlatform() + version;
	}

	/**
	 * Returns the name of the SDK in use by this reporter.
	 * @return The SDK's name
	 */
	private String getSdkVersion()
	{
		return "rest api v2";
	}
}
