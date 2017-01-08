package com.gildedgames.aether.common.items.weapons.crossbow;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBolt extends Item
{
	private static final ItemBoltType[] ITEM_VARIANTS = new ItemBoltType[] {
			ItemBoltType.SKYROOT,
			ItemBoltType.HOLYSTONE,
			ItemBoltType.SCATTERGLASS,
			ItemBoltType.ZANITE,
			ItemBoltType.ARKENIUM,
			ItemBoltType.GRAVITITE
	};

	public ItemBolt()
	{
		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean adv)
	{
		ItemBoltType type = ITEM_VARIANTS[stack.getItemDamage()];

		float damage = type.getDamage() * 2.0F;

		if (damage == Math.floor(damage))
		{
			tooltip.add(TextFormatting.GRAY + String.valueOf((int) Math.floor(damage)) + " " + I18n.format("item.aether.bolt.desc1"));
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + String.valueOf(damage) + " " + I18n.format("item.aether.bolt.desc1"));
		}
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
