package com.gildedgames.aetherii.api.dialog;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface IDialogLine {
	/**
	 * Returns the formatted body component of this line.
	 *
	 * @return The formatted body as a {@link Component}
	 */
	@Nonnull
	Component getLocalizedBody();

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