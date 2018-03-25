package com.gildedgames.aether.common.world.aether.island.voronoi;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.*;
import com.gildedgames.aether.common.world.aether.island.voronoi.groundshapes.HeightAlgorithm;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * VoronoiGraph.java
 *
 * @author Connor
 */
public abstract class VoronoiGraph<VISUAL>
{

	final public Rectangle bounds;

	final ArrayList<Edge> edges = Lists.newArrayList();

	final ArrayList<Corner> corners = Lists.newArrayList();

	final ArrayList<Center> centers = Lists.newArrayList();

	final private Random r;

	public VoronoiGraph(Voronoi v, final int numLloydRelaxations, final Random r, final HeightAlgorithm algorithm)
	{
		this.r = r;
		this.bounds = v.get_plotBounds();

		for (int i = 0; i < numLloydRelaxations; i++)
		{
			final ArrayList<Point> points = v.siteCoords();

			for (final Point p : points)
			{
				final ArrayList<Point> region = v.region(p);

				double x = 0;
				double y = 0;

				for (final Point c : region)
				{
					x += c.x;
					y += c.y;
				}

				x /= region.size();
				y /= region.size();

				p.x = x;
				p.y = y;
			}

			v = new Voronoi(points, null, v.get_plotBounds());
		}

		this.buildGraph(v);
		this.improveCorners();

		this.assignCornerElevations(algorithm);
		this.assignOceanCoastAndLand();
		this.redistributeElevations(this.landCorners());
		this.assignPolygonElevations();

		this.calculateDownslopes();
		//calculateWatersheds();
		this.createRivers();
		this.assignCornerMoisture();
		this.redistributeMoisture(this.landCorners());
		this.assignPolygonMoisture();
		this.assignBiomes();
	}

	public Random getRandom()
	{
		return this.r;
	}

	abstract protected Enum getBiome(Center p);

	abstract protected VISUAL getVisual(Enum biome);

	private void improveCorners()
	{
		final Point[] newP = new Point[this.corners.size()];

		for (final Corner c : this.corners)
		{
			if (c.border)
			{
				newP[c.index] = c.loc;
			}
			else
			{
				double x = 0;
				double y = 0;
				for (final Center center : c.touches)
				{
					x += center.loc.x;
					y += center.loc.y;
				}
				newP[c.index] = new Point(x / c.touches.size(), y / c.touches.size());
			}
		}

		this.corners.forEach((c) -> {
			c.loc = newP[c.index];
		});

		this.edges.stream().filter((e) -> (e.v0 != null && e.v1 != null)).forEach((e) -> {
			e.setVornoi(e.v0, e.v1);
		});
	}

	public Edge edgeWithCenters(final Center c1, final Center c2)
	{
		for (final Edge e : c1.borders)
		{
			if (e.d0 == c2 || e.d1 == c2)
			{
				return e;
			}
		}
		return null;
	}

	public boolean closeEnough(final double d1, final double d2, final double diff)
	{
		return Math.abs(d1 - d2) <= diff;
	}

	private void buildGraph(final Voronoi v)
	{
		final HashMap<Point, Center> pointCenterMap = Maps.newHashMap();

		final ArrayList<Point> points = v.siteCoords();

		points.forEach((p) -> {
			final Center c = new Center();
			c.loc = p;
			c.index = this.centers.size();
			this.centers.add(c);
			pointCenterMap.put(p, c);
		});

		//bug fix
		this.centers.forEach((c) -> {
			v.region(c.loc);
		});

		final ArrayList<VoronoiEdge> libedges = v.edges();
		final HashMap<Integer, Corner> pointCornerMap = Maps.newHashMap();

		for (final VoronoiEdge libedge : libedges)
		{
			final LineSegment vEdge = libedge.voronoiEdge();
			final LineSegment dEdge = libedge.delaunayLine();

			final Edge edge = new Edge();
			edge.index = this.edges.size();
			this.edges.add(edge);

			edge.v0 = this.makeCorner(pointCornerMap, vEdge.p0);
			edge.v1 = this.makeCorner(pointCornerMap, vEdge.p1);
			edge.d0 = pointCenterMap.get(dEdge.p0);
			edge.d1 = pointCenterMap.get(dEdge.p1);

			// Centers point to edges. Corners point to edges.
			if (edge.d0 != null)
			{
				edge.d0.borders.add(edge);
			}
			if (edge.d1 != null)
			{
				edge.d1.borders.add(edge);
			}
			if (edge.v0 != null)
			{
				edge.v0.protrudes.add(edge);
			}
			if (edge.v1 != null)
			{
				edge.v1.protrudes.add(edge);
			}

			// Centers point to centers.
			if (edge.d0 != null && edge.d1 != null)
			{
				this.addToCenterList(edge.d0.neighbors, edge.d1);
				this.addToCenterList(edge.d1.neighbors, edge.d0);
			}

			// Corners point to corners
			if (edge.v0 != null && edge.v1 != null)
			{
				this.addToCornerList(edge.v0.adjacent, edge.v1);
				this.addToCornerList(edge.v1.adjacent, edge.v0);
			}

			// Centers point to corners
			if (edge.d0 != null)
			{
				this.addToCornerList(edge.d0.corners, edge.v0);
				this.addToCornerList(edge.d0.corners, edge.v1);
			}
			if (edge.d1 != null)
			{
				this.addToCornerList(edge.d1.corners, edge.v0);
				this.addToCornerList(edge.d1.corners, edge.v1);
			}

			// Corners point to centers
			if (edge.v0 != null)
			{
				this.addToCenterList(edge.v0.touches, edge.d0);
				this.addToCenterList(edge.v0.touches, edge.d1);
			}
			if (edge.v1 != null)
			{
				this.addToCenterList(edge.v1.touches, edge.d0);
				this.addToCenterList(edge.v1.touches, edge.d1);
			}
		}
	}

