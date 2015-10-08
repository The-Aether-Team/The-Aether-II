package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.renderer.entities.living.RenderSheepuff;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.EntitySheepuff;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class LayerSheepuffFur implements LayerRenderer
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/living/sheepuff/fur.png");

	private final ModelSheep1 sheepModel = new ModelSheep1();

	private final RenderSheepuff render;

	public LayerSheepuffFur(RenderSheepuff render)
	{
		this.render = render;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entity, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
	{
		EntitySheepuff sheepuff = (EntitySheepuff) entity;

		if (!sheepuff.getSheared() && !sheepuff.isInvisible())
		{
			float[] colors = EntitySheep.func_175513_a(sheepuff.getFleeceColor());
			GlStateManager.color(colors[0], colors[1], colors[2]);

			this.render.bindTexture(texture);

			this.sheepModel.setModelAttributes(this.render.getMainModel());
			this.sheepModel.setLivingAnimations(sheepuff, p_177141_2_, p_177141_3_, p_177141_4_);
			this.sheepModel.render(sheepuff, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}
}
