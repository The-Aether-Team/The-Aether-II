package com.gildedgames.aether.client.gui.dialog;

import com.google.common.collect.Lists;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class GuiTextBox extends GuiAbstractButton
{

	public boolean showBackdrop = false;

	public boolean bottomToTop = false;

	public boolean center;

	private ITextComponent text;

	private List<ITextComponent> cachedSplitText = Lists.newArrayList();

	public GuiTextBox(final int elementId, final int x, final int z, final int width, final int height)
	{
		super(elementId, x, z);

		this.width = width;
		this.height = height;
	}

	public ITextComponent getText()
	{
		return this.text;
	}

	public void setText(final ITextComponent text)
	{
		this.text = text;

		this.markForReInit();
	}

	public void setCentered(boolean center)
	{
		this.center = center;
	}

	public int getTextHeight(final FontRenderer fontRenderer)
	{
		if (!this.hasInit)
		{
			this.init(fontRenderer);
			this.hasInit = true;
		}

		final int splitCount = Math.min((this.width + 12 - 5) / fontRenderer.FONT_HEIGHT, this.cachedSplitText.size());

		return splitCount * fontRenderer.FONT_HEIGHT;
	}

	@Override
	public void init(final FontRenderer fontRenderer)
	{
		if (this.text != null)
		{
			this.cachedSplitText = GuiUtilRenderComponents.splitText(this.text, this.width - 6, fontRenderer, true, true);
		}
	}

	@Override
	public void playPressSound(final SoundHandler soundHandlerIn)
	{
		// NO-OP
	}

	@Override
	public void draw(final FontRenderer fontRenderer)
	{
		if (this.text != null)
		{
			final int splitCount = Math.min((this.width + 12 - 5) / fontRenderer.FONT_HEIGHT, this.cachedSplitText.size());

			if (this.showBackdrop)
			{
				if (this.bottomToTop)
				{
					AbstractGui.drawRect(this.x,
							this.y + this.height - (splitCount * fontRenderer.FONT_HEIGHT) - 10,
							this.x + this.width, this.y + this.height, Integer.MIN_VALUE);
				}
				else
				{
					AbstractGui.drawRect(this.x, this.y,
							this.x + this.width, this.y + this.height, Integer.MIN_VALUE);
				}
			}

			for (int index = 0; index < splitCount; ++index)
			{
				final ITextComponent text = this.cachedSplitText.get(index);
				float offset = 5;

				if (this.center)
				{
					offset = (this.width - fontRenderer.getStringWidth(text.getUnformattedText())) / 2f;
				}

				fontRenderer.drawStringWithShadow(text.getUnformattedText(),
						this.x + offset,
						this.y + (this.bottomToTop ? this.height - 5 : 5) + index * fontRenderer.FONT_HEIGHT - (this.bottomToTop ?
								(splitCount * fontRenderer.FONT_HEIGHT) : 0), 0xFFFFFF);
			}
		}
	}

}
