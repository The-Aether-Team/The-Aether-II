package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.world.features.trees.WorldGenMassiveSkyrootTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenGoldenOakTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.BlockLog;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeGenAether extends BiomeGenAetherBase
{
	public static final WorldGenSkyrootTree genGreenSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.green_skyroot_leaves.getDefaultState());

	public static final WorldGenSkyrootTree genBlueSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.blue_skyroot_leaves.getDefaultState());

	public static final WorldGenMassiveSkyrootTree genBlueMassiveSkyrootTree = new WorldGenMassiveSkyrootTree(BlocksAether.blue_skyroot_leaves.getDefaultState(), 8, false);

	public static final WorldGenMassiveSkyrootTree genGreenMassiveSkyrootTree = new WorldGenMassiveSkyrootTree(BlocksAether.green_skyroot_leaves.getDefaultState(), 8, false);

	public static final WorldGenMassiveSkyrootTree genDarkBlueMassiveSkyrootTree = new WorldGenMassiveSkyrootTree(BlocksAether.dark_blue_skyroot_leaves.getDefaultState(), 35, true);

	public static final WorldGenGoldenOakTree genGoldenOakTree = new WorldGenGoldenOakTree(BlocksAether.golden_oak_log.getDefaultState(), BlocksAether.golden_oak_leaves.getDefaultState());

	public BiomeGenAether(int id)
	{
		super(id);
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		int chance = random.nextInt(100);

		if (chance > 97) // 3% chance
		{
			return genGoldenOakTree;
		}
		else if (chance > 90) // 7% chance
		{
			return genDarkBlueMassiveSkyrootTree;
		}
		else if (chance > 75) // 15% chance
		{
			return genBlueMassiveSkyrootTree;
		}
		else if (chance > 60) // 15% chance
		{
			return genGreenMassiveSkyrootTree;
		}
		else if (chance > 25) // 35% chance
		{
			return genGreenSkyrootTree;
		}
		else // 25% chance
		{
			return genBlueSkyrootTree;
		}

	}

}
