package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.attachments.ModelSwetJelly;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class LayerSwetGel implements LayerRenderer<EntitySwet>
{

	private final RenderLiving renderer;

	private final ModelBase swetModel = new ModelSwetJelly();

	public LayerSwetGel(RenderLiving renderLiving)
	{
		this.renderer = renderLiving;
	}

	@Override
	public void doRenderLayer(EntitySwet entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		if (!entity.isInvisible())
		{
			this.renderer.bindTexture(entity.getType().texture);

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableNormalize();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.swetModel.setModelAttributes(this.renderer.getMainModel());
			this.swetModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
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
