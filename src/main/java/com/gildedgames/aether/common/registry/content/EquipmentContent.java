package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.ItemProperties;
import com.gildedgames.aether.api.items.ItemRarity;
import com.gildedgames.aether.api.items.equipment.EquipmentProperties;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.capabilities.item.effects.DebugEffect;
import com.gildedgames.aether.common.capabilities.item.effects.RegenerationEffect;
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
		AetherAPI.equipment().registerEffect(new DebugEffect());
		AetherAPI.equipment().registerEffect(new RegenerationEffect());
	}

	private static void registerItems()
	{
		AetherAPI.items().registerItem(Items.STICK, ItemProperties.builder()
				.setRarity(ItemRarity.RARE)
				.setEquipmentProperties(EquipmentProperties.builder()
						.setSlot(ItemEquipmentSlot.CHARM)
						.addEffect(new DebugEffect.Provider(1.0f))
						.build())
				.build());

		AetherAPI.items().registerItem(ItemsAether.regeneration_stone, ItemProperties.builder()
				.setRarity(ItemRarity.RARE)
				.setEquipmentProperties(EquipmentProperties.builder()
						.setSlot(ItemEquipmentSlot.RELIC)
						.addEffect(new RegenerationEffect.Provider(1))
						.build())
				.build());
	}
}
