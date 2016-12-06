package com.gildedgames.aether.common.dialog;

import com.sun.istack.internal.Nullable;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IDialogButton
{

	// The unlocalized label of the button.
	@Nonnull
	String getUnlocalizedLabel();

	// Optional, gives the GUI button an icon if not null.
	@Nullable
	ResourceLocation getIcon();

	// Called when the parent GUI button is clicked.
	void onClicked(IDialogController controller);

}
