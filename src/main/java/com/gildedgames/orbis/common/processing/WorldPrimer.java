package com.gildedgames.orbis.common.processing;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldPrimer implements IPrimer
{

	private final World world;

	public WorldPrimer(final World world)
	{
		this.world = world;
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public void setBlockState(final BlockPos pos, final IBlockState state)
	{
		this.world.setBlockState(pos, state);
	}

	@Override
	public void setTileEntity(final BlockPos pos, final TileEntity tileEntity)
	{
		this.world.setTileEntity(pos, tileEntity);
	}
}
