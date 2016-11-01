package com.gildedgames.aether.client.ui.util;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.DrawingData;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.graphics.Sprite;
import com.gildedgames.aether.client.ui.input.InputProvider;

public class TextureElement extends GuiFrame
{

	protected final Sprite sprite;

	protected DrawingData data;

	public TextureElement(Sprite sprite, Rect dim)
	{
		this(sprite, dim, new DrawingData());
	}

	public TextureElement(Sprite sprite, Rect dim, DrawingData data)
	{
		super(dim);

		this.sprite = sprite;
		this.data = data;
	}

	public DrawingData getDrawingData()
	{
		return this.data;
	}

	public TextureElement drawingData(DrawingData data)
	{
		this.data = data;

		return this;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		graphics.drawSprite(this.sprite, this.dim(), this.data);
	}

	public TextureElement clone()
	{
		return new TextureElement(this.sprite, this.dim(), this.data);
	}

}
