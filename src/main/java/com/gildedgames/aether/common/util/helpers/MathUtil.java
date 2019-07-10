package com.gildedgames.aether.common.util.helpers;

import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Random;

public class MathUtil
{

	public static boolean epsilonEquals(float a, float b)
	{
		return MathHelper.abs(b - a) < 1.0E-5F;
	}

	public static <T> T getRandomElement(List<T> from, Random rand)
	{
		return from.get(rand.nextInt(from.size()));
	}

	public static double getDoubleRange(Random random, double minimum, double maximum)
	{
		return minimum >= maximum ? minimum : (random.nextDouble() * (maximum - minimum + 1)) + minimum;
	}

	public static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks)
	{
		float f = yawOffset - prevYawOffset;

		while (f < -180.0F)
		{
			f += 360.0F;
		}

		while (f >= 180.0F)
		{
			f -= 360.0F;
		}

		return prevYawOffset + partialTicks * f;
	}



}
