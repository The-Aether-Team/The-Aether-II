package com.gildedgames.aether.api.util;

import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public class BlockAccessVirtualDataManager implements IBlockAccessExtended
{
	private final World world;

	public BlockAccessVirtualDataManager(final World world)
	{
		this.world = world;
	}

	@Nullable
	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public boolean canAccess(final BlockPos pos)
	{
		return this.world.isBlockLoaded(pos);
	}

	@Override
	public boolean canAccess(final int x, final int z)
	{
		return this.canAccess(new BlockPos(x, 0, z));
	}

	@Override
	public BlockPos getTopPos(final BlockPos pos)
	{
		return this.world.getTopSolidOrLiquidBlock(pos);
	}

	@Override
	public int getTopY(final int x, final int z)
	{
		return this.world.getHeight(x, z);
	}

	@Override
	public void setBlockToAir(final BlockPos pos)
	{
		this.world.setBlockToAir(pos);
	}

	@Override
	public boolean setBlockState(final BlockPos pos, final IBlockState state)
	{
		return this.world.setBlockState(pos, state);
	}

	@Override
	public boolean setBlockState(final BlockPos pos, final IBlockState state, final int flags)
	{
		return this.world.setBlockState(pos, state, flags);
	}

	@Override
	public void setTileEntity(final BlockPos pos, final TileEntity tileEntity)
	{
		this.world.setTileEntity(pos, tileEntity);
	}

	@Nullable
	@Override
	public TileEntity getTileEntity(final BlockPos pos)
	{
		return this.world.getTileEntity(pos);
	}

	@Override
	public int getCombinedLight(final BlockPos pos, final int lightValue)
	{
		return this.world.getCombinedLight(pos, lightValue);
	}

	@Override
	public IBlockState getBlockState(final BlockPos pos)
	{
		return this.world.getBlockState(pos);
	}

	@Override
	public boolean isAirBlock(final BlockPos pos)
	{
		return this.world.isAirBlock(pos);
	}

	@Override
	public Biome getBiome(final BlockPos pos)
	{
		return this.world.getBiome(pos);
	}

	@Override
	public int getStrongPower(final BlockPos pos, final EnumFacing direction)
	{
		return this.world.getStrongPower(pos, direction);
	}

	@Override
	public WorldType getWorldType()
	{
		return this.world.getWorldType();
	}

	@Override
	public boolean isSideSolid(final BlockPos pos, final EnumFacing side, final boolean _default)
	{
		return this.world.isSideSolid(pos, side, _default);
	}
}
