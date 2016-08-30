package com.gildedgames.aether.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface InformationProvider
{

	void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced);

}
