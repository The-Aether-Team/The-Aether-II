package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.client.models.entities.living.ModelMysteriousFigure;
import com.gildedgames.aether.common.AetherCelebrations;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.characters.EntityMysteriousFigure;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderMysteriousFigure extends LivingRenderer<EntityMysteriousFigure, ModelMysteriousFigure>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/npcs/mysterious_figure/mysterious_figure.png");

	public RenderMysteriousFigure(final EntityRendererManager renderManager)
	{
		super(renderManager, new ModelMysteriousFigure(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityMysteriousFigure entity)
	{
		return TEXTURE;
	}

	@Override
	protected void renderModel(EntityMysteriousFigure entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
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
