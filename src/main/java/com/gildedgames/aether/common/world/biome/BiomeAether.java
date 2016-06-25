package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.world.features.trees.WorldGenFruitTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenMassiveSkyrootTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenLargeTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.BlockLog;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeAether extends BiomeAetherBase
{
	public static final WorldGenSkyrootTree genGreenSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.green_skyroot_leaves.getDefaultState());

	public static final WorldGenSkyrootTree genBlueSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.blue_skyroot_leaves.getDefaultState());

	public static final WorldGenMassiveSkyrootTree genBlueMassiveSkyrootTree = new WorldGenMassiveSkyrootTree(BlocksAether.blue_skyroot_leaves.getDefaultState(), 8, false);

	public static final WorldGenMassiveSkyrootTree genGreenMassiveSkyrootTree1 = new WorldGenMassiveSkyrootTree(BlocksAether.green_skyroot_leaves.getDefaultState(), 8, false);

	public static final WorldGenMassiveSkyrootTree genGreenMassiveSkyrootTree2 = new WorldGenMassiveSkyrootTree(BlocksAether.green_skyroot_leaves.getDefaultState(), 20, false);

	public static final WorldGenMassiveSkyrootTree genDarkBlueMassiveSkyrootTree = new WorldGenMassiveSkyrootTree(BlocksAether.dark_blue_skyroot_leaves.getDefaultState(), 35, true);

	public static final WorldGenFruitTree genPurpleFruitTree = new WorldGenFruitTree(BlocksAether.purple_crystal_leaves.getDefaultState(), BlocksAether.purple_fruit_leaves.getDefaultState(), 50, 5, true);

	public static final WorldGenLargeTree genGoldenOakTree = new WorldGenLargeTree(BlocksAether.golden_oak_log.getDefaultState(), BlocksAether.golden_oak_leaves.getDefaultState());

	public static final WorldGenLargeTree genGreenLargeSkyrootTree = new WorldGenLargeTree(BlocksAether.skyroot_log.getDefaultState(), BlocksAether.green_skyroot_leaves.getDefaultState());

	public BiomeAether(int id)
	{
		super(id);
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		int ratio = random.nextInt(100);

		if (ratio <= 9)
		{
			return genGreenSkyrootTree;
		}
		else if (ratio > 9 && ratio <= 18)
		{
			return genGreenLargeSkyrootTree;
		}
		else if (ratio > 18 && ratio <= 35)
		{
			return genBlueSkyrootTree;
		}
		else if (ratio > 35 && ratio <= 63)
		{
			return genGreenMassiveSkyrootTree1;
		}
		else if (ratio > 63 && ratio <= 80)
		{
			return genBlueMassiveSkyrootTree;
		}
		else if (ratio > 80 && ratio <= 85)
		{
			return genGoldenOakTree;
		}
		else if (ratio > 85 && ratio <= 90)
		{
			return genGreenMassiveSkyrootTree2;
		}
		else if (ratio > 90 && ratio <= 95)
		{
			return genPurpleFruitTree;
		}

		return genDarkBlueMassiveSkyrootTree;
	}

}
