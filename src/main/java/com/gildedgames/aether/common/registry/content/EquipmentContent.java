package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.ItemPropertiesBuilder;
import com.gildedgames.aether.api.items.ItemRarity;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.common.capabilities.item.effects.*;
import com.gildedgames.aether.common.capabilities.item.effects.CompanionEffectFactory.CompanionEffectProvider;
import com.gildedgames.aether.common.capabilities.item.effects.PotionEffectFactory.PotionEffectProvider;
import com.gildedgames.aether.common.capabilities.item.effects.stats.StatEffectFactory;
import com.gildedgames.aether.common.capabilities.item.effects.stats.StatEffectFactory.StatProvider;
import com.gildedgames.aether.common.entities.living.companions.*;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;

public class EquipmentContent
{
	public static void preInit()
	{
		registerProcessors();
		registerItems();
	}

	private static void registerProcessors()
	{
		AetherAPI.content().effects().registerEffect(new RegenerationEffectFactory());
		AetherAPI.content().effects().registerEffect(new WaterBreathEffectFactory());
		AetherAPI.content().effects().registerEffect(new FireImmunityEffectFactory());
		AetherAPI.content().effects().registerEffect(new StatEffectFactory());
		AetherAPI.content().effects().registerEffect(new PotionEffectFactory());
		AetherAPI.content().effects().registerEffect(new CompanionEffectFactory());
	}

	private static void registerItems()
	{
		// Companions
		createCompanionItem(ItemsAether.frostpine_totem, ItemEquipmentSlot.COMPANION, ItemRarity.RARE, EntityFrostpineTotem.class,
				new PotionEffectProvider(MobEffects.NIGHT_VISION, 0));

		createCompanionItem(ItemsAether.ethereal_stone, ItemEquipmentSlot.COMPANION, ItemRarity.RARE, EntityEtherealWisp.class,
				new PotionEffectProvider(MobEffects.INVISIBILITY, 0));

		createCompanionItem(ItemsAether.fleeting_stone, ItemEquipmentSlot.COMPANION, ItemRarity.RARE, EntityFleetingWisp.class);

		createCompanionItem(ItemsAether.soaring_stone, ItemEquipmentSlot.COMPANION, ItemRarity.RARE, EntitySoaringWisp.class,
				new StatProvider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.4D, StatProvider.OP_MULTIPLY));

		createCompanionItem(ItemsAether.fangrin_capsule, ItemEquipmentSlot.COMPANION, ItemRarity.RARE, EntityFangrin.class);

		createCompanionItem(ItemsAether.kraisith_capsule, ItemEquipmentSlot.COMPANION, ItemRarity.RARE, EntityKraisith.class);

		createCompanionItem(ItemsAether.death_seal, ItemEquipmentSlot.COMPANION, ItemRarity.RARE, EntityNexSpirit.class);

		createCompanionItem(ItemsAether.pink_baby_swet, ItemEquipmentSlot.COMPANION, ItemRarity.RARE, EntityPinkBabySwet.class);

		createCompanionItem(ItemsAether.orb_of_arkenzus, ItemEquipmentSlot.COMPANION, ItemRarity.RARE, EntityShadeOfArkenzus.class);

		// Gloves
		createEquipmentItem(ItemsAether.zanite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE);

		createEquipmentItem(ItemsAether.arkenium_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE);

		createEquipmentItem(ItemsAether.gravitite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE);

