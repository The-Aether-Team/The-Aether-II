package com.gildedgames.aether.client.ui.data;

import java.awt.*;

public class DrawingData
{

	protected final Color color;

	public DrawingData()
	{
		this(new Color(1.0F, 1.0F, 1.0F, 1.0F));
	}

	public DrawingData(Color color)
	{
		this.color = color;
	}

	public Color getColor()
	{
		return this.color;
	}

	public float getAlpha()
	{
		return this.color.getAlpha();
	}

	public float getRed()
	{
		return this.color.getRed();
	}

	public float getGreen()
	{
		return this.color.getGreen();
	}

	public float getBlue()
	{
		return this.color.getBlue();
	}

}
