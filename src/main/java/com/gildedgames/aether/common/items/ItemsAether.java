package com.gildedgames.aether.common.items;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.items.properties.IItemPropertiesCapability;
import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.*;
import com.gildedgames.aether.common.capabilities.entity.effects.rules.*;
import com.gildedgames.aether.common.registry.TemperatureHandler;
import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import com.gildedgames.aether.common.registry.minecraft.MaterialsAether;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.item.effects.ItemEffects;
import com.gildedgames.aether.common.entities.companions.*;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.player.ModifyXPCollectionEffect;
import com.gildedgames.aether.common.items.armor.*;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import com.gildedgames.aether.common.items.companions.ItemDeathSeal;
import com.gildedgames.aether.common.items.consumables.*;
import com.gildedgames.aether.common.items.misc.*;
import com.gildedgames.aether.common.items.tools.*;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.ItemVampireBlade;
import com.gildedgames.aether.common.items.weapons.crossbow.*;
import com.gildedgames.aether.common.items.weapons.swords.*;
import com.gildedgames.aether.common.util.Constraint;
import com.gildedgames.aether.common.util.RandomItemSelector;
import com.google.common.collect.Lists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ItemsAether
{
	public static Item skyroot_stick;

	public static Item ambrosium_shard, ambrosium_chunk, zanite_gemstone, arkenium, arkenium_strip;

	public static ItemSkyrootTool skyroot_axe, skyroot_pickaxe, skyroot_shovel;

	public static ItemHolystoneTool holystone_axe, holystone_pickaxe, holystone_shovel;

	public static ItemZaniteTool zanite_axe, zanite_pickaxe, zanite_shovel;

	public static ItemGravititeTool gravitite_axe, gravitite_pickaxe, gravitite_shovel;

	public static ItemValkyrieTool valkyrie_axe, valkyrie_pickaxe, valkyrie_shovel;

	public static ItemArkeniumTool arkenium_axe, arkenium_pickaxe, arkenium_shovel;

	public static ItemShears arkenium_shears;

	public static ItemAetherSword skyroot_sword, holystone_sword, zanite_sword, gravitite_sword, arkenium_sword;

	public static ItemZaniteArmor zanite_helmet, zanite_chestplate, zanite_leggings, zanite_boots;

	public static ItemGravititeArmor gravitite_helmet, gravitite_chestplate, gravitite_leggings, gravitite_boots;

	public static ItemNeptuneArmor neptune_helmet, neptune_chestplate, neptune_leggings, neptune_boots;

	public static ItemPhoenixArmor phoenix_helmet, phoenix_chestplate, phoenix_leggings, phoenix_boots;

	public static ItemValkyrieArmor valkyrie_helmet, valkyrie_chestplate, valkyrie_leggings, valkyrie_boots;

	public static Item golden_amber;

	public static ItemFood blueberries, enchanted_blueberry, orange, wyndberry, enchanted_wyndberry, swet_jelly, gummy_swet;

	public static ItemFood candy_corn, cocoatrice, wrapped_chocolates, jelly_pumpkin, stomper_pop, blueberry_lollipop, orange_lollipop, icestone_poprocks;

	public static ItemFood ginger_bread_man, candy_cane;

	public static ItemSkyrootBucket skyroot_bucket, skyroot_water_bucket;

	public static ItemSkyrootConsumableBucket skyroot_milk_bucket, skyroot_poison_bucket;

	public static ItemAetherRecord valkyrie_music_disc, labyrinth_music_disc, moa_music_disc, aerwhale_music_disc, recording_892;

	public static Item healing_stone;

	public static Item healing_stone_depleted;

	public static ItemDartShooter dart_shooter;

	public static ItemDart dart;

	public static ItemElementalSword flaming_sword, holy_sword, lightning_sword;

	public static ItemSword vampire_blade, candy_cane_sword, valkyrie_lance;

	public static ItemDoor skyroot_door, arkenium_door, blightwillow_door, earthshifter_door, frostpine_door;

	public static ItemCrossbow skyroot_crossbow;

	public static ItemCrossbow holystone_crossbow;

	public static ItemCrossbow zanite_crossbow;

	public static ItemCrossbow arkenium_crossbow;

	public static ItemCrossbow gravitite_crossbow;

	public static ItemCrossbow vampire_crossbow;

	public static ItemBolt bolt;

	public static Item iron_ring, gold_ring;

	public static Item zanite_ring, zanite_pendant;

	public static Item iron_pendant, gold_pendant;

	public static Item iron_bubble, regeneration_stone;

	public static Item ice_ring, ice_pendant;

	public static Item daggerfrost_rune;

	public static Item candy_ring, bone_ring, skyroot_ring;

	public static Item icestone;

	public static Item skyroot_sign;
	
	public static Item aether_portal_frame;

	public static Item aechor_petal;

	public static Item zanite_gloves, gravitite_gloves, valkyrie_gloves, neptune_gloves, phoenix_gloves;

	public static Item leather_gloves, iron_gloves, gold_gloves, chain_gloves, diamond_gloves;

    public static Item pink_baby_swet;

    public static Item shard_of_life;
    
	public static ItemAetherShield skyroot_shield, holystone_shield, zanite_shield, arkenium_shield, gravitite_shield;

	public static ItemCompanion ethereal_stone, fleeting_stone, soaring_stone;

	public static ItemCompanion frostpine_totem, kraisith_capsule, orb_of_arkenzus, fangrin_capsule, death_seal;

	public static Item[] zanite_armor_set;

	public static Item[] gravitite_armor_set;

	public static Item[] valkyrie_armor_set;

	public static Item[] phoenix_armor_set;

	public static Item[] neptune_armor_set;

	public static Item barbed_iron_ring, barbed_gold_ring;

	public static Item solar_band, lunar_band;

	public static Item ring_of_growth, plague_coil;

	public static Item fleeting_ring, lesser_ring_of_growth, winged_ring;

	public static Item life_coil;

	public static Item glamoured_iron_screw, wisdom_bauble, glamoured_bone_shard;

	public static Item moa_feather, blight_ward, glamoured_skyroot_twig;

	public static Item glamoured_gold_screw, ambrosium_talisman, sunlit_scroll;

	public static Item moonlit_scroll, glamoured_cockatrice_heart;

	public static Item damaged_moa_feather, osseous_bane, rot_bane;

	public static ItemSkyrootBed skyroot_bed;

	public static ItemMoaEgg moa_egg, rainbow_moa_egg;

	public static Item aether_developer_wand;

	public static Item cloud_parachute;

	public static Item amulet_of_growth, lesser_amulet_of_growth;

	public static Item frostward_scarf, gruegar_scarf;

	public static Item zanite_studded_choker, arkenium_studded_choker;

	public static Item hide_gorget, raegorite_gorget, thiefs_gorget, moon_sect_warden_gorget;

	public static Item glamoured_holystone_chip, glamoured_zephyr_husk, glamoured_ice_shard, glamoured_blue_swet_jelly, glamoured_cockatrice_talons, glamoured_coal_ember;

	public static Item granite_ring, gust_ring, typhoon_ring, sporing_ring, ember_ring;

	public static Item white_moa_feather, sakura_moa_feather;

	public static Item gravitite_core, sunlit_tome, moonlit_tome, phoenix_rune, valkyrie_wings, primal_totem_of_survival, primal_totem_of_rage, glamoured_taegore_tusk, divine_beacon;

	public static Item dust_ring, mud_ring, steam_ring, storm_ring;

	public static Item butchers_knife;

	public static Item fleeting_scarf, winged_necklace, gust_amulet, typhoon_amulet, chain_of_sporing_bones, molten_amulet, granite_studded_choker;

	public static Item muggers_cloak, bandit_shawl, hide_pouch, gruegar_pouch, soul_shard, angel_bandage, swift_rune, wynd_cluster, wisdom_rune, glamoured_aerogel_chip;

	public static Item ring_of_strength, gruegar_ring, arkenium_ring, swift_ribbon, wynd_cluster_ring, lesser_ring_of_wisdom, ring_of_wisdom;

	public static Item iron_screw, gold_screw, bone_shard, skyroot_twig, blue_skyroot_twig, dark_blue_skyroot_twig, blighted_twig;

	public static Item enchanted_skyroot_twig, cockatrice_heart, ice_shard, holystone_chip, zephyr_husk, coal_ember, aerogel_chip;

	public static Item cockatrice_talons, cockatrice_keratin, taegore_tusk, glamoured_cockatrice_keratin;

	public static Item irradiated_chunk, irradiated_sword, irradiated_armor, irradiated_tool, irradiated_ring, irradiated_neckwear, irradiated_charm, irradiated_dust;

	public static Item sentry_vaultbox, wrapping_paper;

	public static void preInit()
	{
		skyroot_stick = registerItem("skyroot_stick", new Item(), CreativeTabsAether.tabMaterials);
		ambrosium_shard = registerItem("ambrosium_shard", new Item(), CreativeTabsAether.tabMaterials);
		ambrosium_chunk = registerItem("ambrosium_chunk", new ItemAmbrosiumChunk(), CreativeTabsAether.tabMaterials);
		zanite_gemstone = registerItem("zanite_gemstone", new Item(), CreativeTabsAether.tabMaterials);
		arkenium = registerItem("arkenium", new Item(), CreativeTabsAether.tabMaterials);
        arkenium_strip = registerItem("arkenium_strip", new Item(), CreativeTabsAether.tabMaterials);
		icestone = registerItem("icestone", new Item(), CreativeTabsAether.tabMaterials);

		skyroot_axe = registerItem("skyroot_axe", new ItemSkyrootTool(EnumToolType.AXE, 6.0F, -3.2F));
		skyroot_pickaxe = registerItem("skyroot_pickaxe", new ItemSkyrootTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		skyroot_shovel = registerItem("skyroot_shovel", new ItemSkyrootTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		skyroot_sword = registerItem("skyroot_sword", new ItemSkyrootSword());
        skyroot_shield = registerItem("skyroot_shield", new ItemAetherShield(), CreativeTabsAether.tabArmor);

        holystone_axe = registerItem("holystone_axe", new ItemHolystoneTool(EnumToolType.AXE, 8.0F, -3.2F));
		holystone_pickaxe = registerItem("holystone_pickaxe", new ItemHolystoneTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		holystone_shovel = registerItem("holystone_shovel", new ItemHolystoneTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		holystone_sword = registerItem("holystone_sword", new ItemHolystoneSword());
        holystone_shield = registerItem("holystone_shield", new ItemAetherShield(), CreativeTabsAether.tabArmor);

		zanite_axe = registerItem("zanite_axe", new ItemZaniteTool(EnumToolType.AXE, 8.0F, -3.1F));
		zanite_pickaxe = registerItem("zanite_pickaxe", new ItemZaniteTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		zanite_shovel = registerItem("zanite_shovel", new ItemZaniteTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		zanite_sword = registerItem("zanite_sword", new ItemZaniteSword());
        zanite_shield = registerItem("zanite_shield", new ItemAetherShield(), CreativeTabsAether.tabArmor);

        arkenium_axe = registerItem("arkenium_axe", new ItemArkeniumTool(EnumToolType.AXE, 7.0F, -3.3F));
        arkenium_pickaxe = registerItem("arkenium_pickaxe", new ItemArkeniumTool(EnumToolType.PICKAXE, 4.0F, -3.2F));
        arkenium_shovel = registerItem("arkenium_shovel", new ItemArkeniumTool(EnumToolType.SHOVEL, 4.5F, -3.3F));
        arkenium_sword = registerItem("arkenium_sword", new ItemArkeniumSword());
        arkenium_shield = registerItem("arkenium_shield", new ItemAetherShield(), CreativeTabsAether.tabArmor);

		gravitite_axe = registerItem("gravitite_axe", new ItemGravititeTool(EnumToolType.AXE, 8.0F, -3.0F));
		gravitite_pickaxe = registerItem("gravitite_pickaxe", new ItemGravititeTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		gravitite_shovel = registerItem("gravitite_shovel", new ItemGravititeTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		gravitite_sword = registerItem("gravitite_sword", new ItemGravititeSword());
        gravitite_shield = registerItem("gravitite_shield", new ItemAetherShield(), CreativeTabsAether.tabArmor);

		valkyrie_axe = registerItem("valkyrie_axe", new ItemValkyrieTool(EnumToolType.AXE, 8.0F, -3.0F));
		valkyrie_pickaxe = registerItem("valkyrie_pickaxe", new ItemValkyrieTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		valkyrie_shovel = registerItem("valkyrie_shovel", new ItemValkyrieTool(EnumToolType.SHOVEL,  1.5F, -3.0F));

		arkenium_shears = registerItem("arkenium_shears", new ItemShears(), CreativeTabsAether.tabTools);

		zanite_helmet = registerItem("zanite_helmet", new ItemZaniteArmor(EntityEquipmentSlot.HEAD));
		zanite_chestplate = registerItem("zanite_chestplate", new ItemZaniteArmor(EntityEquipmentSlot.CHEST));
		zanite_leggings = registerItem("zanite_leggings", new ItemZaniteArmor(EntityEquipmentSlot.LEGS));
		zanite_boots = registerItem("zanite_boots", new ItemZaniteArmor(EntityEquipmentSlot.FEET));
        zanite_gloves = registerItem("zanite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.ZANITE));

		gravitite_helmet = registerItem("gravitite_helmet", new ItemGravititeArmor(EntityEquipmentSlot.HEAD));
		gravitite_chestplate = registerItem("gravitite_chestplate", new ItemGravititeArmor(EntityEquipmentSlot.CHEST));
		gravitite_leggings = registerItem("gravitite_leggings", new ItemGravititeArmor(EntityEquipmentSlot.LEGS));
		gravitite_boots = registerItem("gravitite_boots", new ItemGravititeArmor(EntityEquipmentSlot.FEET));
        gravitite_gloves = registerItem("gravitite_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.GRAVITITE));

		neptune_helmet = registerItem("neptune_helmet", new ItemNeptuneArmor(EntityEquipmentSlot.HEAD));
		neptune_chestplate = registerItem("neptune_chestplate", new ItemNeptuneArmor(EntityEquipmentSlot.CHEST));
		neptune_leggings = registerItem("neptune_leggings", new ItemNeptuneArmor(EntityEquipmentSlot.LEGS));
		neptune_boots = registerItem("neptune_boots", new ItemNeptuneArmor(EntityEquipmentSlot.FEET));
        neptune_gloves = registerItem("neptune_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.NEPTUNE));

		phoenix_helmet = registerItem("phoenix_helmet", new ItemPhoenixArmor(EntityEquipmentSlot.HEAD));
		phoenix_chestplate = registerItem("phoenix_chestplate", new ItemPhoenixArmor(EntityEquipmentSlot.CHEST));
		phoenix_leggings = registerItem("phoenix_leggings", new ItemPhoenixArmor(EntityEquipmentSlot.LEGS));
		phoenix_boots = registerItem("phoenix_boots", new ItemPhoenixArmor(EntityEquipmentSlot.FEET));
        phoenix_gloves = registerItem("phoenix_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.PHOENIX));

		valkyrie_helmet = registerItem("valkyrie_helmet", new ItemValkyrieArmor(EntityEquipmentSlot.HEAD));
		valkyrie_chestplate = registerItem("valkyrie_chestplate", new ItemValkyrieArmor(EntityEquipmentSlot.CHEST));
		valkyrie_leggings = registerItem("valkyrie_leggings", new ItemValkyrieArmor(EntityEquipmentSlot.LEGS));
		valkyrie_boots = registerItem("valkyrie_boots", new ItemValkyrieArmor(EntityEquipmentSlot.FEET));
        valkyrie_gloves = registerItem("valkyrie_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.VALKYRIE));

		golden_amber = registerItem("golden_amber", new Item(), CreativeTabsAether.tabMaterials);

		aechor_petal = registerItem("aechor_petal", new Item(), CreativeTabsAether.tabMiscellaneous);

		blueberries = registerItem("blueberries", new ItemFood(2, false), CreativeTabsAether.tabConsumables);

		enchanted_blueberry = registerItem("enchanted_blueberry", new ItemFood(6, false), CreativeTabsAether.tabConsumables);

		orange = registerItem("orange", new ItemFood(4, false), CreativeTabsAether.tabConsumables);

		wyndberry = registerItem("wyndberry", new ItemFood(4, false), CreativeTabsAether.tabConsumables);

		enchanted_wyndberry = registerItem("enchanted_wyndberry", new ItemEnchantedWyndberry(), CreativeTabsAether.tabConsumables);

		swet_jelly = registerItem("swet_jelly", new ItemSwetJelly(), CreativeTabsAether.tabConsumables);

		gummy_swet = registerItem("gummy_swet", new ItemGummySwet(), CreativeTabsAether.tabConsumables);

		candy_corn = registerItem("candy_corn", new ItemFood(8, false), CreativeTabsAether.tabConsumables);

		cocoatrice = registerItem("cocoatrice", new ItemFood(12, false), CreativeTabsAether.tabConsumables);

		wrapped_chocolates = registerItem("wrapped_chocolates", new ItemFood(12, false), CreativeTabsAether.tabConsumables);

		jelly_pumpkin = registerItem("jelly_pumpkin", new ItemFood(12, false), CreativeTabsAether.tabConsumables);

		stomper_pop = registerItem("stomper_pop", new ItemStomperPop(), CreativeTabsAether.tabConsumables);

		blueberry_lollipop = registerItem("blueberry_lollipop", new ItemFood(10, false), CreativeTabsAether.tabConsumables);

		orange_lollipop = registerItem("orange_lollipop", new ItemFood(8, false), CreativeTabsAether.tabConsumables);

		icestone_poprocks = registerItem("icestone_poprocks", new ItemFood(5, false), CreativeTabsAether.tabConsumables);

		ginger_bread_man = registerItem("ginger_bread_man", new ItemFood(2, false), CreativeTabsAether.tabConsumables);

		candy_cane = registerItem("candy_cane", new ItemFood(2, false), CreativeTabsAether.tabConsumables);

		skyroot_bucket = registerItem("skyroot_bucket", new ItemSkyrootBucket(Blocks.AIR), CreativeTabsAether.tabMiscellaneous);
		skyroot_water_bucket = registerItem("skyroot_water_bucket", new ItemSkyrootBucket(Blocks.FLOWING_WATER), CreativeTabsAether.tabMiscellaneous);
		skyroot_milk_bucket = registerItem("skyroot_milk_bucket", new ItemSkyrootConsumableBucket(), CreativeTabsAether.tabMiscellaneous);
		skyroot_poison_bucket = registerItem("skyroot_poison_bucket", new ItemSkyrootConsumableBucket(), CreativeTabsAether.tabMiscellaneous);

		valkyrie_music_disc = registerItem("valkyrie_music_disc", new ItemAetherRecord("valkyrie", SoundsAether.record_valkyrie), CreativeTabsAether.tabMiscellaneous);
		labyrinth_music_disc = registerItem("labyrinth_music_disc", new ItemAetherRecord("labyrinth", SoundsAether.record_labyrinth), CreativeTabsAether.tabMiscellaneous);
		moa_music_disc = registerItem("moa_music_disc", new ItemAetherRecord("moa", SoundsAether.record_moa), CreativeTabsAether.tabMiscellaneous);
		aerwhale_music_disc = registerItem("aerwhale_music_disc", new ItemAetherRecord("aerwhale", SoundsAether.record_aerwhale), CreativeTabsAether.tabMiscellaneous);
		recording_892 = registerItem("recording_892", new ItemAetherRecord("recording_892", SoundsAether.record_recording_892), CreativeTabsAether.tabMiscellaneous);

		healing_stone_depleted = registerItem("healing_stone_depleted", new Item().setMaxStackSize(1), CreativeTabsAether.tabConsumables);
		healing_stone = registerItem("healing_stone", new ItemHealingStone().setMaxStackSize(1), CreativeTabsAether.tabConsumables);

		dart_shooter = registerItem("dart_shooter", new ItemDartShooter(), CreativeTabsAether.tabWeapons);
		dart = registerItem("dart", new ItemDart(), CreativeTabsAether.tabWeapons);

		skyroot_crossbow = registerItem("skyroot_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F), CreativeTabsAether.tabWeapons);
		holystone_crossbow = registerItem("holystone_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.7F), CreativeTabsAether.tabWeapons);
		zanite_crossbow = registerItem("zanite_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F), CreativeTabsAether.tabWeapons);
		arkenium_crossbow = registerItem("arkenium_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.5F), CreativeTabsAether.tabWeapons);
		gravitite_crossbow = registerItem("gravitite_crossbow", new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(1.2F), CreativeTabsAether.tabWeapons);
		vampire_crossbow = registerItem("vampire_crossbow",new ItemCrossbow().setDurationInTicks(20).setKnockBackValue(0.7F), CreativeTabsAether.tabWeapons);
		bolt = registerItem("bolt", new ItemBolt(), CreativeTabsAether.tabWeapons);

		flaming_sword = registerItem("flaming_sword", new ItemElementalSword(ItemElementalSword.SwordElement.FIRE));
		holy_sword = registerItem("holy_sword", new ItemElementalSword(ItemElementalSword.SwordElement.HOLY));
		lightning_sword = registerItem("lightning_sword", new ItemElementalSword(ItemElementalSword.SwordElement.LIGHTNING));

		vampire_blade = registerItem("vampire_blade", new ItemVampireBlade(), CreativeTabsAether.tabWeapons);
		candy_cane_sword = registerItem("candy_cane_sword", new ItemCandyCaneSword(), CreativeTabsAether.tabWeapons);
		valkyrie_lance = registerItem("valkyrie_lance", new ItemAetherSword(MaterialsAether.LEGENDARY_TOOL, ItemAbilityType.PASSIVE));

		skyroot_door = registerItem("skyroot_door_item", new ItemDoor(BlocksAether.skyroot_door), CreativeTabsAether.tabBlocks);
		arkenium_door = registerItem("arkenium_door_item", new ItemDoor(BlocksAether.arkenium_door), CreativeTabsAether.tabBlocks);
		//blightwillow_door = registerItem("blightwillow_door_item", new ItemDoor(BlocksAether.blightwillow_door), CreativeTabsAether.tabBlocks);
		//earthshifter_door = registerItem("earthshifter_door_item", new ItemDoor(BlocksAether.earthshifter_door), CreativeTabsAether.tabBlocks);
		//frostpine_door = registerItem("frostpine_door_item", new ItemDoor(BlocksAether.frostpine_door), CreativeTabsAether.tabBlocks);

		iron_ring = registerItem("iron_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		gold_ring = registerItem("gold_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		iron_pendant = registerItem("iron_pendant", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		gold_pendant = registerItem("gold_pendant", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);

		zanite_ring = registerItem("zanite_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		zanite_pendant = registerItem("zanite_pendant", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);

		iron_bubble = registerItem("iron_bubble", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);
		regeneration_stone = registerItem("regeneration_stone", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);

		ice_ring = registerItem("ice_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		ice_pendant = registerItem("ice_pendant", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);

		daggerfrost_rune = registerItem("daggerfrost_rune", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);

		candy_ring = registerItem("candy_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);

		bone_ring = registerItem("bone_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);

		skyroot_ring = registerItem("skyroot_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);

		skyroot_sign = registerItem("skyroot_sign", new ItemSkyrootSign(), CreativeTabsAether.tabBlocks);
		
		aether_portal_frame = registerItem("aether_portal_frame", new ItemAetherPortalFrame(), CreativeTabsAether.tabBlocks);

        shard_of_life = registerItem("shard_of_life", new ItemShardOfLife().setMaxStackSize(4), CreativeTabsAether.tabConsumables);

		leather_gloves = registerItem("leather_gloves", new ItemLeatherGloves(), CreativeTabs.COMBAT);
		iron_gloves = registerItem("iron_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.IRON), CreativeTabs.COMBAT);
		gold_gloves = registerItem("gold_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.GOLD), CreativeTabs.COMBAT);
		chain_gloves = registerItem("chain_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.CHAIN), CreativeTabs.COMBAT);
		diamond_gloves = registerItem("diamond_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.DIAMOND), CreativeTabs.COMBAT);

		ethereal_stone = registerItem("ethereal_stone", new ItemCompanion(EntityEtheralWisp.class));
		fleeting_stone = registerItem("fleeting_stone", new ItemCompanion(EntityFleetingWisp.class));
		soaring_stone = registerItem("soaring_stone", new ItemCompanion(EntitySoaringWisp.class));

		pink_baby_swet = registerItem("pink_baby_swet", new ItemCompanion(EntityPinkBabySwet.class));
		frostpine_totem = registerItem("frostpine_totem", new ItemCompanion(EntityFrostpineTotem.class));
		kraisith_capsule = registerItem("kraisith_capsule", new ItemCompanion(EntityKraisith.class, new InformationProvider()
		{
			@Override
			public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
			{
				tooltip.add(TextFormatting.RED + "\u2022 " + "10 Health");
				tooltip.add(TextFormatting.BLUE + "\u2022 " + "0.5 Attack Damage");
				tooltip.add(TextFormatting.BLUE + "\u2022 " + "Slows Enemies");
			}
		}));
		orb_of_arkenzus = registerItem("orb_of_arkenzus", new ItemCompanion(EntityShadeOfArkenzus.class));
		fangrin_capsule = registerItem("fangrin_capsule", new ItemCompanion(EntityFangrin.class, new InformationProvider()
		{
			@Override
			public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
			{
				tooltip.add(TextFormatting.RED + "\u2022 " + "10 Health");
				tooltip.add(TextFormatting.BLUE + "\u2022 " + "1.5 Attack Damage");
			}
		}));
		death_seal = registerItem("death_seal", new ItemDeathSeal(EntityNexSpirit.class));

		barbed_iron_ring = registerItem("barbed_iron_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		barbed_gold_ring = registerItem("barbed_gold_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		solar_band = registerItem("solar_band", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		lunar_band = registerItem("lunar_band", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		ring_of_growth = registerItem("ring_of_growth", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		plague_coil = registerItem("plague_coil", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		fleeting_ring = registerItem("fleeting_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		lesser_ring_of_growth = registerItem("lesser_ring_of_growth", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		winged_ring = registerItem("winged_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		life_coil = registerItem("life_coil", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		glamoured_iron_screw = registerItem("glamoured_iron_screw", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		wisdom_bauble = registerItem("wisdom_bauble", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		glamoured_bone_shard = registerItem("glamoured_bone_shard", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		moa_feather = registerItem("moa_feather", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		blight_ward = registerItem("blight_ward", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		glamoured_skyroot_twig = registerItem("glamoured_skyroot_twig", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		glamoured_gold_screw = registerItem("glamoured_gold_screw", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		ambrosium_talisman = registerItem("ambrosium_talisman", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		sunlit_scroll = registerItem("sunlit_scroll", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);
		moonlit_scroll = registerItem("moonlit_scroll", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);
		glamoured_cockatrice_heart = registerItem("glamoured_cockatrice_heart", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		damaged_moa_feather = registerItem("damaged_moa_feather", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		osseous_bane = registerItem("osseous_bane", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		rot_bane = registerItem("rot_bane", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		skyroot_bed = registerItem("skyroot_bed_item", new ItemSkyrootBed(), CreativeTabsAether.tabBlocks);

		moa_egg = registerItem("moa_egg_item", new ItemMoaEgg(false));
		rainbow_moa_egg = registerItem("rainbow_moa_egg", new ItemMoaEgg(true), CreativeTabs.MISC);

		aether_developer_wand = registerItem("aether_developer_wand", new ItemAetherDeveloperWand());

		cloud_parachute = registerItem("cloud_parachute", new ItemCloudParachute(), CreativeTabsAether.tabConsumables);

		amulet_of_growth = registerItem("amulet_of_growth", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		lesser_amulet_of_growth = registerItem("lesser_amulet_of_growth", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		frostward_scarf = registerItem("frostward_scarf", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		gruegar_scarf = registerItem("gruegar_scarf", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		zanite_studded_choker = registerItem("zanite_studded_choker", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		arkenium_studded_choker = registerItem("arkenium_studded_choker", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		hide_gorget = registerItem("hide_gorget", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		raegorite_gorget = registerItem("raegorite_gorget", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		thiefs_gorget = registerItem("thiefs_gorget", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		moon_sect_warden_gorget = registerItem("moon_sect_warden_gorget", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);

		glamoured_holystone_chip = registerItem("glamoured_holystone_chip", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		glamoured_zephyr_husk = registerItem("glamoured_zephyr_husk", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		glamoured_ice_shard = registerItem("glamoured_ice_shard", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		glamoured_blue_swet_jelly = registerItem("glamoured_blue_swet_jelly", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		glamoured_cockatrice_talons = registerItem("glamoured_cockatrice_talons", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		glamoured_coal_ember = registerItem("glamoured_coal_ember", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);

		granite_ring = registerItem("granite_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		gust_ring = registerItem("gust_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		typhoon_ring = registerItem("typhoon_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		sporing_ring = registerItem("sporing_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		ember_ring = registerItem("ember_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		dust_ring = registerItem("dust_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		mud_ring = registerItem("mud_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		storm_ring = registerItem("storm_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		steam_ring = registerItem("steam_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);

		white_moa_feather = registerItem("white_moa_feather", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		sakura_moa_feather = registerItem("sakura_moa_feather", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);

		sunlit_tome = registerItem("sunlit_tome", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);
		moonlit_tome = registerItem("moonlit_tome", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);
		primal_totem_of_survival = registerItem("primal_totem_of_survival", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);
		primal_totem_of_rage = registerItem("primal_totem_of_rage", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);
		divine_beacon = registerItem("divine_beacon", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);
		phoenix_rune = registerItem("phoenix_rune", new Item().setMaxStackSize(1), CreativeTabsAether.tabRelics);
		glamoured_taegore_tusk = registerItem("glamoured_taegore_tusk", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);

		gravitite_core = registerItem("gravitite_core", new Item().setMaxStackSize(1), CreativeTabsAether.tabArtifacts);
		valkyrie_wings = registerItem("valkyrie_wings", new Item().setMaxStackSize(1), CreativeTabsAether.tabArtifacts);

		butchers_knife = registerItem("butchers_knife", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);

		fleeting_scarf = registerItem("fleeting_scarf", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		winged_necklace = registerItem("winged_necklace", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		gust_amulet = registerItem("gust_amulet", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		typhoon_amulet = registerItem("typhoon_amulet", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		chain_of_sporing_bones = registerItem("chain_of_sporing_bones", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		molten_amulet = registerItem("molten_amulet", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		granite_studded_choker = registerItem("granite_studded_choker", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		muggers_cloak = registerItem("muggers_cloak", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);
		bandit_shawl = registerItem("bandit_shawl", new Item().setMaxStackSize(1), CreativeTabsAether.tabNeckwear);

		hide_pouch = registerItem("hide_pouch", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		gruegar_pouch = registerItem("gruegar_pouch", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		soul_shard = registerItem("soul_shard", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		angel_bandage = registerItem("angel_bandage", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		swift_rune = registerItem("swift_rune", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		wynd_cluster = registerItem("wynd_cluster", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		wisdom_rune = registerItem("wisdom_rune", new Item().setMaxStackSize(1), CreativeTabsAether.tabCharms);
		glamoured_aerogel_chip = registerItem("glamoured_aerogel_chip", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);

		ring_of_strength = registerItem("ring_of_strength", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		gruegar_ring = registerItem("gruegar_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		arkenium_ring = registerItem("arkenium_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		swift_ribbon = registerItem("swift_ribbon", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		wynd_cluster_ring = registerItem("wynd_cluster_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		lesser_ring_of_wisdom = registerItem("lesser_ring_of_wisdom", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);
		ring_of_wisdom = registerItem("ring_of_wisdom", new Item().setMaxStackSize(1), CreativeTabsAether.tabRings);

		iron_screw = registerItem("iron_screw", new Item(), CreativeTabsAether.tabMaterials);
		gold_screw = registerItem("gold_screw", new Item(), CreativeTabsAether.tabMaterials);
		bone_shard = registerItem("bone_shard", new Item(), CreativeTabsAether.tabMaterials);
		skyroot_twig = registerItem("skyroot_twig", new Item(), CreativeTabsAether.tabMaterials);
		blue_skyroot_twig = registerItem("blue_skyroot_twig", new Item(), CreativeTabsAether.tabMaterials);
		dark_blue_skyroot_twig = registerItem("dark_blue_skyroot_twig", new Item(), CreativeTabsAether.tabMaterials);
		//blighted_twig = registerItem("blighted_twig", new Item(), CreativeTabsAether.tabMaterials);
		enchanted_skyroot_twig = registerItem("enchanted_skyroot_twig", new Item(), CreativeTabsAether.tabMaterials);
		cockatrice_heart = registerItem("cockatrice_heart", new Item(), CreativeTabsAether.tabMaterials);
		holystone_chip = registerItem("holystone_chip", new Item(), CreativeTabsAether.tabMaterials);
		zephyr_husk = registerItem("zephyr_husk", new Item(), CreativeTabsAether.tabMaterials);
		ice_shard = registerItem("ice_shard", new Item(), CreativeTabsAether.tabMaterials);
		coal_ember = registerItem("coal_ember", new Item(), CreativeTabsAether.tabMaterials);
		aerogel_chip = registerItem("aerogel_chip", new Item(), CreativeTabsAether.tabMaterials);
		cockatrice_keratin = registerItem("cockatrice_keratin", new Item(), CreativeTabsAether.tabMaterials);
		cockatrice_talons = registerItem("cockatrice_talons", new Item(), CreativeTabsAether.tabMaterials);
		taegore_tusk = registerItem("taegore_tusk", new Item(), CreativeTabsAether.tabMaterials);

		irradiated_chunk = registerItem("irradiated_chunk", new ItemIrradiated(new RandomItemSelector(new Constraint()
		{
			@Override
			public boolean accept(ItemStack stack)
			{
				return !(stack.getItem() instanceof ItemIrradiated);
			}
		})).setMaxStackSize(1), CreativeTabsAether.tabMaterials);

		irradiated_sword = registerItem("irradiated_sword", new ItemIrradiated(new RandomItemSelector(new Constraint()
		{
			@Override
			public boolean accept(ItemStack stack)
			{
				return stack.getUnlocalizedName().contains("sword") && !(stack.getItem() instanceof ItemIrradiated);
			}
		})).setMaxStackSize(1), CreativeTabsAether.tabMaterials);

		irradiated_armor = registerItem("irradiated_armor", new ItemIrradiated(new RandomItemSelector(new Constraint()
		{
			@Override
			public boolean accept(ItemStack stack)
			{
				return stack.getItem() instanceof ItemArmor;
			}
		})).setMaxStackSize(1), CreativeTabsAether.tabMaterials);

		irradiated_tool = registerItem("irradiated_tool", new ItemIrradiated(new RandomItemSelector(new Constraint()
		{
			@Override
			public boolean accept(ItemStack stack)
			{
				return stack.getItem() instanceof ItemTool;
			}
		})).setMaxStackSize(1), CreativeTabsAether.tabMaterials);

		irradiated_ring = registerItem("irradiated_ring", new ItemIrradiated(new RandomItemSelector(new Constraint()
		{
			@Override
			public boolean accept(ItemStack stack)
			{
				IItemPropertiesCapability props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

				return props != null && props.getEquipmentType() == ItemEquipmentType.RING;
			}
		})).setMaxStackSize(1), CreativeTabsAether.tabMaterials);

		irradiated_neckwear = registerItem("irradiated_neckwear", new ItemIrradiated(new RandomItemSelector(new Constraint()
		{
			@Override
			public boolean accept(ItemStack stack)
			{
				IItemPropertiesCapability props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

				return props != null && props.getEquipmentType() == ItemEquipmentType.NECKWEAR;
			}
		})).setMaxStackSize(1), CreativeTabsAether.tabMaterials);

		irradiated_charm = registerItem("irradiated_charm", new ItemIrradiated(new RandomItemSelector(new Constraint()
		{
			@Override
			public boolean accept(ItemStack stack)
			{
				IItemPropertiesCapability props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

				return props != null && props.getEquipmentType() == ItemEquipmentType.CHARM;
			}
		})).setMaxStackSize(1), CreativeTabsAether.tabMaterials);

		irradiated_dust = registerItem("irradiated_dust", new ItemIrradiatedVisuals(), CreativeTabsAether.tabMaterials);

		glamoured_cockatrice_keratin = registerItem("glamoured_cockatrice_keratin", new ItemGlamoured().setMaxStackSize(1), CreativeTabsAether.tabCharms);

		sentry_vaultbox = registerItem("sentry_vaultbox", new ItemSentryVault(), CreativeTabsAether.tabMiscellaneous);
		wrapping_paper = registerItem("wrapping_paper", new ItemWrappingPaper(), CreativeTabsAether.tabMiscellaneous);

		final TemperatureHandler temperatureHandler = new TemperatureHandler();

		AetherAPI.temperature().register(ItemBlock.getItemFromBlock(Blocks.TORCH), temperatureHandler);
		AetherAPI.temperature().register(ItemBlock.getItemFromBlock(BlocksAether.ambrosium_torch), temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_dust, temperatureHandler);

		AetherAPI.temperature().register(ItemsAether.moa_egg, temperatureHandler);

		AetherAPI.temperature().register(ItemsAether.icestone, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_chunk, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_sword, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_armor, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_tool, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_ring, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_neckwear, temperatureHandler);
		AetherAPI.temperature().register(ItemsAether.irradiated_charm, temperatureHandler);

		AetherAPI.equipment().register(ItemsAether.iron_ring, ItemRarity.NONE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.gold_ring, ItemRarity.NONE, ItemEquipmentType.RING);
		AetherAPI.equipment().register(ItemsAether.zanite_ring, ItemRarity.NONE, ItemEquipmentType.RING);

		AetherAPI.equipment().register(ItemsAether.zanite_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.gravitite_gloves, ItemRarity.NONE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.valkyrie_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.neptune_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		AetherAPI.equipment().register(ItemsAether.phoenix_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
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

		AetherAPI.equipment().register(ItemsAether.ice_pendant, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.zanite_studded_choker, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.lesser_amulet_of_growth, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.hide_gorget, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		AetherAPI.equipment().register(ItemsAether.fleeting_scarf, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
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
		AetherAPI.equipment().register(ItemsAether.glamoured_bone_shard, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_holystone_chip, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_zephyr_husk, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_ice_shard, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_blue_swet_jelly, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_cockatrice_talons, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_coal_ember, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.moa_feather, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.blight_ward, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_skyroot_twig, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_gold_screw, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.ambrosium_talisman, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.gruegar_pouch, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.soul_shard, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.wynd_cluster, ItemRarity.RARE, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_cockatrice_keratin, ItemRarity.RARE, ItemEquipmentType.CHARM);

		AetherAPI.equipment().register(ItemsAether.glamoured_taegore_tusk, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_cockatrice_heart, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.damaged_moa_feather, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.sakura_moa_feather, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.osseous_bane, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.rot_bane, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.butchers_knife, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		AetherAPI.equipment().register(ItemsAether.glamoured_aerogel_chip, ItemRarity.EPIC, ItemEquipmentType.CHARM);

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
				Pair<EntityEffectProcessor, EntityEffectInstance> effectPair = Pair.of((EntityEffectProcessor) processor, (EntityEffectInstance) instance);

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
				.add(EntityEffects.LEVITATE_ATTACKERS, new LevitateAttackersEffect.Instance(0.02D))
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
		ItemEffects.register(ItemsAether.ring_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.plague_coil, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 2.0D, 6.0D, false)).add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(-2.0D)));
		ItemEffects.register(ItemsAether.fleeting_ring, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.10D)));
		ItemEffects.register(ItemsAether.lesser_ring_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(0.5D)));
		ItemEffects.register(ItemsAether.winged_ring, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.20D)));
		ItemEffects.register(ItemsAether.life_coil, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(2.0D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, -4.0D)));
		ItemEffects.register(ItemsAether.glamoured_iron_screw, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.2D)));
		ItemEffects.register(ItemsAether.wisdom_bauble, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.10D)));
		ItemEffects.register(ItemsAether.glamoured_bone_shard, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 0.3D)));
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
		ItemEffects.register(ItemsAether.lesser_amulet_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(2.0D)));
		ItemEffects.register(ItemsAether.hide_gorget, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.amulet_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(3.0D)));
		ItemEffects.register(ItemsAether.arkenium_studded_choker, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 5.0D)));
		ItemEffects.register(ItemsAether.raegorite_gorget, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(2.0D)));
		ItemEffects.register(ItemsAether.gruegar_scarf, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-15D)));
		ItemEffects.register(ItemsAether.moon_sect_warden_gorget, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(1.5D)).add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(1.0D)));
		ItemEffects.register(ItemsAether.thiefs_gorget, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.3D)).add(EntityEffects.INVISIBILITY, new EntityEffectInstance(new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.frostward_scarf, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.4D)).add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.FROST, 1.0D)));

		ItemEffects.register(ItemsAether.glamoured_holystone_chip, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.EARTH, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_zephyr_husk, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 0.3D)));
		ItemEffects.register(ItemsAether.glamoured_ice_shard, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FROST, 0.3D)));
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
		ItemEffects.register(ItemsAether.primal_totem_of_survival, new Effects().add(EntityEffects.MODIFY_COMPANION_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(10.0D)));
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
		ItemEffects.register(ItemsAether.soul_shard, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(0.5D)));
		ItemEffects.register(ItemsAether.angel_bandage, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(0.25D)));
		ItemEffects.register(ItemsAether.swift_rune, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.01D)));
		ItemEffects.register(ItemsAether.wynd_cluster, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.02D)));
		ItemEffects.register(ItemsAether.wisdom_rune, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.20D)));
		ItemEffects.register(ItemsAether.glamoured_aerogel_chip, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.AIR, 0.1D)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.FIRE, 0.2D)));

		ItemEffects.register(ItemsAether.gruegar_ring, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-10.0D)));
		ItemEffects.register(ItemsAether.ring_of_strength, new Effects().add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(-7.5D)));
		ItemEffects.register(ItemsAether.arkenium_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(ElementalState.BIOLOGICAL, 3.0D)).add(EntityEffects.MODIFY_WEIGHT, new ModifyWeightEffect.Instance(10.0D)));
		ItemEffects.register(ItemsAether.swift_ribbon, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.05D)));
		ItemEffects.register(ItemsAether.wynd_cluster_ring, new Effects().add(EntityEffects.MODIFY_ATTACK_SPEED, new ModifyAttackSpeedEffect.Instance((float) SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue() * 0.1D)));
		ItemEffects.register(ItemsAether.lesser_ring_of_wisdom, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.20D)));
		ItemEffects.register(ItemsAether.ring_of_wisdom, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.40D)));

		ItemEffects.register(ItemsAether.glamoured_cockatrice_keratin, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(ElementalState.BLIGHT, 0.5D)));

		zanite_armor_set = new Item[] { zanite_helmet, zanite_chestplate, zanite_leggings, zanite_boots, zanite_gloves };

		gravitite_armor_set = new Item[] { gravitite_helmet, gravitite_chestplate, gravitite_leggings, gravitite_boots, gravitite_gloves };

		valkyrie_armor_set = new Item[] { valkyrie_helmet, valkyrie_chestplate, valkyrie_leggings, valkyrie_boots, valkyrie_gloves };

		phoenix_armor_set = new Item[] { phoenix_helmet, phoenix_chestplate, phoenix_leggings, phoenix_boots, phoenix_gloves };

		neptune_armor_set = new Item[] { neptune_helmet, neptune_chestplate, neptune_leggings, neptune_boots, neptune_gloves };

		registerItemProperties();
	}

	private static void registerItemProperties()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER,
				new ItemStack(ItemsAether.skyroot_water_bucket), new ItemStack(ItemsAether.skyroot_bucket));
	}

	private static <T extends Item> T registerItem(String name, T item, CreativeTabs tab)
	{
		item.setCreativeTab(tab);

		return registerItem(name, item);
	}

	private static <T extends Item> T registerItem(String name, T item)
	{
		item.setUnlocalizedName(AetherCore.MOD_ID + "." + name);
		item.setRegistryName(name);

		GameRegistry.register(item);

		return item;
	}
}
