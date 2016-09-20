package com.gildedgames.aether.common.world.biome.highlands;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.world.biome.BiomeAetherBase;
import com.gildedgames.aether.common.world.features.trees.WorldGenLargeTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenMassiveSkyrootTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.BlockLog;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeHighlandsCrystal extends BiomeAetherBase
{

	public static final WorldGenSkyrootTree crystal_trees = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.blue_skyroot_leaves.getDefaultState());

	public static final WorldGenMassiveSkyrootTree crystal_pines = new WorldGenMassiveSkyrootTree(BlocksAether.blue_skyroot_leaves.getDefaultState(), 8, false);

	public static final WorldGenLargeTree crystal_oaks = new WorldGenLargeTree(BlocksAether.skyroot_log.getDefaultState(), BlocksAether.blue_skyroot_leaves.getDefaultState());

	public BiomeHighlandsCrystal()
	{
		super(new BiomeProperties("Crystal Highlands").setRainDisabled().setTemperature(0.5f));

		//this.topBlock = BlocksAether.icestone_bricks.getDefaultState();

		this.setRegistryName(AetherCore.getResource("aether_crystal_highlands"));
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		if (random.nextInt(10) < 5)
		{
			return random.nextBoolean() ? crystal_trees : crystal_pines;
		}

		return crystal_oaks;
	}

}
