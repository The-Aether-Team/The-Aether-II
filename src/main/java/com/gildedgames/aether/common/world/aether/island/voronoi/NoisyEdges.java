package com.gildedgames.aether.common.world.aether.island.voronoi;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Point;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class NoisyEdges
{

	public static final double NOISY_LINE_TRADEOFF = 0.5; // low: jagged vedge; high: jagged dedge

	public Map<Integer, Vector<Point>> path0 = Maps.newHashMap(), path1 = Maps.newHashMap();

	public NoisyEdges()
	{

	}

	private static double doubleRange(final Random rand, final double min, final double max)
	{
		return min + (max - min) * rand.nextDouble();
	}

	private static boolean subdivide(final Random rand, final Vector<Point> points, final Point a, final Point b, final Point c, final Point d,
			final double minLength)
	{
		if (Point.subtract(a, c).length() < minLength || Point.subtract(b, d).length() < minLength)
		{
			return false;
		}

		final double p = doubleRange(rand, 0.2, 0.8); // vertical
		final double q = doubleRange(rand, 0.2, 0.8); // horizontal

		// Midpoints
		final Point e = Point.interpolate(a, d, p);
		final Point f = Point.interpolate(b, c, p);
		final Point g = Point.interpolate(a, b, q);
		final Point i = Point.interpolate(d, c, q);

		// Central Point
		final Point h = Point.interpolate(e, f, q);

		// Divide the quad into subquads, but meet at h
		final double s = 1.0 - doubleRange(rand, -0.4, 0.4);
		final double t = 1.0 - doubleRange(rand, -0.4, 0.4);

		if (!subdivide(rand, points, a, Point.interpolate(g, b, s), h, Point.interpolate(e, d, t), minLength))
		{
			return false;
		}

		points.add(h);

		return subdivide(rand, points, h, Point.interpolate(f, c, s), c, Point.interpolate(i, d, t), minLength);
	}

	public static Vector<Point> buildNoisyLineSegments(final Random rand, final Point a, final Point b, final Point c, final Point d, final double minLength)
	{
		final Vector<Point> points = new Vector<>();

		points.add(a);

		if (!subdivide(rand, points, a, b, c, d, minLength))
		{
			return points;
		}

		points.add(c);

		return points;
	}

	public void buildNoisyEdges(final VoronoiGraph<?> graph, final Random rand)
	{
		for (final Center p : graph.centers)
		{
			for (final Edge edge : p.borders)
			{
				if (edge.d0 != null && edge.d1 != null && edge.v0 != null && edge.v1 != null && !this.path0.containsKey(edge.index))
				{
					final double f = NOISY_LINE_TRADEOFF;

					final Point t = Point.interpolate(edge.v0.loc, edge.d0.loc, f);
					final Point q = Point.interpolate(edge.v0.loc, edge.d1.loc, f);
					final Point r = Point.interpolate(edge.v1.loc, edge.d0.loc, f);
					final Point s = Point.interpolate(edge.v1.loc, edge.d1.loc, f);

					int minLength = 10;

					if (edge.d0.biome != edge.d1.biome)
					{
						minLength = 3;
					}

					if (edge.d0.ocean && edge.d1.ocean)
					{
						minLength = 100;
					}

					if (edge.d0.coast || edge.d1.coast)
					{
						minLength = 1;
					}

					if (edge.river != 0)
					{
						minLength = 1;
					}

					this.path0.put(edge.index, buildNoisyLineSegments(rand, edge.v0.loc, t, edge.midpoint, q, minLength));
					this.path1.put(edge.index, buildNoisyLineSegments(rand, edge.v1.loc, s, edge.midpoint, r, minLength));
				}
			}
		}
	}

}
