package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.player.IEquipmentModule;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.entities.effects.EquipmentEffectPool;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketEquipment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class EquipmentModule extends PlayerAetherModule implements IEquipmentModule
{
	private final InventoryEquipment stagingInv;

	private final InventoryEquipment recordedInv;

	private final Map<ResourceLocation, EquipmentEffectPool<IEffectProvider>> pools = new HashMap<>();

	public EquipmentModule(PlayerAether player)
	{
		super(player);

		this.stagingInv = new InventoryEquipment(player);
		this.recordedInv = new InventoryEquipment(player);
	}

	@Override
	public void onUpdate()
	{
		this.update();
	}

	@Override
	public void write(NBTTagCompound compound)
	{
		NBTTagCompound equipment = new NBTTagCompound();
		compound.setTag("EquipmentInventory", equipment);

		this.stagingInv.write(equipment);
	}

	@Override
	public void read(NBTTagCompound compound)
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
			ItemStack oldStack = this.recordedInv.getStackInSlot(i);
			ItemStack newStack = this.stagingInv.getStackInSlot(i);

			if (!ItemStack.areItemStacksEqual(oldStack, newStack))
			{
				if (!oldStack.isEmpty())
				{
					this.onEquipmentRemoved(oldStack, i);
				}

				if (!newStack.isEmpty())
				{
					this.onEquipmentAdded(newStack, i);
				}

				if (!this.getEntity().world.isRemote)
				{
					updates.add(Pair.of(i, newStack));
				}

				this.recordedInv.setInventorySlotContents(i, newStack.isEmpty() ? ItemStack.EMPTY : newStack.copy());
			}
		}

		if (!this.getEntity().world.isRemote)
		{
			NetworkingAether.sendPacketToWatching(new PacketEquipment(this.getEntity(), updates), this.getEntity(), true);
		}

		this.pools.values().forEach(EquipmentEffectPool::update);
	}

	/**
	 * Called when a new equipment item is added to the inventory.
	 * @param stack The new {@link ItemStack}
	 * @param index The inventory index of {@param stack}
	 */
	private void onEquipmentAdded(ItemStack stack, int index)
	{
		AetherAPI.content().items().getProperties(stack.getItem()).getEffectProviders().forEach(provider -> {
			EquipmentEffectPool<IEffectProvider> pool = this.pools.computeIfAbsent(provider.getFactory(), (key) ->
					new EquipmentEffectPool<>(this.getPlayer(), AetherAPI.content().effects().getFactory(key)));

			IEffectProvider copy = provider.copy();

			Validate.isTrue(copy != provider, "IEffectProvider#copy() cannot return itself");

			pool.addInstance(index, copy);
		});
	}

	/**
	 * Called when an equipment item is removed from the inventory.
	 * @param stack The old {@link ItemStack}
	 * @param index The inventory index of {@param stack}
	 */
	private void onEquipmentRemoved(ItemStack stack, int index)
	{
		AetherAPI.content().items().getProperties(stack.getItem()).getEffectProviders().forEach(provider -> {
			EquipmentEffectPool<IEffectProvider> pool = this.pools.get(provider.getFactory());
			pool.removeInstance(index);

			if (pool.isEmpty())
			{
				this.pools.remove(provider.getFactory());
			}
		});
	}

	@Override
	public Optional<IEffectPool<IEffectProvider>> getEffectPool(ResourceLocation id)
	{
		return Optional.ofNullable(this.pools.get(id));
	}

	/**
	 * Returns the collection of active effect providers. Not very useful
	 * for anything other than iteration through effect instance collections.
	 *
	 * @return A non-modifiable collection of active effect providers
	 */
	public Collection<IEffectPool<IEffectProvider>> getActivePools()
	{
		return Collections.unmodifiableCollection(this.pools.values());
	}

	/**
	 * Called when the player teleports. Notifies every effect that a teleport has occured.
	 */
	public void onTeleport()
	{
		this.pools.values().forEach(EquipmentEffectPool::onTeleport);
	}

	@Override
	public IInventoryEquipment getInventory()
	{
		return this.stagingInv;
	}

	@Override
	public boolean isEffectActive(IEffectFactory<?> effect)
	{
		Optional<IEffectPool<IEffectProvider>> pool = this.getEffectPool(effect.getIdentifier());

		return pool.isPresent() && !pool.get().isEmpty();
	}
}
