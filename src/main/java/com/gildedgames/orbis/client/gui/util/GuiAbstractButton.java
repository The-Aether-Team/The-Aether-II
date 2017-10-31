package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.orbis.client.util.rect.Rect;
import com.gildedgames.orbis.common.util.InputHelper;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class GuiAbstractButton extends GuiFrame
{

	protected final GuiTexture defaultState, hoveredState, clickedState, disabledState;

	private final List<Runnable> onClickEvents = Lists.newArrayList();

	public GuiAbstractButton(final Rect dim, final GuiTexture texture)
	{
		this(dim, texture, texture.clone(), texture.clone());
	}

	public GuiAbstractButton(final Rect dim, final GuiTexture defaultState, final GuiTexture hoveredState, final GuiTexture clickedState)
	{
		this(dim, defaultState, hoveredState, clickedState, null);
	}

	public GuiAbstractButton(final Rect dim, final GuiTexture defaultState, final GuiTexture hoveredState, final GuiTexture clickedState,
			final GuiTexture disabledState)
	{
		super(dim);

		this.defaultState = defaultState;
		this.hoveredState = hoveredState;
		this.clickedState = clickedState;

		this.disabledState = disabledState;
	}

	public void addClickEvent(final Runnable event)
	{
		this.onClickEvents.add(event);
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (this.isEnabled() && InputHelper.isHovered(this.clickedState) && mouseButton == 0)
		{
			this.clickedState.setVisible(true);

			this.onClickEvents.forEach(Runnable::run);
		}
	}

	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state)
	{
		super.mouseReleased(mouseX, mouseY, state);

		this.clickedState.setVisible(false);
	}

	@Override
	public void init()
	{
		this.defaultState.setVisible(false);
		this.hoveredState.setVisible(false);
		this.clickedState.setVisible(false);

		this.defaultState.dim().mod().center(false).resetPos().flush();
		this.hoveredState.dim().mod().center(false).resetPos().flush();
		this.clickedState.dim().mod().center(false).resetPos().flush();

		this.addChild(this.hoveredState);
		this.addChild(this.clickedState);
		this.addChild(this.defaultState);

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

			this.addChild(this.disabledState);
		}

		this.defaultState.setVisible(true);
	}

	@Override
	public void draw()
	{
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

		if (InputHelper.isHovered(this.dim()))
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