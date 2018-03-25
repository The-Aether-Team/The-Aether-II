package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

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

	public static double distance(final Point _coord, final Point _coord0)
	{
		return Math.sqrt((_coord.x - _coord0.x) * (_coord.x - _coord0.x) + (_coord.y - _coord0.y) * (_coord.y - _coord0.y));
	}

	public static Point interpolate(final Point p1, final Point p2, final double alpha)
	{
		return new Point(lerp(p1.x, p2.x, alpha), lerp(p1.y, p2.y, alpha));
	}

	private static double lerp(final double point1, final double point2, final double alpha)
	{
		return point1 + alpha * (point2 - point1);
	}

	public static Point subtract(final Point p1, final Point p2)
	{
		return new Point(p1.x - p2.x, p1.y - p2.y);
	}

	@Override
	public String toString()
	{
		return x + ", " + y;
	}

	public double l2()
	{
		return x * x + y * y;
	}

	public double length()
	{
		return Math.sqrt(x * x + y * y);
	}
}
