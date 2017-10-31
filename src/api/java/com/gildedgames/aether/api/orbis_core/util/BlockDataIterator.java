package com.gildedgames.aether.api.orbis_core.util;

import com.gildedgames.aether.api.orbis_core.block.BlockData;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BlockDataIterator implements Iterator<BlockData>
{
	private final BlockData[] data;

	private int current = 0;

	public BlockDataIterator(final BlockData[] data)
	{
		this.data = data;
	}

	@Override
	public boolean hasNext()
	{
		if (this.current < this.data.length)
		{
			return true;
		}

		return false;
	}

	@Override
	public BlockData next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}

		return this.data[this.current++];
	}
}
