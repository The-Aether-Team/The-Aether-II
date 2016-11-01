package com.gildedgames.aether.client.ui.input;

public class MouseInput extends ButtonInput
{

	protected MouseButton button;

	protected MouseMotion motion;

	public MouseInput(MouseButton button)
	{
		this(button, ButtonState.PRESS);
	}

	public MouseInput(MouseButton button, ButtonState state)
	{
		this(button, state, MouseMotion.BOTH);
	}

	public MouseInput(MouseButton button, ButtonState state, MouseMotion motion)
	{
		super(state);

		this.button = button;
		this.motion = motion;
	}

	public MouseButton getButton()
	{
		return this.button;
	}

	public MouseMotion getMotion()
	{
		return this.motion;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof MouseInput)
		{
			MouseInput event = (MouseInput) obj;

			if (event.getButton() == this.getButton())
			{
				return super.equals(obj) && (event.getMotion() == this.getMotion() || event.getMotion() == MouseMotion.BOTH || this.getMotion() == MouseMotion.BOTH);
			}
		}

		return false;
	}

}
