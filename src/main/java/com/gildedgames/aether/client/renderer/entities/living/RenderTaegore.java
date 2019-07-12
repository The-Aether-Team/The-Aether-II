package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelTaegoreBase;
import com.gildedgames.aether.client.models.entities.living.ModelTaegoreLodHigh;
import com.gildedgames.aether.client.models.entities.living.ModelTaegoreLodLow;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityTaegore;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderTaegore extends RenderLivingLOD<EntityTaegore, ModelTaegoreBase>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/taegore/taegore.png");

	private static final ResourceLocation PUPIL_LEFT = AetherCore.getResource("textures/entities/taegore/pupil_left.png");

	private static final ResourceLocation PUPIL_RIGHT = AetherCore.getResource("textures/entities/taegore/pupil_right.png");

	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/taegore/eyes_closed.png");

	public RenderTaegore(EntityRendererManager renderManager)
	{
		super(renderManager, new ModelTaegoreLodHigh(), new ModelTaegoreLodLow(), 0.75f);
	}

	@Override
	protected void preRenderCallback(EntityTaegore entity, float partialTicks)
	{
		float scale = 0.7F;

		GlStateManager.scalef(scale, scale, scale);
		GlStateManager.translatef(0.0F, 0.0F, -0.4F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTaegore entity)
	{
		return texture;
	}

	@Override
	protected void renderModel(EntityTaegore entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);


		boolean globalInvisible = !entity.isInvisible() || this.renderOutlines;
		boolean playerInvisible = !globalInvisible && !entity.isInvisibleToPlayer(Minecraft.getInstance().player);

		if (!this.isLowDetail && (globalInvisible || playerInvisible))
		{
			ModelTaegoreBase model = this.getEntityModel();

			EyeUtil.renderEyes(this.renderManager, model, model.HeadEyeRight, model.HeadEyeLeft, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
					headPitch, scale,
					PUPIL_LEFT,
					PUPIL_RIGHT, EYES_CLOSED, false);
		}
	}
}
