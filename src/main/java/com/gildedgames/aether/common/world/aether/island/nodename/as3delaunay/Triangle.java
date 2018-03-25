package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

import java.util.ArrayList;

public final class Triangle
{

	private ArrayList<Site> _sites;

	public Triangle(final Site a, final Site b, final Site c)
	{
		_sites = new ArrayList();
		_sites.add(a);
		_sites.add(b);
		_sites.add(c);
	}

	public ArrayList<Site> get_sites()
	{
		return _sites;
	}

	public void dispose()
	{
		_sites.clear();
		_sites = null;
	}
}