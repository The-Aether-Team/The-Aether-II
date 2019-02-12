package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

public final class EdgeList
{

	private final double deltaX;

	private final double minX;

	private final int hashSize;

	private final HalfEdge leftEnd;

	private final HalfEdge rightEnd;

	private final HalfEdge[] hash;

	public EdgeList(final double xmin, final double deltax, final int sqrtnsites)
	{
		this.minX = xmin;
		this.deltaX = deltax;
		this.hashSize = 2 * sqrtnsites;

		this.hash = new HalfEdge[this.hashSize];

		// two dummy Halfedges:
		this.leftEnd = HalfEdge.createDummy();
		this.rightEnd = HalfEdge.createDummy();
		this.leftEnd.edgeListLeftNeighbor = null;
		this.leftEnd.edgeListRightNeighbor = this.rightEnd;
		this.rightEnd.edgeListLeftNeighbor = this.leftEnd;
		this.rightEnd.edgeListRightNeighbor = null;

		this.hash[0] = this.leftEnd;
		this.hash[this.hashSize - 1] = this.rightEnd;
	}

	public void insert(final HalfEdge lb, final HalfEdge newHalfEdge)
	{
		newHalfEdge.edgeListLeftNeighbor = lb;
		newHalfEdge.edgeListRightNeighbor = lb.edgeListRightNeighbor;
		lb.edgeListRightNeighbor.edgeListLeftNeighbor = newHalfEdge;
		lb.edgeListRightNeighbor = newHalfEdge;
	}

	public void remove(final HalfEdge halfEdge)
	{
		halfEdge.edgeListLeftNeighbor.edgeListRightNeighbor = halfEdge.edgeListRightNeighbor;
		halfEdge.edgeListRightNeighbor.edgeListLeftNeighbor = halfEdge.edgeListLeftNeighbor;
		halfEdge.edge = VoronoiEdge.DELETED;
		halfEdge.edgeListLeftNeighbor = halfEdge.edgeListRightNeighbor = null;
	}

	public HalfEdge edgeListLeftNeighbor(final Point p)
	{

		/* Use hash table to get close to desired halfedge */
		int bucket = (int) ((p.x - this.minX) / this.deltaX * this.hashSize);
		if (bucket < 0)
		{
			bucket = 0;
		}
		if (bucket >= this.hashSize)
		{
			bucket = this.hashSize - 1;
		}
		HalfEdge halfEdge = this.getHash(bucket);
		if (halfEdge == null)
		{
			for (int i = 1; true; ++i)
			{
				if ((halfEdge = this.getHash(bucket - i)) != null)
				{
					break;
				}
				if ((halfEdge = this.getHash(bucket + i)) != null)
				{
					break;
				}
			}
		}
		/* Now search linear list of halfedges for the correct one */
		if (halfEdge == this.leftEnd || (halfEdge != this.rightEnd && halfEdge.isLeftOf(p)))
		{
			do
			{
				halfEdge = halfEdge.edgeListRightNeighbor;
			}
			while (halfEdge != this.rightEnd && halfEdge.isLeftOf(p));
			halfEdge = halfEdge.edgeListLeftNeighbor;
		}
		else
		{
			do
			{
				halfEdge = halfEdge.edgeListLeftNeighbor;
			}
			while (halfEdge != this.leftEnd && !halfEdge.isLeftOf(p));
		}

		/* Update hash table and reference counts */
		if (bucket > 0 && bucket < this.hashSize - 1)
		{
			this.hash[bucket] = halfEdge;
		}
		return halfEdge;
	}

	/* Get entry from hash table, pruning any deleted nodes */
	private HalfEdge getHash(final int b)
	{
		final HalfEdge halfEdge;

		if (b < 0 || b >= this.hashSize)
		{
			return null;
		}
		halfEdge = this.hash[b];
		if (halfEdge != null && halfEdge.edge == VoronoiEdge.DELETED)
		{
			/* Hash table points to deleted halfedge.  Patch as necessary. */
			this.hash[b] = null;
			// still can't dispose halfEdge yet!
			return null;
		}
		else
		{
			return halfEdge;
		}
	}
}