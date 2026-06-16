package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIJukeboxSongs;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStyleDesigns;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStyleMaterials;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.*;
import com.aetherteam.aetherii.item.consumables.ShiftingGlassItem;
import com.aetherteam.aetherii.item.consumables.HealingStoneItem;
import com.aetherteam.aetherii.item.equipment.AetherEquippable;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.BrokenItem;
import com.aetherteam.aetherii.item.equipment.accessories.GlovesItem;
import com.aetherteam.aetherii.item.equipment.accessories.IcestonePendantItem;
import com.aetherteam.aetherii.item.equipment.accessories.KineticThrustersItem;
import com.aetherteam.aetherii.item.equipment.accessories.ZanitePendantItem;
import com.aetherteam.aetherii.item.miscellaneous.CompanionItem;
import com.aetherteam.aetherii.item.equipment.armor.AetherArmorItem;
import com.aetherteam.aetherii.item.equipment.armor.AetherIIArmorMaterials;
import com.aetherteam.aetherii.item.equipment.armor.abilities.*;
import com.aetherteam.aetherii.item.equipment.charms.CharmItem;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
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
import com.aetherteam.aetherii.item.equipment.tools.zanite.*;
import com.aetherteam.aetherii.item.equipment.weapons.*;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import com.aetherteam.aetherii.item.equipment.weapons.arkenium.ArkeniumCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.arkenium.ArkeniumHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.arkenium.ArkeniumPikeItem;
import com.aetherteam.aetherii.item.equipment.weapons.arkenium.ArkeniumShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.gravitite.GravititeCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.gravitite.GravititeHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.gravitite.GravititePikeItem;
import com.aetherteam.aetherii.item.equipment.weapons.gravitite.GravititeShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.holystone.HolystoneCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.holystone.HolystoneHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.holystone.HolystonePikeItem;
import com.aetherteam.aetherii.item.equipment.weapons.holystone.HolystoneShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.loot.HammerOfDemolitionItem;
import com.aetherteam.aetherii.item.equipment.weapons.skyroot.SkyrootCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.skyroot.SkyrootHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.skyroot.SkyrootPikeItem;
import com.aetherteam.aetherii.item.equipment.weapons.skyroot.SkyrootShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.zanite.ZaniteCrossbowItem;
import com.aetherteam.aetherii.item.equipment.weapons.zanite.ZaniteHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.zanite.ZanitePikeItem;
import com.aetherteam.aetherii.item.equipment.weapons.zanite.ZaniteShortswordItem;
import com.aetherteam.aetherii.item.materials.*;
import com.aetherteam.aetherii.item.miscellaneous.*;
import com.aetherteam.aetherii.item.miscellaneous.bucket.*;
import com.aetherteam.aetherii.item.miscellaneous.glider.AercloudGliderItem;
import com.aetherteam.aetherii.item.miscellaneous.glider.BlueAercloudGliderItem;
import com.aetherteam.aetherii.item.miscellaneous.glider.GoldenAercloudGliderItem;
import com.aetherteam.aetherii.item.miscellaneous.glider.PurpleAercloudGliderItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class AetherIIItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AetherII.MODID);

    public static final Identifier BASE_SLASH_DAMAGE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_slash_damage");
    public static final Identifier BASE_IMPACT_DAMAGE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_impact_damage");
    public static final Identifier BASE_PIERCE_DAMAGE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_pierce_damage");
    public static final Identifier BASE_SLASH_RANGED_DAMAGE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_slash_ranged_damage");
    public static final Identifier BASE_IMPACT_RANGED_DAMAGE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_impact_ranged_damage");
    public static final Identifier BASE_PIERCE_RANGED_DAMAGE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_pierce_ranged_damage");

    public static final Style CURRENCY_NAME_COLOR = Style.EMPTY.withColor(12566527);
    public static final Style TREASURE_NAME_COLOR = Style.EMPTY.withColor(16765952);
    public static final Style UPGRADED_WEAPON_COLOR = Style.EMPTY.withColor(11730876);
    public static final Style WEAPON_TOOLTIP_COLOR = Style.EMPTY.withColor(11393240);

    public static final Rarity AETHER_II_CURRENCY = Rarity.valueOf("AETHER_II_CURRENCY");
    public static final Rarity AETHER_II_TREASURE = Rarity.valueOf("AETHER_II_TREASURE");
    public static final Rarity AETHER_II_UPGRADED = Rarity.valueOf("AETHER_II_UPGRADED");

    // Tools
    public static final DeferredItem<Item> SKYROOT_PICKAXE = register("skyroot_pickaxe", SkyrootPickaxeItem::new);
    public static final DeferredItem<AxeItem> SKYROOT_AXE = register("skyroot_axe", SkyrootAxeItem::new);
    public static final DeferredItem<ShovelItem> SKYROOT_SHOVEL = register("skyroot_shovel", SkyrootShovelItem::new);
    public static final DeferredItem<HoeItem> SKYROOT_TROWEL = register("skyroot_trowel", SkyrootTrowelItem::new);

    public static final DeferredItem<Item> HOLYSTONE_PICKAXE = register("holystone_pickaxe", HolystonePickaxeItem::new);
    public static final DeferredItem<AxeItem> HOLYSTONE_AXE = register("holystone_axe", HolystoneAxeItem::new);
    public static final DeferredItem<ShovelItem> HOLYSTONE_SHOVEL = register("holystone_shovel", HolystoneShovelItem::new);
    public static final DeferredItem<HoeItem> HOLYSTONE_TROWEL = register("holystone_trowel", HolystoneTrowelItem::new);

    public static final DeferredItem<Item> ZANITE_PICKAXE = register("zanite_pickaxe", ZanitePickaxeItem::new);
    public static final DeferredItem<AxeItem> ZANITE_AXE = register("zanite_axe", ZaniteAxeItem::new);
    public static final DeferredItem<ShovelItem> ZANITE_SHOVEL = register("zanite_shovel", ZaniteShovelItem::new);
    public static final DeferredItem<HoeItem> ZANITE_TROWEL = register("zanite_trowel", ZaniteTrowelItem::new);

    public static final DeferredItem<Item> ARKENIUM_PICKAXE = register("arkenium_pickaxe", ArkeniumPickaxeItem::new);
    public static final DeferredItem<AxeItem> ARKENIUM_AXE = register("arkenium_axe", ArkeniumAxeItem::new);
    public static final DeferredItem<ShovelItem> ARKENIUM_SHOVEL = register("arkenium_shovel", ArkeniumShovelItem::new);
    public static final DeferredItem<HoeItem> ARKENIUM_TROWEL = register("arkenium_trowel", ArkeniumTrowelItem::new);

    public static final DeferredItem<Item> GRAVITITE_PICKAXE = register("gravitite_pickaxe", GravititePickaxeItem::new);
    public static final DeferredItem<AxeItem> GRAVITITE_AXE = register("gravitite_axe", GravititeAxeItem::new);
    public static final DeferredItem<ShovelItem> GRAVITITE_SHOVEL = register("gravitite_shovel", GravititeShovelItem::new);
    public static final DeferredItem<HoeItem> GRAVITITE_TROWEL = register("gravitite_trowel", GravititeTrowelItem::new);

    public static final DeferredItem<Item> ZANITE_SHEARS = register("zanite_shears", ZaniteShearsItem::new, () -> new Item.Properties().durability(238).component(DataComponents.TOOL, ShearsItem.createToolProperties()));

    // Combat
    public static final DeferredItem<Item> SKYROOT_SHORTSWORD = register("skyroot_shortsword", SkyrootShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> SKYROOT_HAMMER = register("skyroot_hammer", SkyrootHammerItem::new);
    public static final DeferredItem<TieredPikeItem> SKYROOT_PIKE = register("skyroot_pike", SkyrootPikeItem::new);
    public static final DeferredItem<CrossbowItem> SKYROOT_CROSSBOW = register("skyroot_crossbow", SkyrootCrossbowItem::new, () -> new Item.Properties().enchantable(1));

    public static final DeferredItem<Item> HOLYSTONE_SHORTSWORD = register("holystone_shortsword", HolystoneShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> HOLYSTONE_HAMMER = register("holystone_hammer", HolystoneHammerItem::new);
    public static final DeferredItem<TieredPikeItem> HOLYSTONE_PIKE = register("holystone_pike", HolystonePikeItem::new);
    public static final DeferredItem<CrossbowItem> HOLYSTONE_CROSSBOW = register("holystone_crossbow", HolystoneCrossbowItem::new, () -> new Item.Properties().enchantable(1));

    public static final DeferredItem<Item> ZANITE_SHORTSWORD = register("zanite_shortsword", ZaniteShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> ZANITE_HAMMER = register("zanite_hammer", ZaniteHammerItem::new);
    public static final DeferredItem<TieredPikeItem> ZANITE_PIKE = register("zanite_pike", ZanitePikeItem::new);
    public static final DeferredItem<CrossbowItem> ZANITE_CROSSBOW = register("zanite_crossbow", ZaniteCrossbowItem::new, () -> new Item.Properties().enchantable(1));

    public static final DeferredItem<Item> ARKENIUM_SHORTSWORD = register("arkenium_shortsword", ArkeniumShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> ARKENIUM_HAMMER = register("arkenium_hammer", ArkeniumHammerItem::new);
    public static final DeferredItem<TieredPikeItem> ARKENIUM_PIKE = register("arkenium_pike", ArkeniumPikeItem::new);
    public static final DeferredItem<CrossbowItem> ARKENIUM_CROSSBOW = register("arkenium_crossbow", ArkeniumCrossbowItem::new, () -> new Item.Properties().enchantable(1));

    public static final DeferredItem<Item> GRAVITITE_SHORTSWORD = register("gravitite_shortsword", GravititeShortswordItem::new);
    public static final DeferredItem<TieredHammerItem> GRAVITITE_HAMMER = register("gravitite_hammer", GravititeHammerItem::new);
    public static final DeferredItem<TieredPikeItem> GRAVITITE_PIKE = register("gravitite_pike", GravititePikeItem::new);
    public static final DeferredItem<CrossbowItem> GRAVITITE_CROSSBOW = register("gravitite_crossbow", GravititeCrossbowItem::new, () -> new Item.Properties().enchantable(1));

    public static final DeferredItem<ShieldItem> SKYROOT_SHIELD = register("skyroot_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.SKYROOT, 0.05F, properties));
    public static final DeferredItem<ShieldItem> BURRUKAI_PLATE_SHIELD = register("burrukai_plate_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.HOLYSTONE, 0.1F, properties));
    public static final DeferredItem<ShieldItem> ZANITE_SHIELD = register("zanite_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.ZANITE, 0.2F, properties));
    public static final DeferredItem<ShieldItem> ARKENIUM_SHIELD = register("arkenium_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.ARKENIUM, 0.25F, properties));
    public static final DeferredItem<ShieldItem> GRAVITITE_SHIELD = register("gravitite_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.GRAVITITE, 0.4F, properties));

    public static final DeferredItem<Item> DART_SHOOTER = register("dart_shooter", DartShooterItem::new, () -> new Item.Properties().durability(100));
    public static final DeferredItem<Item> AMBER_DARTS = register("amber_darts", AmberDartsItem::new, () -> new Item.Properties().component(AetherIIDataComponents.BUILDUP_CONTENTS, new BuildupContents(EffectBuildupPresets.VULNERABILITY)).stacksTo(16));

    public static final DeferredItem<Item> SCATTERGLASS_BOLT = register("scatterglass_bolt", ScatterglassBoltItem::new);

    public static final DeferredItem<TieredHammerItem> HAMMER_OF_DEMOLITION = register("hammer_of_demolition", HammerOfDemolitionItem::new, () -> new Item.Properties().rarity(Rarity.RARE).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.TWO))));

    // Armor
    public static final DeferredItem<Item> BEAST_PELT_HELMET = register("beast_pelt_helmet", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.BEAST_PELT, ArmorType.HELMET).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));
    public static final DeferredItem<Item> BEAST_PELT_CHESTPLATE = register("beast_pelt_chestplate", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.BEAST_PELT, ArmorType.CHESTPLATE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));
    public static final DeferredItem<Item> BEAST_PELT_LEGGINGS = register("beast_pelt_leggings", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.BEAST_PELT, ArmorType.LEGGINGS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));
    public static final DeferredItem<Item> BEAST_PELT_BOOTS = register("beast_pelt_boots", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.BEAST_PELT, ArmorType.BOOTS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));
    public static final DeferredItem<Item> BEAST_PELT_GLOVES = register("beast_pelt_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.BEAST_PELT, 0.1, 0.05, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));

    public static final DeferredItem<Item> BURRUKAI_PLATE_HELMET = register("burrukai_plate_helmet", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.BURRUKAI_PLATE, ArmorType.HELMET).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));
    public static final DeferredItem<Item> BURRUKAI_PLATE_CHESTPLATE = register("burrukai_plate_chestplate", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.BURRUKAI_PLATE, ArmorType.CHESTPLATE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));
    public static final DeferredItem<Item> BURRUKAI_PLATE_LEGGINGS = register("burrukai_plate_leggings", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.BURRUKAI_PLATE, ArmorType.LEGGINGS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));
    public static final DeferredItem<Item> BURRUKAI_PLATE_BOOTS = register("burrukai_plate_boots", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.BURRUKAI_PLATE, ArmorType.BOOTS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));
    public static final DeferredItem<Item> BURRUKAI_PLATE_GLOVES = register("burrukai_plate_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.BURRUKAI_PLATE, 0.1, 0.1, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));

    public static final DeferredItem<Item> ZANITE_HELMET = register("zanite_helmet", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.ZANITE, ArmorType.HELMET).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));
    public static final DeferredItem<Item> ZANITE_CHESTPLATE = register("zanite_chestplate", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.ZANITE, ArmorType.CHESTPLATE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));
    public static final DeferredItem<Item> ZANITE_LEGGINGS = register("zanite_leggings", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.ZANITE, ArmorType.LEGGINGS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));
    public static final DeferredItem<Item> ZANITE_BOOTS = register("zanite_boots", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.ZANITE, ArmorType.BOOTS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));
    public static final DeferredItem<Item> ZANITE_GLOVES = register("zanite_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.ZANITE, 0.2, 0.25, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));

    public static final DeferredItem<Item> ARKENIUM_HELMET = register("arkenium_helmet", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.ARKENIUM, ArmorType.HELMET).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));
    public static final DeferredItem<Item> ARKENIUM_CHESTPLATE = register("arkenium_chestplate", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.ARKENIUM, ArmorType.CHESTPLATE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));
    public static final DeferredItem<Item> ARKENIUM_LEGGINGS = register("arkenium_leggings", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.ARKENIUM, ArmorType.LEGGINGS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));
    public static final DeferredItem<Item> ARKENIUM_BOOTS = register("arkenium_boots", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.ARKENIUM, ArmorType.BOOTS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));
    public static final DeferredItem<Item> ARKENIUM_GLOVES = register("arkenium_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.ARKENIUM, 0.3, 0.15, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));

    public static final DeferredItem<Item> GRAVITITE_HELMET = register("gravitite_helmet", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.GRAVITITE, ArmorType.HELMET).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));
    public static final DeferredItem<Item> GRAVITITE_CHESTPLATE = register("gravitite_chestplate", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.GRAVITITE, ArmorType.CHESTPLATE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));
    public static final DeferredItem<Item> GRAVITITE_LEGGINGS = register("gravitite_leggings", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.GRAVITITE, ArmorType.LEGGINGS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));
    public static final DeferredItem<Item> GRAVITITE_BOOTS = register("gravitite_boots", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.GRAVITITE, ArmorType.BOOTS).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));
    public static final DeferredItem<Item> GRAVITITE_GLOVES = register("gravitite_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.GRAVITITE, 0.4, 0.3, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));

    public static final DeferredItem<Item> SENTRY_BOOTS = register("sentry_boots", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.SENTRY, ArmorType.BOOTS).rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.SENTRY_ARMOR).attributes(AetherIIArmorMaterials.SENTRY.createAttributes(ArmorType.BOOTS).withModifierAdded(Attributes.FALL_DAMAGE_MULTIPLIER, new AttributeModifier(SentryArmor.SENTRY_FALL_DAMAGE_SUPPRESSION, -0.75, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.FEET)).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));

    public static final DeferredItem<Item> NEPTUNE_HELMET = register("neptune_helmet", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.NEPTUNE, ArmorType.HELMET).rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));
    public static final DeferredItem<Item> NEPTUNE_CHESTPLATE = register("neptune_chestplate", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.NEPTUNE, ArmorType.CHESTPLATE).rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));
    public static final DeferredItem<Item> NEPTUNE_LEGGINGS = register("neptune_leggings", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.NEPTUNE, ArmorType.LEGGINGS).rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));
    public static final DeferredItem<Item> NEPTUNE_BOOTS = register("neptune_boots", (properties) -> new AetherArmorItem(properties.humanoidArmor(AetherIIArmorMaterials.NEPTUNE, ArmorType.BOOTS).rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));
    public static final DeferredItem<Item> NEPTUNE_GLOVES = register("neptune_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.NEPTUNE, 0.4, 0.3, properties.rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));

    // Relics
    public static final DeferredItem<Item> KINETIC_THRUSTERS = register("kinetic_thrusters", KineticThrustersItem::new, () -> new Item.Properties().rarity(Rarity.RARE));

    // Accessories
    public static final DeferredItem<Item> ZANITE_PENDANT = register("zanite_pendant", ZanitePendantItem::new);
    public static final DeferredItem<Item> ICESTONE_PENDANT = register("icestone_pendant", IcestonePendantItem::new);

    // Charms
    public static final DeferredItem<Item> CHARM_OF_EFFICIENCY_I = register("charm_of_efficiency_1", (properties) -> new CharmItem(properties, Charms.Type.TOOL, Charms.Tier.ONE, AetherIIStats.CHARM_MINING_EFFICIENCY_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> CHARM_OF_REACH_I = register("charm_of_reach_1", (properties) -> new CharmItem(properties, Charms.Type.TOOL, Charms.Tier.ONE, AetherIIStats.CHARM_BLOCK_INTERACTION_RANGE_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> CHARM_OF_DAMAGE_I = register("charm_of_damage_1", (properties) -> new CharmItem(properties, Charms.Type.WEAPON, Charms.Tier.ONE, AetherIIStats.CHARM_DAMAGE_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> CHARM_OF_DEXTERITY_I = register("charm_of_dexterity_1", (properties) -> new CharmItem(properties, Charms.Type.WEAPON, Charms.Tier.ONE, AetherIIStats.CHARM_ATTACK_SPEED_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> CHARM_OF_KNOCKBACK_I = register("charm_of_knockback_1", (properties) -> new CharmItem(properties, Charms.Type.WEAPON, Charms.Tier.ONE, AetherIIStats.CHARM_ATTACK_KNOCKBACK_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> CHARM_OF_HEALTH_I = register("charm_of_health_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_MAX_HEALTH_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> CHARM_OF_DEFENSE_I = register("charm_of_defense_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_ARMOR_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> CHARM_OF_TOUGHNESS_I = register("charm_of_toughness_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_ARMOR_TOUGHNESS_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> CHARM_OF_RESISTANCE_I = register("charm_of_resistance_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_KNOCKBACK_RESISTANCE_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> CHARM_OF_AGILITY_I = register("charm_of_agility_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_MOVEMENT_SPEED_BONUS), () -> new Item.Properties().rarity(Rarity.UNCOMMON));

    // Materials
    public static final DeferredItem<Item> SKYROOT_STICK = register("skyroot_stick");
    public static final DeferredItem<Item> SKYROOT_PINECONE = register("skyroot_pinecone", SkyrootPineconeItem::new);
    public static final DeferredItem<Item> VALKYRIE_WINGS = register("valkyrie_wings");
    public static final DeferredItem<Item> SCATTERGLASS_SHARD = register("scatterglass_shard");
    public static final DeferredItem<Item> AMBROSIUM_SHARD = register("ambrosium_shard", AmbrosiumShardItem::new);
    public static final DeferredItem<Item> FOSSILIZED_ZANITE = register("fossilized_zanite");
    public static final DeferredItem<Item> ZANITE_GEMSTONE = register("zanite_gemstone");
    public static final DeferredItem<Item> INERT_ARKENIUM = register("inert_arkenium");
    public static final DeferredItem<Item> ARKENIUM_PLATE = register("arkenium_plate");
    public static final DeferredItem<Item> ARKENIUM_CHIP = register("arkenium_chip");
    public static final DeferredItem<Item> INERT_GRAVITITE = register("inert_gravitite");
    public static final DeferredItem<Item> GRAVITITE_PLATE = register("gravitite_plate");
    public static final DeferredItem<Item> FOSSILIZED_CORROBONITE = register("fossilized_corrobonite");
    public static final DeferredItem<Item> CORROBONITE_CRYSTAL = register("corrobonite_crystal");
    public static final DeferredItem<Item> NEPTUNE_SCALE = register("neptune_scale", () -> new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> SENTRY_SERVO = register("sentry_servo", () -> new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> RESONANT_STONE = register("resonant_stone", () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> FOSSILIZED_GLINT = register("fossilized_glint");
    public static final DeferredItem<Item> GLINT_GEMSTONE = register("glint_gemstone", (properties) -> new CurrencyItem(10, properties)); //todo
    public static final DeferredItem<Item> GOLDEN_AMBER = register("golden_amber");
    public static final DeferredItem<Item> CLOUDTWINE = register("cloudtwine");
    public static final DeferredItem<Item> BEAST_PELT = register("beast_pelt");
    public static final DeferredItem<Item> BURRUKAI_PLATE = register("burrukai_plate");
    public static final DeferredItem<Item> KIRRID_PLATE = register("kirrid_plate");
    public static final DeferredItem<Item> AECHOR_PETAL = register("aechor_petal");
    public static final DeferredItem<Item> BRETTL_CANE = register("brettl_cane", (properties) -> new BlockItem(AetherIIBlocks.BRETTL_PLANT_TIP.get(), properties.useItemDescriptionPrefix()));
    public static final DeferredItem<Item> BRETTL_GRASS = register("brettl_grass");
    public static final DeferredItem<Item> BRETTL_ROPE = register("brettl_rope");
    public static final DeferredItem<Item> ARILUM_BULBS = register("arilum_bulbs", (properties) -> new BlockItem(AetherIIBlocks.ARILUM_SHOOT.get(), properties.useItemDescriptionPrefix()));
    public static final DeferredItem<Item> ARCTIC_SNOWBALL = register("arctic_snowball", ArcticSnowballItem::new);
    public static final DeferredItem<Item> SWET_GEL = register("swet_gel", SwetGelItem::new);
    public static final DeferredItem<Item> SWET_SUGAR = register("swet_sugar");
    public static final DeferredItem<Item> PRISMALLARD_FEATHER = register("prismallard_feather");
    public static final DeferredItem<Item> MOA_FEATHER = register("moa_feather", MoaFeatherItem::new, () -> new Item.Properties().component(AetherIIDataComponents.FEATHER_COLOR.get(), Moa.FeatherColor.LIGHT_BLUE));
    public static final DeferredItem<Item> COCKATRICE_FEATHER = register("cockatrice_feather");

    public static final DeferredItem<Item> SCATTERGLASS_VIAL = register("scatterglass_vial", VialItem::new, () -> new Item.Properties().stacksTo(8));

    // Treasure
    public static final DeferredItem<Item> ZEPHYR_HUSK = register("zephyr_husk", TreasureItem::new);
    public static final DeferredItem<Item> CHARGE_CATALYST = register("charge_catalyst", TreasureItem::new);
    public static final DeferredItem<Item> ARKENIUM_CORE = register("arkenium_core", TreasureItem::new);
    public static final DeferredItem<Item> GRAVITITE_CORE = register("gravitite_core", TreasureItem::new);
    public static final DeferredItem<Item> EYE_OF_THE_MIMIC = register("eye_of_the_mimic", TreasureItem::new);

    // Irradiated Items
    public static final DeferredItem<Item> IRRADIATED_ARMOR = register("irradiated_armor", () -> new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> IRRADIATED_WEAPON = register("irradiated_weapon", () -> new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> IRRADIATED_TOOL = register("irradiated_tool", () -> new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> IRRADIATED_CHUNK = register("irradiated_chunk", () -> new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> IRRADIATED_DUST = register("irradiated_dust", IrradiatedDustItem::new);

    // Food
    public static final DeferredItem<Item> BLUEBERRY = register("blueberry", () -> new Item.Properties().food(AetherIIFoods.BLUEBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> ENCHANTED_BLUEBERRY = register("enchanted_blueberry", () -> new Item.Properties().food(AetherIIFoods.ENCHANTED_BLUEBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> ORANGE = register("orange", () -> new Item.Properties().food(AetherIIFoods.ORANGE));
    public static final DeferredItem<Item> ENCHANTED_ORANGE = register("enchanted_orange", () -> new Item.Properties().food(AetherIIFoods.ENCHANTED_ORANGE));
    public static final DeferredItem<Item> WYNDBERRY = register("wyndberry", () -> new Item.Properties().food(AetherIIFoods.WYNDBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> ENCHANTED_WYNDBERRY = register("enchanted_wyndberry", () -> new Item.Properties().food(AetherIIFoods.ENCHANTED_WYNDBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> GOLDEN_WYNDBERRY = register("golden_wyndberry");
    public static final DeferredItem<Item> SATIVAL_BULB = register("satival_bulb", () -> new Item.Properties().food(AetherIIFoods.SATIVAL_BULB).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final DeferredItem<Item> SWET_JELLY = register("swet_jelly", () -> new Item.Properties().food(AetherIIFoods.SWET_JELLY));
    public static final DeferredItem<Item> ENCHANTED_SWET_JELLY = register("enchanted_swet_jelly", () -> new Item.Properties().food(AetherIIFoods.ENCHANTED_SWET_JELLY));
    public static final DeferredItem<Item> FRIED_PRISMALLARD_EGG = register("fried_prismallard_egg", () -> new Item.Properties().food(AetherIIFoods.FRIED_PRISMALLARD_EGG));
    public static final DeferredItem<Item> PRISMALLARD_LEG = register("prismallard_leg", () -> new Item.Properties().food(AetherIIFoods.PRISMALLARD_LEG));
    public static final DeferredItem<Item> PRISMALLARD_ROAST = register("prismallard_roast", () -> new Item.Properties().food(AetherIIFoods.PRISMALLARD_ROAST));
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
    public static final DeferredItem<Item> BANDAGE = register("bandage", (properties) -> new SpecialTooltipItem(AetherIITooltips.CURATIVE, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.BANDAGE)));
    public static final DeferredItem<Item> SPLINT = register("splint", (properties) -> new SpecialTooltipItem(AetherIITooltips.CURATIVE, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.SPLINT)));
    public static final DeferredItem<Item> ANTITOXIN_VIAL = register("antitoxin_vial", (properties) -> new SpecialTooltipItem(AetherIITooltips.CURATIVE, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.ANTITOXIN_VIAL).usingConvertsTo(SCATTERGLASS_VIAL.get())));
    public static final DeferredItem<Item> ANTIVENOM_VIAL = register("antivenom_vial", (properties) -> new SpecialTooltipItem(AetherIITooltips.CURATIVE, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.ANTIVENOM_VIAL).usingConvertsTo(SCATTERGLASS_VIAL.get())));
    public static final DeferredItem<Item> VALKYRIE_TEA = register("valkyrie_tea", (properties) -> new SpecialTooltipItem(AetherIITooltips.TEA, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.VALKYRIE_TEA).usingConvertsTo(SCATTERGLASS_VIAL.get())));
    public static final DeferredItem<Item> HEALING_STONE = register("healing_stone", (properties) -> new HealingStoneItem(properties.stacksTo(1).component(AetherIIDataComponents.HEALING_STONE_CHARGES, 0)));

    // Utilities
    public static final DeferredItem<Item> SHIFTING_GLASS = register("shifting_glass", ShiftingGlassItem::new);

    // Companions
    public static final DeferredItem<Item> AERBUNNY_BELL = register("aerbunny_bell", (properties) -> new CompanionItem(AetherIIEntityTypes.AERBUNNY, AetherIISoundEvents.ITEM_AERBUNNY_BELL_RING, properties.rarity(Rarity.UNCOMMON)));

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
    public static final DeferredItem<Item> ARKENIUM_ALKAHEST_CANISTER = register("arkenium_alkahest_canister", (properties) -> new ArkeniumCanisterItem(AetherIIFluids.ALKAHEST.get(), properties.stacksTo(1).craftRemainder(ARKENIUM_CANISTER.get())));
    public static final DeferredItem<Item> ARKENIUM_HESTVEIL_CANISTER = register("arkenium_hestveil_canister", (properties) -> new SolidCanisterItem(AetherIIBlocks.HESTVEIL.get(), SoundEvents.BUCKET_EMPTY_POWDER_SNOW, properties.stacksTo(1).craftRemainder(ARKENIUM_CANISTER.get()))); //todo

    // Music Discs
    public static final DeferredItem<Item> ENGRAVED_DISC_ASCENDING_DAWN = register("engraved_disc_ascending_dawn", () -> new Item.Properties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.ASCENDING_DAWN))));
    public static final DeferredItem<Item> ENGRAVED_DISC_AERWHALE = register("engraved_disc_aerwhale", () -> new Item.Properties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.AERWHALE))));
    public static final DeferredItem<Item> ENGRAVED_DISC_APPROACHES = register("engraved_disc_approaches", () -> new Item.Properties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.APPROACHES))));
    public static final DeferredItem<Item> ENGRAVED_DISC_DEMISE = register("engraved_disc_demise", () -> new Item.Properties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.DEMISE))));
    public static final DeferredItem<Item> ENGRAVED_DISC_CHINCHILLA = register("engraved_disc_chinchilla", () -> new Item.Properties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.CHINCHILLA))));
    public static final DeferredItem<Item> ENGRAVED_DISC_HIGH = register("engraved_disc_high", () -> new Item.Properties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.HIGH))));
    public static final DeferredItem<Item> ENGRAVED_DISC_REVOLUTIONS = register("engraved_disc_revolutions", () -> new Item.Properties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.REVOLUTIONS))));

    // Spawn Eggs
    public static final DeferredItem<SpawnEggItem> FLYING_COW_SPAWN_EGG = register("flying_cow_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.FLYING_COW.get())));
    public static final DeferredItem<SpawnEggItem> SHEEPUFF_SPAWN_EGG = register("sheepuff_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.SHEEPUFF.get())));
    public static final DeferredItem<SpawnEggItem> PHYG_SPAWN_EGG = register("phyg_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.PHYG.get())));
    public static final DeferredItem<SpawnEggItem> AERBUNNY_SPAWN_EGG = register("aerbunny_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.AERBUNNY.get())));
    public static final DeferredItem<SpawnEggItem> AERWHALE_SPAWN_EGG = register("aerwhale_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.AERWHALE.get())));
    public static final DeferredItem<SpawnEggItem> HIGHFIELDS_TAEGORE_SPAWN_EGG = register("highfields_taegore_spawn_egg", (properties) -> new BiomeMobSpawnEggItem("highfields", properties.spawnEgg(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get())));
    public static final DeferredItem<SpawnEggItem> MAGNETIC_TAEGORE_SPAWN_EGG = register("magnetic_taegore_spawn_egg", (properties) -> new BiomeMobSpawnEggItem("magnetic", properties.spawnEgg(AetherIIEntityTypes.MAGNETIC_TAEGORE.get())));
    public static final DeferredItem<SpawnEggItem> ARCTIC_TAEGORE_SPAWN_EGG = register("arctic_taegore_spawn_egg", (properties) -> new BiomeMobSpawnEggItem("arctic", properties.spawnEgg(AetherIIEntityTypes.ARCTIC_TAEGORE.get())));
    public static final DeferredItem<SpawnEggItem> HIGHFIELDS_BURRUKAI_SPAWN_EGG = register("highfields_burrukai_spawn_egg", (properties) -> new BiomeMobSpawnEggItem("highfields", properties.spawnEgg(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get())));
    public static final DeferredItem<SpawnEggItem> MAGNETIC_BURRUKAI_SPAWN_EGG = register("magnetic_burrukai_spawn_egg", (properties) -> new BiomeMobSpawnEggItem("magnetic", properties.spawnEgg(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get())));
    public static final DeferredItem<SpawnEggItem> ARCTIC_BURRUKAI_SPAWN_EGG = register("arctic_burrukai_spawn_egg", (properties) -> new BiomeMobSpawnEggItem("arctic", properties.spawnEgg(AetherIIEntityTypes.ARCTIC_BURRUKAI.get())));
    public static final DeferredItem<SpawnEggItem> HIGHFIELDS_KIRRID_SPAWN_EGG = register("highfields_kirrid_spawn_egg", (properties) -> new BiomeMobSpawnEggItem("highfields", properties.spawnEgg(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get())));
    public static final DeferredItem<SpawnEggItem> MAGNETIC_KIRRID_SPAWN_EGG = register("magnetic_kirrid_spawn_egg", (properties) -> new BiomeMobSpawnEggItem("magnetic", properties.spawnEgg(AetherIIEntityTypes.MAGNETIC_KIRRID.get())));
    public static final DeferredItem<SpawnEggItem> ARCTIC_KIRRID_SPAWN_EGG = register("arctic_kirrid_spawn_egg", (properties) -> new BiomeMobSpawnEggItem("arctic", properties.spawnEgg(AetherIIEntityTypes.ARCTIC_KIRRID.get())));
    public static final DeferredItem<SpawnEggItem> MOA_SPAWN_EGG = register("moa_spawn_egg", (properties) -> new MoaSpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.MOA.get())));
    public static final DeferredItem<SpawnEggItem> PRISMALLARD_SPAWN_EGG = register("prismallard_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.PRISMALLARD.get())));
    public static final DeferredItem<SpawnEggItem> SKYROOT_LIZARD_SPAWN_EGG = register("skyroot_lizard_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.SKYROOT_LIZARD.get())));
    public static final DeferredItem<SpawnEggItem> AECHOR_PLANT_SPAWN_EGG = register("aechor_plant_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.AECHOR_PLANT.get())));
    public static final DeferredItem<SpawnEggItem> CARRION_SPROUT_SPAWN_EGG = register("carrion_sprout_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.CARRION_SPROUT.get())));
    public static final DeferredItem<SpawnEggItem> GLITTERWING_SPAWN_EGG = register("glitterwing_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.GLITTERWING.get())));
    public static final DeferredItem<SpawnEggItem> SHROUDWING_SPAWN_EGG = register("shroudwing_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.SHROUDWING.get())));
    public static final DeferredItem<SpawnEggItem> ZEPHYR_SPAWN_EGG = register("zephyr_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.ZEPHYR.get())));
    public static final DeferredItem<SpawnEggItem> TEMPEST_SPAWN_EGG = register("tempest_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.TEMPEST.get())));
    public static final DeferredItem<SpawnEggItem> COCKATRICE_SPAWN_EGG = register("cockatrice_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.COCKATRICE.get())));
    public static final DeferredItem<SpawnEggItem> BLUE_SWET_SPAWN_EGG = register("blue_swet_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.BLUE_SWET.get())));
    public static final DeferredItem<SpawnEggItem> GOLDEN_SWET_SPAWN_EGG = register("golden_swet_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.GOLDEN_SWET.get())));
    public static final DeferredItem<SpawnEggItem> SKEPHID_SPAWN_EGG = register("skephid_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.SKEPHID.get())));
    public static final DeferredItem<SpawnEggItem> ARKENIUM_TALUTON_SPAWN_EGG = register("arkenium_taluton_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.ARKENIUM_TALUTON.get())));
    public static final DeferredItem<SpawnEggItem> GRAVITITE_TALUTON_SPAWN_EGG = register("gravitite_taluton_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.GRAVITITE_TALUTON.get())));
    public static final DeferredItem<SpawnEggItem> DETONATION_SENTRY_SPAWN_EGG = register("detonation_sentry_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.DETONATION_SENTRY.get())));
    public static final DeferredItem<SpawnEggItem> SENTRY_GOLEM_SPAWN_EGG = register("sentry_golem_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.SENTRY_GOLEM.get())));
    public static final DeferredItem<SpawnEggItem> SENTRY_CRATE_MIMIC_SPAWN_EGG = register("sentry_crate_mimic_spawn_egg", (properties) -> new SpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.MIMIC.get())));
    public static final DeferredItem<SpawnEggItem> SLIDER_SPAWN_EGG = register("slider_spawn_egg", (properties) -> new SliderSpawnEggItem(properties.spawnEgg(AetherIIEntityTypes.SLIDER.get())));

    // Misc
    public static final DeferredItem<Item> MUSIC_PLAYER = register("music_player", MusicPlayerItem::new, () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> BEAST_PELT_BUNDLE = register("beast_pelt_bundle", BundleItem::new, () -> new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY));
    public static final DeferredItem<Item> BRETTL_LASSO = register("lasso", LassoItem::new);
    public static final DeferredItem<Item> PRISMALLARD_EGG = register("prismallard_egg", PrismallardEggItem::new, () -> new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> MOA_EGG = register("moa_egg", MoaEggItem::new, () -> new Item.Properties().component(AetherIIDataComponents.MOA_EGG_TYPE.get(), MoaEggType.defaultType()));
    public static final DeferredItem<Item> MOA_FEED = register("moa_feed", MoaFeedItem::new);
    public static final DeferredItem<Item> BLUEBERRY_MOA_FEED = register("blueberry_moa_feed", MoaFeedItem::new);
    public static final DeferredItem<Item> ENCHANTED_MOA_FEED = register("enchanted_moa_feed", MoaFeedItem::new);
    public static final DeferredItem<Item> MOA_SADDLE = register("moa_saddle", () -> new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, AetherEquippable.moaSaddle()));
    public static final DeferredItem<Item> MOA_SADDLEBAG = register("moa_saddlebag", (properties) -> new MoaSaddlebagItem(5, properties.stacksTo(1)));
    public static final DeferredItem<Item> LARGE_MOA_SADDLEBAG = register("large_moa_saddlebag", (properties) -> new MoaSaddlebagItem(8, properties.stacksTo(1)));
    public static final DeferredItem<Item> CLOUD_SKIFF = register("cloud_skiff", (properties) -> new CloudSkiffItem(AetherIIEntityTypes.CLOUD_SKIFF.get(), properties.stacksTo(1)));
    public static final DeferredItem<Item> GLINT_COIN = register("glint_coin", (properties) -> new CurrencyItem(1, properties));
    public static final DeferredItem<Item> GUIDEBOOK_PAGE = register("guidebook_page", GuidebookPageItem::new, () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> AETHER_PORTAL_FRAME = register("aether_portal_frame", AetherPortalItem::new, () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> MURAL_ITEM = register("mural_item", (properties) -> new MuralItem(AetherIIBlocks.MURAL.get(), properties), () -> new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> BROKEN_ITEM = register("broken_item", BrokenItem::new, () -> new Item.Properties().stacksTo(1).component(AetherIIDataComponents.BROKEN_STACK, new BrokenStack(ItemStack.EMPTY)));

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
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void registerEquipmentAbilities(IEventBus bus) {
        // Armor
        bus.addListener(BeastPeltArmor::updateEntityTargeting);
        bus.addListener(BurrukaiPlateArmor::updatePlayerAttributes);
        bus.addListener(ZaniteArmor::updatePlayerAttributes);
        bus.addListener(ArkeniumArmor::updatePlayerAttributes);
        bus.addListener(ArkeniumArmor::modifyIncomingDamage);
        bus.addListener(GravititeArmor::updatePlayerAttributes);
        bus.addListener(GravititeArmor::playerFall);
        bus.addListener(GravititeArmor::playerUpdate);
        bus.addListener(SentryArmor::playerFall);
        bus.addListener(NeptuneArmor::updatePlayerAttributes);

        // Weapons
        bus.addListener(HammerOfDemolitionItem::disableAttacks);

        // Weapons
        bus.addListener(ZaniteWeapon::updateWeaponAttributes);

        // Tools
        bus.addListener(HolystoneTool::dropAmbrosium);
        bus.addListener(ZaniteTool::updateToolAttributes);
        bus.addListener(TieredShieldItem::updatePlayerAttributes);

        // Charms
        bus.addListener(CharmItem::updateItemAttributes);

        // Accessories
        bus.addListener(ZanitePendantItem::onBlockBreak);

        // Other
        bus.addListener(MusicPlayerItem::entityPostTick);
        bus.addListener(CompanionItem::companionPostTick);
        bus.addListener(CompanionItem::entityChangeDimension);
        bus.addListener(CompanionItem::companionDeath);
        bus.addListener(CompanionItem::playerLoggedOut);
    }

    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        event.modifyMatching((item, data) -> item.getDescriptionId().contains(AetherII.MODID),
                (DataComponentMap.Builder components, HolderLookup.Provider context, Item item) -> components.set(DataComponents.ENCHANTABLE, null));
    }
}