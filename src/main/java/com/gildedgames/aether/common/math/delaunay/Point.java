package com.gildedgames.aether.common.math.delaunay;

/**
 * Point.java
 *
 * @author Connor
 */
public class Point
{

	public double x, y;

	public Point(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	public static double distanceSq(final Point p1, final Point p2)
	{
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
	}

	public static double distance(final Point p1, final Point p2)
	{
		return Math.sqrt(distanceSq(p1, p2));
	}

	public static Point interpolate(final Point p1, final Point p2, final double alpha)
	{
		return new Point(lerp(p1.x, p2.x, alpha), lerp(p1.y, p2.y, alpha));
	}

	public static double lerp(final double point1, final double point2, final double alpha)
	{
		return point1 + alpha * (point2 - point1);
	}

	public static Point centroid(final Point... points)
	{
		double centroidX = 0, centroidY = 0;

		for (final Point knot : points)
		{
			centroidX += knot.x;
			centroidY += knot.y;
		}

		return new Point(centroidX / points.length, centroidY / points.length);
	}

	public static Point subtract(final Point p1, final Point p2)
	{
		return new Point(p1.x - p2.x, p1.y - p2.y);
	}

	/** Compare two doubles within a given epsilon */
	private static boolean equals(final double a, final double b, final double eps)
	{
		if (a == b)
		{
			return true;
		}
		// If the difference is less than epsilon, treat as equal.
		return Math.abs(a - b) < eps;
	}

	@Override
	public String toString()
	{
		return this.x + ", " + this.y;
	}

	public double length()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj == this)
		{
			return true;
		}

		if (obj instanceof Point)
		{
			final Point p = (Point) obj;

			return equals(p.x, this.x, 0.000001) && equals(p.y, this.y, 0.000001);
		}

		return false;
	}
}
