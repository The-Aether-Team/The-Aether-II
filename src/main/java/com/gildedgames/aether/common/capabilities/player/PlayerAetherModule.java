package com.gildedgames.aether.common.capabilities.player;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.companions.IPlayerCompanionManager;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public abstract class PlayerAetherModule implements IPlayerAetherCapability
{

	private PlayerAetherImpl playerAether;

	public PlayerAetherModule(PlayerAetherImpl playerAether)
	{
		this.playerAether = playerAether;
	}

	public PlayerAetherImpl getPlayerAether()
	{
		return this.playerAether;
	}

	@Override
	public void onPlaceBlock(BlockEvent.PlaceEvent event)
	{

	}

	@Override
	public void onPlaceBlockMulti(BlockEvent.MultiPlaceEvent event)
	{

	}

	@Override
	public void onRespawn()
	{

	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{

	}

	@Override
	public void onDeath(LivingDeathEvent event)
	{

	}

	@Override
	public void onDrops(PlayerDropsEvent event)
	{

	}

	@Override
	public void onHurt(LivingHurtEvent event)
	{

	}

	@Override
	public void onFall(LivingFallEvent event)
	{

	}

	@Override
	public void onTeleport(PlayerEvent.PlayerChangedDimensionEvent event)
	{

	}

	@Override
	public void onSpawned(PlayerEvent.PlayerLoggedInEvent event)
	{

	}

	@Override
	public void onDespawn(PlayerEvent.PlayerLoggedOutEvent event)
	{

	}

	@Override
	public boolean performMidAirJump()
	{
		return false;
	}

	@Override
	public void write(NBTTagCompound tag)
	{

	}

	@Override
	public void read(NBTTagCompound tag)
	{

	}

	@Override
	public int getTicksAirborne()
	{
		return 0;
	}

	/** Below should NOT be overriden **/

	@Override
	public final IInventoryEquipment getEquipmentInventory()
	{
		return null;
	}

	public final IPlayerCompanionManager getCompanionModule()
	{
		return null;
	}

	@Override
	public final EntityPlayer getPlayer()
	{
		return this.playerAether.getPlayer();
	}

	@Override
	public final float getMiningSpeedMultiplier()
	{
		return 0;
	}

}
