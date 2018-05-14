package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

import java.util.ArrayList;

public final class EdgeList implements IDisposable
{

	private final double _deltax;

	private final double _xmin;

	private final int _hashsize;

	public Halfedge leftEnd;

	public Halfedge rightEnd;

	private ArrayList<Halfedge> _hash;

	public EdgeList(final double xmin, final double deltax, final int sqrt_nsites)
	{
		this._xmin = xmin;
		this._deltax = deltax;
		this._hashsize = 2 * sqrt_nsites;

		this._hash = new ArrayList(this._hashsize);

		// two dummy Halfedges:
		this.leftEnd = Halfedge.createDummy();
		this.rightEnd = Halfedge.createDummy();
		this.leftEnd.edgeListLeftNeighbor = null;
		this.leftEnd.edgeListRightNeighbor = this.rightEnd;
		this.rightEnd.edgeListLeftNeighbor = this.leftEnd;
		this.rightEnd.edgeListRightNeighbor = null;

		for (int i = 0; i < this._hashsize; i++)
		{
			this._hash.add(null);
		}

		this._hash.set(0, this.leftEnd);
		this._hash.set(this._hashsize - 1, this.rightEnd);
	}

	@Override
	public void dispose()
	{
		Halfedge halfEdge = this.leftEnd;
		Halfedge prevHe;
		while (halfEdge != this.rightEnd)
		{
			prevHe = halfEdge;
			halfEdge = halfEdge.edgeListRightNeighbor;
			prevHe.dispose();
		}
		this.leftEnd = null;
		this.rightEnd.dispose();
		this.rightEnd = null;

		this._hash.clear();
		this._hash = null;
	}

	/**
	 * Insert newHalfedge to the right of lb
	 *
	 * @param lb
	 * @param newHalfedge
	 *
	 */
	public void insert(final Halfedge lb, final Halfedge newHalfedge)
	{
		newHalfedge.edgeListLeftNeighbor = lb;
		newHalfedge.edgeListRightNeighbor = lb.edgeListRightNeighbor;
		lb.edgeListRightNeighbor.edgeListLeftNeighbor = newHalfedge;
		lb.edgeListRightNeighbor = newHalfedge;
	}

	/**
	 * This function only removes the Halfedge from the left-right list. We
	 * cannot dispose it yet because we are still using it.
	 *
	 * @param halfEdge
	 *
	 */
	public void remove(final Halfedge halfEdge)
	{
		halfEdge.edgeListLeftNeighbor.edgeListRightNeighbor = halfEdge.edgeListRightNeighbor;
		halfEdge.edgeListRightNeighbor.edgeListLeftNeighbor = halfEdge.edgeListLeftNeighbor;
		halfEdge.edge = VoronoiEdge.DELETED;
		halfEdge.edgeListLeftNeighbor = halfEdge.edgeListRightNeighbor = null;
	}

	/**
	 * Find the rightmost Halfedge that is still left of p
	 *
	 * @param p
	 * @return
	 *
	 */
	public Halfedge edgeListLeftNeighbor(final Point p)
	{
		int i, bucket;
		Halfedge halfEdge;

		/* Use hash table to get close to desired halfedge */
		bucket = (int) ((p.x - this._xmin) / this._deltax * this._hashsize);
		if (bucket < 0)
		{
			bucket = 0;
		}
		if (bucket >= this._hashsize)
		{
			bucket = this._hashsize - 1;
		}
		halfEdge = this.getHash(bucket);
		if (halfEdge == null)
		{
			for (i = 1; true; ++i)
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
		if (bucket > 0 && bucket < this._hashsize - 1)
		{
			this._hash.set(bucket, halfEdge);
		}
		return halfEdge;
	}

	/* Get entry from hash table, pruning any deleted nodes */
	private Halfedge getHash(final int b)
	{
		final Halfedge halfEdge;

		if (b < 0 || b >= this._hashsize)
		{
			return null;
		}
		halfEdge = this._hash.get(b);
		if (halfEdge != null && halfEdge.edge == VoronoiEdge.DELETED)
		{
			/* Hash table points to deleted halfedge.  Patch as necessary. */
			this._hash.set(b, null);
			// still can't dispose halfEdge yet!
			return null;
		}
		else
		{
			return halfEdge;
		}
	}
}