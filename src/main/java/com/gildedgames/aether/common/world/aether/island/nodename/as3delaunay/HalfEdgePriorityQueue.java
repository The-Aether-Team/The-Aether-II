package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

public final class HalfEdgePriorityQueue // also known as heap
{

	private final int hashSize;

	private final double minY;

	private final double deltaY;

	private final HalfEdge[] hash;

	private int count;

	private int minBucket;

	public HalfEdgePriorityQueue(final double ymin, final double deltay, final int sqrtnsites)
	{
		this.minY = ymin;
		this.deltaY = deltay;
		this.hashSize = 4 * sqrtnsites;

		this.count = 0;
		this.minBucket = 0;
		this.hash = new HalfEdge[this.hashSize];

		// dummy HalfEdge at the top of each hash
		for (int i = 0; i < this.hashSize; ++i)
		{
			this.hash[i] = HalfEdge.createDummy();
		}
	}

	public void insert(final HalfEdge halfEdge)
	{
		final int insertionBucket = this.bucket(halfEdge);

		if (insertionBucket < this.minBucket)
		{
			this.minBucket = insertionBucket;
		}

		HalfEdge previous = this.hash[insertionBucket];
		HalfEdge next;

		while ((next = previous.nextInPriorityQueue) != null
				&& (halfEdge.ystar > next.ystar || (halfEdge.ystar == next.ystar && halfEdge.vertex.x > next.vertex.x)))
		{
			previous = next;
		}

		halfEdge.nextInPriorityQueue = previous.nextInPriorityQueue;
		previous.nextInPriorityQueue = halfEdge;

		++this.count;
	}

	public void remove(final HalfEdge halfEdge)
	{
		if (halfEdge.vertex != null)
		{
			HalfEdge previous = this.hash[this.bucket(halfEdge)];

			while (previous.nextInPriorityQueue != halfEdge)
			{
				previous = previous.nextInPriorityQueue;
			}

			previous.nextInPriorityQueue = halfEdge.nextInPriorityQueue;

			this.count--;

			halfEdge.vertex = null;
			halfEdge.nextInPriorityQueue = null;
		}
	}

	private int bucket(final HalfEdge halfEdge)
	{
		int theBucket = (int) ((halfEdge.ystar - this.minY) / this.deltaY * this.hashSize);

		if (theBucket < 0)
		{
			theBucket = 0;
		}

		if (theBucket >= this.hashSize)
		{
			theBucket = this.hashSize - 1;
		}

		return theBucket;
	}

	private boolean isEmpty(final int bucket)
	{
		return (this.hash[bucket].nextInPriorityQueue == null);
	}

	private void adjustMinBucket()
	{
		while (this.minBucket < this.hashSize - 1 && this.isEmpty(this.minBucket))
		{
			++this.minBucket;
		}
	}

	public boolean empty()
	{
		return this.count == 0;
	}

	public Point min()
	{
		this.adjustMinBucket();
		final HalfEdge answer = this.hash[this.minBucket].nextInPriorityQueue;
		return new Point(answer.vertex.x, answer.ystar);
	}

	public HalfEdge extractMin()
	{
		final HalfEdge a = this.hash[this.minBucket];

		// get the first real HalfEdge in minBucket
		final HalfEdge answer = a.nextInPriorityQueue;
		a.nextInPriorityQueue = answer.nextInPriorityQueue;
		answer.nextInPriorityQueue = null;

		this.count--;

		return answer;
	}
}