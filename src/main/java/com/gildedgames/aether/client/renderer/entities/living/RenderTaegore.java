package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelTaegore;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntityTaegore;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTaegore extends RenderLiving<EntityTaegore>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/taegore/taegore.png");

	public RenderTaegore(RenderManager renderManager)
	{
		super(renderManager, new ModelTaegore(), 0.75f);
	}

	@Override
	protected void preRenderCallback(EntityTaegore entity, float partialTicks)
	{
		float scale = 0.7F;

		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate(0.0F, 0.0F, -0.4F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTaegore entity)
	{
		return texture;
	}
}
