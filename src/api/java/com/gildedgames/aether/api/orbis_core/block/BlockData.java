package com.gildedgames.aether.api.orbis_core.block;

import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.util.NBT;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockData implements NBT
{
	private Block block;

	private IBlockState blockState;

	private TileEntity tileEntity;

	public BlockData()
	{

	}

	public BlockData(final Block block)
	{
		this();
		this.block = block;
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
		this.block = block.block;
		this.blockState = block.blockState;
		this.tileEntity = block.tileEntity;
	}

	public BlockData getDataFrom(final BlockPos pos, final World world)
	{
		this.blockState = world.getBlockState(pos);
		this.tileEntity = world.getTileEntity(pos);

		return this;
	}

	@Nullable
	public Block getBlock()
	{
		return this.block;
	}

	public IBlockState getBlockState()
	{
		if (this.block != null && this.blockState == null)
		{
			return this.block.getDefaultState();
		}

		return this.blockState;
	}

	public TileEntity getTileEntity()
	{
		return this.tileEntity;
	}

	public IBlockState getRotatedBlockState(final Rotation rotation)
	{
		return this.getBlockState().withRotation(rotation);
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setBoolean("noState", this.blockState == null);
		tag.setInteger("id", OrbisCore.getRegistrar().getStateId(this.getBlockState()));

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
		final boolean noState = tag.getBoolean("noState");

		if (noState)
		{
			this.block = OrbisCore.getRegistrar().getStateFromId(tag.getInteger("id")).getBlock();
		}
		else
		{
			this.blockState = OrbisCore.getRegistrar().getStateFromId(tag.getInteger("id"));
		}

		final boolean hasTileEntity = tag.getBoolean("hasTileEntity");

		if (hasTileEntity)
		{
			// TODO: Feed it world reference
			this.tileEntity = TileEntity.create(null, tag.getCompoundTag("tileEntity"));
		}
	}

	public boolean isVoid()
	{
		return this.getBlockState().getBlock() == Blocks.STRUCTURE_VOID;
	}

	public boolean isAir()
	{
		return this.getBlockState().getBlock().getMaterial(this.getBlockState()) == Material.AIR;
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
			return this.block == block.block && this.blockState.equals(block.blockState) && (this.tileEntity == null && block.tileEntity == null
					|| this.tileEntity != null && this.tileEntity.equals(block.tileEntity));
		}

		return false;
	}

}
