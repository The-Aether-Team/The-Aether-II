package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelGlitterwing;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityGlitterwing;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGlitterwing extends RenderLiving<EntityGlitterwing>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/glitterwing/glitterwing.png");

	public RenderGlitterwing(RenderManager renderManager)
	{
		super(renderManager, new ModelGlitterwing(), 0.3f);
	}

	@Override
	protected void preRenderCallback(EntityGlitterwing entity, float partialTicks)
	{
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGlitterwing entity)
	{
		return texture;
	}
}
