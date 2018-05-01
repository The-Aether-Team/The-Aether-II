package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public abstract class PlayerAetherModule implements NBT
{
	private final PlayerAether playerAether;

	public PlayerAetherModule(final PlayerAether playerAether)
	{
		this.playerAether = playerAether;
	}

	public void onDrops(PlayerDropsEvent event)
	{

	}

	public void onDeath(LivingDeathEvent event)
	{

	}

	public void onRespawn(PlayerEvent.PlayerRespawnEvent event)
	{

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
