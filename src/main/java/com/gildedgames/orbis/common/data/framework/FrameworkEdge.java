package com.gildedgames.orbis.common.data.framework;

public class FrameworkEdge
{
	private final FrameworkNode node1, node2;

	public FrameworkEdge(FrameworkNode node1, FrameworkNode node2)
	{
		this.node1 = node1;
		this.node2 = node2;
	}

	public FrameworkNode node1()
	{
		return this.node1;
	}

	public FrameworkNode node2()
	{
		return this.node2;
	}

	public FrameworkNode getOpposite(FrameworkNode node)
	{
		return node == this.node1 ? this.node2 : node == this.node2 ? this.node1 : null;
	}
}