	// Helper functions for the following for loop; ideally these
	// would be inlined
	private void addToCornerList(final ArrayList<Corner> list, final Corner c)
	{
		if (c != null && !list.contains(c))
		{
			list.add(c);
		}
	}

	private void addToCenterList(final ArrayList<Center> list, final Center c)
	{
		if (c != null && !list.contains(c))
		{
			list.add(c);
		}
	}

	//ensures that each corner is represented by only one corner object
	private Corner makeCorner(final HashMap<Integer, Corner> pointCornerMap, final Point p)
	{
		if (p == null)
		{
			return null;
		}

		final int index = (int) ((int) p.x + (int) (p.y) * this.bounds.width * 2);
		Corner c = pointCornerMap.get(index);

		if (c == null)
		{
			c = new Corner();
			c.loc = p;
			c.border = this.bounds.liesOnAxes(p);
			c.index = this.corners.size();
			this.corners.add(c);
			pointCornerMap.put(index, c);
		}
		return c;
	}

	private void assignCornerElevations(final HeightAlgorithm algorithm)
	{
		final LinkedList<Corner> queue = Lists.newLinkedList();
		for (final Corner c : this.corners)
		{
			c.water = algorithm.isWater(c.loc, this.bounds, this.r);
			if (c.border)
			{
				c.elevation = 0;
				queue.add(c);
			}
			else
			{
				c.elevation = Double.MAX_VALUE;
			}
		}

		while (!queue.isEmpty())
		{
			final Corner c = queue.pop();
			for (final Corner a : c.adjacent)
			{
				double newElevation = 0.01 + c.elevation;
				if (!c.water && !a.water)
				{
					newElevation += 1;
				}
				if (newElevation < a.elevation)
				{
					a.elevation = newElevation;
					queue.add(a);
				}
			}
		}
	}

	private void assignOceanCoastAndLand()
	{
		final LinkedList<Center> queue = Lists.newLinkedList();

		final double waterThreshold = .3;

		for (final Center center : this.centers)
		{
			int numWater = 0;

			for (final Corner c : center.corners)
			{
				if (c.border)
				{
					center.border = center.water = center.ocean = true;
					queue.add(center);
				}
				if (c.water)
				{
					numWater++;
				}
			}
			center.water = center.ocean || ((double) numWater / center.corners.size() >= waterThreshold);
		}
		while (!queue.isEmpty())
		{
			final Center center = queue.pop();
			for (final Center n : center.neighbors)
			{
				if (n.water && !n.ocean)
				{
					n.ocean = true;
					queue.add(n);
				}
			}
		}
		for (final Center center : this.centers)
		{
			boolean oceanNeighbor = false;
			boolean landNeighbor = false;
			for (final Center n : center.neighbors)
			{
				oceanNeighbor |= n.ocean;
				landNeighbor |= !n.water;
			}
			center.coast = oceanNeighbor && landNeighbor;
		}

		for (final Corner c : this.corners)
		{
			int numOcean = 0;
			int numLand = 0;
			for (final Center center : c.touches)
			{
				numOcean += center.ocean ? 1 : 0;
				numLand += !center.water ? 1 : 0;
			}
			c.ocean = numOcean == c.touches.size();
			c.coast = numOcean > 0 && numLand > 0;
			c.water = c.border || ((numLand != c.touches.size()) && !c.coast);
		}
	}

