package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogContent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DialogContentSimple implements IDialogContent
{

	private final ResourceLocation portrait;

	private final ITextComponent text;

	public DialogContentSimple(ResourceLocation portrait, ITextComponent text)
	{
		this.portrait = portrait;
		this.text = text;
	}

	@Override
	public ResourceLocation getPortrait()
	{
		return this.portrait;
	}

	@Override
	public ITextComponent getText()
	{
		return this.text;
	}

}
