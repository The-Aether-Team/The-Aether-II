package com.gildedgames.aether.api.dialog;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
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
     * Returns the slides for the viewer GUI to use. First one is default, if exists.
     * @return An {@link Optional} value containing the {@link IDialogSlide}s, if they exist.
     */
    Optional<Map<String, IDialogSlide>> getSlides();

}
