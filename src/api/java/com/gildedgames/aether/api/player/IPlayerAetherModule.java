package com.gildedgames.aether.api.player;

import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface IPlayerAetherModule
{
	void onEntityJoinWorld();

	void onDrops(PlayerDropsEvent event);

	void onDeath(LivingDeathEvent event);

	void onRespawn(PlayerEvent.PlayerRespawnEvent event);

	void tickStart(TickEvent.PlayerTickEvent event);

	void tickEnd(TickEvent.PlayerTickEvent event);

	IPlayerAether getPlayer();

	EntityPlayer getEntity();

	World getWorld();

	interface Serializable extends NBT
	{
		ResourceLocation getIdentifier();
	}
}
