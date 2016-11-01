package com.gildedgames.aether.client.ui.input;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class MouseInputPool implements Iterable<MouseInput>
{

	protected final MouseInput[] events;

	public MouseInputPool(MouseInput... events)
	{
		this.events = events;
	}

	public int size()
	{
		return this.events.length;
	}

	public MouseInput get(int index)
	{
		if (index >= this.events.length)
		{
			return null;
		}

		return this.events[index];
	}

	public boolean contains(Object o)
	{
		return indexOf(o) >= 0;
	}

	public boolean containsAll(Object[] a)
	{
		return this.containsAll(Arrays.asList(a));
	}

	public boolean containsAll(Collection<?> c)
	{
		return Arrays.asList(this.events).containsAll(c);
	}

	public int indexOf(Object o)
	{
		return ArrayUtils.indexOf(this.events, o);
	}

	public MouseInputPool getFrom(ButtonState state)
	{
		List<MouseInput> events = new ArrayList<>();

		for (MouseInput event : this)
		{
			if (event != null && event.getState() == state)
			{
				events.add(event);
			}
		}

		return new MouseInputPool(events.toArray(new MouseInput[events.size()]));
	}

	public MouseInputPool getFrom(MouseButton button)
	{
		List<MouseInput> events = new ArrayList<>();

		for (MouseInput event : this)
		{
			if (event != null && event.getButton() == button)
			{
				events.add(event);
			}
		}

		return new MouseInputPool(events.toArray(new MouseInput[events.size()]));
	}

	public MouseInputPool getFrom(MouseMotion motion)
	{
		List<MouseInput> events = new ArrayList<>();

		for (MouseInput event : this)
		{
			if (event != null && event.getMotion() == motion)
			{
				events.add(event);
			}
		}

		return new MouseInputPool(events.toArray(new MouseInput[events.size()]));
	}

	public boolean has(MouseMotion motion)
	{
		for (MouseInput event : this)
		{
			if (event != null && event.getMotion() == motion)
			{
				return true;
			}
		}

		return false;
	}

	public boolean has(MouseButton button)
	{
		for (MouseInput event : this)
		{
			if (event != null && event.getButton() == button)
			{
				return true;
			}
		}

		return false;
	}

	public boolean has(ButtonState state)
	{
		for (MouseInput event : this)
		{
			if (event != null && event.getState() == state)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public Iterator<MouseInput> iterator()
	{
		return Iterators.forArray(this.events);
	}

}
