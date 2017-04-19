package com.gildedgames.aether.api.dialog;

import net.minecraft.util.ResourceLocation;

import java.util.Optional;

/**
 * Manages the loading and caching of dialog scenes.
 */
public interface IDialogManager
{
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
}
