package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelSheepuff;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerSheepuffWool;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntitySheepuff;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderSheepuff extends LivingRenderer<EntitySheepuff, ModelSheepuff>
{

	private static final ResourceLocation SHEEPUFF_TEXTURE = AetherCore.getResource("textures/entities/sheepuff/sheepuff_base.png");

	public RenderSheepuff(EntityRendererManager renderManager)
	{
		super(renderManager, new ModelSheepuff(), 0.75f);

		this.addLayer(new LayerSheepuffWool(this));
	}

	@Override
	protected void renderModel(EntitySheepuff entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntitySheepuff entity)
	{
		return SHEEPUFF_TEXTURE;
	}
}
