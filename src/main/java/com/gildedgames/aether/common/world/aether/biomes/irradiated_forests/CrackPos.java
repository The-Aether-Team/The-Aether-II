package com.gildedgames.aether.common.world.aether.biomes.irradiated_forests;

public class CrackPos
{
	private final int x, z;

	private double depression;

	public CrackPos(final int x, final int z, final double depression)
	{
		this.x = x;
		this.z = z;
		this.depression = depression;
	}

	public int getX()
	{
		return this.x;
	}

	public int getZ()
	{
		return this.z;
	}

	public double getDepression()
	{
		return this.depression;
	}

	public void addDepression(final double depression)
	{
		this.depression = depression;
	}
}
