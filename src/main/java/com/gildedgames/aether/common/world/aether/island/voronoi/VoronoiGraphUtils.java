package com.gildedgames.aether.common.world.aether.island.voronoi;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Point;
import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Voronoi;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class VoronoiGraphUtils
{
	private static final Color RIVER = new Color(0x3D51B3);

	public static Voronoi lloydRelax(Voronoi v, final int numLloydRelaxations)
	{
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

		return v;
	}

	private static void drawTriangle(final Graphics2D g, final Corner c1, final Corner c2, final Center center)
	{
		final int[] x = new int[3];
		final int[] y = new int[3];
		x[0] = (int) center.loc.x;
		y[0] = (int) center.loc.y;
		x[1] = (int) c1.loc.x;
		y[1] = (int) c1.loc.y;
		x[2] = (int) c2.loc.x;
		y[2] = (int) c2.loc.y;
		g.fillPolygon(x, y, 3);
	}

	public static BufferedImage createMap(final VoronoiGraph<Color> graph)
	{
		final int width = (int) graph.bounds.width;
		final int height = (int) graph.bounds.height;

		final BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		final Graphics2D g = img.createGraphics();

		paint(graph, g);

		return img;
	}

	public static void paint(final VoronoiGraph<Color> graph, final Graphics2D g)
	{
		paint(graph, g, true, true, false, false, false);
	}

	private static void drawPolygon(final VoronoiGraph<Color> graph, final Graphics2D g, final Center c, final Color color)
	{
		g.setColor(color);

		//only used if Center c is on the edge of the graph. allows for completely filling in the outer polygons
		Corner edgeCorner1 = null;
		Corner edgeCorner2 = null;
		c.area = 0;
		for (final Center n : c.neighbors)
		{
			final Edge e = graph.edgeWithCenters(c, n);

			if (e.v0 == null)
			{
				//outermost voronoi edges aren't stored in the graph
				continue;
			}

			//find a corner on the exterior of the graph
			//if this VoronoiEdge e has one, then it must have two,
			//finding these two corners will give us the missing
			//triangle to render. this special triangle is handled
			//outside this for loop
			final Corner cornerWithOneAdjacent = e.v0.border ? e.v0 : e.v1;
			if (cornerWithOneAdjacent.border)
			{
				if (edgeCorner1 == null)
				{
					edgeCorner1 = cornerWithOneAdjacent;
				}
				else
				{
					edgeCorner2 = cornerWithOneAdjacent;
				}
			}

			drawTriangle(g, e.v0, e.v1, c);
			c.area += Math.abs(c.loc.x * (e.v0.loc.y - e.v1.loc.y)
					+ e.v0.loc.x * (e.v1.loc.y - c.loc.y)
					+ e.v1.loc.x * (c.loc.y - e.v0.loc.y)) / 2;
		}

		//handle the missing triangle
		if (edgeCorner2 != null)
		{
			//if these two outer corners are NOT on the same exterior edge of the graph,
			//then we actually must render a polygon (w/ 4 points) and take into consideration
			//one of the four corners (either 0,0 or 0,height or width,0 or width,height)
			//note: the 'missing polygon' may have more than just 4 points. this
			//is common when the number of sites are quite low (less than 5), but not a problem
			//with a more useful number of sites.
			//TODO: find a way to fix this

			if (graph.closeEnough(edgeCorner1.loc.x, edgeCorner2.loc.x, 1))
			{
				drawTriangle(g, edgeCorner1, edgeCorner2, c);
			}
			else
			{
				final int[] x = new int[4];
				final int[] y = new int[4];
				x[0] = (int) c.loc.x;
				y[0] = (int) c.loc.y;
				x[1] = (int) edgeCorner1.loc.x;
				y[1] = (int) edgeCorner1.loc.y;

				//determine which corner this is
				x[2] = (int) ((graph.closeEnough(edgeCorner1.loc.x, graph.bounds.x, 1) || graph.closeEnough(edgeCorner2.loc.x, graph.bounds.x, .5)) ?
						graph.bounds.x :
						graph.bounds.right);
				y[2] = (int) ((graph.closeEnough(edgeCorner1.loc.y, graph.bounds.y, 1) || graph.closeEnough(edgeCorner2.loc.y, graph.bounds.y, .5)) ?
						graph.bounds.y :
						graph.bounds.bottom);

				x[3] = (int) edgeCorner2.loc.x;
				y[3] = (int) edgeCorner2.loc.y;

				g.fillPolygon(x, y, 4);
				c.area += 0; //TODO: area of polygon given vertices
			}
		}
	}

	//also records the area of each voronoi cell
	public static void paint(final VoronoiGraph<Color> graph, final Graphics2D g, final boolean drawBiomes, final boolean drawRivers, final boolean drawSites,
			final boolean drawCorners,
			final boolean drawDelaunay)
	{
		final int numSites = graph.centers.size();

		final Random r = graph.getRandom();

		Color[] defaultColors = null;
		if (!drawBiomes)
		{
			defaultColors = new Color[numSites];
			for (int i = 0; i < defaultColors.length; i++)
			{
				defaultColors[i] = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
		}

		final int width = (int) graph.bounds.width;
		final int height = (int) graph.bounds.height;

		final BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

		final Graphics2D pixelCenterGraphics = img.createGraphics();

		//draw via triangles
		for (final Center p : graph.centers)
		{
			//TODO: Draw noisy edges
			/*for (final Center r : p.neighbors)
			{
				final VoronoiEdge edge = this.lookupEdgeFromCenter(p, r);

				if (this.noisyEdges.path0.containsKey(edge.index) || this.noisyEdges.path1.containsKey(edge.index))
				{
					// edge of map
					continue;
				}

				if (p.ocean != r.ocean)
				{
					g.setStroke(new BasicStroke(2));
					g.setColor(BEACH);
				}
				else if (p.water != r.water)
				{
					// lake boundary
					g.setStroke(new BasicStroke(1));
					g.setColor(LAKE);
				}
				else if (p.water || r.water)
				{
					// Lake interior - we don't want to draw the rivers here
					continue;
				}
				else if (edge.river > 0)
				{
					g.setStroke(new BasicStroke((float) Math.sqrt(edge.river)));
					g.setColor(RIVER);
				}
				else
				{
					//No edge
					continue;
				}

				g.translate(this.noisyEdges.path0.get(edge.index).get(0).x, this.noisyEdges.path0.get(edge.index).get(0).y);
				this.drawPathForwards(g, this.noisyEdges.path0.get(edge.index));
				this.drawPathBackwards(g, this.noisyEdges.path1.get(edge.index));
				g.setStroke(new BasicStroke(1));
			}*/

			drawPolygon(graph, g, p, drawBiomes ? graph.getVisual(p.biome) : defaultColors[p.index]);
			drawPolygon(graph, pixelCenterGraphics, p, new Color(p.index));
		}

		for (final Edge e : graph.edges)
		{
			if (drawDelaunay)
			{
				g.setStroke(new BasicStroke(1));
				g.setColor(Color.YELLOW);
				g.drawLine((int) e.d0.loc.x, (int) e.d0.loc.y, (int) e.d1.loc.x, (int) e.d1.loc.y);
			}
			if (drawRivers && e.river > 0)
			{
				g.setStroke(new BasicStroke(1 + (int) Math.sqrt(e.river * 2)));
				g.setColor(RIVER);
				g.drawLine((int) e.v0.loc.x, (int) e.v0.loc.y, (int) e.v1.loc.x, (int) e.v1.loc.y);
			}
		}

		if (drawSites)
		{
			g.setColor(Color.BLACK);
			graph.centers.forEach((s) -> g.fillOval((int) (s.loc.x - 2), (int) (s.loc.y - 2), 4, 4));
		}

		if (drawCorners)
		{
			g.setColor(Color.WHITE);
			graph.corners.forEach((c) -> g.fillOval((int) (c.loc.x - 2), (int) (c.loc.y - 2), 4, 4));
		}
		g.setColor(Color.WHITE);
		g.drawRect((int) graph.bounds.x, (int) graph.bounds.y, (int) graph.bounds.width, (int) graph.bounds.height);
	}

	// Helper functions for rendering paths
	private static void drawPathForwards(final Graphics2D g, final Vector<Point> path)
	{
		final int[] xs = new int[path.size()];
		final int[] ys = new int[path.size()];

		for (int i = 0; i < path.size(); i++)
		{
			xs[i] = (int) path.get(i).x;
			ys[i] = (int) path.get(i).y;
		}

		g.drawPolyline(xs, ys, path.size());
	}

	private static void drawPathBackwards(final Graphics2D g, final Vector<Point> path)
	{
		final int[] xs = new int[path.size()];
		final int[] ys = new int[path.size()];

		for (int i = path.size() - 1; i >= 0; i--)
		{
			xs[i] = (int) path.get(i).x;
			ys[i] = (int) path.get(i).y;
		}

		g.drawPolyline(xs, ys, path.size());
	}
}
