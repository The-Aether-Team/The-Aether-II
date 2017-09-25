package com.gildedgames.aether.api.dialog;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Controls the progression and displaying of a scene to the player.
 */
public interface IDialogController
{
	/**
	 * Adds a {@link IDialogChangeListener} to this controller. All listeners are removed
	 * when the scene is changed.
	 * @param listener The listener to add
	 */
	void addListener(IDialogChangeListener listener);

	/**
	 * Opens and displays the scene at it's starting node.
	 * @param path The resource path to the scene file
	 */
	void openScene(ResourceLocation path);

	/**
	 * Sets the current node in the scene.
	 */
	void navigateNode(String nodeId);

	/**
	 * Called when the player presses a button in the dialog node.
	 *
	 * @param button The button to press
	 */
	void activateButton(IDialogButton button);

	/**
	 * Advances the scene forward, automatically performing any actions.
	 */
	void advance();

	/**
	 * Returns the current scene of this controller.
	 * @return The currently active scene of this controller
	 */
	IDialogScene getCurrentScene();

	/**
	 * Returns the current node of the controller's scene.
	 * @return An {@link IDialogNode} of the current scene
	 */
	@Nonnull
	IDialogNode getCurrentNode();

	/**
	 * Returns the current line of the controller's scene.
	 * @return An {@link IDialogLine} of the current node
	 */
	@Nonnull
	IDialogLine getCurrentLine();

	/**
	 * Returns whether or not the current node has been read completely.
	 * This is really only used to determine whether or not the buttons should be
	 * shown yet.
	 * @return True if all lines of the node have been read
	 */
	boolean isNodeFinished();

	/**
	 * Closes the dialog and respective GUI.
	 */
	void closeScene();
}
