package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockQuicksoil extends Block
{
	public BlockQuicksoil()
	{
		super(Material.SAND);

		this.setHardness(1.5f);

		this.setSoundType(SoundType.SAND);
	}

	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos)
	{
		return PathNodeType.DANGER_OTHER;
	}
}
