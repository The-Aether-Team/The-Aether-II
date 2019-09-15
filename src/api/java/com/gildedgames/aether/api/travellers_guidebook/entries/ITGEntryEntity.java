package com.gildedgames.aether.api.travellers_guidebook.entries;

import com.gildedgames.aether.api.travellers_guidebook.ITGEntry;
import net.minecraft.util.ResourceLocation;

public interface ITGEntryEntity extends ITGEntry
{
	ResourceLocation getEntityId();
}
