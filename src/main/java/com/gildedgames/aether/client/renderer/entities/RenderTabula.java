package com.gildedgames.aether.client.renderer.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import us.ichun.mods.ichunutil.common.module.tabula.client.formats.ImportList;
import us.ichun.mods.ichunutil.common.module.tabula.client.model.ModelTabula;

public class RenderTabula extends RenderLiving<EntityLiving>
{

	private final ResourceLocation texture, model;

	public RenderTabula(RenderManager renderManager, ResourceLocation texture, ResourceLocation model)
	{
		super(renderManager, new ModelTabula(ImportList.createProjectFromResource(model)), 1f);

		this.texture = texture;
		this.model = model;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return this.texture;
	}
}
