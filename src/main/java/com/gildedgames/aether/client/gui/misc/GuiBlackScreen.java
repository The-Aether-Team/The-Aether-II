package com.gildedgames.aether.client.gui.misc;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class GuiBlackScreen extends GuiScreen
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
