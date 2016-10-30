package com.gildedgames.aether.common.world.util;

import java.util.ArrayList;
import java.util.List;

public class VertexBuffer
{
	List<int[]> items;

	int count = 0;

	int capacity = 0;

	public VertexBuffer()
	{
		this.items = new ArrayList<>();
	}

	public int size()
	{
		return this.count;
	}

	public int[] get(int i)
	{
		return this.items.get(i);
	}

	void ensureCapacity()
	{
		if (this.count + 1 > this.capacity)
		{
			if (this.capacity == 0)
			{
				this.capacity = 4;
			}
			else
			{
				this.capacity = 2 * this.capacity;
			}
			for (int i = 0; i < this.capacity; i++)
			{
				if (i >= this.items.size())
				{
					this.items.add(null);
				}
			}
		}
	}

	public void add(int[] item)
	{
		this.ensureCapacity();
		this.items.set(this.count++, item);
	}

	public void clear()
	{
		this.count = 0;
	}
}
