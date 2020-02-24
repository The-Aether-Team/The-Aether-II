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
	public ItemBolt()
	{

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flag)
	{
		tooltip.add(TextFormatting.GRAY + I18n.format("item.aether.bolt.desc"));
	}
}
