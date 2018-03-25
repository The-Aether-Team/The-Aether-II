package com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay;

/**
 * GenUtil.java
 *
 * @author Connor
 */
public class GenUtils
{

	public static boolean closeEnough(final double d1, final double d2, final double diff)
	{
		return Math.abs(d1 - d2) <= diff;
	}
}
