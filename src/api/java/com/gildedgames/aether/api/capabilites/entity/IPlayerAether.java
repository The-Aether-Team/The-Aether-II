package com.gildedgames.aether.api.capabilites.entity;

import com.gildedgames.aether.api.player.companions.IPlayerCompanionManager;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public interface IPlayerAether
{
	IInventoryEquipment getEquipmentInventory();

	IPlayerCompanionManager getCompanionModule();

	void onRespawn(PlayerEvent.PlayerRespawnEvent event);

	void onPlaceBlock(BlockEvent.PlaceEvent event);

	void onUpdate(LivingUpdateEvent event);

	void onDeath(LivingDeathEvent event);

	void onDrops(PlayerDropsEvent event);

	void onHurt(LivingHurtEvent event);

	void onFall(LivingFallEvent event);

	void onTeleport(PlayerChangedDimensionEvent event);

	void onSpawned(PlayerLoggedInEvent event);

	void onDespawn(PlayerLoggedOutEvent event);

	/**
	 * @return The {@link EntityPlayer} this capability is attached to.
	 */
	EntityPlayer getEntity();

	/**
	 * @return This player's mining speed modifier.
	 */
	float getMiningSpeedMultiplier();

	void write(NBTTagCompound tag);

	void read(NBTTagCompound tag);
}
