package com.gildedgames.aether.client.gui.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class FontRendererWrapper extends FontRenderer
{

	protected FontRenderer parent;

	public FontRendererWrapper(FontRenderer parent)
	{
		super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().renderEngine, false);

		this.parent = parent;
	}

	@Override
	public int drawStringWithShadow(String text, float x, float y, int color)
	{
		return this.parent.drawString(text, x, y, color, true);
	}

	@Override
	public int drawString(String text, int x, int y, int color)
	{
		return this.parent.drawString(text, (float)x, (float)y, color, false);
	}

	@Override
	public int drawString(String text, float x, float y, int color, boolean dropShadow)
	{
		return this.parent.drawString(text, x, y, color, dropShadow);
	}

	@Override
	public int getStringWidth(String text)
	{
		return this.parent.getStringWidth(text);
	}

	@Override
	public int getCharWidth(char character)
	{
		return this.parent.getCharWidth(character);
	}

	@Override
	public String trimStringToWidth(String text, int width)
	{
		return this.parent.trimStringToWidth(text, width);
	}

	@Override
	public String trimStringToWidth(String text, int width, boolean reverse)
	{
		return this.parent.trimStringToWidth(text, width, reverse);
	}

	@Override
	public int splitStringWidth(String str, int maxLength)
	{
		return this.parent.splitStringWidth(str, maxLength);
	}

	@Override
	public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor)
	{
		this.parent.drawSplitString(str, x, y, wrapWidth, textColor);
	}

	@Override
	public List<String> listFormattedStringToWidth(String str, int wrapWidth)
	{
		return this.parent.listFormattedStringToWidth(str, wrapWidth);
	}

	@Override
	public int getColorCode(char character)
	{
		return this.getColorCode(character);
	}

}
