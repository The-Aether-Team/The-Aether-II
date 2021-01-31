package com.gildedgames.aether.client.gui.util;

import com.gildedgames.aether.client.gui.dialog.GuiCoins;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ToolTipCurrencyHelper
{
	private final List<String> cachedText = Lists.newArrayList();

	private double lastValue;

	private static final ResourceLocation[] resourceMap = new ResourceLocation[] { GuiCoins.GILT };

	public List<String> getText(double value)
	{
		if (value != this.lastValue)
		{
			this.cachedText.clear();

			this.lastValue = value;
		}

		return this.cachedText;
	}

	public void render(FontRenderer fontRenderer, int x, int y, int height, double value)
	{
		int amount = (int) value;

		if (amount >= 1)
		{
			if (fontRenderer == null)
			{
				fontRenderer = Minecraft.getMinecraft().fontRenderer;
			}

			TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();

			int x2 = x + 1, y2 = y + height - fontRenderer.FONT_HEIGHT + 1;

			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableLighting();
			GlStateManager.color(1, 1, 1, 1);

			textureManager.bindTexture(resourceMap[0]);
			Gui.drawModalRectWithCustomSizedTexture(x2, y2, 0, 0, 7, 7, 7, 7);
			fontRenderer.drawStringWithShadow(String.valueOf(amount), (float) (x2 + 9.8), y2, 11184810);

			GlStateManager.popMatrix();
		}
	}
}
