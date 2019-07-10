package com.gildedgames.aether.common.math.delaunay;

import java.util.ArrayList;
import java.util.Collections;

public final class Site extends Point implements Comparable<Site>
{

	final private static double EPSILON = .005;

	// the edges that define this Site's Voronoi region:
	private ArrayList<VoronoiEdge> edges;

	private int siteIndex;

	// which end of each edge hooks up with the previous edge in edges:
	private ArrayList<LeftRight> edgeLeftRights;

	// ordered list of points that define the region clipped to bounds:
	private ArrayList<Point> region;

	public Site(final Point p, final int index)
	{
		super(p.x, p.y);

		this.siteIndex = index;
		this.edges = new ArrayList<>();
		this.region = null;
	}

	private static boolean closeEnough(final Point p0, final Point p1)
	{
		return Point.distanceSq(p0, p1) < EPSILON;
	}

	void addEdge(final VoronoiEdge edge)
	{
		this.edges.add(edge);
	}

	public VoronoiEdge nearestEdge()
	{
		// edges.sort(VoronoiEdge.compareSitesDistances);
		this.edges.sort((o1, o2) -> (int) VoronoiEdge.compareSitesDistances(o1, o2));
		return this.edges.get(0);
	}

	Site[] neighborSites()
	{
		if (this.edges == null || this.edges.isEmpty())
		{
			return new Site[0];
		}

		if (this.edgeLeftRights == null)
		{
			this.reorderEdges();
		}

		final Site[] list = new Site[this.edges.size()];

		for (int i = 0; i < this.edges.size(); i++)
		{
			VoronoiEdge edge = this.edges.get(i);

			list[i] = this.neighborSite(edge);
		}

		return list;
	}

	private Site neighborSite(final VoronoiEdge edge)
	{
		if (this == edge.getLeftSite())
		{
			return edge.getRightSite();
		}
		if (this == edge.getRightSite())
		{
			return edge.getLeftSite();
		}
		return null;
	}

	ArrayList<Point> region(final Rectangle clippingBounds)
	{
		if (this.edges == null || this.edges.isEmpty())
		{
			return new ArrayList<>();
		}
		if (this.edgeLeftRights == null)
		{
			this.reorderEdges();
			this.region = this.clipToBounds(clippingBounds);
			if (Polygon.getWinding(this.region) == Winding.CLOCKWISE)
			{
				Collections.reverse(this.region);
			}
		}
		return this.region;
	}

	private void reorderEdges()
	{
		final EdgeReorderer reorderer = new EdgeReorderer(this.edges);

		this.edges = reorderer.getEdges();
		this.edgeLeftRights = reorderer.getEdgeLeftRights();
	}

	private ArrayList<Point> clipToBounds(final Rectangle bounds)
	{
		final int n = this.edges.size();
		int i = 0;
		while (i < n && (!this.edges.get(i).getVisible()))
		{
			++i;
		}

		if (i == n)
		{
			// no edges visible
			return new ArrayList<>();
		}

		VoronoiEdge edge = this.edges.get(i);
		LeftRight leftRight = this.edgeLeftRights.get(i);

		final ArrayList<Point> points = new ArrayList<>();
		points.add(edge.getClippedEnds().get(leftRight));
		points.add(edge.getClippedEnds().get(leftRight.other()));

		for (int j = i + 1; j < n; ++j)
		{
			edge = this.edges.get(j);

			if (!edge.getVisible())
			{
				continue;
			}

			this.connect(points, j, bounds, false);
		}

		// close up the polygon by adding another corner point of the bounds if needed:
		this.connect(points, i, bounds, true);

		return points;
	}

