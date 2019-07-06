package com.gildedgames.aether.api.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * The Aether's extended player capability that manages all Aether-related additions
 * and states.
 */
public interface IPlayerAether
{
	/**
	 * @return The {@link EntityPlayer} entity this capability belongs to.
	 */
	EntityPlayer getEntity();

	void registerModule(@Nonnull IPlayerAetherModule module);

	@Nonnull
	<T extends IPlayerAetherModule> T getModule(@Nonnull Class<T> clazz);

	/**
	 * @return This player's mining speed modifier.
	 */
	float getMiningSpeedMultiplier();

	/**
	 * Writes this capability to {@param tag}.
	 * @param tag The {@link NBTTagCompound} to write to
	 */
	void write(NBTTagCompound tag);

	/**
	 * Updates this capability from {@param tag}.
	 * @param tag The {@link NBTTagCompound} to read from
	 */
	void read(NBTTagCompound tag);
}
