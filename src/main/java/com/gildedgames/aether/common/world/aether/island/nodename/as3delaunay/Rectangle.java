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
		this.left = this.x = x;
		this.top = this.y = y;
		this.width = width;
		this.height = height;
		this.right = x + width;
		this.bottom = y + height;
	}

	public boolean inBounds(final Point p)
	{
		return this.inBounds(p.x, p.y);
	}

	private boolean inBounds(final double x0, final double y0)
	{
		return !(x0 < this.x || x0 > this.right || y0 < this.y || y0 > this.bottom);
	}
}
