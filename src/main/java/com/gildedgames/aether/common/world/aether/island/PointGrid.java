package com.gildedgames.aether.common.world.aether.island;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Point;

public class PointGrid
{
	private final int width, length;

	private Point[] grid;

	public PointGrid(int width, int length)
	{
		this.width = width;
		this.length = length;

		this.grid = new Point[this.width * this.length];
	}

	public Point[] getGrid()
	{
		return this.grid;
	}

	public Point get(int x, int z)
	{
		int index = x + (z * this.width);

		return this.grid[index];
	}

	public void set(Point point, int x, int z)
	{
		int index = x + (z * this.width);

		this.grid[index] = point;
	}
}
