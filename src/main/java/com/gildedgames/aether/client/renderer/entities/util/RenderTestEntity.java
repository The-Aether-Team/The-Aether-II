package com.gildedgames.aether.client.renderer.entities.util;

import com.gildedgames.aether.client.models.entities.util.ModelTestEntity;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderTestEntity extends RenderLiving<EntityLiving>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(AetherCore.MOD_ID, "textures/entities/test.png");

	public RenderTestEntity(RenderManager renderManager)
	{
		super(renderManager, new ModelTestEntity(), 1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return TEXTURE;
	}
}
