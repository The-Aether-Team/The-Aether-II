package com.gildedgames.aether.client.ui.event;

import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInput;
import com.gildedgames.aether.client.ui.input.MouseInputBehavior;
import com.gildedgames.aether.client.ui.input.MouseInputPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class MouseEvent extends GuiEvent
{

	private final Collection<MouseInput> eventList;

	private final List<MouseInputBehavior> behaviors = new ArrayList<>();

	protected int scrollDifference;

	public MouseEvent(MouseInput... events)
	{
		super();

		this.eventList = Arrays.asList(events);
	}

	public MouseEvent(List<MouseInput> events)
	{
		super();

		this.eventList = events;
	}

	@Override
	public void onMouseScroll(int scrollDifference, InputProvider input)
	{
		this.scrollDifference = scrollDifference;
	}

	public Collection<MouseInput> getEvents()
	{
		return this.eventList;
	}

	public Collection<MouseInputBehavior> getBehaviors()
	{
		return this.behaviors;
	}

	public void addBehavior(MouseInputBehavior behavior)
	{
		this.behaviors.add(behavior);
	}

	public boolean behaviorsMet(InputProvider input, MouseInputPool pool, int scrollDifference)
	{
		for (MouseInputBehavior behavior : this.behaviors)
		{
			if (!behavior.isMet(input, pool, scrollDifference))
			{
				return false;
			}
		}

		return true;
	}

}
