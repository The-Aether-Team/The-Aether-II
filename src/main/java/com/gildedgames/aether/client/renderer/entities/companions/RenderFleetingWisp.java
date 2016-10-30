package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.client.models.entities.companions.ModelWisp;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.companions.EntityFleetingWisp;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFleetingWisp extends RenderCompanion<EntityFleetingWisp>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/companions/fleeting_wisp.png");

	public RenderFleetingWisp(RenderManager renderManager)
	{
		super(renderManager, new ModelWisp(), 0.2F, 1.75D);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFleetingWisp entity)
	{
		return TEXTURE;
	}
}
