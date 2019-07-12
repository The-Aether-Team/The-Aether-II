package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.common.blocks.IBlockMultiName;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class ItemBlockMultiName extends BlockItem
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
	public String getTranslationKey(ItemStack stack)
	{
		return super.getTranslationKey() + "." + this.blockWithVariants.getTranslationKey(stack);
	}

}
