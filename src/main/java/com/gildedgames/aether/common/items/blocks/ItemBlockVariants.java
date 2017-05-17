package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockVariants extends ItemBlock
{
	private final IBlockVariants blockWithVariants;

	public ItemBlockVariants(Block block)
	{
		super(block);

		this.blockWithVariants = (IBlockVariants) block;

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
		return super.getUnlocalizedName() + "." + this.blockWithVariants.getVariantName(stack);
	}

}
