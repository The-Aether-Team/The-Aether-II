package com.gildedgames.aether.common.capabilities.entity.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class PlayerAetherModule
{
	private final PlayerAether playerAether;

	public PlayerAetherModule(final PlayerAether playerAether)
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

	public final World getWorld()
	{
		return this.getEntity().world;
	}

	/**
	 * Writes this module's data to the player capability. **This might change in the future to prevent
	 * modules from accessing data outside their scope.**
	 *
	 * @param tag The NBT tag to writeProperties to
	 */
	public abstract void write(NBTTagCompound tag);

	/**
	 * Reads this module's data from the player capability. **This might change in the future to prevent
	 * modules from accessing data outside their scope.**
	 *
	 * @param tag The NBT tag to read from
	 */
	public abstract void read(NBTTagCompound tag);
}
