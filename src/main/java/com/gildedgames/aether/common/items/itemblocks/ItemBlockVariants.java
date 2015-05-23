package com.gildedgames.aether.common.items.itemblocks;

import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithVariants;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

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
