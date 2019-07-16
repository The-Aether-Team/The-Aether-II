package com.gildedgames.aether.common.containers;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;

public class ContainerCustomWorkbench extends WorkbenchContainer
{
	// Duplicate field since parent has private access
	// TODO: Use an AT instead to access the field in WorkbenchContainer?
	private final IWorldPosCallable posCallable;

	public ContainerCustomWorkbench(int id, PlayerInventory playerInventory, IWorldPosCallable posCallable)
	{
		super(id, playerInventory, posCallable);

		this.posCallable = posCallable;
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return isWithinUsableDistance(this.posCallable, playerIn, Blocks.CRAFTING_TABLE);
	}
}
