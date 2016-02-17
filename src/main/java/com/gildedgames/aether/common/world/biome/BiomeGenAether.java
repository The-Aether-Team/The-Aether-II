package com.gildedgames.aether.common.world.biome;

import java.util.Random;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.world.features.trees.WorldGenGoldenOakTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;

import net.minecraft.block.BlockLog;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenAether extends BiomeGenAetherBase
{
	public static final WorldGenSkyrootTree genGreenSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.green_skyroot_leaves.getDefaultState());

	public static final WorldGenSkyrootTree genBlueSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.blue_skyroot_leaves.getDefaultState());

	public static final WorldGenGoldenOakTree genGoldenOakTree = new WorldGenGoldenOakTree(BlocksAether.golden_oak_log.getDefaultState(), BlocksAether.golden_oak_leaves.getDefaultState());

	public BiomeGenAether(int id)
	{
		super(id);
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		int chance = random.nextInt(100);

		if (chance < 5)
		{
			return genGoldenOakTree;
		}
		else if (chance < 50)
		{
			return genGreenSkyrootTree;
		}

		return genBlueSkyrootTree;
	}

}
