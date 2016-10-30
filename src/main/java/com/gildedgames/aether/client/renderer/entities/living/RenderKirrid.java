package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelKirrid;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerKirridWool;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntityKirrid;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderKirrid extends RenderLiving<EntityKirrid>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/kirrid/kirrid.png");

	public RenderKirrid(RenderManager renderManager)
	{
		super(renderManager, new ModelKirrid(), 0.75f);

		this.addLayer(new LayerKirridWool(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityKirrid entity)
	{
		return texture;
	}
}
