package com.gildedgames.orbis_core.processing;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPrimer
{

	World getWorld();

	void setBlockState(BlockPos pos, IBlockState state);

	void setTileEntity(BlockPos pos, TileEntity tileEntity);

}
