package com.gildedgames.aether.client.ui.input;

public class MouseDoubleClick implements MouseInputBehavior
{

	private MouseInput[] events;

	private MouseDoubleClick(MouseInput... events)
	{
		this.events = events;
	}

	@Override
	public boolean isMet(InputProvider input, MouseInputPool pool, int scrollDifference)
	{
		return pool.containsAll(this.events);
	}

	public static MouseDoubleClick with(MouseInput... events)
	{
		return new MouseDoubleClick(events);
	}

}
