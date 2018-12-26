package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.api.entity.EntityNPC;
import com.gildedgames.aether.client.models.entities.living.ModelMysteriousFigure;
import com.gildedgames.aether.common.AetherCelebrations;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderMysteriousFigure extends RenderLiving<EntityNPC>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/npcs/mysterious_figure/mysterious_figure.png");

	public RenderMysteriousFigure(final RenderManager renderManager)
	{
		super(renderManager, new ModelMysteriousFigure(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityNPC entity)
	{
		return TEXTURE;
	}

	@Override
	protected void renderModel(EntityNPC entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (AetherCelebrations.isHalloweenEvent())
		{
			super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			this.shadowSize = 0.5f;
		}
		else
		{
			this.shadowSize = 0.0f;
		}
	}

}
