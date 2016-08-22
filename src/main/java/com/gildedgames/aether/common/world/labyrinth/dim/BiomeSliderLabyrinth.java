package com.gildedgames.aether.common.world.labyrinth.dim;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class BiomeSliderLabyrinth extends Biome
{
	public BiomeSliderLabyrinth()
	{
		super(new BiomeProperties("slider_labyrinth").setRainDisabled().setTemperature(0.5f).setRainfall(0.0f));
		
		this.setRegistryName(AetherCore.getResource("slider_labyrinth"));

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
