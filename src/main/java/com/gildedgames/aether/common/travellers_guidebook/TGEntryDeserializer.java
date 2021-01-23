package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.travellers_guidebook.ITGEntry;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryBestiaryPage;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryEffectsPage;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class TGEntryDeserializer implements JsonDeserializer<ITGEntry>
{
	private final HashMap<String, Class<? extends ITGEntry>> entryDefinitions = new HashMap<>();

	public TGEntryDeserializer()
	{
		this.entryDefinitions.put("bestiary_page", TGEntryBestiaryPage.class);
		this.entryDefinitions.put("effects_page", TGEntryEffectsPage.class);
	}

	@Override
	public ITGEntry deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
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
