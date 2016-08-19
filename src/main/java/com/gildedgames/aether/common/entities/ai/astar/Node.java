package com.gildedgames.aether.common.entities.ai.astar;

import net.minecraft.util.math.BlockPos;

public class Node
{

	private Node parent;

	private BlockPos pos;

	private int movementCost, distanceToTarget;

	public Node(BlockPos pos)
	{
		this.pos = pos;
	}

	public void setParentNode(Node node)
	{
		this.parent = node;
	}

	public Node getParentNode()
	{
		return this.parent;
	}

	public BlockPos getPos()
	{
		return this.pos;
	}

	public int getMovementCost()
	{
		return this.movementCost;
	}

	public void setMovementCost(int movementCost)
	{
		this.movementCost = movementCost;
	}

	public int getDistanceToTarget()
	{
		return this.distanceToTarget;
	}

	public void setDistanceToTarget(int distanceToTarget)
	{
		this.distanceToTarget = distanceToTarget;
	}

}
