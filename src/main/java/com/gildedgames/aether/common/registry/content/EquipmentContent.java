package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentSlot;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.api.items.equipment.EquipmentProperties;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import com.gildedgames.aether.api.items.ItemProperties;
import com.gildedgames.aether.common.capabilities.item.DebugEffectProcessor;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.init.Items;

public class EquipmentContent
{
	public static void preInit()
	{
		registerProcessors();
		registerItems();
	}

	private static void registerProcessors()
	{
		AetherAPI.equipment().registerEffect(new DebugEffectProcessor());
	}

	private static void registerItems()
	{
		AetherAPI.items().registerItem(Items.STICK, ItemProperties.builder()
				.setRarity(ItemRarity.RARE)
				.setEquipmentProperties(EquipmentProperties.builder()
						.setSlot(ItemEquipmentSlot.CHARM)
						.addEffect(new DebugEffectProcessor.Instance(1.0f))
						.build())
				.build());

		AetherAPI.items().registerItem(ItemsAether.skyroot_stick, ItemProperties.builder()
				.setRarity(ItemRarity.RARE)
				.setEquipmentProperties(EquipmentProperties.builder()
						.setSlot(ItemEquipmentSlot.CHARM)
						.addEffect(new DebugEffectProcessor.Instance(3.0f))
						.build())
				.build());
	}
}
