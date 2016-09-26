package com.gildedgames.aether.client.renderer.entities;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

// Why must type erasure be a thing?!
public class TabulaRenderFactory<T extends Entity> implements IRenderFactory<T>
{

	public final Class<? extends Render<T>> clazz;

	public final ResourceLocation texture, model;

	public TabulaRenderFactory(Class<? extends Render<T>> clazz, ResourceLocation texture, ResourceLocation model)
	{
		this.clazz = clazz;
		this.texture = texture;
		this.model = model;
	}

	@Override
	public Render<T> createRenderFor(RenderManager manager)
	{
		Render<T> render = null;

		try
		{
			render = this.clazz.getConstructor(RenderManager.class, ResourceLocation.class, ResourceLocation.class).newInstance(manager, this.texture, this.model);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return render;
	}
}
