package com.gildedgames.aether.api.util;

import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nullable;

public class BlockAccessChunkPrimer implements IBlockAccessExtended
{
	private final World world;

	private final ChunkPrimer primer;

	public BlockAccessChunkPrimer(final World world, final ChunkPrimer primer)
	{
		this.world = world;
		this.primer = primer;
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
		return true;
	}

	@Override
	public boolean canAccess(final int x, final int z)
	{
		return true;
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
		int xDif = pos.getX() % 16;
		int zDif = pos.getZ() % 16;

		if (xDif < 0)
		{
			xDif = 16 - Math.abs(xDif);
		}

		if (zDif < 0)
		{
			zDif = 16 - Math.abs(zDif);
		}

		this.primer.setBlockState(xDif, pos.getY(), zDif, Blocks.AIR.getDefaultState());
	}

	@Override
	public boolean setBlockState(final BlockPos pos, final IBlockState state)
	{
		int xDif = pos.getX() % 16;
		int zDif = pos.getZ() % 16;

		if (xDif < 0)
		{
			xDif = 16 - Math.abs(xDif);
		}

		if (zDif < 0)
		{
			zDif = 16 - Math.abs(zDif);
		}

		this.primer.setBlockState(xDif, pos.getY(), zDif, state);

		return true;
	}

	@Override
	public boolean setBlockState(final BlockPos pos, final IBlockState state, final int flags)
	{
		this.primer.setBlockState(Math.abs(pos.getX() % 16), pos.getY(), Math.abs(pos.getZ() % 16), state);

		return true;
	}

	@Override
	public void setTileEntity(final BlockPos pos, final TileEntity tileEntity)
	{

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
		int xDif = pos.getX() % 16;
		int zDif = pos.getZ() % 16;

		if (xDif < 0)
		{
			xDif = 16 - Math.abs(xDif);
		}

		if (zDif < 0)
		{
			zDif = 16 - Math.abs(zDif);
		}

		return this.primer.getBlockState(xDif, pos.getY(), zDif);
	}

	@Override
	public boolean isAirBlock(final BlockPos pos)
	{
		return this.getBlockState(pos) != Blocks.AIR.getDefaultState();
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
