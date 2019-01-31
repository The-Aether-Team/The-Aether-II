package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.living.ModelGlactrixCrystals;
import com.gildedgames.aether.client.renderer.entities.living.RenderGlactrix;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntityGlactrix;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerGlactrixCrystals implements LayerRenderer<EntityGlactrix>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/kirrid/kirrid_wool.png");
	private final ModelGlactrixCrystals crystals = new ModelGlactrixCrystals();

	private final RenderGlactrix render;

	public LayerGlactrixCrystals(RenderGlactrix render)
	{
		this.render = render;
	}

	@Override
	public void doRenderLayer(EntityGlactrix glactrix, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
			float headPitch, float scale)
	{
		this.crystals.setModelAttributes(this.render.getMainModel());

		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();

		if (glactrix.getIsToppled())
		{
			GlStateManager.rotate(180F, 0,0,1.0F);
			GlStateManager.translate(0, -2.35F, 0);
		}

		if (!glactrix.getIsSheared())
		{
			this.crystals.render(glactrix, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}

		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();

	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}
}
