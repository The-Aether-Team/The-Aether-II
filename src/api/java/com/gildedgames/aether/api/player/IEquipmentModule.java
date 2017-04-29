package com.gildedgames.aether.api.player;

import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import net.minecraft.util.ResourceLocation;

import java.util.Optional;

public interface IEquipmentModule
{
	/**
	 * Returns the player's equipment inventory.
	 *
	 * @return The player's {@link IInventoryEquipment}
	 */
	IInventoryEquipment getInventory();

	/**
	 * Returns the active {@link IEffectPool} for the effect ID.
	 *
	 * @param id The resource name of the effect
	 * @return The {@link IEffectPool}, empty if there is no active pool for the effect
	 */
	Optional<IEffectPool<IEffectProvider>> getEffectPool(ResourceLocation id);

	/**
	 * Checks whether or not a player currently has an active effect for the factory {@param effect}.
	 *
	 * @param effect The factory to check
	 * @return True if the player has an active instance of the effect, otherwise false.
	 */
	boolean isEffectActive(IEffectFactory<?> effect);
}
