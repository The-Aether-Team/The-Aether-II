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
	public static final boolean RENDER_FADE_ON_NEAR = true;

	private final double distanceLimit;

	public RenderCompanion(final RenderManager renderManager, final ModelBase model, final float shadowSize, final double distanceLimit)
	{
		super(renderManager, model, shadowSize);

		this.distanceLimit = distanceLimit;
	}

	@Override
	protected void renderModel(final T entity, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw,
			final float headPitch,
			final float scaleFactor)
	{
		final boolean renderOutlines = !entity.isInvisible() || this.renderOutlines;
		final boolean isInvisible = !renderOutlines && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);

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

			final EntityPlayer owner = entity.getOwner();

			float opacity = 1.0f;

			if (owner != null && RenderCompanion.RENDER_FADE_ON_NEAR)
			{
				final double distance = entity.getDistanceSq(owner);

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

	protected void renderExtra(final T entity, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw,
			final float headPitch,
			final float scaleFactor, final float opacity)
	{

	}
}
