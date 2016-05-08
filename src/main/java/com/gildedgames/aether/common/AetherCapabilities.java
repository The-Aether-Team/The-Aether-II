package com.gildedgames.aether.common;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.items.effects.ItemEffects;
import com.gildedgames.aether.common.items.effects.ItemEffectsProvider;
import com.gildedgames.aether.common.items.properties.ItemProperties;
import com.gildedgames.aether.common.items.properties.ItemPropertiesProvider;
import com.gildedgames.aether.common.entities.player.PlayerAetherBase;
import com.gildedgames.aether.common.entities.player.PlayerAether;
import com.gildedgames.aether.common.entities.player.PlayerAetherProvider;
import com.gildedgames.aether.common.items.effects.ItemEffectsBase;
import com.gildedgames.aether.common.items.properties.ItemPropertiesBase;
import com.gildedgames.aether.common.items.ItemRarity;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class AetherCapabilities
{

	@CapabilityInject(ItemEffectsBase.class)
	public static final Capability<ItemEffectsBase> ITEM_EFFECTS = null;

	@CapabilityInject(ItemPropertiesBase.class)
	public static final Capability<ItemPropertiesBase> ITEM_PROPERTIES = null;

	@CapabilityInject(PlayerAetherBase.class)
	public static final Capability<PlayerAetherBase> PLAYER_DATA = null;

	public void init()
	{
		CapabilityManager.INSTANCE.register(ItemEffectsBase.class, new ItemEffects.Storage(), ItemEffects.class);
		CapabilityManager.INSTANCE.register(ItemPropertiesBase.class, new ItemProperties.Storage(), ItemProperties.class);
		CapabilityManager.INSTANCE.register(PlayerAetherBase.class, new PlayerAether.Storage(), PlayerAether.class);
	}

	@SubscribeEvent
	public void onTooltipConstruction(ItemTooltipEvent event)
	{
		if (event.itemStack != null)
		{
			if (event.itemStack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
			{
				ItemPropertiesBase props = event.itemStack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

				if (props != null && props.getRarity() != ItemRarity.NONE)
				{
					event.toolTip.add(I18n.format(props.getRarity().getUnlocalizedName()));
				}
			}

			if (event.itemStack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
			{
				ItemEffectsBase effects = event.itemStack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

				if (effects != null)
				{
					if (effects.getEffectPairs() == null || effects.getEffectPairs().size() <= 0)
					{
						event.toolTip.add(I18n.format("ability.cosmetic"));
					}
					else
					{
						for (Pair<EffectProcessor, EffectInstance> effect : effects.getEffectPairs())
						{
							EffectProcessor processor = effect.getLeft();
							EffectInstance instance = effect.getRight();

							List<String> localizedDesc = new ArrayList<>();

							for (String line : processor.getUnlocalizedDesc(event.entityPlayer, instance))
							{
								localizedDesc.add(I18n.format(line, (Object[]) processor.getFormatParameters(event.entityPlayer, instance)));
							}

							event.toolTip.addAll(localizedDesc);

							for (EffectRule rule : instance.getRules())
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
				ItemPropertiesBase props = event.itemStack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

				if (props != null)
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
			ItemPropertiesBase props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);
			PlayerAetherBase aePlayer = PlayerAether.getPlayer(event.entityPlayer);

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
	}

	@SubscribeEvent
    public void onTELoad(AttachCapabilitiesEvent.Item event)
    {
        event.addCapability(AetherCore.getResource("ItemStackEffects"), new ItemEffectsProvider(event.getItemStack()));
        event.addCapability(AetherCore.getResource("ItemStackProperties"), new ItemPropertiesProvider(event.getItemStack()));
    }
}
