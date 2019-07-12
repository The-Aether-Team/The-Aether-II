package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelKirrid;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerKirridWool;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityKirrid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderKirrid extends LivingRenderer<EntityKirrid, ModelKirrid>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/kirrid/kirrid.png");

	private static final ResourceLocation PUPIL_LEFT = AetherCore.getResource("textures/entities/kirrid/pupil_left.png");

	private static final ResourceLocation PUPIL_RIGHT = AetherCore.getResource("textures/entities/kirrid/pupil_right.png");

	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/kirrid/eyes_closed.png");

	public RenderKirrid(EntityRendererManager renderManager)
	{
		super(renderManager, new ModelKirrid(), 0.75f);

		this.addLayer(new LayerKirridWool(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityKirrid entity)
	{
		return texture;
	}

	@Override
	protected void renderModel(EntityKirrid entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		ModelKirrid model = this.getEntityModel();

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
