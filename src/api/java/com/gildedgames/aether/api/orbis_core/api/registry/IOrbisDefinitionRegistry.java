package com.gildedgames.aether.api.orbis_core.api.registry;

import com.gildedgames.aether.api.orbis_core.api.BlueprintDefinition;

/**
 * A definition registry used to fetch
 * back any definitions you've created
 * after reloading the game. You should
 * use the default implementation or create
 * your own and register it with the OrbisAPI
 * services.
 */
public interface IOrbisDefinitionRegistry
{
	/**
	 * Used to identify this registry when reading back
	 * definitions registered with your Orbis projects.
	 *
	 * It's important that this is unique to your mod.
	 * Recommended you use your MOD_ID.
	 * @return The id for this registry.
	 */
	String getRegistryId();

	/**
	 * @param def The definition that has been registered
	 *            by this registry.
	 * @return The unique integer id associated with the
	 * provided definition.
	 */
	int getID(BlueprintDefinition def);

	/**
	 * @param id The unique integer id associated with the
	 *           definition you're attempting to find.
	 * @return The definition found from the provided id.
	 */
	BlueprintDefinition get(int id);

	/**
	 * Registers the definition to the registry with a unique
	 * id so you can fetch it back after reloading the game.
	 *
	 * @param id The unique integer id for this definition.
	 * @param def The definition you're registering.
	 */
	void register(int id, BlueprintDefinition def);
}
