package com.gildedgames.aether.client.gui;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IExtendedGui
{

	List<String> getHoveredDescription();

	void setHoveredDescription(ItemStack stack, List<String> desc);

}
