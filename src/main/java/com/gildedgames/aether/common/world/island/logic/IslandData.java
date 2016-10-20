package com.gildedgames.aether.common.world.island.logic;

import java.awt.*;

public class IslandData
{

	private Rectangle bounds;

	private final int minY, height;

	private boolean asleep, toRemove;

	private final IslandBiomeSet biomeSet;

	public IslandData(Rectangle bounds, int minY, int height, IslandBiomeSet biomeSet)
	{
		this.bounds = bounds;
		this.minY = minY;
		this.height = height;
		this.biomeSet = biomeSet;
	}

	public Rectangle getBounds()
	{
		return this.bounds;
	}

	public int getHeight() { return this.height; }

	public int getMinY() { return this.minY; }

	public boolean isAsleep()
	{
		return this.asleep;
	}

	public void setAsleep(boolean flag)
	{
		this.asleep = flag;
	}

	public boolean toRemove()
	{
		return this.toRemove;
	}

	public void setToRemove(boolean flag)
	{
		this.toRemove = flag;
	}

	public IslandBiomeSet getBiomeSet() { return this.biomeSet; }

}
