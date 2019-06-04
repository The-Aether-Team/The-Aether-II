package com.gildedgames.aether.api.player;

import com.gildedgames.aether.api.player.conditions.IConditionResolution;

public interface IPlayerConditionModule
{
	void flagCondition(String conditionUniqueIdentifier);

	boolean isConditionFlagged(String conditionUniqueIdentifier);

	boolean areConditionsFlagged(final IConditionResolution conditionResolution, String... conditionUniqueIdentifiers);
}
