package com.gildedgames.orbis.common.containers.util;

import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketStagedInventoryChanged;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.google.common.collect.Maps;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class StagedInventory<I extends IInventory>
{
	private static final Map<String, Locator> registeredLocators = Maps.newHashMap();

	private final I stagingInv;

	private final I recordedInv;

	private final Locator locator;

	private final String locatorId;

	private final PlayerOrbisModule module;

	public StagedInventory(final PlayerOrbisModule module, final Supplier<I> inventorySupplier, final Locator locator, final String locatorId)
	{
		this.module = module;

		this.stagingInv = inventorySupplier.get();
		this.recordedInv = inventorySupplier.get();

		this.locator = locator;
		this.locatorId = locatorId;

		registeredLocators.put(this.locatorId, this.locator);
	}

	public static Locator getLocator(final String locatorId)
	{
		return registeredLocators.get(locatorId);
	}

	public String getLocatorId()
	{
		return this.locatorId;
	}

	public Locator getLocator()
	{
		return this.locator;
	}

	public void update()
	{
		final World world = this.module.getWorld();

		final List<Pair<Integer, ItemStack>> updates = world.isRemote ? Collections.emptyList() : new ArrayList<>();

		// Checks what items have been changed in the staging inventory, records them, and then
		// fires off to the effect manager
		for (int i = 0; i < this.stagingInv.getSizeInventory(); i++)
		{
			final ItemStack oldStack = this.recordedInv.getStackInSlot(i);
			final ItemStack newStack = this.stagingInv.getStackInSlot(i);

			if (!ItemStack.areItemStacksEqual(oldStack, newStack))
			{
				if (!world.isRemote)
				{
					updates.add(Pair.of(i, newStack));
				}

				this.recordedInv.setInventorySlotContents(i, newStack.isEmpty() ? ItemStack.EMPTY : newStack.copy());
			}
		}

		if (!world.isRemote)
		{
			NetworkingAether
					.sendPacketToWatching(new PacketStagedInventoryChanged(this.module.getEntity(), updates, this.getLocatorId()), this.module.getEntity(),
							true);
		}
	}

	public I get()
	{
		return this.stagingInv;
	}

	public interface Locator
	{
		StagedInventory locate(PlayerOrbisModule module);
	}
}
