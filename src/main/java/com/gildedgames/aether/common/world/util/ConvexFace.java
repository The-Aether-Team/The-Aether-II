package com.gildedgames.aether.common.world.util;

import java.util.ArrayList;
import java.util.List;

public class ConvexFace
{
	public ConvexFace(VertexBuffer beyondList)
	{
		this.adjacentFaces = new ConvexFace[3];
		this.verticesBeyond = beyondList;
		this.normal = new double[3];
		this.vertices = new ArrayList<>(3);
	}

	public ConvexFace[] adjacentFaces;

	public VertexBuffer verticesBeyond;

	public int[] furthestVertex;

	public List<int[]> vertices;

	public double[] normal;

	public boolean isNormalFlipped;

	public double offset;

	public int tag;

	public ConvexFace previous;

	public ConvexFace next;

	public boolean inList;
}
