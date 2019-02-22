package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelKirrid;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerKirridWool;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntityKirrid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderKirrid extends RenderLiving<EntityKirrid>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/kirrid/kirrid.png");

	private static final ResourceLocation PUPILS = AetherCore.getResource("textures/entities/kirrid/kirrid_pupils.png");

	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/kirrid/eyes_closed.png");

	public RenderKirrid(RenderManager renderManager)
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

		ModelKirrid model = (ModelKirrid) this.mainModel;

		boolean globalInvisible = !entity.isInvisible() || this.renderOutlines;
		boolean playerInvisible = !globalInvisible && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);

		if (globalInvisible || playerInvisible)
		{
			EyeUtil.renderEyes(this.renderManager, model, model.HeadEyeRight, model.HeadEyeLeft, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
					headPitch, scale,
					PUPILS, EYES_CLOSED, false);
		}
	}
}
