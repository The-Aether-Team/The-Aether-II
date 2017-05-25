package com.gildedgames.aether.api.dialog;

import java.util.List;
import java.util.Optional;

/**
 * Represents a Slide which will be rendered in a scene.
 * Currently, {@link IDialogSpeaker} objects define what Slides
 * a {@link IDialogScene} can utilize.
 */
public interface IDialogSlide
{
    /**
     * Used to locate this slide in a speaker definition.
     * @return The string address for this slide.
     */
    String getAddress();
    /**
     * The returned implementation is used to draw the {@link #getSlideData()}
     * defined by this Slide.
     * @return An {@link Optional} object containing a {@link IDialogSlideRenderer}
     * implementation.
     */
    Optional<IDialogSlideRenderer> getRenderer();

    /**
     * Slide data that will eventually be interpreted by this Slide's
     * Renderer and drawn on screen.
     * @return An {@link Optional} object containing a list of String data.
     */
    Optional<List<String>> getSlideData();
}
