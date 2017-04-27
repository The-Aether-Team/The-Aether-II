package com.gildedgames.aether.api.registry;

import com.gildedgames.aether.api.dialog.IDialogManager;
import com.gildedgames.aether.api.recipes.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.recipes.IRecipeIndexRegistry;
import com.gildedgames.aether.api.registry.tab.ITabRegistry;

/**
 * Provider for Aether content interfaces.
 */
public interface IContentRegistry
{
	/**
	 * @return The {@link IAltarRecipeRegistry} provider for the Aether.
	 */
	IAltarRecipeRegistry altar();

	/**
	 * @return The {@link ITabRegistry} provider for the Aether.
	 */
	ITabRegistry tabs();

	/**
	 * @return The {@link IItemPropertiesRegistry} provider for the Aether.
	 */
	IItemPropertiesRegistry items();

	/**
	 * @return The {@link IEffectRegistry} provider for the Aether.
	 */
	IEffectRegistry effects();

	/**
	 * @return The {@link IDialogManager} provider for the Aether.
	 */
	IDialogManager dialog();

	/**
	 * @return The {@link IRecipeIndexRegistry} provider for the Aether.
	 */
	IRecipeIndexRegistry craftable();
}
