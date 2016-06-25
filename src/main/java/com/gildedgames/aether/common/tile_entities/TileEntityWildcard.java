package com.gildedgames.aether.common.tile_entities;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import com.gildedgames.aether.common.util.BlockPosUtil;

public class TileEntityWildcard extends TileEntitySchematicBlock
{

	@Override
	public void onSchematicGeneration()
	{
		int contentSize = 0;
		
		for (ItemStack stack : this.contents)
		{
			if (stack != null && stack.getItem() instanceof ItemBlock)
			{
				contentSize += stack.stackSize;
			}
		}
		
		if (contentSize == 0)
		{
			this.getWorld().setBlockToAir(this.getPos());
			this.getWorld().scheduleUpdate(this.getPos(), this.blockType, 0);
			
			return;
		}
		
		int currentIndex = this.getWorld().rand.nextInt(contentSize);
		ItemStack chosenStack = null;
	
		for (ItemStack stack : this.contents)
		{
			if (stack != null && stack.getItem() instanceof ItemBlock)
			{
				if (stack.stackSize > currentIndex)
				{
					chosenStack = stack;
					break;
				}
				else
				{
					currentIndex -= stack.stackSize;
				}
			}
		}
		
		ItemBlock itemBlock = (ItemBlock)chosenStack.getItem();
		
		Block block = itemBlock.getBlock();
		int damage = chosenStack.getItemDamage();
		
		this.getWorld().setBlockState(this.getPos(), block.getStateFromMeta(damage));
		BlockPosUtil.setTileEntityNBT(this.getWorld(), this.getPos(), chosenStack);
		this.getWorld().scheduleUpdate(this.getPos(), this.blockType, 0);
	}
	
	@Override
	public void onMarkedForGeneration(BlockPos start, BlockPos end)
	{
		
	}
	
	@Override
	public boolean shouldInvalidateTEOnGen()
	{
		return true;
	}

}
