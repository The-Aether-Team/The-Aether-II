package com.gildedgames.aether.api.player.conditions;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.List;

public class PlayerConditionUtils
{
	/**
	 * Assemble all ids of conditions and return them.
	 */
	public static Collection<ResourceLocation> getIDs(final Collection<IPlayerCondition> conditions)
	{
		final List<ResourceLocation> conditionIDs = Lists.newArrayList();

		conditions.forEach(c ->
		{
			final ResourceLocation id = c.getUniqueIdentifier();

			if (!conditionIDs.contains(id))
			{
				conditionIDs.add(c.getUniqueIdentifier());
			}
		});

		return conditionIDs;
	}
}
