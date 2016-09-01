package com.gildedgames.aether.api.player;

import com.gildedgames.aether.api.player.companions.IPlayerCompanionManager;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.util.io_manager.io.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public interface IPlayerAetherCapability
{
	// [--- Event handlers for the entity this capability is attached to. ---]

	void onRespawn();

	void onUpdate(LivingUpdateEvent event);

	void onDeath(LivingDeathEvent event);

	void onDrops(LivingDropsEvent event);

	void onHurt(LivingHurtEvent event);

	void onFall(LivingFallEvent event);

	void onTeleport(PlayerChangedDimensionEvent event);

	void onSpawned(PlayerLoggedInEvent event);

	void onDespawn(PlayerLoggedOutEvent event);

	// [--- End of event handlers. ---]

	IInventoryEquipment getEquipmentInventory();

	IPlayerCompanionManager getCompanionModule();

	/**
	 * @return The {@link EntityPlayer} this capability is attached to.
	 */
	EntityPlayer getPlayer();

	/**
	 * @return This player's mining speed modifier.
	 */
	float getMiningSpeedMultiplier();

	int getTicksAirborne();

	boolean performMidAirJump();

	void write(NBTTagCompound tag);

	void read(NBTTagCompound tag);

}
