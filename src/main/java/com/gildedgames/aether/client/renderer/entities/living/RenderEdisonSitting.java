package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelEdisonSitting;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEdisonSitting extends RenderLiving<EntityEdison>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/npcs/edison/edison.png");

	public RenderEdisonSitting(final RenderManager renderManager)
	{
		super(renderManager, new ModelEdisonSitting(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityEdison entity)
	{
		return TEXTURE;
	}

}
