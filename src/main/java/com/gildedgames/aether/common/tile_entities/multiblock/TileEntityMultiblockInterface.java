package com.gildedgames.aether.common.tile_entities.multiblock;

import net.minecraft.entity.player.EntityPlayer;

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
}
