package com.gildedgames.aether.client.ui.util.events.slots;

import com.gildedgames.aether.client.ui.common.GuiDecorator;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.input.InputProvider;

public class SlotStack<T> extends GuiDecorator<GuiFrame>
{

	private T data;

	public SlotStack(GuiFrame element, T data)
	{
		super(element);

		this.data = data;
	}

	public T getData()
	{
		return this.data;
	}

	@Override
	protected void preInitContent(InputProvider input)
	{

	}

	@Override
	protected void postInitContent(InputProvider input)
	{

	}

}
