package com.gildedgames.aether.common.math.delaunay;

/**
 * The line segment connecting the two Sites is part of the Delaunay
 * triangulation; the line segment connecting the two Vertices is part of the
 * Voronoi diagram
 *
 * @author ashaw
 *
 */
public final class VoronoiEdge
{

	final static VoronoiEdge DELETED = new VoronoiEdge(0, 0, 0);

	// the equation of the edge: ax + by = c
	public final double a, b, c;

	// the two Voronoi vertices that the edge connects
	//		(if one of them is null, the edge extends to infinity)
	private final LeftRightPair<Vertex> vertices;

	// Once clipVertices() is called, this Dictionary will hold two Points
	// representing the clipped coordinates of the left and right ends...
	private final LeftRightPair<Point> clippedVertices;

	// the two input Sites for which this VoronoiEdge is a bisector:
	private final LeftRightPair<Site> sites;

	private VoronoiEdge(double a, double b, double c)
	{
		this.vertices = new LeftRightPair<>();
		this.sites = new LeftRightPair<>();
		this.clippedVertices = new LeftRightPair<>();

		this.a = a;
		this.b = b;
		this.c = c;
	}

	public static VoronoiEdge createBisectingEdge(final Site site0, final Site site1)
	{

		final double dx = site1.x - site0.x;
		final double dy = site1.y - site0.y;
		final double absdx = dx > 0 ? dx : -dx;
		final double absdy = dy > 0 ? dy : -dy;
		double c = site0.x * dx + site0.y * dy + (dx * dx + dy * dy) * 0.5;
		final double b;
		final double a;
		if (absdx > absdy)
		{
			a = 1.0;
			b = dy / dx;
			c /= dx;
		}
		else
		{
			b = 1.0;
			a = dx / dy;
			c /= dy;
		}

		final VoronoiEdge edge = new VoronoiEdge(a, b, c);

		edge.setLeftSite(site0);
		edge.setRightSite(site1);
		site0.addEdge(edge);
		site1.addEdge(edge);

		return edge;
	}

	private static double compareSitesDistances_MAX(final VoronoiEdge edge0, final VoronoiEdge edge1)
	{
		final double length0 = edge0.sitesDistanceSq();
		final double length1 = edge1.sitesDistanceSq();

		return Double.compare(length1, length0);
	}

	public static double compareSitesDistances(final VoronoiEdge edge0, final VoronoiEdge edge1)
	{
		return -compareSitesDistances_MAX(edge0, edge1);
	}

	public LineSegment delaunayLine()
	{
		// draw a line connecting the input Sites for which the edge is a bisector:
		return new LineSegment(this.getLeftSite(), this.getRightSite());
	}

	public LineSegment voronoiEdge()
	{
		if (!this.getVisible())
		{
			return new LineSegment(null, null);
		}

		return new LineSegment(this.clippedVertices.getLeft(), this.clippedVertices.getRight());
	}

	public Vertex getLeftVertex()
	{
		return this.vertices.getLeft();
	}

	public Vertex getRightVertex()
	{
		return this.vertices.getRight();
	}

	public void setVertex(final LeftRight leftRight, final Vertex v)
	{
		this.vertices.put(leftRight, v);
	}


	public boolean isPartOfConvexHull()
	{
		return (this.vertices.getLeft() == null || this.vertices.getRight() == null);
	}

	private double sitesDistanceSq()
	{
		return Point.distanceSq(this.getLeftSite(), this.getRightSite());
	}

	public LeftRightPair<Point> getClippedEnds()
	{
		return this.clippedVertices;
	}

	public boolean getVisible()
	{
		return this.clippedVertices.getLeft() != null && this.clippedVertices.getRight() != null;
	}

	public Site getLeftSite()
	{
		return this.sites.getLeft();
	}

	private void setLeftSite(final Site s)
	{
		this.sites.setLeft(s);
	}

	public Site getRightSite()
	{
		return this.sites.getRight();
	}

	private void setRightSite(final Site s)
	{
		this.sites.setRight(s);
	}

	public Site getSite(final LeftRight leftRight)
	{
		return this.sites.get(leftRight);
	}

	public void clipVertices(final Rectangle bounds)
	{
		final double xmin = bounds.x;
		final double ymin = bounds.y;
		final double xmax = bounds.right;
		final double ymax = bounds.bottom;

		final Vertex vertex0;
		final Vertex vertex1;

		if (this.a == 1.0 && this.b >= 0.0)
		{
			vertex0 = this.vertices.getRight();
			vertex1 = this.vertices.getLeft();
		}
		else
		{
			vertex0 = this.vertices.getLeft();
			vertex1 = this.vertices.getRight();
		}

		double y1;
		double y0;
		double x1;
		double x0;

		if (this.a == 1.0)
		{
			y0 = ymin;
			if (vertex0 != null && vertex0.y > ymin)
			{
				y0 = vertex0.y;
			}
			if (y0 > ymax)
			{
				return;
			}
			x0 = this.c - this.b * y0;

			y1 = ymax;
			if (vertex1 != null && vertex1.y < ymax)
			{
				y1 = vertex1.y;
			}
			if (y1 < ymin)
			{
				return;
			}
			x1 = this.c - this.b * y1;

			if ((x0 > xmax && x1 > xmax) || (x0 < xmin && x1 < xmin))
			{
				return;
			}

			if (x0 > xmax)
			{
				x0 = xmax;
				y0 = (this.c - x0) / this.b;
			}
			else if (x0 < xmin)
			{
				x0 = xmin;
				y0 = (this.c - x0) / this.b;
			}

			if (x1 > xmax)
			{
				x1 = xmax;
				y1 = (this.c - x1) / this.b;
			}
			else if (x1 < xmin)
			{
				x1 = xmin;
				y1 = (this.c - x1) / this.b;
			}
		}
		else
		{
			x0 = xmin;
			if (vertex0 != null && vertex0.x > xmin)
			{
				x0 = vertex0.x;
			}
			if (x0 > xmax)
			{
				return;
			}
			y0 = this.c - this.a * x0;

			x1 = xmax;
			if (vertex1 != null && vertex1.x < xmax)
			{
				x1 = vertex1.x;
			}
			if (x1 < xmin)
			{
				return;
			}
			y1 = this.c - this.a * x1;

			if ((y0 > ymax && y1 > ymax) || (y0 < ymin && y1 < ymin))
			{
				return;
			}

			if (y0 > ymax)
			{
				y0 = ymax;
				x0 = (this.c - y0) / this.a;
			}
			else if (y0 < ymin)
			{
				y0 = ymin;
				x0 = (this.c - y0) / this.a;
			}

			if (y1 > ymax)
			{
				y1 = ymax;
				x1 = (this.c - y1) / this.a;
			}
			else if (y1 < ymin)
			{
				y1 = ymin;
				x1 = (this.c - y1) / this.a;
			}
		}

		if (vertex0 == this.vertices.getLeft())
		{
			this.clippedVertices.setLeft(new Point(x0, y0));
			this.clippedVertices.setRight(new Point(x1, y1));
		}
		else
		{
			this.clippedVertices.setRight(new Point(x0, y0));
			this.clippedVertices.setLeft(new Point(x1, y1));
		}
	}
}