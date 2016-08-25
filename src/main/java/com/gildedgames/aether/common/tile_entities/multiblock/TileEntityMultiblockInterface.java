package com.gildedgames.aether.common.tile_entities.multiblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface TileEntityMultiblockInterface
{
	/**
	 * Called when the corresponding {@link TileEntityMultiblockInterface} is interacted with.
	 *
	 * @param player The player interacting with the {@link TileEntityMultiblockInterface}
	 */
	void onInteract(EntityPlayer player);

	/**
	 * Called when the corresponding {@link TileEntityMultiblockInterface} is destroyed.
	 */
	void onDestroyed();

	ItemStack getPickedStack(World world, BlockPos pos, IBlockState state);

}
