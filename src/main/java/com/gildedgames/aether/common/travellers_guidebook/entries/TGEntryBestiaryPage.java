package com.gildedgames.aether.common.travellers_guidebook.entries;

import com.gildedgames.aether.api.travellers_guidebook.entries.ITGEntryBestiaryPage;
import com.gildedgames.aether.common.travellers_guidebook.TGEntryDefinitionBase;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class TGEntryBestiaryPage extends TGEntryDefinitionBase implements ITGEntryBestiaryPage
{
	private final String tag, entityId, descriptionEntryId, statsEntryId, movesEntryId, nameEntryId;

	private final ResourceLocation silhouetteTexture, discoveredTexture;

	protected TGEntryBestiaryPage(final String tag, final String entityId, final String descriptionEntryId,
			final String statsEntryId, final String movesEntryId,
			final String nameEntryId, final ResourceLocation silhouetteTexture, final ResourceLocation discoveredTexture)
	{
		this.tag = tag;

		this.entityId = entityId;
		this.descriptionEntryId = descriptionEntryId;
		this.statsEntryId = statsEntryId;
		this.movesEntryId = movesEntryId;
		this.nameEntryId = nameEntryId;

		this.silhouetteTexture = silhouetteTexture;
		this.discoveredTexture = discoveredTexture;
	}

	@Override
	public String getEntityId()
	{
		return this.entityId;
	}

	@Override
	public ResourceLocation getSilhouetteTexture()
	{
		return this.silhouetteTexture;
	}

	@Override
	public ResourceLocation getDiscoveredTexture()
	{
		return this.discoveredTexture;
	}

	@Override
	public String getDescriptionEntryId()
	{
		return this.descriptionEntryId;
	}

	@Override
	public String getStatsEntryId()
	{
		return this.statsEntryId;
	}

	@Override
	public String getMovesEntryId()
	{
		return this.movesEntryId;
	}

	@Override
	public String getNameEntryId()
	{
		return this.nameEntryId;
	}

	@Override
	public String tag()
	{
		return this.tag;
	}

	public static class Deserializer implements JsonDeserializer<TGEntryBestiaryPage>
	{
		@Override
		public TGEntryBestiaryPage deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
				throws JsonParseException
		{
			return new TGEntryBestiaryPage(json.getAsJsonObject().get("tag").getAsString(),
					json.getAsJsonObject().get("entityId").getAsString(),
					json.getAsJsonObject().get("descriptionEntryId").getAsString(),
					json.getAsJsonObject().get("statsEntryId").getAsString(),
					json.getAsJsonObject().get("movesEntryId").getAsString(),
					json.getAsJsonObject().get("nameEntryId").getAsString(),
					new ResourceLocation(json.getAsJsonObject().get("silhouetteTexture").getAsString()),
					new ResourceLocation(json.getAsJsonObject().get("discoveredTexture").getAsString()));
		}
	}
}
