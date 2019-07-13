package com.gildedgames.aether.common.entities.tiles.multiblock;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface ITileEntityMultiblock
{
	/**
	 * Called when the corresponding {@link ITileEntityMultiblock} is interacted with.
	 *
	 * @param player The player interacting with the {@link ITileEntityMultiblock}
	 */
	boolean onInteract(PlayerEntity player);

	/**
	 * Called when the corresponding {@link ITileEntityMultiblock} is destroyed.
	 */
	void onDestroyed();

	ItemStack getPickedStack(IBlockReader world, BlockPos pos, BlockState state);

}
