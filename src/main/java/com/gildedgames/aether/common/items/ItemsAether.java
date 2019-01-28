package com.gildedgames.aether.common.items;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.armor.*;
import com.gildedgames.aether.common.items.consumables.*;
import com.gildedgames.aether.common.items.misc.*;
import com.gildedgames.aether.common.items.tools.*;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBolt;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import com.gildedgames.aether.common.items.weapons.swords.*;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.util.selectors.RandomItemSelector;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Mod.EventBusSubscriber
public class ItemsAether
{
	public static final Item skyroot_stick = new ItemSkyrootStick();

	public static final Item cloudtwine = new ItemDropOnDeath();

	public static final ItemMoaFeather moa_feather = new ItemMoaFeather();

	public static final Item cockatrice_feather = new ItemDropOnDeath();

	public static final Item aether_saddle = new ItemDropOnDeath().setMaxStackSize(1);

	public static final Item ambrosium_shard = new ItemAmbrosiumShard(),
			ambrosium_chunk = new ItemAmbrosiumChunk(),
			zanite_gemstone = new ItemDropOnDeath(),
			arkenium = new ItemDropOnDeath(),
			arkenium_strip = new ItemDropOnDeath(),
			gravitite_plate = new ItemDropOnDeath();

	public static final ItemTool skyroot_axe = new ItemAetherAxe(MaterialsAether.SKYROOT_TOOL),
			skyroot_pickaxe = new ItemAetherPickaxe(MaterialsAether.SKYROOT_TOOL),
			skyroot_shovel = new ItemAetherShovel(MaterialsAether.SKYROOT_TOOL);

	public static final ItemTool holystone_axe = new ItemAetherAxe(MaterialsAether.HOLYSTONE_TOOL),
			holystone_pickaxe = new ItemAetherPickaxe(MaterialsAether.HOLYSTONE_TOOL),
			holystone_shovel = new ItemAetherShovel(MaterialsAether.HOLYSTONE_TOOL);

	public static final ItemTool zanite_axe = new ItemAetherAxe(MaterialsAether.ZANITE_TOOL),
			zanite_pickaxe = new ItemAetherPickaxe(MaterialsAether.ZANITE_TOOL),
			zanite_shovel = new ItemAetherShovel(MaterialsAether.ZANITE_TOOL);

	public static final ItemTool gravitite_axe = new ItemAetherAxe(MaterialsAether.GRAVITITE_TOOL),
			gravitite_pickaxe = new ItemAetherPickaxe(MaterialsAether.GRAVITITE_TOOL),
			gravitite_shovel = new ItemAetherShovel(MaterialsAether.GRAVITITE_TOOL);

	public static final ItemTool arkenium_axe = new ItemAetherAxe(MaterialsAether.ARKENIUM_TOOL, 7.0F, -3.3F),
			arkenium_pickaxe = new ItemAetherPickaxe(MaterialsAether.ARKENIUM_TOOL),
			arkenium_shovel = new ItemAetherShovel(MaterialsAether.ARKENIUM_TOOL);

	public static final ItemArkeniumShears arkenium_shears = new ItemArkeniumShears();

	public static final ItemAetherSword skyroot_sword = new ItemSkyrootSword(),
			holystone_sword = new ItemHolystoneSword(),
			zanite_sword = new ItemZaniteSword(),
			gravitite_sword = new ItemGravititeSword(),
			arkenium_sword = new ItemArkeniumSword();

	public static final ItemTaegoreHideArmor taegore_hide_helmet = new ItemTaegoreHideArmor(EntityEquipmentSlot.HEAD).impactDefense(2),
			taegore_hide_chestplate = new ItemTaegoreHideArmor(EntityEquipmentSlot.CHEST).slashDefense(2),
			taegore_hide_leggings = new ItemTaegoreHideArmor(EntityEquipmentSlot.LEGS).pierceDefense(2),
			taegore_hide_boots = new ItemTaegoreHideArmor(EntityEquipmentSlot.FEET).slashDefense(1);

	public static final ItemZaniteArmor zanite_helmet = new ItemZaniteArmor(EntityEquipmentSlot.HEAD).impactDefense(2),
			zanite_chestplate = new ItemZaniteArmor(EntityEquipmentSlot.CHEST).slashDefense(2),
			zanite_leggings = new ItemZaniteArmor(EntityEquipmentSlot.LEGS).pierceDefense(2),
			zanite_boots = new ItemZaniteArmor(EntityEquipmentSlot.FEET).pierceDefense(2);

