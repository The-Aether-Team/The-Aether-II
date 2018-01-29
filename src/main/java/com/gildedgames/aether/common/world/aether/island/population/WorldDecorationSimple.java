package com.gildedgames.aether.common.world.aether.island.population;

import com.gildedgames.orbis.api.processing.IBlockAccessExtended;
import com.gildedgames.orbis.api.world.IWorldGen;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class WorldDecorationSimple implements WorldDecoration
{

	private final int count;

	private final IWorldGen[] generators;

	private final float percentRequired;

	public WorldDecorationSimple(final int count, final IWorldGen... generators)
	{
		this(count, 0, generators);
	}

	public WorldDecorationSimple(final int count, final float percentRequired, final IWorldGen... generators)
	{
		for (final IWorldGen generator : generators)
		{
			if (generator == null)
			{
				throw new IllegalArgumentException("Argument cannot be null");
			}
		}

		this.count = count;
		this.generators = generators;
		this.percentRequired = percentRequired;
	}

	@Override
	public boolean shouldGenerate(final Random random)
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
	public IWorldGen getGenerator(final Random rand)
	{
		return this.generators[rand.nextInt(this.generators.length)];
	}

	@Override
	public BlockPos findPositionToPlace(final IBlockAccessExtended blockAccess, final Random rand, final BlockPos pos)
	{
		final int x = rand.nextInt(16) + 8;
		final int z = rand.nextInt(16) + 8;

		final BlockPos pos2 = pos.add(x, 0, z);

		if (!blockAccess.canAccess(pos2))
		{
			return new BlockPos(pos2.getX(), -1, pos2.getZ());
		}

		return blockAccess.getTopPos(pos2);
	}

}
