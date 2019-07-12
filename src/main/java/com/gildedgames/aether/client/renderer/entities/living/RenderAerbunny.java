package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelAerbunny;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityAerbunny;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderAerbunny extends LivingRenderer<EntityAerbunny, ModelAerbunny>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/aerbunny/aerbunny.png");

	public RenderAerbunny(final EntityRendererManager renderManager)
	{
		super(renderManager, new ModelAerbunny(), 0.4f);
	}

	protected void rotateAerbunny(final EntityAerbunny entity)
	{
		if (!entity.onGround && entity.isOnePlayerRiding())
		{
			Vec3d motion = entity.getMotion();

			if (motion.y > 0.5D)
			{
				GlStateManager.rotatef(15F, -1F, 0.0F, 0.0F);
			}
			else if (motion.y < -0.5D)
			{
				GlStateManager.rotatef(-15F, -1F, 0.0F, 0.0F);
			}
			else
			{
				GlStateManager.rotatef((float) (motion.y * 30D), -1F, 0.0F, 0.0F);
			}
		}
	}

	@Override
	protected void preRenderCallback(final EntityAerbunny entity, final float partialTicks)
	{
		this.rotateAerbunny(entity);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityAerbunny entity)
	{
		return texture;
	}
}
