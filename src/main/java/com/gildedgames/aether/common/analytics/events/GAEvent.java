package com.gildedgames.aether.common.analytics.events;

import com.gildedgames.aether.common.AetherCore;
import com.google.gson.JsonObject;

public abstract class GAEvent
{
	private final long timestamp;

	public GAEvent()
	{
		this.timestamp = AetherCore.ANALYTICS.getEpochTimestamp();
	}

	/**
	 * Serializes this event to an {@link JsonObject}
	 * @return An {@link JsonObject} that can be reported to the GA
	 */
	public JsonObject serialize()
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("client_ts", this.getTimestamp());

		this.writeProperties(obj);

		return obj;
	}

	/**
	 * Writes this event's additional properties to an {@link JsonObject}.
	 * @param obj The event's JSON object
	 */
	protected abstract void writeProperties(JsonObject obj);

	/**
	 * Returns the timestamp this event was created.
	 * @return The timestamp in epoch format
	 */
	public long getTimestamp()
	{
		return this.timestamp;
	}
}
