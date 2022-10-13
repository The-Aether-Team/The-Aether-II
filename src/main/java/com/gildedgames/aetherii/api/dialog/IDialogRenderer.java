package com.gildedgames.aetherii.api.dialog;

import com.mojang.blaze3d.vertex.PoseStack;

public interface IDialogRenderer {
	/**
	 * Should ideally be used to interpret and transform slide data for the
	 * {@link #draw(IDialog, PoseStack, double, double, int, int, float) draw()} method.
	 *
	 * @param slide The slide that will be rendered by this implementation.
	 */
	void setup(IDialog slide);

	/**
	 * Should interpret the data defined by the provided slide and
	 * render it based on this implementation's goals.
	 *
	 * @param slide The slide that will be rendered by this implementation.
	 */
	void draw(IDialog slide, PoseStack poseStack, double screenWidth, double screenHeight, int mouseX, int mouseY, float partialTicks);
}