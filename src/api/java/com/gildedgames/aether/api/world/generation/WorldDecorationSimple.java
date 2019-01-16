package com.gildedgames.aether.api.world.generation;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class WorldDecorationSimple implements WorldDecoration
{

	private final int count;

	private final WorldGenerator[] generators;

	private final float percentRequired;

	private final DecorateBiomeEvent.Decorate.EventType decorateType;

	public WorldDecorationSimple(final int count, DecorateBiomeEvent.Decorate.EventType decorateType, final WorldGenerator... generators)
	{
		this(count, 0, decorateType, generators);
	}

	public WorldDecorationSimple(final int count, final float percentRequired, DecorateBiomeEvent.Decorate.EventType decorateType,
			final WorldGenerator... generators)
	{
		this.decorateType = decorateType;

		for (final WorldGenerator generator : generators)
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
	public DecorateBiomeEvent.Decorate.EventType getDecorateType()
	{
		return this.decorateType;
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
	public WorldGenerator getGenerator(final Random rand)
	{
		return this.generators[rand.nextInt(this.generators.length)];
	}

	@Override
	public BlockPos findPositionToPlace(final World world, final Random rand, final BlockPos pos)
	{
		final int x = rand.nextInt(16) + 8;
		final int z = rand.nextInt(16) + 8;

		final BlockPos pos2 = pos.add(x, 0, z);

		if (!world.isBlockLoaded(pos2))
		{
			return new BlockPos(pos2.getX(), -1, pos2.getZ());
		}

		return world.getTopSolidOrLiquidBlock(pos2);
	}

}
