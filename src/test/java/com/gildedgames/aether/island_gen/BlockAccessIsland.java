package com.gildedgames.aether.island_gen;

import com.gildedgames.aether.common.registry.content.BiomesAether;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public class BlockAccessIsland implements IBlockAccessExtended
{
	@Nullable
	@Override
	public TileEntity getTileEntity(final BlockPos pos)
	{
		return null;
	}

	@Override
	public int getCombinedLight(final BlockPos pos, final int lightValue)
	{
		return 0;
	}

	@Override
	public IBlockState getBlockState(final BlockPos pos)
	{
		return null;
	}

	@Override
	public boolean isAirBlock(final BlockPos pos)
	{
		return false;
	}

	@Override
	public Biome getBiome(final BlockPos pos)
	{
		return BiomesAether.HIGHLANDS;
	}

	@Override
	public int getStrongPower(final BlockPos pos, final EnumFacing direction)
	{
		return 0;
	}

	@Override
	public WorldType getWorldType()
	{
		return null;
	}

	@Override
	public boolean isSideSolid(final BlockPos pos, final EnumFacing side, final boolean _default)
	{
		return false;
	}

	@Nullable
	@Override
	public World getWorld()
	{
		return null;
	}

	@Override
	public boolean canAccess(BlockPos blockPos)
	{
		return true;
	}

	@Override
	public boolean canAccess(int i, int i1)
	{
		return true;
	}

	@Override
	public boolean canAccess(int i, int i1, int i2, int i3, int i4, int i5)
	{
		return true;
	}

	@Override
	public BlockPos getTopPos(BlockPos blockPos)
	{
		return null;
	}

	@Override
	public int getTopY(int i, int i1)
	{
		return 0;
	}

	@Override
	public void setBlockToAir(BlockPos blockPos)
	{

	}

	@Override
	public boolean setBlockState(BlockPos blockPos, IBlockState iBlockState)
	{
		return false;
	}

	@Override
	public boolean setBlockState(BlockPos blockPos, IBlockState iBlockState, int i)
	{
		return false;
	}

	@Override
	public void setTileEntity(BlockPos blockPos, TileEntity tileEntity)
	{

	}

	@Override
	public void spawnEntity(Entity entity)
	{

	}

	@Override
	public Biome getServerBiome(BlockPos pos)
	{
		return BiomesAether.HIGHLANDS;
	}
}
