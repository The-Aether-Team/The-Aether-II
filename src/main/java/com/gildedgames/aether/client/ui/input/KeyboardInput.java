package com.gildedgames.aether.client.ui.input;

public class KeyboardInput extends ButtonInput
{

	protected int key;

	protected char character;

	public KeyboardInput(int key, char character, ButtonState state)
	{
		super(state);

		this.key = key;

		this.character = character;
	}

	public int getKey()
	{
		return this.key;
	}

	public char getChar()
	{
		return this.character;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof KeyboardInput)
		{
			KeyboardInput event = (KeyboardInput) obj;

			if (event.getKey() == this.getKey())
			{
				return super.equals(obj);
			}
		}

		return false;
	}

}
