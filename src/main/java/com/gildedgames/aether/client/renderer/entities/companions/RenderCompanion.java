package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.common.entities.living.companions.EntityCompanion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public abstract class RenderCompanion<T extends EntityCompanion> extends RenderLiving<T>
{
	public static boolean RENDER_FADE_ON_NEAR = true;

	private final double distanceLimit;

	public RenderCompanion(RenderManager renderManager, ModelBase model, float shadowSize, double distanceLimit)
	{
		super(renderManager, model, shadowSize);

		this.distanceLimit = distanceLimit;
	}

	@Override
	protected void renderModel(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		boolean renderOutlines = !entity.isInvisible() || this.renderOutlines;
		boolean isInvisible = !renderOutlines && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);

		if (renderOutlines || isInvisible)
		{
			if (!this.bindEntityTexture(entity))
			{
				return;
			}

			if (isInvisible)
			{
				GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}

			EntityPlayer owner = entity.getOwner();

			float opacity = 1.0f;

			if (owner != null && RenderCompanion.RENDER_FADE_ON_NEAR)
			{
				double distance = entity.getDistanceSqToEntity(owner);

				opacity = (float) Math.min(0.1f + (1.0f * (distance / this.distanceLimit)), 1.0f);
			}

			if (opacity < 1.0f)
			{
				GL11.glPushMatrix();

				GlStateManager.enableBlend();

				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, opacity);
			}

			this.mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

			this.renderExtra(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, opacity);

			if (opacity < 1.0f)
			{
				GlStateManager.disableBlend();

				GL11.glPopMatrix();

				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			}

			if (isInvisible)
			{
				GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
		}
	}

	protected void renderExtra(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, float opacity)
	{

	}
}
