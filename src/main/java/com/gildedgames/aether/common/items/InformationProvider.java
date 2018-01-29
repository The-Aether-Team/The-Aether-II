package com.gildedgames.aether.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface InformationProvider
{

	void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag flag);

}
