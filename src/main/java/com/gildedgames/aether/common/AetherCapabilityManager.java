package com.gildedgames.aether.common;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.entities.effects.IEntityEffectsCapability;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.entities.effects.EntityEffectsProvider;
import com.gildedgames.aether.common.items.effects.ItemEffects;
import com.gildedgames.aether.common.items.effects.ItemEffectsProvider;
import com.gildedgames.aether.common.items.properties.ItemProperties;
import com.gildedgames.aether.common.items.properties.ItemPropertiesProvider;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.player.PlayerAetherProvider;
import com.gildedgames.aether.api.items.IItemEffectsCapability;
import com.gildedgames.aether.api.items.IItemPropertiesCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AetherCapabilityManager
{
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(this);

		CapabilityManager.INSTANCE.register(IItemEffectsCapability.class, new ItemEffects.Storage(), ItemEffects.class);
		CapabilityManager.INSTANCE.register(IItemPropertiesCapability.class, new ItemProperties.Storage(), ItemProperties.class);
		CapabilityManager.INSTANCE.register(IPlayerAetherCapability.class, new PlayerAether.Storage(), PlayerAether.class);
		CapabilityManager.INSTANCE.register(IEntityEffectsCapability.class, new EntityEffects.Storage(), EntityEffects.class);
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		EntityPlayer player = event.getEntityPlayer();

		ItemStack stack = player.getActiveItemStack();

		if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
		{
			IItemPropertiesCapability props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

			IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(player);

			if (props != null && props.isEquippable())
			{
				int nextEmptySlot = aePlayer.getEquipmentInventory().getNextEmptySlotForType(props.getEquipmentType());

				if (nextEmptySlot != -1)
				{
					aePlayer.getEquipmentInventory().setInventorySlotContents(nextEmptySlot, stack.copy());

					if (!player.capabilities.isCreativeMode)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityLoad(AttachCapabilitiesEvent.Entity event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			event.addCapability(AetherCore.getResource("PlayerData"), new PlayerAetherProvider(new PlayerAether((EntityPlayer) event.getEntity())));
		}
		
		event.addCapability(AetherCore.getResource("EntityEffects"), new EntityEffectsProvider(new EntityEffects(event.getEntity())));
	}

	@SubscribeEvent
    public void onItemLoad(AttachCapabilitiesEvent.Item event)
    {
        event.addCapability(AetherCore.getResource("ItemStackEffects"), new ItemEffectsProvider(event.getItemStack()));
        event.addCapability(AetherCore.getResource("ItemStackProperties"), new ItemPropertiesProvider(event.getItemStack()));
    }
}
