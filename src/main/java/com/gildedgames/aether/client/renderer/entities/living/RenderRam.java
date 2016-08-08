package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelRam;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerRamWool;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.EntityRam;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRam extends RenderLiving<EntityRam>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/ram/ram.png");

	public RenderRam(RenderManager renderManager)
	{
		super(renderManager, new ModelRam(), 0.75f);

		this.addLayer(new LayerRamWool(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRam entity)
	{
		return texture;
	}
}
