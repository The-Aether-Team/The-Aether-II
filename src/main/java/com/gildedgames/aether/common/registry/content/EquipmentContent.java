package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.ItemProperties;
import com.gildedgames.aether.api.items.ItemPropertiesBuilder;
import com.gildedgames.aether.api.items.ItemRarity;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.capabilities.item.effects.*;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.SharedMonsterAttributes;

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
		AetherAPI.equipment().registerEffect(new WaterBreathEffect());
		AetherAPI.equipment().registerEffect(new FireImmunityEffect());
		AetherAPI.equipment().registerEffect(new HungerImmuneEffect());
		AetherAPI.equipment().registerEffect(new StatEffect());
	}

	private static void registerItems()
	{
		AetherAPI.items().registerItem(ItemsAether.white_moa_feather, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.RARE)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.025D, StatEffect.Provider.OP_MULTIPLER))
				.build());

		AetherAPI.items().registerItem(ItemsAether.sakura_moa_feather, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.MYTHIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.05D, StatEffect.Provider.OP_MULTIPLER))
				.build());

		AetherAPI.items().registerItem(ItemsAether.damaged_moa_feather, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.EPIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.1D, StatEffect.Provider.OP_MULTIPLER))
				.build());
	}
}
