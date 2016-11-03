package com.gildedgames.aether.common.world.gen.templates.conditions;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;

public class TemplateConditions
{
	public static final WorldGenTemplate.PlacementCondition FLAT_GROUND = new FlatGroundPlacementCondition(),
			INSIDE_GROUND = new InsideGroundPlacementCondition(),
			REPLACEABLE = new ReplaceablePlacementCondition(),
			UNDERGROUND_ENTRANCE = new UndergroundEntrancePlacementCondition(),
			UNDERGROUND_PLACEMENT = new UndergroundPlacementCondition(),
			ON_AETHER_GRASS = new OnSpecificBlockPlacementCondition(BlocksAether.aether_grass.getDefaultState()),
			IGNORE_QUICKSOIL = new IgnoreBlockPlacementCondition(BlocksAether.quicksoil.getDefaultState());
}
