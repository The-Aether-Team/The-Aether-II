package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public final class Site implements ICoord
{

	final private static double EPSILON = .005;

	private static final Stack<Site> _pool = new Stack();

	public Color color;

	public double weight;

	// the edges that define this Site's Voronoi region:
	public ArrayList<VoronoiEdge> _edges;

	private Point _coord;

	private int _siteIndex;

	// which end of each edge hooks up with the previous edge in _edges:
	private ArrayList<LR> _edgeOrientations;

	// ordered list of points that define the region clipped to bounds:
	private ArrayList<Point> _region;

	public Site(final Point p, final int index, final double weight, final Color color)
	{
		init(p, index, weight, color);
	}

	public static Site create(final Point p, final int index, final double weight, final Color color)
	{
		if (_pool.size() > 0)
		{
			return _pool.pop().init(p, index, weight, color);
		}
		else
		{
			return new Site(p, index, weight, color);
		}
	}

	public static void sortSites(final ArrayList<Site> sites)
	{
		//sites.sort(Site.compare);
		Collections.sort(sites, new Comparator<Site>()
		{
			@Override
			public int compare(final Site o1, final Site o2)
			{
				return (int) Site.compare(o1, o2);
			}
		});
	}

	/**
	 * sort sites on y, then x, coord also change each site's _siteIndex to
	 * match its new position in the list so the _siteIndex can be used to
	 * identify the site for nearest-neighbor queries
	 *
	 * haha "also" - means more than one responsibility...
	 *
	 */
	private static double compare(final Site s1, final Site s2)
	{
		final int returnValue = Voronoi.compareByYThenX(s1, s2);

		// swap _siteIndex values if necessary to match new ordering:
		final int tempIndex;
		if (returnValue == -1)
		{
			if (s1._siteIndex > s2._siteIndex)
			{
				tempIndex = s1._siteIndex;
				s1._siteIndex = s2._siteIndex;
				s2._siteIndex = tempIndex;
			}
		}
		else if (returnValue == 1)
		{
			if (s2._siteIndex > s1._siteIndex)
			{
				tempIndex = s2._siteIndex;
				s2._siteIndex = s1._siteIndex;
				s1._siteIndex = tempIndex;
			}

		}

		return returnValue;
	}

	private static boolean closeEnough(final Point p0, final Point p1)
	{
		return Point.distance(p0, p1) < EPSILON;
	}

	@Override
	public Point get_coord()
	{
		return _coord;
	}

	private Site init(final Point p, final int index, final double weight, final Color color)
	{
		_coord = p;
		_siteIndex = index;
		this.weight = weight;
		this.color = color;
		_edges = new ArrayList();
		_region = null;
		return this;
	}

	@Override
	public String toString()
	{
		return "Site " + _siteIndex + ": " + get_coord();
	}

	private void move(final Point p)
	{
		clear();
		_coord = p;
	}

	public void dispose()
	{
		_coord = null;
		clear();
		_pool.push(this);
	}

	private void clear()
	{
		if (_edges != null)
		{
			_edges.clear();
			_edges = null;
		}
		if (_edgeOrientations != null)
		{
			_edgeOrientations.clear();
			_edgeOrientations = null;
		}
		if (_region != null)
		{
			_region.clear();
			_region = null;
		}
	}

	void addEdge(final VoronoiEdge edge)
	{
		_edges.add(edge);
	}

	public VoronoiEdge nearestEdge()
	{
		// _edges.sort(VoronoiEdge.compareSitesDistances);
		Collections.sort(_edges, new Comparator<VoronoiEdge>()
		{
			@Override
			public int compare(final VoronoiEdge o1, final VoronoiEdge o2)
			{
				return (int) VoronoiEdge.compareSitesDistances(o1, o2);
			}
		});
		return _edges.get(0);
	}

	ArrayList<Site> neighborSites()
	{
		if (_edges == null || _edges.isEmpty())
		{
			return new ArrayList();
		}
		if (_edgeOrientations == null)
		{
			reorderEdges();
		}
		final ArrayList<Site> list = new ArrayList();
		for (final VoronoiEdge edge : _edges)
		{
			list.add(neighborSite(edge));
		}
		return list;
	}

	private Site neighborSite(final VoronoiEdge edge)
	{
		if (this == edge.get_leftSite())
		{
			return edge.get_rightSite();
		}
		if (this == edge.get_rightSite())
		{
			return edge.get_leftSite();
		}
		return null;
	}

	ArrayList<Point> region(final Rectangle clippingBounds)
	{
		if (_edges == null || _edges.isEmpty())
		{
			return new ArrayList();
		}
		if (_edgeOrientations == null)
		{
			reorderEdges();
			_region = clipToBounds(clippingBounds);
			if ((new Polygon(_region)).winding() == Winding.CLOCKWISE)
			{
				Collections.reverse(_region);
			}
		}
		return _region;
	}

	private void reorderEdges()
	{
		//trace("_edges:", _edges);
		final EdgeReorderer reorderer = new EdgeReorderer(_edges, Vertex.class);
		_edges = reorderer.get_edges();
		//trace("reordered:", _edges);
		_edgeOrientations = reorderer.get_edgeOrientations();
		reorderer.dispose();
	}

	private ArrayList<Point> clipToBounds(final Rectangle bounds)
	{
		final ArrayList<Point> points = new ArrayList();
		final int n = _edges.size();
		int i = 0;
		VoronoiEdge edge;
		while (i < n && (_edges.get(i).get_visible() == false))
		{
			++i;
		}

		if (i == n)
		{
			// no edges visible
			return new ArrayList();
		}
		edge = _edges.get(i);
		final LR orientation = _edgeOrientations.get(i);
		points.add(edge.get_clippedEnds().get(orientation));
		points.add(edge.get_clippedEnds().get((LR.other(orientation))));

		for (int j = i + 1; j < n; ++j)
		{
			edge = _edges.get(j);
			if (edge.get_visible() == false)
			{
				continue;
			}
			connect(points, j, bounds, false);
		}
		// close up the polygon by adding another corner point of the bounds if needed:
		connect(points, i, bounds, true);

		return points;
	}

	private void connect(final ArrayList<Point> points, final int j, final Rectangle bounds, final boolean closingUp)
	{
		final Point rightPoint = points.get(points.size() - 1);
		final VoronoiEdge newEdge = _edges.get(j);
		final LR newOrientation = _edgeOrientations.get(j);
		// the point that  must be connected to rightPoint:
		final Point newPoint = newEdge.get_clippedEnds().get(newOrientation);
		if (!closeEnough(rightPoint, newPoint))
		{
			// The points do not coincide, so they must have been clipped at the bounds;
			// see if they are on the same border of the bounds:
			if (rightPoint.x != newPoint.x
					&& rightPoint.y != newPoint.y)
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
		final Point newRightPoint = newEdge.get_clippedEnds().get(LR.other(newOrientation));
		if (!closeEnough(points.get(0), newRightPoint))
		{
			points.add(newRightPoint);
		}
	}

	public double get_x()
	{
		return _coord.x;
	}

	public double get_y()
	{
		return _coord.y;
	}

	public double dist(final ICoord p)
	{
		return Point.distance(p.get_coord(), this._coord);
	}
}

final class BoundsCheck
{

	final public static int TOP = 1;

	final public static int BOTTOM = 2;

	final public static int LEFT = 4;

	final public static int RIGHT = 8;

	public BoundsCheck()
	{
		throw new Error("BoundsCheck constructor unused");
	}

	/**
	 *
	 * @param point
	 * @param bounds
	 * @return an int with the appropriate bits set if the Point lies on the
	 * corresponding bounds lines
	 *
	 */
	public static int check(final Point point, final Rectangle bounds)
	{
		int value = 0;
		if (point.x == bounds.left)
		{
			value |= LEFT;
		}
		if (point.x == bounds.right)
		{
			value |= RIGHT;
		}
		if (point.y == bounds.top)
		{
			value |= TOP;
		}
		if (point.y == bounds.bottom)
		{
			value |= BOTTOM;
		}
		return value;
	}
}
