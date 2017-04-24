package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.entities.effects.EffectPool;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class EquipmentModule extends PlayerAetherModule
{
	private final InventoryEquipment stagingInv;

	private final InventoryEquipment recordedInv;

	private Map<ResourceLocation, EffectPool> effects = new HashMap<>();

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

		this.tickEquipment();
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
		for (int i = 0; i < this.stagingInv.getSizeInventory(); i++)
		{
			ItemStack oldStack = this.recordedInv.getStackInSlot(i);
			ItemStack newStack = this.stagingInv.getStackInSlot(i);

			if (!ItemStack.areItemStacksEqual(oldStack, newStack))
			{
				if (!oldStack.isEmpty())
				{
					this.onEquipmentRemoved(oldStack);
				}

				if (!newStack.isEmpty())
				{
					this.onEquipmentAdded(newStack);
				}

				this.recordedInv.setInventorySlotContents(i, newStack.isEmpty() ? ItemStack.EMPTY : newStack.copy());
			}
		}
	}

	/**
	 * Updates every active effect pool.
	 */
	private void tickEquipment()
	{
		for (EffectPool pool : this.effects.values())
		{
			pool.getInstance().onEntityUpdate(this.getPlayer());
		}
	}

	/**
	 * Called when a new equipment item is added to the inventory.
	 * @param stack The new {@link ItemStack}
	 */
	private void onEquipmentAdded(ItemStack stack)
	{
		AetherAPI.items().getProperties(stack.getItem()).getEffectProviders().forEach(this::addEffect);
	}

	/**
	 * Called when an equipment item is removed from the inventory.
	 * @param stack The old {@link ItemStack}
	 */
	private void onEquipmentRemoved(ItemStack stack)
	{
		AetherAPI.items().getProperties(stack.getItem()).getEffectProviders().forEach(this::removeEffect);
	}

	/**
	 * Returns the {@link EffectPool} for the effect with {@param id}.
	 * @param id The effect's ID
	 * @return An @{link Optional} containing the pool if it exists
	 */
	public Optional<EffectPool> getEffectPool(ResourceLocation id)
	{
		return Optional.ofNullable(this.effects.get(id));
	}

	/**
	 * Returns the collection of active effect providers. Not very useful
	 * for anything other than iteration through effect instance collections.
	 * @return A non-modifiable collection of active effect providers
	 */
	public Collection<ResourceLocation> getActiveEffectProviders()
	{
		return Collections.unmodifiableCollection(this.effects.keySet());
	}

	/**
	 * Removes the {@param instance} from the active pool of effects.
	 * @param instance The {@link IEffectProvider} to remove
	 */
	private void removeEffect(IEffectProvider instance)
	{
		EffectPool pool = this.effects.get(instance.getFactory());

		if (pool == null)
		{
			AetherCore.LOGGER.warn("An attempt was made to remove an effect instance from effect pool '{}', but it isn't active", instance.getFactory().toString());
			return;
		}

		pool.removeInstance(instance);

		if (pool.isEmpty())
		{
			this.effects.remove(instance.getFactory());
		}
	}

	/**
	 * Adds {@param instance} to the active pool of effects.
	 * @param instance The {@link IEffectProvider} to add
	 */
	private void addEffect(IEffectProvider instance)
	{
		EffectPool pool = this.effects.get(instance.getFactory());

		if (pool == null)
		{
			IEffect<IEffectProvider> factory = AetherAPI.equipment().getFactory(instance.getFactory());

			this.effects.put(instance.getFactory(), pool = new EffectPool(this.getPlayer(), factory));
		}

		pool.addInstance(instance);
	}

	public IInventoryEquipment getInventory()
	{
		return this.stagingInv;
	}
}
