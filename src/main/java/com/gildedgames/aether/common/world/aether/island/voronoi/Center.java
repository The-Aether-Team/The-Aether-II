package com.gildedgames.aether.common.world.aether.island.voronoi;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Point;
import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * Center.java
 *
 * @author Connor
 */
public class Center
{

	public int index;

	public Point loc;

	public ArrayList<Corner> corners = Lists.newArrayList();//good

	public ArrayList<Center> neighbors = Lists.newArrayList();//good

	public ArrayList<Edge> borders = Lists.newArrayList();

	public boolean border, ocean, water, coast;

	public double elevation;

	public double moisture;

	public Enum biome;

	public double area;

	public Center()
	{
	}

	public Center(final Point loc)
	{
		this.loc = loc;
	}
}
