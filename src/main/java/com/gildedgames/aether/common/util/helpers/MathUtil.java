package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.common.entities.util.flying.advanced.FlightPath;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import javax.vecmath.Point3d;
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

	public static BlockPos getBlockBezier(FlightPath path, double t)
	{
		double t1 = 1 - t;

		BlockPos start = path.getStartPos(), end = path.getEndPos();
		Point3d startHandle = path.getStartHandle(), endHandle = path.getEndHandle();

		double x0 = t1 * t1 * t1 * start.getX();
		double x1 = 3 * t1 * t1 * t * startHandle.getX();
		double x2 = 3 * t1 * t * t * endHandle.getX();
		double x3 = t * t * t * end.getX();

		double y0 = t1 * t1 * t1 * start.getY();
		double y1 = 3 * t1 * t1 * t * startHandle.getY();
		double y2 = 3 * t1 * t * t * endHandle.getY();
		double y3 = t * t * t * end.getY();

		double z0 = t1 * t1 * t1 * start.getZ();
		double z1 = 3 * t1 * t1 * t * startHandle.getZ();
		double z2 = 3 * t1 * t * t * endHandle.getZ();
		double z3 = t * t * t * end.getZ();

		return new BlockPos(x0 + x1 + x2 + x3, y0 + y1 + y2 + y3, z0 + z1 + z2 + z3);
	}

	public static Point3d getPoints(FlightPath path, double t)
	{
		return getPoints(path, null, t);
	}

	public static Point3d getPoints(FlightPath path, Point3d dest, double t)
	{
		if (dest == null)
		{
			dest = new Point3d();
		}

		double t1 = 1 - t;

		BlockPos start = path.getStartPos(), end = path.getEndPos();
		Point3d startHandle = path.getStartHandle(), endHandle = path.getEndHandle();

		double x0 = t1 * t1 * t1 * start.getX();
		double x1 = 3 * t1 * t1 * t * startHandle.getX();
		double x2 = 3 * t1 * t * t * endHandle.getX();
		double x3 = t * t * t * end.getX();

		double y0 = t1 * t1 * t1 * start.getY();
		double y1 = 3 * t1 * t1 * t * startHandle.getY();
		double y2 = 3 * t1 * t * t * endHandle.getY();
		double y3 = t * t * t * end.getY();

		double z0 = t1 * t1 * t1 * start.getZ();
		double z1 = 3 * t1 * t1 * t * startHandle.getZ();
		double z2 = 3 * t1 * t * t * endHandle.getZ();
		double z3 = t * t * t * end.getZ();

		dest.x = x0 + x1 + x2 + x3;
		dest.y = y0 + y1 + y2 + y3;
		dest.z = z0 + z1 + z2 + z3;

		return dest;
	}


}
