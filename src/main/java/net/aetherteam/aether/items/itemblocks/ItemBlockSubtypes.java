package net.aetherteam.aether.items.itemblocks;

import net.aetherteam.aether.blocks.util.ISubtypedAetherBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSubtypes extends ItemBlock
{
	private final ISubtypedAetherBlock subtypeBlock;

	public ItemBlockSubtypes(Block block)
	{
		super(block);

		this.subtypeBlock = (ISubtypedAetherBlock) block;
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
		return super.getUnlocalizedName() + "." + this.subtypeBlock.getNameFromSubtype(stack);
	}

}
