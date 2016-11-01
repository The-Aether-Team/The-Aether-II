package com.gildedgames.aether.client.ui.util.input;

import com.gildedgames.aether.client.ui.data.Pos2D;
import com.gildedgames.aether.client.ui.event.view.MouseEventGui;
import com.gildedgames.aether.client.ui.input.*;
import com.gildedgames.aether.client.ui.util.GuiCollection;
import com.gildedgames.aether.client.ui.util.factory.ContentFactory;
import com.gildedgames.aether.client.ui.util.transform.GuiPositioner;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonSet<T extends RadioButton> extends GuiCollection<T>
{

	private List<T> buttons = new ArrayList<>();

	public RadioButtonSet(GuiPositioner positioner, ContentFactory<T>... contentProviders)
	{
		super(positioner, contentProviders);
	}

	public RadioButtonSet(Pos2D pos, int width, GuiPositioner positioner, ContentFactory<T>... contentProviders)
	{
		super(pos, width, positioner, contentProviders);
	}

	@Override
	protected void onElementAdded(final T element)
	{
		super.onElementAdded(element);

		this.buttons.add(element);

		element.events().set("click", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{
			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				element.setOn(true);

				for (T rButton : RadioButtonSet.this.buttons)
				{
					if (rButton != element)
					{
						rButton.setOn(false);
					}
				}
			}

			@Override
			protected void onFalse(InputProvider input, MouseInputPool pool)
			{
			}

			@Override
			public void initEvent()
			{
			}
		});
	}

	/**
	 * Confirms the selection of the currently
	 * active radio button.
	 */
	public T getSelected()
	{
		for (T rButton : RadioButtonSet.this.buttons)
		{
			if (rButton.isOn())
			{
				return rButton;
			}
		}
		return null;
	}

}
