package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

import java.util.Stack;

final public class Vertex implements ICoord
{

	final public static Vertex VERTEX_AT_INFINITY = new Vertex(Double.NaN, Double.NaN);

	final private static Stack<Vertex> _pool = new Stack<>();

	private static int _nvertices = 0;

	private Point _coord;

	private int _vertexIndex;

	public Vertex(final double x, final double y)
	{
		this.init(x, y);
	}

	private static Vertex create(final double x, final double y)
	{

		if (Double.isNaN(x) || Double.isNaN(y))
		{
			return VERTEX_AT_INFINITY;
		}
		if (_pool.size() > 0)
		{

			return _pool.pop().init(x, y);
		}
		else
		{
			return new Vertex(x, y);
		}
	}

	/**
	 * This is the only way to make a Vertex
	 *
	 * @param halfedge0
	 * @param halfedge1
	 * @return
	 *
	 */
	public static Vertex intersect(final Halfedge halfedge0, final Halfedge halfedge1)
	{
		final VoronoiEdge edge0;
		final VoronoiEdge edge1;
		final VoronoiEdge edge;
		final Halfedge halfedge;
		final double determinant;
		final double intersectionX;
		final double intersectionY;
		final boolean rightOfSite;

		edge0 = halfedge0.edge;
		edge1 = halfedge1.edge;
		if (edge0 == null || edge1 == null)
		{
			return null;
		}
		if (edge0.get_rightSite() == edge1.get_rightSite())
		{
			return null;
		}

		determinant = edge0.a * edge1.b - edge0.b * edge1.a;
		if (-1.0e-10 < determinant && determinant < 1.0e-10)
		{
			// the edges are parallel
			return null;
		}

		intersectionX = (edge0.c * edge1.b - edge1.c * edge0.b) / determinant;
		intersectionY = (edge1.c * edge0.a - edge0.c * edge1.a) / determinant;

		if (Voronoi.compareByYThenX(edge0.get_rightSite(), edge1.get_rightSite()) < 0)
		{
			halfedge = halfedge0;
			edge = edge0;
		}
		else
		{
			halfedge = halfedge1;
			edge = edge1;
		}
		rightOfSite = intersectionX >= edge.get_rightSite().get_x();
		if ((rightOfSite && halfedge.leftRight == LR.LEFT)
				|| (!rightOfSite && halfedge.leftRight == LR.RIGHT))
		{
			return null;
		}

		return Vertex.create(intersectionX, intersectionY);
	}

	@Override
	public Point get_coord()
	{
		return this._coord;
	}

	public int get_vertexIndex()
	{
		return this._vertexIndex;
	}

	private Vertex init(final double x, final double y)
	{
		this._coord = new Point(x, y);
		return this;
	}

	public void dispose()
	{
		this._coord = null;
		_pool.push(this);
	}

	public void setIndex()
	{
		this._vertexIndex = _nvertices++;
	}

	@Override
	public String toString()
	{
		return "Vertex (" + this._vertexIndex + ")";
	}

	public double get_x()
	{
		return this._coord.x;
	}

	public double get_y()
	{
		return this._coord.y;
	}
}
