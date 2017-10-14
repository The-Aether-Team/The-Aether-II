package com.gildedgames.orbis.common.block;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.orbis.common.OrbisCore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockData implements NBT
{
	private IBlockState blockState;

	private TileEntity tileEntity;

	public BlockData()
	{

	}

	public BlockData(final IBlockState blockState)
	{
		this();
		this.blockState = blockState;
	}

	public BlockData(final IBlockState blockState, final TileEntity tileEntity)
	{
		this(blockState);
		this.tileEntity = tileEntity;
	}

	public BlockData(final BlockData block)
	{
		this.blockState = block.blockState;
		this.tileEntity = block.tileEntity;
	}

	public BlockData getDataFrom(final BlockPos pos, final World world)
	{
		this.blockState = world.getBlockState(pos);
		this.tileEntity = world.getTileEntity(pos);

		return this;
	}

	public IBlockState getBlockState()
	{
		return this.blockState;
	}

	public TileEntity getTileEntity()
	{
		return this.tileEntity;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setInteger("id", OrbisCore.getRegistrar().getStateId(this.blockState));

		final boolean hasTileEntity = this.tileEntity != null;
		tag.setBoolean("hasTileEntity", hasTileEntity);

		if (hasTileEntity)
		{
			final NBTTagCompound newTag = new NBTTagCompound();

			this.tileEntity.writeToNBT(newTag);

			tag.setTag("tileEntity", newTag);
		}
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.blockState = OrbisCore.getRegistrar().getStateFromId(tag.getInteger("id"));

		final boolean hasTileEntity = tag.getBoolean("hasTileEntity");

		if (hasTileEntity)
		{
			// TODO: Feed it world reference
			this.tileEntity = TileEntity.create(null, tag.getCompoundTag("tileEntity"));
		}
	}

	public boolean isAir()
	{
		return this.blockState.getBlock().getMaterial(this.blockState) == Material.AIR;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}

		if (obj instanceof BlockData)
		{
			final BlockData block = (BlockData) obj;
			return this.blockState.equals(block.blockState) && (this.tileEntity == null && block.tileEntity == null
					|| this.tileEntity != null && this.tileEntity.equals(block.tileEntity));
		}

		return false;
	}

}
