package com.gildedgames.aether.common.dialog;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public interface IDialogContent
{

	ResourceLocation getPortrait();

	ITextComponent getText();

}
