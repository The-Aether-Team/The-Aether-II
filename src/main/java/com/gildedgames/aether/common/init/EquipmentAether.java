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
		createEquipmentItem(ItemsAether.taegore_hide_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DEFENSE_LEVEL, 1, StatEffectFactory.StatProvider.OP_ADD),
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL, 2, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.burrukai_pelt_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DEFENSE_LEVEL, 1, StatEffectFactory.StatProvider.OP_ADD),
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL, 4, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.zanite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DEFENSE_LEVEL, 2, StatEffectFactory.StatProvider.OP_ADD),
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL, 4, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.arkenium_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED,
				new StatEffectFactory.StatProvider(SharedMonsterAttributes.MOVEMENT_SPEED, -0.075, StatEffectFactory.StatProvider.OP_MULTIPLY),
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DEFENSE_LEVEL, 3, StatEffectFactory.StatProvider.OP_ADD),
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL, 7, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.gravitite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DEFENSE_LEVEL, 4, StatEffectFactory.StatProvider.OP_ADD),
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL, 8, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.skyroot_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 14, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.holystone_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 21, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.zanite_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 34, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.arkenium_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 40, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.gravitite_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 42, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.skyroot_crossbow, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 14, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.holystone_crossbow, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 21, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.zanite_crossbow, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 34, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.arkenium_crossbow, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 40, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.gravitite_crossbow, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 42, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.skyroot_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 10, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.holystone_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 17, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.zanite_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 30, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.arkenium_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 36, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.gravitite_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 38, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.skyroot_pickaxe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 10, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.holystone_pickaxe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 17, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.zanite_pickaxe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 30, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.arkenium_pickaxe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 36, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.gravitite_pickaxe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL, 38, StatEffectFactory.StatProvider.OP_ADD));

		createEquipmentItem(ItemsAether.skyroot_shovel, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 5, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.holystone_shovel, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 12, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.zanite_shovel, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 19, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.arkenium_shovel, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 24, StatEffectFactory.StatProvider.OP_ADD));
		createEquipmentItem(ItemsAether.gravitite_shovel, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD,
				new StatEffectFactory.StatProvider(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 26, StatEffectFactory.StatProvider.OP_ADD));
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
