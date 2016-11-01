package com.gildedgames.aether.client.ui.input;

import org.lwjgl.input.Mouse;

public enum MouseButton
{

	NONE(-1), LEFT(0), RIGHT(1), MIDDLE(2);

	private final int index;

	MouseButton(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return this.index;
	}

	public static MouseButton fromIndex(int index)
	{
		for (MouseButton button : values())
		{
			if (button.getIndex() == index)
			{
				return button;
			}
		}

		return NONE;
	}

	public boolean isDown()
	{
		return Mouse.isButtonDown(this.index);
	}

}
