package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.effects.*;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class EquipmentEffectPool<T extends IEffectProvider> implements IEffectPool<T>
{
	private final IPlayerAether player;

	private final BiMap<T, Integer> providers = HashBiMap.create();

	private final HashSet<T> active = new HashSet<>();

	private final IEffectFactory<T> factory;

	private final boolean isRemote;

	private EffectInstance instance;

	public EquipmentEffectPool(IPlayerAether player, IEffectFactory<T> factory)
	{
		this.player = player;
		this.factory = factory;

		this.isRemote = player.getEntity().getEntityWorld().isRemote;
	}

	public void update()
	{
		this.providers.forEach(this::updateProvider);

		if (this.instance != null && !this.isRemote)
		{
			this.instance.onEntityUpdate(this.player);
		}
	}

	public void onTeleport()
	{
		this.rebuildInstance();
	}

	private void updateProvider(T provider, int index)
	{
		ItemStack stack = this.player.getEquipmentModule().getInventory().getStackInSlot(index);

		Collection<IEffectPrecondition> preconditions = AetherAPI.content().items().getProperties(stack.getItem()).getEffectPreconditions();

		boolean match = preconditions.stream().allMatch((e) -> e.canApply(this.player, stack));

		if (preconditions.size() <= 0 || match)
		{
			if (this.active.add(provider))
			{
				this.rebuildInstance();
			}
		}
		else
		{
			if (this.active.remove(provider))
			{
				this.rebuildInstance();
			}
		}
	}

	/**
	 * Removes an provider from this pool, and rebuilds the state as needed.
	 * @param index The index of the provider's {@link ItemStack} in {@link IInventoryEquipment}
	 */
	public void removeInstance(int index)
	{
		T provider = this.providers.inverse().remove(index);

		this.providers.remove(provider);
		this.active.remove(provider);

		this.rebuildInstance();
	}

	/**
	 * Adds a provider to this pool, and rebuilds the state as needed.
	 * @param index The index of the provider's {@link ItemStack} in {@link IInventoryEquipment}
	 * @param provider The provider to add
	 */
	public void addInstance(int index, T provider)
	{
		this.providers.put(provider, index);

		this.updateProvider(provider, index);
	}

	/**
	 * Rebuilds the state of this effect from the active instances.
	 */
	private void rebuildInstance()
	{
		if (this.instance != null && !this.isRemote)
		{
			this.instance.onInstanceRemoved(this.player);
		}

		this.instance = this.factory.createInstance(this);

		if (!this.isRemote)
		{
			this.instance.onInstanceAdded(this.player);
		}
	}

	@Override
	public boolean isEmpty()
	{
		return this.active.size() <= 0;
	}

	@Override
	public ItemStack getProvider(T provider)
	{
		if (!this.providers.containsKey(provider))
		{
			return ItemStack.EMPTY;
		}

		return this.player.getEquipmentModule().getInventory().getStackInSlot(this.providers.get(provider));
	}

	@Override
	public Collection<T> getActiveProviders()
	{
		return this.active;
	}

	@Override
	public Optional<EffectInstance> getInstance()
	{
		return Optional.ofNullable(this.instance);
	}
}
