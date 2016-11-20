package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.*;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.player.ModifyXPCollectionEffect;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.*;
import com.gildedgames.aether.common.capabilities.item.effects.ItemEffects;
import com.gildedgames.aether.common.items.ItemsAether;
import com.google.common.collect.Lists;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class EquipmentContent
{
	public static void preInit()
	{
		AetherAPI.equipment().register(ItemsAether.neptune_helmet, ItemRarity.COMMON, null);
		AetherAPI.equipment().register(ItemsAether.neptune_chestplate, ItemRarity.COMMON, null);
		AetherAPI.equipment().register(ItemsAether.neptune_leggings, ItemRarity.COMMON, null);
		AetherAPI.equipment().register(ItemsAether.neptune_boots, ItemRarity.COMMON, null);
		AetherAPI.equipment().register(ItemsAether.neptune_gloves, ItemRarity.COMMON, ItemEquipmentType.HANDWEAR);

		AetherAPI.equipment().register(ItemsAether.valkyrie_helmet, ItemRarity.RARE, null);
		AetherAPI.equipment().register(ItemsAether.valkyrie_chestplate, ItemRarity.RARE, null);
		AetherAPI.equipment().register(ItemsAether.valkyrie_leggings, ItemRarity.RARE, null);
		AetherAPI.equipment().register(ItemsAether.valkyrie_boots, ItemRarity.RARE, null);
		AetherAPI.equipment().register(ItemsAether.valkyrie_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);

		AetherAPI.equipment().register(ItemsAether.phoenix_helmet, ItemRarity.EPIC, null);
		AetherAPI.equipment().register(ItemsAether.phoenix_chestplate, ItemRarity.EPIC, null);
		AetherAPI.equipment().register(ItemsAether.phoenix_leggings, ItemRarity.EPIC, null);
		AetherAPI.equipment().register(ItemsAether.phoenix_boots, ItemRarity.EPIC, null);
		AetherAPI.equipment().register(ItemsAether.phoenix_gloves, ItemRarity.EPIC, ItemEquipmentType.HANDWEAR);

		AetherAPI.equipment().register(ItemsAether.iron_ring, ItemRarity.NONE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.gold_ring, ItemRarity.NONE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.zanite_ring, ItemRarity.NONE, ItemEquipmentType.RING);

		AetherAPI.equipment().register(ItemsAether.zanite_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.gravitite_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.leather_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.iron_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.gold_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.chain_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.diamond_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);

		/** ARTIFACTS **/

		AetherAPI.equipment().register(ItemsAether.gravitite_core, ItemRarity.MYTHIC, ItemEquipmentType.ARTIFACT);
		AetherAPI.equipment().register(ItemsAether.valkyrie_wings, ItemRarity.MYTHIC, ItemEquipmentType.ARTIFACT);

		/** RELICS **/

		AetherAPI.equipment().register(ItemsAether.iron_bubble, ItemRarity.COMMON, ItemEquipmentType.RELIC);

		AetherAPI.equipment().register(ItemsAether.sunlit_tome, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.moonlit_tome, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.primal_totem_of_survival, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.primal_totem_of_rage, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.divine_beacon, ItemRarity.RARE, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.regeneration_stone, ItemRarity.RARE, ItemEquipmentType.RELIC);

		AetherAPI.equipment().register(ItemsAether.phoenix_rune, ItemRarity.EPIC, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.daggerfrost_rune, ItemRarity.EPIC, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.sunlit_scroll, ItemRarity.EPIC, ItemEquipmentType.RELIC);
		AetherAPI.equipment().register(ItemsAether.moonlit_scroll, ItemRarity.EPIC, ItemEquipmentType.RELIC);

		/** NECKWEAR **/

		AetherAPI.equipment().register(ItemsAether.iron_pendant, ItemRarity.NONE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.gold_pendant, ItemRarity.NONE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.zanite_pendant, ItemRarity.NONE, ItemEquipmentType.NECKWEAR);

		AetherAPI.equipment().register(ItemsAether.zanite_studded_choker, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.lesser_amulet_of_growth, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.hide_gorget, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.fleeting_scarf, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);

		AetherAPI.equipment().register(ItemsAether.ice_pendant, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.muggers_cloak, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.amulet_of_growth, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.arkenium_studded_choker, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.raegorite_gorget, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.gruegar_scarf, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.winged_necklace, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.gust_amulet, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.typhoon_amulet, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.chain_of_sporing_bones, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.molten_amulet, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.granite_studded_choker, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.bandit_shawl, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);

		AetherAPI.equipment().register(ItemsAether.moon_sect_warden_gorget, ItemRarity.EPIC, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.thiefs_gorget, ItemRarity.EPIC, ItemEquipmentType.NECKWEAR);

		AetherAPI.equipment().register(ItemsAether.frostward_scarf, ItemRarity.MYTHIC, ItemEquipmentType.NECKWEAR);

		/** COMPANIONS **/

		AetherAPI.equipment().register(ItemsAether.pink_baby_swet, ItemRarity.COMMON, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.kraisith_capsule, ItemRarity.COMMON, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.fangrin_capsule, ItemRarity.COMMON, ItemEquipmentType.COMPANION);

		AetherAPI.equipment().register(ItemsAether.orb_of_arkenzus, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.ethereal_stone, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.fleeting_stone, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.soaring_stone, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		AetherAPI.equipment().register(ItemsAether.frostpine_totem, ItemRarity.RARE, ItemEquipmentType.COMPANION);

		AetherAPI.equipment().register(ItemsAether.death_seal, ItemRarity.EPIC, ItemEquipmentType.COMPANION);

		/** RINGS **/
		AetherAPI.equipment().register(ItemsAether.barbed_iron_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.fleeting_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.lesser_ring_of_growth, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ring_of_strength, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.swift_ribbon, ItemRarity.COMMON, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.lesser_ring_of_wisdom, ItemRarity.COMMON, ItemEquipmentType.RING);

		AetherAPI.equipment().register(ItemsAether.bone_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ice_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.granite_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.gust_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.typhoon_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.sporing_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ember_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ring_of_growth, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.barbed_gold_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.winged_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.gruegar_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.wynd_cluster_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.ring_of_wisdom, ItemRarity.RARE, ItemEquipmentType.RING);

		AetherAPI.equipment().register(ItemsAether.solar_band, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.lunar_band, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.skyroot_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.candy_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.dust_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.mud_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.storm_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.steam_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.arkenium_ring, ItemRarity.EPIC, ItemEquipmentType.RING);

		AetherAPI.equipment().register(ItemsAether.plague_coil, ItemRarity.MYTHIC, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.life_coil, ItemRarity.MYTHIC, ItemEquipmentType.RING);

		/** CHARMS **/
		AetherAPI.equipment().register(ItemsAether.glamoured_iron_screw, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.white_moa_feather, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.hide_pouch, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.angel_bandage, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.wisdom_bauble, ItemRarity.COMMON, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.swift_rune, ItemRarity.COMMON, ItemEquipmentType.CHARM);

		AetherAPI.equipment().register(ItemsAether.wisdom_rune, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_zephyr_husk, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_blue_swet_jelly, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_cockatrice_talons, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_coal_ember, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.moa_feather, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.blight_ward, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_skyroot_twig, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_gold_screw, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.ambrosium_talisman, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.gruegar_pouch, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.wynd_cluster, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_cockatrice_keratin, ItemRarity.RARE, ItemEquipmentType.CHARM);

		AetherAPI.equipment().register(ItemsAether.glamoured_taegore_tusk, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_cockatrice_heart, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.damaged_moa_feather, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.sakura_moa_feather, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.osseous_bane, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.rot_bane, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.butchers_knife, ItemRarity.EPIC, ItemEquipmentType.CHARM);

		/** OFF-HAND **/

		AetherAPI.equipment().register(Items.SHIELD, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.skyroot_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.holystone_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.zanite_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.arkenium_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.gravitite_shield, ItemRarity.NONE, ItemEquipmentType.OFFHAND);

		AetherAPI.equipment().register(ItemsAether.bolt, ItemRarity.NONE, ItemEquipmentType.OFFHAND);
		AetherAPI.equipment().register(ItemsAether.dart, ItemRarity.NONE, ItemEquipmentType.OFFHAND);

		class Effects implements ItemEffects.ItemEffectsProvider
		{

			private List<Pair<EntityEffectProcessor, EntityEffectInstance>> pairs = Lists.newArrayList();

			public Effects()
			{

			}

			public <I extends EntityEffectInstance> Effects add(EntityEffectProcessor<I> processor, I instance)
			{
				Pair<EntityEffectProcessor, EntityEffectInstance> effectPair = Pair.of((EntityEffectProcessor) processor, instance);

				this.pairs.add(effectPair);

				return this;
			}

			public Effects add(EntityEffectProcessor<EntityEffectInstance> processor)
			{
				return this.add(processor, new EntityEffectInstance());
			}

			@Override
			public List<Pair<EntityEffectProcessor, EntityEffectInstance>> provide()
			{
				List<Pair<EntityEffectProcessor, EntityEffectInstance>> clone = Lists.newArrayList();

				for (Pair<EntityEffectProcessor, EntityEffectInstance> pair : this.pairs)
				{
					clone.add(Pair.of(pair.getLeft(), pair.getRight().cloneInstance()));
				}

				return clone;
			}

		}

		/** ARMOR **/

		ItemEffects.register(ItemsAether.gravitite_helmet, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.gravitite_chestplate, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.05D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(15D)));

		ItemEffects.register(ItemsAether.gravitite_leggings, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.02D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(7.5D)));

		ItemEffects.register(ItemsAether.gravitite_boots, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.gravitite_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D))
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.gravitite_shield, new Effects()
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.02D, new BlockingRule()))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(10D)));

		ItemEffects.register(ItemsAether.phoenix_helmet, new Effects()
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.phoenix_chestplate, new Effects()
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.05D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(15D)));

		ItemEffects.register(ItemsAether.phoenix_leggings, new Effects()
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.02D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(7.5D)));

		ItemEffects.register(ItemsAether.phoenix_boots, new Effects()
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.phoenix_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 2.0D))
				.add(EntityEffects.SET_ATTACKERS_ON_FIRE, new SetAttackersOnFireEffect.Instance(0.01D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.neptune_helmet, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.neptune_chestplate, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.75D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.neptune_leggings, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.neptune_boots, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.neptune_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 2.0D))
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.WATER, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_helmet, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_chestplate, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.zanite_leggings, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_boots, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.zanite_shield, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3.5D)));

		ItemEffects.register(ItemsAether.valkyrie_helmet, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.valkyrie_chestplate, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.75D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.valkyrie_leggings, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.valkyrie_boots, new Effects()
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.valkyrie_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 2.5D))
				.add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.AIR, 0.25D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(Items.LEATHER_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(Items.LEATHER_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3D)));

		ItemEffects.register(Items.LEATHER_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2D)));

		ItemEffects.register(Items.LEATHER_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(ItemsAether.leather_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(Items.IRON_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(Items.IRON_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(Items.IRON_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(Items.IRON_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.iron_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(Items.DIAMOND_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(Items.DIAMOND_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(15D)));

		ItemEffects.register(Items.DIAMOND_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(Items.DIAMOND_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(ItemsAether.diamond_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.5D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(5D)));

		ItemEffects.register(Items.GOLDEN_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(Items.GOLDEN_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3D)));

		ItemEffects.register(Items.GOLDEN_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2D)));

		ItemEffects.register(Items.GOLDEN_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(ItemsAether.gold_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.5D, 1.0D, true))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1D)));

		ItemEffects.register(Items.CHAINMAIL_HELMET, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1.5D)));

		ItemEffects.register(Items.CHAINMAIL_CHESTPLATE, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3D)));

		ItemEffects.register(Items.CHAINMAIL_LEGGINGS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2D)));

		ItemEffects.register(Items.CHAINMAIL_BOOTS, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1.5D)));

		ItemEffects.register(ItemsAether.chain_gloves, new Effects()
				.add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D))
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(1.5D)));

		ItemEffects.register(Items.SHIELD, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3.5D)));

		ItemEffects.register(ItemsAether.skyroot_shield, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(2.5D)));

		ItemEffects.register(ItemsAether.holystone_shield, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(3D)));

		ItemEffects.register(ItemsAether.arkenium_shield, new Effects()
				.add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(15D)));

		/** OTHERS **/

		ItemEffects.register(ItemsAether.iron_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.5D)));
		ItemEffects.register(ItemsAether.gold_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.0D, 1.0D, false)));
		ItemEffects.register(ItemsAether.iron_pendant, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 3.0D)));
		ItemEffects.register(ItemsAether.gold_pendant, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D, 4.0D, false)));
		ItemEffects.register(ItemsAether.zanite_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.5D, 1.0D, true)));
		ItemEffects.register(ItemsAether.zanite_pendant, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 3.0D, 4.0D, false)));
		ItemEffects.register(ItemsAether.iron_bubble, new Effects().add(EntityEffects.BREATHE_UNDERWATER));
		ItemEffects.register(ItemsAether.regeneration_stone, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.ice_ring, new Effects().add(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(2)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FROST, 2.0D)));
		ItemEffects.register(ItemsAether.ice_pendant, new Effects().add(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(2)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FROST, 5.0D)));
		ItemEffects.register(ItemsAether.daggerfrost_rune, new Effects().add(EntityEffects.DAGGERFROST, new EntityEffectInstance()));
		ItemEffects.register(ItemsAether.candy_ring, new Effects().add(EntityEffects.REDUCE_HUNGER));
		ItemEffects.register(ItemsAether.bone_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D)));
		ItemEffects.register(ItemsAether.skyroot_ring, new Effects().add(EntityEffects.DOUBLE_DROPS, new DoubleDropEffect.Instance(1.5D)));

		ItemEffects.register(ItemsAether.barbed_iron_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D)));
		ItemEffects.register(ItemsAether.barbed_gold_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D, 3.0D, false)));
		ItemEffects.register(ItemsAether.solar_band, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectSunlightRule())));
		ItemEffects.register(ItemsAether.lunar_band, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectMoonlightRule())));
		ItemEffects.register(ItemsAether.ring_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(2.0D)));
		ItemEffects.register(ItemsAether.plague_coil, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D, 6.0D, false)).add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(-2.0D)));
		ItemEffects.register(ItemsAether.fleeting_ring, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.10D)));
		ItemEffects.register(ItemsAether.lesser_ring_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.winged_ring, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.20D)));
		ItemEffects.register(ItemsAether.life_coil, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(4.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, -4.0D)));
		ItemEffects.register(ItemsAether.glamoured_iron_screw, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.2D)));
		ItemEffects.register(ItemsAether.wisdom_bauble, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.10D)));
		ItemEffects.register(ItemsAether.moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.05D)));
		ItemEffects.register(ItemsAether.blight_ward, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1D, new DamagingElementRule(ElementalState.BLIGHT))));
		ItemEffects.register(ItemsAether.glamoured_skyroot_twig);
		ItemEffects.register(ItemsAether.glamoured_gold_screw, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.1D, 0.5D, true)));
		ItemEffects.register(ItemsAether.ambrosium_talisman, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new HoldingItemRule(new ItemStack(ItemsAether.ambrosium_shard)))));
		ItemEffects.register(ItemsAether.sunlit_scroll, new Effects().add(EntityEffects.PAUSE_HUNGER, new EntityEffectInstance(new InDirectSunlightRule())));
		ItemEffects.register(ItemsAether.moonlit_scroll, new Effects().add(EntityEffects.PAUSE_HUNGER, new EntityEffectInstance(new InDirectMoonlightRule())));
		ItemEffects.register(ItemsAether.glamoured_cockatrice_heart, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new WhenPoisonedRule())));
		ItemEffects.register(ItemsAether.damaged_moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.075D, new InCombatRule())));
		ItemEffects.register(ItemsAether.white_moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.03D)));
		ItemEffects.register(ItemsAether.sakura_moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.10D, new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.osseous_bane, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D, new DamagingMobRule(EntitySkeleton.class, "Skeleton"))));
		ItemEffects.register(ItemsAether.rot_bane, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 1.0D, new DamagingUndeadRule())));
		ItemEffects.register(ItemsAether.butchers_knife, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D, new DamagingPassiveAnimalsRule())));

		ItemEffects.register(ItemsAether.zanite_studded_choker, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 4.0D)));
		ItemEffects.register(ItemsAether.lesser_amulet_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(4.0D)));
		ItemEffects.register(ItemsAether.hide_gorget, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.amulet_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(6.0D)));
		ItemEffects.register(ItemsAether.arkenium_studded_choker, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 5.0D)));
		ItemEffects.register(ItemsAether.raegorite_gorget, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(2.0D)));
		ItemEffects.register(ItemsAether.gruegar_scarf, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-15D)));
		ItemEffects.register(ItemsAether.moon_sect_warden_gorget, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(3.0D)).add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.thiefs_gorget, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.3D)).add(EntityEffects.INVISIBILITY, new EntityEffectInstance(new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.frostward_scarf, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.4D)).add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.FROST, 1.0D)));

		ItemEffects.register(ItemsAether.glamoured_zephyr_husk, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_blue_swet_jelly, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_cockatrice_talons, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BLIGHT, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_coal_ember, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 0.3D)));

		ItemEffects.register(ItemsAether.granite_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 2.0D)));
		ItemEffects.register(ItemsAether.gust_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 2.0D)));
		ItemEffects.register(ItemsAether.typhoon_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 2.0D)));
		ItemEffects.register(ItemsAether.sporing_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BLIGHT, 2.0D)));
		ItemEffects.register(ItemsAether.ember_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 2.0D)));
		ItemEffects.register(ItemsAether.dust_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 1.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 1.0D)));
		ItemEffects.register(ItemsAether.mud_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 1.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 1.0D)));
		ItemEffects.register(ItemsAether.storm_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 1.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 1.0D)));
		ItemEffects.register(ItemsAether.steam_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 1.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 1.0D)));

		ItemEffects.register(ItemsAether.sunlit_tome, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectSunlightRule())));
		ItemEffects.register(ItemsAether.moonlit_tome, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectMoonlightRule())));
		ItemEffects.register(ItemsAether.phoenix_rune, new Effects().add(EntityEffects.CHANGE_ATTACK_ELEMENT, new ChangeAttackElementEffect.Instance(ElementalState.FIRE)).add(EntityEffects.FIRE_IMMUNITY));
		ItemEffects.register(ItemsAether.glamoured_taegore_tusk, new Effects().add(EntityEffects.LEECH_LIFE, new LeechLifeEffect.Instance(0.3D)));
		ItemEffects.register(ItemsAether.primal_totem_of_survival, new Effects().add(EntityEffects.MODIFY_COMPANION_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(20.0D)));
		ItemEffects.register(ItemsAether.primal_totem_of_rage, new Effects().add(EntityEffects.MODIFY_COMPANION_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D)));

		ItemEffects.register(ItemsAether.gravitite_core, new Effects().add(EntityEffects.EXTRA_JUMPS));
		ItemEffects.register(ItemsAether.valkyrie_wings, new Effects().add(EntityEffects.SLOWFALL));

		ItemEffects.register(ItemsAether.fleeting_scarf, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.2D)));
		ItemEffects.register(ItemsAether.winged_necklace, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.4D)));
		ItemEffects.register(ItemsAether.gust_amulet, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 5.0D)));
		ItemEffects.register(ItemsAether.typhoon_amulet, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.WATER, 5.0D)));
		ItemEffects.register(ItemsAether.chain_of_sporing_bones, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BLIGHT, 5.0D)));
		ItemEffects.register(ItemsAether.molten_amulet, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 5.0D)));
		ItemEffects.register(ItemsAether.granite_studded_choker, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 5.0D)));
		ItemEffects.register(ItemsAether.muggers_cloak, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.2D)));
		ItemEffects.register(ItemsAether.bandit_shawl, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.1D)));

		ItemEffects.register(ItemsAether.hide_pouch, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-3.0D)));
		ItemEffects.register(ItemsAether.gruegar_pouch, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-5.0D)));
		ItemEffects.register(ItemsAether.angel_bandage, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(0.5D)));
		ItemEffects.register(ItemsAether.swift_rune, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.01D)));
		ItemEffects.register(ItemsAether.wynd_cluster, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.02D)));
		ItemEffects.register(ItemsAether.wisdom_rune, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.20D)));

		ItemEffects.register(ItemsAether.gruegar_ring, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-10.0D)));
		ItemEffects.register(ItemsAether.ring_of_strength, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-7.5D)));
		ItemEffects.register(ItemsAether.arkenium_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 3.0D)).add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(10.0D)));
		ItemEffects.register(ItemsAether.swift_ribbon, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.05D)));
		ItemEffects.register(ItemsAether.wynd_cluster_ring, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.1D)));
		ItemEffects.register(ItemsAether.lesser_ring_of_wisdom, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.20D)));
		ItemEffects.register(ItemsAether.ring_of_wisdom, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.40D)));

		ItemEffects.register(ItemsAether.glamoured_cockatrice_keratin, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.BLIGHT, 0.5D)));

	}
}
