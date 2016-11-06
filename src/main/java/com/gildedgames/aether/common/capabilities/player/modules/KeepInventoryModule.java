package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.common.capabilities.player.ItemSlot;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.registry.minecraft.DimensionsAether;
import com.gildedgames.aether.common.util.io.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

import java.util.List;

public class KeepInventoryModule extends PlayerAetherModule
{

	private List<ItemSlot> mainInvOnDeath = Lists.newArrayList();

	private List<ItemSlot> armorInvOnDeath = Lists.newArrayList();

	private List<ItemSlot> offhandInvOnDeath = Lists.newArrayList();

	public KeepInventoryModule(PlayerAetherImpl playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onDeath(LivingDeathEvent event)
	{
		if (!this.getPlayer().worldObj.isRemote)
		{
			DimensionType type = this.getPlayer().getEntityWorld().provider.getDimensionType();

			if (type == DimensionsAether.AETHER || type == DimensionsAether.SLIDER_LABYRINTH)
			{
				this.mainInvOnDeath.clear();
				this.armorInvOnDeath.clear();
				this.offhandInvOnDeath.clear();

				this.recordInventory(this.getPlayer().inventory.mainInventory, this.mainInvOnDeath);
				this.recordInventory(this.getPlayer().inventory.armorInventory, this.armorInvOnDeath);
				this.recordInventory(this.getPlayer().inventory.offHandInventory, this.offhandInvOnDeath);
			}
		}
	}

	@Override
	public void onDrops(PlayerDropsEvent event)
	{
		if (!this.getPlayer().worldObj.isRemote)
		{
			DimensionType type = this.getPlayer().getEntityWorld().provider.getDimensionType();

			this.reapplyInventory(this.mainInvOnDeath, this.getPlayer().inventory.mainInventory);
			this.reapplyInventory(this.armorInvOnDeath, this.getPlayer().inventory.armorInventory);
			this.reapplyInventory(this.offhandInvOnDeath, this.getPlayer().inventory.offHandInventory);

			this.getPlayer().inventory.markDirty();

			if (type == DimensionsAether.AETHER || type == DimensionsAether.SLIDER_LABYRINTH)
			{
				event.setCanceled(true);
			}
		}
	}

	@Override
	public void onRespawn()
	{
		if (!this.getPlayer().worldObj.isRemote)
		{
			DimensionType type = this.getPlayer().getEntityWorld().provider.getDimensionType();

			if (type == DimensionsAether.AETHER || type == DimensionsAether.SLIDER_LABYRINTH)
			{
				this.reapplyInventory(this.mainInvOnDeath, this.getPlayer().inventory.mainInventory);
				this.reapplyInventory(this.armorInvOnDeath, this.getPlayer().inventory.armorInventory);
				this.reapplyInventory(this.offhandInvOnDeath, this.getPlayer().inventory.offHandInventory);

				this.getPlayer().inventory.markDirty();

				this.getPlayer().addChatComponentMessage(new TextComponentString("A mysterious force returns your items to you."));
			}

			this.mainInvOnDeath.clear();
			this.armorInvOnDeath.clear();
			this.offhandInvOnDeath.clear();
		}
	}

	private void recordInventory(ItemStack[] stacks, List<ItemSlot> recordTo)
	{
		for (int i = 0; i < stacks.length; ++i)
		{
			if (stacks[i] != null)
			{
				recordTo.add(new ItemSlot(i, stacks[i]));
			}
		}
	}

	private void reapplyInventory(List<ItemSlot> reapplyFrom, ItemStack[] reapplyTo)
	{
		if (reapplyFrom.isEmpty())
		{
			return;
		}

		for (ItemSlot slot : reapplyFrom)
		{
			reapplyTo[slot.getSlot()] = slot.getStack();
		}
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTHelper.fullySerializeList("mainInvOnDeath", this.mainInvOnDeath, tag);
		NBTHelper.fullySerializeList("armorInvOnDeath", this.armorInvOnDeath, tag);
		NBTHelper.fullySerializeList("offhandInvOnDeath", this.offhandInvOnDeath, tag);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.mainInvOnDeath = NBTHelper.fullyDeserializeList("mainInvOnDeath", tag, this.mainInvOnDeath);
		this.armorInvOnDeath = NBTHelper.fullyDeserializeList("armorInvOnDeath", tag, this.armorInvOnDeath);
		this.offhandInvOnDeath = NBTHelper.fullyDeserializeList("offhandInvOnDeath", tag, this.offhandInvOnDeath);
	}


}
