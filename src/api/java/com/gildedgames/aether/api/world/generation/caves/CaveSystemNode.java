package com.gildedgames.aether.api.world.generation.caves;

import java.util.ArrayList;
import java.util.List;

public class CaveSystemNode
{
	public final List<CaveSystemNode> branches = new ArrayList<>();

	public final List<CaveSystemTunnel> entries = new ArrayList<>();

	public CaveSystemNode addBranch()
	{
		CaveSystemNode node = new CaveSystemNode();

		this.branches.add(node);

		return node;
	}
}
