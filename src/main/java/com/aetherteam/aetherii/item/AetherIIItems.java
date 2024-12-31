package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.data.resources.registries.AetherIIJukeboxSongs;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import com.aetherteam.aetherii.item.consumables.HealingStoneItem;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.item.equipment.armor.AetherIIArmorMaterials;
import com.aetherteam.aetherii.item.equipment.armor.GlovesItem;
import com.aetherteam.aetherii.item.equipment.armor.abilities.*;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import com.aetherteam.aetherii.item.equipment.tools.arkenium.ArkeniumAxeItem;
import com.aetherteam.aetherii.item.equipment.tools.arkenium.ArkeniumPickaxeItem;
import com.aetherteam.aetherii.item.equipment.tools.arkenium.ArkeniumShovelItem;
import com.aetherteam.aetherii.item.equipment.tools.arkenium.ArkeniumTrowelItem;
import com.aetherteam.aetherii.item.equipment.tools.gravitite.GravititeAxeItem;
import com.aetherteam.aetherii.item.equipment.tools.gravitite.GravititePickaxeItem;
import com.aetherteam.aetherii.item.equipment.tools.gravitite.GravititeShovelItem;
import com.aetherteam.aetherii.item.equipment.tools.gravitite.GravititeTrowelItem;
import com.aetherteam.aetherii.item.equipment.tools.holystone.HolystoneAxeItem;
import com.aetherteam.aetherii.item.equipment.tools.holystone.HolystonePickaxeItem;
import com.aetherteam.aetherii.item.equipment.tools.holystone.HolystoneShovelItem;
import com.aetherteam.aetherii.item.equipment.tools.holystone.HolystoneTrowelItem;
import com.aetherteam.aetherii.item.equipment.tools.skyroot.SkyrootAxeItem;
import com.aetherteam.aetherii.item.equipment.tools.skyroot.SkyrootPickaxeItem;
import com.aetherteam.aetherii.item.equipment.tools.skyroot.SkyrootShovelItem;
import com.aetherteam.aetherii.item.equipment.tools.skyroot.SkyrootTrowelItem;
import com.aetherteam.aetherii.item.equipment.tools.zanite.ZaniteAxeItem;
import com.aetherteam.aetherii.item.equipment.tools.zanite.ZanitePickaxeItem;
import com.aetherteam.aetherii.item.equipment.tools.zanite.ZaniteShovelItem;
import com.aetherteam.aetherii.item.equipment.tools.zanite.ZaniteTrowelItem;
import com.aetherteam.aetherii.item.equipment.weapons.ScatterglassBoltItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.arkenium.ArkeniumCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.arkenium.ArkeniumHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.arkenium.ArkeniumShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.arkenium.ArkeniumSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.gravitite.GravititeCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.gravitite.GravititeHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.gravitite.GravititeShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.gravitite.GravititeSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.holystone.HolystoneCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.holystone.HolystoneHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.holystone.HolystoneShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.holystone.HolystoneSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.skyroot.SkyrootCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.skyroot.SkyrootHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.skyroot.SkyrootShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.skyroot.SkyrootSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.zanite.ZaniteCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.zanite.ZaniteHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.zanite.ZaniteShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.zanite.ZaniteSpearItem;
import com.aetherteam.aetherii.item.materials.*;
import com.aetherteam.aetherii.item.miscellaneous.*;
import com.aetherteam.aetherii.item.miscellaneous.bucket.*;
import com.aetherteam.aetherii.item.miscellaneous.glider.*;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class AetherIIItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AetherII.MODID);

    public static final ResourceLocation BASE_SLASH_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_slash_damage");
    public static final ResourceLocation BASE_IMPACT_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_impact_damage");
    public static final ResourceLocation BASE_PIERCE_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_pierce_damage");

    public static final Style CURRENCY_NAME_COLOR = Style.EMPTY.withColor(12566527);
    public static final Style TREASURE_NAME_COLOR = Style.EMPTY.withColor(16765952);
    public static final Style WEAPON_TOOLTIP_COLOR = Style.EMPTY.withColor(11393240);

    public static final Rarity AETHER_II_CURRENCY = Rarity.valueOf("AETHER_II_CURRENCY");
    public static final Rarity AETHER_II_TREASURE = Rarity.valueOf("AETHER_II_TREASURE");

    // Tools
    public static final DeferredItem<PickaxeItem> SKYROOT_PICKAXE = register("skyroot_pickaxe", SkyrootPickaxeItem::new);
    public static final DeferredItem<AxeItem> SKYROOT_AXE = register("skyroot_axe", SkyrootAxeItem::new);
    public static final DeferredItem<ShovelItem> SKYROOT_SHOVEL = register("skyroot_shovel", SkyrootShovelItem::new);
    public static final DeferredItem<HoeItem> SKYROOT_TROWEL = register("skyroot_trowel", SkyrootTrowelItem::new);

    public static final DeferredItem<PickaxeItem> HOLYSTONE_PICKAXE = register("holystone_pickaxe", HolystonePickaxeItem::new);
    public static final DeferredItem<AxeItem> HOLYSTONE_AXE = register("holystone_axe", HolystoneAxeItem::new);
    public static final DeferredItem<ShovelItem> HOLYSTONE_SHOVEL = register("holystone_shovel", HolystoneShovelItem::new);
    public static final DeferredItem<HoeItem> HOLYSTONE_TROWEL = register("holystone_trowel", HolystoneTrowelItem::new);

    public static final DeferredItem<PickaxeItem> ZANITE_PICKAXE = register("zanite_pickaxe", ZanitePickaxeItem::new);
    public static final DeferredItem<AxeItem> ZANITE_AXE = register("zanite_axe", ZaniteAxeItem::new);
    public static final DeferredItem<ShovelItem> ZANITE_SHOVEL = register("zanite_shovel", ZaniteShovelItem::new);
    public static final DeferredItem<HoeItem> ZANITE_TROWEL = register("zanite_trowel", ZaniteTrowelItem::new);

    public static final DeferredItem<PickaxeItem> ARKENIUM_PICKAXE = register("arkenium_pickaxe", ArkeniumPickaxeItem::new);
    public static final DeferredItem<AxeItem> ARKENIUM_AXE = register("arkenium_axe", ArkeniumAxeItem::new);
    public static final DeferredItem<ShovelItem> ARKENIUM_SHOVEL = register("arkenium_shovel", ArkeniumShovelItem::new);
    public static final DeferredItem<HoeItem> ARKENIUM_TROWEL = register("arkenium_trowel", ArkeniumTrowelItem::new);

    public static final DeferredItem<PickaxeItem> GRAVITITE_PICKAXE = register("gravitite_pickaxe", GravititePickaxeItem::new);
    public static final DeferredItem<AxeItem> GRAVITITE_AXE = register("gravitite_axe", GravititeAxeItem::new);
    public static final DeferredItem<ShovelItem> GRAVITITE_SHOVEL = register("gravitite_shovel", GravititeShovelItem::new);
    public static final DeferredItem<HoeItem> GRAVITITE_TROWEL = register("gravitite_trowel", GravititeTrowelItem::new);

    public static final DeferredItem<Item> ARKENIUM_SHEARS = register("arkenium_shears", ShearsItem::new, () -> new Item.Properties().durability(238).component(DataComponents.TOOL, ShearsItem.createToolProperties()));

    // Combat
    public static final DeferredItem<SwordItem> SKYROOT_SHORTSWORD = register("skyroot_shortsword", SkyrootShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> SKYROOT_HAMMER = register("skyroot_hammer", SkyrootHammerItem::new);
    public static final DeferredItem<TieredSpearItem> SKYROOT_SPEAR = register("skyroot_spear", SkyrootSpearItem::new);
    public static final DeferredItem<CrossbowItem> SKYROOT_CROSSBOW = register("skyroot_crossbow", SkyrootCrossbowItem::new);
    public static final DeferredItem<ShieldItem> SKYROOT_SHIELD = register("skyroot_shield", (properties) -> new TieredShieldItem(AetherIIItemTiers.SKYROOT, properties), () -> new Item.Properties().attributes(TieredShieldItem.createAttributes(100)));

    public static final DeferredItem<SwordItem> HOLYSTONE_SHORTSWORD = register("holystone_shortsword", HolystoneShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> HOLYSTONE_HAMMER = register("holystone_hammer", HolystoneHammerItem::new);
    public static final DeferredItem<TieredSpearItem> HOLYSTONE_SPEAR = register("holystone_spear", HolystoneSpearItem::new);
    public static final DeferredItem<CrossbowItem> HOLYSTONE_CROSSBOW = register("holystone_crossbow", HolystoneCrossbowItem::new);
    public static final DeferredItem<ShieldItem> HOLYSTONE_SHIELD = register("holystone_shield", (properties) -> new TieredShieldItem(AetherIIItemTiers.HOLYSTONE, properties), () -> new Item.Properties().attributes(TieredShieldItem.createAttributes(80)));

    public static final DeferredItem<SwordItem> ZANITE_SHORTSWORD = register("zanite_shortsword", ZaniteShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> ZANITE_HAMMER = register("zanite_hammer", ZaniteHammerItem::new);
    public static final DeferredItem<TieredSpearItem> ZANITE_SPEAR = register("zanite_spear", ZaniteSpearItem::new);
    public static final DeferredItem<CrossbowItem> ZANITE_CROSSBOW = register("zanite_crossbow", ZaniteCrossbowItem::new);
    public static final DeferredItem<ShieldItem> ZANITE_SHIELD = register("zanite_shield", (properties) -> new TieredShieldItem(AetherIIItemTiers.ZANITE, properties), () -> new Item.Properties().attributes(TieredShieldItem.createAttributes(60)));

    public static final DeferredItem<SwordItem> ARKENIUM_SHORTSWORD = register("arkenium_shortsword", ArkeniumShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> ARKENIUM_HAMMER = register("arkenium_hammer", ArkeniumHammerItem::new);
    public static final DeferredItem<TieredSpearItem> ARKENIUM_SPEAR = register("arkenium_spear", ArkeniumSpearItem::new);
    public static final DeferredItem<CrossbowItem> ARKENIUM_CROSSBOW = register("arkenium_crossbow", ArkeniumCrossbowItem::new);
    public static final DeferredItem<ShieldItem> ARKENIUM_SHIELD = register("arkenium_shield", (properties) -> new TieredShieldItem(AetherIIItemTiers.ARKENIUM, properties), () -> new Item.Properties().attributes(TieredShieldItem.createAttributes(60)));

    public static final DeferredItem<SwordItem> GRAVITITE_SHORTSWORD = register("gravitite_shortsword", GravititeShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> GRAVITITE_HAMMER = register("gravitite_hammer", GravititeHammerItem::new);
    public static final DeferredItem<TieredSpearItem> GRAVITITE_SPEAR = register("gravitite_spear", GravititeSpearItem::new);
    public static final DeferredItem<CrossbowItem> GRAVITITE_CROSSBOW = register("gravitite_crossbow", GravititeCrossbowItem::new);
    public static final DeferredItem<ShieldItem> GRAVITITE_SHIELD = register("gravitite_shield", (properties) -> new TieredShieldItem(AetherIIItemTiers.GRAVITITE, properties), () -> new Item.Properties().attributes(TieredShieldItem.createAttributes(40)));

    public static final DeferredItem<Item> SCATTERGLASS_BOLT = register("scatterglass_bolt", ScatterglassBoltItem::new);

    // Armor
    public static final DeferredItem<Item> TAEGORE_HIDE_HELMET = register("taegore_hide_helmet", (properties) -> new ArmorItem(AetherIIArmorMaterials.TAEGORE_HIDE, ArmorType.HELMET, properties));
    public static final DeferredItem<Item> TAEGORE_HIDE_CHESTPLATE = register("taegore_hide_chestplate", (properties) -> new ArmorItem(AetherIIArmorMaterials.TAEGORE_HIDE, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<Item> TAEGORE_HIDE_LEGGINGS = register("taegore_hide_leggings", (properties) -> new ArmorItem(AetherIIArmorMaterials.TAEGORE_HIDE, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<Item> TAEGORE_HIDE_BOOTS = register("taegore_hide_boots", (properties) -> new ArmorItem(AetherIIArmorMaterials.TAEGORE_HIDE, ArmorType.BOOTS, properties));
    public static final DeferredItem<Item> TAEGORE_HIDE_GLOVES = register("taegore_hide_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.TAEGORE_HIDE, 25.0, properties));

    public static final DeferredItem<Item> BURRUKAI_PELT_HELMET = register("burrukai_pelt_helmet", (properties) -> new ArmorItem(AetherIIArmorMaterials.BURRUKAI_PELT, ArmorType.HELMET, properties));
    public static final DeferredItem<Item> BURRUKAI_PELT_CHESTPLATE = register("burrukai_pelt_chestplate", (properties) -> new ArmorItem(AetherIIArmorMaterials.BURRUKAI_PELT, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<Item> BURRUKAI_PELT_LEGGINGS = register("burrukai_pelt_leggings", (properties) -> new ArmorItem(AetherIIArmorMaterials.BURRUKAI_PELT, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<Item> BURRUKAI_PELT_BOOTS = register("burrukai_pelt_boots", (properties) -> new ArmorItem(AetherIIArmorMaterials.BURRUKAI_PELT, ArmorType.BOOTS, properties));
    public static final DeferredItem<Item> BURRUKAI_PELT_GLOVES = register("burrukai_pelt_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.BURRUKAI_PELT, 50.0, properties));

    public static final DeferredItem<Item> ZANITE_HELMET = register("zanite_helmet", (properties) -> new ArmorItem(AetherIIArmorMaterials.ZANITE, ArmorType.HELMET, properties));
    public static final DeferredItem<Item> ZANITE_CHESTPLATE = register("zanite_chestplate", (properties) -> new ArmorItem(AetherIIArmorMaterials.ZANITE, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<Item> ZANITE_LEGGINGS = register("zanite_leggings", (properties) -> new ArmorItem(AetherIIArmorMaterials.ZANITE, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<Item> ZANITE_BOOTS = register("zanite_boots", (properties) -> new ArmorItem(AetherIIArmorMaterials.ZANITE, ArmorType.BOOTS, properties));
    public static final DeferredItem<Item> ZANITE_GLOVES = register("zanite_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.ZANITE, 100.0, properties));

    public static final DeferredItem<Item> ARKENIUM_HELMET = register("arkenium_helmet", (properties) -> new ArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorType.HELMET, properties));
    public static final DeferredItem<Item> ARKENIUM_CHESTPLATE = register("arkenium_chestplate", (properties) -> new ArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<Item> ARKENIUM_LEGGINGS = register("arkenium_leggings", (properties) -> new ArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<Item> ARKENIUM_BOOTS = register("arkenium_boots", (properties) -> new ArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorType.BOOTS, properties));
    public static final DeferredItem<Item> ARKENIUM_GLOVES = register("arkenium_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.ARKENIUM, 100.0, properties));

    public static final DeferredItem<Item> GRAVITITE_HELMET = register("gravitite_helmet", (properties) -> new ArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorType.HELMET, properties));
    public static final DeferredItem<Item> GRAVITITE_CHESTPLATE = register("gravitite_chestplate", (properties) -> new ArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<Item> GRAVITITE_LEGGINGS = register("gravitite_leggings", (properties) -> new ArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<Item> GRAVITITE_BOOTS = register("gravitite_boots", (properties) -> new ArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorType.BOOTS, properties));
    public static final DeferredItem<Item> GRAVITITE_GLOVES = register("gravitite_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.GRAVITITE, 200.0, properties));

    // Materials
    public static final DeferredItem<Item> SKYROOT_STICK = register("skyroot_stick");
    public static final DeferredItem<Item> SKYROOT_PINECONE = register("skyroot_pinecone", SkyrootPineconeItem::new);
    public static final DeferredItem<Item> VALKYRIE_WINGS = register("valkyrie_wings");
    public static final DeferredItem<Item> SCATTERGLASS_SHARD = register("scatterglass_shard");
    public static final DeferredItem<Item> AMBROSIUM_SHARD = register("ambrosium_shard", AmbrosiumShardItem::new);
    public static final DeferredItem<Item> ZANITE_GEMSTONE = register("zanite_gemstone");
    public static final DeferredItem<Item> INERT_ARKENIUM = register("inert_arkenium");
    public static final DeferredItem<Item> ARKENIUM_PLATES = register("arkenium_plates");
    public static final DeferredItem<Item> INERT_GRAVITITE = register("inert_gravitite");
    public static final DeferredItem<Item> GRAVITITE_PLATE = register("gravitite_plate");
    public static final DeferredItem<Item> CORROBONITE_CRYSTAL = register("corrobonite_crystal");
    public static final DeferredItem<Item> GLINT_GEMSTONE = register("glint_gemstone", (properties) -> new CurrencyItem(10, properties)); //todo
    public static final DeferredItem<Item> GOLDEN_AMBER = register("golden_amber");
    public static final DeferredItem<Item> CLOUDTWINE = register("cloudtwine");
    public static final DeferredItem<Item> TAEGORE_HIDE = register("taegore_hide");
    public static final DeferredItem<Item> BURRUKAI_PELT = register("burrukai_pelt");
    public static final DeferredItem<Item> AECHOR_PETAL = register("aechor_petal");
    public static final DeferredItem<Item> BRETTL_CANE = register("brettl_cane", (properties) -> new BlockItem(AetherIIBlocks.BRETTL_PLANT_TIP.get(), properties.useItemDescriptionPrefix()));
    public static final DeferredItem<Item> BRETTL_GRASS = register("brettl_grass");
    public static final DeferredItem<Item> BRETTL_ROPE = register("brettl_rope");
    public static final DeferredItem<Item> BRETTL_FLOWER = register("brettl_flower");
    public static final DeferredItem<Item> ARILUM_BULBS = register("arilum_bulbs", (properties) -> new BlockItem(AetherIIBlocks.ARILUM_SHOOT.get(), properties.useItemDescriptionPrefix()));
    public static final DeferredItem<Item> ARCTIC_SNOWBALL = register("arctic_snowball", ArcticSnowballItem::new);
    public static final DeferredItem<Item> GREEN_SWET_GEL = register("green_swet_gel", SwetGelItem::new);
    public static final DeferredItem<Item> BLUE_SWET_GEL = register("blue_swet_gel", SwetGelItem::new);
    public static final DeferredItem<Item> PURPLE_SWET_GEL = register("purple_swet_gel", SwetGelItem::new);
    public static final DeferredItem<Item> GOLDEN_SWET_GEL = register("golden_swet_gel", SwetGelItem::new);
    public static final DeferredItem<Item> WHITE_SWET_GEL = register("white_swet_gel", SwetGelItem::new);
    public static final DeferredItem<Item> SWET_SUGAR = register("swet_sugar");
    public static final DeferredItem<Item> MOA_FEATHER = register("moa_feather", MoaFeatherItem::new, () -> new Item.Properties().component(AetherIIDataComponents.FEATHER_COLOR.get(), Moa.FeatherColor.LIGHT_BLUE));
    public static final DeferredItem<Item> COCKATRICE_FEATHER = register("cockatrice_feather");

    public static final DeferredItem<Item> SCATTERGLASS_VIAL = register("scatterglass_vial", VialItem::new, () -> new Item.Properties().stacksTo(8));
    public static final DeferredItem<Item> CHARGE_CORE = register("charge_core", TreasureItem::new);

    // Irradiated Items
    public static final DeferredItem<Item> IRRADIATED_ARMOR = register("irradiated_armor", () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> IRRADIATED_WEAPON = register("irradiated_weapon", () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> IRRADIATED_TOOL = register("irradiated_tool", () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> IRRADIATED_CHUNK = register("irradiated_chunk", () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> IRRADIATED_DUST = register("irradiated_dust", IrradiatedDustItem::new);

    // Food
    public static final DeferredItem<Item> BLUEBERRY = register("blueberry", () -> new Item.Properties().food(AetherIIFoods.BLUEBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> ENCHANTED_BLUEBERRY = register("enchanted_blueberry", () -> new Item.Properties().food(AetherIIFoods.ENCHANTED_BLUEBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> ORANGE = register("orange", () -> new Item.Properties().food(AetherIIFoods.ORANGE));
    public static final DeferredItem<Item> SATIVAL_BULB = register("satival_bulb", () -> new Item.Properties().food(AetherIIFoods.SATIVAL_BULB).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> WYNDBERRY = register("wyndberry", () -> new Item.Properties().food(AetherIIFoods.WYNDBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> ENCHANTED_WYNDBERRY = register("enchanted_wyndberry", () -> new Item.Properties().food(AetherIIFoods.ENCHANTED_WYNDBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> GREEN_SWET_JELLY = register("green_swet_jelly", () -> new Item.Properties().food(AetherIIFoods.SWET_JELLY));
    public static final DeferredItem<Item> BLUE_SWET_JELLY = register("blue_swet_jelly", () -> new Item.Properties().food(AetherIIFoods.SWET_JELLY));
    public static final DeferredItem<Item> PURPLE_SWET_JELLY = register("purple_swet_jelly", () -> new Item.Properties().food(AetherIIFoods.SWET_JELLY));
    public static final DeferredItem<Item> GOLDEN_SWET_JELLY = register("golden_swet_jelly", () -> new Item.Properties().food(AetherIIFoods.SWET_JELLY));
    public static final DeferredItem<Item> WHITE_SWET_JELLY = register("white_swet_jelly", () -> new Item.Properties().food(AetherIIFoods.SWET_JELLY));
    public static final DeferredItem<Item> BURRUKAI_RIB_CUT = register("burrukai_rib_cut", () -> new Item.Properties().food(AetherIIFoods.BURRUKAI_RIB_CUT));
    public static final DeferredItem<Item> BURRUKAI_RIBS = register("burrukai_ribs", () -> new Item.Properties().food(AetherIIFoods.BURRUKAI_RIBS));
    public static final DeferredItem<Item> KIRRID_LOIN = register("kirrid_loin", () -> new Item.Properties().food(AetherIIFoods.KIRRID_LOIN));
    public static final DeferredItem<Item> KIRRID_CUTLET = register("kirrid_cutlet", () -> new Item.Properties().food(AetherIIFoods.KIRRID_CUTLET));
    public static final DeferredItem<Item> RAW_TAEGORE_MEAT = register("raw_taegore_meat", () -> new Item.Properties().food(AetherIIFoods.RAW_TAEGORE_MEAT));
    public static final DeferredItem<Item> TAEGORE_STEAK = register("taegore_steak", () -> new Item.Properties().food(AetherIIFoods.TAEGORE_STEAK));
    public static final DeferredItem<Item> SKYROOT_LIZARD_ON_A_STICK = register("skyroot_lizard_on_a_stick", () -> new Item.Properties().food(AetherIIFoods.SKYROOT_LIZARD_ON_A_STICK).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> ROASTED_SKYROOT_LIZARD_ON_A_STICK = register("roasted_skyroot_lizard_on_a_stick", () -> new Item.Properties().food(AetherIIFoods.ROASTED_SKYROOT_LIZARD_ON_A_STICK).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));

    // Consumables
    public static final DeferredItem<Item> WATER_VIAL = register("water_vial", WaterVialItem::new, () -> new Item.Properties().stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.WATER_VIAL).usingConvertsTo(SCATTERGLASS_VIAL.get()));
    public static final DeferredItem<Item> BANDAGE = register("bandage", () -> new Item.Properties().component(DataComponents.CONSUMABLE, AetherIIConsumables.BANDAGE));
    public static final DeferredItem<Item> SPLINT = register("splint", () -> new Item.Properties().component(DataComponents.CONSUMABLE, AetherIIConsumables.SPLINT));
    public static final DeferredItem<Item> ANTITOXIN_VIAL = register("antitoxin_vial", () -> new Item.Properties().stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.ANTITOXIN_VIAL).usingConvertsTo(SCATTERGLASS_VIAL.get()));
    public static final DeferredItem<Item> ANTIVENOM_VIAL = register("antivenom_vial", () -> new Item.Properties().stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.ANTIVENOM_VIAL).usingConvertsTo(SCATTERGLASS_VIAL.get()));
    public static final DeferredItem<Item> VALKYRIE_TEA = register("valkyrie_tea", () -> new Item.Properties().stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.VALKYRIE_TEA).usingConvertsTo(SCATTERGLASS_VIAL.get()));
    public static final DeferredItem<Item> HEALING_STONE = register("healing_stone", (properties) -> new HealingStoneItem(properties.stacksTo(1).component(AetherIIDataComponents.HEALING_STONE_CHARGES, 0)));

    // Gliders
    public static final DeferredItem<Item> COLD_AERCLOUD_GLIDER = register("cold_aercloud_glider", (properties) -> new AercloudGliderItem(properties.durability(5).setNoCombineRepair()));
    public static final DeferredItem<Item> GOLDEN_AERCLOUD_GLIDER = register("golden_aercloud_glider", (properties) -> new GoldenAercloudGliderItem(properties.durability(30).setNoCombineRepair()));
    public static final DeferredItem<Item> BLUE_AERCLOUD_GLIDER = register("blue_aercloud_glider", (properties) -> new BlueAercloudGliderItem(properties.durability(3).setNoCombineRepair()));
    public static final DeferredItem<Item> PURPLE_AERCLOUD_GLIDER = register("purple_aercloud_glider", (properties) -> new PurpleAercloudGliderItem(properties.durability(3).setNoCombineRepair()));

    // Skyroot Buckets
    public static final DeferredItem<Item> SKYROOT_BUCKET = register("skyroot_bucket", (properties) -> new SkyrootBucketItem(Fluids.EMPTY, properties.stacksTo(16)));
    public static final DeferredItem<Item> SKYROOT_WATER_BUCKET = register("skyroot_water_bucket", (properties) -> new SkyrootBucketItem(Fluids.WATER, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_MILK_BUCKET = register("skyroot_milk_bucket", () -> new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(SKYROOT_BUCKET.get()).stacksTo(1));
    public static final DeferredItem<Item> SKYROOT_POWDER_SNOW_BUCKET = register("skyroot_powder_snow_bucket", (properties) -> new SkyrootSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_COD_BUCKET = register("skyroot_cod_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_SALMON_BUCKET = register("skyroot_salmon_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_PUFFERFISH_BUCKET = register("skyroot_pufferfish_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_TROPICAL_FISH_BUCKET = register("skyroot_tropical_fish_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_AXOLOTL_BUCKET = register("skyroot_axolotl_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_TADPOLE_BUCKET = register("skyroot_tadpole_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));

    // Arkenium Canisters
    public static final DeferredItem<Item> ARKENIUM_CANISTER = register("arkenium_canister", (properties) -> new ArkeniumCanisterItem(Fluids.EMPTY, properties.stacksTo(16)));
    public static final DeferredItem<Item> ARKENIUM_ACID_CANISTER = register("arkenium_acid_canister", (properties) -> new ArkeniumCanisterItem(AetherIIFluids.ACID.get(), properties.stacksTo(1).craftRemainder(ARKENIUM_CANISTER.get())));
    public static final DeferredItem<Item> ARKENIUM_GAS_CANISTER = register("arkenium_gas_canister", (properties) -> new SolidCanisterItem(AetherIIBlocks.GAS.get(), SoundEvents.BUCKET_EMPTY_POWDER_SNOW, properties.stacksTo(1).craftRemainder(ARKENIUM_CANISTER.get()))); //todo

    // Music Discs
    public static final DeferredItem<Item> MUSIC_DISC_AETHER_TUNE = register("music_disc_aether_tune", () -> new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.AETHER_TUNE));
    public static final DeferredItem<Item> MUSIC_DISC_ASCENDING_DAWN = register("music_disc_ascending_dawn", () -> new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.ASCENDING_DAWN));
    public static final DeferredItem<Item> MUSIC_DISC_AERWHALE = register("music_disc_aerwhale", () -> new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.AERWHALE));
    public static final DeferredItem<Item> MUSIC_DISC_APPROACHES = register("music_disc_approaches", () -> new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.APPROACHES));
    public static final DeferredItem<Item> MUSIC_DISC_DEMISE = register("music_disc_demise", () -> new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.DEMISE));
    public static final DeferredItem<Item> RECORDING_892 = register("recording_892", () -> new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherIIJukeboxSongs.RECORDING_892));

    // Spawn Eggs
    public static final DeferredItem<SpawnEggItem> FLYING_COW_SPAWN_EGG = register("flying_cow_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.FLYING_COW.get(), properties));
    public static final DeferredItem<SpawnEggItem> SHEEPUFF_SPAWN_EGG = register("sheepuff_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.SHEEPUFF.get(), properties));
    public static final DeferredItem<SpawnEggItem> PHYG_SPAWN_EGG = register("phyg_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.PHYG.get(), properties));
    public static final DeferredItem<SpawnEggItem> AERBUNNY_SPAWN_EGG = register("aerbunny_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.AERBUNNY.get(), properties));
    public static final DeferredItem<SpawnEggItem> HIGHFIELDS_TAEGORE_SPAWN_EGG = register("highfields_taegore_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), "highfields", properties));
    public static final DeferredItem<SpawnEggItem> MAGNETIC_TAEGORE_SPAWN_EGG = register("magnetic_taegore_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), "magnetic", properties));
    public static final DeferredItem<SpawnEggItem> ARCTIC_TAEGORE_SPAWN_EGG = register("arctic_taegore_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), "arctic", properties));
    public static final DeferredItem<SpawnEggItem> HIGHFIELDS_BURRUKAI_SPAWN_EGG = register("highfields_burrukai_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), "highfields", properties));
    public static final DeferredItem<SpawnEggItem> MAGNETIC_BURRUKAI_SPAWN_EGG = register("magnetic_burrukai_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), "magnetic", properties));
    public static final DeferredItem<SpawnEggItem> ARCTIC_BURRUKAI_SPAWN_EGG = register("arctic_burrukai_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), "arctic", properties));
    public static final DeferredItem<SpawnEggItem> HIGHFIELDS_KIRRID_SPAWN_EGG = register("highfields_kirrid_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), "highfields", properties));
    public static final DeferredItem<SpawnEggItem> MAGNETIC_KIRRID_SPAWN_EGG = register("magnetic_kirrid_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), "magnetic", properties));
    public static final DeferredItem<SpawnEggItem> ARCTIC_KIRRID_SPAWN_EGG = register("arctic_kirrid_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.ARCTIC_KIRRID.get(), "arctic", properties));
    public static final DeferredItem<SpawnEggItem> MOA_SPAWN_EGG = register("moa_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.MOA.get(), properties));
    public static final DeferredItem<SpawnEggItem> SKYROOT_LIZARD_SPAWN_EGG = register("skyroot_lizard_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.SKYROOT_LIZARD.get(), properties));
    public static final DeferredItem<SpawnEggItem> AECHOR_PLANT_SPAWN_EGG = register("aechor_plant_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.AECHOR_PLANT.get(), properties));
    public static final DeferredItem<SpawnEggItem> ZEPHYR_SPAWN_EGG = register("zephyr_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.ZEPHYR.get(), properties));
    public static final DeferredItem<SpawnEggItem> TEMPEST_SPAWN_EGG = register("tempest_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.TEMPEST.get(), properties));
    public static final DeferredItem<SpawnEggItem> COCKATRICE_SPAWN_EGG = register("cockatrice_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.COCKATRICE.get(), properties));
    public static final DeferredItem<SpawnEggItem> SWET_SPAWN_EGG = register("swet_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.SWET.get(), properties));
    public static final DeferredItem<SpawnEggItem> SKEPHID_SPAWN_EGG = register("skephid_spawn_egg", (properties) -> new SpawnEggItem(AetherIIEntityTypes.SKEPHID.get(), properties));

    // Misc
    public static final DeferredItem<Item> MOA_EGG = register("moa_egg", MoaEggItem::new, () -> new Item.Properties().stacksTo(1).component(AetherIIDataComponents.MOA_EGG_TYPE.get(), MoaEggType.defaultType()));
    public static final DeferredItem<Item> MOA_FEED = register("moa_feed");
    public static final DeferredItem<Item> BLUEBERRY_MOA_FEED = register("blueberry_moa_feed");
    public static final DeferredItem<Item> ENCHANTED_MOA_FEED = register("enchanted_moa_feed");
    public static final DeferredItem<Item> MOA_SADDLE = register("moa_saddle", MoaSaddleItem::new, () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> GLINT_COIN = register("glint_coin", (properties) -> new CurrencyItem(1, properties));
    public static final DeferredItem<Item> AETHER_PORTAL_FRAME = register("aether_portal_frame", AetherPortalItem::new, () -> new Item.Properties().stacksTo(1));

    private static <T extends Item> DeferredItem<Item> register(String name) {
        return register(name, Item::new);
    }

    private static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> builder) {
        return baseRegister(name, createKey(name), builder, Item.Properties::new);
    }

    private static <T extends Item> DeferredItem<Item> register(String name, Supplier<Item.Properties> properties) {
        return register(name, Item::new, properties);
    }
    
    private static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> builder, Supplier<Item.Properties> properties) {
        return baseRegister(name, createKey(name), builder, properties);
    }
    
    private static <T extends Item> DeferredItem<T> baseRegister(String name, ResourceKey<Item> key, Function<Item.Properties, T> builder, Supplier<Item.Properties> properties) {
        return ITEMS.register(name, () -> builder.apply(properties.get().setId(key)));
    }
    
    private static ResourceKey<Item> createKey(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void registerAccessories() {
        AccessoriesAPI.registerAccessory(AetherIIItems.TAEGORE_HIDE_GLOVES.get(), (Accessory) AetherIIItems.TAEGORE_HIDE_GLOVES.get());
        AccessoriesAPI.registerAccessory(AetherIIItems.BURRUKAI_PELT_GLOVES.get(), (Accessory) AetherIIItems.BURRUKAI_PELT_GLOVES.get());
        AccessoriesAPI.registerAccessory(AetherIIItems.ZANITE_GLOVES.get(), (Accessory) AetherIIItems.ZANITE_GLOVES.get());
        AccessoriesAPI.registerAccessory(AetherIIItems.ARKENIUM_GLOVES.get(), (Accessory) AetherIIItems.ARKENIUM_GLOVES.get());
        AccessoriesAPI.registerAccessory(AetherIIItems.GRAVITITE_GLOVES.get(), (Accessory) AetherIIItems.GRAVITITE_GLOVES.get());
    }

    public static void registerEquipmentAbilities(IEventBus bus) {
        // Armor
        bus.addListener(TaegoreHideArmor::updateEntityTargeting);
        bus.addListener(BurrukaiPeltArmor::updatePlayerAttributes);
        bus.addListener(ZaniteArmor::updatePlayerAttributes);
        bus.addListener(ArkeniumArmor::updatePlayerAttributes);
        bus.addListener(ArkeniumArmor::modifyIncomingDamage);
        bus.addListener(GravititeArmor::playerFall);
        bus.addListener(GravititeArmor::playerUpdate);

        // Tools
        bus.addListener(HolystoneTool::dropAmbrosium);
    }

    public static void registerTooltips(Player player, ItemStack itemStack, List<Component> itemTooltips) {
        EquipmentUtil.addShieldTooltips(itemTooltips, itemStack);
        EquipmentUtil.addArmorTooltips(player, itemTooltips, itemStack);
        EquipmentUtil.addGloveTooltips(player, itemTooltips, itemStack); //todo move to glovesitem class.
        EquipmentUtil.addReinforcingTooltip(itemStack, itemTooltips);
        EquipmentUtil.addEffectResistanceTooltips(player, itemStack, itemTooltips);
    }
}