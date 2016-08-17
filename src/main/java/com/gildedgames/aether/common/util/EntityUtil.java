package com.gildedgames.aether.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EntityUtil
{

	private EntityUtil()
	{

	}

	public static void facePos(Entity entity, BlockPos pos, float maxYawIncrease, float maxPitchIncrease)
	{
		double x = pos.getX() - entity.posX;
		double y = pos.getY() - entity.posY;
		double z = pos.getZ() - entity.posZ;

		double d3 = (double) MathHelper.sqrt_double(x * x + z * z);
		float f = (float)(MathHelper.atan2(z, x) * (180D / Math.PI)) - 90.0F;
		float f1 = (float)(-(MathHelper.atan2(y, d3) * (180D / Math.PI)));

		entity.rotationPitch = EntityUtil.updateRotation(entity.rotationPitch, f1, maxPitchIncrease);
		entity.rotationYaw = EntityUtil.updateRotation(entity.rotationYaw, f, maxYawIncrease);
	}

	public static float updateRotation(float angle, float targetAngle, float maxIncrease)
	{
		float f = MathHelper.wrapDegrees(targetAngle - angle);

		if (f > maxIncrease)
		{
			f = maxIncrease;
		}

		if (f < -maxIncrease)
		{
			f = -maxIncrease;
		}

		return angle + f;
	}

}
