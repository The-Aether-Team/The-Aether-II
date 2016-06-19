package com.gildedgames.aether.api.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;

public interface IPlayerAetherCapability
{
	// [--- Event handlers for the entity this capability is attached to. ---]

	void onUpdate(LivingUpdateEvent event);

	void onDeath(LivingDeathEvent event);

	void onDrops(LivingDropsEvent event);

	void onHurt(LivingHurtEvent event);

	void onFall(LivingFallEvent event);

	void onJump(LivingJumpEvent event);

	// [--- End of event handlers. ---]

	IInventoryEquipment getEquipmentInventory();

	/**
	 * @return The {@link EntityPlayer} this capability is attached to.
	 */
	EntityPlayer getPlayer();

	/**
	 * @return This player's mining speed modifier.
	 */
	float getMiningSpeedMultiplier();

	BlockPos getLinkingSchematicBoundary();
	
	void setLinkingSchematicBoundary(BlockPos pos);

	int getTicksAirborne();

	boolean performDoubleJump();
}
