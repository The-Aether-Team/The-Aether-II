package com.gildedgames.aetherii.api.dialog;

import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface IDialogButton {

	/**
	 * @return The conditions required for this button to display, but using or operation.
	 */
	@Nonnull
	Collection<IDialogCondition> getOrConditions();

	/**
	 * @return The conditions required for this button to display.
	 */
	@Nonnull
	Collection<IDialogCondition> getConditions();

	/**
	 * Returns the unlocalized label of this button.
	 */
	@Nonnull
	String getLabel();

	/**
	 * Returns the localized display string of this button.
	 *
	 * @return
	 */
	@Nonnull
	Component getLocalizedLabel();

	/**
	 * Returns the action to perform when this button is clicked.
	 */
	@Nonnull
	Collection<IDialogAction> getActions();
}