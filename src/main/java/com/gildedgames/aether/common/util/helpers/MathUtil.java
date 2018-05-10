package com.gildedgames.aether.common.util.helpers;

import net.minecraft.util.math.MathHelper;

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
}
