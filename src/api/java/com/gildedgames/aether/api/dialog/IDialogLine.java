package com.gildedgames.aether.api.dialog;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
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
	 * If the returned ResourceLocation has a #address suffix, it will use that to point to
	 * a given slide registered within that speaker's json file.
	 *
	 * @return An {@link Optional} containing the {@link ResourceLocation} representing the speaker,
	 * empty if there is no speaker.
	 */
	@Nonnull
	Optional<ResourceLocation> getSpeaker();
}
