package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeGenAether extends BiomeGenBase
{
	private final WorldGenSkyrootTree genGreenSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log, BlocksAether.green_skyroot_leaves, 0, 4, 4);

	private final WorldGenSkyrootTree genBlueSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log, BlocksAether.blue_skyroot_leaves, 0, 4, 4);

	public BiomeGenAether(int id)
	{
		super(id);

		this.setBiomeName("Aether");
		this.setDisableRain();
		this.setTemperatureRainfall(0.5f, 0f);

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		int chance = random.nextInt(100);

		if (chance < 50)
		{
			return this.genBlueSkyrootTree;
		}
		else
		{
			return this.genGreenSkyrootTree;
		}

		// TODO: _actual_ chances
	}

	@Override
	public int getSkyColorByTemp(float temp)
	{
		return 0xc0c0ff;
	}

	@Override
	public int getWaterColorMultiplier()
	{
		return 0x70DB70;
	}

	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return this.getModdedBiomeDecorator(new BiomeDecoratorAether());
	}
}
