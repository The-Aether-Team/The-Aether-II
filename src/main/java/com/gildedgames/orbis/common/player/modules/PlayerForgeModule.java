package com.gildedgames.orbis.common.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.containers.inventory.InventoryForge;
import com.gildedgames.orbis.common.network.packets.PacketForgeInventoryChanged;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerForgeModule extends PlayerAetherModule
{
	private final InventoryForge stagingInv;

	private final InventoryForge recordedInv;

	public PlayerForgeModule(final PlayerAether player)
	{
		super(player);

		this.stagingInv = new InventoryForge(player.getEntity());
		this.recordedInv = new InventoryForge(player.getEntity());
	}

	@Override
	public void onUpdate()
	{
		this.update();
	}

	@Override
	public void write(final NBTTagCompound compound)
	{
		final NBTTagCompound equipment = new NBTTagCompound();
		compound.setTag("EquipmentInventory", equipment);

		this.stagingInv.write(equipment);
	}

	@Override
	public void read(final NBTTagCompound compound)
	{
		if (compound.hasKey("EquipmentInventory"))
		{
			this.stagingInv.read(compound.getCompoundTag("EquipmentInventory"));
		}
	}

	private void update()
	{
		final List<Pair<Integer, ItemStack>> updates = this.getEntity().world.isRemote ? Collections.emptyList() : new ArrayList<>();

		// Checks what items have been changed in the staging inventory, records them, and then
		// fires off to the effect manager
		for (int i = 0; i < this.stagingInv.getSizeInventory(); i++)
		{
			final ItemStack oldStack = this.recordedInv.getStackInSlot(i);
			final ItemStack newStack = this.stagingInv.getStackInSlot(i);

			if (!ItemStack.areItemStacksEqual(oldStack, newStack))
			{
				if (!this.getEntity().world.isRemote)
				{
					updates.add(Pair.of(i, newStack));
				}

				this.recordedInv.setInventorySlotContents(i, newStack.isEmpty() ? ItemStack.EMPTY : newStack.copy());
			}
		}

		if (!this.getEntity().world.isRemote)
		{
			NetworkingAether.sendPacketToWatching(new PacketForgeInventoryChanged(this.getEntity(), updates), this.getEntity(), true);
		}
	}

	public IInventory getForgeInventory()
	{
		return this.stagingInv;
	}

}