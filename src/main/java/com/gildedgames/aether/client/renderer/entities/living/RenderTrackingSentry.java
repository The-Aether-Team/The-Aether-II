package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelTrackingSentry;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderTrackingSentry extends RenderLiving<EntityLiving>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(AetherCore.MOD_ID, "textures/entities/detonation_sentry/detonation_sentry.png");

	public RenderTrackingSentry(RenderManager renderManager)
	{
		super(renderManager, new ModelTrackingSentry(), 1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return TEXTURE;
	}

	@Override
	protected void preRenderCallback(EntityLiving entityliving, float f)
	{
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		GlStateManager.translate(0.0D, -1.5D, 0.0D);
	}

}
