package com.gildedgames.aether.common.world.util;

import java.util.List;

public class Connection
{

	private int[] pointa, pointb;

	private int weight;

	public Connection(int[] pointa, int[] pointb, List<Connection> connections)
	{
		this.pointa = pointa;
		this.pointb = pointb;

		for (Connection c : connections)//Make sure the connection is unique
		{
			int[] point1 = c.pointa;
			int[] point2 = c.pointb;
			if ((this.pointa == point1 && this.pointb == point2) || (this.pointa == point2 && this.pointb == point1))
			{
				return;
			}
		}
		connections.add(this);
		int[] subt = new int[2];
		for (int i = 0; i < 2; i++)
		{
			subt[i] = this.pointa[i] - this.pointb[i];
		}
		this.weight = subt[0] * subt[0] + subt[1] * subt[1];
	}

	public int getWeight()
	{
		return this.weight;
	}

	public int[] getPointA()
	{
		return this.pointa;
	}

	public int[] getPointB()
	{
		return this.pointb;
	}
}
