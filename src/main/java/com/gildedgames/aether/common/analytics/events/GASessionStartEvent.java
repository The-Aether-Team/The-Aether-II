package com.gildedgames.aether.common.analytics.events;

import com.gildedgames.aether.common.analytics.GAUser;
import com.google.gson.JsonObject;

public class GASessionStartEvent extends GAEvent
{
	public GASessionStartEvent(GAUser user)
	{
		super(user);
	}

	@Override
	public JsonObject serialize()
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("category", "user");

		return obj;
	}
}
