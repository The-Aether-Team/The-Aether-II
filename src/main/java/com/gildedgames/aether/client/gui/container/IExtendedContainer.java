package com.gildedgames.aether.client.gui.container;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IExtendedContainer
{
	void setHoveredDescription(ItemStack stack, List<String> desc);
}
