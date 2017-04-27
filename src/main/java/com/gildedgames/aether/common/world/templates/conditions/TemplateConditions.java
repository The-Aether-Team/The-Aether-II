package com.gildedgames.aether.common.world.templates.conditions;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import net.minecraft.block.material.Material;

public class TemplateConditions
{
	public static final WorldGenTemplate.PlacementCondition FLAT_GROUND = new FlatGroundPlacementCondition(),
			INSIDE_GROUND = new InsideGroundPlacementCondition(),
			REPLACEABLE = new ReplaceablePlacementCondition(true),
			REPLACEABLE_NOT_CRITICAL = new ReplaceablePlacementCondition(false),
			REPLACEABLE_CANOPY = new ReplaceablePlacementCondition(false, Material.WOOD, Material.LEAVES),
			REPLACEABLE_GROUND = new ReplaceablePlacementCondition(true, Material.GROUND, Material.GRASS),
			UNDERGROUND_ENTRANCE = new UndergroundEntrancePlacementCondition(),
			UNDERGROUND_PLACEMENT = new UndergroundPlacementCondition(),
			ON_SOIL = new OnSpecificBlockPlacementCondition(BlocksAether.aether_grass, BlocksAether.aether_dirt),
			IGNORE_QUICKSOIL = new IgnoreBlockPlacementCondition(BlocksAether.quicksoil.getDefaultState());
}
