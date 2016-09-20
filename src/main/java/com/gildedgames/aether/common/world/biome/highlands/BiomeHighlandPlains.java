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

public class BiomeHighlandPlains extends BiomeAetherBase
{

	public static final WorldGenSkyrootTree trees = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.green_skyroot_leaves.getDefaultState());

	public static final WorldGenMassiveSkyrootTree pines = new WorldGenMassiveSkyrootTree(BlocksAether.green_skyroot_leaves.getDefaultState(), 8, false);

	public BiomeHighlandPlains()
	{
		super(new BiomeProperties("Highland Plains").setRainDisabled().setTemperature(0.5f));

		this.setRegistryName(AetherCore.getResource("aether_highland_plains"));
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		if (random.nextInt(20) < 5)
		{
			return random.nextBoolean() ? trees : pines;
		}

		return null;
	}

}
