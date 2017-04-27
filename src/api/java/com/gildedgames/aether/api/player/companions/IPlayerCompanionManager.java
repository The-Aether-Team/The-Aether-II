package com.gildedgames.aether.api.player.companions;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Manages the companion of a player.
 */
public interface IPlayerCompanionManager
{
	/**
	 * Returns the currently active companion entity of the player.
	 *
	 * @return The in-world entity of the current companion, empty if none.
	 */
	Optional<Entity> getCompanionEntity();

	/**
	 * Returns the currently equipped companion item.
	 *
	 * @return The currently equipped companion item of the player, {@link ItemStack#EMPTY} if none.
	 */
	@Nonnull
	ItemStack getCompanionItem();
}
