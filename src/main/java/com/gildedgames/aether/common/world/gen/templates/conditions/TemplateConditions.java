package com.gildedgames.aether.common.world.gen.templates.conditions;

import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;

public class TemplateConditions
{
	public static final WorldGenTemplate.PlacementCondition FLAT_GROUND = new FlatGroundPlacementCondition(),
			INSIDE_GROUND = new InsideGroundPlacementCondition(),
			REPLACEABLE = new ReplaceablePlacementCondition(),
			UNDERGROUND_ENTRANCE = new UndergroundEntrancePlacementCondition(),
			UNDERGROUND_PLACEMENT = new UndergroundPlacementCondition();
}
