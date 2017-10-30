package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class PlayerAetherModule implements NBT
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
}
