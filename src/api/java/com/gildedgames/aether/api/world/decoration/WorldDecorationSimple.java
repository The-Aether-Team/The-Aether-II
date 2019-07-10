package com.gildedgames.aether.api.world.decoration;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;

import java.util.Random;

public class WorldDecorationSimple implements WorldDecoration
{

	private final int count;

	private final WorldDecorationGenerator[] generators;

	private final float percentRequired;

	private final EventType decorateType;

	private final WorldDecorationPositioner positioner;

	public WorldDecorationSimple(final int count, final float percentRequired, EventType decorateType,
			WorldDecorationPositioner positioner, final WorldDecorationGenerator... generators)
	{
		this.decorateType = decorateType;

		for (final WorldDecorationGenerator generator : generators)
		{
			if (generator == null)
			{
				throw new IllegalArgumentException("Argument cannot be null");
			}
		}

		this.count = count;
		this.generators = generators;
		this.percentRequired = percentRequired;
		this.positioner = positioner;
	}

	@Override
	public EventType getDecorateType()
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
	public WorldDecorationGenerator getGenerator(final Random rand)
	{
		return this.generators[rand.nextInt(this.generators.length)];
	}

	@Override
	public final BlockPos findPositionToPlace(World world, Random rand, BlockPos pos)
	{
		return this.positioner.findPositionToPlace(world, rand, pos);
	}
}
