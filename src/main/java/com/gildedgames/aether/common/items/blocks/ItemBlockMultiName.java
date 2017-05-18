package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.common.blocks.IBlockMultiName;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMultiName extends ItemBlock
{
	private final IBlockMultiName blockWithVariants;

	public ItemBlockMultiName(Block block)
	{
		super(block);

		this.blockWithVariants = (IBlockMultiName) block;

		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "." + this.blockWithVariants.getUnlocalizedName(stack);
	}

}
