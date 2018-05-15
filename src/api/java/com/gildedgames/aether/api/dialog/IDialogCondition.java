package com.gildedgames.aether.api.dialog;

/**
 * A condition that has to be met during a dialog scene before, for example,
 * a button can be displayed.
 */
public interface IDialogCondition
{
	/**
	 * Called when the condition needs to be met.
	 *
	 * @param controller The controller this condition was called from
	 */
	boolean isMet(IDialogController controller);
}
