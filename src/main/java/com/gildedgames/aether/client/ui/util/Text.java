package com.gildedgames.aether.client.ui.util;

import com.gildedgames.aether.client.ui.data.DrawingData;

import java.awt.Color;

public class Text
{
	String text;

	float scale;

	DrawingData drawingData;

	Font font;

	public Text(String text, Color color, Font font)
	{
		this.drawingData = new DrawingData(color);
		this.text = text;
		this.scale = 1.0f;
		this.font = font;
	}

	public Text(String text, Color color, float scale, Font font)
	{
		this.drawingData = new DrawingData(color);
		this.text = text;
		this.scale = scale;
		this.font = font;
	}

	public String text()
	{
		return this.text;
	}

	public float scale()
	{
		return this.scale;
	}

	public DrawingData drawingData()
	{
		return this.drawingData;
	}

	public Font font()
	{
		return this.font;
	}

	public float width()
	{
		return this.font.getWidth(this.text);
	}

	public float height()
	{
		return this.font.getHeight(this.text);
	}

	public float scaledWidth()
	{
		return (int) (this.width() * this.scale);
	}

	public float scaledHeight()
	{
		return (int) (this.height() * this.scale);
	}
}
