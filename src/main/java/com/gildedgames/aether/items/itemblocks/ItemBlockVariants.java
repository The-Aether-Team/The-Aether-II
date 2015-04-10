package com.gildedgames.aether.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.gildedgames.aether.blocks.util.IAetherBlockWithVariants;

public class ItemBlockVariants extends ItemBlock
{
	private final IAetherBlockWithVariants variantBlock;

	public ItemBlockVariants(Block block)
	{
		super(block);

		this.variantBlock = (IAetherBlockWithVariants) block;
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
		return super.getUnlocalizedName() + "." + this.variantBlock.getUnlocalizedNameFromStack(stack);
	}

}
