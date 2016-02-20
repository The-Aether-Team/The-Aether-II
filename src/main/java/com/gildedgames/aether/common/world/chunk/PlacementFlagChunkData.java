package com.gildedgames.aether.common.world.chunk;

import com.gildedgames.util.modules.chunk.common.hook.IChunkHook;
import net.minecraft.nbt.NBTTagCompound;

import java.util.BitSet;

public class PlacementFlagChunkData implements IChunkHook
{
	private BitSet bits;

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

	@Override
	public void write(NBTTagCompound output)
	{
		output.setByteArray("placeFlags", this.bits.toByteArray());
	}

	@Override
	public void read(NBTTagCompound input)
	{
		if (input.hasKey("placeFlags"))
		{
			this.bits = BitSet.valueOf(input.getByteArray("placeFlags"));
		}
		else
		{
			this.bits = new BitSet(65535);
		}
	}
}
