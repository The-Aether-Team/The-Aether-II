package com.gildedgames.aether.client.ui.input;

public class ButtonInput
{

	protected ButtonState state;

	public ButtonInput(ButtonState state)
	{
		this.state = state;
	}

	public ButtonState getState()
	{
		return this.state;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ButtonInput)
		{
			ButtonInput event = (ButtonInput) obj;

			if (event.getState() == this.getState())
			{
				return true;
			}
		}

		return false;
	}

}
