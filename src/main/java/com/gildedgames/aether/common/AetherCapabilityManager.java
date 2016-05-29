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
import com.gildedgames.aether.api.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.api.entities.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.entities.effects.EntityEffectRule;
import com.gildedgames.aether.api.items.properties.ItemRarity;
import com.gildedgames.aether.api.items.IItemEffectsCapability;
import com.gildedgames.aether.api.items.IItemPropertiesCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class AetherCapabilityManager
{
	public void init()
	{
		CapabilityManager.INSTANCE.register(IItemEffectsCapability.class, new ItemEffects.Storage(), ItemEffects.class);
		CapabilityManager.INSTANCE.register(IItemPropertiesCapability.class, new ItemProperties.Storage(), ItemProperties.class);
		CapabilityManager.INSTANCE.register(IPlayerAetherCapability.class, new PlayerAether.Storage(), PlayerAether.class);
		CapabilityManager.INSTANCE.register(IEntityEffectsCapability.class, new EntityEffects.Storage(), EntityEffects.class);
	}

	@SubscribeEvent
	public void onTooltipConstruction(ItemTooltipEvent event)
	{
		if (event.itemStack != null)
		{
			if (event.itemStack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
			{
				IItemPropertiesCapability props = event.itemStack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

				if (props != null && props.getRarity() != ItemRarity.NONE)
				{
					event.toolTip.add(I18n.format(props.getRarity().getUnlocalizedName()));
				}
			}

			if (event.itemStack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
			{
				IItemEffectsCapability effects = event.itemStack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

				if (effects != null)
				{
					if (effects.getEffectPairs() == null || effects.getEffectPairs().size() <= 0)
					{
						event.toolTip.add(I18n.format("ability.cosmetic"));
					}
					else
					{
						for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : effects.getEffectPairs())
						{
							EntityEffectProcessor processor = effect.getLeft();
							EntityEffectInstance instance = effect.getRight();

							List<String> localizedDesc = new ArrayList<>();

							for (String line : processor.getUnlocalizedDesc(event.entityPlayer, instance))
							{
								localizedDesc.add(I18n.format(line, (Object[]) processor.getFormatParameters(event.entityPlayer, instance)));
							}

							event.toolTip.addAll(localizedDesc);

							for (EntityEffectRule rule : instance.getRules())
							{
								for (String line : rule.getUnlocalizedDesc())
								{
									event.toolTip.add(EnumChatFormatting.GRAY + "\u2022 " + I18n.format(line));
								}
							}
						}
					}
				}
			}

			if (event.itemStack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
			{
				IItemPropertiesCapability props = event.itemStack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

				if (props.isEquippable())
				{
					if (props.getRarity() != ItemRarity.NONE)
					{
						event.toolTip.add("");
					}

					event.toolTip.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + I18n.format(props.getEquipmentType().getUnlocalizedName()));
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		ItemStack stack = event.entityPlayer.getCurrentEquippedItem();

		if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
		{
			IItemPropertiesCapability props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);
			IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.entityPlayer);

			if (props != null && props.isEquippable())
			{
				int nextEmptySlot = aePlayer.getEquipmentInventory().getNextEmptySlotForType(props.getEquipmentType());

				if (nextEmptySlot != -1)
				{
					aePlayer.getEquipmentInventory().setInventorySlotContents(nextEmptySlot, stack.copy());

					if (!event.entityPlayer.capabilities.isCreativeMode)
					{
						event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
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
