package com.gildedgames.aether.common.math.delaunay;

/*
 * Java implementaition by Connor Clark (www.hotengames.com). Pretty much a 1:1
 * translation of a wonderful map generating algorthim by Amit Patel of Red Blob Games,
 * which can be found here (http://www-cs-students.stanford.edu/~amitp/game-programming/polygon-map-generation/)
 * Hopefully it's of use to someone out there who needed it in Java like I did!
 * Note, the only island mode implemented is Radial. Implementing more is generateDecorations for another day.
 *
 * FORTUNE'S ALGORTIHIM
 *
 * This is a java implementation of an AS3 (Flash) implementation of an algorthim
 * originally created in C++. Pretty much a 1:1 translation from as3 to java, save
 * for some necessary workarounds. Original as3 implementation by Alan Shaw (of nodename)
 * can be found here (https://github.com/nodename/as3delaunay). Original algorthim
 * by Steven Fortune (see lisence for c++ implementation below)
 *
 * The author of this software is Steven Fortune.  Copyright (c) 1994 by AT&T
 * Bell Laboratories.
 * Permission to use, copy, modify, and distribute this software for any
 * purpose without fee is hereby granted, provided that this entire notice
 * is included in all copies of any software which is or includes a copy
 * or modification of this software and in all copies of the supporting
 * documentation for such software.
 * THIS SOFTWARE IS BEING PROVIDED "AS IS", WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTY.  IN PARTICULAR, NEITHER THE AUTHORS NOR AT&T MAKE ANY
 * REPRESENTATION OR WARRANTY OF ANY KIND CONCERNING THE MERCHANTABILITY
 * OF THIS SOFTWARE OR ITS FITNESS FOR ANY PARTICULAR PURPOSE.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Voronoi
{

	private SiteList sites;

	private ArrayList<VoronoiEdge> edges;

	// TODO generalize this so it doesn't have to be a rectangle;
	// then we can make the fractal voronois-within-voronois
	private Rectangle plotBounds;

	public Voronoi(final List<Point> points, final Rectangle plotBounds)
	{
		this.init(points, plotBounds);
		this.fortunesAlgorithm();
	}

	public Voronoi(final int numSites, final Random r, final Rectangle plotBounds)
	{
		final ArrayList<Point> points = new ArrayList<>(numSites);
		for (int i = 0; i < numSites; i++)
		{
			points.add(new Point(plotBounds.x + (r.nextDouble() * plotBounds.width), plotBounds.y + (r.nextDouble() * plotBounds.height)));
		}
		this.init(points, plotBounds);
		this.fortunesAlgorithm();
	}

	public static int compareByYThenX(final Site s1, final Site s2)
	{
		return Double.compare(s1.y, s2.y);
	}

	private static int compareByYThenX(final Site s1, final Point s2)
	{
		return Double.compare(s1.y, s2.y);
	}

	public Rectangle getPlotBounds()
	{
		return this.plotBounds;
	}

	private void init(final List<Point> points, final Rectangle plotBounds)
	{
		this.sites = new SiteList();
		this.addSites(points);
		this.plotBounds = plotBounds;
		this.edges = new ArrayList<>();
	}

	private void addSites(final List<Point> points)
	{
		final int length = points.size();
		for (int i = 0; i < length; ++i)
		{
			this.addSite(points.get(i), i);
		}
	}

	private void addSite(final Point p, final int index)
	{
		final Site site = new Site(p, index);
		this.sites.push(site);
	}

	public ArrayList<VoronoiEdge> edges()
	{
		return this.edges;
	}

	public ArrayList<Point> region(final Site site)
	{
		return site.region(this.plotBounds);
	}

	// TODO: bug: if you call this before you call region(), generateDecorations goes wrong :(
	public Site[] neighborSitesForSite(final Site site)
	{
		return site.neighborSites();
	}

	public List<Site> getSites()
	{
		return this.sites.getInner();
	}

	private void fortunesAlgorithm()
	{
		final Rectangle dataBounds = this.sites.getSitesBounds();

		final int sqrtnsites = (int) Math.sqrt(this.sites.getSize() + 4);

		final HalfEdgePriorityQueue heap = new HalfEdgePriorityQueue(dataBounds.y, dataBounds.height, sqrtnsites);
		final EdgeList edgeList = new EdgeList(dataBounds.x, dataBounds.width, sqrtnsites);

		final ArrayDeque<Site> sortedSites = this.sites.getSortedQueue();

		final Site bottomMostSite = sortedSites.poll();
		Site newSite = sortedSites.poll();

		Point newintstar = null;

		while (true)
		{
			if (!heap.empty())
			{
				newintstar = heap.min();
			}

			if (newSite != null && (heap.empty() || compareByYThenX(newSite, newintstar) < 0))
			{
				/* new site is smallest */

				// Step 8:
				HalfEdge lbnd = edgeList.edgeListLeftNeighbor(newSite);    // the HalfEdge just to the left of newSite
				HalfEdge rbnd = lbnd.edgeListRightNeighbor;        // the HalfEdge just to the right
				Site bottomSite = this.rightRegion(lbnd, bottomMostSite);        // this is the same as leftRegion(rbnd)
				// this Site determines the region containing the new site

				// Step 9:
				final VoronoiEdge edge = VoronoiEdge.createBisectingEdge(bottomSite, newSite);

				this.edges.add(edge);

				HalfEdge bisector = new HalfEdge(edge, LeftRight.LEFT);

				// inserting two Halfedges into edgeList constitutes Step 10:
				// insert bisector to the right of lbnd:
				edgeList.insert(lbnd, bisector);

				Vertex vertex;

				// first half of Step 11:
				if ((vertex = Vertex.intersect(lbnd, bisector)) != null)
				{
					heap.remove(lbnd);
					lbnd.vertex = vertex;
					lbnd.ystar = vertex.y + newSite.dist(vertex);
					heap.insert(lbnd);
				}

				lbnd = bisector;
				bisector = new HalfEdge(edge, LeftRight.RIGHT);
				// second HalfEdge for Step 10:
				// insert bisector to the right of lbnd:
				edgeList.insert(lbnd, bisector);

				// second half of Step 11:
				if ((vertex = Vertex.intersect(bisector, rbnd)) != null)
				{
					bisector.vertex = vertex;
					bisector.ystar = vertex.y + newSite.dist(vertex);
					heap.insert(bisector);
				}

				newSite = sortedSites.poll();
			}
			else if (!heap.empty())
			{
				/* intersection is smallest */
				final HalfEdge lbnd = heap.extractMin();
				final HalfEdge llbnd = lbnd.edgeListLeftNeighbor;
				final HalfEdge rbnd = lbnd.edgeListRightNeighbor;
				final HalfEdge rrbnd = rbnd.edgeListRightNeighbor;
				Site bottomSite = this.leftRegion(lbnd, bottomMostSite);
				Site topSite = this.rightRegion(rbnd, bottomMostSite);
				// these three sites define a Delaunay triangle
				// (not actually using these for anything...)

				Vertex v = lbnd.vertex;
				lbnd.edge.setVertex(lbnd.leftRight, v);
				rbnd.edge.setVertex(rbnd.leftRight, v);

				edgeList.remove(lbnd);
				heap.remove(rbnd);
				edgeList.remove(rbnd);

				LeftRight leftRight = LeftRight.LEFT;

				if (bottomSite.y > topSite.y)
				{
					Site tempSite = bottomSite;
					bottomSite = topSite;
					topSite = tempSite;
					leftRight = LeftRight.RIGHT;
				}

				final VoronoiEdge edge = VoronoiEdge.createBisectingEdge(bottomSite, topSite);

				this.edges.add(edge);

				HalfEdge bisector = new HalfEdge(edge, leftRight);
				edgeList.insert(llbnd, bisector);
				edge.setVertex(leftRight.other(), v);

				Vertex vertex;

				if ((vertex = Vertex.intersect(llbnd, bisector)) != null)
				{
					heap.remove(llbnd);
					llbnd.vertex = vertex;
					llbnd.ystar = vertex.y + bottomSite.dist(vertex);
					heap.insert(llbnd);
				}

				if ((vertex = Vertex.intersect(bisector, rrbnd)) != null)
				{
					bisector.vertex = vertex;
					bisector.ystar = vertex.y + bottomSite.dist(vertex);
					heap.insert(bisector);
				}
			}
			else
			{
				break;
			}
		}

		// we need the vertices to clip the edges
		for (final VoronoiEdge e : this.edges)
		{
			e.clipVertices(this.plotBounds);
		}
	}

	private Site leftRegion(final HalfEdge he, final Site site)
	{
		final VoronoiEdge edge = he.edge;
		if (edge == null)
		{
			return site;
		}
		return edge.getSite(he.leftRight);
	}

	private Site rightRegion(final HalfEdge he, final Site site)
	{
		final VoronoiEdge edge = he.edge;
		if (edge == null)
		{
			return site;
		}
		return edge.getSite(he.leftRight.other());
	}
}
