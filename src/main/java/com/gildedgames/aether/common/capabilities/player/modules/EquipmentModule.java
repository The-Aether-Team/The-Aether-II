package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.api.capabilites.items.effects.IItemEffectsCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.EquipmentChangedPacket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EquipmentModule extends PlayerAetherModule
{

	private InventoryEquipment equipment;

	public EquipmentModule(IPlayerAetherCapability playerAether, InventoryEquipment equipment)
	{
		super(playerAether);

		this.equipment = equipment;
	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (!this.getPlayer().worldObj.isRemote)
		{
			this.handleEquipmentChanges();
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

	private void sendEquipmentChanges(Set<InventoryEquipment.PendingItemChange> dirties)
	{
		List<Pair<Integer, ItemStack>> updates = new ArrayList<>();

		for (InventoryEquipment.PendingItemChange change : dirties)
		{
			updates.add(Pair.of(change.getSlot(), change.getAfter()));
		}

		NetworkingAether.sendPacketToWatching(new EquipmentChangedPacket(this.getPlayer(), updates), this.getPlayer());
	}

	private void handleEquipmentChanges()
	{
		if (this.equipment.getDirties().size() > 0)
		{
			Set<InventoryEquipment.PendingItemChange> changes = this.equipment.getDirties();

			this.sendEquipmentChanges(changes);

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
