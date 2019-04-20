package com.gildedgames.aether.api.travellers_guidebook.entries;

import com.gildedgames.aether.api.travellers_guidebook.ITGEntryDefinition;
import net.minecraft.util.ResourceLocation;

public interface ITGEntryBestiaryPage extends ITGEntryDefinition
{
	String getEntityId();

	ResourceLocation getSilhouetteTexture();

	ResourceLocation getDiscoveredTexture();

	String getDescriptionEntryId();

	String getStatsEntryId();

	String getMovesEntryId();

	String getNameEntryId();
}
