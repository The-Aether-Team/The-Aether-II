package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSkyrootTrapDoor extends BlockTrapDoor
{
	public BlockSkyrootTrapDoor()
	{
		super(Material.WOOD);

		this.setSoundType(SoundType.WOOD);

		this.setHardness(3.0f);

		this.disableStats();
	}

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		if (state.getValue(OPEN))
		{
			IBlockState down = world.getBlockState(pos.down());
			if (down.getBlock() instanceof BlockLadder)
				return down.getValue(BlockLadder.FACING) == state.getValue(FACING);
		}

		return false;
	}
}
