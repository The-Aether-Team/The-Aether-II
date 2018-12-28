package com.gildedgames.aether.client.gui.overlays;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SwetOverlay implements IOverlay
{

	private final Minecraft mc = Minecraft.getMinecraft();

	public SwetOverlay()
	{

	}

	@Override
	public boolean isEnabled()
	{
		return this.mc.world != null;
	}

	@Override
	public void draw()
	{
		boolean hasBlue = false, hasGreen = false, hasPurple = false;

		final PlayerAether player = PlayerAether.getPlayer(this.mc.player);

		for (final EntitySwet swet : player.getSwetTracker().getLatchedSwets())
		{
			switch (swet.getType())
			{
				case BLUE:
					hasBlue = true;
					break;
				case GREEN:
					hasGreen = true;
					break;
				case PURPLE:
					hasPurple = true;
					break;
				default:
					break;
			}
		}

		if (hasBlue)
		{
			this.drawSwet(EntitySwet.Type.BLUE);
		}

		if (hasGreen)
		{
			this.drawSwet(EntitySwet.Type.GREEN);
		}

		if (hasPurple)
		{
			this.drawSwet(EntitySwet.Type.PURPLE);
		}
	}

	private void drawSwet(final EntitySwet.Type type)
	{
		final ScaledResolution scaledRes = new ScaledResolution(this.mc);

		final int y = scaledRes.getScaledHeight() - 64;

		this.drawCorner(0, y, type.left1, type.left2, 2.0F);
		this.drawCorner((int) (scaledRes.getScaledWidth() - (64 * 2.0F)), y, type.right1, type.right2, 2.0F);
	}

	private void drawCorner(final int x, final int y, final ResourceLocation corner1, final ResourceLocation corner2, final float scale)
	{
		final float startRange = 0.1F;
		final float endRange = 0.7F;

		final float oscilationRange = (endRange - startRange) / 2;
		final float oscilationOffset = oscilationRange + startRange;

		this.drawSingle(x, y, corner1, oscilationOffset + (float) Math.sin(System.currentTimeMillis() / 200.0) * oscilationRange, scale);
		this.drawSingle(x, y, corner2, oscilationOffset + (float) Math.sin((System.currentTimeMillis() / 200.0) + 60.0) * oscilationRange, scale);
	}

	private void drawSingle(final int x, final int y, final ResourceLocation texture, final float alpha, final float scale)
	{
		GlStateManager.pushMatrix();

		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();

		GlStateManager.depthMask(true);
		GlStateManager.disableLighting();

		final double width = 64;
		final double height = 64;

		GlStateManager.translate(x, y, 0);

		GlStateManager.translate(0, -((height * scale) - height), 0);

		GlStateManager.scale(scale, scale, 0);

		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

		this.mc.getTextureManager().bindTexture(texture);

		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 64, 64, 64, 64);

		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.popMatrix();
	}

}
