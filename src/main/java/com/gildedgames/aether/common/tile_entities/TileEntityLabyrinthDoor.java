package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.dungeon.BlockLabyrinthDoor;
import com.gildedgames.aether.common.tile_entities.multiblock.TileEntityMultiblockController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityLabyrinthDoor extends TileEntityMultiblockController implements ITickable
{

	public TileEntityLabyrinthDoor()
	{
		super(BlocksAether.labyrinth_door);
	}

	public EnumFacing getFacing()
	{
		return this.worldObj.getBlockState(this.pos).getValue(BlockLabyrinthDoor.PROPERTY_FACING);
	}

	@Override
	public void update()
	{
	}

	@Override
	public void onInteract(EntityPlayer player)
	{

	}
}
