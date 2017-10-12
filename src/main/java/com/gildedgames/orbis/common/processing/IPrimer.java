package com.gildedgames.orbis.common.processing;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public interface IPrimer
{

	void setBlockState(BlockPos pos, IBlockState state);

	void setTileEntity(BlockPos pos, TileEntity tileEntity);

}
