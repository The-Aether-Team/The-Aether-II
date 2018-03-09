package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.orbis.client.gui.util.GuiFrameNoContainer;
import net.minecraft.client.renderer.GlStateManager;

public class GuiBlackScreen extends GuiFrameNoContainer
{
	@Override
	public void init()
	{

	}

	@Override
	public void draw()
	{
		GlStateManager.pushMatrix();

		GlStateManager.disableDepth();

		this.drawGradientRect(0, 0, this.width, this.height, 0xFF000000, 0xFF000000);

		GlStateManager.enableDepth();

		GlStateManager.popMatrix();
	}

}
