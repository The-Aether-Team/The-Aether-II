package com.gildedgames.aether.client.renderer.world;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.ReflectionAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.lang.reflect.Method;

public class RenderWorldSkybox extends IRenderHandler
{
	private static final ResourceLocation TEXTURE_SKYBOX = AetherCore.getResource("textures/environment/skybox/skybox_clouds.png");

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		float farPlaneDistance = (float) (mc.gameSettings.renderDistanceChunks * 16);

		mc.entityRenderer.enableLightmap();

		GlStateManager.disableFog();
		GlStateManager.disableLighting();

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
		builder.pos(-0.5f * f, -0.5f * f, 0.5f * f).tex(0.5f, 0.75f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, -0.5f * f, 0.5f * f).tex(0.5f, 0.5f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, -0.5f * f, -0.5f * f).tex(0.25f, 0.5f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, -0.5f * f, -0.5f * f).tex(0.25f, 0.75f).lightmap(h, k).color(b, b, b, a).endVertex();

		builder.pos(0.5f * f, 0.5f * f, 0.5f * f).tex(0.5f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, 0.5f * f).tex(0.5f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, -0.5f * f).tex(0.25f, 0.0f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, -0.5f * f).tex(0.25f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();

		builder.pos(0.5f * f, -0.5f * f, -0.5f * f).tex(0.25f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, -0.5f * f).tex(0.25f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, -0.5f * f).tex(0.0f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, -0.5f * f, -0.5f * f).tex(0.0f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();


		builder.pos(-0.5f * f, -0.5f * f, 0.5f * f).tex(0.75f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, 0.5f * f).tex(0.75f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, 0.5f * f).tex(0.5f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, -0.5f * f, 0.5f * f).tex(0.5f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();

		builder.pos(-0.5f * f, -0.5f * f, -0.5f * f).tex(1.0f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, -0.5f * f).tex(1.0f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, 0.5f * f, 0.5f * f).tex(0.75f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(-0.5f * f, -0.5f * f, 0.5f * f).tex(0.75f, 0.50f).lightmap(h, k).color(b, b, b, a).endVertex();


		builder.pos(0.5f * f, -0.5f * f, 0.5f * f).tex(0.5f, 0.5f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, 0.5f * f).tex(0.5f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, 0.5f * f, -0.5f * f).tex(0.25f, 0.25f).lightmap(h, k).color(b, b, b, a).endVertex();
		builder.pos(0.5f * f, -0.5f * f, -0.5f * f).tex(0.25f, 0.5f).lightmap(h, k).color(b, b, b, a).endVertex();

		tessellator.draw();

		GlStateManager.disableBlend();

		GlStateManager.depthMask(true);

		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.loadIdentity();

		Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) mc.displayWidth / (float) mc.displayHeight, 0.05F, farPlaneDistance * MathHelper.SQRT_2);

		GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		GlStateManager.disableFog();
		GlStateManager.enableLighting();

		mc.entityRenderer.disableLightmap();

	}

	private float getFOVModifier(float partialTicks, boolean useFOVSetting)
	{
		Method method = ReflectionAether.getMethod(EntityRenderer.class, new Class[]{Float.TYPE, Boolean.TYPE}, ReflectionAether.GET_FOV_MODIFIER.getMappings());

		return (float) ReflectionAether.invokeMethod(method, Minecraft.getMinecraft().entityRenderer, partialTicks, useFOVSetting);
	}
}
