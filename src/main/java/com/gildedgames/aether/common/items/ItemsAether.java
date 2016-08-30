package com.gildedgames.aether.common.items;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.api.registry.equipment.IEquipmentRegistry;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import com.gildedgames.aether.common.registry.minecraft.MaterialsAether;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.item.effects.ItemEffects;
import com.gildedgames.aether.common.entities.companions.*;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.entities.effects.processors.*;
import com.gildedgames.aether.common.entities.effects.processors.player.ModifyXPCollectionEffect;
import com.gildedgames.aether.common.entities.effects.rules.*;
import com.gildedgames.aether.common.items.armor.*;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import com.gildedgames.aether.common.items.companions.ItemDeathSeal;
import com.gildedgames.aether.common.items.consumables.*;
import com.gildedgames.aether.common.items.misc.*;
import com.gildedgames.aether.common.items.tools.*;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.ItemPigSlayer;
import com.gildedgames.aether.common.items.weapons.ItemVampireBlade;
import com.gildedgames.aether.common.items.weapons.crossbow.*;
import com.gildedgames.aether.common.items.weapons.swords.*;
import com.google.common.collect.Lists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
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

	public static ItemObsidianArmor obsidian_helmet, obsidian_chestplate, obsidian_leggings, obsidian_boots;

	public static ItemValkyrieArmor valkyrie_helmet, valkyrie_chestplate, valkyrie_leggings, valkyrie_boots;

	public static Item golden_amber;

	public static ItemFood blueberries, enchanted_blueberry, orange, wyndberry, enchanted_wyndberry, swet_jelly, gummy_swet;

	public static ItemFood candy_corn, cocoatrice, wrapped_chocolates, jelly_pumpkin, stomper_pop, blueberry_lollipop, orange_lollipop, icestone_poprocks;

	public static ItemFood ginger_bread_man, candy_cane;

	public static ItemSkyrootBucket skyroot_bucket, skyroot_water_bucket;

	public static ItemSkyrootConsumableBucket skyroot_milk_bucket, skyroot_poison_bucket;

	public static ItemAetherRecord valkyrie_music_disc, labyrinth_music_disc, moa_music_disc, aerwhale_music_disc, recording_892;

	public static ItemFood healing_stone;

	public static Item healing_stone_depleted;

	public static ItemDartShooter dart_shooter;

	public static ItemDart dart;

	public static ItemElementalSword flaming_sword, holy_sword, lightning_sword;

	public static ItemSword pig_slayer, vampire_blade, candy_cane_sword, valkyrie_lance;

	public static ItemDoor skyroot_door, arkenium_door;

	public static ItemSkyrootCrossbow skyroot_crossbow;

	public static ItemHolystoneCrossbow holystone_crossbow;

	public static ItemZaniteCrossbow zanite_crossbow;

	public static ItemArkeniumCrossbow arkenium_crossbow;

	public static ItemGravititeCrossbow gravitite_crossbow;

	public static ItemVampireCrossbow vampire_crossbow;

	public static ItemBolt bolt;

	public static Item iron_ring, gold_ring;

	public static Item zanite_ring, zanite_pendant;

	public static Item iron_pendant, gold_pendant;

	public static Item iron_bubble, regeneration_stone;

	public static Item ice_ring, ice_pendant;

	public static Item daggerfrost_locket;

	public static Item candy_ring, bone_ring, skyroot_ring;

	public static Item icestone, continuum_orb;

	public static Item skyroot_sign;
	
	public static Item aether_portal_frame;

	public static Item aechor_petal;

	public static Item zanite_gloves, gravitite_gloves, valkyrie_gloves, neptune_gloves, phoenix_gloves, obsidian_gloves;

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

	public static Item[] obsidian_armor_set;

	public static Item barbed_iron_ring, barbed_gold_ring;

	public static Item solar_band, lunar_band;

	public static Item ring_of_growth, plague_coil;

	public static Item fleeting_ring, lesser_ring_of_growth, winged_ring;

	public static Item life_coil;

	public static Item iron_barbed_wire, wisdom_bauble, bone_shard;

	public static Item moa_feather, blight_ward, skyroot_twig;

	public static Item gold_barbed_wire, ambrosium_talisman, carrion_petal;

	public static Item moonlit_petal, cockatrice_heart;

	public static Item damaged_moa_feather, osseous_bane, rot_bane;

	public static Item continuum_talisman, labyrinth_plans;

	public static ItemSkyrootBed skyroot_bed;

	public static ItemMoaEgg moa_egg, rainbow_moa_egg;

	public static Item aether_developer_wand;

	public static Item cloud_parachute;

	public static Item amulet_of_growth, lesser_amulet_of_growth;

	public static Item frostward_scarf, gruegar_scarf;

	public static Item zanite_studded_choker, arkenium_studded_choker;

	public static Item hide_gorget, raegorite_gorget, thiefs_gorget, moon_sect_warden_gorget;

	public static void preInit()
	{
		skyroot_stick = registerItem("skyroot_stick", new Item(), CreativeTabsAether.tabMaterials);
		ambrosium_shard = registerItem("ambrosium_shard", new Item(), CreativeTabsAether.tabMaterials);
		ambrosium_chunk = registerItem("ambrosium_chunk", new ItemAmbrosiumChunk(), CreativeTabsAether.tabMaterials);
		zanite_gemstone = registerItem("zanite_gemstone", new Item(), CreativeTabsAether.tabMaterials);
		arkenium = registerItem("arkenium", new Item(), CreativeTabsAether.tabMaterials);
        arkenium_strip = registerItem("arkenium_strip", new Item(), CreativeTabsAether.tabMaterials);
		icestone = registerItem("icestone", new Item(), CreativeTabsAether.tabMaterials);
		continuum_orb = registerItem("continuum_orb", new ItemContinuumOrb(), CreativeTabsAether.tabMaterials);

		skyroot_axe = registerItem("skyroot_axe", new ItemSkyrootTool(EnumToolType.AXE, 6.0F, -3.2F));
		skyroot_pickaxe = registerItem("skyroot_pickaxe", new ItemSkyrootTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		skyroot_shovel = registerItem("skyroot_shovel", new ItemSkyrootTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		skyroot_sword = registerItem("skyroot_sword", new ItemSkyrootSword());
        skyroot_shield = registerItem("skyroot_shield", new ItemAetherShield(), CreativeTabsAether.tabWeapons);

        holystone_axe = registerItem("holystone_axe", new ItemHolystoneTool(EnumToolType.AXE, 8.0F, -3.2F));
		holystone_pickaxe = registerItem("holystone_pickaxe", new ItemHolystoneTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		holystone_shovel = registerItem("holystone_shovel", new ItemHolystoneTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		holystone_sword = registerItem("holystone_sword", new ItemHolystoneSword());
        holystone_shield = registerItem("holystone_shield", new ItemAetherShield(), CreativeTabsAether.tabWeapons);

		zanite_axe = registerItem("zanite_axe", new ItemZaniteTool(EnumToolType.AXE, 8.0F, -3.1F));
		zanite_pickaxe = registerItem("zanite_pickaxe", new ItemZaniteTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		zanite_shovel = registerItem("zanite_shovel", new ItemZaniteTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		zanite_sword = registerItem("zanite_sword", new ItemZaniteSword());
        zanite_shield = registerItem("zanite_shield", new ItemAetherShield(), CreativeTabsAether.tabWeapons);

        arkenium_axe = registerItem("arkenium_axe", new ItemArkeniumTool(EnumToolType.AXE, 7.0F, -3.3F));
        arkenium_pickaxe = registerItem("arkenium_pickaxe", new ItemArkeniumTool(EnumToolType.PICKAXE, 4.0F, -3.2F));
        arkenium_shovel = registerItem("arkenium_shovel", new ItemArkeniumTool(EnumToolType.SHOVEL, 4.5F, -3.3F));
        arkenium_sword = registerItem("arkenium_sword", new ItemArkeniumSword());
        arkenium_shield = registerItem("arkenium_shield", new ItemAetherShield(), CreativeTabsAether.tabWeapons);

		gravitite_axe = registerItem("gravitite_axe", new ItemGravititeTool(EnumToolType.AXE, 8.0F, -3.0F));
		gravitite_pickaxe = registerItem("gravitite_pickaxe", new ItemGravititeTool(EnumToolType.PICKAXE, 1.0F, -2.8F));
		gravitite_shovel = registerItem("gravitite_shovel", new ItemGravititeTool(EnumToolType.SHOVEL, 1.5F, -3.0F));
		gravitite_sword = registerItem("gravitite_sword", new ItemGravititeSword());
        gravitite_shield = registerItem("gravitite_shield", new ItemAetherShield(), CreativeTabsAether.tabWeapons);

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

		obsidian_helmet = registerItem("obsidian_helmet", new ItemObsidianArmor(EntityEquipmentSlot.HEAD));
		obsidian_chestplate = registerItem("obsidian_chestplate", new ItemObsidianArmor(EntityEquipmentSlot.CHEST));
		obsidian_leggings = registerItem("obsidian_leggings", new ItemObsidianArmor(EntityEquipmentSlot.LEGS));
		obsidian_boots = registerItem("obsidian_boots", new ItemObsidianArmor(EntityEquipmentSlot.FEET));
		obsidian_gloves = registerItem("obsidian_gloves", new ItemAetherGloves(ItemAetherGloves.GloveType.OBSIDIAN));

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

		healing_stone_depleted = registerItem("healing_stone_depleted", new Item(), CreativeTabsAether.tabConsumables);
		healing_stone = registerItem("healing_stone", new ItemFood(0, 1.2f, false).setAlwaysEdible().setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0), 1.0f), CreativeTabsAether.tabConsumables);

		dart_shooter = registerItem("dart_shooter", new ItemDartShooter(), CreativeTabsAether.tabWeapons);
		dart = registerItem("dart", new ItemDart(), CreativeTabsAether.tabWeapons);

		skyroot_crossbow = registerItem("skyroot_crossbow", new ItemSkyrootCrossbow(), CreativeTabsAether.tabWeapons);
		holystone_crossbow = registerItem("holystone_crossbow", new ItemHolystoneCrossbow(), CreativeTabsAether.tabWeapons);
		zanite_crossbow = registerItem("zanite_crossbow", new ItemZaniteCrossbow(), CreativeTabsAether.tabWeapons);
		arkenium_crossbow = registerItem("arkenium_crossbow", new ItemArkeniumCrossbow(), CreativeTabsAether.tabWeapons);
		gravitite_crossbow = registerItem("gravitite_crossbow", new ItemGravititeCrossbow(), CreativeTabsAether.tabWeapons);
		vampire_crossbow = registerItem("vampire_crossbow",new ItemVampireCrossbow(), CreativeTabsAether.tabWeapons);
		bolt = registerItem("bolt", new ItemBolt(), CreativeTabsAether.tabWeapons);

		flaming_sword = registerItem("flaming_sword", new ItemElementalSword(ItemElementalSword.SwordElement.FIRE));
		holy_sword = registerItem("holy_sword", new ItemElementalSword(ItemElementalSword.SwordElement.HOLY));
		lightning_sword = registerItem("lightning_sword", new ItemElementalSword(ItemElementalSword.SwordElement.LIGHTNING));

		pig_slayer = registerItem("pig_slayer", new ItemPigSlayer(), CreativeTabsAether.tabWeapons);
		vampire_blade = registerItem("vampire_blade", new ItemVampireBlade(), CreativeTabsAether.tabWeapons);
		candy_cane_sword = registerItem("candy_cane_sword", new ItemCandyCaneSword(), CreativeTabsAether.tabWeapons);
		valkyrie_lance = registerItem("valkyrie_lance", new ItemAetherSword(MaterialsAether.LEGENDARY_TOOL, ItemAbilityType.PASSIVE));

		skyroot_door = registerItem("skyroot_door_item", new ItemDoor(BlocksAether.skyroot_door), CreativeTabsAether.tabBlocks);
		arkenium_door = registerItem("arkenium_door_item", new ItemDoor(BlocksAether.arkenium_door), CreativeTabsAether.tabBlocks);

		iron_ring = registerItem("iron_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		gold_ring = registerItem("gold_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		iron_pendant = registerItem("iron_pendant", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		gold_pendant = registerItem("gold_pendant", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);

		zanite_ring = registerItem("zanite_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		zanite_pendant = registerItem("zanite_pendant", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);

		iron_bubble = registerItem("iron_bubble", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		regeneration_stone = registerItem("regeneration_stone", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);

		ice_ring = registerItem("ice_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		ice_pendant = registerItem("ice_pendant", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);

		daggerfrost_locket = registerItem("daggerfrost_locket", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);

		candy_ring = registerItem("candy_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);

		bone_ring = registerItem("bone_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);

		skyroot_ring = registerItem("skyroot_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);

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

		barbed_iron_ring = registerItem("barbed_iron_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		barbed_gold_ring = registerItem("barbed_gold_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		solar_band = registerItem("solar_band", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		lunar_band = registerItem("lunar_band", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		ring_of_growth = registerItem("ring_of_growth", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		plague_coil = registerItem("plague_coil", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		fleeting_ring = registerItem("fleeting_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		lesser_ring_of_growth = registerItem("lesser_ring_of_growth", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		winged_ring = registerItem("winged_ring", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		life_coil = registerItem("life_coil", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		iron_barbed_wire = registerItem("iron_barbed_wire", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		wisdom_bauble = registerItem("wisdom_bauble", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		bone_shard = registerItem("bone_shard", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		moa_feather = registerItem("moa_feather", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		blight_ward = registerItem("blight_ward", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		skyroot_twig = registerItem("skyroot_twig", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		gold_barbed_wire = registerItem("gold_barbed_wire", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		ambrosium_talisman = registerItem("ambrosium_talisman", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		carrion_petal = registerItem("carrion_petal", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		moonlit_petal = registerItem("moonlit_petal", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		cockatrice_heart = registerItem("cockatrice_heart", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		damaged_moa_feather = registerItem("damaged_moa_feather", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		osseous_bane = registerItem("osseous_bane", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		rot_bane = registerItem("rot_bane", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		continuum_talisman = registerItem("continuum_talisman", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		labyrinth_plans = registerItem("labyrinth_plans", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		skyroot_bed = registerItem("skyroot_bed_item", new ItemSkyrootBed(), CreativeTabsAether.tabBlocks);

		moa_egg = registerItem("moa_egg_item", new ItemMoaEgg(false));
		rainbow_moa_egg = registerItem("rainbow_moa_egg", new ItemMoaEgg(true), CreativeTabs.MISC);

		aether_developer_wand = registerItem("aether_developer_wand", new ItemAetherDeveloperWand());

		cloud_parachute = registerItem("cloud_parachute", new ItemCloudParachute(), CreativeTabsAether.tabConsumables);

		amulet_of_growth = registerItem("amulet_of_growth", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		lesser_amulet_of_growth = registerItem("lesser_amulet_of_growth", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		frostward_scarf = registerItem("frostward_scarf", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		gruegar_scarf = registerItem("gruegar_scarf", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		zanite_studded_choker = registerItem("zanite_studded_choker", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		arkenium_studded_choker = registerItem("arkenium_studded_choker", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		hide_gorget = registerItem("hide_gorget", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		raegorite_gorget = registerItem("raegorite_gorget", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		thiefs_gorget = registerItem("thiefs_gorget", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);
		moon_sect_warden_gorget = registerItem("moon_sect_warden_gorget", new Item().setMaxStackSize(1), CreativeTabsAether.tabAccessories);

		IEquipmentRegistry equipmentRegistry = AetherCore.INSTANCE.getEquipmentRegistry();

		equipmentRegistry.register(ItemsAether.iron_ring, ItemRarity.BASIC, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.gold_ring, ItemRarity.BASIC, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.zanite_ring, ItemRarity.BASIC, ItemEquipmentType.RING);

		equipmentRegistry.register(ItemsAether.iron_bubble, ItemRarity.COMMON, ItemEquipmentType.RELIC);
		equipmentRegistry.register(ItemsAether.regeneration_stone, ItemRarity.RARE, ItemEquipmentType.RELIC);

		equipmentRegistry.register(ItemsAether.zanite_gloves, ItemRarity.BASIC, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.gravitite_gloves, ItemRarity.BASIC, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.valkyrie_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.neptune_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.phoenix_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.obsidian_gloves, ItemRarity.RARE, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.leather_gloves, ItemRarity.BASIC, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.iron_gloves, ItemRarity.BASIC, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.gold_gloves, ItemRarity.BASIC, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.chain_gloves, ItemRarity.BASIC, ItemEquipmentType.HANDWEAR);
		equipmentRegistry.register(ItemsAether.diamond_gloves, ItemRarity.BASIC, ItemEquipmentType.HANDWEAR);

		/** NECKWEAR **/

		equipmentRegistry.register(ItemsAether.iron_pendant, ItemRarity.BASIC, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.gold_pendant, ItemRarity.BASIC, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.zanite_pendant, ItemRarity.BASIC, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.ice_pendant, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.zanite_studded_choker, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.lesser_amulet_of_growth, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.hide_gorget, ItemRarity.COMMON, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.amulet_of_growth, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.daggerfrost_locket, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.arkenium_studded_choker, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.raegorite_gorget, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.gruegar_scarf, ItemRarity.RARE, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.moon_sect_warden_gorget, ItemRarity.EPIC, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.thiefs_gorget, ItemRarity.EPIC, ItemEquipmentType.NECKWEAR);
		equipmentRegistry.register(ItemsAether.frostward_scarf, ItemRarity.MYTHIC, ItemEquipmentType.NECKWEAR);

		/** COMPANIONS **/

		equipmentRegistry.register(ItemsAether.pink_baby_swet, ItemRarity.COMMON, ItemEquipmentType.COMPANION);
		equipmentRegistry.register(ItemsAether.kraisith_capsule, ItemRarity.COMMON, ItemEquipmentType.COMPANION);
		equipmentRegistry.register(ItemsAether.fangrin_capsule, ItemRarity.COMMON, ItemEquipmentType.COMPANION);

		equipmentRegistry.register(ItemsAether.orb_of_arkenzus, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		equipmentRegistry.register(ItemsAether.ethereal_stone, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		equipmentRegistry.register(ItemsAether.fleeting_stone, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		equipmentRegistry.register(ItemsAether.soaring_stone, ItemRarity.RARE, ItemEquipmentType.COMPANION);
		equipmentRegistry.register(ItemsAether.frostpine_totem, ItemRarity.RARE, ItemEquipmentType.COMPANION);

		equipmentRegistry.register(ItemsAether.death_seal, ItemRarity.EPIC, ItemEquipmentType.COMPANION);

		/** RINGS **/
		equipmentRegistry.register(ItemsAether.barbed_iron_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.fleeting_ring, ItemRarity.COMMON, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.lesser_ring_of_growth, ItemRarity.COMMON, ItemEquipmentType.RING);

		equipmentRegistry.register(ItemsAether.bone_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.ring_of_growth, ItemRarity.RARE, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.barbed_gold_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.winged_ring, ItemRarity.RARE, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.ice_ring, ItemRarity.RARE, ItemEquipmentType.RING);

		equipmentRegistry.register(ItemsAether.solar_band, ItemRarity.EPIC, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.lunar_band, ItemRarity.EPIC, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.skyroot_ring, ItemRarity.EPIC, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.candy_ring, ItemRarity.EPIC, ItemEquipmentType.RING);

		equipmentRegistry.register(ItemsAether.plague_coil, ItemRarity.MYTHIC, ItemEquipmentType.RING);
		equipmentRegistry.register(ItemsAether.life_coil, ItemRarity.MYTHIC, ItemEquipmentType.RING);

		/** CHARMS **/
		equipmentRegistry.register(ItemsAether.iron_barbed_wire, ItemRarity.COMMON, ItemEquipmentType.CHARM);

		equipmentRegistry.register(ItemsAether.wisdom_bauble, ItemRarity.RARE, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.bone_shard, ItemRarity.RARE, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.moa_feather, ItemRarity.RARE, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.blight_ward, ItemRarity.RARE, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.skyroot_twig, ItemRarity.RARE, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.gold_barbed_wire, ItemRarity.RARE, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.ambrosium_talisman, ItemRarity.RARE, ItemEquipmentType.CHARM);

		equipmentRegistry.register(ItemsAether.carrion_petal, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.moonlit_petal, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.cockatrice_heart, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.damaged_moa_feather, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.osseous_bane, ItemRarity.EPIC, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.rot_bane, ItemRarity.EPIC, ItemEquipmentType.CHARM);

		equipmentRegistry.register(ItemsAether.continuum_talisman, ItemRarity.MYTHIC, ItemEquipmentType.CHARM);
		equipmentRegistry.register(ItemsAether.labyrinth_plans, ItemRarity.MYTHIC, ItemEquipmentType.CHARM);

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

		ItemEffects.register(ItemsAether.iron_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(0.5F)));
		ItemEffects.register(ItemsAether.gold_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(0.0F, 1.0F, false)));
		ItemEffects.register(ItemsAether.iron_pendant, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(3.0F)));
		ItemEffects.register(ItemsAether.gold_pendant, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F, 4.0F, false)));
		ItemEffects.register(ItemsAether.zanite_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(0.5F, 1.0F, true)));
		ItemEffects.register(ItemsAether.zanite_pendant, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(3.0F, 4.0F, false)));
		ItemEffects.register(ItemsAether.iron_bubble, new Effects().add(EntityEffects.BREATHE_UNDERWATER));
		ItemEffects.register(ItemsAether.regeneration_stone, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.ice_ring, new Effects().add(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(2)));
		ItemEffects.register(ItemsAether.ice_pendant, new Effects().add(EntityEffects.FREEZE_BLOCKS, new FreezeBlocksEffect.Instance(2)));
		ItemEffects.register(ItemsAether.daggerfrost_locket, new Effects().add(EntityEffects.DAGGERFROST, new EntityEffectInstance()));
		ItemEffects.register(ItemsAether.candy_ring, new Effects().add(EntityEffects.REDUCE_HUNGER));
		ItemEffects.register(ItemsAether.bone_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F)));
		ItemEffects.register(ItemsAether.skyroot_ring, new Effects().add(EntityEffects.DOUBLE_DROPS, new DoubleDropEffect.Instance(1.5F)));
		ItemEffects.register(ItemsAether.zanite_gloves, new Effects().add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(1.5F)));
		ItemEffects.register(ItemsAether.gravitite_gloves, new Effects().add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(2.0F)));
		ItemEffects.register(ItemsAether.valkyrie_gloves, new Effects().add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(2.5F)));
		ItemEffects.register(ItemsAether.neptune_gloves, new Effects().add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(2.0F)));
		ItemEffects.register(ItemsAether.phoenix_gloves, new Effects().add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(2.0F)));
		ItemEffects.register(ItemsAether.obsidian_gloves, new Effects().add(EntityEffects.PUNCHING_DAMAGE, new ModifyDamageEffect.Instance(3.0F)));
		ItemEffects.register(ItemsAether.barbed_iron_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(1.0F)));
		ItemEffects.register(ItemsAether.barbed_gold_ring, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(1.0F, 3.0F, false)));
		ItemEffects.register(ItemsAether.solar_band, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectSunlightRule())));
		ItemEffects.register(ItemsAether.lunar_band, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InDirectMoonlightRule())));
		ItemEffects.register(ItemsAether.ring_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(1.0F)));
		ItemEffects.register(ItemsAether.plague_coil, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F, 6.0F, false)).add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(-2.0F)));
		ItemEffects.register(ItemsAether.fleeting_ring, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.10F)));
		ItemEffects.register(ItemsAether.lesser_ring_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(0.5F)));
		ItemEffects.register(ItemsAether.winged_ring, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.20F)));
		ItemEffects.register(ItemsAether.life_coil, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(2.0F)).add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(-4.0F)));
		ItemEffects.register(ItemsAether.iron_barbed_wire, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(0.2F)));
		ItemEffects.register(ItemsAether.wisdom_bauble, new Effects().add(EntityEffects.MODIFY_XP_COLLECTION, new ModifyXPCollectionEffect.Instance(0.10F)));
		ItemEffects.register(ItemsAether.bone_shard, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(0.3F)));
		ItemEffects.register(ItemsAether.moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.03F)));
		ItemEffects.register(ItemsAether.blight_ward);
		ItemEffects.register(ItemsAether.skyroot_twig);
		ItemEffects.register(ItemsAether.gold_barbed_wire, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(0.1F, 0.5F, true)));
		ItemEffects.register(ItemsAether.ambrosium_talisman, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new HoldingItemRule(new ItemStack(ItemsAether.ambrosium_shard)))));
		ItemEffects.register(ItemsAether.carrion_petal, new Effects().add(EntityEffects.PAUSE_HUNGER, new EntityEffectInstance(new InDirectSunlightRule())));
		ItemEffects.register(ItemsAether.moonlit_petal, new Effects().add(EntityEffects.PAUSE_HUNGER, new EntityEffectInstance(new InDirectMoonlightRule())));
		ItemEffects.register(ItemsAether.cockatrice_heart, new Effects().add(EntityEffects.REGENERATE_HEALTH, new RegenerateHealthEffect.Instance(4, new InBiomeRule(Biomes.BEACH))));
		ItemEffects.register(ItemsAether.damaged_moa_feather, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.10F, new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.osseous_bane, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(2.0F, new DamagingMobRule(EntitySkeleton.class, "Skeleton"))));
		ItemEffects.register(ItemsAether.rot_bane, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(1.0F, new DamagingUndeadRule())));
		ItemEffects.register(ItemsAether.continuum_talisman);
		ItemEffects.register(ItemsAether.labyrinth_plans);

		ItemEffects.register(ItemsAether.zanite_studded_choker, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(4.0F)));
		ItemEffects.register(ItemsAether.lesser_amulet_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(2.0F)));
		ItemEffects.register(ItemsAether.hide_gorget, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(1.0F)));
		ItemEffects.register(ItemsAether.amulet_of_growth, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(3.0F)));
		ItemEffects.register(ItemsAether.arkenium_studded_choker, new Effects().add(EntityEffects.MODIFY_DAMAGE, new ModifyDamageEffect.Instance(5.0F)));
		ItemEffects.register(ItemsAether.raegorite_gorget, new Effects().add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(2.0F)));
		ItemEffects.register(ItemsAether.gruegar_scarf, new Effects().add(EntityEffects.WEIGHT_TOLERANCE, new EntityEffectInstance()));
		ItemEffects.register(ItemsAether.moon_sect_warden_gorget, new Effects().add(EntityEffects.MODIFY_MAX_HEALTH, new ModifyMaxHealthEffect.Instance(1.5F)).add(EntityEffects.MODIFY_DEFENSE, new ModifyDefenseEffect.Instance(1.0F)));
		ItemEffects.register(ItemsAether.thiefs_gorget, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.30F)).add(EntityEffects.INVISIBILITY, new EntityEffectInstance(new OutOfCombatRule(160))));
		ItemEffects.register(ItemsAether.frostward_scarf, new Effects().add(EntityEffects.MODIFY_SPEED, new ModifySpeedEffect.Instance((float) SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue() * 0.40F)));

		zanite_armor_set = new Item[] { zanite_helmet, zanite_chestplate, zanite_leggings, zanite_boots, zanite_gloves };

		gravitite_armor_set = new Item[] { gravitite_helmet, gravitite_chestplate, gravitite_leggings, gravitite_boots, gravitite_gloves };

		valkyrie_armor_set = new Item[] { valkyrie_helmet, valkyrie_chestplate, valkyrie_leggings, valkyrie_boots, valkyrie_gloves };

		phoenix_armor_set = new Item[] { phoenix_helmet, phoenix_chestplate, phoenix_leggings, phoenix_boots, phoenix_gloves };

		neptune_armor_set = new Item[] { neptune_helmet, neptune_chestplate, neptune_leggings, neptune_boots, neptune_gloves };

		obsidian_armor_set = new Item[] { obsidian_helmet, obsidian_chestplate, obsidian_leggings, obsidian_boots, obsidian_gloves };

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
