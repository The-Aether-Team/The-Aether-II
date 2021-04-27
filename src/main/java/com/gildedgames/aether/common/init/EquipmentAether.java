package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.items.EffectActivator;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.items.properties.ItemPropertiesBuilder;
import com.gildedgames.aether.api.items.properties.ItemRarity;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.capabilities.item.effects.*;
import com.gildedgames.aether.common.capabilities.item.effects.CompanionEffectFactory.CompanionEffectProvider;
import com.gildedgames.aether.common.capabilities.item.effects.stats.StatEffectFactory;
import com.gildedgames.aether.common.entities.companions.EntityCompanion;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;

public class EquipmentAether
{
	private static final EffectActivator[] EMPTY_ACTIVATORS = new EffectActivator[0];

	private static final EffectActivator[] WHEN_HELD = new EffectActivator[] {
			EffectActivator.WHEN_HELD
	};

	private static final EffectActivator[] WHEN_EQUIPPED = new EffectActivator[] {
			EffectActivator.WHEN_EQUIPPED
	};

	public static void init()
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
		AetherAPI.content().effects().registerEffect(new InvisiblityEffectFactory());
	}

	private static void registerItems()
	{
		//GLOVES
		createEquipmentItem(ItemsAether.taegore_hide_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.burrukai_pelt_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.zanite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.arkenium_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED,
				new StatEffectFactory.StatProvider(SharedMonsterAttributes.MOVEMENT_SPEED, -0.075, StatEffectFactory.StatProvider.OP_MULTIPLY));

		createEquipmentItem(ItemsAether.gravitite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED);

		//CHARMS
		//createEquipmentItem(ItemsAether.charm_arm_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.ARMOR, 1.0, StatEffectFactory.StatProvider.OP_ADD));
		//createEquipmentItem(ItemsAether.charm_arm_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.ARMOR, 2.0, StatEffectFactory.StatProvider.OP_ADD));

		//createEquipmentItem(ItemsAether.charm_arm_tgh_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.ARMOR_TOUGHNESS, 1.0, StatEffectFactory.StatProvider.OP_ADD));
		//createEquipmentItem(ItemsAether.charm_arm_tgh_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.ARMOR_TOUGHNESS, 2.0, StatEffectFactory.StatProvider.OP_ADD));

		//createEquipmentItem(ItemsAether.charm_imp_dmg_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		//createEquipmentItem(ItemsAether.charm_imp_dmg_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		//createEquipmentItem(ItemsAether.charm_atk_spd_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.ATTACK_SPEED, 0.1, StatEffectFactory.StatProvider.OP_ADD));
		//createEquipmentItem(ItemsAether.charm_atk_spd_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.ATTACK_SPEED, 0.2, StatEffectFactory.StatProvider.OP_ADD));

		//createEquipmentItem(ItemsAether.charm_kbk_res_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, 1.0, StatEffectFactory.StatProvider.OP_ADD));
		//createEquipmentItem(ItemsAether.charm_kbk_res_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, 2.0, StatEffectFactory.StatProvider.OP_ADD));

		//createEquipmentItem(ItemsAether.charm_lck_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.LUCK, 1.0, StatEffectFactory.StatProvider.OP_ADD));
		//createEquipmentItem(ItemsAether.charm_lck_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.LUCK, 2.0, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_max_hlt_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED,
				new StatEffectFactory.StatProvider(SharedMonsterAttributes.MAX_HEALTH, 0.5, StatEffectFactory.StatProvider.OP_ADD));
		//createEquipmentItem(ItemsAether.charm_max_hlt_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED,
		//		new StatEffectFactory.StatProvider(SharedMonsterAttributes.MAX_HEALTH, 1.0, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.charm_mve_spd_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED,
				new StatEffectFactory.StatProvider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.001, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.charm_mve_spd_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED,
				new StatEffectFactory.StatProvider(SharedMonsterAttributes.MOVEMENT_SPEED, 0.002, StatEffectFactory.StatProvider.OP_ADD));

		//createEquipmentItem(ItemsAether.charm_prc_dmg_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		//createEquipmentItem(ItemsAether.charm_prc_dmg_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		//createEquipmentItem(ItemsAether.charm_slsh_dmg_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		//createEquipmentItem(ItemsAether.charm_slsh_dmg_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_amb_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_amb_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_bld_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_bld_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_fra_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_fra_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_frz_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_frz_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_grd_brk_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_grd_brk_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_irr_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_irr_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_stn_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_stn_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_tox_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_tox_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_ven_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_ven_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);

		createEquipmentItem(ItemsAether.charm_res_web_01, ItemEquipmentSlot.CHARM, ItemRarity.COMMON, WHEN_EQUIPPED);
		createEquipmentItem(ItemsAether.charm_res_web_02, ItemEquipmentSlot.CHARM, ItemRarity.RARE, WHEN_EQUIPPED);
	}

	private static void createEquipmentItem(final Item item, final ItemEquipmentSlot slot, final ItemRarity rarity)
	{
		createEquipmentItem(item, slot, rarity, EMPTY_ACTIVATORS);
	}

	private static void createEquipmentItem(final Item item, final ItemEquipmentSlot slot, final ItemRarity rarity, EffectActivator[] effectActivators,
			final IEffectProvider... providers)
	{
		final ItemPropertiesBuilder builder = new ItemPropertiesBuilder();
		builder.withSlot(slot);
		builder.withRarity(rarity);

		for (final IEffectProvider provider : providers)
		{
			builder.withEffect(provider);
		}

		builder.withEffectActivators(effectActivators);

		AetherAPI.content().items().registerItem(item, builder);
	}

	private static void createCompanionItem(final Item item, final ItemEquipmentSlot slot, final ItemRarity rarity,
			final Class<? extends EntityCompanion> companion, final IEffectProvider... providers)
	{
		final ItemPropertiesBuilder builder = new ItemPropertiesBuilder();
		builder.withSlot(slot);
		builder.withRarity(rarity);
		builder.withEffect(new CompanionEffectProvider(companion));
		builder.withPrecondition((player, stack) ->
				!(stack.getItem() instanceof ItemCompanion) || ItemCompanion.getTicksUntilRespawn(stack, player.getEntity().getEntityWorld()) <= 0);

		for (final IEffectProvider provider : providers)
		{
			builder.withEffect(provider);
		}

		AetherAPI.content().items().registerItem(item, builder);
	}
}
