package com.gildedgames.aetherii.api.dialog;

import java.util.Map;
import java.util.Optional;

public interface IDialog {
	/**
	 * The returned String represents an implementation that is used to draw
	 * the {@link #getDialogData()} defined by this Dialog.
	 *
	 * @return An {@link Optional} object containing a String which represents
	 * a renderer implementation
	 */
	Optional<String> getRenderer();

	/**
	 * Dialog data that will eventually be interpreted by this Dialog's
	 * Renderer and drawn on screen.
	 *
	 * @return An {@link Optional} object containing a map of String data.
	 */
	Optional<Map<String, String>> getDialogData();
}
