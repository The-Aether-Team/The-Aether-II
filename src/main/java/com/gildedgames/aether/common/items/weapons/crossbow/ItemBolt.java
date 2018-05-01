package com.gildedgames.aether.common.items.weapons.crossbow;

import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBolt extends Item implements IDropOnDeath
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

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flag)
	{
		final ItemBoltType type = ITEM_VARIANTS[stack.getItemDamage()];

		final float damage = type.getDamage() * 2.0F;

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
	public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> subItems)
	{
		if (!this.isInCreativeTab(tab))
		{
			return;
		}

		for (final ItemBoltType type : ITEM_VARIANTS)
		{
			subItems.add(new ItemStack(this, 1, type.ordinal()));
		}
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + ItemBoltType.fromOrdinal(stack.getMetadata()).getID();
	}
}
