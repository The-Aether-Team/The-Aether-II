package com.gildedgames.aether.common.util;

import java.util.Random;

public class XORShiftRandom extends Random
{
	private long seed;

	public XORShiftRandom()
	{
		this(System.nanoTime());
	}

	public XORShiftRandom(long seed)
	{
		this.seed = seed;
	}

	@Override
	protected int next(int nbits)
	{
		long x = this.seed;
		x ^= (x << 21);
		x ^= (x >>> 35);
		x ^= (x << 4);

		this.seed = x;

		x &= ((1L << nbits) - 1);

		return (int) x;
	}
}
