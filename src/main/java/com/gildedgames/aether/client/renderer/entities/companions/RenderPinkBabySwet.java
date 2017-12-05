package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.client.renderer.entities.living.layers.LayerSwetJelly;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.companions.EntityPinkBabySwet;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPinkBabySwet extends RenderCompanion<EntityPinkBabySwet>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/companions/pink_baby_swet.png");

	public RenderPinkBabySwet(final RenderManager renderManager)
	{
		super(renderManager, new ModelSlime(2), 0.5f, 2f);
	}

	@Override
	protected void preRenderCallback(final EntityPinkBabySwet entity, final float partialTickTime)
	{
		GlStateManager.scale(0.999F, 0.999F, 0.999F);
		final float f1 = 1.5F;
		final float f2 = (entity.prevSquishFactor + (entity.squishFactor - entity.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
		final float f3 = 1.0F / (f2 + 1.0F);
		GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityPinkBabySwet entity)
	{
		return TEXTURE;
	}

}
