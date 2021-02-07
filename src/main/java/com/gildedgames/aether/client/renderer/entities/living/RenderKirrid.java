package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelKirrid;
import com.gildedgames.aether.client.models.entities.living.ModelKirridBaby;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.client.renderer.ModelBaseAether;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerKirridWool;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityKirrid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderKirrid extends RenderLivingChild<EntityKirrid>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/kirrid/kirrid.png");
	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/kirrid/eyes_closed.png");

	private static final ResourceLocation texture_baby = AetherCore.getResource("textures/entities/kirrid/babykirrid.png");
	private static final ResourceLocation EYES_CLOSED_baby = AetherCore.getResource("textures/entities/kirrid/babykirrid_eyes_closed.png");

	public RenderKirrid(RenderManager renderManager)
	{
		super(renderManager, new ModelKirrid(), new ModelKirridBaby(), texture, texture_baby, 0.75f);

		this.addLayer(new LayerKirridWool(this));
	}

	@Override
	protected void renderModel(EntityKirrid entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		ResourceLocation eyes = !entity.isChild() ? EYES_CLOSED : EYES_CLOSED_baby;

		boolean globalInvisible = !entity.isInvisible() || this.renderOutlines;
		boolean playerInvisible = !globalInvisible && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);

		ModelBaseAether model = (ModelBaseAether) this.mainModel;

		if (globalInvisible || playerInvisible)
		{
			EyeUtil.renderEyesBasic(this.renderManager, model, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale,
					eyes);
		}
	}
}
