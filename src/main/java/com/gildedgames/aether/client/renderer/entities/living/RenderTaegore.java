package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelTaegore;
import com.gildedgames.aether.client.models.entities.living.ModelTaegoreBaby;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.client.renderer.ModelBaseAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityTaegore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTaegore extends RenderLivingChild<EntityTaegore>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/taegore/taegore.png");
	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/taegore/eyes_closed.png");

	private static final ResourceLocation texture_baby = AetherCore.getResource("textures/entities/taegore/babytaegore.png");
	private static final ResourceLocation EYES_CLOSED_baby = AetherCore.getResource("textures/entities/taegore/babytaegore_eyes_closed.png");

	public RenderTaegore(RenderManager renderManager)
	{
		super(renderManager, new ModelTaegore(), new ModelTaegoreBaby(), texture, texture_baby, 0.75f);
	}

	@Override
	protected void renderModel(EntityTaegore entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		ResourceLocation eyes = !entity.isChild() ? EYES_CLOSED : EYES_CLOSED_baby;

		ModelBaseAether model = (ModelBaseAether) this.mainModel;

		boolean globalInvisible = !entity.isInvisible() || this.renderOutlines;
		boolean playerInvisible = !globalInvisible && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);

		if (globalInvisible || playerInvisible)
		{
			EyeUtil.renderEyesBasic(this.renderManager, model, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale,
					eyes);
		}
	}
}
