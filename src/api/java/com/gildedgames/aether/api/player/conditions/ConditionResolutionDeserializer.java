package com.gildedgames.aether.api.player.conditions;

import com.gildedgames.aether.api.player.conditions.resolutions.ConditionResolutionRequireAll;
import com.gildedgames.aether.api.player.conditions.resolutions.ConditionResolutionRequireAny;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;

public class ConditionResolutionDeserializer implements JsonDeserializer<IConditionResolution>
{
	private final HashMap<String, Class<? extends IConditionResolution>> resolutions = new HashMap<>();

	public ConditionResolutionDeserializer()
	{
		this.resolutions.put("requireAll", ConditionResolutionRequireAll.class);
		this.resolutions.put("requireAny", ConditionResolutionRequireAny.class);
	}

	@Override
	public IConditionResolution deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		final String type = json.getAsString();

		if (!this.resolutions.containsKey(type))
		{
			throw new JsonParseException("Invalid condition resolution type " + type);
		}

		return context.deserialize(json, this.resolutions.get(type));
	}
}
