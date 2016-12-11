package com.gildedgames.aether.common.dialog;

public interface IDialogController
{

	/**
	 * Sets the current dialog.
	 */
	void show(DialogTree scene);

	/**
	 * @return The current dialog
	 */
	DialogTree getCurrentScene();

	/**
	 * Sets the current node.
	 */
	void setNode(String nodeId);

	/**
	 * Closes the dialog and respective GUI.
	 */
	void close();

}
