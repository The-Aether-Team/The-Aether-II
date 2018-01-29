package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityOutpostCampfire extends TileEntityMultiblockController implements ITickable
{

	public TileEntityOutpostCampfire()
	{
		super((BlockMultiController) BlocksAether.outpost_campfire, BlocksAether.multiblock_dummy_half);
	}

	@Override
	public void onInteract(final EntityPlayer player)
	{

	}

	@Override
	public ItemStack getPickedStack(final World world, final BlockPos pos, final IBlockState state)
	{
		return new ItemStack(BlocksAether.outpost_campfire);
	}

	@Override
	public void update()
	{

	}

}
