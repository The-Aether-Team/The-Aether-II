package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.world.features.trees.WorldGenLargeTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenMassiveSkyrootTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.BlockLog;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeEnchantedForest extends BiomeAetherBase
{

	public static final WorldGenSkyrootTree genSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.golden_oak_leaves.getDefaultState());

	public static final WorldGenMassiveSkyrootTree genMassiveSkyrootTree = new WorldGenMassiveSkyrootTree(BlocksAether.golden_oak_leaves.getDefaultState(), 35, true);

	public static final WorldGenLargeTree genGoldenOakTree = new WorldGenLargeTree(BlocksAether.golden_oak_log.getDefaultState(), BlocksAether.golden_oak_leaves.getDefaultState());

	public static final WorldGenLargeTree genLargeSkyrootTree = new WorldGenLargeTree(BlocksAether.skyroot_log.getDefaultState(), BlocksAether.golden_oak_leaves.getDefaultState());

	public BiomeEnchantedForest()
	{
		super(new Biome.BiomeProperties("Enchanted Forest").setRainDisabled().setTemperature(0.5f));

		this.setRegistryName(AetherCore.getResource("aether_enchanted_forest"));

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ENCHANTED_AETHER_GRASS);
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		int ratio = random.nextInt(100);

		if (ratio <= 40)
		{
			return genSkyrootTree;
		}
		else if (ratio > 40 && ratio <= 60)
		{
			return genGoldenOakTree;
		}
		else if (ratio > 60 && ratio <= 80)
		{
			return genLargeSkyrootTree;
		}

		return genMassiveSkyrootTree;
	}

}
