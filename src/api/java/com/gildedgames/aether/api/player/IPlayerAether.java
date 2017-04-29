package com.gildedgames.aether.api.player;

import com.gildedgames.aether.api.dialog.IDialogController;
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
	IEquipmentModule getEquipmentModule();

	IDialogController getDialogController();

	void onRespawn(PlayerEvent.PlayerRespawnEvent event);

	void onPlaceBlock(BlockEvent.PlaceEvent event);

	void onUpdate(LivingUpdateEvent event);

	void onDeath(LivingDeathEvent event);

	void onDrops(PlayerDropsEvent event);

	void onHurt(LivingHurtEvent event);

	void onFall(LivingFallEvent event);

	void onTeleport(PlayerChangedDimensionEvent event);

	/**
	 * Called when another {@link IPlayerAether} begins watching this player.
	 *
	 * @param other The entity that has begun watching us
	 */
	void onPlayerBeginWatching(IPlayerAether other);

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
