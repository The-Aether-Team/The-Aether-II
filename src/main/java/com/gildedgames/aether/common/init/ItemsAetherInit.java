package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.items.armor.*;
import com.gildedgames.aether.common.items.consumables.*;
import com.gildedgames.aether.common.items.irradiated.ItemIrradiated;
import com.gildedgames.aether.common.items.irradiated.ItemIrradiatedVisuals;
import com.gildedgames.aether.common.items.other.*;
import com.gildedgames.aether.common.items.tools.*;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBolt;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import com.gildedgames.aether.common.items.weapons.swords.*;
import com.gildedgames.aether.common.util.selectors.RandomItemSelector;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
		r.register("aechor_petal", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("aerwhale_music_disc", new ItemAetherRecord(new SoundEvent(AetherCore.getResource("records.aerwhale")), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("aether_saddle", new Item(new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("ambrosium_chunk", new ItemAmbrosiumChunk(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("ambrosium_shard", new ItemAmbrosiumShard(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("arkenium", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("arkenium_axe", new ItemAetherAxe(MaterialsAether.ARKENIUM_TOOL, 10.0F, -5.0F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("arkenium_boots", new ItemArkeniumArmor(EquipmentSlotType.FEET, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setPierceDefense(1).setImpactDefense(1));
		r.register("arkenium_chestplate", new ItemArkeniumArmor(EquipmentSlotType.CHEST, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setSlashDefense(2).setImpactDefense(1));
		r.register("arkenium_crossbow", new ItemCrossbow(ItemCrossbow.Type.ARKENIUM, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)).setDurationInTicks(35).setKnockBackValue(0.5F));
		r.register("arkenium_door_item", new TallBlockItem(BlocksAether.arkenium_door, new Item.Properties().maxStackSize(16).group(CreativeTabsAether.TAB_CONSTRUCTION)));
		r.register("arkenium_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ARKENIUM, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)));
		r.register("arkenium_helmet", new ItemArkeniumArmor(EquipmentSlotType.HEAD, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setImpactDefense(1));
		r.register("arkenium_leggings", new ItemArkeniumArmor(EquipmentSlotType.LEGS, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setPierceDefense(2).setImpactDefense(1));
		r.register("arkenium_pickaxe", new ItemAetherPickaxe(MaterialsAether.ARKENIUM_TOOL, 1, -2.8F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("arkenium_shears", new ItemArkeniumShears(new Item.Properties().maxDamage(238).group(CreativeTabsAether.TAB_TOOLS)));
		r.register("arkenium_shield", new ItemAetherShield(new Item.Properties().maxDamage(336).group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("arkenium_shovel", new ItemAetherShovel(MaterialsAether.ARKENIUM_TOOL, 1.5F, -3.0F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("arkenium_strip", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("arkenium_sword", new ItemAetherSword(MaterialsAether.ARKENIUM_TOOL, ItemAbilityType.PASSIVE, 7, -3.0F, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("blueberries", new Item(new Item.Properties().food(new Food.Builder().hunger(2).saturation(0.2F).fastToEat().build())));
		r.register("blueberry_lollipop", new Item(new Item.Properties().food(new Food.Builder().hunger(4).saturation(0.2F).fastToEat().build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("skyroot_bolt", new ItemBolt(ItemBoltType.SKYROOT, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("holystone_bolt", new ItemBolt(ItemBoltType.HOLYSTONE, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("scatterglass_bolt", new ItemBolt(ItemBoltType.SCATTERGLASS, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("zanite_bolt", new ItemBolt(ItemBoltType.ZANITE, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("arkenium_bolt", new ItemBolt(ItemBoltType.ARKENIUM, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("gravitite_bolt", new ItemBolt(ItemBoltType.GRAVITITE, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("brettl_cane", new ItemBrettlCane(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("brettl_grass", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("burrukai_pelt", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("burrukai_rib_cut", new Item(new Item.Properties().food(new Food.Builder().hunger(5).saturation(0.3F).meat().build())));
		r.register("burrukai_ribs", new Item(new Item.Properties().food(new Food.Builder().hunger(12).saturation(0.3F).meat().build())));
		r.register("candy_cane", new Item(new Item.Properties().food(new Food.Builder().hunger(5).saturation(0.3F).fastToEat().build())));
		r.register("candy_corn", new Item(new Item.Properties().food(new Food.Builder().hunger(4).saturation(0.1F).fastToEat().build())));
		r.register("cloudtwine", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("cockatrice_feather", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("cocoatrice", new Item(new Item.Properties().food(new Food.Builder().hunger(8).saturation(0.2F).build())));
		r.register("dart", new ItemDart(new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("golden_dart_shooter", new ItemDartShooter(ItemDartType.GOLDEN, new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("enchanted_dart_shooter", new ItemDartShooter(ItemDartType.ENCHANTED, new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("posion_shooter", new ItemDartShooter(ItemDartType.POISON, new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("eggnog", new ItemEggnog(new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("enchanted_blueberry", new Item(new Item.Properties().food(new Food.Builder().hunger(6).build())));
		r.register("enchanted_wyndberry", new Item(new Item.Properties().food(new Food.Builder().hunger(8).effect(new EffectInstance(Effects.RESISTANCE, 200, 1), 1.0f).effect(new EffectInstance(Effects.HASTE, 200, 1), 1.0f).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("fried_moa_egg", new Item(new Item.Properties().food(new Food.Builder().hunger(10).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("ginger_bread_man", new Item(new Item.Properties().food(new Food.Builder().hunger(6).saturation(0.2F).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("golden_amber", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("gravitite_axe", new ItemAetherAxe(MaterialsAether.GRAVITITE_TOOL, 5.0F, -3.0F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("gravitite_boots", new ItemGravititeArmor(EquipmentSlotType.FEET, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setPierceDefense(2));
		r.register("gravitite_chestplate", new ItemGravititeArmor(EquipmentSlotType.CHEST, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setSlashDefense(3));
		r.register("gravitite_crossbow", new ItemCrossbow(ItemCrossbow.Type.GRAVETITE, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)).setDurationInTicks(25).setKnockBackValue(1.2F));
		r.register("gravitite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.GRAVITITE, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)));
		r.register("gravitite_helmet", new ItemGravititeArmor(EquipmentSlotType.HEAD, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setImpactDefense(3));
		r.register("gravitite_leggings", new ItemGravititeArmor(EquipmentSlotType.LEGS, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setPierceDefense(3));
		r.register("gravitite_pickaxe", new ItemAetherPickaxe(MaterialsAether.GRAVITITE_TOOL, 1, -2.8F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("gravitite_plate", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("gravitite_shield", new ItemAetherShield(new Item.Properties().maxDamage(336).group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("gravitite_shovel", new ItemAetherShovel(MaterialsAether.GRAVITITE_TOOL, 1.5F, -3.0F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("gravitite_sword", new ItemGravititeSword(MaterialsAether.GRAVITITE_TOOL, ItemAbilityType.ACTIVE, 3, -2.4F, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("healing_stone", new ItemHealingStone(new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("healing_stone_depleted", new Item(new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("holystone_axe", new ItemAetherAxe(MaterialsAether.HOLYSTONE_TOOL, 7.0F, -3.2F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("holystone_crossbow", new ItemCrossbow(ItemCrossbow.Type.HOLYSTONE, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)).setDurationInTicks(30).setKnockBackValue(0.7F));
		r.register("holystone_pickaxe", new ItemAetherPickaxe(MaterialsAether.HOLYSTONE_TOOL, 1, -2.8F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("holystone_shield", new ItemAetherShield(new Item.Properties().maxDamage(336).group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("holystone_shovel", new ItemAetherShovel(MaterialsAether.HOLYSTONE_TOOL, 1.5F, -3.0F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("holystone_sword", new ItemHolystoneSword(MaterialsAether.HOLYSTONE_TOOL, ItemAbilityType.PASSIVE, 3, -2.4F, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("icestone", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("icestone_poprocks", new Item(new Item.Properties().food(new Food.Builder().hunger(2).saturation(0.5F).fastToEat().build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("irradiated_armor", new ItemIrradiated(new RandomItemSelector(item -> item instanceof ArmorItem), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("irradiated_charm", new ItemIrradiated(
				new RandomItemSelector(item -> AetherAPI.content().items().getProperties(item).getEquipmentSlot() == ItemEquipmentSlot.CHARM), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("irradiated_chunk", new ItemIrradiated(new RandomItemSelector(stack -> !(stack instanceof ItemIrradiated)), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("irradiated_dust", new ItemIrradiatedVisuals(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("irradiated_neckwear", new ItemIrradiated(
				new RandomItemSelector(item -> AetherAPI.content().items().getProperties(item).getEquipmentSlot() == ItemEquipmentSlot.NECKWEAR), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("irradiated_ring",
				new ItemIrradiated(new RandomItemSelector(item -> AetherAPI.content().items().getProperties(item).getEquipmentSlot() == ItemEquipmentSlot.RING), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("irradiated_sword",
				new ItemIrradiated(new RandomItemSelector(item -> item.getTranslationKey().contains("sword") && !(item instanceof ItemIrradiated)), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("irradiated_tool", new ItemIrradiated(new RandomItemSelector(item -> item instanceof ToolItem), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("jelly_plumproot", new Item(new Item.Properties().food(new Food.Builder().hunger(6).saturation(0.25F).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("kirrid_cutlet", new Item(new Item.Properties().food(new Food.Builder().hunger(12).saturation(0.5f).meat().build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("kirrid_loin", new Item(new Item.Properties().food(new Food.Builder().hunger(5).saturation(0.3F).meat().build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("labyrinth_music_disc", new ItemAetherRecord(new SoundEvent(AetherCore.getResource("records.labyrinth")), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("moa_egg_item", new ItemMoaEgg(false, new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("moa_feather", new ItemMoaFeather(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("moa_music_disc", new ItemAetherRecord(new SoundEvent(AetherCore.getResource("records.moa")), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("orange", new Item(new Item.Properties().food(new Food.Builder().hunger(4).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("orange_lollipop", new Item(new Item.Properties().food(new Food.Builder().hunger(4).saturation(0.2F).build())));
		r.register("plumproot_mash", new Item(new Item.Properties().food(new Food.Builder().hunger(3).saturation(1.0f).build())));
		r.register("plumproot_pie", new Item(new Item.Properties().food(new Food.Builder().hunger(10).saturation(0.5F).build())));
		r.register("rainbow_moa_egg", new ItemMoaEgg(true, new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("raw_taegore_meat", new Item(new Item.Properties().food(new Food.Builder().hunger(5).saturation(0.3F).meat().build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("recording_892", new ItemAetherRecord(new SoundEvent(AetherCore.getResource("records.recording_892")), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("secret_skyroot_door_item", new TallBlockItem(BlocksAether.secret_skyroot_door, new Item.Properties().maxStackSize(16).group(CreativeTabsAether.TAB_DECORATIVE_BLOCKS)));
		r.register("shard_of_life", new ItemShardOfLife(new Item.Properties().maxStackSize(4).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("skyroot_axe", new ItemAetherAxe(MaterialsAether.SKYROOT_TOOL, 6.0F, -3.2F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("skyroot_bed_item", new BedItem(BlocksAether.skyroot_bed, new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_UTILITY)));
		r.register("skyroot_bucket", new BucketItem(Fluids.EMPTY, new Item.Properties().maxStackSize(8).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("skyroot_crossbow", new ItemCrossbow(ItemCrossbow.Type.SKYROOT, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)).setDurationInTicks(20).setKnockBackValue(0.5F));
		r.register("skyroot_door_item", new TallBlockItem(BlocksAether.skyroot_door, new Item.Properties().maxStackSize(16).group(CreativeTabsAether.TAB_CONSTRUCTION)));
		r.register("skyroot_lizard_stick", new Item(new Item.Properties().food(new Food.Builder().hunger(2).saturation(0.5f).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("skyroot_lizard_stick_roasted", new Item(new Item.Properties().food(new Food.Builder().hunger(8).saturation(0.5F).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("skyroot_milk_bucket", new ItemSkyrootConsumableBucket(new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("skyroot_pickaxe", new ItemAetherPickaxe(MaterialsAether.SKYROOT_TOOL, 1, -2.8F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("skyroot_poison_bucket", new ItemSkyrootConsumableBucket(new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("skyroot_shield", new ItemAetherShield(new Item.Properties().maxDamage(336).group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("skyroot_shovel", new ItemAetherShovel(MaterialsAether.SKYROOT_TOOL, 1.5F, -3.0F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("skyroot_sign", new WallOrFloorItem(BlocksAether.wall_skyroot_sign, BlocksAether.standing_skyroot_sign, new Item.Properties().maxStackSize(16).group(CreativeTabsAether.TAB_CONSTRUCTION)));
		r.register("skyroot_stick", new ItemSkyrootStick(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("skyroot_sword", new ItemAetherSword(MaterialsAether.SKYROOT_TOOL, ItemAbilityType.PASSIVE, 3, -2.4F, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("skyroot_water_bucket", new BucketItem(Fluids.WATER, new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("stomper_pop", new Item(new Item.Properties().food(new Food.Builder().hunger(20).saturation(0.5F).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("blue_swet_gel", new ItemSwetGel(EntitySwet.Type.BLUE, new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("green_swet_gel", new ItemSwetGel(EntitySwet.Type.GREEN, new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("purple_swet_gel", new ItemSwetGel(EntitySwet.Type.PURPLE, new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("blue_swet_jelly", new ItemSwetJelly(EntitySwet.Type.BLUE, new Item.Properties().food(new Food.Builder().hunger(5).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("green_swet_jelly", new ItemSwetJelly(EntitySwet.Type.GREEN, new Item.Properties().food(new Food.Builder().hunger(5).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("purple_swet_jelly", new ItemSwetJelly(EntitySwet.Type.PURPLE, new Item.Properties().food(new Food.Builder().hunger(5).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("swet_sugar", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("taegore_hide", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("taegorfinal ItemStack stacke_hide_boots", new ItemTaegoreHideArmor(EquipmentSlotType.FEET, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setSlashDefense(1));
		r.register("taegore_hide_chestplate", new ItemTaegoreHideArmor(EquipmentSlotType.CHEST, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setSlashDefense(2));
		r.register("taegore_hide_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.TAEGOREHIDE, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)));
		r.register("taegore_hide_helmet", new ItemTaegoreHideArmor(EquipmentSlotType.HEAD, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setImpactDefense(2));
		r.register("taegore_hide_leggings", new ItemTaegoreHideArmor(EquipmentSlotType.LEGS, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setPierceDefense(2));
		r.register("taegore_steak", new Item(new Item.Properties().food(new Food.Builder().hunger(12).saturation(0.5f).meat().build())));
		r.register("valkyrie_music_disc", new ItemAetherRecord(new SoundEvent(AetherCore.getResource("records.valkyrie")), new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("valkyrie_wings", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("winter_hat", new ItemWinterHat(new Item.Properties().maxStackSize(1).group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("wrapped_chocolates", new Item(new Item.Properties().food(new Food.Builder().hunger(6).saturation(0.25f).fastToEat().build())));
		r.register("wrapping_paper", new ItemWrappingPaper(new Item.Properties().group(CreativeTabsAether.TAB_MISCELLANEOUS)));
		r.register("wyndberry", new Item(new Item.Properties().food(new Food.Builder().hunger(4).build())));
		r.register("yule_log", new Item(new Item.Properties().food(new Food.Builder().hunger(10).saturation(0.25F).build()).group(CreativeTabsAether.TAB_CONSUMABLES)));
		r.register("zanite_axe", new ItemAetherAxe(MaterialsAether.ZANITE_TOOL, 6.0F, -3.1F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("zanite_boots", new ItemZaniteArmor(EquipmentSlotType.FEET, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setPierceDefense(2));
		r.register("zanite_chestplate", new ItemZaniteArmor(EquipmentSlotType.CHEST, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setSlashDefense(2));
		r.register("zanite_crossbow", new ItemCrossbow(ItemCrossbow.Type.ZANITE, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)).setDurationInTicks(20).setKnockBackValue(0.5F));
		r.register("zanite_gemstone", new Item(new Item.Properties().group(CreativeTabsAether.TAB_MATERIALS)));
		r.register("zanite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ZANITE, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)));
		r.register("zanite_helmet", new ItemZaniteArmor(EquipmentSlotType.HEAD, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setImpactDefense(2));
		r.register("zanite_leggings", new ItemZaniteArmor(EquipmentSlotType.LEGS, new Item.Properties().group(CreativeTabsAether.TAB_ARMOR)).setPierceDefense(2));
		r.register("zanite_pickaxe", new ItemAetherPickaxe(MaterialsAether.ZANITE_TOOL, 1, -2.8F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("zanite_shield", new ItemAetherShield(new Item.Properties().maxDamage(336).group(CreativeTabsAether.TAB_WEAPONS)));
		r.register("zanite_shovel", new ItemAetherShovel(MaterialsAether.ZANITE_TOOL, 1.5F, -3.0F, new Item.Properties().group(CreativeTabsAether.TAB_TOOLS)));
		r.register("zanite_sword", new ItemZaniteSword(MaterialsAether.ZANITE_TOOL, ItemAbilityType.PASSIVE, 3, -2.4F, new Item.Properties().group(CreativeTabsAether.TAB_WEAPONS)));
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
			item.setRegistryName(registryName);

			this.registry.register(item);

			registeredItems.add(item);
		}
	}
}
