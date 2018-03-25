package com.gildedgames.aether.common.world.aether.island.voronoi;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Point;

import java.util.ArrayList;

/**
 * Corner.java
 *
 * @author Connor
 */
public class Corner
{

	public ArrayList<Center> touches = new ArrayList(); //good

	public ArrayList<Corner> adjacent = new ArrayList(); //good

	public ArrayList<Edge> protrudes = new ArrayList();

	public Point loc;

	public int index;

	public boolean border;

	public double elevation;

	public boolean water, ocean, coast;

	public Corner downslope;

	public int river;

	public double moisture;
}
