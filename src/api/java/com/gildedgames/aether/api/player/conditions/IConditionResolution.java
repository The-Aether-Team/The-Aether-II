package com.gildedgames.aether.api.player.conditions;

import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public interface IConditionResolution
{
	IConditionResolution REQUIRE_ALL = (conditionIDs, isConditionMet) -> {
		for (final ResourceLocation condition : conditionIDs)
		{
			if (!isConditionMet.apply(condition))
			{
				return false;
			}
		}

		return true;
	};

	IConditionResolution REQUIRE_ANY = (conditionIDs, isConditionMet) -> {
		for (final ResourceLocation condition : conditionIDs)
		{
			if (isConditionMet.apply(condition))
			{
				return true;
			}
		}

		return false;
	};

	boolean areConditionsMet(ResourceLocation[] conditionIDs, Function<ResourceLocation, Boolean> isConditionMet);
}
