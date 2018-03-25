package com.gildedgames.aether.island_gen;

import com.gildedgames.aether.common.registry.content.BiomesAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public class BlockAccessIsland implements IBlockAccess
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
}
