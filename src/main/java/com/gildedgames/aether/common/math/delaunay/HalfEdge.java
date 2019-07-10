package com.gildedgames.aether.common.math.delaunay;

public final class HalfEdge
{

	public HalfEdge edgeListLeftNeighbor, edgeListRightNeighbor;

	public HalfEdge nextInPriorityQueue;

	public VoronoiEdge edge;

	public final LeftRight leftRight;

	public Vertex vertex;

	// the vertex's y-coordinate in the transformed Voronoi space V*
	public double ystar;

	public HalfEdge(final VoronoiEdge edge, final LeftRight lr)
	{
		this.edge = edge;
		this.leftRight = lr;
		this.nextInPriorityQueue = null;
		this.vertex = null;
	}

	public static HalfEdge createDummy()
	{
		return new HalfEdge(null, null);
	}

	@Override
	public String toString()
	{
		return "HalfEdge (leftRight: " + this.leftRight + "; vertex: " + this.vertex + ")";
	}

	public boolean isLeftOf(final Point p)
	{

		final Site topSite = this.edge.getRightSite();
		final boolean rightOfSite = p.x > topSite.x;
		if (rightOfSite && this.leftRight == LeftRight.LEFT)
		{
			return true;
		}
		if (!rightOfSite && this.leftRight == LeftRight.RIGHT)
		{
			return false;
		}

		boolean above;
		if (this.edge.a == 1.0)
		{
			final double dyp = p.y - topSite.y;
			final double dxp = p.x - topSite.x;
			boolean fast = false;
			if ((!rightOfSite && this.edge.b < 0.0) || (rightOfSite && this.edge.b >= 0.0))
			{
				above = dyp >= this.edge.b * dxp;
				fast = above;
			}
			else
			{
				above = p.x + p.y * this.edge.b > this.edge.c;
				if (this.edge.b < 0.0)
				{
					above = !above;
				}
				if (!above)
				{
					fast = true;
				}
			}
			if (!fast)
			{
				final double dxs = topSite.x - this.edge.getLeftSite().x;
				above = this.edge.b * (dxp * dxp - dyp * dyp)
						< dxs * dyp * (1.0 + 2.0 * dxp / dxs + this.edge.b * this.edge.b);
				if (this.edge.b < 0.0)
				{
					above = !above;
				}
			}
		}
		else /* edge.b == 1.0 */
		{
			final double yl = this.edge.c - this.edge.a * p.x;
			final double t1 = p.y - yl;
			final double t2 = p.x - topSite.x;
			final double t3 = yl - topSite.y;
			above = t1 * t1 > t2 * t2 + t3 * t3;
		}
		return (this.leftRight == LeftRight.LEFT) == above;
	}
}