	public static final ItemArkeniumArmor arkenium_helmet = new ItemArkeniumArmor(EntityEquipmentSlot.HEAD).impactDefense(1),
			arkenium_chestplate = new ItemArkeniumArmor(EntityEquipmentSlot.CHEST).slashDefense(2).impactDefense(1),
			arkenium_leggings = new ItemArkeniumArmor(EntityEquipmentSlot.LEGS).pierceDefense(2).impactDefense(1),
			arkenium_boots = new ItemArkeniumArmor(EntityEquipmentSlot.FEET).pierceDefense(1).impactDefense(1);

	public static final ItemGravititeArmor gravitite_helmet = new ItemGravititeArmor(EntityEquipmentSlot.HEAD).impactDefense(3),
			gravitite_chestplate = new ItemGravititeArmor(EntityEquipmentSlot.CHEST).slashDefense(3),
			gravitite_leggings = new ItemGravititeArmor(EntityEquipmentSlot.LEGS).pierceDefense(3),
			gravitite_boots = new ItemGravititeArmor(EntityEquipmentSlot.FEET).pierceDefense(2);

	public static final Item golden_amber = new ItemDropOnDeath(),
			taegore_hide = new ItemDropOnDeath(),
			burrukai_pelt = new ItemDropOnDeath(),
			swet_gel = new ItemSwetGel();

	public static final ItemAetherFood blueberries = new ItemAetherFood(2, false).setConsumptionDuration(12),
			enchanted_blueberry = new ItemAetherFood(6, false).setConsumptionDuration(12),
			orange = new ItemAetherFood(4, false).setConsumptionDuration(22),
			wyndberry = new ItemAetherFood(4, false).setConsumptionDuration(22),
			enchanted_wyndberry = new ItemEnchantedWyndberry().setConsumptionDuration(22),
			swet_jelly = new ItemSwetJelly(),
			raw_taegore_meat = new ItemAetherFood(5, 0.3F, true),
			taegore_steak = new ItemAetherFood(12, 0.5F, true),
			burrukai_rib_cut = new ItemAetherFood(5, 0.3F, true).setConsumptionDuration(40),
			burrukai_ribs = new ItemAetherFood(12, 0.5F, true).setConsumptionDuration(40),
			kirrid_loin = new ItemAetherFood(5, 0.3F, true),
			kirrid_cutlet = new ItemAetherFood(12, 0.5F, true);

	public static final ItemAetherFood candy_corn = new ItemAetherFood(4, 0.1F, false).setConsumptionDuration(5),
			cocoatrice = new ItemAetherFood(8, 0.2F, false).setConsumptionDuration(8),
			wrapped_chocolates = new ItemAetherFood(6, 0.25F, false).setConsumptionDuration(8),
			jelly_plumproot = new ItemAetherFood(6, 0.25F, false).setConsumptionDuration(15),
			stomper_pop = new ItemStomperPop(),
			blueberry_lollipop = new ItemAetherFood(4, 0.2F, false).setConsumptionDuration(8),
			orange_lollipop = new ItemAetherFood(4, 0.2F, false).setConsumptionDuration(8),
			icestone_poprocks = new ItemAetherFood(2, 0.5F, false).setConsumptionDuration(8);

	public static final ItemFood ginger_bread_man = new ItemAetherFood(6, 0.2F, false).setConsumptionDuration(8),
			candy_cane = new ItemAetherFood(5, 0.3F, false).setConsumptionDuration(10),
			yule_log = new ItemAetherFood(10, 0.25F, false).setConsumptionDuration(50);

	public static final ItemSkyrootBucket skyroot_bucket = new ItemSkyrootBucket(Blocks.AIR),
			skyroot_water_bucket = new ItemSkyrootBucket(Blocks.FLOWING_WATER);

	public static final ItemSkyrootConsumableBucket skyroot_milk_bucket = new ItemSkyrootConsumableBucket(),
			skyroot_poison_bucket = new ItemSkyrootConsumableBucket();

	public static final ItemAetherRecord valkyrie_music_disc = new ItemAetherRecord("valkyrie", SoundsAether.record_valkyrie),
			labyrinth_music_disc = new ItemAetherRecord("labyrinth", SoundsAether.record_labyrinth),
			moa_music_disc = new ItemAetherRecord("moa", SoundsAether.record_moa),
			aerwhale_music_disc = new ItemAetherRecord("aerwhale", SoundsAether.record_aerwhale),
			recording_892 = new ItemAetherRecord("recording_892", SoundsAether.record_recording_892);

