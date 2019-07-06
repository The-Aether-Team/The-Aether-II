package com.gildedgames.aether.client.renderer.world;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.client.renderer.textures.SimpleDXT1Texture;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class RenderWorldSkybox extends IRenderHandler
{
	private static final float TRANSITION_PER_TICK = 1.0f / 8.0f;

	private static final ResourceLocation TEXTURE_SKYBOX = AetherCore.getResource("textures/environment/skybox/skybox_clouds.png");

	private SimpleDXT1Texture texture;

	private long prevUpdateTimeMillis, nowUpdateTimeMillis;

	private float skyDarkness;

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		if (!AetherCore.CONFIG.isSkyboxRenderEnabled())
		{
			return;
		}

		if (this.texture == null)
		{
			mc.getTextureManager().loadTexture(TEXTURE_SKYBOX, this.texture = new SimpleDXT1Texture(TEXTURE_SKYBOX));
		}

		this.prevUpdateTimeMillis = this.nowUpdateTimeMillis;
		this.nowUpdateTimeMillis = System.nanoTime() / 1_000_000;

		this.updateLightmap(mc, partialTicks);

		float farPlaneDistance = (float) (mc.gameSettings.renderDistanceChunks * 32);

		mc.entityRenderer.enableLightmap();

		GlStateManager.disableFog();

		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.loadIdentity();

		Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) mc.displayWidth / (float) mc.displayHeight, 0.05F, farPlaneDistance * 4.0F);

		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.pushMatrix();

		mc.getTextureManager().bindTexture(TEXTURE_SKYBOX);

		GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		int k2 = 15 << 20;
		int h = k2 >> 16 & 65535;
		int k = k2 & 65535;

		Tessellator tessellator = Tessellator.getInstance();

		float f = farPlaneDistance * 4;
		float a = 1f;
		float b = 1f;

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		BufferBuilder builder = tessellator.getBuffer();

		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

		builder.pos(-0.5f * f, -0.5f * f, 0.5f * f).tex(0.5f, 1.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, -0.5f * f, 0.5f * f).tex(0.5f, 0.5f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, -0.5f * f, -0.5f * f).tex(0.25f, 0.5f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, -0.5f * f, -0.5f * f).tex(0.25f, 1.0f).lightmap(h, k).color(b, b, b, a).endVertex();

		builder.pos(0.5f * f, 0.5f * f, 0.5f * f).tex(0.5f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, 0.5f * f).tex(0.5f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, -0.5f * f).tex(0.25f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, -0.5f * f).tex(0.25f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();

		builder.pos(0.5f * f, -0.5f * f, -0.5f * f).tex(0.25f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, -0.5f * f).tex(0.25f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, -0.5f * f).tex(0.0f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, -0.5f * f, -0.5f * f).tex(0.0f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();

		builder.pos(-0.5f * f, -0.5f * f, 0.5f * f).tex(0.75f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, 0.5f * f).tex(0.75f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, 0.5f * f).tex(0.5f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, -0.5f * f, 0.5f * f).tex(0.5f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();

		builder.pos(-0.5f * f, -0.5f * f, -0.5f * f).tex(1.0f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, -0.5f * f).tex(1.0f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, 0.5f * f).tex(0.75f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, -0.5f * f, 0.5f * f).tex(0.75f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();

		builder.pos(0.5f * f, -0.5f * f, 0.5f * f).tex(0.5f, 0.5f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, 0.5f * f).tex(0.5f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, -0.5f * f).tex(0.25f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, -0.5f * f, -0.5f * f).tex(0.25f, 0.5f).lightmap(h, k).color(b, b, b, a).endVertex();

		tessellator.draw();

		GlStateManager.disableBlend();

		GlStateManager.depthMask(true);

		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.loadIdentity();

		Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) mc.displayWidth / (float) mc.displayHeight, 0.05F,
				farPlaneDistance * MathHelper.SQRT_2);

		GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		GlStateManager.disableFog();

		mc.entityRenderer.disableLightmap();

	}

	private float getFOVModifier(float partialTicks, boolean useFOVSetting)
	{
		return Minecraft.getMinecraft().entityRenderer.getFOVModifier(partialTicks, useFOVSetting);
	}

	private void updateLightmap(Minecraft mc, float partialTicks)
	{
		IPrecipitationManager precipitation = Minecraft.getMinecraft().world.getCapability(AetherCapabilities.PRECIPITATION_MANAGER, null);

		if (precipitation == null)
		{
			this.skyDarkness = 0.0f;

			return;
		}

		if (!mc.world.isRaining())
		{
			this.skyDarkness -= this.getUpdateStep() * TRANSITION_PER_TICK;

			if (this.skyDarkness < 0.0f)
			{
				this.skyDarkness = 0.0f;
			}
		}
		else
		{
			float target = precipitation.getSkyDarkness();

			if (target > this.skyDarkness)
			{
				this.skyDarkness += this.getUpdateStep() * TRANSITION_PER_TICK;

				if (this.skyDarkness > target)
				{
					this.skyDarkness = target;
				}
			}
			else
			{
				this.skyDarkness -= this.getUpdateStep() * TRANSITION_PER_TICK;

				if (this.skyDarkness < target)
				{
					this.skyDarkness = target;
				}
			}

		}

		float strength = 1.0f - this.skyDarkness;

		float f = mc.world.getSunBrightness(partialTicks);
		f = f * strength;

		EntityRendererHelper.updateLightmap(mc, partialTicks, f);
	}

	private float getUpdateStep()
	{
		return (this.nowUpdateTimeMillis - this.prevUpdateTimeMillis) / 1000.0f;
	}
}
