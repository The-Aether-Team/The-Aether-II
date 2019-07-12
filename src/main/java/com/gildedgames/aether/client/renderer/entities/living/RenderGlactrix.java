package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelGlactrix;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlactrixCrystals;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityGlactrix;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderGlactrix extends LivingRenderer<EntityGlactrix, ModelGlactrix>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/glactrix/glactrix.png");

	public RenderGlactrix(EntityRendererManager renderManager)
	{
		super(renderManager, new ModelGlactrix(), 0.4f);

		this.addLayer(new LayerGlactrixCrystals(this));
	}

	@Override
	protected void preRenderCallback(EntityGlactrix entity, float partialTicks)
	{
		float scale = 1F;

		GlStateManager.scalef(scale, scale, scale);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGlactrix entity)
	{
		return TEXTURE;
	}

	@Override
	protected void renderModel(EntityGlactrix entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		GlStateManager.pushMatrix();

		if (entity.getIsToppled())
		{
			GlStateManager.rotatef(180F , 0, 0, 1F);
			GlStateManager.translatef(0, -2.3F, 0);
			GlStateManager.rotatef(MathHelper.cos((ageInTicks % 100) / 4) * 10, 0, 0F, 1.0F);
			GlStateManager.rotatef((ageInTicks % 100) / 100f * 360, 0, 1.0F, 0F);
		}

		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		GlStateManager.popMatrix();
	}

}