	public static final Item healing_stone = new ItemHealingStone();

	public static final Item healing_stone_depleted = new ItemDropOnDeath();

	public static final ItemDartShooter dart_shooter = new ItemDartShooter();

	public static final ItemDart dart = new ItemDart();

	public static final Item valkyrie_wings = new ItemDropOnDeath();

	public static final ItemDoor skyroot_door = new ItemDoor(BlocksAether.skyroot_door),
			secret_skyroot_door = new ItemDoor(BlocksAether.secret_skyroot_door),
			arkenium_door = new ItemDoor(BlocksAether.arkenium_door);

	/*
		SLOWEST	- 35
		SLOW	- 30
		NORMAL	- 25
		FAST	- 20
		FASTEST	- 15
	 */
	public static final ItemCrossbow
			skyroot_crossbow = new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.SKYROOT),
			holystone_crossbow = new ItemCrossbow().setDurationInTicks(30).setKnockBackValue(0.7F).setType(ItemCrossbow.crossBowTypes.HOLYSTONE),
			zanite_crossbow = new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.ZANITE),
			arkenium_crossbow = new ItemCrossbow().setDurationInTicks(35).setKnockBackValue(0.5F).setType(ItemCrossbow.crossBowTypes.ARKENIUM),
			gravitite_crossbow = new ItemCrossbow().setDurationInTicks(25).setKnockBackValue(1.2F).setType(ItemCrossbow.crossBowTypes.GRAVETITE);

	public static final ItemBolt bolt = new ItemBolt();

	public static final Item icestone = new ItemDropOnDeath();

	public static final Item skyroot_sign = new ItemSkyrootSign();

	public static final Item aechor_petal = new ItemDropOnDeath();

	public static final ItemBrettlCane brettl_cane = new ItemBrettlCane();

	public static final Item brettl_grass = new ItemDropOnDeath();

	public static final Item taegore_hide_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.TAEGOREHIDE),
			zanite_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.ZANITE),
			arkenium_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.ARKENIUM),
			gravitite_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.GRAVITITE);

	public static final Item shard_of_life = new ItemShardOfLife();

	public static final ItemAetherShield skyroot_shield = new ItemAetherShield(),
			holystone_shield = new ItemAetherShield(),
			zanite_shield = new ItemAetherShield(),
			arkenium_shield = new ItemAetherShield(),
			gravitite_shield = new ItemAetherShield();

	public static final ItemSkyrootBed skyroot_bed = new ItemSkyrootBed();

	public static final ItemMoaEgg moa_egg = new ItemMoaEgg(false),
			rainbow_moa_egg = new ItemMoaEgg(true);

	public static final Item cloud_parachute = new ItemCloudParachute();

	public static final Item irradiated_chunk = new ItemIrradiated(new RandomItemSelector(stack -> !(stack instanceof ItemIrradiated))),
			irradiated_sword = new ItemIrradiated(new RandomItemSelector(item -> item.getTranslationKey().contains("sword")
					&& !(item instanceof ItemIrradiated))),
			irradiated_armor = new ItemIrradiated(new RandomItemSelector(item -> item instanceof ItemArmor)),
			irradiated_tool = new ItemIrradiated(new RandomItemSelector(item -> item instanceof ItemTool)),
			irradiated_ring = new ItemIrradiated(new RandomItemSelector(item ->
					AetherAPI.content().items().getProperties(item).getEquipmentSlot() == ItemEquipmentSlot.RING)),
			irradiated_neckwear = new ItemIrradiated(new RandomItemSelector(item ->
					AetherAPI.content().items().getProperties(item).getEquipmentSlot() == ItemEquipmentSlot.NECKWEAR)),
			irradiated_charm = new ItemIrradiated(new RandomItemSelector(item ->
					AetherAPI.content().items().getProperties(item).getEquipmentSlot() == ItemEquipmentSlot.CHARM)),
			irradiated_dust = new ItemIrradiatedVisuals();

	public static final Item wrapping_paper = new ItemWrappingPaper(),
			fried_moa_egg = new ItemFood(10, false);

	public static final Item swet_sugar = new ItemDropOnDeath();

	public static final Item plumproot_mash = new ItemAetherFood(3, 1.0F, false).setConsumptionDuration(12);

	public static final Item plumproot_pie = new ItemAetherFood(10, 0.5F, false).setConsumptionDuration(40);

	public static final ArmorMaterial WINTER_ARMOR = EnumHelper.addArmorMaterial("winter", "", 8,
			new int[] { 0, 0, 0, 0 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F).setRepairItem(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));

	public static final ItemWinterHat winter_hat = new ItemWinterHat();

	public static final Item eggnog = new ItemEggnog();

	private static final Collection<Item> registeredItems = new ArrayList<>();

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event)
	{
		final ItemRegistryHelper r = new ItemRegistryHelper(event.getRegistry());

		r.register("skyroot_stick", skyroot_stick.setCreativeTab(CreativeTabsAether.MATERIALS));

		r.register("ambrosium_shard", ambrosium_shard.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("ambrosium_chunk", ambrosium_chunk.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("zanite_gemstone", zanite_gemstone.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("arkenium", arkenium.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("arkenium_strip", arkenium_strip.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("icestone", icestone.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("gravitite_plate", gravitite_plate.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("cloudtwine", cloudtwine.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("moa_feather", moa_feather.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("cockatrice_feather", cockatrice_feather.setCreativeTab(CreativeTabsAether.MATERIALS));

		r.register("skyroot_axe", skyroot_axe);
		r.register("skyroot_pickaxe", skyroot_pickaxe);
		r.register("skyroot_shovel", skyroot_shovel);
		r.register("skyroot_sword", skyroot_sword);
		r.register("skyroot_shield", skyroot_shield);

		r.register("holystone_axe", holystone_axe);
		r.register("holystone_pickaxe", holystone_pickaxe);
		r.register("holystone_shovel", holystone_shovel);
		r.register("holystone_sword", holystone_sword);
		r.register("holystone_shield", holystone_shield);

		r.register("zanite_axe", zanite_axe);
		r.register("zanite_pickaxe", zanite_pickaxe);
		r.register("zanite_shovel", zanite_shovel);
		r.register("zanite_sword", zanite_sword);
		r.register("zanite_shield", zanite_shield);

		r.register("arkenium_axe", arkenium_axe);
		r.register("arkenium_pickaxe", arkenium_pickaxe);
		r.register("arkenium_shovel", arkenium_shovel);
		r.register("arkenium_sword", arkenium_sword);
		r.register("arkenium_shield", arkenium_shield);

		r.register("gravitite_axe", gravitite_axe);
		r.register("gravitite_pickaxe", gravitite_pickaxe);
		r.register("gravitite_shovel", gravitite_shovel);
		r.register("gravitite_sword", gravitite_sword);
		r.register("gravitite_shield", gravitite_shield);

		r.register("arkenium_shears", arkenium_shears.setCreativeTab(CreativeTabsAether.TOOLS));

		r.register("taegore_hide_helmet", taegore_hide_helmet);
		r.register("taegore_hide_chestplate", taegore_hide_chestplate);
		r.register("taegore_hide_leggings", taegore_hide_leggings);
		r.register("taegore_hide_boots", taegore_hide_boots);
		r.register("taegore_hide_gloves", taegore_hide_gloves);

		r.register("zanite_helmet", zanite_helmet);
		r.register("zanite_chestplate", zanite_chestplate);
		r.register("zanite_leggings", zanite_leggings);
		r.register("zanite_boots", zanite_boots);
		r.register("zanite_gloves", zanite_gloves);

		r.register("arkenium_helmet", arkenium_helmet);
		r.register("arkenium_chestplate", arkenium_chestplate);
		r.register("arkenium_leggings", arkenium_leggings);
		r.register("arkenium_boots", arkenium_boots);
		r.register("arkenium_gloves", arkenium_gloves);

		r.register("gravitite_helmet", gravitite_helmet);
		r.register("gravitite_chestplate", gravitite_chestplate);
		r.register("gravitite_leggings", gravitite_leggings);
		r.register("gravitite_boots", gravitite_boots);
		r.register("gravitite_gloves", gravitite_gloves);

		r.register("golden_amber", golden_amber.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("taegore_hide", taegore_hide.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("burrukai_pelt", burrukai_pelt.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("valkyrie_wings", valkyrie_wings.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("brettl_cane", brettl_cane.setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("brettl_grass", brettl_grass.setCreativeTab(CreativeTabsAether.MATERIALS));

		r.register("aechor_petal", aechor_petal.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		r.register("aether_saddle", aether_saddle.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		r.register("blueberries", blueberries);
		r.register("enchanted_blueberry", enchanted_blueberry);
		r.register("orange", orange);
		r.register("wyndberry", wyndberry);
		r.register("enchanted_wyndberry", enchanted_wyndberry);
		r.register("swet_jelly", swet_jelly);
		r.register("swet_gel", swet_gel.setCreativeTab(CreativeTabsAether.MATERIALS));

		r.register("swet_sugar", swet_sugar.setCreativeTab(CreativeTabsAether.MATERIALS));

		r.register("raw_taegore_meat", raw_taegore_meat);
		r.register("taegore_steak", taegore_steak);

		r.register("burrukai_rib_cut", burrukai_rib_cut);
		r.register("burrukai_ribs", burrukai_ribs);

		r.register("kirrid_loin", kirrid_loin);
		r.register("kirrid_cutlet", kirrid_cutlet);

		r.register("fried_moa_egg", fried_moa_egg.setCreativeTab(CreativeTabsAether.CONSUMABLES));

		r.register("candy_corn", candy_corn);
		r.register("cocoatrice", cocoatrice);
		r.register("wrapped_chocolates", wrapped_chocolates);
		r.register("jelly_plumproot", jelly_plumproot);
		r.register("stomper_pop", stomper_pop);
		r.register("blueberry_lollipop", blueberry_lollipop);
		r.register("orange_lollipop", orange_lollipop);
		r.register("icestone_poprocks", icestone_poprocks);

		r.register("ginger_bread_man", ginger_bread_man.setCreativeTab(CreativeTabsAether.CONSUMABLES));
		r.register("candy_cane", candy_cane.setCreativeTab(CreativeTabsAether.CONSUMABLES));
		r.register("yule_log", yule_log.setCreativeTab(CreativeTabsAether.CONSUMABLES));

		r.register("skyroot_bucket", skyroot_bucket);
		r.register("skyroot_water_bucket", skyroot_water_bucket);
		r.register("skyroot_milk_bucket", skyroot_milk_bucket);
		r.register("skyroot_poison_bucket", skyroot_poison_bucket);

		r.register("valkyrie_music_disc", valkyrie_music_disc);
		r.register("labyrinth_music_disc", labyrinth_music_disc);
		r.register("moa_music_disc", moa_music_disc);
		r.register("aerwhale_music_disc", aerwhale_music_disc);
		r.register("recording_892", recording_892);

		r.register("healing_stone_depleted", healing_stone_depleted.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CONSUMABLES));
		r.register("healing_stone", healing_stone.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CONSUMABLES));

		r.register("dart_shooter", dart_shooter.setCreativeTab(CreativeTabsAether.WEAPONS));
		r.register("dart", dart.setCreativeTab(CreativeTabsAether.WEAPONS));

		r.register("skyroot_crossbow", skyroot_crossbow);
		r.register("holystone_crossbow", holystone_crossbow);
		r.register("zanite_crossbow", zanite_crossbow);
		r.register("arkenium_crossbow", arkenium_crossbow);
		r.register("gravitite_crossbow", gravitite_crossbow);
		r.register("bolt", bolt.setCreativeTab(CreativeTabsAether.WEAPONS));

		r.register("skyroot_door_item", skyroot_door.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("secret_skyroot_door_item", secret_skyroot_door.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("arkenium_door_item", arkenium_door.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("skyroot_sign", skyroot_sign.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("shard_of_life", shard_of_life.setMaxStackSize(4).setCreativeTab(CreativeTabsAether.CONSUMABLES));

		r.register("skyroot_bed_item", skyroot_bed.setCreativeTab(CreativeTabsAether.UTILITY));

		r.register("moa_egg_item", moa_egg);
		r.register("rainbow_moa_egg", rainbow_moa_egg.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		r.register("cloud_parachute", cloud_parachute.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		r.register("irradiated_chunk", irradiated_chunk.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("irradiated_sword", irradiated_sword.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("irradiated_armor", irradiated_armor.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("irradiated_tool", irradiated_tool.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("irradiated_ring", irradiated_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("irradiated_neckwear", irradiated_neckwear.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("irradiated_charm", irradiated_charm.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		r.register("irradiated_dust", irradiated_dust.setCreativeTab(CreativeTabsAether.MATERIALS));

		r.register("wrapping_paper", wrapping_paper.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		r.register("plumproot_mash", plumproot_mash);
		r.register("plumproot_pie", plumproot_pie);
		r.register("winter_hat", winter_hat.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));
		r.register("eggnog", eggnog.setCreativeTab(CreativeTabsAether.CONSUMABLES));
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
