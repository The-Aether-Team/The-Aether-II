package com.gildedgames.aether.common.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldDecorationSimple implements WorldDecoration
{

	private final int count;

	private final WorldGenerator[] generators;

	private final float percentRequired;

	public WorldDecorationSimple(int count, WorldGenerator... generators)
	{
		this(count, 0, generators);
	}

	public WorldDecorationSimple(int count, float percentRequired, WorldGenerator... generators)
	{
		this.count = count;
		this.generators = generators;
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
	public WorldGenerator getGenerator(Random rand)
	{
		return this.generators[rand.nextInt(this.generators.length)];
	}

	@Override
	public BlockPos findPositionToPlace(World world, Random rand, BlockPos pos)
	{
		int x = rand.nextInt(16) + 8;
		int z = rand.nextInt(16) + 8;

		return world.getTopSolidOrLiquidBlock(pos.add(x, 0, z));
	}

}
