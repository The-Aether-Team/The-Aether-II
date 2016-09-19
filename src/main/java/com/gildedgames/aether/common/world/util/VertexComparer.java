package com.gildedgames.aether.common.world.util;

import java.util.Comparator;
import java.util.List;

//Compares the index of the objects in the list
public class VertexComparer implements Comparator<int[]>
{
	List<int[]> points;

	public VertexComparer(List<int[]> points)
	{
		this.points = points;
	}

	@Override
	public int compare(int[] arg0, int[] arg1)
	{
		int compare1 = this.points.indexOf(arg0);
		int compare2 = this.points.indexOf(arg1);
		return compare1 - compare2;
	}
}
