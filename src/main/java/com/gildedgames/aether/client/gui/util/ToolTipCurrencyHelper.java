package com.gildedgames.aether.client.gui.util;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.gui.dialog.GuiCoins;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.gildedgames.aether.common.shop.ShopCurrencyGilt;
import com.google.common.collect.Lists;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.List;

public class ToolTipCurrencyHelper
{
	private final List<String> cachedText = Lists.newArrayList();

	private double lastValue;

	private static final ResourceLocation[] resourceMap = new ResourceLocation[] { GuiCoins.GILTAENI, GuiCoins.GILTAEN, GuiCoins.GILTAE, GuiCoins.GILT };

	public List<String> getText(double value)
	{
		if (value != this.lastValue)
		{
			this.cachedText.clear();

			int[] brokenUp = PlayerCurrencyModule.breakUpCurrency((long) value);
			boolean newLine = false;
			String curLine = "";

			for (int cur : brokenUp)
			{
				if (cur > 0)
				{
					curLine += (newLine ? "    " : "   ") + cur;

					if (newLine)
					{
						this.cachedText.add(curLine);
						curLine = "";
					}

					newLine = !newLine;
				}
			}

			if (!curLine.isEmpty() && newLine)
			{
				this.cachedText.add(curLine);
			}

			this.lastValue = value;
		}

		return this.cachedText;
	}

	public void render(FontRenderer fontRenderer, int x, int y, int height, double value)
	{
		if (value != 0)
		{
			if (fontRenderer == null)
			{
				fontRenderer = Minecraft.getMinecraft().fontRenderer;
			}

			TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
			int[] brokenUp = PlayerCurrencyModule.breakUpCurrency((long) value);

			final int size = this.cachedText.size();
			int x2 = x + 1, y2 = y + height - fontRenderer.FONT_HEIGHT * size + (size == 1 ? 1 : 0);
			boolean newLine = false;

			for (int i = 0; i < brokenUp.length; i++)
			{
				int currency = brokenUp[i];

				if (currency != 0)
				{
					GlStateManager.pushMatrix();
					GlStateManager.enableBlend();
					GlStateManager.disableRescaleNormal();
					GlStateManager.disableLighting();
					GlStateManager.color(1, 1, 1, 1);

					textureManager.bindTexture(resourceMap[i]);
					Gui.drawModalRectWithCustomSizedTexture(x2, y2, 0, 0, 7, 7, 7, 7);

					GlStateManager.popMatrix();

					x2 += fontRenderer.getStringWidth(String.valueOf(currency)) + 16;

					if (newLine)
					{
						y2 += fontRenderer.FONT_HEIGHT + 1;
						x2 = x + 1;
					}

					newLine = !newLine;
				}
			}
		}
	}
}
