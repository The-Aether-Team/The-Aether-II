package com.gildedgames.aether.common.items.weapons.crossbow;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBolt extends Item
{
	private static final ItemBoltType[] ITEM_VARIANTS = new ItemBoltType[] { ItemBoltType.STONE, ItemBoltType.ZANITE };

	public ItemBolt()
	{
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems)
	{
		for (ItemBoltType type : ITEM_VARIANTS)
		{
			subItems.add(new ItemStack(item, 1, type.ordinal()));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + ItemBoltType.fromOrdinal(stack.getMetadata()).getID();
	}
}
