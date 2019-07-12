package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelBurrukai;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityBurrukai;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderBurrukai extends LivingRenderer<EntityBurrukai, ModelBurrukai>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/burrukai/burrukai.png");

	private static final ResourceLocation PUPIL_LEFT = AetherCore.getResource("textures/entities/burrukai/burrukai_pupil_left.png");

	private static final ResourceLocation PUPIL_RIGHT = AetherCore.getResource("textures/entities/burrukai/burrukai_pupil_right.png");

	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/burrukai/burrukai_eyes_closed.png");

	public RenderBurrukai(EntityRendererManager renderManager)
	{
		super(renderManager, new ModelBurrukai(), 1.0f);
	}

	@Override
	protected void preRenderCallback(EntityBurrukai entity, float partialTicks)
	{
		float scale = 1.0F;

		GlStateManager.scalef(scale, scale, scale);
		GlStateManager.translatef(0.0F, 0.0F, -0.8F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBurrukai entity)
	{
		return texture;
	}

	@Override
	protected void renderModel(EntityBurrukai entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		ModelBurrukai model = this.getEntityModel();

		boolean globalInvisible = !entity.isInvisible() || this.renderOutlines;
		boolean playerInvisible = !globalInvisible && !entity.isInvisibleToPlayer(Minecraft.getInstance().player);

		if (globalInvisible || playerInvisible)
		{
			EyeUtil.renderEyes(this.renderManager, model, model.HeadEyeRight, model.HeadEyeLeft, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
					headPitch, scale,
					PUPIL_LEFT,
					PUPIL_RIGHT, EYES_CLOSED, false);
		}
	}
}
