package com.gildedgames.aether.common.util.helpers;

import net.minecraft.util.math.MathHelper;

public class MathUtil
{
	public static boolean epsilonEquals(float p_180185_0_, float p_180185_1_)
	{
		return MathHelper.abs(p_180185_1_ - p_180185_0_) < 1.0E-5F;
	}
}
