package com.gildedgames.aether.common.world.dungeon.labyrinth.dim;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenSliderLabyrinth extends BiomeGenBase
{

	public BiomeGenSliderLabyrinth(int id)
	{
		super(id);
		
		this.setBiomeName("slider_labyrinth");
		this.setDisableRain();
		this.setTemperatureRainfall(0.5f, 0f);

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}

	@Override
	public float getSpawningChance()
	{
		return 0.0F;
	}
	
	@Override
	public int getSkyColorByTemp(float temp)
	{
		return 0xC0C0FF;
	}

	@Override
	public int getWaterColorMultiplier()
	{
		return 0x70DB70;
	}

	@Override
	public void decorate(World world, Random random, BlockPos pos)
	{
		
	}
	
}
