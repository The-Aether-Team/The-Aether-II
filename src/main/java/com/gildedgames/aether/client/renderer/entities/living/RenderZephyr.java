package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelZephyr;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderZephyr extends RenderLiving<EntityLiving>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/zephyr/zephyr.png");

	public RenderZephyr(final RenderManager manager)
	{
		super(manager, new ModelZephyr(), 1.0F);
	}

	@Override
	protected void preRenderCallback(final EntityLiving entity, final float partialTicks)
	{
		GlStateManager.translate(0.0F, -0.4F, 0.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityLiving entity)
	{
		return TEXTURE;
	}

}
