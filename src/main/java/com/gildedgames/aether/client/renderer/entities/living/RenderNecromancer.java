package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelNecromancer;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.npc.EntityNecromancer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNecromancer extends RenderLiving<EntityNecromancer>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/npcs/necromancer/necromancer.png");

	public RenderNecromancer(final RenderManager renderManager)
	{
		super(renderManager, new ModelNecromancer(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityNecromancer entity)
	{
		return TEXTURE;
	}

}
