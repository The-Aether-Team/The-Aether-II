package com.gildedgames.aether.client.util;

import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Quaternion;

public class QuaternionUtil
{
	public static final Vec3d Z_AXIS = new Vec3d(0, 0, 1);

	public static final Quaternion Y_IDENTITY = new Quaternion(0, 1, 0, (float) Math.PI);

	public static final Quaternion IDENTITY = new Quaternion();

	public static Quaternion lookAt(Vec3d sourcePoint, Vec3d destPoint)
	{
		Vec3d forward = destPoint.subtract(sourcePoint).normalize();

		double dot = Z_AXIS.dotProduct(forward);

		if (Math.abs(dot - (-1.0f)) < 0.000001f)
		{
			return Y_IDENTITY;
		}

		if (Math.abs(dot - (1.0f)) < 0.000001f)
		{
			return IDENTITY;
		}

		float rotAngle = (float)Math.acos(dot);
		Vec3d rotAxis = Z_AXIS.crossProduct(forward);
		rotAxis = rotAxis.normalize();

		float halfAngle = rotAngle * .5f;
		float s = (float)Math.sin(halfAngle);

		return new Quaternion((float) rotAxis.x * s, (float) rotAxis.y * s, (float)  rotAxis.z * s, (float) Math.cos(halfAngle));
	}

	public static Quaternion interpolateQuaternion(Quaternion a, Quaternion b, float p)
	{
		Quaternion d = new Quaternion(b.x - a.x, b.y - a.y, b.z - a.z, b.w - a.w);
		Quaternion q = new Quaternion(a.x + d.x * p, a.y + d.y * p, a.z + d.z * p, a.w + d.w * p);

		return q.normalise(q);
	}
}
