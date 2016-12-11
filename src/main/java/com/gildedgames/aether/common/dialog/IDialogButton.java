package com.gildedgames.aether.common.dialog;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IDialogButton
{

	// The unlocalized label of the button.
	@Nonnull
	ITextComponent getLabel();

	// Optional, gives the GUI button an icon if not null.
	@Nullable
	ResourceLocation getIcon();

	// Called when the parent GUI button is clicked.
	void onClicked(IDialogController controller);

}
