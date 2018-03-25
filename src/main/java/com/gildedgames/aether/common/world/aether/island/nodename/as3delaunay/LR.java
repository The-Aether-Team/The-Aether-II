package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

public final class LR
{

	final public static LR LEFT = new LR("left");

	final public static LR RIGHT = new LR("right");

	private final String _name;

	public LR(final String name)
	{
		_name = name;
	}

	public static LR other(final LR leftRight)
	{
		return leftRight == LEFT ? RIGHT : LEFT;
	}

	@Override
	public String toString()
	{
		return _name;
	}
}
