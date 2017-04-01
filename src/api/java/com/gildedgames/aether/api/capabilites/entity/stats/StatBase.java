package com.gildedgames.aether.api.capabilites.entity.stats;

import net.minecraft.util.ResourceLocation;

public class StatBase
{
	private final ResourceLocation id;

	private final double value;

	public StatBase(ResourceLocation id, double value)
	{
		this.id = id;
		this.value = value;
	}

	/**
	 * @return Returns this stat's value.
	 */
	public double getValue()
	{
		return this.value;
	}

	/**
	 * @return Returns this stat's resource ID.
	 */
	public ResourceLocation getResourceID()
	{
		return this.id;
	}
}
