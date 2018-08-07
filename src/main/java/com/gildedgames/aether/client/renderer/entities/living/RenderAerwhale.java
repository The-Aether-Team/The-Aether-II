package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelAerwhale;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderAerwhale extends RenderLiving<EntityLiving>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/aerwhale/aerwhale.png");

	public RenderAerwhale(RenderManager renderManager)
	{
		super(renderManager, new ModelAerwhale(), 2.0F);
	}

	@Override
	protected void preRenderCallback(EntityLiving entity, float f)
	{
		GlStateManager.scale(2.0D, 2.0D, 2.0D);
		GlStateManager.translate(0.0D, 1.0D, -1.5D);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return texture;
	}
}