	private void connect(final ArrayList<Point> points, final int j, final Rectangle bounds, final boolean closingUp)
	{
		final Point rightPoint = points.get(points.size() - 1);
		final VoronoiEdge newEdge = this.edges.get(j);
		final LeftRight newLeftRight = this.edgeLeftRights.get(j);
		// the point that  must be connected to rightPoint:
		final Point newPoint = newEdge.getClippedEnds().get(newLeftRight);
		if (!closeEnough(rightPoint, newPoint))
		{
			// The points do not coincide, so they must have been clipped at the bounds;
			// see if they are on the same border of the bounds:
			if (rightPoint.x != newPoint.x && rightPoint.y != newPoint.y)
			{
				// They are on different borders of the bounds;
				// insert one or two corners of bounds as needed to hook them up:
				// (NOTE this will not be correct if the region should take up more than
				// half of the bounds rect, for then we will have gone the wrong way
				// around the bounds and included the smaller part rather than the larger)
				final int rightCheck = BoundsCheck.check(rightPoint, bounds);
				final int newCheck = BoundsCheck.check(newPoint, bounds);
				final double px;
				final double py;
				if ((rightCheck & BoundsCheck.RIGHT) != 0)
				{
					px = bounds.right;
					if ((newCheck & BoundsCheck.BOTTOM) != 0)
					{
						py = bounds.bottom;
						points.add(new Point(px, py));
					}
					else if ((newCheck & BoundsCheck.TOP) != 0)
					{
						py = bounds.top;
						points.add(new Point(px, py));
					}
					else if ((newCheck & BoundsCheck.LEFT) != 0)
					{
						if (rightPoint.y - bounds.y + newPoint.y - bounds.y < bounds.height)
						{
							py = bounds.top;
						}
						else
						{
							py = bounds.bottom;
						}
						points.add(new Point(px, py));
						points.add(new Point(bounds.left, py));
					}
				}
				else if ((rightCheck & BoundsCheck.LEFT) != 0)
				{
					px = bounds.left;
					if ((newCheck & BoundsCheck.BOTTOM) != 0)
					{
						py = bounds.bottom;
						points.add(new Point(px, py));
					}
					else if ((newCheck & BoundsCheck.TOP) != 0)
					{
						py = bounds.top;
						points.add(new Point(px, py));
					}
					else if ((newCheck & BoundsCheck.RIGHT) != 0)
					{
						if (rightPoint.y - bounds.y + newPoint.y - bounds.y < bounds.height)
						{
							py = bounds.top;
						}
						else
						{
							py = bounds.bottom;
						}
						points.add(new Point(px, py));
						points.add(new Point(bounds.right, py));
					}
				}
				else if ((rightCheck & BoundsCheck.TOP) != 0)
				{
					py = bounds.top;
					if ((newCheck & BoundsCheck.RIGHT) != 0)
					{
						px = bounds.right;
						points.add(new Point(px, py));
					}
					else if ((newCheck & BoundsCheck.LEFT) != 0)
					{
						px = bounds.left;
						points.add(new Point(px, py));
					}
					else if ((newCheck & BoundsCheck.BOTTOM) != 0)
					{
						if (rightPoint.x - bounds.x + newPoint.x - bounds.x < bounds.width)
						{
							px = bounds.left;
						}
						else
						{
							px = bounds.right;
						}
						points.add(new Point(px, py));
						points.add(new Point(px, bounds.bottom));
					}
				}
				else if ((rightCheck & BoundsCheck.BOTTOM) != 0)
				{
					py = bounds.bottom;
					if ((newCheck & BoundsCheck.RIGHT) != 0)
					{
						px = bounds.right;
						points.add(new Point(px, py));
					}
					else if ((newCheck & BoundsCheck.LEFT) != 0)
					{
						px = bounds.left;
						points.add(new Point(px, py));
					}
					else if ((newCheck & BoundsCheck.TOP) != 0)
					{
						if (rightPoint.x - bounds.x + newPoint.x - bounds.x < bounds.width)
						{
							px = bounds.left;
						}
						else
						{
							px = bounds.right;
						}
						points.add(new Point(px, py));
						points.add(new Point(px, bounds.top));
					}
				}
			}
			if (closingUp)
			{
				// newEdge's ends have already been added
				return;
			}
			points.add(newPoint);
		}
		final Point newRightPoint = newEdge.getClippedEnds().get(newLeftRight.other());
		if (!closeEnough(points.get(0), newRightPoint))
		{
			points.add(newRightPoint);
		}
	}

	public double dist(final Vertex p)
	{
		return Point.distance(p, this);
	}

	/**
	 * sort sites on y, then x, coord also change each site's siteIndex to
	 * match its new position in the list so the siteIndex can be used to
	 * identify the site for nearest-neighbor queries
	 *
	 * haha "also" - means more than one responsibility...
	 *
	 */
	@Override
	public int compareTo(Site other)
	{
		if (this.y < other.y)
		{
			if (this.siteIndex > other.siteIndex)
			{
				final int tempIndex = this.siteIndex;
				this.siteIndex = other.siteIndex;
				other.siteIndex = tempIndex;
			}

			return -1;
		}
		else if (this.y > other.y)
		{
			if (other.siteIndex > this.siteIndex)
			{
				final int tempIndex = other.siteIndex;
				other.siteIndex = this.siteIndex;
				this.siteIndex = tempIndex;
			}

			return 1;
		}
		else
		{
			return 0;
		}
	}
}

