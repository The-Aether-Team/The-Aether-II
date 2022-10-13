package com.gildedgames.aetherii.api.dialog;

public interface IDialogChangeListener {
	/**
	 * Called when the dialog controller advances or otherwise updates.
	 */
	void onDialogChanged();

	/**
	 * Called when the dialog controller closes a scene.
	 */
	void beforeSceneCloses();
}