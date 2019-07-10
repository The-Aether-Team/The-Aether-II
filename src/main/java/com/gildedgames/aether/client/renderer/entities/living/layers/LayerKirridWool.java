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
	public void doRenderLayer(EntityKirrid ram, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
	{
		if (!ram.getSheared() && !ram.isInvisible())
		{
			GlStateManager.scale(1.01F, 1.01F, 1.01F);

			this.render.bindTexture(texture);

			this.woolModel.setModelAttributes(this.render.getMainModel());
			this.woolModel.setLivingAnimations(ram, p_177141_2_, p_177141_3_, p_177141_4_);
			this.woolModel.render(ram, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}
}
