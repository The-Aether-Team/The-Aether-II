package com.gildedgames.aether.client.ui.util.input;

import java.util.ArrayList;
import java.util.List;

public abstract class DataInputBase<T> implements DataInput<T>
{

	private List<DataInputListener<T>> listeners = new ArrayList<>();

	@Override
	public final void setData(T data)
	{
		this.set(data);

		for (DataInputListener<T> listener : this.listeners)
		{
			listener.onChange(data);
		}
	}

	@Override
	public void addListener(DataInputListener<T> listener)
	{
		this.listeners.add(listener);

		listener.onInit();
	}

	@Override
	public boolean removeListener(DataInputListener<T> listener)
	{
		return this.listeners.remove(listener);
	}

	public abstract void set(T data);

}
