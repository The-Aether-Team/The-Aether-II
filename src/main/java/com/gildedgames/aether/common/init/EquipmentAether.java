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
		// Gloves
		createEquipmentItem(ItemsAether.taegore_hide_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.PIERCE_DEFENSE_LEVEL, 1, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.zanite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.SLASH_DEFENSE_LEVEL, 2, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.arkenium_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.SLASH_DEFENSE_LEVEL, 1, AttributeModifier.Operation.ADDITION), new StatEffectFactory.StatProvider(
				DamageTypeAttributes.IMPACT_DEFENSE_LEVEL, 1, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.gravitite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE, WHEN_EQUIPPED, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.SLASH_DEFENSE_LEVEL, 2, AttributeModifier.Operation.ADDITION));

		createEquipmentItem(ItemsAether.skyroot_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 7, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.holystone_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 10, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.zanite_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 16, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.arkenium_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 24, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.gravitite_sword, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.SLASH_DAMAGE_LEVEL, 20, AttributeModifier.Operation.ADDITION));

		createEquipmentItem(ItemsAether.skyroot_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 7, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.holystone_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 10, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.zanite_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 16, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.arkenium_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 24, AttributeModifier.Operation.ADDITION));
		createEquipmentItem(ItemsAether.gravitite_axe, ItemEquipmentSlot.NONE, ItemRarity.NONE, WHEN_HELD, new StatEffectFactory.StatProvider(
				DamageTypeAttributes.IMPACT_DAMAGE_LEVEL, 20, AttributeModifier.Operation.ADDITION));
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
