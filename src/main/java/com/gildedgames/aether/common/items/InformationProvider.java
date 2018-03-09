package com.gildedgames.aether.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface InformationProvider
{
    @SideOnly(Side.CLIENT)
	void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag flag);

}
