package com.gildedgames.aether.common.math.delaunay;

import java.util.ArrayList;

public final class EdgeReorderer
{

	private ArrayList<VoronoiEdge> edges;

	private ArrayList<LeftRight> edgeLeftRights;

	public EdgeReorderer(final ArrayList<VoronoiEdge> origEdges)
	{
		if (origEdges.size() > 0)
		{
			this.reorderEdges(origEdges);
		}
		else
		{
			this.edges = new ArrayList<>();
			this.edgeLeftRights = new ArrayList<>();
		}
	}

	public ArrayList<VoronoiEdge> getEdges()
	{
		return this.edges;
	}

	public ArrayList<LeftRight> getEdgeLeftRights()
	{
		return this.edgeLeftRights;
	}

	private void reorderEdges(final ArrayList<VoronoiEdge> origEdges)
	{
		final int n = origEdges.size();

		// we're going to reorder the edges in order of traversal
		final boolean[] done = new boolean[n];

		final ArrayList<VoronoiEdge> newEdges = new ArrayList<>(n);
		final ArrayList<LeftRight> newLeftRights = new ArrayList<>(n);

		int i = 0;

		VoronoiEdge edge = origEdges.get(i);

		newEdges.add(edge);
		newLeftRights.add(LeftRight.LEFT);

		Vertex firstPoint = edge.getLeftVertex();
		Vertex lastPoint = edge.getRightVertex();

		done[i] = true;

		int nDone = 1;

		while (nDone < n)
		{
			for (i = 1; i < n; ++i)
			{
				if (done[i])
				{
					continue;
				}

				edge = origEdges.get(i);

				final Vertex leftPoint = edge.getLeftVertex();
				final Vertex rightPoint = edge.getRightVertex();

				boolean created = false;

				if (leftPoint == lastPoint)
				{
					lastPoint = rightPoint;

					newLeftRights.add(LeftRight.LEFT);
					newEdges.add(edge);

					created = true;
				}
				else if (rightPoint == firstPoint)
				{
					firstPoint = leftPoint;

					newLeftRights.add(0, LeftRight.LEFT);
					newEdges.add(0, edge);

					created = true;
				}
				else if (leftPoint == firstPoint)
				{
					firstPoint = rightPoint;

					newLeftRights.add(0, LeftRight.RIGHT);
					newEdges.add(0, edge);

					created = true;
				}
				else if (rightPoint == lastPoint)
				{
					lastPoint = leftPoint;

					newLeftRights.add(LeftRight.RIGHT);
					newEdges.add(edge);

					created = true;
				}

				if (created)
				{
					done[i] = true;

					++nDone;
				}
			}
		}

		this.edgeLeftRights = newLeftRights;
		this.edges = newEdges;
	}
}