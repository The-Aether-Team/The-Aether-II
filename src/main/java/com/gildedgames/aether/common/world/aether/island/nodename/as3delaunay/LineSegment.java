package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

public final class LineSegment
{

	private final Point p0;

	private final Point p1;

	public LineSegment(final Point p0, final Point p1)
	{
		this.p0 = p0;
		this.p1 = p1;
	}

	private static double compareLengths_MAX(final LineSegment segment0, final LineSegment segment1)
	{
		final double length0 = Point.distanceSq(segment0.p0, segment0.p1);
		final double length1 = Point.distanceSq(segment1.p0, segment1.p1);

		return Double.compare(length1, length0);
	}

	public static double compareLengths(final LineSegment edge0, final LineSegment edge1)
	{
		return -compareLengths_MAX(edge0, edge1);
	}
}