package com.gildedgames.aether.common.world.aether.island.voronoi;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Point;
import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Site;
import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Voronoi;

import java.util.ArrayList;
import java.util.List;

public class VoronoiGraphUtils
{
	public static Voronoi lloydRelax(Voronoi v, final int numLloydRelaxations)
	{
		for (int i = 0; i < numLloydRelaxations; i++)
		{
			final List<Site> sites = v.getSites();
			final List<Point> points = new ArrayList<>(sites.size());

			for (final Site site : sites)
			{
				final ArrayList<Point> region = v.region(site);

				double x = 0;
				double y = 0;

				for (final Point c : region)
				{
					x += c.x;
					y += c.y;
				}

				x /= region.size();
				y /= region.size();

				site.x = x;
				site.y = y;

				points.add(site);
			}

			v = new Voronoi(points, v.getPlotBounds());
		}

		return v;
	}
}
