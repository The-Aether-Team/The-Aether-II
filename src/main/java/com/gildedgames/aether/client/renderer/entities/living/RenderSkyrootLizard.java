package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelSkyrootLizard;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntitySkyrootLizard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSkyrootLizard extends RenderLiving<EntitySkyrootLizard>
{
	private static final ResourceLocation AMBERROOT = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_amberoot.png");

	private static final ResourceLocation GREATROOT = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_greatroot.png");

	private static final ResourceLocation SKYROOT = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_skyroot.png");

	private static final ResourceLocation WISPROOT = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_wisproot.png");

	private static final ResourceLocation LEAF_LAYER = AetherCore.getResource("textures/entities/skyroot_lizard/skyroot_lizard_leaf.png");

	public RenderSkyrootLizard(final RenderManager renderManager)
	{
		super(renderManager, new ModelSkyrootLizard(), 0.4f);
	}

	public void renderLizard(EntitySkyrootLizard entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		ModelSkyrootLizard model = (ModelSkyrootLizard) this.mainModel;

		switch (entity.getLeafType())
		{
			case SKYROOT:
				this.renderManager.renderEngine.bindTexture(SKYROOT);
				break;
			case WISPROOT:
				this.renderManager.renderEngine.bindTexture(WISPROOT);
				break;
			case GREATROOT:
				this.renderManager.renderEngine.bindTexture(GREATROOT);
				break;
			case AMBERROOT:
				this.renderManager.renderEngine.bindTexture(AMBERROOT);
				break;
			default:
				return;
		}

		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		int color = entity.getLizardAccentColor();

		if (color != Integer.MIN_VALUE)
		{
			this.renderManager.renderEngine.bindTexture(LEAF_LAYER);

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
		boolean playerInvisible = !globalInvisible && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);

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