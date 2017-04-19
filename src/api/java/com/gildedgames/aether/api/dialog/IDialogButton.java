package com.gildedgames.aether.api.dialog;

import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

/**
 * A user-pressable button that can appear at the end of a dialog node.
 */
public interface IDialogButton
{
	/**
	 * Returns the localized display string of this button.
	 */
	@Nonnull
	ITextComponent getLocalizedLabel();

	/**
	 * Returns the action to perform when this button is clicked.
	 */
	@Nonnull
	IDialogAction getAction();
}
