package com.gildedgames.aether.common.world.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.GenUtil;
import com.gildedgames.aether.common.world.features.placement_conditions.FlatGroundPlacementCondition;
import com.gildedgames.aether.common.world.features.placement_conditions.ReplaceablePlacementCondition;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.Random;

public class WorldGenDungeonEntrance extends WorldGenTemplate
{
	public WorldGenDungeonEntrance(TemplatePipeline pipeline, Template template)
	{
		super(pipeline, template, new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
	}

	@Override
	public void postGenerate(World world, Random random, BlockPos pos, Rotation rotation)
	{
		world.setBlockState(GenUtil.rotate(pos, pos.add(4, 2, 4), rotation), BlocksAether.labyrinth_totem.getDefaultState());
	}
}
