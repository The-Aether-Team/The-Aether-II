package com.gildedgames.aether.common.capabilities.world.chunk;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import java.util.BitSet;

public class PlacementFlagCapability implements IPlacementFlagCapability
{
	private static final int CHUNK_SIZE = 16 * 256 * 16;

	private BitSet bits = new BitSet(CHUNK_SIZE);

	@Override
	public void markModified(BlockPos pos)
	{
		this.set(pos, true);
	}

	@Override
	public void clearModified(BlockPos pos)
	{
		this.set(pos, false);
	}

	private void set(BlockPos pos, boolean value)
	{
		this.bits.set(this.getIndexFromCoordinate(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15), value);
	}

	@Override
	public boolean isModified(BlockPos pos)
	{
		return this.bits.get(this.getIndexFromCoordinate(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15));
	}

	@Override
	public void write(CompoundNBT output)
	{
		output.setByteArray("bits", this.bits.toByteArray());
	}

	@Override
	public void read(CompoundNBT input)
	{
		if (input.contains("bits"))
		{
			this.bits = BitSet.valueOf(input.getByteArray("bits"));
		}
	}

	private int getIndexFromCoordinate(int x, int y, int z)
	{
		return (x * 256 * 16 + y * 16 + z);
	}

	public static class Storage implements Capability.IStorage<IPlacementFlagCapability>
	{
		@Override
		public INBT writeNBT(Capability<IPlacementFlagCapability> capability, IPlacementFlagCapability instance, Direction side)
		{
			CompoundNBT out = new CompoundNBT();
			instance.write(out);

			return out;
		}

		@Override
		public void readNBT(Capability<IPlacementFlagCapability> capability, IPlacementFlagCapability instance, Direction side,
				INBT nbt)
		{
			CompoundNBT input = (CompoundNBT) nbt;
			instance.read(input);
		}
	}
}
