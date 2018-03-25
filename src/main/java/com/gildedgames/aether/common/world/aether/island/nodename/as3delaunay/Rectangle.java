package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

/**
 * Rectangle.java
 *
 * @author Connor
 */
public class Rectangle
{

	final public double x, y, width, height, right, bottom, left, top;

	public Rectangle(final double x, final double y, final double width, final double height)
	{
		left = this.x = x;
		top = this.y = y;
		this.width = width;
		this.height = height;
		right = x + width;
		bottom = y + height;
	}

	public boolean liesOnAxes(final Point p)
	{
		return GenUtils.closeEnough(p.x, x, 1) || GenUtils.closeEnough(p.y, y, 1) || GenUtils.closeEnough(p.x, right, 1) || GenUtils
				.closeEnough(p.y, bottom, 1);
	}

	public boolean inBounds(final Point p)
	{
		return inBounds(p.x, p.y);
	}

	public boolean inBounds(final double x0, final double y0)
	{
		return !(x0 < x || x0 > right || y0 < y || y0 > bottom);
	}
}
