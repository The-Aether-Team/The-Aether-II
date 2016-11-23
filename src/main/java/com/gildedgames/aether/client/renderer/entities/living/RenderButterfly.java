package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelButterfly;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntityAerbunny;
import com.gildedgames.aether.common.entities.living.passive.EntityButterfly;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderButterfly extends RenderLiving<EntityButterfly>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/butterfly/butterfly.png");

	public RenderButterfly(RenderManager renderManager)
	{
		super(renderManager, new ModelButterfly(), 0.3f);
	}

	@Override
	protected void preRenderCallback(EntityButterfly entity, float partialTicks)
	{
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityButterfly entity)
	{
		return texture;
	}
}
