package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogButton;
import com.gildedgames.aether.common.dialog.IDialogController;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class DialogButtonClose implements IDialogButton
{

	private String label;

	public DialogButtonClose(String label)
	{
		this.label = label;
	}

	@Nonnull
	@Override
	public String getUnlocalizedLabel()
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
