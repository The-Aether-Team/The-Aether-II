package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.renderer.entities.living.layers.LayerSheepuffFur;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSheepuff extends RenderLiving
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/sheepuff/sheepuff.png");

	public RenderSheepuff(RenderManager renderManager, ModelBase modelBase, float shadowsSize)
	{
		super(renderManager, modelBase, shadowsSize);

		this.addLayer(new LayerSheepuffFur(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}
