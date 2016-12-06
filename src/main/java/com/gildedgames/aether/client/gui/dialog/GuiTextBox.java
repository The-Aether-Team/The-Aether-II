package com.gildedgames.aether.client.gui.dialog;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class GuiTextBox extends GuiElement
{

	private ITextComponent text;

	private List<ITextComponent> cachedSplitText = Lists.newArrayList();

	public GuiTextBox(int elementId, int x, int z, int width, int height)
	{
		super(elementId, x, z);

		this.width = width;
		this.height = height;
	}

	public ITextComponent getText()
	{
		return this.text;
	}

	public void setText(ITextComponent text)
	{
		this.text = text;

		this.markForReInit();
	}

	@Override
	public void init(FontRenderer fontRenderer)
	{
		if (this.text != null)
		{
			this.cachedSplitText = GuiUtilRenderComponents.splitText(this.text, this.width - 5, fontRenderer, true, true);
		}
	}

	@Override
	public void draw(FontRenderer fontRenderer)
	{
		Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, Integer.MIN_VALUE);

		if (this.text != null)
		{
			int splitCount = Math.min((this.width + 12 - 5) / fontRenderer.FONT_HEIGHT, this.cachedSplitText.size());

			for (int index = 0; index < splitCount; ++index)
			{
				ITextComponent text = this.cachedSplitText.get(index);
				fontRenderer.drawStringWithShadow(text.getUnformattedText(), this.xPosition + 5, this.yPosition + 5 + index * fontRenderer.FONT_HEIGHT, 0xFFFFFF);
			}
		}
	}

}
