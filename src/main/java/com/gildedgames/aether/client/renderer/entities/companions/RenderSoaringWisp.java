package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.client.models.entities.companions.ModelWisp;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.companions.EntitySoaringWisp;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderSoaringWisp extends RenderCompanion<EntitySoaringWisp, ModelWisp<EntitySoaringWisp>>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/companions/soaring_wisp.png");

	public RenderSoaringWisp(EntityRendererManager renderManager)
	{
		super(renderManager, new ModelWisp<>(), 0.2F, 1.75D);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySoaringWisp entity)
	{
		return TEXTURE;
	}
}
