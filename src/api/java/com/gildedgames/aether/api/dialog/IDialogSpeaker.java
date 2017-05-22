package com.gildedgames.aether.api.dialog;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Represents the data associated with a speaker.
 */
public interface IDialogSpeaker
{
    /**
     * @return The name of the speaker.
     */
    @Nonnull
    String getUnlocalizedName();

    /**
     * Returns the style for the viewer GUI to use.
     * @return An {@link Optional} value containing the style ResourceLocation, if it exists.
     */
    Optional<ResourceLocation> getStyle();

}
