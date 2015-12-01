package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.renderer.entities.living.layers.LayerWings;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.mounts.EntityFlyingCow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderFlyingCow extends RenderLiving<EntityFlyingCow>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/flying_cow/flying_cow.png");

	private static final ResourceLocation saddledTexture = AetherCore.getResource("textures/entities/flying_cow/flying_cow_saddled.png");

	public RenderFlyingCow(RenderManager renderManager, ModelBase modelBase, float shadowSize)
	{
		super(renderManager, modelBase, shadowSize);

		this.addLayer(new LayerWings(this, -10.0f));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingCow entity)
	{
		return entity.isSaddled() ? saddledTexture : texture;
	}
}
