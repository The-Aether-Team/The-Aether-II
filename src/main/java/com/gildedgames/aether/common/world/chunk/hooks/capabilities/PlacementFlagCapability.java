package com.gildedgames.aether.common.world.chunk.hooks.capabilities;

import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import java.util.BitSet;

public class PlacementFlagCapability implements IPlacementFlagCapability
{
	private static final int CHUNK_SIZE = 16 * 256 * 16;

	private BitSet bits = new BitSet(CHUNK_SIZE);

	@Override
	public void mark(BlockPos pos)
	{
		this.set(pos, true);
	}

	@Override
	public void clear(BlockPos pos)
	{
		this.set(pos, false);
	}

	@Override
	public void set(BlockPos pos, boolean value)
	{
		this.bits.set(this.getIndexFromCoordinate(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15), value);
	}

	@Override
	public boolean isMarked(BlockPos pos)
	{
		return this.bits.get(this.getIndexFromCoordinate(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15));
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setByteArray("bits", this.bits.toByteArray());
	}

	@Override
	public void read(NBTTagCompound input)
	{
		if (input.hasKey("bits"))
		{
			this.bits = BitSet.valueOf(input.getByteArray("bits"));
		}
	}

	public static class Storage implements Capability.IStorage<IPlacementFlagCapability>
	{
		@Override
		public NBTBase writeNBT(Capability<IPlacementFlagCapability> capability, IPlacementFlagCapability instance, EnumFacing side)
		{
			NBTTagCompound out = new NBTTagCompound();
			instance.write(out);

			return out;
		}

		@Override
		public void readNBT(Capability<IPlacementFlagCapability> capability, IPlacementFlagCapability instance, EnumFacing side,
				NBTBase nbt)
		{
			NBTTagCompound input = (NBTTagCompound) nbt;
			instance.read(input);
		}
	}

	private int getIndexFromCoordinate(int x, int y, int z)
	{
		return (x * 256 * 16 + y * 16 + z);
	}
}
