package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

public final class Winding
{

	final public static Winding CLOCKWISE = new Winding("clockwise");

	final public static Winding COUNTERCLOCKWISE = new Winding("counterclockwise");

	final public static Winding NONE = new Winding("none");

	private final String _name;

	private Winding(final String name)
	{
		super();
		this._name = name;
	}

	@Override
	public String toString()
	{
		return this._name;
	}
}