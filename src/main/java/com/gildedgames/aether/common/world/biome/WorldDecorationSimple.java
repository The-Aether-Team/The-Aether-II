package com.gildedgames.aether.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldDecorationSimple implements WorldDecoration
{

	private final int count;

	private final WorldGenerator generator;

	private final float percentRequired;

	public WorldDecorationSimple(int count, WorldGenerator generator)
	{
		this(count, generator, 0);
	}

	public WorldDecorationSimple(int count, WorldGenerator generator, float percentRequired)
	{
		this.count = count;
		this.generator = generator;
		this.percentRequired = percentRequired;
	}

	@Override
	public boolean shouldGenerate(Random random)
	{
		if (this.percentRequired <= 0)
		{
			return true;
		}

		return random.nextFloat() <= this.percentRequired;
	}

	@Override
	public int getGenerationCount()
	{
		return this.count;
	}

	@Override
	public WorldGenerator getGenerator()
	{
		return this.generator;
	}

}
