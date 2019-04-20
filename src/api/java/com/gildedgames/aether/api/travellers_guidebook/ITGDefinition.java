package com.gildedgames.aether.api.travellers_guidebook;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;

public interface ITGDefinition
{
	@Nonnull
	Collection<ITGCondition> conditions();

	@Nonnull
	Map<String, ITGEntryDefinition> entries();
}
