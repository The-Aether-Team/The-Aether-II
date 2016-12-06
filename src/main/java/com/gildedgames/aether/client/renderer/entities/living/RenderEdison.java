package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelEdison;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEdison extends RenderLiving<EntityEdison>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/npcs/edison/edison.png");

	public RenderEdison(RenderManager renderManager)
	{
		super(renderManager, new ModelEdison(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEdison entity)
	{
		return TEXTURE;
	}

}
