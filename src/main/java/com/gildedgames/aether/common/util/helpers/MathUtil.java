package com.gildedgames.aether.common.util.helpers;

import net.minecraft.util.math.MathHelper;

import java.util.Collection;
import java.util.Random;

public class MathUtil
{
	public static boolean epsilonEquals(double p_180185_0_, double p_180185_1_)
	{
		return Math.abs(p_180185_1_ - p_180185_0_) < 1.0E-5F;
	}

	public static boolean epsilonEquals(float p_180185_0_, float p_180185_1_)
	{
		return MathHelper.abs(p_180185_1_ - p_180185_0_) < 1.0E-5F;
	}

	public static <T> T getRandomElement(Collection<T> from, Random rand)
	{
		int i = rand.nextInt(from.size());
		return (T) from.toArray()[i];
	}

	public static int floor(long value)
	{
		int i = (int) value;
		return value < (long) i ? i - 1 : i;
	}

	public static double getDoubleRange(Random random, double minimum, double maximum)
	{
		return minimum >= maximum ? minimum : (random.nextDouble() * (maximum - minimum + 1)) + minimum;
	}
}
