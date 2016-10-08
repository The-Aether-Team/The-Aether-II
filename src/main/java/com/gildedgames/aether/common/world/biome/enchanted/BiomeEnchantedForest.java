package com.gildedgames.aether.common.world.biome.enchanted;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.world.biome.BiomeAetherBase;
import com.gildedgames.aether.common.world.features.WorldGenAetherFlowers;
import com.gildedgames.aether.common.world.features.WorldGenCustomVines;
import com.gildedgames.aether.common.world.features.trees.WorldGenLargeTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenMassiveSkyrootTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.BlockLog;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeEnchantedForest extends BiomeAetherBase
{

	public static final WorldGenSkyrootTree golden_tree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.golden_oak_leaves.getDefaultState());

	public static final WorldGenMassiveSkyrootTree earthshifter_tree = new WorldGenMassiveSkyrootTree(BlocksAether.earthshifter_leaves.getDefaultState(), BlocksAether.earthshifter_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y), 35, true);

	public static final WorldGenLargeTree golden_oak = new WorldGenLargeTree(BlocksAether.golden_oak_log.getDefaultState(), BlocksAether.golden_oak_leaves.getDefaultState());

	public static final WorldGenLargeTree large_golden_tree = new WorldGenLargeTree(BlocksAether.skyroot_log.getDefaultState(), BlocksAether.golden_oak_leaves.getDefaultState());

	public static final WorldGenCustomVines vines = new WorldGenCustomVines(BlocksAether.enchanted_skyroot_vines.getDefaultState());

	protected WorldGenAetherFlowers bushes = new WorldGenAetherFlowers(BlocksAether.enchanted_blueberry_bush.getDefaultState().withProperty(BlockBlueberryBush.PROPERTY_HARVESTABLE, true), 32);

	public BiomeEnchantedForest()
	{
		super(new Biome.BiomeProperties("Enchanted Forest").setRainDisabled().setTemperature(0.5f));

		this.setRegistryName(AetherCore.getResource("aether_enchanted_forest"));

		this.biomeDecorator.generateBushes = false;
		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ENCHANTED_AETHER_GRASS);
	}

	@Override
	public void decorate(World world, Random random, BlockPos pos)
	{
		super.decorate(world, random, pos);

		/*for (int k = 0; k < 80; ++k)
		{
			int x = random.nextInt(16) + 8;
			byte y = (byte) (random.nextInt(64) + 32);
			int z = random.nextInt(16) + 8;

			BiomeEnchantedForest.vines.generate(world, random, new BlockPos(pos.getX() + x, y, pos.getZ() + z));
		}*/

		int x, y, z;

		for (int count = 0; count < 2; count++)
		{
			x = random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = random.nextInt(16) + 8;

			this.bushes.generate(world, random, pos.add(x, y, z));
		}
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		int ratio = random.nextInt(100);

		if (ratio <= 60)
		{
			return golden_oak;
		}
		else if (ratio > 60 && ratio <= 80)
		{
			return large_golden_tree;
		}

		return earthshifter_tree;
	}

}
