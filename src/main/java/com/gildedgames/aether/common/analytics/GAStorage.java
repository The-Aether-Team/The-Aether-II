package com.gildedgames.aether.common.analytics;

import com.gildedgames.aether.common.AetherCore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Thread safe storage for GameAnalytics data with a maximum queue size.
 */
public class GAStorage
{
	private static final int MAX_QUEUE_SIZE = 2000;

	private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	private final ReentrantLock latch = new ReentrantLock();

	@Expose
	@SerializedName("queue")
	private Queue<JsonElement> queue = new ArrayDeque<>();

	@Expose
	@SerializedName("user")
	private GAUser user = new GAUser();

	private boolean isDirty = true;

	/**
	 * Loads a storage instance from disk and discards any previously unsaved queued uploads.
	 *
	 * @param file The location to load this storage from
	 */
	public void load(File file)
	{
		Validate.notNull(file, "Path cannot be null");

		if (file.exists())
		{
			this.latch.lock();

			try (GZIPInputStream input = new GZIPInputStream(new FileInputStream(file)))
			{
				GAStorage storage;

				try (InputStreamReader reader = new InputStreamReader(input))
				{
					storage = this.gson.fromJson(reader, GAStorage.class);
				}

				this.user = storage.user;
				this.queue = storage.queue;
			}
			catch (JsonParseException | IOException e)
			{
				AetherCore.LOGGER.error("Failed to load storage", e);
			}
			finally
			{
				this.latch.unlock();
			}
		}
	}

	/**
	 * Saves a storage instance to disk and marks this storage synced.
	 *
	 * @param file The location to save this storage to
	 * @param force If true, the storage will be re-saved even if no changes
	 *              have been made since the last save.
	 */
	public void save(File file, boolean force)
	{
		Validate.notNull(file, "Path cannot be null");

		// Don't save when needless unless forced
		if (!this.isDirty && !force)
		{
			return;
		}

		this.latch.lock();

		try (GZIPOutputStream gzipStream = new GZIPOutputStream(new FileOutputStream(file)))
		{
			try (OutputStreamWriter writer = new OutputStreamWriter(gzipStream))
			{
				this.gson.toJson(this, writer);
			}

			this.isDirty = false;
		}
		catch (JsonParseException | IOException e)
		{
			AetherCore.LOGGER.error("Failed to save storage", e);
		}
		finally
		{
			this.latch.unlock();
		}
	}

	/**
	 * Queues an event for uploading or saving later. Marks storage as dirty for
	 * saving later.
	 *
	 * @param json The serialized event as a JSON object
	 */
	public void queueEvent(@Nonnull JsonElement json)
	{
		Validate.notNull(json, "Event cannot be null");

		this.latch.lock();

		if (this.queue.size() < MAX_QUEUE_SIZE)
		{
			this.queue.add(json);

			this.isDirty = true;
		}

		this.latch.unlock();
	}

	/**
	 * Drains up to the specified number of events from the upload queue.
	 *
	 * @param max The maximum number of events to drain
	 * @return A {@link Collection} of events drained from the upload queue
	 */
	public Collection<JsonElement> drainQueuedEvents(int max)
	{
		this.latch.lock();

		if (this.queue.isEmpty())
		{
			this.latch.unlock();

			return Collections.emptyList();
		}

		ArrayList<JsonElement> list = new ArrayList<>();

		while (list.size() < max && !this.queue.isEmpty())
		{
			list.add(this.queue.remove());
		}

		this.isDirty = true;

		this.latch.unlock();

		return list;
	}

	/**
	 * Returns the {@link GAUser} of this storage that owns the scheduled events.
	 *
	 * @return A unique {@link GAUser}
	 */
	@Nonnull
	public GAUser getUser()
	{
		return this.user;
	}

	/**
	 * Clears all queued uploads.
	 */
	public void clearQueued()
	{
		this.latch.lock();

		this.queue.clear();
		this.isDirty = true;

		this.latch.unlock();
	}
}
