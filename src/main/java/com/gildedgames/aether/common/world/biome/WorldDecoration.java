package com.gildedgames.aether.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public interface WorldDecoration
{

	boolean shouldGenerate(Random random);

	/**
	 * @return Number of times this decoration should attempt to generate per chunk.
	 */
	int getGenerationCount();

	WorldGenerator getGenerator();

}
