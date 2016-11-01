package com.gildedgames.aether.client.ui.util;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.event.view.MouseEventGuiFocus;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.ButtonState;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseButton;
import com.gildedgames.aether.client.ui.input.MouseInput;

public class Button extends GuiFrame
{

	protected final GuiFrame defaultState, hoveredState, clickedState, disabledState;

	public Button(Rect dim, TextureElement texture)
	{
		this(dim, texture, texture.clone(), texture.clone());
	}

	public Button(Rect dim, TextureElement defaultState, TextureElement hoveredState, TextureElement clickedState)
	{
		this(dim, defaultState, hoveredState, clickedState, null);
	}

	public Button(Rect dim, TextureElement defaultState, TextureElement hoveredState, TextureElement clickedState, TextureElement disabledState)
	{
		super(dim);

		this.defaultState = defaultState;
		this.hoveredState = hoveredState;
		this.clickedState = clickedState;

		this.disabledState = disabledState;
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.defaultState.setVisible(false);
		this.hoveredState.setVisible(false);
		this.clickedState.setVisible(false);

		this.defaultState.dim().mod().center(false).resetPos().flush();
		this.hoveredState.dim().mod().center(false).resetPos().flush();
		this.clickedState.dim().mod().center(false).resetPos().flush();

		this.clickedState.events().set("clickEvent", new MouseEventGuiFocus(new MouseInput(MouseButton.LEFT, ButtonState.PRESS)));

		this.content().set("hoveredState", this.hoveredState);
		this.content().set("clickedState", this.clickedState);
		this.content().set("defaultState", this.defaultState);

		if (this.disabledState != null)
		{
			this.disabledState.dim().mod().center(false).resetPos().flush();

			if (!this.isEnabled())
			{
				this.disabledState.setVisible(true);
			}
			else
			{
				this.disabledState.setVisible(false);
			}

			this.content().set("disabledState", this.disabledState);
		}

		this.defaultState.setVisible(true);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		if (this.disabledState != null)
		{
			if (!this.isEnabled())
			{
				this.disabledState.setVisible(true);
			}
			else
			{
				this.disabledState.setVisible(false);
			}
		}

		if (input.isHovered(this.dim()))
		{
			this.defaultState.setVisible(false);
			this.hoveredState.setVisible(true);
		}
		else
		{
			this.defaultState.setVisible(true);
			this.hoveredState.setVisible(false);
		}
	}

}
