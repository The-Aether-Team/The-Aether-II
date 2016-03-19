package com.gildedgames.aether.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.commons.lang3.tuple.Pair;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.items.ItemEffectsBase;
import com.gildedgames.aether.common.items.ItemEffectsBase.ItemEffects;
import com.gildedgames.aether.common.items.ItemPropertiesBase;
import com.gildedgames.aether.common.items.ItemPropertiesBase.ItemProperties;
import com.gildedgames.aether.common.items.ItemRarity;
import com.gildedgames.aether.common.player.PlayerAether;

public class AetherCapabilities
{
	
	@CapabilityInject(ItemEffectsBase.class)
    public static final Capability<ItemEffectsBase> ITEM_EFFECTS = null;

	@CapabilityInject(ItemPropertiesBase.class)
    public static final Capability<ItemPropertiesBase> ITEM_PROPERTIES = null;
	
	public void init()
	{
		CapabilityManager.INSTANCE.register(ItemEffectsBase.class, new ItemEffectsBase.Storage(), ItemEffects.class);
		CapabilityManager.INSTANCE.register(ItemPropertiesBase.class, new ItemPropertiesBase.Storage(), ItemProperties.class);
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
							
							List<String> localizedDesc = new ArrayList<String>();
	
							for (String line : processor.getUnlocalizedDesc(event.entityPlayer, instance))
							{
								localizedDesc.add(I18n.format(line, (Object[])processor.getFormatParameters(event.entityPlayer, instance)));
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
			PlayerAether aePlayer = PlayerAether.get(event.entityPlayer);

			if (props != null && props.isEquippable())
			{
				int nextEmptySlot = aePlayer.getEquipment().getNextEmptySlotForType(props.getEquipmentType());

				if (nextEmptySlot != -1)
				{
					aePlayer.getEquipment().setInventorySlotContents(nextEmptySlot, stack.copy());

					if (!event.entityPlayer.capabilities.isCreativeMode)
					{
						event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
    public void onTELoad(AttachCapabilitiesEvent.Item event)
    {
        class ItemEffectsProvider implements ICapabilityProvider
        {

            private ItemEffectsBase effects;

            private ItemStack stack;
            
            ItemEffectsProvider(ItemStack stack)
            {
                this.stack = stack;
            }
            
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing)
            {
                return ITEM_EFFECTS != null && capability == ITEM_EFFECTS;
            }
            
            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing)
            {
                if (ITEM_EFFECTS != null && capability == ITEM_EFFECTS)
                {
                	if (this.effects == null)
                	{
                		for (ItemEffects.RegistrationEntry entry : ItemEffects.getRegistrationEntries())
                		{
                			if (entry.getItem() == this.stack.getItem())
                			{
                				List<Pair<EffectProcessor, EffectInstance>> emptyList = Collections.emptyList();
                				
                				this.effects = new ItemEffectsBase.ItemEffects(entry.getEffectsProvider() == null ? emptyList : entry.getEffectsProvider().provide());
                				break;
                			}
                		}
                	}
                	
                	return (T)this.effects;
                }
                
                return null;
            }

        }

        event.addCapability(new ResourceLocation(AetherCore.MOD_ID + ":ItemEffectsCapability"), new ItemEffectsProvider(event.getItemStack()));
    
        class ItemPropertiesProvider implements ICapabilityProvider
        {

            private ItemPropertiesBase properties;

            private ItemStack stack;
            
            ItemPropertiesProvider(ItemStack stack)
            {
                this.stack = stack;
            }
            
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing)
            {
                return ITEM_PROPERTIES != null && capability == ITEM_PROPERTIES;
            }
            
            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing)
            {
                if (ITEM_PROPERTIES != null && capability == ITEM_PROPERTIES)
                {
                	if (this.properties == null)
                	{
                		for (ItemProperties.RegistrationEntry entry : ItemProperties.getRegistrationEntries())
                		{
                			if (entry.getItem() == this.stack.getItem())
                			{
                				this.properties = new ItemPropertiesBase.ItemProperties(entry.getRarity(), entry.getEquipmentType());
                				break;
                			}
                		}
                	}
                	
                	return (T)this.properties;
                }
                
                return null;
            }

        }
        
        event.addCapability(new ResourceLocation(AetherCore.MOD_ID + ":ItemPropertiesCapability"), new ItemPropertiesProvider(event.getItemStack()));
    }
	
}
