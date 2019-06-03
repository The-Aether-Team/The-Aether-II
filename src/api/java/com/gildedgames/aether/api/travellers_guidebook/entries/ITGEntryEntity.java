package com.gildedgames.aether.api.travellers_guidebook.entries;

import com.gildedgames.aether.api.travellers_guidebook.ITGEntryDefinition;
import net.minecraft.util.ResourceLocation;

public interface ITGEntryEntity extends ITGEntryDefinition
{
	ResourceLocation getEntityId();
}
