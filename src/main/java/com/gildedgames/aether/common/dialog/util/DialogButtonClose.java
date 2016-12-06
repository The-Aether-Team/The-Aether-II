package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogButton;
import com.gildedgames.aether.common.dialog.IDialogController;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

import javax.annotation.Nonnull;

public class DialogButtonClose implements IDialogButton
{

	private ITextComponent label;

	public DialogButtonClose(ITextComponent label)
	{
		this.label = label;
	}

	@Nonnull
	@Override
	public ITextComponent getLabel()
	{
		return this.label;
	}

	@Override
	public ResourceLocation getIcon()
	{
		return null;
	}

	@Override
	public void onClicked(IDialogController controller) {
		controller.close();
	}

}
