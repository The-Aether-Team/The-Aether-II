package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.living.ModelRamWool;
import com.gildedgames.aether.client.renderer.entities.living.RenderKirrid;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityKirrid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerKirridWool implements LayerRenderer<EntityKirrid>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/kirrid/kirrid_wool.png");

	private final ModelRamWool woolModel = new ModelRamWool();

	private final RenderKirrid render;

	public LayerKirridWool(RenderKirrid render)
	{
		this.render = render;
	}

	@Override
	public void doRenderLayer(EntityKirrid ram, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (!ram.getSheared() && !ram.isInvisible())
		{
			GlStateManager.scale(1.01F, 1.01F, 1.01F);

			this.render.bindTexture(texture);

			this.woolModel.setModelAttributes(this.render.getMainModel());
			this.woolModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, ram);
			this.woolModel.setLivingAnimations(ram, limbSwing, limbSwingAmount, partialTicks);
			this.woolModel.render(ram, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}
}
