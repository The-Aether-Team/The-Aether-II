package com.gildedgames.aether.common.world.chunk;

import java.util.BitSet;

public class PlacementFlagChunkData
{
	private final BitSet bits;

	public PlacementFlagChunkData()
	{
		this.bits = new BitSet(65535);
	}

	public PlacementFlagChunkData(byte[] data)
	{
		this.bits = BitSet.valueOf(data);
	}

	public boolean wasPlacedAt(int x, int y, int z)
	{
		return this.bits.get(this.getIndexFromCoordinate(x, y, z));
	}

	public void setPlaced(int x, int y, int z)
	{
		this.bits.set(this.getIndexFromCoordinate(x, y, z));
	}

	public void clearPlaced(int x, int y, int z)
	{
		this.bits.clear(this.getIndexFromCoordinate(x, y, z));
	}

	private int getIndexFromCoordinate(int x, int y, int z)
	{
		return (x * 256 * 16 + y * 16 + z);
	}

	public byte[] toBytes()
	{
		return this.bits.toByteArray();
	}
}
