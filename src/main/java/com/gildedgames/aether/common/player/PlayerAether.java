package com.gildedgames.aether.common.player;

import java.util.UUID;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.inventory.InventoryAccessories;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.util.PlayerUtil;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent;

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

	/**
	 * Called when the bound player ticks
	 */
	public void onUpdate()
	{
		if (this.isAccessoryEquipped(ItemsAether.iron_bubble))
		{
			if (this.getPlayer().isInWater())
			{
				this.getPlayer().setAir(300);
			}
		}
	}

	/**
	 * Called when the living player attached to this instance dies
	 */
	public void onDeath()
	{
		// TODO: Drop accessories
	}

	public void onCalculateBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		if (PlayerUtil.isWearingFullSet(this.getPlayer(), ItemNeptuneArmor.class))
		{
			if (!EnchantmentHelper.getAquaAffinityModifier(this.getPlayer()) && this.getPlayer().isInsideOfMaterial(Material.water))
			{
				event.newSpeed = event.originalSpeed * 5.0f;
			}
		}

		if (PlayerUtil.wearingAccessory(this.getPlayer(), ItemsAether.zanite_ring) || PlayerUtil.wearingAccessory(this.getPlayer(), ItemsAether.zanite_pendant))
		{
			event.newSpeed = event.newSpeed * 5.0f; // testing code!!!! Should be removed.

			// TODO: rings don't have durability so the below code won't do anything
			// when rings do have durability this should be uncommented and the above removed.

				/*
				* PlayerAether aePlayer = PlayerAether.get(player);
				* InventoryAccessories inventory = new InventoryAccessories(aePlayer);
				*
				*
				* for (ItemStack stack : inventory.getInventory())
				* {
				*	if (stack != null && stack.getItem() == ItemsAether.zanite_ring)
				*	{
				*		event.newSpeed = 1.0f + (stack.getItemDamage() / stack.getMaxDamage() * 3);
				*	}
				* }
				* */
		}
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
