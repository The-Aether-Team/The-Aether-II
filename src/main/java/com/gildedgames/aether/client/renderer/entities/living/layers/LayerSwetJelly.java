package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.living.ModelSwetJelly;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class LayerSwetJelly implements LayerRenderer<EntitySwet>
{
	private final RenderLiving render;

	private final ModelBase model = new ModelSwetJelly();

	public LayerSwetJelly(final RenderLiving renderLiving)
	{
		this.render = renderLiving;
	}

	@Override
	public void doRenderLayer(final EntitySwet entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks,
			final float netHeadYaw, final float headPitch, final float scale)
	{
		if (!entity.isInvisible())
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			final float sc = (entity.getFoodSaturation() / 5.0F);
			final float s = sc * 0.3F;

			GlStateManager.scale(1.0F + s, 1.0F + s, 1.0F + s);
			GlStateManager.translate(0, -(sc * 0.35), 0);

			this.render.bindTexture(entity.getType().texture_jelly);

			GlStateManager.enableNormalize();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.model.setModelAttributes(this.render.getMainModel());
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
