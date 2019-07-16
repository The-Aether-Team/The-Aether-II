package com.gildedgames.aether.client.gui.util;

import com.gildedgames.aether.client.gui.dialog.GuiCoins;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class ToolTipCurrencyHelper
{
	private final List<ITextComponent> cachedText = Lists.newArrayList();

	private double lastValue;

	private static final ResourceLocation[] resourceMap = new ResourceLocation[] { GuiCoins.GILTAENI, GuiCoins.GILTAEN, GuiCoins.GILTAE, GuiCoins.GILT };

	public List<ITextComponent> getText(double value)
	{
		if (value != this.lastValue)
		{
			this.cachedText.clear();

			int[] brokenUp = PlayerCurrencyModule.breakUpCurrency((long) value);

			boolean newLine = false;

			StringBuilder curLine = new StringBuilder();

			for (int cur : brokenUp)
			{
				if (cur > 0)
				{
					curLine.append(newLine ? "    " : "   ").append(cur);

					if (newLine)
					{
						this.cachedText.add(new StringTextComponent(curLine.toString()));

						curLine = new StringBuilder();
					}

					newLine = !newLine;
				}
			}

			if ((curLine.length() > 0) && newLine)
			{
				this.cachedText.add(new StringTextComponent(curLine.toString()));
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
				fontRenderer = Minecraft.getInstance().fontRenderer;
			}

			TextureManager textureManager = Minecraft.getInstance().getTextureManager();
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
					GlStateManager.color4f(1, 1, 1, 1);

					textureManager.bindTexture(resourceMap[i]);
					AbstractGui.blit(x2, y2, 0, 0, 7, 7, 7, 7);

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
