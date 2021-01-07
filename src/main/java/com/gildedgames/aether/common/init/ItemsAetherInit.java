package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.effects.StatusEffectBleed;
import com.gildedgames.aether.common.entities.effects.StatusEffectFreeze;
import com.gildedgames.aether.common.entities.effects.StatusEffectToxin;
import com.gildedgames.aether.common.items.ItemDropOnDeath;
import com.gildedgames.aether.common.items.accessories.ItemAccessory;
import com.gildedgames.aether.common.items.accessories.ItemDamageCharm;
import com.gildedgames.aether.common.items.armor.*;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomDoor;
import com.gildedgames.aether.common.items.consumables.*;
import com.gildedgames.aether.common.items.irradiated.ItemIrradiatedDust;
import com.gildedgames.aether.common.items.irradiated.ItemIrradiatedVisuals;
import com.gildedgames.aether.common.items.other.*;
import com.gildedgames.aether.common.items.tools.*;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBolt;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import com.gildedgames.aether.common.items.weapons.swords.*;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Mod.EventBusSubscriber
public class ItemsAetherInit
{
	private static final Collection<Item> registeredItems = new ArrayList<>();

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event)
	{
		final ItemRegistryHelper r = new ItemRegistryHelper(event.getRegistry());

		r.register("skyroot_door_item", new ItemBlockCustomDoor(BlocksAether.skyroot_door).setCreativeTab(CreativeTabsAether.TAB_CONSTRUCTION));
		r.register("arkenium_door_item", new ItemBlockCustomDoor(BlocksAether.arkenium_door).setCreativeTab(CreativeTabsAether.TAB_CONSTRUCTION));
		r.register("skyroot_sign", new ItemSkyrootSign().setCreativeTab(CreativeTabsAether.TAB_CONSTRUCTION));

		r.register("secret_skyroot_door_item", new ItemBlockCustomDoor(BlocksAether.secret_skyroot_door).setCreativeTab(CreativeTabsAether.TAB_DECORATIVE_BLOCKS));

		r.register("skyroot_bed_item", new ItemSkyrootBed().setCreativeTab(CreativeTabsAether.TAB_UTILITY));

		r.register("skyroot_stick", new ItemSkyrootStick().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("valkyrie_wings", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("brettl_cane", new ItemBrettlCane().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("brettl_grass", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("brettl_rope", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("skyroot_pinecone", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("golden_amber", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("cloudtwine", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("moa_feather", new ItemMoaFeather().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("taegore_hide", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("burrukai_pelt", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("swet_gel", new ItemSwetGel().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("swet_sugar", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("aechor_petal", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("cockatrice_feather", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("crude_scatterglass_shard", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("ambrosium_shard", new ItemAmbrosiumShard().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("ambrosium_chunk", new ItemAmbrosiumChunk().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("icestone", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("zanite_gemstone", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("arkenium", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("arkenium_strip", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("gravitite_plate", new ItemDropOnDeath().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("scatterglass_vial", new ItemScatterglassVial().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_dust", new ItemIrradiatedDust().setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_tool", new ItemIrradiatedVisuals().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_sword", new ItemIrradiatedVisuals().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_armor", new ItemIrradiatedVisuals().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_ring", new ItemIrradiatedVisuals().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_neckwear", new ItemIrradiatedVisuals().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));
		r.register("irradiated_charm", new ItemIrradiatedVisuals().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MATERIALS));

		r.register("blueberries", new ItemAetherFood(1,0.2F,false).setConsumptionDuration(16).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("enchanted_blueberry", new ItemAetherFood(3, 0.6F, false).setConsumptionDuration(16).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("orange", new ItemAetherFood(2, 0.3F, false).setConsumptionDuration(24).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("wyndberry", new ItemAetherFood(3, 0.3F, false).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("enchanted_wyndberry", new ItemEnchantedWyndberry().setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("plumproot_mash", new ItemAetherFood(2, 0.2F, false).setConsumptionDuration(24).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("plumproot_pie", new ItemAetherFood(7, 0.3F, false).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("fried_moa_egg", new ItemFood(5, 0.3F,false).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("skyroot_lizard_stick", new ItemAetherFood(2, 0.3F, false).setConsumptionDuration(16).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("skyroot_lizard_stick_roasted", new ItemAetherFood(6, 0.6F, false).setConsumptionDuration(24).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("kirrid_loin", new ItemAetherFood(2, 0.3F, true).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("kirrid_cutlet", new ItemAetherFood(6, 0.8F, true).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("raw_taegore_meat", new ItemAetherFood(2, 0.3F, true).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("taegore_steak", new ItemAetherFood(6, 0.8F, true).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("burrukai_rib_cut", new ItemAetherFood(4, 0.3F, true).setConsumptionDuration(40).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("burrukai_ribs", new ItemAetherFood(9, 0.8F, true).setConsumptionDuration(40).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("swet_jelly", new ItemSwetJelly().setConsumptionDuration(24).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("blueberry_lollipop", new ItemAetherFood(5, 0.6F, false).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("orange_lollipop", new ItemAetherFood(7, 0.6F, false).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("candy_corn", new ItemAetherFood(3, 0.1F, false).setConsumptionDuration(16).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("wrapped_chocolates", new ItemAetherFood(4, 0.3F, false).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("jelly_plumproot", new ItemAetherFood(6, 0.6F, false).setConsumptionDuration(32).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("cocoatrice", new ItemAetherFood(4, 0.3F, false).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("candy_cane", new ItemAetherFood(2, 0.1F, false).setConsumptionDuration(16).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("ginger_bread_man", new ItemAetherFood(3, 0.2F, false).setConsumptionDuration(24).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("icestone_poprocks", new ItemAetherFood(2, 0.1F, false).setConsumptionDuration(16).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("eggnog", new ItemEggnog().setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("yule_log", new ItemAetherFood(8, 0.6F, false).setConsumptionDuration(40).setCreativeTab(CreativeTabsAether.TAB_FOOD));
		r.register("stomper_pop", new ItemStomperPop().setCreativeTab(CreativeTabsAether.TAB_FOOD));

		r.register("healing_stone_depleted", new ItemDropOnDeath().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("healing_stone", new ItemHealingStone().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("bandage", new ItemCurative(IAetherStatusEffects.effectTypes.BLEED, false, 32, EnumAction.DRINK)
				.setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("splint", new ItemCurative(IAetherStatusEffects.effectTypes.FRACTURE,false, 32, EnumAction.DRINK)
				.setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("water_vial", new ItemWaterVial().setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("antitoxin_vial", new ItemCurative(IAetherStatusEffects.effectTypes.TOXIN,true, 32, EnumAction.DRINK)
				.setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("antivenom_vial", new ItemCurative(IAetherStatusEffects.effectTypes.COCKATRICE_VENOM,true, 32, EnumAction.DRINK)
				.setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("valkyrie_tea", new ItemTea(IAetherStatusEffects.effectTypes.SATURATION_BOOST, true).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));
		r.register("shard_of_life", new ItemShardOfLife().setMaxStackSize(4).setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES));

		r.register("skyroot_shovel", new ItemAetherShovel(MaterialsAether.SKYROOT_TOOL).setImpactDamageLevel(2.5F));
		r.register("skyroot_pickaxe", new ItemAetherPickaxe(MaterialsAether.SKYROOT_TOOL).setPierceDamageLevel(2.0F));
		r.register("skyroot_axe", new ItemAetherAxe(MaterialsAether.SKYROOT_TOOL).setImpactDamageLevel(7.0F));
		r.register("holystone_shovel", new ItemAetherShovel(MaterialsAether.HOLYSTONE_TOOL).setImpactDamageLevel(3.5F));
		r.register("holystone_pickaxe", new ItemAetherPickaxe(MaterialsAether.HOLYSTONE_TOOL).setPierceDamageLevel(3.0F));
		r.register("holystone_axe", new ItemAetherAxe(MaterialsAether.HOLYSTONE_TOOL, 8.0F, -3.2F).setImpactDamageLevel(9.0F));
		r.register("zanite_shovel", new ItemAetherShovel(MaterialsAether.ZANITE_TOOL).setImpactDamageLevel(4.5F));
		r.register("zanite_pickaxe", new ItemAetherPickaxe(MaterialsAether.ZANITE_TOOL).setPierceDamageLevel(4.0F));
		r.register("zanite_axe", new ItemAetherAxe(MaterialsAether.ZANITE_TOOL, 8.0F, -3.1F).setImpactDamageLevel(9.0F));
		r.register("arkenium_shovel", new ItemAetherShovel(MaterialsAether.ARKENIUM_TOOL, 1.5F, -3.5F).setImpactDamageLevel(6.5F));
		r.register("arkenium_pickaxe", new ItemAetherPickaxe(MaterialsAether.ARKENIUM_TOOL, 1.0F, -3.3F).setPierceDamageLevel(6.0F));
		r.register("arkenium_axe", new ItemAetherAxe(MaterialsAether.ARKENIUM_TOOL, 9.0F, -3.5F).setImpactDamageLevel(10.0F));
		r.register("gravitite_shovel", new ItemAetherShovel(MaterialsAether.GRAVITITE_TOOL).setImpactDamageLevel(5.5F));
		r.register("gravitite_pickaxe", new ItemAetherPickaxe(MaterialsAether.GRAVITITE_TOOL).setPierceDamageLevel(5.0F));
		r.register("gravitite_axe", new ItemAetherAxe(MaterialsAether.GRAVITITE_TOOL, 8.0F, -3.0F).setImpactDamageLevel(9.0F));
		r.register("arkenium_shears", new ItemArkeniumShears().setCreativeTab(CreativeTabsAether.TAB_TOOLS));

		r.register("dart_shooter", new ItemDartShooter().setCreativeTab(CreativeTabsAether.TAB_WEAPONS));
		r.register("dart", new ItemDart().setCreativeTab(CreativeTabsAether.TAB_WEAPONS));
		r.register("skyroot_sword", new ItemSkyrootSword().setSlashDamageLevel(4.0F));
		r.register("holystone_sword", new ItemHolystoneSword().setSlashDamageLevel(5.0F));
		r.register("zanite_sword", new ItemZaniteSword().setSlashDamageLevel(6.0F));
		r.register("arkenium_sword", new ItemArkeniumSword().setSlashDamageLevel(8.0F));
		r.register("gravitite_sword", new ItemGravititeSword().setSlashDamageLevel(7.0F));
		r.register("bolt", new ItemBolt().setCreativeTab(CreativeTabsAether.TAB_WEAPONS));
		r.register("skyroot_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.SKYROOT));
		r.register("holystone_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.7F).setType(ItemCrossbow.crossBowTypes.HOLYSTONE));
		r.register("zanite_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.ZANITE));
		r.register("arkenium_crossbow", new ItemCrossbow().setDurationInTicks(24).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.ARKENIUM));
		r.register("gravitite_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(1.2F).setType(ItemCrossbow.crossBowTypes.GRAVETITE));

		r.register("taegore_hide_helmet", new ItemTaegoreHideArmor(EntityEquipmentSlot.HEAD));
		r.register("taegore_hide_chestplate", new ItemTaegoreHideArmor(EntityEquipmentSlot.CHEST));
		r.register("taegore_hide_leggings", new ItemTaegoreHideArmor(EntityEquipmentSlot.LEGS));
		r.register("taegore_hide_boots", new ItemTaegoreHideArmor(EntityEquipmentSlot.FEET));
		r.register("taegore_hide_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.TAEGOREHIDE).setMaxDamage(60));
		r.register("skyroot_shield", new ItemAetherShield(ItemAetherShield.ShieldType.SKYROOT).setMaxDamage(114));
		r.register("burrukai_pelt_helmet", new ItemBurrukaiPeltArmor(EntityEquipmentSlot.HEAD));
		r.register("burrukai_pelt_chestplate", new ItemBurrukaiPeltArmor(EntityEquipmentSlot.CHEST));
		r.register("burrukai_pelt_leggings", new ItemBurrukaiPeltArmor(EntityEquipmentSlot.LEGS));
		r.register("burrukai_pelt_boots", new ItemBurrukaiPeltArmor(EntityEquipmentSlot.FEET));
		r.register("burrukai_pelt_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.BURRUKAIPELT).setMaxDamage(60));
		r.register("holystone_shield", new ItemAetherShield(ItemAetherShield.ShieldType.HOLYSTONE).setMaxDamage(253));
		r.register("zanite_helmet", new ItemZaniteArmor(EntityEquipmentSlot.HEAD));
		r.register("zanite_chestplate", new ItemZaniteArmor(EntityEquipmentSlot.CHEST));
		r.register("zanite_leggings", new ItemZaniteArmor(EntityEquipmentSlot.LEGS));
		r.register("zanite_boots", new ItemZaniteArmor(EntityEquipmentSlot.FEET));
		r.register("zanite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ZANITE).setMaxDamage(180));
		r.register("zanite_shield", new ItemAetherShield(ItemAetherShield.ShieldType.ZANITE).setMaxDamage(482));
		r.register("arkenium_helmet", new ItemArkeniumArmor(EntityEquipmentSlot.HEAD));
		r.register("arkenium_chestplate", new ItemArkeniumArmor(EntityEquipmentSlot.CHEST));
		r.register("arkenium_leggings", new ItemArkeniumArmor(EntityEquipmentSlot.LEGS));
		r.register("arkenium_boots", new ItemArkeniumArmor(EntityEquipmentSlot.FEET));
		r.register("arkenium_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ARKENIUM).setMaxDamage(528));
		r.register("arkenium_shield", new ItemAetherShield(ItemAetherShield.ShieldType.ARKENIUM).setMaxDamage(6154));
		r.register("gravitite_helmet", new ItemGravititeArmor(EntityEquipmentSlot.HEAD));
		r.register("gravitite_chestplate", new ItemGravititeArmor(EntityEquipmentSlot.CHEST));
		r.register("gravitite_leggings", new ItemGravititeArmor(EntityEquipmentSlot.LEGS));
		r.register("gravitite_boots", new ItemGravititeArmor(EntityEquipmentSlot.FEET));
		r.register("gravitite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.GRAVITITE).setMaxDamage(396));
		r.register("gravitite_shield", new ItemAetherShield(ItemAetherShield.ShieldType.GRAVITITE).setMaxDamage(3010));

		r.register("rainbow_moa_egg", new ItemMoaEgg(true).setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("aether_saddle", new ItemDropOnDeath().setMaxStackSize(1).setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("moa_feed", new ItemMoaFeed(ItemMoaFeed.MoaFeedType.BASIC).setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("moa_feed_blueberries", new ItemMoaFeed(ItemMoaFeed.MoaFeedType.BLUEBERRY).setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("moa_feed_enchanted_blueberries", new ItemMoaFeed(ItemMoaFeed.MoaFeedType.ENCHANTED_BLUEBERRY).setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("cloud_parachute", new ItemCloudParachute().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("skyroot_bucket", new ItemSkyrootBucket(Blocks.AIR));
		r.register("skyroot_water_bucket", new ItemSkyrootBucket(Blocks.FLOWING_WATER));
		r.register("skyroot_milk_bucket", new ItemSkyrootConsumableBucket());
		r.register("skyroot_poison_bucket", new ItemSkyrootConsumableBucket());
		r.register("aerwhale_music_disc", new ItemAetherRecord("aerwhale", "records.aerwhale"));
		r.register("moa_music_disc", new ItemAetherRecord("moa", "records.moa"));
		r.register("labyrinth_music_disc", new ItemAetherRecord("labyrinth", "records.labyrinth"));
		r.register("valkyrie_music_disc", new ItemAetherRecord("valkyrie", "records.valkyrie"));
		r.register("recording_892", new ItemAetherRecord("recording_892", "records.recording_892"));
		r.register("winter_hat", new ItemWinterHat().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));
		r.register("wrapping_paper", new ItemWrappingPaper().setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS));

		r.register("moa_egg_item", new ItemMoaEgg(false));

		r.register("charm_arm_01", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_arm_02", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_arm_03", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_arm_tgh_01", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_arm_tgh_02", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_arm_tgh_03", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_imp_dmg_01", new ItemDamageCharm().setImpactDamageLevel(0.5F).setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_imp_dmg_02", new ItemDamageCharm().setImpactDamageLevel(1.0F).setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_imp_dmg_03", new ItemDamageCharm().setImpactDamageLevel(1.5F).setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_atk_spd_01", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_atk_spd_02", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_atk_spd_03", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_kbk_res_01", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_kbk_res_02", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_kbk_res_03", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_lck_01", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_lck_02", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_lck_03", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_max_hlt_01", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_max_hlt_02", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_max_hlt_03", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_mve_spd_01", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_mve_spd_02", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_mve_spd_03", new ItemAccessory().setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_prc_dmg_01", new ItemDamageCharm().setPierceDamageLevel(0.5F).setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_prc_dmg_02", new ItemDamageCharm().setPierceDamageLevel(1.0F).setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_prc_dmg_03", new ItemDamageCharm().setPierceDamageLevel(1.5F).setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_slsh_dmg_01", new ItemDamageCharm().setSlashDamageLevel(0.5F).setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_slsh_dmg_02", new ItemDamageCharm().setSlashDamageLevel(1.0F).setCreativeTab(CreativeTabsAether.TAB_CHARMS));
		r.register("charm_slsh_dmg_03", new ItemDamageCharm().setSlashDamageLevel(1.5F).setCreativeTab(CreativeTabsAether.TAB_CHARMS));
	}

	public static Collection<Item> getRegisteredItems()
	{
		return Collections.unmodifiableCollection(registeredItems);
	}

	private static class ItemRegistryHelper
	{
		private final IForgeRegistry<Item> registry;

		ItemRegistryHelper(final IForgeRegistry<Item> registry)
		{
			this.registry = registry;
		}

		private void register(final String registryName, final Item item)
		{
			item.setTranslationKey(AetherCore.MOD_ID + "." + registryName);
			item.setRegistryName(registryName);

			this.registry.register(item);

			registeredItems.add(item);
		}
	}
}