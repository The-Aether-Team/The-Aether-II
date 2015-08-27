package com.gildedgames.aether.common.world.biome;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenAether extends BiomeGenBase
{
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
