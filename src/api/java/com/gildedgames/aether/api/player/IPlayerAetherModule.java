package com.gildedgames.aether.api.player;

import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface IPlayerAetherModule
{
	void onDrops(LivingDropsEvent event);

	void onDeath(LivingDeathEvent event);

	void onRespawn(PlayerEvent.PlayerRespawnEvent event);

	void tickStart(TickEvent.PlayerTickEvent event);

	void tickEnd(TickEvent.PlayerTickEvent event);

	IPlayerAether getPlayer();

	PlayerEntity getEntity();

	World getWorld();

	interface Serializable extends NBT
	{
		ResourceLocation getIdentifier();
	}
}
