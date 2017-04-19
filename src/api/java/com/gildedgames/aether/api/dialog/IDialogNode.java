package com.gildedgames.aether.api.dialog;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

/**
 * Represents a node that can be navigated to by scene buttons or actions.
 */
public interface IDialogNode
{
	/**
	 * Returns the dialog lines of this node.
	 */
	@Nonnull
	List<IDialogLine> getLines();

	/**
	 * Returns the buttons the player is given after all lines have been read.
	 *
	 * @return A collection of {@link IDialogButton}
	 */
	@Nonnull
	Collection<IDialogButton> getButtons();

	/**
	 * Returns a collection of actions to perform after the node has been completed.
	 *
	 * @return A collection of {@link IDialogAction}
	 */
	@Nonnull
	Collection<IDialogAction> getEndActions();

	/**
	 * This node's unique ID for use by other parts of the dialog system. Must be unique.
	 */
	@Nonnull
	String getIdentifier();
}
