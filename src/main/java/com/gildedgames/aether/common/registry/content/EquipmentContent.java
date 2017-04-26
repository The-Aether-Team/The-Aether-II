package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.ItemProperties;
import com.gildedgames.aether.api.items.ItemPropertiesBuilder;
import com.gildedgames.aether.api.items.ItemRarity;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.capabilities.item.effects.*;
import com.gildedgames.aether.common.entities.SharedAetherAttributes;
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
		// Charms

		AetherAPI.items().registerItem(ItemsAether.charm_arm_01, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.RARE)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ARMOR, 0.3D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_arm_02, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.EPIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ARMOR, 0.6D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_arm_03, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.MYTHIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ARMOR, 1.2D, StatEffect.Provider.OP_ADD))
				.build());

		AetherAPI.items().registerItem(ItemsAether.charm_arm_tgh_01, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.RARE)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ARMOR_TOUGHNESS, 0.1D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_arm_tgh_02, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.EPIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ARMOR_TOUGHNESS, 0.3D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_arm_tgh_03, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.MYTHIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ARMOR_TOUGHNESS, 0.7D, StatEffect.Provider.OP_ADD))
				.build());

		AetherAPI.items().registerItem(ItemsAether.charm_atk_dmg_01, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.RARE)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ATTACK_DAMAGE, 0.2D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_atk_dmg_02, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.EPIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ATTACK_DAMAGE, 0.5D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_atk_dmg_03, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.MYTHIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ATTACK_DAMAGE, 1D, StatEffect.Provider.OP_ADD))
				.build());

		AetherAPI.items().registerItem(ItemsAether.charm_atk_spd_01, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.RARE)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ATTACK_SPEED, 0.1D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_atk_spd_02, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.EPIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ATTACK_SPEED, 0.3D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_atk_spd_03, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.MYTHIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.ATTACK_SPEED, 0.7D, StatEffect.Provider.OP_ADD))
				.build());

		AetherAPI.items().registerItem(ItemsAether.charm_kbk_res_01, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.RARE)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, 0.01D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_kbk_res_02, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.EPIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, 0.5D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_kbk_res_03, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.MYTHIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, 0.1D, StatEffect.Provider.OP_ADD))
				.build());

		AetherAPI.items().registerItem(ItemsAether.charm_lck_01, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.RARE)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.LUCK, 0.1D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_lck_02, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.EPIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.LUCK, 0.3D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_lck_03, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.MYTHIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.LUCK, 0.7D, StatEffect.Provider.OP_ADD))
				.build());

		AetherAPI.items().registerItem(ItemsAether.charm_max_hlt_01, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.RARE)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.MAX_HEALTH, 0.4D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_max_hlt_02, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.EPIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.MAX_HEALTH, 0.85D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_max_hlt_03, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.MYTHIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.MAX_HEALTH, 2D, StatEffect.Provider.OP_ADD))
				.build());

		AetherAPI.items().registerItem(ItemsAether.charm_mve_spd_01, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.RARE)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.0005D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_mve_spd_02, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.EPIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.0025D, StatEffect.Provider.OP_ADD))
				.build());
		AetherAPI.items().registerItem(ItemsAether.charm_mve_spd_03, new ItemPropertiesBuilder()
				.withSlot(ItemEquipmentSlot.CHARM)
				.withRarity(ItemRarity.MYTHIC)
				.withEffect(new StatEffect.Provider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.0075D, StatEffect.Provider.OP_ADD))
				.build());
	}
}
