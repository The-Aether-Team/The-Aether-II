package com.gildedgames.aether.common.math.delaunay;

import java.util.ArrayList;

public final class Polygon
{
	public static Winding getWinding(ArrayList<Point> vertices)
	{
		final double signedDoubleArea = signedDoubleArea(vertices);
		if (signedDoubleArea < 0)
		{
			return Winding.CLOCKWISE;
		}
		if (signedDoubleArea > 0)
		{
			return Winding.COUNTERCLOCKWISE;
		}
		return Winding.NONE;
	}

	private static double signedDoubleArea(ArrayList<Point> vertices)
	{
		int index, nextIndex;
		final int n = vertices.size();
		Point point, next;
		double signedDoubleArea = 0;
		for (index = 0; index < n; ++index)
		{
			nextIndex = (index + 1) % n;
			point = vertices.get(index);
			next = vertices.get(nextIndex);
			signedDoubleArea += point.x * next.y - next.x * point.y;
		}
		return signedDoubleArea;
	}
}