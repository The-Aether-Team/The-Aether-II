package com.gildedgames.aether.client.ui.event.view;

import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInput;
import com.gildedgames.aether.client.ui.input.MouseInputPool;

import java.util.List;

public class MouseEventGuiFocus extends MouseEventGui
{

	public MouseEventGuiFocus()
	{
		super();
	}

	public MouseEventGuiFocus(List<MouseInput> events)
	{
		super(events);
	}

	public MouseEventGuiFocus(MouseInput... events)
	{
		super(events);
	}

	@Override
	protected void onTrue(InputProvider input, MouseInputPool pool)
	{
		this.getGui().setVisible(true);
	}

	@Override
	protected void onFalse(InputProvider input, MouseInputPool pool)
	{
		this.getGui().setVisible(false);
	}

	@Override
	public void initEvent()
	{

	}

}
