package com.gildedgames.aether.api.player;

import com.gildedgames.aether.api.player.conditions.IConditionResolution;
import com.gildedgames.aether.api.travellers_guidebook.ITGEntry;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.util.ResourceLocation;

public interface IPlayerConditionModule extends NBT
{
	void flagCondition(ResourceLocation conditionUniqueIdentifier);

	boolean isConditionFlagged(ResourceLocation conditionUniqueIdentifier);

	boolean areConditionsFlagged(final IConditionResolution conditionResolution, ResourceLocation... conditionUniqueIdentifiers);

	boolean isEntryUnlocked(final ITGEntry entry);
}
