package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.google.common.base.Supplier;
import net.minecraft.util.ResourceLocation;

public class GlowingLayer implements Supplier<ResourceLocation>
{

	private ResourceLocation resource;

	public void setResourceLocation(ResourceLocation resource)
	{
		this.resource = resource;
	}

	@Override
	public ResourceLocation get()
	{
		return this.resource;
	}

}
