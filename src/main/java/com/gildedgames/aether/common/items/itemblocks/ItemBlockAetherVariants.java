package com.gildedgames.aether.common.items.itemblocks;

import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithVariants;
import com.sun.deploy.model.Resource;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemBlockAetherVariants extends ItemBlock
{
	private final IAetherBlockWithVariants blockWithVariants;

	public ItemBlockAetherVariants(Block block, ResourceLocation location)
	{
		super(block);
		this.blockWithVariants = (IAetherBlockWithVariants) block;

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
		return super.getUnlocalizedName() + "." + this.blockWithVariants.getSubtypeUnlocalizedName(stack);
	}

}
