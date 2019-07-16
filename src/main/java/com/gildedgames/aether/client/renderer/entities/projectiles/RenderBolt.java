package com.gildedgames.aether.client.renderer.entities.projectiles;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

public class RenderBolt extends EntityRenderer<EntityBolt>
{
	private static final HashMap<ItemBoltType, ResourceLocation> boltTextures = new HashMap<>();

	static
	{
		boltTextures.put(ItemBoltType.SKYROOT, AetherCore.getResource("textures/entities/bolts/skyroot_bolt.png"));
		boltTextures.put(ItemBoltType.HOLYSTONE, AetherCore.getResource("textures/entities/bolts/stone_bolt.png"));
		boltTextures.put(ItemBoltType.SCATTERGLASS, AetherCore.getResource("textures/entities/bolts/scatterglass_bolt.png"));
		boltTextures.put(ItemBoltType.ZANITE, AetherCore.getResource("textures/entities/bolts/zanite_bolt.png"));
		boltTextures.put(ItemBoltType.ARKENIUM, AetherCore.getResource("textures/entities/bolts/arkenium_bolt.png"));
		boltTextures.put(ItemBoltType.GRAVITITE, AetherCore.getResource("textures/entities/bolts/gravitite_bolt.png"));
	}

	public RenderBolt(final EntityRendererManager renderManager)
	{
		super(renderManager);
	}

	@Override
	public void doRender(final EntityBolt bolt, final double posX, final double posY, final double posZ, final float entityYaw, final float partialTicks)
	{
		this.bindTexture(boltTextures.get(bolt.getBoltType()));
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.translatef((float) posX, (float) posY, (float) posZ);
		GlStateManager.rotatef(bolt.prevRotationYaw + (bolt.rotationYaw - bolt.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(bolt.prevRotationPitch + (bolt.rotationPitch - bolt.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);

		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder renderer = tessellator.getBuffer();

		final int i = 0;
		final float f = 0.0F;
		final float f1 = 0.5F;
		final float f2 = (float) (i * 10) / 32.0F;
		final float f3 = (float) (5) / 32.0F;
		final float f4 = 0.0F;
		final float f5 = 0.15625F;
		final float f6 = (float) (5 + i * 10) / 32.0F;
		final float f7 = (float) (10 + i * 10) / 32.0F;
		final float f8 = 0.05625F;
		final float f9 = (float) bolt.arrowShake - partialTicks;

		GlStateManager.enableRescaleNormal();

		if (f9 > 0.0F)
		{
			final float f10 = -MathHelper.sin(f9 * 3.0F) * f9;

			GlStateManager.rotatef(f10, 0.0F, 0.0F, 1.0F);
		}

		GlStateManager.rotatef(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scalef(f8, f8, f8);
		GlStateManager.translatef(-4.0F, 0.0F, 0.0F);

		if (this.renderOutlines)
		{
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(bolt));
		}

		GL11.glNormal3f(f8, 0.0F, 0.0F);
		renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(-7.0D, -2.0D, -2.0D).tex((double) f4, (double) f6).endVertex();
		renderer.pos(-7.0D, -2.0D, 2.0D).tex((double) f5, (double) f6).endVertex();
		renderer.pos(-7.0D, 2.0D, 2.0D).tex((double) f5, (double) f7).endVertex();
		renderer.pos(-7.0D, 2.0D, -2.0D).tex((double) f4, (double) f7).endVertex();
		tessellator.draw();

		GL11.glNormal3f(-f8, 0.0F, 0.0F);
		renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(-7.0D, 2.0D, -2.0D).tex((double) f4, (double) f6).endVertex();
		renderer.pos(-7.0D, 2.0D, 2.0D).tex((double) f5, (double) f6).endVertex();
		renderer.pos(-7.0D, -2.0D, 2.0D).tex((double) f5, (double) f7).endVertex();
		renderer.pos(-7.0D, -2.0D, -2.0D).tex((double) f4, (double) f7).endVertex();

		tessellator.draw();

		for (int j = 0; j < 4; ++j)
		{
			GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, f8);

			renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
			renderer.pos(-8.0D, -2.0D, 0.0D).tex((double) f, (double) f2).endVertex();
			renderer.pos(8.0D, -2.0D, 0.0D).tex((double) f1, (double) f2).endVertex();
			renderer.pos(8.0D, 2.0D, 0.0D).tex((double) f1, (double) f3).endVertex();
			renderer.pos(-8.0D, 2.0D, 0.0D).tex((double) f, (double) f3).endVertex();

			tessellator.draw();
		}

		if (this.renderOutlines)
		{
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();

		super.doRender(bolt, posX, posY, posZ, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityBolt bolt)
	{
		return boltTextures.get(bolt.getBoltType());
	}
}
