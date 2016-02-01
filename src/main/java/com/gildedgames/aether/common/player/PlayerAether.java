package com.gildedgames.aether.common.player;

import java.util.UUID;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.inventory.InventoryAccessories;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerAether implements IPlayerHook
{
	private final IPlayerProfile playerProfile;

	private final IPlayerHookPool<PlayerAether> playerHookPool;

	private boolean isDirty;

	private InventoryAccessories inventoryAccessories = new InventoryAccessories(this);

	public PlayerAether(IPlayerProfile playerProfile, IPlayerHookPool<PlayerAether> playerHookPool)
	{
		this.playerProfile = playerProfile;
		this.playerHookPool = playerHookPool;
	}

	public static PlayerAether get(EntityPlayer player)
	{
		return AetherCore.locate().getPool().get(player);
	}

	public static PlayerAether get(UUID uuid)
	{
		return AetherCore.locate().getPool().get(uuid);
	}

	@Override
	public void entityInit(EntityPlayer player)
	{
	}

	/* === DISK SYNCING === */

	@Override
	public void write(NBTTagCompound output)
	{
		this.inventoryAccessories.write(output);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.inventoryAccessories.read(input);
	}

	/* === NETWORK SYNCING === */

	@Override
	public void syncTo(ByteBuf buf, SyncSide side)
	{

	}

	@Override
	public void syncFrom(ByteBuf buf, SyncSide side)
	{

	}

	/* === BOILERPLATE === */

	@Override
	public boolean isDirty()
	{
		return this.isDirty;
	}

	@Override
	public void markDirty()
	{
		this.isDirty = true;
	}

	@Override
	public void markClean()
	{
		this.isDirty = false;
	}

	@Override
	public IPlayerHookPool getParentPool()
	{
		return this.playerHookPool;
	}

	@Override
	public IPlayerProfile getProfile()
	{
		return this.playerProfile;
	}

	public EntityPlayer getPlayer()
	{
		return this.getProfile().getEntity();
	}

	public InventoryAccessories getInventoryAccessories()
	{
		return this.inventoryAccessories;
	}

	public boolean isAccessoryEquipped(Item item)
	{
		return this.getInventoryAccessories().isAccessoryEquipped(item);
	}
}
