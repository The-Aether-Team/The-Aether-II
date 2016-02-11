package com.gildedgames.aether.common.blocks.natural;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.gildedgames.aether.common.items.ItemsAether;

public class BlockGoldenOakLog extends BlockAetherLog
{
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (player.getHeldItem() != null)
		{
			Item heldItem = player.getHeldItem().getItem();

			if (heldItem instanceof ItemTool)
			{
				Item.ToolMaterial material = ((ItemTool) heldItem).getToolMaterial();

				if (material.getHarvestLevel() > 2)
				{
					this.dropGoldenAmber(world, pos, world.rand);
				}
			}
		}

		super.onBlockHarvested(world, pos, state, player);
	}

	private void dropGoldenAmber(World world, BlockPos pos, Random random)
	{
		ItemStack stack = new ItemStack(ItemsAether.golden_amber, random.nextInt(3) + 1);

		EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		world.spawnEntityInWorld(entity);
	}
}
