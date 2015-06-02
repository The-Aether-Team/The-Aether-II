package com.gildedgames.aether.common.items.itemblocks;

import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
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
		BlockVariant variant = this.variantBlock.getVariantFromStack(stack);

		String suffix = variant != null ? variant.getName() : "missingno";

		return super.getUnlocalizedName() + "." + suffix;
	}

}
