package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.living.ModelGlactrix;
import com.gildedgames.aether.client.models.entities.living.ModelGlactrixCrystals;
import com.gildedgames.aether.client.renderer.entities.living.RenderGlactrix;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityGlactrix;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class LayerGlactrixCrystals extends LayerRenderer<EntityGlactrix, ModelGlactrix>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/glactrix/glactrix_crystals.png");

	private final ModelGlactrixCrystals crystals = new ModelGlactrixCrystals();

	private final RenderGlactrix render;

	public LayerGlactrixCrystals(RenderGlactrix render)
	{
		super(render);

		this.render = render;
	}

	@Override
	public void render(EntityGlactrix glactrix, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
			float headPitch, float scale)
	{
		this.crystals.setModelAttributes(this.render.getEntityModel());

		GlStateManager.pushMatrix();
		Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);

		if (!glactrix.getIsSheared())
		{
			if (glactrix.getIsToppled())
			{
				GlStateManager.rotatef(180F, 0,0, 1F);
				GlStateManager.translatef(0, -2.35F, 0);
				GlStateManager.rotatef(MathHelper.cos((ageInTicks % 100) / 4) * 10, 0, 0F, 1.0F);
				GlStateManager.rotatef((ageInTicks % 100) / 100f * 360, 0, 1.0F, 0F);
			}

			this.crystals.render(glactrix, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}

		GlStateManager.popMatrix();
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}
}
