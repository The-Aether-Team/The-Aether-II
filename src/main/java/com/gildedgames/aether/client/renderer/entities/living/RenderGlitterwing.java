package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelGlitterwing;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityGlitterwing;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderGlitterwing extends LivingRenderer<EntityGlitterwing, ModelGlitterwing>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/glitterwing/glitterwing.png");

	public RenderGlitterwing(EntityRendererManager renderManager)
	{
		super(renderManager, new ModelGlitterwing(), 0.3f);
	}

	@Override
	protected void preRenderCallback(EntityGlitterwing entity, float partialTicks)
	{
		GlStateManager.scalef(0.5F, 0.5F, 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGlitterwing entity)
	{
		return texture;
	}
}
