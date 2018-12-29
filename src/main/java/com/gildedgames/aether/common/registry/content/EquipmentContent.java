package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.EffectActivator;
import com.gildedgames.aether.api.items.ItemPropertiesBuilder;
import com.gildedgames.aether.api.items.ItemRarity;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.common.capabilities.item.effects.*;
import com.gildedgames.aether.common.capabilities.item.effects.CompanionEffectFactory.CompanionEffectProvider;
import com.gildedgames.aether.common.capabilities.item.effects.stats.StatEffectFactory;
import com.gildedgames.aether.common.entities.living.companions.EntityCompanion;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import net.minecraft.item.Item;

public class EquipmentContent
{
	private static final EffectActivator[] EMPTY_ACTIVATORS = new EffectActivator[0];

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
		createEquipmentItem(ItemsAether.taegore_hide_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE);
		createEquipmentItem(ItemsAether.zanite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE);
		createEquipmentItem(ItemsAether.arkenium_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE);
		createEquipmentItem(ItemsAether.gravitite_gloves, ItemEquipmentSlot.HANDWEAR, ItemRarity.NONE);
	}

	private static void createEquipmentItem(final Item item, final ItemEquipmentSlot slot, final ItemRarity rarity, final IEffectProvider... providers)
	{
		createEquipmentItem(item, slot, rarity, EMPTY_ACTIVATORS, providers);
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

		AetherAPI.content().items().registerItem(item, builder.build());
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

		AetherAPI.content().items().registerItem(item, builder.build());
	}
}
