package com.gildedgames.orbis.client.gui.data;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;

public class Text implements IText
{

	private static final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

	private final ITextComponent component;

	private final float scale;

	public Text(final ITextComponent component, final float scale)
	{
		this.component = component;
		this.scale = scale;
	}

	@Override
	public ITextComponent component()
	{
		return this.component;
	}

	@Override
	public float scaledHeight()
	{
		return (int) (this.height() * this.scale);
	}

	@Override
	public float scaledWidth()
	{
		return (int) (this.width() * this.scale);
	}

	@Override
	public float scale()
	{
		return this.scale;
	}

	@Override
	public float width()
	{
		return fontRenderer.getStringWidth(this.component.getFormattedText());
	}

	@Override
	public float height()
	{
		return fontRenderer.FONT_HEIGHT;
	}
}
