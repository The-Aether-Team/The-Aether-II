package com.gildedgames.aether.common.analytics.events;

import com.google.gson.JsonObject;

public class GASessionEndEvent extends GAEvent
{
	private final long duration;

	public GASessionEndEvent(long duration)
	{
		this.duration = duration;
	}

	@Override
	public void writeProperties(JsonObject obj)
	{
		obj.addProperty("category", "session_end");
		obj.addProperty("length", this.duration);
	}
}
