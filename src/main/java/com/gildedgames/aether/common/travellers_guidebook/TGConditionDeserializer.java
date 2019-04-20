package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.travellers_guidebook.ITGCondition;
import com.gildedgames.aether.common.travellers_guidebook.conditions.TGConditionSeeEntity;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class TGConditionDeserializer implements JsonDeserializer<ITGCondition>
{
	private final HashMap<String, Class<? extends ITGCondition>> conditions = new HashMap<>();

	public TGConditionDeserializer()
	{
		this.conditions.put("seeEntity", TGConditionSeeEntity.class);
	}

	@Override
	public ITGCondition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		final JsonObject root = json.getAsJsonObject();

		if (!root.has("type"))
		{
			throw new JsonParseException("Missing required field 'type' for condition");
		}

		final String type = root.get("type").getAsString();

		if (!this.conditions.containsKey(type))
		{
			throw new JsonParseException("Invalid condition type " + type);
		}

		return context.deserialize(json, this.conditions.get(type));
	}
}
