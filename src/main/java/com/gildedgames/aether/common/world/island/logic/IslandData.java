package com.gildedgames.aether.common.world.island.logic;

import java.awt.*;

public class IslandData
{

	private Rectangle bounds;

	private boolean asleep, toRemove;

	public IslandData(Rectangle bounds)
	{
		this.bounds = bounds;
	}

	public Rectangle getBounds()
	{
		return this.bounds;
	}

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

}
