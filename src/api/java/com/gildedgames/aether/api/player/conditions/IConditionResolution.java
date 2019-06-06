package com.gildedgames.aether.api.player.conditions;

import com.gildedgames.aether.api.player.conditions.resolutions.ConditionResolutionRequireAll;
import com.gildedgames.aether.api.player.conditions.resolutions.ConditionResolutionRequireAny;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public interface IConditionResolution
{
	IConditionResolution REQUIRE_ALL = new ConditionResolutionRequireAll();
	IConditionResolution REQUIRE_ANY = new ConditionResolutionRequireAny();

	boolean areConditionsMet(ResourceLocation[] conditionIDs, Function<ResourceLocation, Boolean> isConditionMet);
}
