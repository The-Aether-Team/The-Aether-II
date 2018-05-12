package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelTivalier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.npc.EntityTivalier;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTivalier extends RenderLiving<EntityTivalier>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/npcs/tivalier/tivalier.png");

	public RenderTivalier(final RenderManager renderManager)
	{
		super(renderManager, new ModelTivalier(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityTivalier entity)
	{
		return TEXTURE;
	}

}
