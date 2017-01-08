package com.gildedgames.aether.common.capabilities.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class PlayerAetherModule
{
	private PlayerAether playerAether;

	public PlayerAetherModule(PlayerAether playerAether)
	{
		this.playerAether = playerAether;
	}

	public abstract void onUpdate();

	public final PlayerAether getPlayer()
	{
		return this.playerAether;
	}

	public final EntityPlayer getEntity()
	{
		return this.playerAether.getEntity();
	}

	/**
	 * Writes this module's data to the player capability. **This might change in the future to prevent
	 * modules from accessing data outside their scope.**
	 *
	 * @param compound The NBT tag to write to
	 */
	public abstract void write(NBTTagCompound compound);

	/**
	 * Reads this module's data from the player capability. **This might change in the future to prevent
	 * modules from accessing data outside their scope.**
	 *
	 * @param compound The NBT tag to read from
	 */
	public abstract void read(NBTTagCompound compound);
}
