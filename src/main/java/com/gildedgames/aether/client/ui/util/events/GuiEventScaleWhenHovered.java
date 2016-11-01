package com.gildedgames.aether.client.ui.util.events;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.event.GuiEvent;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;

public class GuiEventScaleWhenHovered extends GuiEvent<Gui>
{

	private final float selectedScale, originalScale;

	public GuiEventScaleWhenHovered(float originalScale, float selectedScale)
	{
		this.selectedScale = selectedScale;
		this.originalScale = originalScale;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		if (this.getGui().dim().intersects(input.getMouseX(), input.getMouseY()))
		{
			this.getGui().dim().mod().scale(this.selectedScale).flush();
		}
		else
		{
			this.getGui().dim().mod().scale(this.originalScale).flush();
		}
	}

	@Override
	public void initEvent()
	{

	}

}
