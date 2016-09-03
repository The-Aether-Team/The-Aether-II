package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.api.capabilites.items.effects.IItemEffectsCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.EquipmentChangedPacket;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EquipmentModule extends PlayerAetherModule
{

	public static class ItemSlot
	{
		private final int slot;

		private final ItemStack stack;

		public ItemSlot(int slot, ItemStack stack)
		{
			this.slot = slot;
			this.stack = stack;
		}

		public int getSlot()
		{
			return this.slot;
		}

		public ItemStack getStack()
		{
			return this.stack;
		}
	}

	private InventoryEquipment equipment;

	private Set<ItemSlot> dirties = new HashSet<>();

	public EquipmentModule(IPlayerAetherCapability playerAether, InventoryEquipment equipment)
	{
		super(playerAether);

		this.equipment = equipment;

		this.dirties.add(new ItemSlot(0, null));
		this.dirties.add(new ItemSlot(1, null));
		this.dirties.add(new ItemSlot(2, null));
		this.dirties.add(new ItemSlot(3, null));
	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		this.handleEquipmentChanges();

		IEntityEffectsCapability effects = EntityEffects.get(this.getPlayer());

		List<ItemSlot> slotsToAdd = Lists.newArrayList();
		List<ItemSlot> slotsToRemove = Lists.newArrayList();

		for (int index = 0; index < this.getPlayer().inventory.armorInventory.length; index++)
		{
			ItemStack stack = this.getPlayer().inventory.armorInventory[index];

			if (stack != null && !stack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
			{
				continue;
			}

			if (stack == null)
			{
				for (ItemSlot slot : this.dirties)
				{
					if (slot != null)
					{
						if (slot.getSlot() == index && slot.getStack() != null)
						{
							IItemEffectsCapability slotEffects = slot.getStack().getCapability(AetherCapabilities.ITEM_EFFECTS, null);

							for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : slotEffects.getEffectPairs())
							{
								EntityEffectProcessor processor = effect.getLeft();
								EntityEffectInstance instance = effect.getRight();

								if (effects.hasInstance(processor, instance))
								{
									effects.removeInstance(processor, instance);

									slotsToRemove.add(slot);
									slotsToAdd.add(new ItemSlot(index, null));
								}
							}
						}
					}
				}

				continue;
			}

			IItemEffectsCapability itemEffects = stack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

			for (ItemSlot slot : this.dirties)
			{
				if (slot != null)
				{
					if (slot.getSlot() == index && slot.getStack() != stack)
					{
						if (slot.getStack() != null)
						{
							IItemEffectsCapability slotEffects = slot.getStack().getCapability(AetherCapabilities.ITEM_EFFECTS, null);

							for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : slotEffects.getEffectPairs())
							{
								EntityEffectProcessor processor = effect.getLeft();
								EntityEffectInstance instance = effect.getRight();

								if (effects.hasInstance(processor, instance))
								{
									effects.removeInstance(processor, instance);
								}
							}
						}

						for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : itemEffects.getEffectPairs())
						{
							EntityEffectProcessor processor = effect.getLeft();
							EntityEffectInstance instance = effect.getRight();

							if (!effects.hasInstance(processor, instance))
							{
								effects.addInstance(processor, instance);
							}
						}

						slotsToRemove.add(slot);
						slotsToAdd.add(new ItemSlot(index, stack));
					}
				}
			}
		}

		this.dirties.removeAll(slotsToRemove);
		this.dirties.addAll(slotsToAdd);
	}

	@Override
	public void onDrops(LivingDropsEvent event)
	{
		if (!this.getPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory"))
		{
			this.getPlayer().captureDrops = true;

			this.equipment.dropAllItems();

			this.getPlayer().captureDrops = false;
		}
	}

	@Override
	public void onHurt(LivingHurtEvent event)
	{
		if (!event.getSource().isUnblockable())
		{
			for (ItemStack stack : this.getPlayer().inventory.armorInventory)
			{
				if (stack != null && stack.getItem() instanceof ItemAetherArmor)
				{
					event.setAmount(event.getAmount() - ((ItemAetherArmor) stack.getItem()).getExtraDamageReduction(stack));
				}
			}
		}
	}

	public void sendEquipmentChanges(Set<InventoryEquipment.PendingItemChange> dirties)
	{
		List<Pair<Integer, ItemStack>> updates = new ArrayList<>();

		for (InventoryEquipment.PendingItemChange change : dirties)
		{
			updates.add(Pair.of(change.getSlot(), change.getAfter()));
		}

		NetworkingAether.sendPacketToWatching(new EquipmentChangedPacket(this.getPlayer(), updates), this.getPlayer());
	}

	public void handleEquipmentChanges()
	{
		if (this.equipment.getDirties().size() > 0)
		{
			Set<InventoryEquipment.PendingItemChange> changes = this.equipment.getDirties();

			if (!this.getPlayer().worldObj.isRemote)
			{
				this.sendEquipmentChanges(changes);
			}

			for (InventoryEquipment.PendingItemChange change : changes)
			{
				ItemStack beforeStack = change.getBefore();

				this.handleEffectChanges(beforeStack, change);
			}

			changes.clear();
		}
	}

	private void handleEffectChanges(ItemStack before, InventoryEquipment.PendingItemChange change)
	{
		IEntityEffectsCapability effects = EntityEffects.get(this.getPlayer());

		if (before != null && before.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
		{
			IItemEffectsCapability itemEffects = before.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

			for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : itemEffects.getEffectPairs())
			{
				EntityEffectProcessor processor = effect.getLeft();
				EntityEffectInstance instance = effect.getRight();

				effects.removeInstance(processor, instance);
			}
		}

		if (!this.getPlayer().worldObj.isRemote)
		{
			ItemStack afterStack = change.getAfter();

			if (afterStack != null && afterStack != before && afterStack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
			{
				IItemEffectsCapability itemEffects = afterStack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

				for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : itemEffects.getEffectPairs())
				{
					EntityEffectProcessor processor = effect.getLeft();
					EntityEffectInstance instance = effect.getRight();

					effects.addInstance(processor, instance);
				}
			}
		}
	}

}
