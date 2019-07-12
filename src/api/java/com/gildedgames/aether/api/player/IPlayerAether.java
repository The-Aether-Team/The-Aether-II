package com.gildedgames.aether.api.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

/**
 * The Aether's extended player capability that manages all Aether-related additions
 * and states.
 */
public interface IPlayerAether
{
	/**
	 * @return The {@link PlayerEntity} entity this capability belongs to.
	 */
	PlayerEntity getEntity();

	void registerModule(@Nonnull IPlayerAetherModule module);

	@Nonnull
	<T extends IPlayerAetherModule> T getModule(@Nonnull Class<T> clazz);

	/**
	 * @return This player's mining speed modifier.
	 */
	float getMiningSpeedMultiplier();

	/**
	 * Writes this capability to {@param tag}.
	 * @param tag The {@link CompoundNBT} to write to
	 */
	void write(CompoundNBT tag);

	/**
	 * Updates this capability from {@param tag}.
	 * @param tag The {@link CompoundNBT} to read from
	 */
	void read(CompoundNBT tag);
}
