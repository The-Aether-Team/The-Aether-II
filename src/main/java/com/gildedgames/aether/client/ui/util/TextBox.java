package com.gildedgames.aether.client.ui.util;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.input.InputProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextBox extends GuiFrame
{

	private Text[] text;

	private boolean centerFormat;

	public TextBox(Rect dim, boolean centerFormat, Text... text)
	{
		super(dim);

		this.text = text;
		this.centerFormat = centerFormat;
	}

	public void setText(Text... text)
	{
		this.text = text;
	}

	@Override
	public void initContent(InputProvider input)
	{
		int i = 0;
		int textHeight = 0;

		float halfWidth = this.dim().width() / 2;

		for (Text t : this.text)
		{
			if (t.text == null || t.text.isEmpty())
			{
				continue;
			}

			final String[] strings = t.text.split("/n");

			final List<String> stringList = new ArrayList<>(strings.length);
			Collections.addAll(stringList, strings);

			for (final String string : stringList)
			{
				final List<String> newStrings = t.font.splitStringsIntoArea(string, (int) (this.dim().width() / t.scale));

				for (final String s : newStrings)
				{
					TextElement textElement;

					if (this.centerFormat)
					{
						textElement = new TextElement(new Text(s, t.drawingData.getColor(), t.scale, t.font), Dim2D.build().pos(halfWidth, textHeight).centerX(true).flush());
					}
					else
					{
						textElement = new TextElement(new Text(s, t.drawingData.getColor(), t.scale, t.font), Dim2D.build().pos(0, textHeight).flush());
					}

					this.content().set(String.valueOf(i), textElement);

					textHeight += 1.1f * t.scaledHeight();
					i++;
				}
			}
		}

		this.dim().mod().height(textHeight).flush();

		super.initContent(input);
	}

}
