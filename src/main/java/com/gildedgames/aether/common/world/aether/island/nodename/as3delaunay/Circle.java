package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

public final class Circle
{

	public final Point center;

	public final double radius;

	public Circle(final double centerX, final double centerY, final double radius)
	{
		super();
		this.center = new Point(centerX, centerY);
		this.radius = radius;
	}

	@Override
	public String toString()
	{
		return "Circle (center: " + this.center + "; radius: " + this.radius + ")";
	}
}