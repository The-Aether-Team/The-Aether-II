package com.gildedgames.aether.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public interface InformationProvider
{
	@OnlyIn(Dist.CLIENT)
	void addInformation(ItemStack stack, List<ITextComponent> tooltip, ITooltipFlag flag);

}
