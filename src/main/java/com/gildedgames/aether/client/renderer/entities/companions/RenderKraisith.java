package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.client.models.entities.companions.ModelKraisith;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.companions.EntityKraisith;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderKraisith extends RenderCompanion<EntityKraisith>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/companions/kraisith.png");

	public RenderKraisith(RenderManager renderManager)
	{
		super(renderManager, new ModelKraisith(), 0.5f, 1.5D);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityKraisith entity)
	{
		return TEXTURE;
	}
}
