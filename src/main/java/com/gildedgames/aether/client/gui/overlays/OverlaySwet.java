package com.gildedgames.aether.client.gui.overlays;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerSwetTrackerModule;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public class OverlaySwet implements IOverlay
{

	private final Minecraft mc = Minecraft.getInstance();

	public OverlaySwet()
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

		for (final EntitySwet swet : player.getModule(PlayerSwetTrackerModule.class).getLatchedSwets())
		{
			switch (swet.getSwetType())
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
		final int y = this.mc.mainWindow.getScaledHeight() - 64;

		this.drawCorner(0, y, type.left1, type.left2, 2.0F);
		this.drawCorner((int) (this.mc.mainWindow.getScaledWidth() - (64 * 2.0F)), y, type.right1, type.right2, 2.0F);
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
		GlStateManager.disableAlphaTest();

		GlStateManager.depthMask(true);

		final float width = 64;
		final float height = 64;

		GlStateManager.translatef(x, y, 0);
		GlStateManager.translatef(0, -((height * scale) - height), 0);

		GlStateManager.scalef(scale, scale, 0);

		GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);

		this.mc.getTextureManager().bindTexture(texture);

		AbstractGui.blit(0, 0, 0, 0, 64, 64, 64, 64);

		GlStateManager.disableBlend();
		GlStateManager.enableAlphaTest();

		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.popMatrix();
	}

}
