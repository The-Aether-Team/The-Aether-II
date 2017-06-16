package com.gildedgames.aether.common.analytics.events;

import com.google.gson.JsonObject;

public class GASessionStartEvent extends GAEvent
{
	@Override
	public void writeProperties(JsonObject obj)
	{
		obj.addProperty("category", "user");
	}
}
