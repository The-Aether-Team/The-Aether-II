package com.gildedgames.aether.common.entities.tiles.multiblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITileEntityMultiblock
{
	/**
	 * Called when the corresponding {@link ITileEntityMultiblock} is interacted with.
	 *
	 * @param player The player interacting with the {@link ITileEntityMultiblock}
	 */
	boolean onInteract(EntityPlayer player);

	/**
	 * Called when the corresponding {@link ITileEntityMultiblock} is destroyed.
	 */
	void onDestroyed();

	ItemStack getPickedStack(World world, BlockPos pos, IBlockState state);

}
