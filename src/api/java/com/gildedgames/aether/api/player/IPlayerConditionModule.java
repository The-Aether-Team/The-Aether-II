package com.gildedgames.aether.api.player;

import com.gildedgames.aether.api.player.conditions.IConditionResolution;
import net.minecraft.util.ResourceLocation;

public interface IPlayerConditionModule
{
	void flagCondition(ResourceLocation conditionUniqueIdentifier);

	boolean isConditionFlagged(ResourceLocation conditionUniqueIdentifier);

	boolean areConditionsFlagged(final IConditionResolution conditionResolution, ResourceLocation... conditionUniqueIdentifiers);
}
