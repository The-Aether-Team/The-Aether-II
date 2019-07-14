package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.living.ModelSwetHead;
import com.gildedgames.aether.client.models.entities.living.ModelSwetJelly;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class LayerSwetJelly extends LayerRenderer<EntitySwet, ModelSwetHead>
{
	private final LivingRenderer<EntitySwet, ModelSwetHead> render;

	private final EntityModel<EntitySwet> model = new ModelSwetJelly();

	public LayerSwetJelly(final LivingRenderer<EntitySwet, ModelSwetHead> renderLiving)
	{
		super(renderLiving);

		this.render = renderLiving;
	}

	@Override
	public void render(final EntitySwet entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks,
			final float netHeadYaw, final float headPitch, final float scale)
	{
		if (!entity.isInvisible() && entity.getActualSaturation() != 0)
		{
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

			final float sc = ((entity.getActualSaturation() - 1) / 5.0F);
			final float s = sc * 1.1F;

			GlStateManager.translatef(-0.0f, 0.4F + (s * -1.35F), -0.15f);
			GlStateManager.scalef(0.7F + s, 0.7F + s, 0.7F + s);

			this.render.bindTexture(entity.getSwetType().texture_jelly);

			GlStateManager.enableNormalize();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.model.setModelAttributes(this.render.getEntityModel());
			this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlend();
			GlStateManager.disableNormalize();
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}
}
