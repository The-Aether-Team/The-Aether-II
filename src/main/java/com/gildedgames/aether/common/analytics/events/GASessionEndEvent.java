package com.gildedgames.aether.common.analytics.events;

import com.gildedgames.aether.common.analytics.GAUser;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;

public class GASessionEndEvent extends GAEvent
{
	private final long duration;

	public GASessionEndEvent(GAUser user, long duration)
	{
		super(user);

		Validate.isTrue(duration > 0, "Session duration must be greater than 0");

		this.duration = duration;
	}

	@Nonnull
	@Override
	public JsonObject serialize()
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("category", "session_end");
		obj.addProperty("length", this.duration);

		return obj;
	}
}
