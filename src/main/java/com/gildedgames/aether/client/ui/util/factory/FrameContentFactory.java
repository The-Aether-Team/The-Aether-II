package com.gildedgames.aether.client.ui.util.factory;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.google.common.collect.ImmutableMap;

import java.util.LinkedHashMap;

public class FrameContentFactory implements ContentFactory<Ui>
{

	private Factory<GuiFrame> frameFactory;

	private int buttonCount;

	public FrameContentFactory(Factory<GuiFrame> frameFactory, int buttonCount)
	{
		this.frameFactory = frameFactory;
		this.buttonCount = buttonCount;
	}

	@Override
	public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
	{
		LinkedHashMap<String, Ui> buttons = new LinkedHashMap<>();

		for (int count = 0; count < this.buttonCount; count++)
		{
			GuiFrame button = this.frameFactory.create();

			buttons.put("frame" + count, button);
		}

		return buttons;
	}

}
