package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIJukeboxSongs;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.combat.*;
import com.aetherteam.aetherii.item.combat.arkenium.ArkeniumHammerItem;
import com.aetherteam.aetherii.item.combat.arkenium.ArkeniumShortswordItem;
import com.aetherteam.aetherii.item.combat.arkenium.ArkeniumSpearItem;
import com.aetherteam.aetherii.item.combat.gravitite.GravititeCrossbowItem;
import com.aetherteam.aetherii.item.combat.gravitite.GravititeHammerItem;
import com.aetherteam.aetherii.item.combat.gravitite.GravititeShortswordItem;
import com.aetherteam.aetherii.item.combat.gravitite.GravititeSpearItem;
import com.aetherteam.aetherii.item.combat.holystone.HolystoneCrossbowItem;
import com.aetherteam.aetherii.item.combat.holystone.HolystoneHammerItem;
import com.aetherteam.aetherii.item.combat.holystone.HolystoneShortswordItem;
import com.aetherteam.aetherii.item.combat.holystone.HolystoneSpearItem;
import com.aetherteam.aetherii.item.combat.skyroot.SkyrootCrossbowItem;
import com.aetherteam.aetherii.item.combat.skyroot.SkyrootHammerItem;
import com.aetherteam.aetherii.item.combat.skyroot.SkyrootShortswordItem;
import com.aetherteam.aetherii.item.combat.skyroot.SkyrootSpearItem;
import com.aetherteam.aetherii.item.combat.zanite.ZaniteHammerItem;
import com.aetherteam.aetherii.item.combat.zanite.ZaniteShortswordItem;
import com.aetherteam.aetherii.item.combat.zanite.ZaniteSpearItem;
import com.aetherteam.aetherii.item.food.AetherIIFoods;
import com.aetherteam.aetherii.item.materials.AmbrosiumShardItem;
import com.aetherteam.aetherii.item.materials.ArcticSnowballItem;
import com.aetherteam.aetherii.item.materials.SkyrootPineconeItem;
import com.aetherteam.aetherii.item.materials.SwetGelItem;
import com.aetherteam.aetherii.item.miscellaneous.AetherPortalItem;
import com.aetherteam.aetherii.item.miscellaneous.bucket.SkyrootBucketItem;
import com.aetherteam.aetherii.item.miscellaneous.bucket.SkyrootMilkBucketItem;
import com.aetherteam.aetherii.item.miscellaneous.bucket.SkyrootMobBucketItem;
import com.aetherteam.aetherii.item.miscellaneous.bucket.SkyrootSolidBucketItem;
import com.aetherteam.aetherii.item.tools.arkenium.ArkeniumAxeItem;
import com.aetherteam.aetherii.item.tools.arkenium.ArkeniumPickaxeItem;
import com.aetherteam.aetherii.item.tools.arkenium.ArkeniumShovelItem;
import com.aetherteam.aetherii.item.tools.arkenium.ArkeniumTrowelItem;
import com.aetherteam.aetherii.item.tools.gravitite.GravititeAxeItem;
import com.aetherteam.aetherii.item.tools.gravitite.GravititePickaxeItem;
import com.aetherteam.aetherii.item.tools.gravitite.GravititeShovelItem;
import com.aetherteam.aetherii.item.tools.gravitite.GravititeTrowelItem;
import com.aetherteam.aetherii.item.tools.holystone.HolystoneAxeItem;
import com.aetherteam.aetherii.item.tools.holystone.HolystonePickaxeItem;
import com.aetherteam.aetherii.item.tools.holystone.HolystoneShovelItem;
import com.aetherteam.aetherii.item.tools.holystone.HolystoneTrowelItem;
import com.aetherteam.aetherii.item.tools.skyroot.SkyrootAxeItem;
import com.aetherteam.aetherii.item.tools.skyroot.SkyrootPickaxeItem;
import com.aetherteam.aetherii.item.tools.skyroot.SkyrootShovelItem;
import com.aetherteam.aetherii.item.tools.skyroot.SkyrootTrowelItem;
import com.aetherteam.aetherii.item.tools.zanite.ZaniteAxeItem;
import com.aetherteam.aetherii.item.tools.zanite.ZanitePickaxeItem;
import com.aetherteam.aetherii.item.tools.zanite.ZaniteShovelItem;
import com.aetherteam.aetherii.item.tools.zanite.ZaniteTrowelItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AetherII.MODID);

    public static final Style WEAPON_TOOLTIP_COLOR = Style.EMPTY.withColor(11393240);

    // Tools
    public static final DeferredItem<PickaxeItem> SKYROOT_PICKAXE = ITEMS.register("skyroot_pickaxe", SkyrootPickaxeItem::new);
    public static final DeferredItem<AxeItem> SKYROOT_AXE = ITEMS.register("skyroot_axe", SkyrootAxeItem::new);
    public static final DeferredItem<ShovelItem> SKYROOT_SHOVEL = ITEMS.register("skyroot_shovel", SkyrootShovelItem::new);
    public static final DeferredItem<HoeItem> SKYROOT_TROWEL = ITEMS.register("skyroot_trowel", SkyrootTrowelItem::new);

    public static final DeferredItem<PickaxeItem> HOLYSTONE_PICKAXE = ITEMS.register("holystone_pickaxe", HolystonePickaxeItem::new);
    public static final DeferredItem<AxeItem> HOLYSTONE_AXE = ITEMS.register("holystone_axe", HolystoneAxeItem::new);
    public static final DeferredItem<ShovelItem> HOLYSTONE_SHOVEL = ITEMS.register("holystone_shovel", HolystoneShovelItem::new);
    public static final DeferredItem<HoeItem> HOLYSTONE_TROWEL = ITEMS.register("holystone_trowel", HolystoneTrowelItem::new);

    public static final DeferredItem<PickaxeItem> ZANITE_PICKAXE = ITEMS.register("zanite_pickaxe", ZanitePickaxeItem::new);
    public static final DeferredItem<AxeItem> ZANITE_AXE = ITEMS.register("zanite_axe", ZaniteAxeItem::new);
    public static final DeferredItem<ShovelItem> ZANITE_SHOVEL = ITEMS.register("zanite_shovel", ZaniteShovelItem::new);
    public static final DeferredItem<HoeItem> ZANITE_TROWEL = ITEMS.register("zanite_trowel", ZaniteTrowelItem::new);

    public static final DeferredItem<PickaxeItem> ARKENIUM_PICKAXE = ITEMS.register("arkenium_pickaxe", ArkeniumPickaxeItem::new);
    public static final DeferredItem<AxeItem> ARKENIUM_AXE = ITEMS.register("arkenium_axe", ArkeniumAxeItem::new);
    public static final DeferredItem<ShovelItem> ARKENIUM_SHOVEL = ITEMS.register("arkenium_shovel", ArkeniumShovelItem::new);
    public static final DeferredItem<HoeItem> ARKENIUM_TROWEL = ITEMS.register("arkenium_trowel", ArkeniumTrowelItem::new);

    public static final DeferredItem<PickaxeItem> GRAVITITE_PICKAXE = ITEMS.register("gravitite_pickaxe", GravititePickaxeItem::new);
    public static final DeferredItem<AxeItem> GRAVITITE_AXE = ITEMS.register("gravitite_axe", GravititeAxeItem::new);
    public static final DeferredItem<ShovelItem> GRAVITITE_SHOVEL = ITEMS.register("gravitite_shovel", GravititeShovelItem::new);
    public static final DeferredItem<HoeItem> GRAVITITE_TROWEL = ITEMS.register("gravitite_trowel", GravititeTrowelItem::new);

    public static final DeferredItem<Item> ARKENIUM_SHEARS = ITEMS.register("arkenium_shears", () -> new ShearsItem(new Item.Properties().durability(238).component(DataComponents.TOOL, ShearsItem.createToolProperties())));

    // Combat
    public static final DeferredItem<SwordItem> SKYROOT_SHORTSWORD = ITEMS.register("skyroot_shortsword", SkyrootShortswordItem::new);
    public static final DeferredItem<HammerItem> SKYROOT_HAMMER = ITEMS.register("skyroot_hammer", SkyrootHammerItem::new);
    public static final DeferredItem<SpearItem> SKYROOT_SPEAR = ITEMS.register("skyroot_spear", SkyrootSpearItem::new);
    public static final DeferredItem<CrossbowItem> SKYROOT_CROSSBOW = ITEMS.register("skyroot_crossbow", SkyrootCrossbowItem::new);
    public static final DeferredItem<ShieldItem> SKYROOT_SHIELD = ITEMS.register("skyroot_shield", () -> new AetherIIShieldItem(AetherIIItemTiers.SKYROOT, new Item.Properties().attributes(AetherIIShieldItem.createAttributes(100))));

    public static final DeferredItem<SwordItem> HOLYSTONE_SHORTSWORD = ITEMS.register("holystone_shortsword", HolystoneShortswordItem::new);
    public static final DeferredItem<HammerItem> HOLYSTONE_HAMMER = ITEMS.register("holystone_hammer", HolystoneHammerItem::new);
    public static final DeferredItem<SpearItem> HOLYSTONE_SPEAR = ITEMS.register("holystone_spear", HolystoneSpearItem::new);
    public static final DeferredItem<CrossbowItem> HOLYSTONE_CROSSBOW = ITEMS.register("holystone_crossbow", HolystoneCrossbowItem::new);
    public static final DeferredItem<ShieldItem> HOLYSTONE_SHIELD = ITEMS.register("holystone_shield", () -> new AetherIIShieldItem(AetherIIItemTiers.HOLYSTONE, new Item.Properties().attributes(AetherIIShieldItem.createAttributes(80))));

    public static final DeferredItem<SwordItem> ZANITE_SHORTSWORD = ITEMS.register("zanite_shortsword", ZaniteShortswordItem::new);
    public static final DeferredItem<HammerItem> ZANITE_HAMMER = ITEMS.register("zanite_hammer", ZaniteHammerItem::new);
    public static final DeferredItem<SpearItem> ZANITE_SPEAR = ITEMS.register("zanite_spear", ZaniteSpearItem::new);
    public static final DeferredItem<CrossbowItem> ZANITE_CROSSBOW = ITEMS.register("zanite_crossbow", () -> new AetherIICrossbowItem(AetherIIItemTiers.ZANITE, new Item.Properties()));
    public static final DeferredItem<ShieldItem> ZANITE_SHIELD = ITEMS.register("zanite_shield", () -> new AetherIIShieldItem(AetherIIItemTiers.ZANITE, new Item.Properties().attributes(AetherIIShieldItem.createAttributes(60))));

    public static final DeferredItem<SwordItem> ARKENIUM_SHORTSWORD = ITEMS.register("arkenium_shortsword", ArkeniumShortswordItem::new);
    public static final DeferredItem<HammerItem> ARKENIUM_HAMMER = ITEMS.register("arkenium_hammer", ArkeniumHammerItem::new);
    public static final DeferredItem<SpearItem> ARKENIUM_SPEAR = ITEMS.register("arkenium_spear", ArkeniumSpearItem::new);
    public static final DeferredItem<CrossbowItem> ARKENIUM_CROSSBOW = ITEMS.register("arkenium_crossbow", () -> new AetherIICrossbowItem(AetherIIItemTiers.ARKENIUM, new Item.Properties()));
    public static final DeferredItem<ShieldItem> ARKENIUM_SHIELD = ITEMS.register("arkenium_shield", () -> new AetherIIShieldItem(AetherIIItemTiers.ARKENIUM, new Item.Properties().attributes(AetherIIShieldItem.createAttributes(60))));

    public static final DeferredItem<SwordItem> GRAVITITE_SHORTSWORD = ITEMS.register("gravitite_shortsword", GravititeShortswordItem::new);
    public static final DeferredItem<HammerItem> GRAVITITE_HAMMER = ITEMS.register("gravitite_hammer", GravititeHammerItem::new);
    public static final DeferredItem<SpearItem> GRAVITITE_SPEAR = ITEMS.register("gravitite_spear", GravititeSpearItem::new);
    public static final DeferredItem<CrossbowItem> GRAVITITE_CROSSBOW = ITEMS.register("gravitite_crossbow", GravititeCrossbowItem::new);
    public static final DeferredItem<ShieldItem> GRAVITITE_SHIELD = ITEMS.register("gravitite_shield", () -> new AetherIIShieldItem(AetherIIItemTiers.GRAVITITE, new Item.Properties().attributes(AetherIIShieldItem.createAttributes(40))));

    public static final DeferredItem<Item> SCATTERGLASS_BOLT = ITEMS.register("scatterglass_bolt", () -> new ScatterglassBoltItem(new Item.Properties()));

    // Armor
    public static final DeferredItem<Item> TAEGORE_HIDE_HELMET = ITEMS.register("taegore_hide_helmet", () -> new ArmorItem(AetherIIArmorMaterials.TAEGORE_HIDE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))));
    public static final DeferredItem<Item> TAEGORE_HIDE_CHESTPLATE = ITEMS.register("taegore_hide_chestplate", () -> new ArmorItem(AetherIIArmorMaterials.TAEGORE_HIDE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(5))));
    public static final DeferredItem<Item> TAEGORE_HIDE_LEGGINGS = ITEMS.register("taegore_hide_leggings", () -> new ArmorItem(AetherIIArmorMaterials.TAEGORE_HIDE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(5))));
    public static final DeferredItem<Item> TAEGORE_HIDE_BOOTS = ITEMS.register("taegore_hide_boots", () -> new ArmorItem(AetherIIArmorMaterials.TAEGORE_HIDE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(5))));
    public static final DeferredItem<Item> TAEGORE_HIDE_GLOVES = ITEMS.register("taegore_hide_gloves", () -> new GlovesItem(AetherIIArmorMaterials.TAEGORE_HIDE, "taegore_hide_gloves", new Item.Properties().durability(GlovesItem.getDurability(5))));

    public static final DeferredItem<Item> BURRUKAI_PELT_HELMET = ITEMS.register("burrukai_pelt_helmet", () -> new ArmorItem(AetherIIArmorMaterials.BURRUKAI_PELT, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))));
    public static final DeferredItem<Item> BURRUKAI_PELT_CHESTPLATE = ITEMS.register("burrukai_pelt_chestplate", () -> new ArmorItem(AetherIIArmorMaterials.BURRUKAI_PELT, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(5))));
    public static final DeferredItem<Item> BURRUKAI_PELT_LEGGINGS = ITEMS.register("burrukai_pelt_leggings", () -> new ArmorItem(AetherIIArmorMaterials.BURRUKAI_PELT, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(5))));
    public static final DeferredItem<Item> BURRUKAI_PELT_BOOTS = ITEMS.register("burrukai_pelt_boots", () -> new ArmorItem(AetherIIArmorMaterials.BURRUKAI_PELT, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(5))));
    public static final DeferredItem<Item> BURRUKAI_PELT_GLOVES = ITEMS.register("burrukai_pelt_gloves", () -> new GlovesItem(AetherIIArmorMaterials.BURRUKAI_PELT, "burrukai_pelt_gloves", new Item.Properties().durability(GlovesItem.getDurability(5))));

    public static final DeferredItem<Item> ZANITE_HELMET = ITEMS.register("zanite_helmet", () -> new ArmorItem(AetherIIArmorMaterials.ZANITE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
    public static final DeferredItem<Item> ZANITE_CHESTPLATE = ITEMS.register("zanite_chestplate", () -> new ArmorItem(AetherIIArmorMaterials.ZANITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));
    public static final DeferredItem<Item> ZANITE_LEGGINGS = ITEMS.register("zanite_leggings", () -> new ArmorItem(AetherIIArmorMaterials.ZANITE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));
    public static final DeferredItem<Item> ZANITE_BOOTS = ITEMS.register("zanite_boots", () -> new ArmorItem(AetherIIArmorMaterials.ZANITE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));
    public static final DeferredItem<Item> ZANITE_GLOVES = ITEMS.register("zanite_gloves", () -> new GlovesItem(AetherIIArmorMaterials.ZANITE, "zanite_gloves", new Item.Properties().durability(GlovesItem.getDurability(15))));

    public static final DeferredItem<Item> ARKENIUM_HELMET = ITEMS.register("arkenium_helmet", () -> new ArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
    public static final DeferredItem<Item> ARKENIUM_CHESTPLATE = ITEMS.register("arkenium_chestplate", () -> new ArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));
    public static final DeferredItem<Item> ARKENIUM_LEGGINGS = ITEMS.register("arkenium_leggings", () -> new ArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));
    public static final DeferredItem<Item> ARKENIUM_BOOTS = ITEMS.register("arkenium_boots", () -> new ArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));
    public static final DeferredItem<Item> ARKENIUM_GLOVES = ITEMS.register("arkenium_gloves", () -> new GlovesItem(AetherIIArmorMaterials.ARKENIUM, "arkenium_gloves", new Item.Properties().durability(GlovesItem.getDurability(15))));

    public static final DeferredItem<Item> GRAVITITE_HELMET = ITEMS.register("gravitite_helmet", () -> new ArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(33))));
    public static final DeferredItem<Item> GRAVITITE_CHESTPLATE = ITEMS.register("gravitite_chestplate", () -> new ArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(33))));
    public static final DeferredItem<Item> GRAVITITE_LEGGINGS = ITEMS.register("gravitite_leggings", () -> new ArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(33))));
    public static final DeferredItem<Item> GRAVITITE_BOOTS = ITEMS.register("gravitite_boots", () -> new ArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(33))));
    public static final DeferredItem<Item> GRAVITITE_GLOVES = ITEMS.register("gravitite_gloves", () -> new GlovesItem(AetherIIArmorMaterials.GRAVITITE, "gravitite_gloves", new Item.Properties().durability(GlovesItem.getDurability(33))));

    // Materials
    public static final DeferredItem<Item> SKYROOT_STICK = ITEMS.register("skyroot_stick", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SKYROOT_PINECONE = ITEMS.register("skyroot_pinecone", () -> new SkyrootPineconeItem(new Item.Properties()));
    public static final DeferredItem<Item> VALKYRIE_WINGS = ITEMS.register("valkyrie_wings", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SCATTERGLASS_SHARD = ITEMS.register("scatterglass_shard", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AMBROSIUM_SHARD = ITEMS.register("ambrosium_shard", () -> new AmbrosiumShardItem(new Item.Properties()));
    public static final DeferredItem<Item> ZANITE_GEMSTONE = ITEMS.register("zanite_gemstone", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INERT_ARKENIUM = ITEMS.register("inert_arkenium", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ARKENIUM_PLATES = ITEMS.register("arkenium_plates", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INERT_GRAVITITE = ITEMS.register("inert_gravitite", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GRAVITITE_PLATE = ITEMS.register("gravitite_plate", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORROBONITE_CRYSTAL = ITEMS.register("corrobonite_crystal", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLDEN_AMBER = ITEMS.register("golden_amber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CLOUDTWINE = ITEMS.register("cloudtwine", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TAEGORE_HIDE = ITEMS.register("taegore_hide", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BURRUKAI_PELT = ITEMS.register("burrukai_pelt", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AECHOR_PETAL = ITEMS.register("aechor_petal", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BRETTL_CANE = ITEMS.register("brettl_cane", () -> new ItemNameBlockItem(AetherIIBlocks.BRETTL_PLANT_TIP.get(), new Item.Properties()));
    public static final DeferredItem<Item> BRETTL_GRASS = ITEMS.register("brettl_grass", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BRETTL_ROPE = ITEMS.register("brettl_rope", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BRETTL_FLOWER = ITEMS.register("brettl_flower", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ARCTIC_SNOWBALL = ITEMS.register("arctic_snowball", () -> new ArcticSnowballItem(new Item.Properties()));
    public static final DeferredItem<Item> GREEN_SWET_GEL = ITEMS.register("green_swet_gel", () -> new SwetGelItem(new Item.Properties()));
    public static final DeferredItem<Item> BLUE_SWET_GEL = ITEMS.register("blue_swet_gel", () -> new SwetGelItem(new Item.Properties()));
    public static final DeferredItem<Item> PURPLE_SWET_GEL = ITEMS.register("purple_swet_gel", () -> new SwetGelItem(new Item.Properties()));
    public static final DeferredItem<Item> GOLDEN_SWET_GEL = ITEMS.register("golden_swet_gel", () -> new SwetGelItem(new Item.Properties()));
    public static final DeferredItem<Item> WHITE_SWET_GEL = ITEMS.register("white_swet_gel", () -> new SwetGelItem(new Item.Properties()));

    // Food
    public static final DeferredItem<Item> BLUEBERRY = ITEMS.register("blueberry", () -> new Item(new Item.Properties().food(AetherIIFoods.BLUEBERRY)));
    public static final DeferredItem<Item> ENCHANTED_BLUEBERRY = ITEMS.register("enchanted_blueberry", () -> new Item(new Item.Properties().food(AetherIIFoods.ENCHANTED_BLUEBERRY)));
    public static final DeferredItem<Item> ORANGE = ITEMS.register("orange", () -> new Item(new Item.Properties().food(AetherIIFoods.ORANGE)));
    public static final DeferredItem<Item> WYNDBERRY = ITEMS.register("wyndberry", () -> new Item(new Item.Properties().food(AetherIIFoods.WYNDBERRY)));
    public static final DeferredItem<Item> ENCHANTED_WYNDBERRY = ITEMS.register("enchanted_wyndberry", () -> new Item(new Item.Properties().food(AetherIIFoods.ENCHANTED_WYNDBERRY)));
    public static final DeferredItem<Item> GREEN_SWET_JELLY = ITEMS.register("green_swet_jelly", () -> new Item(new Item.Properties().food(AetherIIFoods.SWET_JELLY)));
    public static final DeferredItem<Item> BLUE_SWET_JELLY = ITEMS.register("blue_swet_jelly", () -> new Item(new Item.Properties().food(AetherIIFoods.SWET_JELLY)));
    public static final DeferredItem<Item> PURPLE_SWET_JELLY = ITEMS.register("purple_swet_jelly", () -> new Item(new Item.Properties().food(AetherIIFoods.SWET_JELLY)));
    public static final DeferredItem<Item> GOLDEN_SWET_JELLY = ITEMS.register("golden_swet_jelly", () -> new Item(new Item.Properties().food(AetherIIFoods.SWET_JELLY)));
    public static final DeferredItem<Item> WHITE_SWET_JELLY = ITEMS.register("white_swet_jelly", () -> new Item(new Item.Properties().food(AetherIIFoods.SWET_JELLY)));
    public static final DeferredItem<Item> BURRUKAI_RIB_CUT = ITEMS.register("burrukai_rib_cut", () -> new Item(new Item.Properties().food(AetherIIFoods.BURRUKAI_RIB_CUT)));
    public static final DeferredItem<Item> BURRUKAI_RIBS = ITEMS.register("burrukai_ribs", () -> new Item(new Item.Properties().food(AetherIIFoods.BURRUKAI_RIBS)));
    public static final DeferredItem<Item> KIRRID_LOIN = ITEMS.register("kirrid_loin", () -> new Item(new Item.Properties().food(AetherIIFoods.KIRRID_LOIN)));
    public static final DeferredItem<Item> KIRRID_CUTLET = ITEMS.register("kirrid_cutlet", () -> new Item(new Item.Properties().food(AetherIIFoods.KIRRID_CUTLET)));
    public static final DeferredItem<Item> RAW_TAEGORE_MEAT = ITEMS.register("raw_taegore_meat", () -> new Item(new Item.Properties().food(AetherIIFoods.RAW_TAEGORE_MEAT)));
    public static final DeferredItem<Item> TAEGORE_STEAK = ITEMS.register("taegore_steak", () -> new Item(new Item.Properties().food(AetherIIFoods.TAEGORE_STEAK)));
    public static final DeferredItem<Item> SKYROOT_LIZARD_ON_A_STICK = ITEMS.register("skyroot_lizard_on_a_stick", () -> new Item(new Item.Properties().food(AetherIIFoods.SKYROOT_LIZARD_ON_A_STICK)));
    public static final DeferredItem<Item> ROASTED_SKYROOT_LIZARD_ON_A_STICK = ITEMS.register("roasted_skyroot_lizard_on_a_stick", () -> new Item(new Item.Properties().food(AetherIIFoods.ROASTED_SKYROOT_LIZARD_ON_A_STICK)));

    // Skyroot Buckets
    public static final DeferredItem<Item> SKYROOT_BUCKET = ITEMS.register("skyroot_bucket", () -> new SkyrootBucketItem(Fluids.EMPTY, new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> SKYROOT_WATER_BUCKET = ITEMS.register("skyroot_water_bucket", () -> new SkyrootBucketItem(Fluids.WATER, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_MILK_BUCKET = ITEMS.register("skyroot_milk_bucket", () -> new SkyrootMilkBucketItem(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_POWDER_SNOW_BUCKET = ITEMS.register("skyroot_powder_snow_bucket", () -> new SkyrootSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_COD_BUCKET = ITEMS.register("skyroot_cod_bucket", () -> new SkyrootMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_SALMON_BUCKET = ITEMS.register("skyroot_salmon_bucket", () -> new SkyrootMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_PUFFERFISH_BUCKET = ITEMS.register("skyroot_pufferfish_bucket", () -> new SkyrootMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_TROPICAL_FISH_BUCKET = ITEMS.register("skyroot_tropical_fish_bucket", () -> new SkyrootMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_AXOLOTL_BUCKET = ITEMS.register("skyroot_axolotl_bucket", () -> new SkyrootMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_TADPOLE_BUCKET = ITEMS.register("skyroot_tadpole_bucket", () -> new SkyrootMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));

    // Music Discs
    public static final DeferredItem<Item> MUSIC_DISC_AETHER_TUNE = ITEMS.register("music_disc_aether_tune", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.AETHER_TUNE)));
    public static final DeferredItem<Item> MUSIC_DISC_ASCENDING_DAWN = ITEMS.register("music_disc_ascending_dawn", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.ASCENDING_DAWN)));
    public static final DeferredItem<Item> MUSIC_DISC_AERWHALE = ITEMS.register("music_disc_aerwhale", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.AERWHALE)));
    public static final DeferredItem<Item> MUSIC_DISC_APPROACHES = ITEMS.register("music_disc_approaches", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.APPROACHES)));
    public static final DeferredItem<Item> MUSIC_DISC_DEMISE = ITEMS.register("music_disc_demise", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.DEMISE)));
    public static final DeferredItem<Item> RECORDING_892 = ITEMS.register("recording_892", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.RECORDING_892)));

    // Spawn Eggs
    public static final DeferredItem<SpawnEggItem> FLYING_COW_SPAWN_EGG = ITEMS.register("flying_cow_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.FLYING_COW, 0xD8D8D8, 0xFFD939, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> SHEEPUFF_SPAWN_EGG = ITEMS.register("sheepuff_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.SHEEPUFF, 0xE2FCFF, 0xCB9090, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> PHYG_SPAWN_EGG = ITEMS.register("phyg_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.PHYG, 0xFFC1D0, 0xFFD939, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> AERBUNNY_SPAWN_EGG = ITEMS.register("aerbunny_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.AERBUNNY, 0xE2FCFF, 0xFFDFF9, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> HIGHFIELDS_TAEGORE_SPAWN_EGG = ITEMS.register("highfields_taegore_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.HIGHFIELDS_TAEGORE, 0xEDD681, 0xBFDCFB, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> MAGNETIC_TAEGORE_SPAWN_EGG = ITEMS.register("magnetic_taegore_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.MAGNETIC_TAEGORE, 0xC9AA83, 0x8D91B4, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> ARCTIC_TAEGORE_SPAWN_EGG = ITEMS.register("arctic_taegore_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.ARCTIC_TAEGORE, 0xCCCCCC, 0x967FA2, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> HIGHFIELDS_BURRUKAI_SPAWN_EGG = ITEMS.register("highfields_burrukai_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI, 0x2A2B35, 0x4E7EA8, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> MAGNETIC_BURRUKAI_SPAWN_EGG = ITEMS.register("magnetic_burrukai_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.MAGNETIC_BURRUKAI, 0x373D53, 0x5C594E, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> ARCTIC_BURRUKAI_SPAWN_EGG = ITEMS.register("arctic_burrukai_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.ARCTIC_BURRUKAI, 0x8B8A8C, 0x725D8A, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> HIGHFIELDS_KIRRID_SPAWN_EGG = ITEMS.register("highfields_kirrid_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.HIGHFIELDS_KIRRID, 0x495056, 0xD2CEBA, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> MAGNETIC_KIRRID_SPAWN_EGG = ITEMS.register("magnetic_kirrid_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.MAGNETIC_KIRRID, 0x5F5A69, 0x9FA2D6, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> ARCTIC_KIRRID_SPAWN_EGG = ITEMS.register("arctic_kirrid_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.ARCTIC_KIRRID, 0x6B6255, 0xDDDDDD, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> MOA_SPAWN_EGG = ITEMS.register("moa_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.MOA, 0x87BFEF, 0x7A7A7A, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> SKYROOT_LIZARD_SPAWN_EGG = ITEMS.register("skyroot_lizard_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.SKYROOT_LIZARD, 0x626343, 0xADCC83, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> AECHOR_PLANT_SPAWN_EGG = ITEMS.register("aechor_plant_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.AECHOR_PLANT, 0x076178, 0x4BC69E, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> ZEPHYR_SPAWN_EGG = ITEMS.register("zephyr_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.ZEPHYR, 0xDFDFDF, 0x99CFE8, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> TEMPEST_SPAWN_EGG = ITEMS.register("tempest_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.TEMPEST, 0x3C464C, 0xC3E6F0, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> COCKATRICE_SPAWN_EGG = ITEMS.register("cockatrice_spawn_egg", () -> new DeferredSpawnEggItem(AetherIIEntityTypes.COCKATRICE, 0x837B9E, 0x9CFCAC, new Item.Properties()));

    // Misc
    public static final DeferredItem<Item> MOA_FEED = ITEMS.register("moa_feed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLUEBERRY_MOA_FEED = ITEMS.register("blueberry_moa_feed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ENCHANTED_MOA_FEED = ITEMS.register("enchanted_moa_feed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHARGE_CORE = ITEMS.register("charge_core", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AETHER_PORTAL_FRAME = ITEMS.register("aether_portal_frame", () -> new AetherPortalItem(new Item.Properties().stacksTo(1)));
}