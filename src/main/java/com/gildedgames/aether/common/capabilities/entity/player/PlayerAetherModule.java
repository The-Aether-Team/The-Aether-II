package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.IPlayerAetherModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public abstract class PlayerAetherModule implements IPlayerAetherModule
{
	private final PlayerAether playerAether;

	public PlayerAetherModule(final PlayerAether playerAether)
	{
		this.playerAether = playerAether;
	}

	@Override
	public void onDrops(LivingDropsEvent event)
	{

	}

	@Override
	public void onDeath(LivingDeathEvent event)
	{

	}

	@Override
	public void onRespawn(PlayerEvent.PlayerRespawnEvent event)
	{

	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public final IPlayerAether getPlayer()
	{
		return this.playerAether;
	}

	@Override
	public final PlayerEntity getEntity()
	{
		return this.playerAether.getEntity();
	}

	@Override
	public final World getWorld()
	{
		return this.getEntity().world;
	}
}
