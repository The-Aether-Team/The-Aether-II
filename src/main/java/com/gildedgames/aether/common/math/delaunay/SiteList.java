package com.gildedgames.aether.common.math.delaunay;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SiteList
{

	private final List<Site> sites;

	public SiteList()
	{
		this.sites = new ArrayList<>();
	}

	public void push(final Site site)
	{
		this.sites.add(site);
	}

	public int getSize()
	{
		return this.sites.size();
	}

	public ArrayDeque<Site> getSortedQueue()
	{
		Collections.sort(this.sites);

		return new ArrayDeque<>(this.sites);
	}

	public Rectangle getSitesBounds()
	{
		if (this.sites.isEmpty())
		{
			return new Rectangle(0, 0, 0, 0);
		}
		double xmin = Double.MAX_VALUE;
		double xmax = Double.MIN_VALUE;

		double ymin = Double.MAX_VALUE;
		double ymax = Double.MIN_VALUE;

		for (final Site site : this.sites)
		{
			if (site.x < xmin)
			{
				xmin = site.x;
			}
			if (site.x > xmax)
			{
				xmax = site.x;
			}
			if (site.y < ymin)
			{
				ymin = site.y;
			}
			if (site.y > ymax)
			{
				ymax = site.y;
			}
		}
		return new Rectangle(xmin, ymin, xmax - xmin, ymax - ymin);
	}

	public List<Site> getInner()
	{
		return this.sites;
	}
}