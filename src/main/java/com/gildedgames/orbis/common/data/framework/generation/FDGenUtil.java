package com.gildedgames.orbis.common.data.framework.generation;

import com.gildedgames.aether.api.orbis_core.util.RegionHelp;
import com.gildedgames.orbis.common.data.framework.Graph;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class FDGenUtil
{
	public static boolean hasCollision(Graph<FDGDNode, FDGDEdge> graph)
	{
		for (final FDGDNode node1 : graph.vertexSet())
		{
			for (final FDGDNode node2 : graph.vertexSet())
			{
				if (RegionHelp.intersects(node1, node2))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean hasEdgeIntersections(Graph<FDGDNode, FDGDEdge> graph)
	{
		final List<FDGDEdge> edges = new ArrayList<FDGDEdge>(graph.edgeSet());

		for (int i = 0; i < edges.size(); i++)
		{
			for (int x = i + 1; x < edges.size(); x++)
			{
				final FDGDEdge edge1 = edges.get(i);
				final FDGDEdge edge2 = edges.get(x);

				final FDGDNode e1S = edge1.node1();
				final FDGDNode e1T = edge1.node2();

				final FDGDNode e2S = edge2.node1();
				final FDGDNode e2T = edge2.node2();

				if (e1T != e2T && e1T != e2S && e1S != e2T && e1S != e2S)
				{
					if (isIntersecting(edge1, edge2))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isIntersecting(FDGDEdge edge1, FDGDEdge edge2)
	{
		//e1s, e1t, e2s, e2t
		final float line1X = edge1.entrance2X() - edge1.entrance1X();
		final float line1Z = edge1.entrance2Z() - edge1.entrance1Z();

		final float line2X = edge2.entrance2X() - edge2.entrance1X();
		final float line2Z = edge2.entrance2Z() - edge2.entrance1Z();

		final float denom = line1X * line2Z - line2X * line1Z;
		final float diffX = edge1.entrance1X() - edge2.entrance1X();
		final float diffZ = edge1.entrance1Z() - edge2.entrance1Z();

		final float s = (line1X * diffZ - line1Z * diffX) / denom;
		final float t = (line2X * diffZ - line2Z * diffX) / denom;

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
		{
			return true;
		}

		return false;
	}

	public static int euclidian(BlockPos from, BlockPos to)
	{
		return euclidian(from, to.getX(), to.getY(), to.getZ());
	}

	public static int euclidian(BlockPos from, int toX, int toY, int toZ)
	{
		return euclidian(from.getX(), from.getY(), from.getZ(), toX, toY, toZ);
	}

	public static int euclidian(int fromX, int fromY, int fromZ, int toX, int toY, int toZ)
	{
		return Math.abs(fromX - toX) + Math.abs(fromY - toY) + Math.abs(fromZ - toZ);
	}

	public static float euclidian(float fromX, float fromY, float fromZ, float toX, float toY, float toZ)
	{
		return Math.abs(fromX - toX) + Math.abs(fromY - toY) + Math.abs(fromZ - toZ);
	}
}
