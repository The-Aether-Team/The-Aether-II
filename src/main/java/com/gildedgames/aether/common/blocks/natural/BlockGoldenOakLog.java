package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockGoldenOakLog extends BlockAetherLog
{
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		if (player.getActiveItemStack() != null)
		{
			Item heldItem = player.getActiveItemStack().getItem();

			if (heldItem instanceof ItemTool)
			{
				Item.ToolMaterial material = ((ItemTool) heldItem).getToolMaterial();

				if (material.getHarvestLevel() >= 2)
				{
					this.dropGoldenAmber(world, pos, world.rand);
				}
			}
		}

		super.harvestBlock(world, player, pos, state, te, stack);
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
