package com.gildedgames.aether.common.world.util;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChunkMap<T>
{
	private TLongObjectMap<T> map = new TLongObjectHashMap<>();

	private List<T> values = new ArrayList<>();

	public int size()
	{
		return this.map.size();
	}

	public boolean isEmpty()
	{
		return this.map.size() == 0;
	}

	public boolean containsKey(int chunkX, int chunkZ)
	{
		return this.map.containsKey(ChunkPos.asLong(chunkX, chunkZ));
	}

	public T get(int chunkX, int chunkZ)
	{
		return this.map.get(ChunkPos.asLong(chunkX, chunkZ));
	}

	public T put(int chunkX, int chunkZ, T value)
	{
		long hash = ChunkPos.asLong(chunkX, chunkZ);
		T old = this.map.get(hash);
		this.map.put(hash, value);
		this.values.add(value);
		return old;
	}

	public T remove(int chunkX, int chunkZ)
	{
		T ob = this.map.remove(ChunkPos.asLong(chunkX, chunkZ));
		this.values.remove(ob);
		return ob;
	}

	public Collection<T> getValues()
	{
		return this.values;
	}

}
