package com.gildedgames.aether.client.gui.misc;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.Screen;

public class GuiBlackScreen extends Screen
{
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		GlStateManager.pushMatrix();

		GlStateManager.disableDepth();

		this.drawGradientRect(0, 0, this.width, this.height, 0xFF000000, 0xFF000000);

		GlStateManager.enableDepth();

		GlStateManager.popMatrix();
	}

}
