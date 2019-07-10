package com.gildedgames.aether.common.math.delaunay;

public enum LeftRight
{
	LEFT,
	RIGHT;

	public LeftRight other()
	{
		return this == LEFT ? RIGHT : LEFT;
	}
}
