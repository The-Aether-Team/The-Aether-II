package com.gildedgames.aether.common.world.util;

import java.util.List;

public class TriangulationCell
{
	public TriangulationCell[] adjacency;
	
	public List<int[]> vertices;
	
	public TriangulationCell(List<int[]> vertices, TriangulationCell[] adjacency)
	{
		this.adjacency = adjacency;
		this.vertices = vertices;
	}
}
