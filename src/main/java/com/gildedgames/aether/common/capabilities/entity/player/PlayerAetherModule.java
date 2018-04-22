package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.orbis.api.util.mc.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public abstract class PlayerAetherModule implements NBT
{
	private final PlayerAether playerAether;

	public PlayerAetherModule(final PlayerAether playerAether)
	{
		this.playerAether = playerAether;
	}

	public abstract void tickStart(TickEvent.PlayerTickEvent event);

	public abstract void tickEnd(TickEvent.PlayerTickEvent event);

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
