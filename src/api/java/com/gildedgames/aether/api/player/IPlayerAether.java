package com.gildedgames.aether.api.player;

import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.api.net.data.UserFeatures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * The Aether's extended player capability that manages all Aether-related additions
 * and states.
 */
public interface IPlayerAether
{
	/**
	 * @return The player's {@link IEquipmentModule} that is responsible for updating and managing it's
	 * equipment.
	 */
	IEquipmentModule getEquipmentModule();

	/**
	 * @return The player's {@link IDialogController} that is responsible for managing the player's
	 * dialog state.
	 */
	IDialogController getDialogController();

	/**
	 * @return The {@link EntityPlayer} entity this capability belongs to.
	 */
	EntityPlayer getEntity();

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

	/**
	 * Called when a player logs in.
	 */
	void onEntityJoinWorld();
}
