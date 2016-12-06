package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogButton;
import com.gildedgames.aether.common.dialog.IDialogController;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class DialogButtonBackToRoot implements IDialogButton
{

	@Nonnull
	@Override
	public String getUnlocalizedLabel()
	{
		return "dialog.back";
	}

	@Override
	public ResourceLocation getIcon()
	{
		return null;
	}

	@Override
	public void onClicked(IDialogController controller) {
		controller.setNode(controller.getCurrentScene().getRootNode().getIdentifier());
	}

}
