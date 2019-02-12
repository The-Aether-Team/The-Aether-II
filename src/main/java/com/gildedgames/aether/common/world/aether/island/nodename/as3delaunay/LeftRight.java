package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

public enum LeftRight
{
	LEFT,
	RIGHT;

	public LeftRight other()
	{
		return this == LEFT ? RIGHT : LEFT;
	}
}
