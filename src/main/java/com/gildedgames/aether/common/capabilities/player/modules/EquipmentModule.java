package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.api.capabilites.items.effects.IItemEffectsCapability;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

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

	private Map<ItemStack[], ItemSlot[]> trackedSlots = Maps.newHashMap();

	public EquipmentModule(PlayerAetherImpl playerAether, InventoryEquipment equipment)
	{
		super(playerAether);

		this.equipment = equipment;
	}

	public void resetEffects()
	{
		this.removeEffects(this.getPlayer().inventory.armorInventory);
		this.removeEffects(this.getPlayer().inventory.offHandInventory);
		this.removeEffects(this.equipment.getInventory());

		this.trackedSlots.clear();
	}

	private void removeEffects(ItemStack[] inventory)
	{
		IEntityEffectsCapability effects = EntityEffects.get(this.getPlayer());

		for (ItemSlot slot : this.trackedSlots.get(inventory))
		{
			if (slot != null && slot.getStack() != null)
			{
				IItemEffectsCapability itemEffects = slot.getStack().getCapability(AetherCapabilities.ITEM_EFFECTS, null);

				for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : itemEffects.getEffectPairs())
				{
					EntityEffectProcessor processor = effect.getLeft();
					EntityEffectInstance instance = effect.getRight();

					if (effects.hasInstance(processor, instance))
					{
						effects.removeInstance(processor, instance);
					}
				}
			}
		}
	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		this.processEffects(this.getPlayer().inventory.armorInventory);
		this.processEffects(this.getPlayer().inventory.offHandInventory);
		this.processEffects(this.equipment.getInventory());
	}

	public static List<Pair<Integer, ItemStack>> getAllEquipment(IInventoryEquipment equipment)
	{
		List<Pair<Integer, ItemStack>> stacks = new ArrayList<>();

		for (int i = 0; i < equipment.getSizeInventory(); i++)
		{
			stacks.add(Pair.of(i, equipment.getStackInSlot(i)));
		}

		return stacks;
	}

	private void processEffects(ItemStack[] inventory)
	{
		IEntityEffectsCapability effects = EntityEffects.get(this.getPlayer());

		List<ItemSlot> slotsToAdd = Lists.newArrayList();

		if (!this.trackedSlots.containsKey(inventory))
		{
			ItemSlot[] slots = new ItemSlot[inventory.length];

			for (int index = 0; index < inventory.length; index++)
			{
				slots[index] = new ItemSlot(index, null);
			}

			this.trackedSlots.put(inventory, slots);
		}

		for (int index = 0; index < inventory.length; index++)
		{
			ItemStack stack = inventory[index];

			if (stack != null && !stack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
			{
				continue;
			}

			if (stack == null)
			{
				for (ItemSlot slot : this.trackedSlots.get(inventory))
				{
					if (slot != null && slot.getSlot() == index && slot.getStack() != stack)
					{
						IItemEffectsCapability slotEffects = slot.getStack().getCapability(AetherCapabilities.ITEM_EFFECTS, null);

						for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : slotEffects.getEffectPairs())
						{
							EntityEffectProcessor processor = effect.getLeft();
							EntityEffectInstance instance = effect.getRight();

							if (effects.hasInstance(processor, instance))
							{
								effects.removeInstance(processor, instance);

								slotsToAdd.add(new ItemSlot(index, null));
							}
						}
					}
				}

				continue;
			}

			for (ItemSlot slot : this.trackedSlots.get(inventory))
			{
				if (slot != null && slot.getSlot() == index)
				{
					IItemEffectsCapability itemEffects = stack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

					for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : itemEffects.getEffectPairs())
					{
						EntityEffectProcessor processor = effect.getLeft();
						EntityEffectInstance instance = effect.getRight();

						if (!effects.hasInstance(processor, instance))
						{
							effects.addInstance(processor, instance);
						}
					}

					slotsToAdd.add(new ItemSlot(index, stack));
				}
			}
		}

		for (ItemSlot slot : slotsToAdd)
		{
			int index = slot.getSlot();

			this.trackedSlots.get(inventory)[index] = slot;
		}
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

}
