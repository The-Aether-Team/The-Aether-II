package com.gildedgames.aether.api.dialog;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Represents a line of text in a scene.
 */
public interface IDialogLine
{
	/**
	 * Returns the formatted body component of this line.
	 *
	 * @return The formatted body as a {@link ITextComponent}
	 */
	@Nonnull
	ITextComponent getLocalizedBody();

	/**
	 * Returns the speaker of this line. If the result is empty, no speaker will be shown.
	 *
	 * @return An {@link Optional} containing the {@link ResourceLocation} representing the speaker,
	 * empty if there is no speaker.
	 */
	@Nonnull
	Optional<ResourceLocation> getSpeaker();

	/**
	 * Returns the slide address for the defined speaker. If the result is empty, no slide will be shown.
	 *
	 * @return An {@link Optional} containing the {@link String} representing the slide address,
	 * empty if there is no address.
	 */
	@Nonnull
	Optional<String> getSlideAddress();
}
