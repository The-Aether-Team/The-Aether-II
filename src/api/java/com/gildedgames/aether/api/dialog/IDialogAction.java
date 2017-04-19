package com.gildedgames.aether.api.dialog;

/**
 * An action that can be performed during a dialog scene either by the user
 * pressing a {@link IDialogButton} or after a {@link IDialogNode} completes.
 */
public interface IDialogAction
{
	/**
	 * Called when the action is performed.
	 *
	 * @param controller The controller this action was called from
	 */
	void performAction(IDialogController controller);
}
