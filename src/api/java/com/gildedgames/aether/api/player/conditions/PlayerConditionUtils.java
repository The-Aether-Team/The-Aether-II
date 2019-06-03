package com.gildedgames.aether.api.player.conditions;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

public class PlayerConditionUtils
{
	/**
	 * Assemble all ids of conditions and return them.
	 */
	public static Collection<String> getIDs(final Collection<IPlayerCondition> conditions)
	{
		final List<String> conditionIDs = Lists.newArrayList();

		conditions.forEach(c ->
		{
			final String id = c.getUniqueIdentifier();

			if (!conditionIDs.contains(id))
			{
				conditionIDs.add(c.getUniqueIdentifier());
			}
		});

		return conditionIDs;
	}
}
