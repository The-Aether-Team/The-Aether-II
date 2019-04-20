package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.travellers_guidebook.ITGEntryDefinition;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryBestiaryPage;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class TGEntryDeserializer implements JsonDeserializer<ITGEntryDefinition>
{
	private final HashMap<String, Class<? extends ITGEntryDefinition>> entryDefinitions = new HashMap<>();

	public TGEntryDeserializer()
	{
		this.entryDefinitions.put("bestiary_page", TGEntryBestiaryPage.class);
	}

	@Override
	public ITGEntryDefinition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		final JsonObject root = json.getAsJsonObject();

		if (!root.has("type"))
		{
			throw new JsonParseException("Missing required field 'type' for entry");
		}

		final String type = root.get("type").getAsString();

		if (!this.entryDefinitions.containsKey(type))
		{
			throw new JsonParseException("Invalid entry type " + type);
		}

		return context.deserialize(json, this.entryDefinitions.get(type));
	}
}
