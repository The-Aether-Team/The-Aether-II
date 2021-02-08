package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelBurrukai;
import com.gildedgames.aether.client.models.entities.living.ModelBurrukaiBaby;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.client.renderer.ModelBaseAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityBurrukai;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBurrukai extends RenderLivingChild<EntityBurrukai>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/burrukai/burrukai.png");
	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/burrukai/burrukai_eyes_closed.png");

	private static final ResourceLocation texture_baby = AetherCore.getResource("textures/entities/burrukai/babyburrukai.png");
	private static final ResourceLocation EYES_CLOSED_baby = AetherCore.getResource("textures/entities/burrukai/babyburrukai_eyes_closed.png");

	public RenderBurrukai(RenderManager renderManager)
	{
		super(renderManager, new ModelBurrukai(), new ModelBurrukaiBaby(), texture, texture_baby, 1.0f);
	}

	@Override
	protected void preRenderCallback(EntityBurrukai entity, float partialTicks)
	{
		if (!entity.isChild())
		{
			float scale = 1.0F;

			GlStateManager.scale(scale, scale, scale);
			GlStateManager.translate(0.0F, 0.0F, -0.8F);
		}
	}

	@Override
	protected void renderModel(EntityBurrukai entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
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
