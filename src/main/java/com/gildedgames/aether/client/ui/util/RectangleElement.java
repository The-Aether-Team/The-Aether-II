package com.gildedgames.aether.client.ui.util;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.DrawingData;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;

public class RectangleElement extends GuiFrame
{

	protected DrawingData startColor, endColor;

	public RectangleElement(Rect rect, DrawingData data)
	{
		this(rect, data, null);
	}

	public RectangleElement(Rect rect, DrawingData startColor, DrawingData endColor)
	{
		super(rect);

		this.startColor = startColor;
		this.endColor = endColor;
	}

	public DrawingData getDrawingData()
	{
		return this.startColor;
	}

	public void setDrawingData(DrawingData data)
	{
		this.startColor = data;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		if (this.endColor == null)
		{
			graphics.drawRectangle(this.dim(), this.startColor);
		}
		else
		{
			graphics.drawGradientRectangle(this.dim(), this.startColor, this.endColor);
		}
	}

}
