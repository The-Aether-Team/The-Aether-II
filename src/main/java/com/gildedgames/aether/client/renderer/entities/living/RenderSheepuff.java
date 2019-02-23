package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelSheepuff;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerSheepuffWool;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntitySheepuff;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderSheepuff extends RenderLiving<EntitySheepuff>
{

	private static final ResourceLocation SHEEPUFF_TEXTURE = AetherCore.getResource("textures/entities/sheepuff/sheepuff_base.png");

	public RenderSheepuff(RenderManager renderManager)
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
