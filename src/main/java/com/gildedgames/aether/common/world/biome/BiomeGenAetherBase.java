package com.gildedgames.aether.common.world.biome;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenAetherBase extends BiomeGenBase
{
	public BiomeGenAetherBase(int id)
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

	public void generateClouds(World world, Random rand, int chunkX, int chunkZ)
	{
		int x1 = chunkX * 16;
		int z1 = chunkZ * 16;

		//Purple Aercloud Generator
		/*if (rand.nextInt(50) == 0)
		{
			int x = x1 + rand.nextInt(4);
			int y = rand.nextInt(32);
			int z = z1 + rand.nextInt(4);
			(new AetherGenClouds(AetherBlocks.Aercloud.blockID, 8, 4, false)).generate(world, rand, x, y, z);
		}
		if (rand.nextInt(50) == 0)
		{
			int x = x1 + rand.nextInt(4);
			int y = rand.nextInt(32);
			int z = z1 + rand.nextInt(4);
			(new AetherGenClouds(AetherBlocks.Aercloud.blockID, 7, 4, false)).generate(world, rand, x, y, z);
		}
		if (rand.nextInt(50) == 0)
		{
			int x = x1 + rand.nextInt(4);
			int y = rand.nextInt(32);
			int z = z1 + rand.nextInt(4);
			(new AetherGenClouds(AetherBlocks.Aercloud.blockID, 6, 4, false)).generate(world, rand, x, y, z);
		}
		if (rand.nextInt(50) == 0)
		{
			int x = x1 + rand.nextInt(4);
			int y = rand.nextInt(32);
			int z = z1 + rand.nextInt(4);
			(new AetherGenClouds(AetherBlocks.Aercloud.blockID, 5, 4, false)).generate(world, rand, x, y, z);
		}

		//Blue Aercloud Generator
		if (rand.nextInt(15) == 0)
		{
			int x = x1 + rand.nextInt(16);
			int y = rand.nextInt(65) + 32;
			int z = z1 + rand.nextInt(16);
			(new AetherGenClouds(AetherBlocks.Aercloud.blockID, 1, 8, false)).generate(world, rand, x, y, z);
		}

		//White Aercloud Generator
		if (rand.nextInt(10) == 0)
		{
			int x = x1 + rand.nextInt(16);
			int y = rand.nextInt(65) + 32;
			int z = z1 + rand.nextInt(16);
			(new AetherGenClouds(AetherBlocks.Aercloud.blockID, 0, 16, false)).generate(world, rand, x, y, z);
		}

		if (rand.nextInt(6) == 0)
		{
			int x = x1 + rand.nextInt(16);
			int y = rand.nextInt(64) + 64;
			int z = z1 + rand.nextInt(16);
			(new AetherGenCumulusClouds(AetherBlocks.Aercloud.blockID, 10, 3)).generate(world, rand, x, y, z);
		}

		//Flat White Aercloud Generator
		if (rand.nextInt(30) == 0)
		{
			int x = x1 + rand.nextInt(16);
			int y = rand.nextInt(32);
			int z = z1 + rand.nextInt(16);
			(new AetherGenClouds(AetherBlocks.Aercloud.blockID, 0, 64, true)).generate(world, rand, x, y, z);
		}*/
	}
}
