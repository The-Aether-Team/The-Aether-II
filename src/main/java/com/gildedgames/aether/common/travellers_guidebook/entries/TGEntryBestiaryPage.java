package com.gildedgames.aether.common.travellers_guidebook.entries;

import com.gildedgames.aether.api.cache.IEntityStats;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.conditions.IConditionResolution;
import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import com.gildedgames.aether.api.travellers_guidebook.entries.ITGEntryBestiaryPage;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.player_conditions.types.PlayerConditionFeedEntity;
import com.gildedgames.aether.common.player_conditions.types.PlayerConditionKillEntity;
import com.gildedgames.aether.common.travellers_guidebook.TGEntryBase;
import com.google.common.collect.Lists;
import com.google.gson.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.lang.reflect.Type;
import java.util.Collection;

public class TGEntryBestiaryPage extends TGEntryBase implements ITGEntryBestiaryPage
{
	private final ResourceLocation entityId;

	private final String tag;

	private final ResourceLocation silhouetteTexture, discoveredTexture;

	private final ITextComponent description;

	protected TGEntryBestiaryPage(final String tag, final ResourceLocation entityId, final String description, final ResourceLocation silhouetteTexture,
			final ResourceLocation discoveredTexture)
	{
		this.tag = tag;

		this.entityId = entityId;

		this.description = new TextComponentTranslation(description);

		this.silhouetteTexture = silhouetteTexture;
		this.discoveredTexture = discoveredTexture;
	}

	@Override
	public Collection<IPlayerCondition> providePlayerConditions()
	{
		final Collection<IPlayerCondition> conditions = Lists.newArrayList();

		conditions.add(new PlayerConditionFeedEntity(this.entityId));
		conditions.add(new PlayerConditionKillEntity(this.entityId));

		return conditions;
	}

	@Override
	public ResourceLocation getEntityId()
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
	public IEntityStats getEntityStats()
	{
		return AetherCore.PROXY.content().entityStatsCache().getStats(this.entityId);
	}

	@Override
	public ITextComponent getDescription()
	{
		return this.description;
	}

	@Override
	public String getEntityName()
	{
		return I18n.format("entity." + this.getEntityId().getNamespace() + "." + this.getEntityId().getPath() + ".name");
	}

	@Override
	public boolean hasUnlockedStats(final IPlayerAether playerAether)
	{
		return playerAether.getPlayerConditionModule().areConditionsFlagged(IConditionResolution.REQUIRE_ANY,
				"feedEntity:" + this.entityId,
				"killEntity:" + this.entityId);
	}

	@Override
	public boolean hasUnlockedCompleteOverview(final IPlayerAether playerAether)
	{
		return this.hasUnlockedName(playerAether); // TODO: Need to have conditions for each move
	}

	@Override
	public boolean hasUnlockedName(final IPlayerAether playerAether)
	{
		return this.hasUnlockedStats(playerAether);
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
			final JsonObject obj = json.getAsJsonObject();

			return new TGEntryBestiaryPage(obj.get("tag").getAsString(),
					new ResourceLocation(obj.get("entityId").getAsString()),
					obj.get("description").getAsString(),
					new ResourceLocation(obj.get("silhouetteTexture").getAsString()),
					new ResourceLocation(obj.get("discoveredTexture").getAsString()));
		}
	}
}
