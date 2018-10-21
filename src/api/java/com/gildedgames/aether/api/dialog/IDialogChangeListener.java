package com.gildedgames.aether.api.dialog;

/**
 * A listener interface that can be implemented to subscribe to
 * dialog events.
 */
public interface IDialogChangeListener
{
	/**
	 * Called when the dialog controller advances or otherwise updates.
	 */
	void onDialogChanged();

	/**
	 * Called when the dialog controller closes a scene.
	 */
	void beforeSceneCloses();
}
