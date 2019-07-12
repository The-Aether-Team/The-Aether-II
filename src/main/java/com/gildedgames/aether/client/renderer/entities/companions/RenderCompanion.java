package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.common.entities.companions.EntityCompanion;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.opengl.GL11;

public abstract class RenderCompanion<T extends EntityCompanion, M extends EntityModel<T>> extends LivingRenderer<T, M>
{
	public static final boolean RENDER_FADE_ON_NEAR = true;

	private final double distanceLimit;

	public RenderCompanion(final EntityRendererManager renderManager, final M model, final float shadowSize, final double distanceLimit)
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
		final boolean isInvisible = !renderOutlines && !entity.isInvisibleToPlayer(Minecraft.getInstance().player);

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

			final PlayerEntity owner = entity.getOwner();

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

			this.field_77045_g.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

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
