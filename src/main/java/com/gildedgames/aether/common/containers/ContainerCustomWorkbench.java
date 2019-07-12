package com.gildedgames.aether.common.containers;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerCustomWorkbench extends ContainerWorkbench
{
	private final World world;

	private final BlockPos pos;

	public ContainerCustomWorkbench(final PlayerInventory playerInventory, final World worldIn,
			final BlockPos posIn)
	{
		super(playerInventory, worldIn, posIn);

		this.world = worldIn;
		this.pos = posIn;
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean canInteractWith(final PlayerEntity playerIn)
	{
		if (!(this.world.getBlockState(this.pos).getBlock() instanceof BlockWorkbench))
		{
			return false;
		}
		else
		{
			return playerIn.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}
}
