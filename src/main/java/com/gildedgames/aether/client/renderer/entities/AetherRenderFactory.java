package com.gildedgames.aether.client.renderer.entities;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

// Why must type erasure be a thing?!
public class AetherRenderFactory<T extends Entity> implements IRenderFactory<T>
{
	public final Class<? extends Render<T>> clazz;

	public AetherRenderFactory(Class<? extends Render<T>> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public Render<T> createRenderFor(RenderManager manager)
	{
		Render<T> render = null;

		try
		{
			render = this.clazz.getConstructor(RenderManager.class).newInstance(manager);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return render;
	}
}
