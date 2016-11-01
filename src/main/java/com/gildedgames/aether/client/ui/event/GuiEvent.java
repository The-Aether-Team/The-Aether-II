package com.gildedgames.aether.client.ui.event;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.common.GuiFrame;

public abstract class GuiEvent<E extends Gui> extends GuiFrame
{

	private E gui;

	public GuiEvent()
	{

	}

	public E getGui()
	{
		return this.gui;
	}

	public void setGui(E gui)
	{
		this.gui = gui;

		this.initEvent();
	}

	public abstract void initEvent();

}
