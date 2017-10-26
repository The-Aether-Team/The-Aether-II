package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.aether.api.util.IText;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Rect;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiTextBox extends GuiFrame
{

	private final boolean centerFormat;

	private IText[] text;

	public GuiTextBox(final Rect dim, final boolean centerFormat, final IText... text)
	{
		super(dim);

		this.text = text;
		this.centerFormat = centerFormat;
	}

	public void setText(final IText... text)
	{
		this.text = text;
	}

	@Override
	public void init()
	{
		int textHeight = 0;

		final float halfWidth = this.dim().width() / 2;

		for (final IText t : this.text)
		{
			if (t.component().getFormattedText().isEmpty())
			{
				continue;
			}

			final String[] strings = t.component().getFormattedText().split("/n");

			final List<String> stringList = new ArrayList<>(strings.length);
			Collections.addAll(stringList, strings);

			for (final String string : stringList)
			{
				final List<String> newStrings = this.fontRenderer.listFormattedStringToWidth(string, (int) (this.dim().width() / t.scale()));

				for (final String s : newStrings)
				{
					final GuiText textElement;

					if (this.centerFormat)
					{
						textElement = new GuiText(Dim2D.build().pos(halfWidth, textHeight).centerX(true).flush(),
								new Text(new TextComponentString(s), t.scale()));
					}
					else
					{
						textElement = new GuiText(Dim2D.build().pos(0, textHeight).flush(), new Text(new TextComponentString(s), t.scale()));
					}

					this.addChild(textElement);

					textHeight += 0.9f * t.scaledHeight();
				}
			}
		}

		this.dim().mod().height(textHeight).flush();
	}

}