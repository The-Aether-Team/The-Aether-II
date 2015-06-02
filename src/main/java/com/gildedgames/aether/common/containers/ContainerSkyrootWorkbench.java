package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerSkyrootWorkbench extends ContainerWorkbench
{
	private World world;

	private BlockPos pos;

	public ContainerSkyrootWorkbench(InventoryPlayer inventory, World world, BlockPos pos)
	{
		super(inventory, world, pos);

		this.world = world;
		this.pos = pos;
	}

	public boolean canInteractWith(EntityPlayer player)
	{
		return this.world.getBlockState(this.pos).getBlock() == BlocksAether.skyroot_crafting_table &&
				player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}
}
