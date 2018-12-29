package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

import java.util.ArrayList;

public final class SiteList implements IDisposable
{

	private ArrayList<Site> _sites;

	private int _currentIndex;

	private boolean _sorted;

	public SiteList()
	{
		this._sites = new ArrayList<>();
		this._sorted = false;
	}

	@Override
	public void dispose()
	{
		if (this._sites != null)
		{
			for (final Site site : this._sites)
			{
				site.dispose();
			}
			this._sites.clear();
			this._sites = null;
		}
	}

	public int push(final Site site)
	{
		this._sorted = false;
		this._sites.add(site);
		return this._sites.size();
	}

	public int get_length()
	{
		return this._sites.size();
	}

	public Site next()
	{
		if (this._sorted == false)
		{
			throw new Error("SiteList::next():  sites have not been sorted");
		}
		if (this._currentIndex < this._sites.size())
		{
			return this._sites.get(this._currentIndex++);
		}
		else
		{
			return null;
		}
	}

	public Rectangle getSitesBounds()
	{
		if (this._sorted == false)
		{
			Site.sortSites(this._sites);
			this._currentIndex = 0;
			this._sorted = true;
		}
		double xmin;
		double xmax;
		final double ymin;
		final double ymax;
		if (this._sites.isEmpty())
		{
			return new Rectangle(0, 0, 0, 0);
		}
		xmin = Double.MAX_VALUE;
		xmax = Double.MIN_VALUE;
		for (final Site site : this._sites)
		{
			if (site.get_x() < xmin)
			{
				xmin = site.get_x();
			}
			if (site.get_x() > xmax)
			{
				xmax = site.get_x();
			}
		}
		// here's where we assume that the sites have been sorted on y:
		ymin = this._sites.get(0).get_y();
		ymax = this._sites.get(this._sites.size() - 1).get_y();

		return new Rectangle(xmin, ymin, xmax - xmin, ymax - ymin);
	}

	/*public ArrayList<Color> siteColors(referenceImage:BitmapData = null)
	 {
	 var colors:Vector.<uint> = new Vector.<uint>();
	 for each (var site:Site in _sites)
	 {
	 colors.push(referenceImage ? referenceImage.getPixel(site.x, site.y) : site.color);
	 }
	 return colors;
	 }*/
	public ArrayList<Point> siteCoords()
	{
		final ArrayList<Point> coords = new ArrayList<>();
		for (final Site site : this._sites)
		{
			coords.add(site.get_coord());
		}
		return coords;
	}

	/**
	 *
	 * @return the largest circle centered at each site that fits in its region;
	 * if the region is infinite, return a circle of radius 0.
	 *
	 */
	public ArrayList<Circle> circles()
	{
		final ArrayList<Circle> circles = new ArrayList<>();
		for (final Site site : this._sites)
		{
			double radius = 0;
			final VoronoiEdge nearestEdge = site.nearestEdge();

			//!nearestEdge.isPartOfConvexHull() && (radius = nearestEdge.sitesDistance() * 0.5);
			if (!nearestEdge.isPartOfConvexHull())
			{
				radius = nearestEdge.sitesDistance() * 0.5;
			}
			circles.add(new Circle(site.get_x(), site.get_y(), radius));
		}
		return circles;
	}

	public ArrayList<ArrayList<Point>> regions(final Rectangle plotBounds)
	{
		final ArrayList<ArrayList<Point>> regions = new ArrayList<>();
		for (final Site site : this._sites)
		{
			regions.add(site.region(plotBounds));
		}
		return regions;
	}
	/**
	 *
	 * @param proximityMap a BitmapData whose regions are filled with the site
	 * index values; see PlanePointsCanvas::fillRegions()
	 * @param x
	 * @param y
	 * @return coordinates of nearest Site to (x, y)
	 *
	 */
	/*public Point nearestSitePoint(proximityMap:BitmapData, double x, double y)
	 {
     var index:uint = proximityMap.getPixel(x, y);
     if (index > _sites.length - 1)
     {
     return null;
     }
     return _sites[index].coord;
     }*/
}