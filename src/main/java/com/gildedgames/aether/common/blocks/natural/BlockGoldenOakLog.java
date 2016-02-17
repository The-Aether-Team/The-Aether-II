package com.gildedgames.aether.common.blocks.natural;

import java.util.Random;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.gildedgames.aether.common.items.ItemsAether;

public class BlockGoldenOakLog extends BlockAetherLog
{
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
	{
		if (player.getHeldItem() != null)
		{
			Item heldItem = player.getHeldItem().getItem();

			if (heldItem instanceof ItemTool)
			{
				Item.ToolMaterial material = ((ItemTool) heldItem).getToolMaterial();

				if (material.getHarvestLevel() >= 2)
				{
					this.dropGoldenAmber(world, pos, world.rand);
				}
			}
		}

		super.onBlockHarvested(world, pos, state, player);
	}

	private void dropGoldenAmber(World world, BlockPos pos, Random random)
	{
		Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.golden_amber, random.nextInt(3) + 1));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.skyroot_log);
	}
}
