package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogButton;
import com.gildedgames.aether.common.dialog.IDialogController;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public class DialogButtonBackToRoot implements IDialogButton
{

	private static final ITextComponent LABEL = new TextComponentTranslation("dialog.back");

	@Nonnull
	@Override
	public ITextComponent getLabel()
	{
		return LABEL;
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
