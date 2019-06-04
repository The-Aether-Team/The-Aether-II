package com.gildedgames.aether.api.travellers_guidebook;

import com.gildedgames.aether.api.player.conditions.IConditionResolution;
import com.gildedgames.aether.api.player.conditions.IPlayerCondition;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;

public interface ITGDefinition
{
	@Nonnull
	Collection<IPlayerCondition> conditions();

	@Nonnull
	IConditionResolution conditionResolution();

	@Nonnull
	Map<String, ITGEntry> entries();
}
