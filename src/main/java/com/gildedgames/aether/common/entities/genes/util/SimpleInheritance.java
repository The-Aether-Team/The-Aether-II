package com.gildedgames.aether.common.entities.genes.util;

import com.gildedgames.aether.api.genes.Inheritance;

import java.util.Random;

public class SimpleInheritance implements Inheritance
{

	public enum Type
	{

		RANDOM(0.0F, 1.00F), DOMINANT(0.5F, 1.0F), INBETWEEN(0.25F, 0.75F), RECESSIVE(0.0F, 0.5F);

		private final float min, max;

		Type(float min, float max)
		{
			this.min = min;
			this.max = max;
		}

		public float evaluate(Random r)
		{
			return this.min + (r.nextFloat() * (this.max - this.min));
		}

	}

	private Type type;

	public SimpleInheritance(Type type)
	{
		this.type = type;
	}

	@Override
	public float generateChanceToInherit(Random r)
	{
		return this.type.evaluate(r);
	}

}
