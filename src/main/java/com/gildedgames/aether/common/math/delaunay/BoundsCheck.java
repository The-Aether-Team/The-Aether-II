package com.gildedgames.aether.common.math.delaunay;

final class BoundsCheck
{

	final public static int TOP = 1;

	final public static int BOTTOM = 2;

	final public static int LEFT = 4;

	final public static int RIGHT = 8;

	public static int check(final Point point, final Rectangle bounds)
	{
		int value = 0;
		if (point.x == bounds.left)
		{
			value |= LEFT;
		}
		if (point.x == bounds.right)
		{
			value |= RIGHT;
		}
		if (point.y == bounds.top)
		{
			value |= TOP;
		}
		if (point.y == bounds.bottom)
		{
			value |= BOTTOM;
		}
		return value;
	}
}
