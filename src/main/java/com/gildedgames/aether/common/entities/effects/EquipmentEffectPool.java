package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.effects.*;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class EquipmentEffectPool<T extends IEffectProvider> implements IEffectPool<T>
{
	private final IPlayerAether player;

	private final Multimap<Pair<Integer, InventoryProvider>, T> providers = HashMultimap.create();

	private final Map<T, Pair<Integer, InventoryProvider>> providersInverse = new HashMap<>();

	private final HashSet<T> active = new HashSet<>();

	private final IEffectFactory<T> factory;

	private final boolean isRemote;

	private EffectInstance instance;

	public EquipmentEffectPool(IPlayerAether player, IEffectFactory<T> factory)
	{
		this.player = player;
		this.factory = factory;

		this.isRemote() = player.getEntity().getEntityWorld().isRemote;
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

	private void updateProvider(Pair<Integer, InventoryProvider> inventoryIndexPair, T provider)
	{
		PlayerAether playerAether = PlayerAether.getPlayer(this.player.getEntity());

		Integer index = inventoryIndexPair.getKey();
		InventoryProvider inventoryProvider = inventoryIndexPair.getValue();

		ItemStack stack = inventoryProvider.provide(playerAether).getStackInSlot(index);

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
	 * @param inventoryIndexPair The index and inventory of the provider's {@link ItemStack}
	 */
	public void removeInstances(Pair<Integer, InventoryProvider> inventoryIndexPair)
	{
		Collection<T> provider = this.providers.removeAll(inventoryIndexPair);

		if (provider == null || provider.isEmpty())
		{
			return;
		}

		for (T inst : provider)
		{
			this.active.remove(inst);

			this.providersInverse.remove(inst);
		}

		this.rebuildInstance();
	}

	/**
	 * Adds a provider to this pool, and rebuilds the state as needed.
	 * @param inventoryIndexPair The index and inventory of the provider's {@link ItemStack}
	 * @param provider The provider to add
	 */
	public void addInstance(Pair<Integer, InventoryProvider> inventoryIndexPair, T provider)
	{
		this.providers.put(inventoryIndexPair, provider);
		this.providersInverse.put(provider, inventoryIndexPair);

		this.updateProvider(inventoryIndexPair, provider);
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
		Pair<Integer, InventoryProvider> inventoryIndexPair = this.providersInverse.get(provider);

		return inventoryIndexPair.getValue().provide(this.player)
				.getStackInSlot(inventoryIndexPair.getKey());
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
