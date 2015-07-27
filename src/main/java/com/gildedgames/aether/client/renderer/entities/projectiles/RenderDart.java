package com.gildedgames.aether.client.renderer.entities.projectiles;

import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDart extends Render
{
	private static final ResourceLocation[] dartTextures = new ResourceLocation[ItemDart.DartType.values().length];

	static
	{
		dartTextures[ItemDart.DartType.GOLDEN.ordinal()] = new ResourceLocation("aether", "textures/entities/projectiles/dart/golden_dart.png");
		dartTextures[ItemDart.DartType.ENCHANTED.ordinal()] = new ResourceLocation("aether", "textures/entities/projectiles/dart/enchanted_dart.png");
		dartTextures[ItemDart.DartType.POISON.ordinal()] = new ResourceLocation("aether", "textures/entities/projectiles/dart/poison_dart.png");
		dartTextures[ItemDart.DartType.PHOENIX.ordinal()] = new ResourceLocation("aether", "textures/entities/projectiles/dart/golden_dart.png");
	}

	public RenderDart(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity entity, double posX, double posY, double posZ, float entityYaw, float partialTicks)
	{
		EntityDart dart = (EntityDart) entity;

		this.bindEntityTexture(dart);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, (float) posZ);
		GlStateManager.rotate(dart.prevRotationYaw + (dart.rotationYaw - dart.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(dart.prevRotationPitch + (dart.rotationPitch - dart.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		byte b0 = 0;
		float f2 = 0.0F;
		float f3 = 0.5F;
		float f4 = (float) (b0 * 10) / 32.0F;
		float f5 = (float) (5 + b0 * 10) / 32.0F;
		float f6 = 0.0F;
		float f7 = 0.15625F;
		float f8 = (float) (5 + b0 * 10) / 32.0F;
		float f9 = (float) (10 + b0 * 10) / 32.0F;
		float scale = 0.05625F;
		float shakeAmount = (float) dart.getDartShake() - partialTicks;

		GlStateManager.enableRescaleNormal();

		if (shakeAmount > 0.0F)
		{
			float shakeAngle = -MathHelper.sin(shakeAmount * 3.0F) * shakeAmount;
			GlStateManager.rotate(shakeAngle, 0.0F, 0.0F, 1.0F);
		}

		GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(scale, 0.0F, 0.0F);

		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double) f6, (double) f8);
		worldrenderer.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double) f7, (double) f8);
		worldrenderer.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double) f7, (double) f9);
		worldrenderer.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double) f6, (double) f9);
		tessellator.draw();

		GL11.glNormal3f(-scale, 0.0F, 0.0F);

		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double) f6, (double) f8);
		worldrenderer.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double) f7, (double) f8);
		worldrenderer.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double) f7, (double) f9);
		worldrenderer.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double) f6, (double) f9);
		tessellator.draw();

		for (int i = 0; i < 4; ++i)
		{
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, scale);

			worldrenderer.startDrawingQuads();
			worldrenderer.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double) f2, (double) f4);
			worldrenderer.addVertexWithUV(8.0D, -2.0D, 0.0D, (double) f3, (double) f4);
			worldrenderer.addVertexWithUV(8.0D, 2.0D, 0.0D, (double) f3, (double) f5);
			worldrenderer.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double) f2, (double) f5);
			tessellator.draw();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

		super.doRender(dart, posX, posY, posZ, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		EntityDart dart = (EntityDart) entity;

		return dartTextures[dart.getDartType().ordinal()];
	}
}
