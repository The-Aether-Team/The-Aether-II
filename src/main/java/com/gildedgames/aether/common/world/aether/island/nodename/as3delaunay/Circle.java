package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

public final class Circle extends Object
{

	public Point center;

	public double radius;

	public Circle(final double centerX, final double centerY, final double radius)
	{
		super();
		this.center = new Point(centerX, centerY);
		this.radius = radius;
	}

	@Override
	public String toString()
	{
		return "Circle (center: " + center + "; radius: " + radius + ")";
	}
}