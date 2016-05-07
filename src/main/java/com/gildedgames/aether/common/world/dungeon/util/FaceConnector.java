package com.gildedgames.aether.common.world.dungeon.util;

import java.util.List;

class FaceConnector
{
	public ConvexFace face;

	public int edgeIndex;

	public int[] vertices;

	public int hashCode;

	public FaceConnector previous;

	public FaceConnector next;

	public FaceConnector()
	{
		this.vertices = new int[2];
	}

	public void update(ConvexFace face, int edgeIndex, List<int[]> fullInput)
	{
		this.face = face;
		this.edgeIndex = edgeIndex;

		int hashCode = 31;

		List<int[]> vs = face.vertices;
		for (int i = 0, c = 0; i < 3; i++)
		{
			if (i != edgeIndex)
			{
				int v = fullInput.indexOf(vs.get(i));
				this.vertices[c++] = v;
				hashCode += 23 * hashCode + v;
			}
		}

		this.hashCode = hashCode;
	}

	public static boolean AreConnectable(FaceConnector a, FaceConnector b)
	{
		if (a.hashCode != b.hashCode)
			return false;

		int n = 2;
		int[] av = a.vertices;
		int[] bv = b.vertices;
		for (int i = 0; i < n; i++)
		{
			if (av[i] != bv[i])
				return false;
		}

		return true;
	}

	public static void Connect(FaceConnector a, FaceConnector b)
	{
		a.face.adjacentFaces[a.edgeIndex] = b.face;
		b.face.adjacentFaces[b.edgeIndex] = a.face;
	}
}