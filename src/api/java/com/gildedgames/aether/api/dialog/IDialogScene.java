package com.gildedgames.aether.api.dialog;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * A scene representing it's dialog nodes and starting point.
 */
public interface IDialogScene
{
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
}
