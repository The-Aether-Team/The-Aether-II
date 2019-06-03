package com.gildedgames.aether.api.player;

import com.gildedgames.aether.api.player.conditions.ConditionResolution;

public interface IPlayerConditionModule
{
	void flagCondition(String conditionUniqueIdentifier);

	boolean isConditionFlagged(String conditionUniqueIdentifier);

	boolean areConditionsFlagged(final ConditionResolution conditionResolution, String... conditionUniqueIdentifiers);
}
