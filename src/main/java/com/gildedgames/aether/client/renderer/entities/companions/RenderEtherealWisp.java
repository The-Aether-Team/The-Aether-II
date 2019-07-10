package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.client.models.entities.companions.ModelWisp;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.companions.EntityEtherealWisp;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEtherealWisp extends RenderCompanion<EntityEtherealWisp>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/companions/ethereal_wisp.png");

	public RenderEtherealWisp(RenderManager renderManager)
	{
		super(renderManager, new ModelWisp(), 0.2F, 1.75D);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEtherealWisp entity)
	{
		return TEXTURE;
	}
}
