package com.gildedgames.aether.client.renderer.entities.projectiles;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

public class RenderDart extends Render<EntityDart>
{
	private static final HashMap<ItemDartType, ResourceLocation> dartTextures = new HashMap<ItemDartType, ResourceLocation>();

	static
	{
		dartTextures.put(ItemDartType.GOLDEN, AetherCore.getResource("textures/entities/darts/golden_dart.png"));
		dartTextures.put(ItemDartType.ENCHANTED, AetherCore.getResource("textures/entities/darts/enchanted_dart.png"));
		dartTextures.put(ItemDartType.POISON, AetherCore.getResource("textures/entities/darts/poison_dart.png"));
		dartTextures.put(ItemDartType.PHOENIX, AetherCore.getResource("textures/entities/darts/golden_dart.png"));
	}

	public RenderDart(RenderManager renderManager)
	{
		super(renderManager);
	}

	@Override
	public void doRender(EntityDart dart, double posX, double posY, double posZ, float entityYaw, float partialTicks)
	{
		this.bindEntityTexture(dart);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, (float) posZ);
		GlStateManager.rotate(dart.prevRotationYaw + (dart.rotationYaw - dart.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(dart.prevRotationPitch + (dart.rotationPitch - dart.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		byte b0 = 0;
		float f = 0.0F;
		float f1 = 0.5F;
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

		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos(-7.0D, -2.0D, -2.0D).tex((double) f4, (double) f6).endVertex();
		worldrenderer.pos(-7.0D, -2.0D, 2.0D).tex((double) f5, (double) f6).endVertex();
		worldrenderer.pos(-7.0D, 2.0D, 2.0D).tex((double) f5, (double) f7).endVertex();
		worldrenderer.pos(-7.0D, 2.0D, -2.0D).tex((double) f4, (double) f7).endVertex();
		tessellator.draw();

		GL11.glNormal3f(-scale, 0.0F, 0.0F);

		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos(-7.0D, 2.0D, -2.0D).tex((double) f4, (double) f6).endVertex();
		worldrenderer.pos(-7.0D, 2.0D, 2.0D).tex((double) f5, (double) f6).endVertex();
		worldrenderer.pos(-7.0D, -2.0D, 2.0D).tex((double) f5, (double) f7).endVertex();
		worldrenderer.pos(-7.0D, -2.0D, -2.0D).tex((double) f4, (double) f7).endVertex();
		tessellator.draw();

		for (int i = 0; i < 4; ++i)
		{
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, scale);

			worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
			worldrenderer.pos(-8.0D, -2.0D, 0.0D).tex((double) f, (double) f2).endVertex();
			worldrenderer.pos(8.0D, -2.0D, 0.0D).tex((double) f1, (double) f2).endVertex();
			worldrenderer.pos(8.0D, 2.0D, 0.0D).tex((double) f1, (double) f3).endVertex();
			worldrenderer.pos(-8.0D, 2.0D, 0.0D).tex((double) f, (double) f3).endVertex();
			tessellator.draw();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

		super.doRender(dart, posX, posY, posZ, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDart dart)
	{
		return dartTextures.get(dart.getDartType());
	}
}
