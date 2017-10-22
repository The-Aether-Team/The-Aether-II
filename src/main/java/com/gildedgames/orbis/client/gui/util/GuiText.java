package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.orbis.client.gui.data.IText;
import com.gildedgames.orbis.client.util.rect.Rect;
import net.minecraft.client.Minecraft;

public class GuiText extends GuiFrame
{
	private IText text;

	public GuiText(final Rect rect, final IText text)
	{
		super(rect);
		this.setText(text);
	}

	public void setText(final IText component)
	{
		this.text = component;

		if (component == null)
		{
			this.dim().mod().width(0).flush();
		}
		else
		{
			this.dim().mod().scale(this.text.scale()).width(Minecraft.getMinecraft().fontRenderer.getStringWidth(this.text.component().getFormattedText()))
					.flush();
		}
	}

	@Override
	public void init()
	{

	}

	@Override
	public void draw()
	{
		if (this.text != null)
		{
			this.drawString(this.fontRenderer, this.text.component().getFormattedText(), (int) this.dim().x(), (int) this.dim().y(), 16777215);
		}
	}
}
