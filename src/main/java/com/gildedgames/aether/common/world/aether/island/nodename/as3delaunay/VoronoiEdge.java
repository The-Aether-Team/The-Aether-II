package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

import java.util.HashMap;
import java.util.Stack;

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

	final public static VoronoiEdge DELETED = new VoronoiEdge();

	final private static Stack<VoronoiEdge> _pool = new Stack<>();

	private static int _nedges = 0;

	private final int _edgeIndex;

	// the equation of the edge: ax + by = c
	public double a, b, c;

	// the two Voronoi vertices that the edge connects
	//		(if one of them is null, the edge extends to infinity)
	private Vertex _leftVertex;

	private Vertex _rightVertex;

	// Once clipVertices() is called, this Dictionary will hold two Points
	// representing the clipped coordinates of the left and right ends...
	private HashMap<LR, Point> _clippedVertices;

	// the two input Sites for which this VoronoiEdge is a bisector:
	private HashMap<LR, Site> _sites;

	private VoronoiEdge()
	{
		this._edgeIndex = _nedges++;
		this.init();
	}

	/**
	 * This is the only way to create a new VoronoiEdge
	 *
	 * @param site0
	 * @param site1
	 * @return
	 *
	 */
	public static VoronoiEdge createBisectingEdge(final Site site0, final Site site1)
	{
		final double dx;
		final double dy;
		final double absdx;
		final double absdy;
		final double a;
		final double b;
		double c;

		dx = site1.get_x() - site0.get_x();
		dy = site1.get_y() - site0.get_y();
		absdx = dx > 0 ? dx : -dx;
		absdy = dy > 0 ? dy : -dy;
		c = site0.get_x() * dx + site0.get_y() * dy + (dx * dx + dy * dy) * 0.5;
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

		final VoronoiEdge edge = VoronoiEdge.create();

		edge.set_leftSite(site0);
		edge.set_rightSite(site1);
		site0.addEdge(edge);
		site1.addEdge(edge);

		edge._leftVertex = null;
		edge._rightVertex = null;

		edge.a = a;
		edge.b = b;
		edge.c = c;
		//trace("createBisectingEdge: a ", edge.a, "b", edge.b, "c", edge.c);

		return edge;
	}

	private static VoronoiEdge create()
	{
		final VoronoiEdge edge;
		if (_pool.size() > 0)
		{
			edge = _pool.pop();
			edge.init();
		}
		else
		{
			edge = new VoronoiEdge();
		}
		return edge;
	}

	public static double compareSitesDistances_MAX(final VoronoiEdge edge0, final VoronoiEdge edge1)
	{
		final double length0 = edge0.sitesDistance();
		final double length1 = edge1.sitesDistance();
		return Double.compare(length1, length0);
	}

	public static double compareSitesDistances(final VoronoiEdge edge0, final VoronoiEdge edge1)
	{
		return -compareSitesDistances_MAX(edge0, edge1);
	}

	/*final private static LINESPRITE:Sprite = new Sprite();
	 final private static GRAPHICS:Graphics = LINESPRITE.graphics;

	 private var _delaunayLineBmp:BitmapData;
	 internal function get delaunayLineBmp():BitmapData
	 {
	 if (!_delaunayLineBmp)
	 {
	 _delaunayLineBmp = makeDelaunayLineBmp();
	 }
	 return _delaunayLineBmp;
	 }

	 // making this available to Voronoi; running out of memory in AIR so I cannot cache the bmp
	 internal function makeDelaunayLineBmp():BitmapData
	 {
	 var p0:Point = leftSite.coord;
	 var p1:Point = rightSite.coord;

	 GRAPHICS.clear();
	 // clear() resets line style back to undefined!
	 GRAPHICS.lineStyle(0, 0, 1.0, false, LineScaleMode.NONE, CapsStyle.NONE);
	 GRAPHICS.moveTo(p0.x, p0.y);
	 GRAPHICS.lineTo(p1.x, p1.y);

	 var w:int = int(Math.ceil(Math.max(p0.x, p1.x)));
	 if (w < 1)
	 {
	 w = 1;
	 }
	 var h:int = int(Math.ceil(Math.max(p0.y, p1.y)));
	 if (h < 1)
	 {
	 h = 1;
	 }
	 var bmp:BitmapData = new BitmapData(w, h, true, 0);
	 bmp.draw(LINESPRITE);
	 return bmp;
	 }*/
	public LineSegment delaunayLine()
	{
		// draw a line connecting the input Sites for which the edge is a bisector:
		return new LineSegment(this.get_leftSite().get_coord(), this.get_rightSite().get_coord());
	}

	public LineSegment voronoiEdge()
	{
		if (!this.get_visible())
		{
			return new LineSegment(null, null);
		}
		return new LineSegment(this._clippedVertices.get(LR.LEFT),
				this._clippedVertices.get(LR.RIGHT));
	}

	public Vertex get_leftVertex()
	{
		return this._leftVertex;
	}

	public Vertex get_rightVertex()
	{
		return this._rightVertex;
	}

	public Vertex vertex(final LR leftRight)
	{
		return (leftRight == LR.LEFT) ? this._leftVertex : this._rightVertex;
	}

	public void setVertex(final LR leftRight, final Vertex v)
	{
		if (leftRight == LR.LEFT)
		{
			this._leftVertex = v;
		}
		else
		{
			this._rightVertex = v;
		}
	}
	// unless the entire VoronoiEdge is outside the bounds.
	// In that case visible will be false:

	public boolean isPartOfConvexHull()
	{
		return (this._leftVertex == null || this._rightVertex == null);
	}

	public double sitesDistance()
	{
		return Point.distance(this.get_leftSite().get_coord(), this.get_rightSite().get_coord());
	}

	public HashMap<LR, Point> get_clippedEnds()
	{
		return this._clippedVertices;
	}

	public boolean get_visible()
	{
		return this._clippedVertices != null;
	}

	public Site get_leftSite()
	{
		return this._sites.get(LR.LEFT);
	}

	public void set_leftSite(final Site s)
	{
		this._sites.put(LR.LEFT, s);
	}

	public Site get_rightSite()
	{
		return this._sites.get(LR.RIGHT);
	}

	public void set_rightSite(final Site s)
	{
		this._sites.put(LR.RIGHT, s);
	}

	public Site site(final LR leftRight)
	{
		return this._sites.get(leftRight);
	}

	public void dispose()
	{
		/*if (_delaunayLineBmp)
		 {
         _delaunayLineBmp.dispose();
         _delaunayLineBmp = null;
         }*/
		this._leftVertex = null;
		this._rightVertex = null;
		if (this._clippedVertices != null)
		{
			this._clippedVertices.clear();
			this._clippedVertices = null;
		}
		this._sites.clear();
		this._sites = null;

		_pool.push(this);
	}

	private void init()
	{
		this._sites = new HashMap<>();
	}

	@Override
	public String toString()
	{
		return "VoronoiEdge " + this._edgeIndex + "; sites " + this._sites.get(LR.LEFT) + ", " + this._sites.get(LR.RIGHT)
				+ "; endVertices " + (this._leftVertex != null ? this._leftVertex.get_vertexIndex() : "null") + ", "
				+ (this._rightVertex != null ? this._rightVertex.get_vertexIndex() : "null") + "::";
	}

	/**
	 * Set _clippedVertices to contain the two ends of the portion of the
	 * Voronoi edge that is visible within the bounds. If no part of the VoronoiEdge
	 * falls within the bounds, leave _clippedVertices null.
	 *
	 * @param bounds
	 *
	 */
	public void clipVertices(final Rectangle bounds)
	{
		final double xmin = bounds.x;
		final double ymin = bounds.y;
		final double xmax = bounds.right;
		final double ymax = bounds.bottom;

		final Vertex vertex0;
		final Vertex vertex1;
		double x0, x1, y0, y1;

		if (this.a == 1.0 && this.b >= 0.0)
		{
			vertex0 = this._rightVertex;
			vertex1 = this._leftVertex;
		}
		else
		{
			vertex0 = this._leftVertex;
			vertex1 = this._rightVertex;
		}

		if (this.a == 1.0)
		{
			y0 = ymin;
			if (vertex0 != null && vertex0.get_y() > ymin)
			{
				y0 = vertex0.get_y();
			}
			if (y0 > ymax)
			{
				return;
			}
			x0 = this.c - this.b * y0;

			y1 = ymax;
			if (vertex1 != null && vertex1.get_y() < ymax)
			{
				y1 = vertex1.get_y();
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
			if (vertex0 != null && vertex0.get_x() > xmin)
			{
				x0 = vertex0.get_x();
			}
			if (x0 > xmax)
			{
				return;
			}
			y0 = this.c - this.a * x0;

			x1 = xmax;
			if (vertex1 != null && vertex1.get_x() < xmax)
			{
				x1 = vertex1.get_x();
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

		this._clippedVertices = new HashMap<>();
		if (vertex0 == this._leftVertex)
		{
			this._clippedVertices.put(LR.LEFT, new Point(x0, y0));
			this._clippedVertices.put(LR.RIGHT, new Point(x1, y1));
		}
		else
		{
			this._clippedVertices.put(LR.RIGHT, new Point(x0, y0));
			this._clippedVertices.put(LR.LEFT, new Point(x1, y1));
		}
	}
}