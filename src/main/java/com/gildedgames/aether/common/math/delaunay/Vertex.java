package com.gildedgames.aether.common.math.delaunay;

final class Vertex extends Point
{
	private Vertex(final double x, final double y)
	{
		super(x, y);
	}

	public static Vertex intersect(final HalfEdge halfEdge0, final HalfEdge halfEdge1)
	{
		final VoronoiEdge edge0 = halfEdge0.edge;
		final VoronoiEdge edge1 = halfEdge1.edge;

		if (edge0 == null || edge1 == null)
		{
			return null;
		}

		if (edge0.getRightSite() == edge1.getRightSite())
		{
			return null;
		}

		final double determinant = edge0.a * edge1.b - edge0.b * edge1.a;

		if (-1.0e-10 < determinant && determinant < 1.0e-10)
		{
			// the edges are parallel
			return null;
		}

		final VoronoiEdge edge;
		final HalfEdge halfedge;

		if (Voronoi.compareByYThenX(edge0.getRightSite(), edge1.getRightSite()) < 0)
		{
			halfedge = halfEdge0;
			edge = edge0;
		}
		else
		{
			halfedge = halfEdge1;
			edge = edge1;
		}

		final double intersectionX = (edge0.c * edge1.b - edge1.c * edge0.b) / determinant;
		final double intersectionY = (edge1.c * edge0.a - edge0.c * edge1.a) / determinant;

		final boolean rightOfSite = intersectionX >= edge.getRightSite().x;

		if ((rightOfSite && halfedge.leftRight == LeftRight.LEFT) || (!rightOfSite && halfedge.leftRight == LeftRight.RIGHT))
		{
			return null;
		}

		return new Vertex(intersectionX, intersectionY);

	}
}
