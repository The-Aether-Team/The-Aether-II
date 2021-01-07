package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.items.EffectActivator;
import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.items.properties.IItemProperties;
import com.gildedgames.aether.api.player.IEquipmentModule;
import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.entities.effects.EquipmentEffectPool;
import com.gildedgames.aether.common.entities.effects.IEffectResistanceHolder;
import com.gildedgames.aether.common.entities.effects.InventoryProvider;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import com.gildedgames.aether.common.items.accessories.ItemAccessory;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketEquipment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class PlayerEquipmentModule extends PlayerAetherModule implements IEquipmentModule, IPlayerAetherModule.Serializable
{
	private static final InventoryProvider EQUIPMENT_INV_PROVIDER = new InventoryProvider(AetherCore.getResource("equipmentInv"),
			(playerAether -> playerAether.getModule(PlayerEquipmentModule.class).getInventory()));

	private static final InventoryProvider NORMAL_INV_PROVIDER = new InventoryProvider(AetherCore.getResource("normalInv"),
			(playerAether -> playerAether.getEntity().inventory));

	private final InventoryEquipment stagingInv;

	private final InventoryEquipment recordedInv;

	private final Map<ResourceLocation, EquipmentEffectPool<IEffectProvider>> pools = new HashMap<>();

	private ItemStack lastHeldStack = ItemStack.EMPTY;

	private int lastHeldStackIndex = -1;

	private Map<ItemStack, Boolean> applicationTracker = new HashMap<>();

	public PlayerEquipmentModule(PlayerAether player)
	{
		super(player);

		this.stagingInv = new InventoryEquipment(player);
		this.recordedInv = new InventoryEquipment(player);
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		ItemStack mainHeldStack = this.getEntity().getHeldItemMainhand();
		int mainHeldStackIndex = this.getSlotFor(mainHeldStack);

		if (this.lastHeldStackIndex != -1)
		{
			if ((this.lastHeldStackIndex != mainHeldStackIndex || !this.lastHeldStack.isEmpty()) && !ItemStack.areItemStacksEqual(this.lastHeldStack, mainHeldStack))
			{
				this.deactivateEquipmentEffects(this.lastHeldStack, Pair.of(this.lastHeldStackIndex, NORMAL_INV_PROVIDER), EffectActivator.WHEN_HELD);
			}

			if (mainHeldStack.isEmpty())
			{
				this.deactivateEquipmentEffects(this.lastHeldStack, Pair.of(mainHeldStackIndex, NORMAL_INV_PROVIDER), EffectActivator.WHEN_HELD);

				if (this.lastHeldStackIndex != mainHeldStackIndex)
				{
					for (int i = 0; i < this.getEntity().inventory.getSizeInventory(); i++)
					{
						this.deactivateEquipmentEffects(this.getEntity().inventory.getStackInSlot(i), Pair.of(this.lastHeldStackIndex, NORMAL_INV_PROVIDER), EffectActivator.WHEN_HELD);
					}
				}
			}
		}

		if (!mainHeldStack.isEmpty() && !ItemStack.areItemStacksEqual(this.lastHeldStack, mainHeldStack))
		{
			this.activateEquipmentEffects(mainHeldStack, Pair.of(mainHeldStackIndex, NORMAL_INV_PROVIDER), EffectActivator.WHEN_HELD);
		}

		this.lastHeldStack = this.getEntity().getHeldItemMainhand();
		this.lastHeldStackIndex = this.getSlotFor(this.lastHeldStack);


		IAetherStatusEffectPool statusEffectPool = event.player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

		if (statusEffectPool == null)
		{
			return;
		}

		final List<Pair<Integer, ItemStack>> updates = this.getEntity().world.isRemote ? null : new ArrayList<>();

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
					this.deactivateEquipmentEffects(oldStack, Pair.of(i, EQUIPMENT_INV_PROVIDER), EffectActivator.WHEN_EQUIPPED);

					statusEffectPool.resetAllResistances();
				}

				if (!newStack.isEmpty())
				{
					this.activateEquipmentEffects(newStack, Pair.of(i, EQUIPMENT_INV_PROVIDER), EffectActivator.WHEN_EQUIPPED);
				}

				if (updates != null)
				{
					updates.add(Pair.of(i, newStack));
				}

				this.recordedInv.setInventorySlotContents(i, newStack.isEmpty() ? ItemStack.EMPTY : newStack.copy());
			}

			if (!newStack.isEmpty())
			{
				if (newStack.getItem() instanceof ItemAccessory)
				{
					ItemAccessory accessory = (ItemAccessory) newStack.getItem();

					if (!accessory.getStatusEffects().isEmpty())
					{
						for (Map.Entry<StatusEffect, Double> effect : accessory.getStatusEffects().entrySet())
						{
							applicationTracker.putIfAbsent(newStack, false);

							IAetherStatusEffects actualEffect = statusEffectPool.createEffect(effect.getKey().getEffectType().name, event.player);

							if (!applicationTracker.get(newStack))
							{
								statusEffectPool.addResistanceToEffect(actualEffect.getEffectType(), effect.getValue());

								if (statusEffectPool.getResistanceToEffect(actualEffect.getEffectType()) != 1.0D)
								{
									applicationTracker.put(newStack, true);
								}
							}
							else
							{
								if (statusEffectPool.getResistanceToEffect(actualEffect.getEffectType()) == 1.0D)
								{
									applicationTracker.put(newStack, false);
								}
							}
						}
					}
				}
			}
		}

		if (updates != null && !updates.isEmpty())
		{
			NetworkingAether.sendPacketToWatching(new PacketEquipment(this.getEntity(), updates), this.getEntity(), true);
		}

		this.pools.values().forEach(EquipmentEffectPool::update);
	}

	private int getSlotFor(ItemStack stack)
	{
		for (int i = 0; i < this.getEntity().inventory.mainInventory.size(); ++i)
		{
			if (!(this.getEntity().inventory.mainInventory.get(i)).isEmpty() && this.stackEqualExact(stack, this.getEntity().inventory.mainInventory.get(i)))
			{
				return i;
			}
		}

		return -1;
	}

	private boolean stackEqualExact(ItemStack stack1, ItemStack stack2)
	{
		return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
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

	/**
	 * Called when a new equipment item is added to the inventory.
	 * @param stack The new {@link ItemStack}
	 * @param inventoryIndexPair The inventory and index of {@param stack}
	 */
	private void activateEquipmentEffects(ItemStack stack, Pair<Integer, InventoryProvider> inventoryIndexPair, EffectActivator activator)
	{
		IItemProperties properties = AetherAPI.content().items().getProperties(stack.getItem());

		if (!properties.getEffectActivators().contains(activator))
		{
			return;
		}

		properties.getEffectProviders().forEach(provider -> {
			EquipmentEffectPool<IEffectProvider> pool = this.pools.computeIfAbsent(provider.getFactory(), (key) ->
					new EquipmentEffectPool<>(this.getPlayer(), AetherAPI.content().effects().getFactory(key)));

			IEffectProvider copy = provider.copy();

			Validate.isTrue(copy != provider, "IEffectProvider#copy() cannot return itself");

			pool.addInstance(inventoryIndexPair, copy);
		});
	}

	/**
	 * Called when an equipment item is removed from the inventory.
	 * @param stack The old {@link ItemStack}
	 * @param inventoryIndexPair The inventory and index of {@param stack}
	 */
	private void deactivateEquipmentEffects(ItemStack stack, Pair<Integer, InventoryProvider> inventoryIndexPair, EffectActivator activator)
	{
		IItemProperties properties = AetherAPI.content().items().getProperties(stack.getItem());

		if (!properties.getEffectActivators().contains(activator))
		{
			return;
		}

		this.pools.entrySet().removeIf(pair -> {
			EquipmentEffectPool<IEffectProvider> pool = pair.getValue();
			pool.removeInstances(inventoryIndexPair);

			return pool.isEmpty();
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

	@Override
	public ResourceLocation getIdentifier()
	{
		return AetherCore.getResource("equipment");
	}
}
