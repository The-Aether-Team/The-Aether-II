package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.client.models.entities.companions.ModelFangrin;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.companions.EntityFangrin;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFangrin extends RenderCompanion<EntityFangrin>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/companions/fangrin.png");

	public RenderFangrin(RenderManager renderManager)
	{
		super(renderManager, new ModelFangrin(), 0.4f, 2.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFangrin entity)
	{
		return TEXTURE;
	}
}
