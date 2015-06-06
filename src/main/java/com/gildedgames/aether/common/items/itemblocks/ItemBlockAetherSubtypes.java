package com.gildedgames.aether.common.items.itemblocks;

import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithSubtypes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockAetherSubtypes extends ItemBlock
{
	private final IAetherBlockWithSubtypes blockWithSubtypes;

	public ItemBlockAetherSubtypes(Block block)
	{
		super(block);
		this.blockWithSubtypes = (IAetherBlockWithSubtypes) block;

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
		return super.getUnlocalizedName() + "." + this.blockWithSubtypes.getSubtypeUnlocalizedName(stack);
	}

}
