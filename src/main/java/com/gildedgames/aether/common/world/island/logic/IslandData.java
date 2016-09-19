package com.gildedgames.aether.common.world.island.logic;

import net.minecraft.world.biome.Biome;

import java.awt.*;

public class IslandData
{

	private Rectangle bounds;

	private boolean asleep, toRemove;

	private final Biome biome;

	public IslandData(Rectangle bounds, Biome biome)
	{
		this.bounds = bounds;
		this.biome = biome;
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

	public Biome getBiome() { return this.biome; }

}
