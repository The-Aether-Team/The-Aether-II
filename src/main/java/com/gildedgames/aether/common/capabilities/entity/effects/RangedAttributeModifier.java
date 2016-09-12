package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RangedAttributeModifier extends AttributeModifier
{

	private final double min, max;

	private final boolean floatRanges;

	private final Random rand;

	private double lastAmount;

	public RangedAttributeModifier(String nameIn, double min, double max, boolean floatRanges, Random rand, int operationIn)
	{
		super(UUID.randomUUID(), nameIn, 0, operationIn);

		this.min = min;
		this.max = max;

		this.floatRanges = floatRanges;

		this.rand = rand;
	}

	@Override
	public double getAmount()
	{
		double amount;

		if (this.floatRanges)
		{
			amount = this.min + (this.max - this.min) * this.rand.nextFloat();
		}
		else
		{
			amount = ThreadLocalRandom.current().nextInt((int) min, (int) max + 1);
		}

		this.lastAmount = amount;

		return amount;
	}

	public double getLastAmount()
	{
		return this.lastAmount;
	}

}
