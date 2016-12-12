package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelBurrukai;
import com.gildedgames.aether.client.models.entities.living.ModelTaegore;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntityBurrukai;
import com.gildedgames.aether.common.entities.living.passive.EntityTaegore;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBurrukai extends RenderLiving<EntityBurrukai>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/burrukai/burrukai.png");

	public RenderBurrukai(RenderManager renderManager)
	{
		super(renderManager, new ModelBurrukai(), 1.0f);
	}

	@Override
	protected void preRenderCallback(EntityBurrukai entity, float partialTicks)
	{
		float scale = 1.0F;

		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate(0.0F, 0.0F, -0.8F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBurrukai entity)
	{
		return texture;
	}
}