		// Charms
		createEquipmentItem(ItemsAether.charm_arm_01, ItemEquipmentSlot.CHARM, ItemRarity.RARE,
				new StatProvider(SharedMonsterAttributes.ARMOR, 0.3D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_arm_02, ItemEquipmentSlot.CHARM, ItemRarity.EPIC,
				new StatProvider(SharedMonsterAttributes.ARMOR, 0.6D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_arm_03, ItemEquipmentSlot.CHARM, ItemRarity.MYTHIC,
				new StatProvider(SharedMonsterAttributes.ARMOR, 1.2D, StatProvider.OP_ADD));


		createEquipmentItem(ItemsAether.charm_arm_tgh_01, ItemEquipmentSlot.CHARM, ItemRarity.RARE,
				new StatProvider(SharedMonsterAttributes.ARMOR_TOUGHNESS, 0.1D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_arm_tgh_02, ItemEquipmentSlot.CHARM, ItemRarity.EPIC,
				new StatProvider(SharedMonsterAttributes.ARMOR_TOUGHNESS, 0.3D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_arm_tgh_03, ItemEquipmentSlot.CHARM, ItemRarity.MYTHIC,
				new StatProvider(SharedMonsterAttributes.ARMOR_TOUGHNESS, 0.7D, StatProvider.OP_ADD));


		createEquipmentItem(ItemsAether.charm_atk_dmg_01, ItemEquipmentSlot.CHARM, ItemRarity.RARE,
				new StatProvider(SharedMonsterAttributes.ATTACK_DAMAGE, 0.5D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_atk_dmg_02, ItemEquipmentSlot.CHARM, ItemRarity.EPIC,
				new StatProvider(SharedMonsterAttributes.ATTACK_DAMAGE, 1.0D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_atk_dmg_03, ItemEquipmentSlot.CHARM, ItemRarity.MYTHIC,
				new StatProvider(SharedMonsterAttributes.ATTACK_DAMAGE, 1.5D, StatProvider.OP_ADD));


		createEquipmentItem(ItemsAether.charm_atk_spd_01, ItemEquipmentSlot.CHARM, ItemRarity.RARE,
				new StatProvider(SharedMonsterAttributes.ATTACK_SPEED, 0.03D, StatProvider.OP_MULTIPLY));

		createEquipmentItem(ItemsAether.charm_atk_spd_02, ItemEquipmentSlot.CHARM, ItemRarity.EPIC,
				new StatProvider(SharedMonsterAttributes.ATTACK_SPEED, 0.07D, StatProvider.OP_MULTIPLY));

		createEquipmentItem(ItemsAether.charm_atk_spd_03, ItemEquipmentSlot.CHARM, ItemRarity.MYTHIC,
				new StatProvider(SharedMonsterAttributes.ATTACK_SPEED, 0.12D, StatProvider.OP_MULTIPLY));


		createEquipmentItem(ItemsAether.charm_kbk_res_01, ItemEquipmentSlot.CHARM, ItemRarity.RARE,
				new StatProvider(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, 0.1D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_kbk_res_02, ItemEquipmentSlot.CHARM, ItemRarity.EPIC,
				new StatProvider(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, 0.2D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_kbk_res_03, ItemEquipmentSlot.CHARM, ItemRarity.MYTHIC,
				new StatProvider(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, 0.3D, StatProvider.OP_ADD));


		createEquipmentItem(ItemsAether.charm_lck_01, ItemEquipmentSlot.CHARM, ItemRarity.RARE,
				new StatProvider(SharedMonsterAttributes.LUCK, 0.1D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_lck_02, ItemEquipmentSlot.CHARM, ItemRarity.EPIC,
				new StatProvider(SharedMonsterAttributes.LUCK, 0.3D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_lck_03, ItemEquipmentSlot.CHARM, ItemRarity.MYTHIC,
				new StatProvider(SharedMonsterAttributes.LUCK, 0.7D, StatProvider.OP_ADD));


		createEquipmentItem(ItemsAether.charm_max_hlt_01, ItemEquipmentSlot.CHARM, ItemRarity.RARE,
				new StatProvider(SharedMonsterAttributes.MAX_HEALTH, 0.5D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_max_hlt_02, ItemEquipmentSlot.CHARM, ItemRarity.EPIC,
				new StatProvider(SharedMonsterAttributes.MAX_HEALTH, 1.0D, StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_max_hlt_03, ItemEquipmentSlot.CHARM, ItemRarity.MYTHIC,
				new StatProvider(SharedMonsterAttributes.MAX_HEALTH, 2.0D, StatProvider.OP_ADD));


		createEquipmentItem(ItemsAether.charm_mve_spd_01, ItemEquipmentSlot.CHARM, ItemRarity.RARE,
				new StatProvider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.05D, StatProvider.OP_MULTIPLY));

		createEquipmentItem(ItemsAether.charm_mve_spd_02, ItemEquipmentSlot.CHARM, ItemRarity.EPIC,
				new StatProvider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.1D, StatProvider.OP_MULTIPLY));

		createEquipmentItem(ItemsAether.charm_mve_spd_03, ItemEquipmentSlot.CHARM, ItemRarity.MYTHIC,
				new StatProvider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.15D, StatProvider.OP_MULTIPLY));
	}

	private static void createEquipmentItem(Item item, ItemEquipmentSlot slot, ItemRarity rarity, IEffectProvider... providers)
	{
		ItemPropertiesBuilder builder = new ItemPropertiesBuilder();
		builder.withSlot(slot);
		builder.withRarity(rarity);

		for (IEffectProvider provider : providers)
		{
			builder.withEffect(provider);
		}

		AetherAPI.content().items().registerItem(item, builder.build());
	}

	private static void createCompanionItem(Item item, ItemEquipmentSlot slot, ItemRarity rarity,
			Class<? extends EntityCompanion> companion, IEffectProvider... providers)
	{
		ItemPropertiesBuilder builder = new ItemPropertiesBuilder();
		builder.withSlot(slot);
		builder.withRarity(rarity);
		builder.withEffect(new CompanionEffectProvider(companion));
		builder.withPrecondition((player, stack) ->
				!(stack.getItem() instanceof ItemCompanion) ||ItemCompanion.getTicksUntilRespawn(stack, player.getEntity().getEntityWorld()) <= 0);

		for (IEffectProvider provider : providers)
		{
			builder.withEffect(provider);
		}

		AetherAPI.content().items().registerItem(item, builder.build());
	}
}
