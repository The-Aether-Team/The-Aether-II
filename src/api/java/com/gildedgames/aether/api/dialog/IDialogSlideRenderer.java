package com.gildedgames.aether.api.dialog;

import net.minecraft.client.renderer.texture.TextureManager;

/**
 * Used to render the data provided by a {@link IDialogSlide} object.
 */
public interface IDialogSlideRenderer
{
	/**
	 * Should ideally be used to interpret and transform slide data for the
	 * {@link #draw(IDialogSlide, double, double, int, int, float) draw()} method.
	 * @param slide The slide that will be rendered by this implementation.
	 */
	void setup(IDialogSlide slide);
    /**
     * Should interpret the data defined by the provided slide and
     * render it based on this implementation's goals.
     * @param slide The slide that will be rendered by this implementation.
     */
    void draw(IDialogSlide slide, double screenWidth, double screenHeight, int mouseX, int mouseY, float partialTicks);
}
