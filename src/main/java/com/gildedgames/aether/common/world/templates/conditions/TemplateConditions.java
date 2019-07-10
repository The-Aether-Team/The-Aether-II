package com.gildedgames.aether.common.world.templates.conditions;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.templates.PlacementConditionTemplate;
import net.minecraft.block.material.Material;

public class TemplateConditions
{
	public static final PlacementConditionTemplate FLAT_GROUND = new FlatGroundPlacementCondition(),
			INSIDE_GROUND = new InsideGroundPlacementCondition(),
			INSIDE_GROUND_AT_SOURCE = new InsideGroundAtSourcePlacementCondition(),
			REPLACEABLE = new ReplaceablePlacementCondition(true),
			REPLACEABLE_NOT_CRITICAL = new ReplaceablePlacementCondition(false),
			REPLACEABLE_CANOPY = new ReplaceablePlacementCondition(false, Material.WOOD, Material.LEAVES),
			REPLACEABLE_GROUND = new ReplaceablePlacementCondition(true, Material.GROUND, Material.GRASS),
			UNDERGROUND_ENTRANCE = new UndergroundEntrancePlacementCondition(),
			UNDERGROUND_PLACEMENT = new UndergroundPlacementCondition(),
			ON_SOIL = new OnSpecificBlockPlacementCondition(BlocksAether.aether_grass, BlocksAether.aether_dirt),
			IGNORE_QUICKSOIL = new IgnoreBlockPlacementCondition(BlocksAether.quicksoil.getDefaultState());
}
