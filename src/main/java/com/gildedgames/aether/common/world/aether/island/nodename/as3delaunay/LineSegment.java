package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

public final class LineSegment extends Object
{

	public Point p0, p1;

	public LineSegment(final Point p0, final Point p1)
	{
		super();
		this.p0 = p0;
		this.p1 = p1;
	}

	public static double compareLengths_MAX(final LineSegment segment0, final LineSegment segment1)
	{
		final double length0 = Point.distance(segment0.p0, segment0.p1);
		final double length1 = Point.distance(segment1.p0, segment1.p1);
		if (length0 < length1)
		{
			return 1;
		}
		if (length0 > length1)
		{
			return -1;
		}
		return 0;
	}

	public static double compareLengths(final LineSegment edge0, final LineSegment edge1)
	{
		return -compareLengths_MAX(edge0, edge1);
	}
}