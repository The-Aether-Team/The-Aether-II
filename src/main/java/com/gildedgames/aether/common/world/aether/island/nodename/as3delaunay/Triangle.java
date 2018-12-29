package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

import java.util.ArrayList;

public final class Triangle
{

	private ArrayList<Site> _sites;

	public Triangle(final Site a, final Site b, final Site c)
	{
		this._sites = new ArrayList<>();
		this._sites.add(a);
		this._sites.add(b);
		this._sites.add(c);
	}

	public ArrayList<Site> get_sites()
	{
		return this._sites;
	}

	public void dispose()
	{
		this._sites.clear();
		this._sites = null;
	}
}