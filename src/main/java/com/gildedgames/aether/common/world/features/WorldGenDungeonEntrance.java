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

	private BlockPos totemOffset;

	public WorldGenDungeonEntrance(Template template, BlockPos totemOffset, PlacementCondition condition, PlacementCondition... conditions)
	{
		super(template, condition, conditions);

		this.totemOffset = totemOffset;
	}

	@Override
	public void postGenerate(World world, Random random, BlockPos pos, Rotation rotation)
	{
		world.setBlockState(GenUtil.rotate(pos, pos.add(this.totemOffset), rotation), BlocksAether.labyrinth_totem.getDefaultState());
	}
}
