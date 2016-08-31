package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RangedAttributeModifier extends AttributeModifier
{

	private final double min, max;

	private final boolean floatRanges;

	private final Random rand;

	public RangedAttributeModifier(String nameIn, double min, double max, boolean floatRanges, Random rand, int operationIn)
	{
		super(nameIn, 0, operationIn);

		this.min = min;
		this.max = max;

		this.floatRanges = floatRanges;

		this.rand = rand;
	}

	@Override
	public double getAmount()
	{
		if (this.floatRanges)
		{
			return this.min + (this.max - this.min) * this.rand.nextFloat();
		}
		else
		{
			return ThreadLocalRandom.current().nextInt((int) min, (int) max + 1);
		}
	}

}
