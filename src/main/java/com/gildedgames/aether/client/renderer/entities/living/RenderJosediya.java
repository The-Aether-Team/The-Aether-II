package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelJosediya;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.npc.EntityJosediya;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderJosediya extends RenderLiving<EntityJosediya>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/npcs/josediya/josediya.png");

	public RenderJosediya(final RenderManager renderManager)
	{
		super(renderManager, new ModelJosediya(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityJosediya entity)
	{
		return TEXTURE;
	}

}
