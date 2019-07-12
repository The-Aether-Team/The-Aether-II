package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelSkyrootLizard;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntitySkyrootLizard;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderSkyrootLizard extends LivingRenderer<EntitySkyrootLizard>
{
	private static final ResourceLocation AMBERROOT = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_amberoot.png");

	private static final ResourceLocation GREATROOT = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_greatroot.png");

	private static final ResourceLocation SKYROOT = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_skyroot.png");

	private static final ResourceLocation WISPROOT = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_wisproot.png");

	private static final ResourceLocation LEAF_LAYER = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_leaf.png");

	public RenderSkyrootLizard(final EntityRendererManager renderManager)
	{
		super(renderManager, new ModelSkyrootLizard(), 0.4f);
	}

	public void renderLizard(EntitySkyrootLizard entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		ModelSkyrootLizard model = (ModelSkyrootLizard) this.mainModel;

		switch (entity.getLeafType())
		{
			case SKYROOT:
				this.renderManager.textureManager.bindTexture(SKYROOT);
				break;
			case WISPROOT:
				this.renderManager.textureManager.bindTexture(WISPROOT);
				break;
			case GREATROOT:
				this.renderManager.textureManager.bindTexture(GREATROOT);
				break;
			case AMBERROOT:
				this.renderManager.textureManager.bindTexture(AMBERROOT);
				break;
			default:
				return;
		}

		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		int color = entity.getLizardAccentColor();

		if (color != Integer.MIN_VALUE)
		{
			this.renderManager.textureManager.bindTexture(LEAF_LAYER);

			int red = (color >> 16) & 0xFF;
			int green = (color >> 8) & 0xFF;
			int blue = color & 0xFF;

			GlStateManager.color(red / 255f, green / 255f, blue / 255f, 1f);
		}

		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

	@Override
	protected void renderModel(EntitySkyrootLizard entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		boolean globalInvisible = !entity.isInvisible() || this.renderOutlines;
		boolean playerInvisible = !globalInvisible && !entity.isInvisibleToPlayer(Minecraft.getInstance().player);

		if (globalInvisible || playerInvisible)
		{
			this.renderLizard(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntitySkyrootLizard entity)
	{
		return null;
	}
}