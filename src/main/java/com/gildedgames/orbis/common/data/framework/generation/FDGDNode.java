package com.gildedgames.orbis.common.data.framework.generation;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.orbis.common.data.pathway.Entrance;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.Map.Entry;

public class FDGDNode extends Blueprint
{
	//This is the center
	private float posX, posY, posZ;

	private float prevX, prevY, prevZ;

	private float forceX, forceY, forceZ;

	public FDGDNode(BlueprintData data, BlockPos pos, World world)
	{
		super(world, pos, data);
		this.data = data;
		this.posX = pos.getX();
		this.posY = pos.getY();
		this.posZ = pos.getZ();
		this.computeMinMax();
	}

	public FDGDNode(BlueprintData data, Rotation rotation, World world)
	{
		super(world, BlockPos.ORIGIN, rotation, data);
		this.data = data;
		this.rotation = rotation;
	}

	private void computeMinMax()
	{
		final IRegion region = RotationHelp.regionFromCenter((int) this.posX, (int) this.posY, (int) this.posZ, this.data, this.rotation);
		this.min = region.getMin();
		this.max = region.getMax();
	}

	public float getX()
	{
		return this.posX;
	}

	public float getY()
	{
		return this.posY;
	}

	public float getZ()
	{
		return this.posZ;
	}

	public float getPrevX()
	{
		return this.prevX;
	}

	public float getPrevY()
	{
		return this.prevY;
	}

	public float getPrevZ()
	{
		return this.prevZ;
	}

	public float getForceX()
	{
		return this.forceX;
	}

	public float getForceY()
	{
		return this.forceY;
	}

	public float getForceZ()
	{
		return this.forceZ;
	}

	public void setPosition(float x, float y, float z)
	{
		this.prevX = this.posX;
		this.prevY = this.posY;
		this.prevZ = this.posZ;

		this.posX = x;
		this.posY = y;
		this.posZ = z;

		this.computeMinMax();
	}

	public void setForce(float x, float y, float z)
	{
		this.forceX = x;
		this.forceY = y;
		this.forceZ = z;
	}

	public void applyForce()
	{
		this.setPosition(this.posX + this.forceX, this.posY + this.forceY, this.posZ + this.forceZ);
	}

	/**
	 * Try all possible ways to connect this node to its parents with the edges
	 * out of it. Also tries all different rotations to minimise the length between. 
	 * 
	 * Be careful: This can change the region this node takes because it can choose
	 * a different rotation! Don't use this at the end of the Framework algorithm.
	 * 
	 * This method assumes that there is a solution. First solve the Framework
	 * CSP to guarantee this (see {@link })
	 * @param edges
	 */
	public void assignConnections(Collection<FDGDEdge> edges)
	{
		int best = Integer.MAX_VALUE;
		Map<FDGDEdge, Entrance> bestResult = null;
		Rotation bestRotation = Rotation.NONE;
		final List<FDGDEdge> edgesL = new ArrayList<FDGDEdge>(edges);
		for (final Rotation rotation : Rotation.values())
		{
			final List<Entrance> entrances = this.getEntrances(rotation);
			if (entrances.size() < edges.size())
			{
				throw new IllegalStateException();
			}
			final Tuple<Map<FDGDEdge, Entrance>, Integer> result = this.bestEntrances(edgesL, entrances, 0, 0, best);
			if (result != null)
			{
				bestResult = result.getFirst();
				best = result.getSecond();
				bestRotation = rotation;
			}
		}
		for (final Entry<FDGDEdge, Entrance> edge : bestResult.entrySet())
		{
			edge.getKey().setConnection(this, edge.getValue());
		}
		this.rotation = bestRotation;
		this.computeMinMax();
	}

	/**
	 * Does the same thing as the method up here, but does not try
	 * different rotations so the node does not change in dimensions.
	 * @param edges
	 */
	public void assignConnectionsFixRot(Collection<FDGDEdge> edges)
	{
		final List<Entrance> entrances = this.getEntrances(this.rotation);
		final List<FDGDEdge> edgesL = new ArrayList<FDGDEdge>(edges);
		if (entrances.size() < edges.size())
		{
			throw new IllegalStateException();
		}
		final Tuple<Map<FDGDEdge, Entrance>, Integer> result = this.bestEntrances(edgesL, entrances, 0, 0, Integer.MAX_VALUE);
		for (final Entry<FDGDEdge, Entrance> edge : result.getFirst().entrySet())
		{
			edge.getKey().setConnection(this, edge.getValue());
		}
	}

	/**
	 * Branch and bound exhaustive search of all the possible ways to connect
	 * this blueprint to its neighbors. The shortest route is the best.
	 * Returns null if no better config was found.
	 * If a better config was found it returns a tuple with the connections chosen
	 * for each edge, and an integer representing the total distance between 
	 * the connections and the nodes they're connected to.
	 */
	private Tuple<Map<FDGDEdge, Entrance>, Integer> bestEntrances(List<FDGDEdge> edges, List<Entrance> entrancesLeft, int edgeIndex, int cost, int best)
	{
		if (edgeIndex >= edges.size())
		{
			return new Tuple<>(new HashMap<>(edges.size()), cost);
		}
		final FDGDEdge edge = edges.get(edgeIndex);
		Tuple<Map<FDGDEdge, Entrance>, Integer> bestInDepth = null;
		final FDGDNode opposite = edge.getOpposite(this);
		final Map<Entrance, Integer> costMap = new HashMap<>(entrancesLeft.size());
		for (final Entrance entrance : entrancesLeft)
		{
			costMap.put(entrance, cost + FDGenUtil.euclidian(entrance.getPos(), (int) opposite.getX(), (int) opposite.getY(), (int) opposite.getZ()));
		}

		//TODO: See if the heuristic has noticeable performance improvements
		entrancesLeft.sort(Comparator.comparing(costMap::get));
		for (final Entrance entrance : entrancesLeft)
		{
			if (!edge.pathway().equals(entrance.toConnectTo()))
			{
				continue;
			}
			final int newCost = costMap.get(entrance);
			//Prune branch if we already found a solution with a lower total cost
			if (newCost >= best)
			{
				continue;
			}
			final List<Entrance> copy = new ArrayList<>(entrancesLeft);
			copy.remove(entrance);

			//Go into recursion
			final Tuple<Map<FDGDEdge, Entrance>, Integer> result = this.bestEntrances(edges, copy, edgeIndex + 1, newCost, best);
			if (result != null)
			{
				bestInDepth = result;
				best = result.getSecond();
				result.getFirst().put(edge, entrance);
			}
		}
		return bestInDepth;
	}

	private List<Entrance> getEntrances(Rotation rotation)
	{
		final List<Entrance> newList = new ArrayList<Entrance>();
		final BlockPos position = this.centerAsBP();
//		for (final Entrance beforeTrans : this.data.entrances())
//		{
//			final BlockPos finalBP = beforeTrans.getPos().add((int) this.posX - this.data.getWidth() / 2, this.posY, (int) this.posZ - this.data.getLength() / 2);
//			final BlockPos trans = RotationHelp.rotate(finalBP, position, rotation, this.getWidth(), this.getLength());
//			newList.add(new Entrance(trans, beforeTrans.toConnectTo()));
//		}
		return newList;
	}

	public BlockPos centerAsBP()
	{
		return new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ);
	}

}
