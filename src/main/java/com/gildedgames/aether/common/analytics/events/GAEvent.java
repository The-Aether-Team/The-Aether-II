package com.gildedgames.aether.common.analytics.events;

import com.gildedgames.aether.common.analytics.GAUser;
import com.google.gson.JsonObject;

import javax.annotation.Nonnull;

public abstract class GAEvent
{
	private final GAUser user;

	private final long timestamp;

	public GAEvent(GAUser user)
	{
		this(user, System.currentTimeMillis() / 1000);
	}

	public GAEvent(GAUser user, long timestamp)
	{
		this.user = user;
		this.timestamp = timestamp;
	}

	/**
	 * Serializes this event to an {@link JsonObject}. Shouldn't contain timestamp!
	 * @return The serialized result
	 */
	@Nonnull
	public abstract JsonObject serialize();

	/**
	 * Returns the user that this event belongs to.
	 * @return The {@link GAUser}
	 */
	@Nonnull
	public GAUser getUser()
	{
		return this.user;
	}

	/**
	 * Returns the timestamp this event was created.
	 * @return The timestamp in epoch format
	 */
	public long getTimestamp()
	{
		return this.timestamp;
	}
}
