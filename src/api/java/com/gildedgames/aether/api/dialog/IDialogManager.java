package com.gildedgames.aether.api.dialog;

import net.minecraft.util.ResourceLocation;

import java.util.Optional;

/**
 * Manages the loading and caching of dialog scenes.
 */
public interface IDialogManager
{
	/**
	 * Returns a {@link IDialogSpeaker} for the specified resource location. The default
	 * implementation may also cache the speaker in memory for faster access later.
	 *
	 * This expects the file to be located at /assets/DOMAIN/dialog/speakers/NAME.json
	 * @param resource The resource name of the speaker.
	 * @return The {@link IDialogSpeaker} representing the resource.
	 */
	Optional<IDialogSpeaker> getSpeaker(ResourceLocation resource);

	/**
	 * Returns a {@link IDialogScene} for the specified resource location. The default
	 * implementation may also cache the scene in memory for faster access later.
	 *
	 * This expects the file to be located at /assets/DOMAIN/dialog/scenes/NAME.json.
	 *
	 * @param resource The resource name of the scene
	 * @return The {@link IDialogScene} representing the resource
	 */
	Optional<IDialogScene> getScene(ResourceLocation resource);

	/**
	 * Attempts to find a slide with the given slideAddress. If not defined,
	 * it will return an empty optional.
	 * @param slideAddress The address of the slide we're attempting to find.
	 * @param speaker The speaker where the slide is defined.
	 * @return The {@link IDialogSlide} representing the address
	 */
	Optional<IDialogSlide> findSlide(String slideAddress, IDialogSpeaker speaker);

	/**
	 * Attempts to find a slide renderer implementation tied with the given
	 * String. If not defined, it will return an empty optional.
	 * @param type The defined String key/type for that registered renderer
	 * @return The {@link IDialogSlideRenderer} representing the given String.
	 */
	Optional<IDialogSlideRenderer> findRenderer(String type);
}
