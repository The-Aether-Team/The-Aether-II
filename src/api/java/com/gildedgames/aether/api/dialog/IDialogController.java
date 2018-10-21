package com.gildedgames.aether.api.dialog;

import com.gildedgames.aether.api.entity.EntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Controls the progression and displaying of a scene to the player.
 */
public interface IDialogController
{
	void setConditionsMetData(Map<String, Boolean> data);

	/**
	 * @param button The button we're checking if their conditions are met.
	 * @return Whether or not the conditions were met for this button.
	 */
	boolean conditionsMet(IDialogButton button);

	EntityPlayer getDialogPlayer();

	@Nullable
	EntityNPC getTalkingNPC();

	void setTalkingEntity(EntityNPC entity);

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
	void openScene(ResourceLocation path, String startinNodeId);

	/**
	 * Sets the current node in the scene.
	 */
	void navigateNode(String nodeId);

	/**
	 * Sets the current node in the scene to the last active node.
	 */
	void navigateBack();

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
	void closeScene(boolean closeGUI);
}