	private ArrayList<Corner> landCorners()
	{
		final ArrayList<Corner> list = Lists.newArrayList();

		for (final Corner c : this.corners)
		{
			if (!c.ocean && !c.coast)
			{
				list.add(c);
			}
		}

		return list;
	}

	private void redistributeElevations(final ArrayList<Corner> landCorners)
	{
		landCorners.sort((o1, o2) -> {
			if (o1.elevation > o2.elevation)
			{
				return 1;
			}
			else if (o1.elevation < o2.elevation)
			{
				return -1;
			}
			return 0;
		});

		final double SCALE_FACTOR = 1.1;
		for (int i = 0; i < landCorners.size(); i++)
		{
			final double y = (double) i / landCorners.size();
			double x = Math.sqrt(SCALE_FACTOR) - Math.sqrt(SCALE_FACTOR * (1 - y));
			x = Math.min(x, 1);
			landCorners.get(i).elevation = x;
		}

		for (final Corner c : this.corners)
		{
			if (c.ocean || c.coast)
			{
				c.elevation = 0.0;
			}
		}
	}

	private void assignPolygonElevations()
	{
		for (final Center center : this.centers)
		{
			double total = 0;
			for (final Corner c : center.corners)
			{
				total += c.elevation;
			}
			center.elevation = total / center.corners.size();
		}
	}

	private void calculateDownslopes()
	{
		for (final Corner c : this.corners)
		{
			Corner down = c;
			//System.out.println("ME: " + c.elevation);
			for (final Corner a : c.adjacent)
			{
				//System.out.println(a.elevation);
				if (a.elevation <= down.elevation)
				{
					down = a;
				}
			}
			c.downslope = down;
		}
	}

	private void createRivers()
	{
		for (int i = 0; i < this.bounds.width / 2; i++)
		{
			Corner c = this.corners.get(r.nextInt(this.corners.size()));

			if (c.ocean || c.elevation < 0.3 || c.elevation > 0.9)
			{
				continue;
			}
			// Bias rivers to go west: if (q.downslope.x > q.x) continue;
			while (!c.coast)
			{
				if (c == c.downslope)
				{
					break;
				}

				final Edge edge = this.lookupEdgeFromCorner(c, c.downslope);

				if (!edge.v0.water || !edge.v1.water)
				{
					edge.river++;
					c.river++;
					c.downslope.river++;  // TODO: fix double count
				}
				c = c.downslope;
			}
		}
	}

	private Edge lookupEdgeFromCorner(final Corner c, final Corner downslope)
	{
		for (final Edge e : c.protrudes)
		{
			if (e.v0 == downslope || e.v1 == downslope)
			{
				return e;
			}
		}
		return null;
	}

	private Edge lookupEdgeFromCenter(final Center c, final Center r)
	{
		for (final Edge edge : c.borders)
		{
			if (edge.d0 == r || edge.d1 == r)
			{
				return edge;
			}
		}

		return null;
	}

	private void assignCornerMoisture()
	{
		final LinkedList<Corner> queue = Lists.newLinkedList();

		for (final Corner c : this.corners)
		{
			if ((c.water || c.river > 0) && !c.ocean)
			{
				c.moisture = c.river > 0 ? Math.min(3.0, (0.2 * c.river)) : 1.0;
				queue.push(c);
			}
			else
			{
				c.moisture = 0.0;
			}
		}

		while (!queue.isEmpty())
		{
			final Corner c = queue.pop();
			for (final Corner a : c.adjacent)
			{
				final double newM = .9 * c.moisture;
				if (newM > a.moisture)
				{
					a.moisture = newM;
					queue.add(a);
				}
			}
		}

		// Salt water
		for (final Corner c : corners)
		{
			if (c.ocean || c.coast)
			{
				c.moisture = 1.0;
			}
		}
	}

	private void redistributeMoisture(final ArrayList<Corner> landCorners)
	{
		landCorners.sort((o1, o2) -> {
			if (o1.moisture > o2.moisture)
			{
				return 1;
			}
			else if (o1.moisture < o2.moisture)
			{
				return -1;
			}
			return 0;
		});
		for (int i = 0; i < landCorners.size(); i++)
		{
			landCorners.get(i).moisture = (double) i / landCorners.size();
		}
	}

	private void assignPolygonMoisture()
	{
		for (final Center center : this.centers)
		{
			double total = 0;
			for (final Corner c : center.corners)
			{
				total += c.moisture;
			}
			center.moisture = total / center.corners.size();
		}
	}

	private void assignBiomes()
	{
		for (final Center center : this.centers)
		{
			center.biome = this.getBiome(center);
		}
	}
}
