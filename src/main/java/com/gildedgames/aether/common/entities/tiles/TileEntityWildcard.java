package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.orbis.lib.processing.IBlockAccessExtended;
import com.gildedgames.orbis.lib.util.mc.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class TileEntityWildcard extends TileEntitySchematicBlock
{

	@Override
	public void onSchematicGeneration(final IBlockAccessExtended blockAccess, final Random rand)
	{
		int contentSize = 0;

		for (final ItemStack stack : this.contents)
		{
			if (stack != null && (stack.getItem() instanceof ItemBlock || stack.getItem() == Items.STRING))
			{
				contentSize += stack.getCount();
			}
		}

		if (contentSize == 0)
		{
			blockAccess.setBlockToAir(this.getPos());

			if (blockAccess.getWorld() != null)
			{
				blockAccess.getWorld().scheduleUpdate(this.getPos(), BlocksAether.wildcard, 0);
			}

			return;
		}

		int currentIndex = rand.nextInt(contentSize);
		ItemStack chosenStack = ItemStack.EMPTY;

		for (final ItemStack stack : this.contents)
		{
			if (!stack.isEmpty())
			{
				if (stack.getItem() instanceof ItemBlock || stack.getItem() == Items.STRING)
				{
					if (stack.getCount() > currentIndex)
					{
						chosenStack = stack;

						break;
					}
					else
					{
						currentIndex -= stack.getCount();
					}
				}
			}
		}

		final Block block;
		int damage = 0;

		if (chosenStack.getItem() == Items.STRING)
		{
			block = Blocks.AIR;
		}
		else
		{
			final ItemBlock itemBlock = (ItemBlock) chosenStack.getItem();

			block = itemBlock.getBlock();
			damage = chosenStack.getItemDamage();
		}

		if (block == Blocks.AIR)
		{
			return;
		}

		blockAccess.setBlockState(this.getPos(), block.getStateFromMeta(damage));
		BlockUtil.setTileEntityNBT(blockAccess, this.getPos(), chosenStack);

		if (blockAccess.getWorld() != null)
		{
			blockAccess.getWorld().scheduleUpdate(this.getPos(), block, 0);
		}
	}

	@Override
	public boolean shouldInvalidateTEOnGen()
	{
		return true;
	}

}
