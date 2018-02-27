package com.gildedgames.aether.api.util;

import com.gildedgames.orbis.api.util.mc.NBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BlockPosDimension extends BlockPos.MutableBlockPos implements NBT
{

	private int dimensionId;

	@SuppressWarnings("unused")
	private BlockPosDimension()
	{
		
	}

	public BlockPosDimension(final BlockPos pos, final int dimensionId)
	{
		this(pos.getX(), pos.getY(), pos.getZ(), dimensionId);
	}

	public BlockPosDimension(final int x, final int y, final int z, final int dimensionId)
	{
		super(x, y, z);

		this.dimensionId = dimensionId;
	}

	public int getDim()
	{
		return this.dimensionId;
	}

	@Override
	public boolean equals(final Object object)
	{
		if (object == this)
		{
			return true;
		}

		if (object instanceof BlockPosDimension)
		{
			final BlockPosDimension dungeonPosition = (BlockPosDimension) object;
			return dungeonPosition.getX() == this.getX() && dungeonPosition.getY() == this.getY() && dungeonPosition.getZ() == this.getZ()
					&& dungeonPosition.dimensionId == this.dimensionId;
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.getX());
		builder.append(this.getY());
		builder.append(this.getZ());
		builder.append(this.dimensionId);

		return builder.toHashCode();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setInteger("x", this.getX());
		tag.setInteger("y", this.getY());
		tag.setInteger("z", this.getZ());
		tag.setInteger("d", this.dimensionId);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.x = tag.getInteger("x");
		this.y = tag.getInteger("y");
		this.z = tag.getInteger("z");
		this.dimensionId = tag.getInteger("d");
	}
}
