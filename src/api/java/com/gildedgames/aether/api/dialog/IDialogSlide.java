package com.gildedgames.aether.api.dialog;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a Slide which will be rendered in a scene.
 * Currently, {@link IDialogSpeaker} objects define what Slides
 * a {@link IDialogScene} can utilize.
 */
public interface IDialogSlide
{
	/**
	 * The returned String represents an implementation that is used to draw
	 * the {@link #getSlideData()} defined by this Slide.
	 * @return An {@link Optional} object containing a String which represents
	 * a renderer implementation
	 */
	Optional<String> getRenderer();

	/**
	 * Slide data that will eventually be interpreted by this Slide's
	 * Renderer and drawn on screen.
	 * @return An {@link Optional} object containing a map of String data.
	 */
	Optional<Map<String, String>> getSlideData();
}
