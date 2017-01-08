package com.gildedgames.aether.common.items;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.IEquipmentProperties;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.companions.*;
import com.gildedgames.aether.common.items.armor.*;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import com.gildedgames.aether.common.items.companions.ItemDeathSeal;
import com.gildedgames.aether.common.items.consumables.*;
import com.gildedgames.aether.common.items.misc.*;
import com.gildedgames.aether.common.items.tools.ItemAetherAxe;
import com.gildedgames.aether.common.items.tools.ItemAetherPickaxe;
import com.gildedgames.aether.common.items.tools.ItemAetherShovel;
import com.gildedgames.aether.common.items.tools.ItemSkyrootBucket;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.ItemVampireBlade;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBolt;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import com.gildedgames.aether.common.items.weapons.swords.*;
import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.util.selectors.RandomItemSelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class ItemsAether
{
	private static final Collection<Item> registeredItems = new ArrayList<>();

	public static final Item skyroot_stick = new Item();

	public static final Item cloudwool = new Item();

	public static final ItemMoaFeather moa_feather = new ItemMoaFeather();

	public static final Item cockatrice_feather = new Item();

	public static final Item ambrosium_shard = new Item(),
			ambrosium_chunk = new ItemAmbrosiumChunk(),
			zanite_gemstone = new Item(),
			arkenium = new Item(),
			arkenium_strip = new Item(),
			arkenium_ore = new Item(),
			gravitite_ore = new Item(),
			gravitite_plate = new Item();

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

	public static final ItemTool valkyrie_axe = new ItemAetherAxe(MaterialsAether.LEGENDARY_TOOL),
			valkyrie_pickaxe = new ItemAetherPickaxe(MaterialsAether.LEGENDARY_TOOL),
			valkyrie_shovel = new ItemAetherShovel(MaterialsAether.LEGENDARY_TOOL);

	public static final ItemTool arkenium_axe = new ItemAetherAxe(MaterialsAether.ARKENIUM_TOOL, 7.0F, -3.3F),
			arkenium_pickaxe = new ItemAetherPickaxe(MaterialsAether.ARKENIUM_TOOL),
			arkenium_shovel = new ItemAetherShovel(MaterialsAether.ARKENIUM_TOOL);

	public static final ItemShears arkenium_shears = new ItemShears();

	public static final ItemAetherSword skyroot_sword = new ItemSkyrootSword(),
			holystone_sword = new ItemHolystoneSword(),
			zanite_sword = new ItemZaniteSword(),
			gravitite_sword = new ItemGravititeSword(),
			arkenium_sword = new ItemArkeniumSword();

	public static final ItemZaniteArmor zanite_helmet = new ItemZaniteArmor(EntityEquipmentSlot.HEAD),
			zanite_chestplate = new ItemZaniteArmor(EntityEquipmentSlot.CHEST),
			zanite_leggings = new ItemZaniteArmor(EntityEquipmentSlot.LEGS),
			zanite_boots = new ItemZaniteArmor(EntityEquipmentSlot.FEET);

	public static final ItemGravititeArmor gravitite_helmet = new ItemGravititeArmor(EntityEquipmentSlot.HEAD),
			gravitite_chestplate = new ItemGravititeArmor(EntityEquipmentSlot.CHEST),
			gravitite_leggings = new ItemGravititeArmor(EntityEquipmentSlot.LEGS),
			gravitite_boots = new ItemGravititeArmor(EntityEquipmentSlot.FEET);

	public static final ItemNeptuneArmor neptune_helmet = new ItemNeptuneArmor(EntityEquipmentSlot.HEAD),
			neptune_chestplate = new ItemNeptuneArmor(EntityEquipmentSlot.CHEST),
			neptune_leggings = new ItemNeptuneArmor(EntityEquipmentSlot.LEGS),
			neptune_boots = new ItemNeptuneArmor(EntityEquipmentSlot.FEET);

	public static final ItemPhoenixArmor phoenix_helmet = new ItemPhoenixArmor(EntityEquipmentSlot.HEAD),
			phoenix_chestplate = new ItemPhoenixArmor(EntityEquipmentSlot.CHEST),
			phoenix_leggings = new ItemPhoenixArmor(EntityEquipmentSlot.LEGS),
			phoenix_boots = new ItemPhoenixArmor(EntityEquipmentSlot.FEET);

	public static final ItemValkyrieArmor valkyrie_helmet = new ItemValkyrieArmor(EntityEquipmentSlot.HEAD),
			valkyrie_chestplate = new ItemValkyrieArmor(EntityEquipmentSlot.CHEST),
			valkyrie_leggings = new ItemValkyrieArmor(EntityEquipmentSlot.LEGS),
			valkyrie_boots = new ItemValkyrieArmor(EntityEquipmentSlot.FEET);

	public static final Item golden_amber = new Item(),
			taegore_hide = new Item(),
			burrukai_pelt = new Item();

	public static final ItemAetherFood blueberries = new ItemAetherFood(2, false).setConsumptionDuration(12),
			enchanted_blueberry = new ItemAetherFood(6, false).setConsumptionDuration(12),
			orange = new ItemAetherFood(4, false).setConsumptionDuration(22),
			wyndberry = new ItemAetherFood(4, false).setConsumptionDuration(22),
			enchanted_wyndberry = new ItemEnchantedWyndberry().setConsumptionDuration(22),
			swet_jelly = new ItemSwetJelly(),
			gummy_swet = new ItemGummySwet(),
			raw_taegore_meat = new ItemAetherFood(3, 0.3F, false),
			taegore_steak = new ItemAetherFood(8, 0.8F, false),
			burrukai_rib_cut = new ItemAetherFood(3, 0.3F, false),
			burrukai_ribs = new ItemAetherFood(8, 0.8F, false),
			kirrid_loin = new ItemAetherFood(3, 0.3F, false),
			kirrid_cutlet = new ItemAetherFood(8, 0.8F, false);

	public static final ItemAetherFood candy_corn = new ItemAetherFood(8, false),
			cocoatrice = new ItemAetherFood(12, false),
			wrapped_chocolates = new ItemAetherFood(12, false),
			jelly_pumpkin = new ItemAetherFood(12, false),
			stomper_pop = new ItemStomperPop(),
			blueberry_lollipop = new ItemAetherFood(10, false),
			orange_lollipop = new ItemAetherFood(8, false),
			icestone_poprocks = new ItemAetherFood(5, false);

	public static final ItemFood ginger_bread_man = new ItemFood(2, false),
			candy_cane = new ItemFood(2, false);

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

	public static final Item healing_stone_depleted = new Item();

	public static final ItemDartShooter dart_shooter = new ItemDartShooter();

	public static final ItemDart dart = new ItemDart();

	public static final ItemElementalSword flaming_sword = new ItemElementalSword(ItemElementalSword.SwordElement.FIRE),
			holy_sword = new ItemElementalSword(ItemElementalSword.SwordElement.HOLY),
			lightning_sword = new ItemElementalSword(ItemElementalSword.SwordElement.LIGHTNING);

	public static final ItemSword vampire_blade = new ItemVampireBlade(),
			candy_cane_sword = new ItemCandyCaneSword(),
			valkyrie_lance = new ItemAetherSword(MaterialsAether.LEGENDARY_TOOL, ItemAbilityType.PASSIVE);

	public static final ItemDoor skyroot_door = new ItemDoor(BlocksAether.skyroot_door),
			arkenium_door = new ItemDoor(BlocksAether.arkenium_door),
			blightwillow_door = new ItemDoor(BlocksAether.blightwillow_door),
			earthshifter_door = new ItemDoor(BlocksAether.earthshifter_door),
			frostpine_door = new ItemDoor(BlocksAether.frostpine_door);

	public static final ItemCrossbow skyroot_crossbow = new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F),
			holystone_crossbow = new ItemCrossbow().setDurationInTicks(30).setKnockBackValue(0.7F),
			zanite_crossbow = new ItemCrossbow().setDurationInTicks(15).setKnockBackValue(0.5F),
			arkenium_crossbow = new ItemCrossbow().setDurationInTicks(35).setKnockBackValue(0.5F),
			gravitite_crossbow = new ItemCrossbow().setDurationInTicks(25).setKnockBackValue(1.2F),
			vampire_crossbow = new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.7F);

	public static final ItemBolt bolt = new ItemBolt();

	public static final Item iron_ring = new Item(), gold_ring = new Item();

	public static final Item zanite_ring = new Item(), zanite_pendant = new Item();

	public static final Item iron_pendant = new Item(), gold_pendant = new Item();

	public static final Item iron_bubble = new Item(), regeneration_stone = new Item();

	public static final Item ice_ring = new Item(), ice_pendant = new Item();

	public static final Item daggerfrost_rune = new Item();

	public static final Item candy_ring = new Item(),
			bone_ring = new Item(),
			skyroot_ring = new Item();

	public static final Item icestone = new Item();

	public static final Item skyroot_sign = new ItemSkyrootSign();

	public static final Item aether_portal_frame = new ItemTemplatePlacer(() -> GenerationAether.aether_portal);

	public static final Item nether_portal_frame = new ItemTemplatePlacer(() -> GenerationAether.nether_portal);

	public static final Item end_portal_frame = new ItemTemplatePlacer(() -> GenerationAether.end_portal);

	public static final Item aechor_petal = new Item();

	public static final Item zanite_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.ZANITE),
			gravitite_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.GRAVITITE),
			valkyrie_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.VALKYRIE),
			neptune_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.NEPTUNE),
			phoenix_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.PHOENIX);

	public static final Item leather_gloves = new ItemLeatherGloves(),
			iron_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.IRON),
			gold_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.GOLD),
			chain_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.CHAIN),
			diamond_gloves = new ItemAetherGloves(ItemAetherGloves.GloveType.DIAMOND);

	public static final Item pink_baby_swet = new ItemCompanion(EntityPinkBabySwet.class);

	public static final Item shard_of_life = new ItemShardOfLife();

	public static final ItemAetherShield skyroot_shield = new ItemAetherShield(),
			holystone_shield = new ItemAetherShield(),
			zanite_shield = new ItemAetherShield(),
			arkenium_shield = new ItemAetherShield(),
			gravitite_shield = new ItemAetherShield();

	public static final ItemCompanion ethereal_stone = new ItemCompanion(EntityEtherealWisp.class),
			fleeting_stone = new ItemCompanion(EntityFleetingWisp.class),
			soaring_stone = new ItemCompanion(EntitySoaringWisp.class);

	public static final ItemCompanion frostpine_totem = new ItemCompanion(EntityFrostpineTotem.class),
			kraisith_capsule = new ItemCompanion(EntityKraisith.class, (stack, player, tooltip, advanced) ->
			{
				tooltip.add(TextFormatting.RED + "\u2022 " + "10 Health");
				tooltip.add(TextFormatting.BLUE + "\u2022 " + "0.5 Attack Damage");
				tooltip.add(TextFormatting.BLUE + "\u2022 " + "Slows Enemies");
			}),
			orb_of_arkenzus = new ItemCompanion(EntityShadeOfArkenzus.class),
			fangrin_capsule = new ItemCompanion(EntityFangrin.class, (stack, player, tooltip, advanced) ->
			{
				tooltip.add(TextFormatting.RED + "\u2022 " + "10 Health");
				tooltip.add(TextFormatting.BLUE + "\u2022 " + "1.5 Attack Damage");
			}),
			death_seal = new ItemDeathSeal(EntityNexSpirit.class);

	public static final Item barbed_iron_ring = new Item(),
			barbed_gold_ring = new Item();

	public static final Item solar_band = new Item(),
			lunar_band = new Item();

	public static final Item ring_of_growth = new Item(),
			plague_coil = new Item();

	public static final Item fleeting_ring = new Item(),
			lesser_ring_of_growth = new Item(),
			winged_ring = new Item();

	public static final Item life_coil = new Item();

	public static final Item glamoured_iron_screw = new Item(),
			wisdom_bauble = new Item();

	public static final Item blight_ward = new Item(),
			glamoured_skyroot_twig = new Item();

	public static final Item glamoured_gold_screw = new Item(),
			ambrosium_talisman = new Item(),
			sunlit_scroll = new Item();

	public static final Item moonlit_scroll = new Item(),
			glamoured_cockatrice_heart = new Item();

	public static final Item damaged_moa_feather = new Item(),
			osseous_bane = new Item(),
			rot_bane = new Item();

	public static final ItemSkyrootBed skyroot_bed = new ItemSkyrootBed();

	public static final ItemMoaEgg moa_egg = new ItemMoaEgg(false),
			rainbow_moa_egg = new ItemMoaEgg(true);

	public static final Item aether_developer_wand = new ItemAetherDeveloperWand();

	public static final Item cloud_parachute = new ItemCloudParachute();

	public static final Item amulet_of_growth = new Item(),
			lesser_amulet_of_growth = new Item();

	public static final Item frostward_scarf = new Item(),
			gruegar_scarf = new Item();

	public static final Item zanite_studded_choker = new Item(),
			arkenium_studded_choker = new Item();

	public static final Item hide_gorget = new Item(),
			raegorite_gorget = new Item(),
			thiefs_gorget = new Item(),
			moon_sect_warden_gorget = new Item();

	public static final Item glamoured_zephyr_husk = new Item(),
			glamoured_blue_swet_jelly = new Item(),
			glamoured_cockatrice_talons = new Item(),
			glamoured_coal_ember = new Item();

	public static final Item granite_ring = new Item(),
			gust_ring = new Item(),
			typhoon_ring = new Item(),
			sporing_ring = new Item(),
			ember_ring = new Item();

	public static final Item white_moa_feather = new Item(),
			sakura_moa_feather = new Item();

	public static final Item valkyrie_wings = new Item(),
			sunlit_tome = new Item(),
			moonlit_tome = new Item(),
			phoenix_rune = new Item(),
			gravitite_core = new Item(),
			primal_totem_of_survival = new Item(),
			primal_totem_of_rage = new Item(),
			glamoured_taegore_tusk = new Item(),
			divine_beacon = new Item();

	public static final Item dust_ring = new Item(), mud_ring = new Item(),
			steam_ring = new Item(),
			storm_ring = new Item();

	public static final Item butchers_knife = new Item();

	public static final Item fleeting_scarf = new Item(),
			winged_necklace = new Item(),
			gust_amulet = new Item(),
			typhoon_amulet = new Item(),
			chain_of_sporing_bones = new Item(),
			molten_amulet = new Item(),
			granite_studded_choker = new Item();

	public static final Item muggers_cloak = new Item(),
			bandit_shawl = new Item(),
			hide_pouch = new Item(),
			gruegar_pouch = new Item(),
			angel_bandage = new Item(),
			swift_rune = new Item(),
			wynd_cluster = new Item(),
			wisdom_rune = new Item();

	public static final Item ring_of_strength = new Item(),
			gruegar_ring = new Item(),
			arkenium_ring = new Item(),
			swift_ribbon = new Item(),
			wynd_cluster_ring = new Item(),
			lesser_ring_of_wisdom = new Item(),
			ring_of_wisdom = new Item();

	public static final Item glamoured_cockatrice_keratin = new Item();

	public static final Item irradiated_chunk = new ItemIrradiated(new RandomItemSelector(stack -> !(stack instanceof ItemIrradiated))),
			irradiated_sword = new ItemIrradiated(new RandomItemSelector(item -> item.getUnlocalizedName().contains("sword")
					&& !(item instanceof ItemIrradiated))),
			irradiated_armor = new ItemIrradiated(new RandomItemSelector(item -> item instanceof ItemArmor)),
			irradiated_tool = new ItemIrradiated(new RandomItemSelector(item -> item instanceof ItemTool)),
			irradiated_ring = new ItemIrradiated(new RandomItemSelector(item ->
			{
				Optional<IEquipmentProperties> equipment = AetherAPI.items().getEquipmentProperties(item);

				return equipment.isPresent() && equipment.get().getSlot() == ItemEquipmentSlot.RING;
			})),
			irradiated_neckwear = new ItemIrradiated(new RandomItemSelector(item ->
			{
				Optional<IEquipmentProperties> equipment = AetherAPI.items().getEquipmentProperties(item);

				return equipment.isPresent() && equipment.get().getSlot() == ItemEquipmentSlot.NECKWEAR;
			})),
			irradiated_charm = new ItemIrradiated(new RandomItemSelector(item ->
			{
				Optional<IEquipmentProperties> equipment = AetherAPI.items().getEquipmentProperties(item);

				return equipment.isPresent() && equipment.get().getSlot() == ItemEquipmentSlot.CHARM;
			})),
			irradiated_dust = new ItemIrradiatedVisuals();

	public static final Item sentry_vaultbox = new ItemSentryVault(),
			wrapping_paper = new ItemWrappingPaper(),
			fried_moa_egg = new ItemFood(10, false);

	public static final ItemAetherSpawnEgg aether_spawn_egg = new ItemAetherSpawnEgg();

	public static void preInit()
	{
		registerItem("skyroot_stick", skyroot_stick.setCreativeTab(CreativeTabsAether.MATERIALS));

		registerItem("ambrosium_shard", ambrosium_shard.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("ambrosium_chunk", ambrosium_chunk.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("zanite_gemstone", zanite_gemstone.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("arkenium", arkenium.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("arkenium_strip", arkenium_strip.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("icestone", icestone.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("arkenium_ore_item", arkenium_ore.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("gravitite_ore_item", gravitite_ore.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("gravitite_plate", gravitite_plate.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("cloudwool", cloudwool.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("moa_feather", moa_feather.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("cockatrice_feather", cockatrice_feather.setCreativeTab(CreativeTabsAether.MATERIALS));

		registerItem("skyroot_axe", skyroot_axe);
		registerItem("skyroot_pickaxe", skyroot_pickaxe);
		registerItem("skyroot_shovel", skyroot_shovel);
		registerItem("skyroot_sword", skyroot_sword);
		registerItem("skyroot_shield", skyroot_shield);

		registerItem("holystone_axe", holystone_axe);
		registerItem("holystone_pickaxe", holystone_pickaxe);
		registerItem("holystone_shovel", holystone_shovel);
		registerItem("holystone_sword", holystone_sword);
		registerItem("holystone_shield", holystone_shield);

		registerItem("zanite_axe", zanite_axe);
		registerItem("zanite_pickaxe", zanite_pickaxe);
		registerItem("zanite_shovel", zanite_shovel);
		registerItem("zanite_sword", zanite_sword);
		registerItem("zanite_shield", zanite_shield);

		registerItem("arkenium_axe", arkenium_axe);
		registerItem("arkenium_pickaxe", arkenium_pickaxe);
		registerItem("arkenium_shovel", arkenium_shovel);
		registerItem("arkenium_sword", arkenium_sword);
		registerItem("arkenium_shield", arkenium_shield);

		registerItem("gravitite_axe", gravitite_axe);
		registerItem("gravitite_pickaxe", gravitite_pickaxe);
		registerItem("gravitite_shovel", gravitite_shovel);
		registerItem("gravitite_sword", gravitite_sword);
		registerItem("gravitite_shield", gravitite_shield);

		registerItem("valkyrie_axe", valkyrie_axe);
		registerItem("valkyrie_pickaxe", valkyrie_pickaxe);
		registerItem("valkyrie_shovel", valkyrie_shovel);

		registerItem("arkenium_shears", arkenium_shears.setCreativeTab(CreativeTabsAether.TOOLS));

		registerItem("zanite_helmet", zanite_helmet);
		registerItem("zanite_chestplate", zanite_chestplate);
		registerItem("zanite_leggings", zanite_leggings);
		registerItem("zanite_boots", zanite_boots);
		registerItem("zanite_gloves", zanite_gloves);

		registerItem("gravitite_helmet", gravitite_helmet);
		registerItem("gravitite_chestplate", gravitite_chestplate);
		registerItem("gravitite_leggings", gravitite_leggings);
		registerItem("gravitite_boots", gravitite_boots);
		registerItem("gravitite_gloves", gravitite_gloves);

		registerItem("neptune_helmet", neptune_helmet);
		registerItem("neptune_chestplate", neptune_chestplate);
		registerItem("neptune_leggings", neptune_leggings);
		registerItem("neptune_boots", neptune_boots);
		registerItem("neptune_gloves", neptune_gloves);

		registerItem("phoenix_helmet", phoenix_helmet);
		registerItem("phoenix_chestplate", phoenix_chestplate);
		registerItem("phoenix_leggings", phoenix_leggings);
		registerItem("phoenix_boots", phoenix_boots);
		registerItem("phoenix_gloves", phoenix_gloves);

		registerItem("valkyrie_helmet", valkyrie_helmet);
		registerItem("valkyrie_chestplate", valkyrie_chestplate);
		registerItem("valkyrie_leggings", valkyrie_leggings);
		registerItem("valkyrie_boots", valkyrie_boots);
		registerItem("valkyrie_gloves", valkyrie_gloves);

		registerItem("golden_amber", golden_amber.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("taegore_hide", taegore_hide.setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("burrukai_pelt", burrukai_pelt.setCreativeTab(CreativeTabsAether.MATERIALS));

		registerItem("aechor_petal", aechor_petal.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		registerItem("blueberries", blueberries);
		registerItem("enchanted_blueberry", enchanted_blueberry);
		registerItem("orange", orange);
		registerItem("wyndberry", wyndberry);
		registerItem("enchanted_wyndberry", enchanted_wyndberry);
		registerItem("swet_jelly", swet_jelly);
		registerItem("gummy_swet", gummy_swet);

		registerItem("raw_taegore_meat", raw_taegore_meat);
		registerItem("taegore_steak", taegore_steak);

		registerItem("burrukai_rib_cut", burrukai_rib_cut);
		registerItem("burrukai_ribs", burrukai_ribs);

		registerItem("kirrid_loin", kirrid_loin);
		registerItem("kirrid_cutlet", kirrid_cutlet);

		registerItem("candy_corn", candy_corn);
		registerItem("cocoatrice", cocoatrice);
		registerItem("wrapped_chocolates", wrapped_chocolates);
		registerItem("jelly_pumpkin", jelly_pumpkin);
		registerItem("stomper_pop", stomper_pop);
		registerItem("blueberry_lollipop", blueberry_lollipop);
		registerItem("orange_lollipop", orange_lollipop);
		registerItem("icestone_poprocks", icestone_poprocks);

		registerItem("ginger_bread_man", ginger_bread_man.setCreativeTab(CreativeTabsAether.CONSUMABLES));
		registerItem("candy_cane", candy_cane.setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("skyroot_bucket", skyroot_bucket);
		registerItem("skyroot_water_bucket", skyroot_water_bucket);
		registerItem("skyroot_milk_bucket", skyroot_milk_bucket);
		registerItem("skyroot_poison_bucket", skyroot_poison_bucket);

		registerItem("valkyrie_music_disc", valkyrie_music_disc);
		registerItem("labyrinth_music_disc", labyrinth_music_disc);
		registerItem("moa_music_disc", moa_music_disc);
		registerItem("aerwhale_music_disc", aerwhale_music_disc);
		registerItem("recording_892", recording_892);

		registerItem("healing_stone_depleted", healing_stone_depleted.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CONSUMABLES));
		registerItem("healing_stone", healing_stone.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("dart_shooter", dart_shooter.setCreativeTab(CreativeTabsAether.WEAPONS));
		registerItem("dart", dart.setCreativeTab(CreativeTabsAether.WEAPONS));

		registerItem("skyroot_crossbow", skyroot_crossbow);
		registerItem("holystone_crossbow", holystone_crossbow);
		registerItem("zanite_crossbow", zanite_crossbow);
		registerItem("arkenium_crossbow", arkenium_crossbow);
		registerItem("gravitite_crossbow", gravitite_crossbow);
		registerItem("vampire_crossbow", vampire_crossbow);
		registerItem("bolt", bolt.setCreativeTab(CreativeTabsAether.WEAPONS));

		registerItem("flaming_sword", flaming_sword);
		registerItem("holy_sword", holy_sword);
		registerItem("lightning_sword", lightning_sword);

		registerItem("vampire_blade", vampire_blade);
		registerItem("candy_cane_sword", candy_cane_sword);
		registerItem("valkyrie_lance", valkyrie_lance);

		registerItem("skyroot_door_item", skyroot_door.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerItem("arkenium_door_item", arkenium_door.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		//registerItem("blightwillow_door_item", blightwillow_door_item.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		//registerItem("earthshifter_door_item", earthshifter_door_item.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		//registerItem("frostpine_door_item", frostpine_door_item.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		registerItem("iron_ring", iron_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("gold_ring", gold_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("iron_pendant", iron_pendant.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("gold_pendant", gold_pendant.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("zanite_ring", zanite_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("zanite_pendant", zanite_pendant.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("iron_bubble", iron_bubble.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("regeneration_stone", regeneration_stone.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("ice_ring", ice_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("ice_pendant", ice_pendant.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("daggerfrost_rune", daggerfrost_rune.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("candy_ring", candy_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("bone_ring", bone_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("skyroot_ring", skyroot_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));

		registerItem("skyroot_sign", skyroot_sign.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerItem("aether_portal_frame", aether_portal_frame.setCreativeTab(CreativeTabsAether.UTILITY));
		registerItem("nether_portal_frame", nether_portal_frame.setCreativeTab(CreativeTabs.TRANSPORTATION));
		registerItem("end_portal_frame", end_portal_frame.setCreativeTab(CreativeTabs.TRANSPORTATION));

		registerItem("shard_of_life", shard_of_life.setMaxStackSize(4).setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("leather_gloves", leather_gloves.setCreativeTab(CreativeTabs.COMBAT));
		registerItem("iron_gloves", iron_gloves.setCreativeTab(CreativeTabs.COMBAT));
		registerItem("gold_gloves", gold_gloves.setCreativeTab(CreativeTabs.COMBAT));
		registerItem("chain_gloves", chain_gloves.setCreativeTab(CreativeTabs.COMBAT));
		registerItem("diamond_gloves", diamond_gloves.setCreativeTab(CreativeTabs.COMBAT));

		registerItem("ethereal_stone", ethereal_stone);
		registerItem("fleeting_stone", fleeting_stone);
		registerItem("soaring_stone", soaring_stone);

		registerItem("pink_baby_swet", pink_baby_swet);
		registerItem("frostpine_totem", frostpine_totem);
		registerItem("kraisith_capsule", kraisith_capsule);
		registerItem("orb_of_arkenzus", orb_of_arkenzus);
		registerItem("fangrin_capsule", fangrin_capsule);
		registerItem("death_seal", death_seal);

		registerItem("barbed_iron_ring", barbed_iron_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("barbed_gold_ring", barbed_gold_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("solar_band", solar_band.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("lunar_band", lunar_band.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("ring_of_growth", ring_of_growth.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("plague_coil", plague_coil.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("fleeting_ring", fleeting_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("lesser_ring_of_growth", lesser_ring_of_growth.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("winged_ring", winged_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("life_coil", life_coil.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("glamoured_iron_screw", glamoured_iron_screw.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("wisdom_bauble", wisdom_bauble.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("blight_ward", blight_ward.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_skyroot_twig", glamoured_skyroot_twig.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_gold_screw", glamoured_gold_screw.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("ambrosium_talisman", ambrosium_talisman.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("sunlit_scroll", sunlit_scroll.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("moonlit_scroll", moonlit_scroll.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("glamoured_cockatrice_heart", glamoured_cockatrice_heart.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("damaged_moa_feather", damaged_moa_feather.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("osseous_bane", osseous_bane.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("rot_bane", rot_bane.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("skyroot_bed_item", skyroot_bed.setCreativeTab(CreativeTabsAether.UTILITY));

		registerItem("moa_egg_item", moa_egg);
		registerItem("rainbow_moa_egg", rainbow_moa_egg.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		registerItem("aether_developer_wand", aether_developer_wand);

		registerItem("cloud_parachute", cloud_parachute.setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("amulet_of_growth", amulet_of_growth.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("lesser_amulet_of_growth", lesser_amulet_of_growth.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("frostward_scarf", frostward_scarf.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("gruegar_scarf", gruegar_scarf.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("zanite_studded_choker", zanite_studded_choker.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("arkenium_studded_choker", arkenium_studded_choker.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("hide_gorget", hide_gorget.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("raegorite_gorget", raegorite_gorget.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("thiefs_gorget", thiefs_gorget.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("moon_sect_warden_gorget", moon_sect_warden_gorget.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));

		registerItem("glamoured_zephyr_husk", glamoured_zephyr_husk.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_blue_swet_jelly", glamoured_blue_swet_jelly.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_cockatrice_talons", glamoured_cockatrice_talons.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("glamoured_coal_ember", glamoured_coal_ember.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("granite_ring", granite_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("gust_ring", gust_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("typhoon_ring", typhoon_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("sporing_ring", sporing_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("ember_ring", ember_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("dust_ring", dust_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("mud_ring", mud_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("storm_ring", storm_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("steam_ring", steam_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));

		registerItem("white_moa_feather", white_moa_feather.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("sakura_moa_feather", sakura_moa_feather.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("sunlit_tome", sunlit_tome.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("moonlit_tome", moonlit_tome.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("primal_totem_of_survival", primal_totem_of_survival.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("primal_totem_of_rage", primal_totem_of_rage.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("divine_beacon", divine_beacon.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("phoenix_rune", phoenix_rune.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RELICS));
		registerItem("glamoured_taegore_tusk", glamoured_taegore_tusk.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("valkyrie_wings", valkyrie_wings.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.ARTIFACTS));
		registerItem("gravitite_core", gravitite_core.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.ARTIFACTS));

		registerItem("butchers_knife", butchers_knife.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("fleeting_scarf", fleeting_scarf.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("winged_necklace", winged_necklace.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("gust_amulet", gust_amulet.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("typhoon_amulet", typhoon_amulet.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("chain_of_sporing_bones", chain_of_sporing_bones.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("molten_amulet", molten_amulet.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("granite_studded_choker", granite_studded_choker.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("muggers_cloak", muggers_cloak.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));
		registerItem("bandit_shawl", bandit_shawl.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.NECKWEAR));

		registerItem("hide_pouch", hide_pouch.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("gruegar_pouch", gruegar_pouch.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("angel_bandage", angel_bandage.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("swift_rune", swift_rune.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("wynd_cluster", wynd_cluster.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));
		registerItem("wisdom_rune", wisdom_rune.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("ring_of_strength", ring_of_strength.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("gruegar_ring", gruegar_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("arkenium_ring", arkenium_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("swift_ribbon", swift_ribbon.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("wynd_cluster_ring", wynd_cluster_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("lesser_ring_of_wisdom", lesser_ring_of_wisdom.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));
		registerItem("ring_of_wisdom", ring_of_wisdom.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.RINGS));

		registerItem("irradiated_chunk", irradiated_chunk.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_sword", irradiated_sword.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_armor", irradiated_armor.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_tool", irradiated_tool.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_ring", irradiated_ring.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_neckwear", irradiated_neckwear.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_charm", irradiated_charm.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.MATERIALS));
		registerItem("irradiated_dust", irradiated_dust.setCreativeTab(CreativeTabsAether.MATERIALS));

		registerItem("glamoured_cockatrice_keratin", glamoured_cockatrice_keratin.setMaxStackSize(1).setCreativeTab(CreativeTabsAether.CHARMS));

		registerItem("sentry_vaultbox", sentry_vaultbox.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));
		registerItem("wrapping_paper", wrapping_paper.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		registerItem("fried_moa_egg", fried_moa_egg.setCreativeTab(CreativeTabsAether.CONSUMABLES));

		registerItem("aether_spawn_egg", aether_spawn_egg.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		registerItemProperties();
	}

	private static void registerItemProperties()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER,
				new ItemStack(ItemsAether.skyroot_water_bucket), new ItemStack(ItemsAether.skyroot_bucket));
	}

	private static <T extends Item> T registerItem(String name, T item)
	{
		item.setUnlocalizedName(AetherCore.MOD_ID + "." + name);
		item.setRegistryName(name);

		GameRegistry.register(item);

		if (!(item instanceof ItemMoaEgg) && !(item instanceof ItemAetherSpawnEgg))
		{
			registeredItems.add(item);
		}

		return item;
	}

	public static Collection<Item> getRegisteredItems()
	{
		return Collections.unmodifiableCollection(registeredItems);
	}
}
