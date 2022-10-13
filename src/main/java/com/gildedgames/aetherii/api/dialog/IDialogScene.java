package com.gildedgames.aetherii.api.dialog;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface IDialogScene {
	/**
	 * Returns a dialog node for the scene.
	 *
	 * @param id The name of the node
	 * @return An {@link Optional} value containing the node, if it exists
	 */
	Optional<IDialogNode> getNode(String id);

	/**
	 * Returns the starting node for this scene.
	 *
	 * @return A {@link IDialogNode} that kicks off the scene.
	 */
	@Nonnull
	IDialogNode getStartingNode();

	/**
	 * Sets the starting node for this scene.
	 *
	 * @param id The name of the node
	 */
	void setStartingNode(String id);
}
