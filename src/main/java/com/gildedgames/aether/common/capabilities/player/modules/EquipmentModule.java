package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.entities.effects.EffectPool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class EquipmentModule extends PlayerAetherModule
{
	private final InventoryEquipment equipmentInventory;

	private Map<ResourceLocation, EffectPool> pools = new HashMap<>();

	private Collection<Item> watchedItems = new ArrayList<>();

	public EquipmentModule(PlayerAether player)
	{
		super(player);

		this.equipmentInventory = new InventoryEquipment(player);
	}

	@Override
	public void onUpdate()
	{
		this.updateInventory();

		this.tickEquipment();
	}

	@Override
	public void write(NBTTagCompound compound)
	{
		NBTTagCompound equipment = new NBTTagCompound();
		compound.setTag("EquipmentInventory", equipment);

		this.equipmentInventory.write(equipment);
	}

	@Override
	public void read(NBTTagCompound compound)
	{
		if (compound.hasKey("EquipmentInventory"))
		{
			this.equipmentInventory.read(compound.getCompoundTag("EquipmentInventory"));
		}
	}

	private void updateInventory()
	{
		IInventoryEquipment equipment = this.getPlayer().getEquipmentInventory();

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
			pool.getInstance().onEntityUpdate(this.getPlayer());
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
	private Collection<IEffectProvider> getItemEffects(Item item)
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
	 * @param instance The {@link IEffectProvider} to remove
	 */
	private void removeEffect(IEffectProvider instance)
	{
		EffectPool pool = this.pools.get(instance.getFactory());

		if (pool == null)
		{
			AetherCore.LOGGER.warn("An attempt was made to remove an effect instance from effect pool '{}', but it isn't active", instance.getFactory().toString());
			return;
		}

		pool.removeInstance(instance);

		if (pool.isEmpty())
		{
			this.pools.remove(instance.getFactory());
		}
	}

	/**
	 * Adds {@param instance} to the active pool of effects.
	 * @param instance The {@link IEffectProvider} to add
	 */
	private void addEffect(IEffectProvider instance)
	{
		EffectPool pool = this.pools.get(instance.getFactory());

		if (pool == null)
		{
			IEffect<IEffectProvider> factory = AetherAPI.equipment().getFactory(instance.getFactory());

			this.pools.put(instance.getFactory(), pool = new EffectPool(factory));
		}

		pool.addInstance(instance);
	}

	public IInventoryEquipment getInventory()
	{
		return this.equipmentInventory;
	}
}
