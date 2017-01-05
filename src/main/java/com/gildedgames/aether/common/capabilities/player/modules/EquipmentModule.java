package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.equipment.effects.EffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProcessor;
import com.gildedgames.aether.api.items.equipment.effects.IEffectState;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.item.EquipmentRegistry;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.*;

public class EquipmentModule extends PlayerAetherModule
{
	private Map<ResourceLocation, EffectPool> pools = new HashMap<>();

	private Collection<Item> watchedItems = new ArrayList<>();

	public EquipmentModule(PlayerAetherImpl playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		this.updateInventory();

		this.tickEquipment();
	}

	private void updateInventory()
	{
		InventoryEquipment equipment = this.getPlayerAether().getEquipmentInventory();

		// All currently watched equipment is added. If we don't find an item from this list
		// in the inventory, it will be removed.
		ArrayList<Item> removed = new ArrayList<>();
		removed.addAll(this.watchedItems);

		for (int i = 0; i < equipment.getSizeInventory(); i++)
		{
			ItemStack stack = equipment.getStackInSlot(i);

			if (stack == null)
			{
				continue;
			}

			Item item = stack.getItem();

			// If we weren't watching the item before, activate it's effects
			if (!removed.remove(item))
			{
				this.onEquipmentAdded(item);
			}
		}

		// Remove all equipment that wasn't found
		for (Item item : removed)
		{
			this.onEquipmentRemoved(item);
		}
	}

	/**
	 * Updates every active effect pool.
	 */
	private void tickEquipment()
	{
		for (EffectPool pool : this.pools.values())
		{
			pool.getProcessor().onEntityUpdate(this, pool.getState());
		}
	}

	/**
	 * Called when a new equipment item is added to the inventory.
	 * @param item The new item
	 */
	private void onEquipmentAdded(Item item)
	{
		this.watchedItems.add(item);

		this.getItemEffects(item).forEach(this::addEffect);
	}

	/**
	 * Called when an equipment item is removed from the inventory.
	 * @param item The item removed.
	 */
	private void onEquipmentRemoved(Item item)
	{
		this.watchedItems.remove(item);

		this.getItemEffects(item).forEach(this::removeEffect);
	}

	/**
	 * Returns the {@link EffectPool} for the effect with {@param id}.
	 * @param id The effect's ID
	 * @return An @{link Optional} containing the pool if it exists
	 */
	public Optional<EffectPool> getEffectPool(ResourceLocation id)
	{
		return Optional.ofNullable(this.pools.get(id));
	}

	/**
	 * Returns the collection of active effect providers. Not very useful
	 * for anything other than iteration through effect instance collections.
	 * @return A non-modifiable collection of active effect providers
	 */
	public Collection<ResourceLocation> getActiveEffectProviders()
	{
		return Collections.unmodifiableCollection(this.pools.keySet());
	}

	/**
	 * Returns a list of effects an item can provide.
	 * @param item The item
	 * @return A collection of effect instances, empty if it provides none
	 */
	private Collection<IEffectInstance> getItemEffects(Item item)
	{
		IItemProperties properties = AetherAPI.items().getProperties(item);

		if (properties.getEquipmentProperties().isPresent())
		{
			return properties.getEquipmentProperties().get().getEffectInstances();
		}

		return Collections.emptyList();
	}

	/**
	 * Removes the {@param instance} from the active pool of effects.
	 * @param instance The {@link IEffectInstance} to remove
	 */
	private void removeEffect(IEffectInstance instance)
	{
		EffectPool pool = this.pools.get(instance.getProcessor());

		if (pool == null)
		{
			AetherCore.LOGGER.warn("An attempt was made to remove an effect instance from effect pool '{}', but it isn't active", instance.getProcessor().toString());
			return;
		}

		pool.removeInstance(instance);

		if (pool.isEmpty())
		{
			this.pools.remove(instance.getProcessor());
		}
	}

	/**
	 * Adds {@param instance} to the active pool of effects.
	 * @param instance The {@link IEffectInstance} to add
	 */
	private void addEffect(IEffectInstance instance)
	{
		EffectPool pool = this.pools.get(instance.getProcessor());

		if (pool == null)
		{
			IEffectProcessor<IEffectInstance, IEffectState> processor = AetherAPI.equipment().getEffect(instance.getProcessor());

			this.pools.put(instance.getProcessor(), pool = new EffectPool(processor));
		}

		pool.addInstance(instance);
	}
}
