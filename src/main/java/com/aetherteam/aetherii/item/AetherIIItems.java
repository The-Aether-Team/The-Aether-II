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
import com.aetherteam.aetherii.util.RegistryObjectUtil;
import net.minecraft.core.HolderLookup;
import com.aetherteam.aetherii.item.components.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import com.aetherteam.aetherii.item.components.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class AetherIIItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AetherII.MODID);

    public static final ResourceLocation BASE_SLASH_DAMAGE_ID = new ResourceLocation(AetherII.MODID, "base_slash_damage");
    public static final ResourceLocation BASE_IMPACT_DAMAGE_ID = new ResourceLocation(AetherII.MODID, "base_impact_damage");
    public static final ResourceLocation BASE_PIERCE_DAMAGE_ID = new ResourceLocation(AetherII.MODID, "base_pierce_damage");
    public static final ResourceLocation BASE_SLASH_RANGED_DAMAGE_ID = new ResourceLocation(AetherII.MODID, "base_slash_ranged_damage");
    public static final ResourceLocation BASE_IMPACT_RANGED_DAMAGE_ID = new ResourceLocation(AetherII.MODID, "base_impact_ranged_damage");
    public static final ResourceLocation BASE_PIERCE_RANGED_DAMAGE_ID = new ResourceLocation(AetherII.MODID, "base_pierce_ranged_damage");

    public static final Style CURRENCY_NAME_COLOR = Style.EMPTY.withColor(12566527);
    public static final Style TREASURE_NAME_COLOR = Style.EMPTY.withColor(16765952);
    public static final Style UPGRADED_WEAPON_COLOR = Style.EMPTY.withColor(11730876);
    public static final Style WEAPON_TOOLTIP_COLOR = Style.EMPTY.withColor(11393240);

    public static final Rarity AETHER_II_CURRENCY = Rarity.create("AETHER_II_CURRENCY", style -> CURRENCY_NAME_COLOR);
    public static final Rarity AETHER_II_TREASURE = Rarity.create("AETHER_II_TREASURE", style -> TREASURE_NAME_COLOR);
    public static final Rarity AETHER_II_UPGRADED = Rarity.create("AETHER_II_UPGRADED", style -> UPGRADED_WEAPON_COLOR);

    // Tools
    public static final RegistryObject<Item> SKYROOT_PICKAXE = register("skyroot_pickaxe", SkyrootPickaxeItem::new);
    public static final RegistryObject<AxeItem> SKYROOT_AXE = register("skyroot_axe", SkyrootAxeItem::new);
    public static final RegistryObject<ShovelItem> SKYROOT_SHOVEL = register("skyroot_shovel", SkyrootShovelItem::new);
    public static final RegistryObject<HoeItem> SKYROOT_TROWEL = register("skyroot_trowel", SkyrootTrowelItem::new);

    public static final RegistryObject<Item> HOLYSTONE_PICKAXE = register("holystone_pickaxe", HolystonePickaxeItem::new);
    public static final RegistryObject<AxeItem> HOLYSTONE_AXE = register("holystone_axe", HolystoneAxeItem::new);
    public static final RegistryObject<ShovelItem> HOLYSTONE_SHOVEL = register("holystone_shovel", HolystoneShovelItem::new);
    public static final RegistryObject<HoeItem> HOLYSTONE_TROWEL = register("holystone_trowel", HolystoneTrowelItem::new);

    public static final RegistryObject<Item> ZANITE_PICKAXE = register("zanite_pickaxe", ZanitePickaxeItem::new);
    public static final RegistryObject<AxeItem> ZANITE_AXE = register("zanite_axe", ZaniteAxeItem::new);
    public static final RegistryObject<ShovelItem> ZANITE_SHOVEL = register("zanite_shovel", ZaniteShovelItem::new);
    public static final RegistryObject<HoeItem> ZANITE_TROWEL = register("zanite_trowel", ZaniteTrowelItem::new);

    public static final RegistryObject<Item> ARKENIUM_PICKAXE = register("arkenium_pickaxe", ArkeniumPickaxeItem::new);
    public static final RegistryObject<AxeItem> ARKENIUM_AXE = register("arkenium_axe", ArkeniumAxeItem::new);
    public static final RegistryObject<ShovelItem> ARKENIUM_SHOVEL = register("arkenium_shovel", ArkeniumShovelItem::new);
    public static final RegistryObject<HoeItem> ARKENIUM_TROWEL = register("arkenium_trowel", ArkeniumTrowelItem::new);

    public static final RegistryObject<Item> GRAVITITE_PICKAXE = register("gravitite_pickaxe", GravititePickaxeItem::new);
    public static final RegistryObject<AxeItem> GRAVITITE_AXE = register("gravitite_axe", GravititeAxeItem::new);
    public static final RegistryObject<ShovelItem> GRAVITITE_SHOVEL = register("gravitite_shovel", GravititeShovelItem::new);
    public static final RegistryObject<HoeItem> GRAVITITE_TROWEL = register("gravitite_trowel", GravititeTrowelItem::new);

    public static final RegistryObject<Item> ZANITE_SHEARS = register("zanite_shears", ZaniteShearsItem::new, () -> new AetherItemProperties().durability(238));

    // Combat
    public static final RegistryObject<Item> SKYROOT_SHORTSWORD = register("skyroot_shortsword", SkyrootShortswordItem::new);
    public static final RegistryObject<TieredHammerItem> SKYROOT_HAMMER = register("skyroot_hammer", SkyrootHammerItem::new);
    public static final RegistryObject<TieredPikeItem> SKYROOT_PIKE = register("skyroot_pike", SkyrootPikeItem::new);
    public static final RegistryObject<CrossbowItem> SKYROOT_CROSSBOW = register("skyroot_crossbow", SkyrootCrossbowItem::new, () -> new AetherItemProperties().enchantable(1));

    public static final RegistryObject<Item> HOLYSTONE_SHORTSWORD = register("holystone_shortsword", HolystoneShortswordItem::new);
    public static final RegistryObject<TieredHammerItem> HOLYSTONE_HAMMER = register("holystone_hammer", HolystoneHammerItem::new);
    public static final RegistryObject<TieredPikeItem> HOLYSTONE_PIKE = register("holystone_pike", HolystonePikeItem::new);
    public static final RegistryObject<CrossbowItem> HOLYSTONE_CROSSBOW = register("holystone_crossbow", HolystoneCrossbowItem::new, () -> new AetherItemProperties().enchantable(1));

    public static final RegistryObject<Item> ZANITE_SHORTSWORD = register("zanite_shortsword", ZaniteShortswordItem::new);
    public static final RegistryObject<TieredHammerItem> ZANITE_HAMMER = register("zanite_hammer", ZaniteHammerItem::new);
    public static final RegistryObject<TieredPikeItem> ZANITE_PIKE = register("zanite_pike", ZanitePikeItem::new);
    public static final RegistryObject<CrossbowItem> ZANITE_CROSSBOW = register("zanite_crossbow", ZaniteCrossbowItem::new, () -> new AetherItemProperties().enchantable(1));

    public static final RegistryObject<Item> ARKENIUM_SHORTSWORD = register("arkenium_shortsword", ArkeniumShortswordItem::new);
    public static final RegistryObject<TieredHammerItem> ARKENIUM_HAMMER = register("arkenium_hammer", ArkeniumHammerItem::new);
    public static final RegistryObject<TieredPikeItem> ARKENIUM_PIKE = register("arkenium_pike", ArkeniumPikeItem::new);
    public static final RegistryObject<CrossbowItem> ARKENIUM_CROSSBOW = register("arkenium_crossbow", ArkeniumCrossbowItem::new, () -> new AetherItemProperties().enchantable(1));

    public static final RegistryObject<Item> GRAVITITE_SHORTSWORD = register("gravitite_shortsword", GravititeShortswordItem::new);
    public static final RegistryObject<TieredHammerItem> GRAVITITE_HAMMER = register("gravitite_hammer", GravititeHammerItem::new);
    public static final RegistryObject<TieredPikeItem> GRAVITITE_PIKE = register("gravitite_pike", GravititePikeItem::new);
    public static final RegistryObject<CrossbowItem> GRAVITITE_CROSSBOW = register("gravitite_crossbow", GravititeCrossbowItem::new, () -> new AetherItemProperties().enchantable(1));

    public static final RegistryObject<ShieldItem> SKYROOT_SHIELD = register("skyroot_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.SKYROOT, 0.05F, properties));
    public static final RegistryObject<ShieldItem> BURRUKAI_PLATE_SHIELD = register("burrukai_plate_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.HOLYSTONE, 0.1F, properties));
    public static final RegistryObject<ShieldItem> ZANITE_SHIELD = register("zanite_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.ZANITE, 0.2F, properties));
    public static final RegistryObject<ShieldItem> ARKENIUM_SHIELD = register("arkenium_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.ARKENIUM, 0.25F, properties));
    public static final RegistryObject<ShieldItem> GRAVITITE_SHIELD = register("gravitite_shield", (properties) -> new TieredShieldItem(AetherIIToolMaterials.GRAVITITE, 0.4F, properties));

    public static final RegistryObject<Item> DART_SHOOTER = register("dart_shooter", DartShooterItem::new, () -> new AetherItemProperties().durability(100));
    public static final RegistryObject<Item> AMBER_DARTS = register("amber_darts", AmberDartsItem::new, () -> new AetherItemProperties().component(AetherIIDataComponents.BUILDUP_CONTENTS, new BuildupContents(EffectBuildupPresets.VULNERABILITY)).stacksTo(16));

    public static final RegistryObject<Item> SCATTERGLASS_BOLT = register("scatterglass_bolt", ScatterglassBoltItem::new);

    public static final RegistryObject<TieredHammerItem> HAMMER_OF_DEMOLITION = register("hammer_of_demolition", HammerOfDemolitionItem::new, () -> new AetherItemProperties().rarity(Rarity.RARE).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.TWO))));

    // Armor
    public static final RegistryObject<Item> BEAST_PELT_HELMET = register("beast_pelt_helmet", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.BEAST_PELT, ArmorItem.Type.HELMET, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));
    public static final RegistryObject<Item> BEAST_PELT_CHESTPLATE = register("beast_pelt_chestplate", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.BEAST_PELT, ArmorItem.Type.CHESTPLATE, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));
    public static final RegistryObject<Item> BEAST_PELT_LEGGINGS = register("beast_pelt_leggings", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.BEAST_PELT, ArmorItem.Type.LEGGINGS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));
    public static final RegistryObject<Item> BEAST_PELT_BOOTS = register("beast_pelt_boots", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.BEAST_PELT, ArmorItem.Type.BOOTS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));
    public static final RegistryObject<Item> BEAST_PELT_GLOVES = register("beast_pelt_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.BEAST_PELT, 0.1, 0.05, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BEAST_PELT_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BEAST_PELT, AetherIIStyleDesigns.SCOUT, false))));

    public static final RegistryObject<Item> BURRUKAI_PLATE_HELMET = register("burrukai_plate_helmet", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.BURRUKAI_PLATE, ArmorItem.Type.HELMET, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));
    public static final RegistryObject<Item> BURRUKAI_PLATE_CHESTPLATE = register("burrukai_plate_chestplate", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.BURRUKAI_PLATE, ArmorItem.Type.CHESTPLATE, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));
    public static final RegistryObject<Item> BURRUKAI_PLATE_LEGGINGS = register("burrukai_plate_leggings", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.BURRUKAI_PLATE, ArmorItem.Type.LEGGINGS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));
    public static final RegistryObject<Item> BURRUKAI_PLATE_BOOTS = register("burrukai_plate_boots", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.BURRUKAI_PLATE, ArmorItem.Type.BOOTS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));
    public static final RegistryObject<Item> BURRUKAI_PLATE_GLOVES = register("burrukai_plate_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.BURRUKAI_PLATE, 0.1, 0.1, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.BURRUKAI_PLATE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.BURRUKAI_PLATE, AetherIIStyleDesigns.RANGER, false))));

    public static final RegistryObject<Item> ZANITE_HELMET = register("zanite_helmet", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.ZANITE, ArmorItem.Type.HELMET, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));
    public static final RegistryObject<Item> ZANITE_CHESTPLATE = register("zanite_chestplate", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.ZANITE, ArmorItem.Type.CHESTPLATE, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));
    public static final RegistryObject<Item> ZANITE_LEGGINGS = register("zanite_leggings", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.ZANITE, ArmorItem.Type.LEGGINGS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));
    public static final RegistryObject<Item> ZANITE_BOOTS = register("zanite_boots", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.ZANITE, ArmorItem.Type.BOOTS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));
    public static final RegistryObject<Item> ZANITE_GLOVES = register("zanite_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.ZANITE, 0.2, 0.25, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ZANITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ZANITE, AetherIIStyleDesigns.GUARD, false))));

    public static final RegistryObject<Item> ARKENIUM_HELMET = register("arkenium_helmet", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorItem.Type.HELMET, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));
    public static final RegistryObject<Item> ARKENIUM_CHESTPLATE = register("arkenium_chestplate", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorItem.Type.CHESTPLATE, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));
    public static final RegistryObject<Item> ARKENIUM_LEGGINGS = register("arkenium_leggings", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorItem.Type.LEGGINGS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));
    public static final RegistryObject<Item> ARKENIUM_BOOTS = register("arkenium_boots", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.ARKENIUM, ArmorItem.Type.BOOTS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));
    public static final RegistryObject<Item> ARKENIUM_GLOVES = register("arkenium_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.ARKENIUM, 0.3, 0.15, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.ARKENIUM_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.ARKENIUM, AetherIIStyleDesigns.KNIGHT, false))));

    public static final RegistryObject<Item> GRAVITITE_HELMET = register("gravitite_helmet", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorItem.Type.HELMET, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));
    public static final RegistryObject<Item> GRAVITITE_CHESTPLATE = register("gravitite_chestplate", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorItem.Type.CHESTPLATE, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));
    public static final RegistryObject<Item> GRAVITITE_LEGGINGS = register("gravitite_leggings", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorItem.Type.LEGGINGS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));
    public static final RegistryObject<Item> GRAVITITE_BOOTS = register("gravitite_boots", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.GRAVITITE, ArmorItem.Type.BOOTS, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));
    public static final RegistryObject<Item> GRAVITITE_GLOVES = register("gravitite_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.GRAVITITE, 0.4, 0.3, properties.component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.GRAVITITE_ARMOR).component(AetherIIDataComponents.ARMOR_STYLE, new ArmorStyle(AetherIIStyleMaterials.GRAVITITE, AetherIIStyleDesigns.WARRIOR, false))));

    public static final RegistryObject<Item> SENTRY_BOOTS = register("sentry_boots", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.SENTRY, ArmorItem.Type.BOOTS, properties.rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.SENTRY_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));

    public static final RegistryObject<Item> NEPTUNE_HELMET = register("neptune_helmet", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.NEPTUNE, ArmorItem.Type.HELMET, properties.rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));
    public static final RegistryObject<Item> NEPTUNE_CHESTPLATE = register("neptune_chestplate", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.NEPTUNE, ArmorItem.Type.CHESTPLATE, properties.rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));
    public static final RegistryObject<Item> NEPTUNE_LEGGINGS = register("neptune_leggings", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.NEPTUNE, ArmorItem.Type.LEGGINGS, properties.rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));
    public static final RegistryObject<Item> NEPTUNE_BOOTS = register("neptune_boots", (properties) -> new AetherArmorItem(AetherIIArmorMaterials.NEPTUNE, ArmorItem.Type.BOOTS, properties.rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));
    public static final RegistryObject<Item> NEPTUNE_GLOVES = register("neptune_gloves", (properties) -> new GlovesItem(AetherIIArmorMaterials.NEPTUNE, 0.4, 0.3, properties.rarity(Rarity.RARE).component(AetherIIDataComponents.ARMOR_SET, AetherIITags.Items.NEPTUNE_ARMOR).component(AetherIIDataComponents.CHARMS, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO), new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))));

    // Relics
    public static final RegistryObject<Item> KINETIC_THRUSTERS = register("kinetic_thrusters", KineticThrustersItem::new, () -> new AetherItemProperties().rarity(Rarity.RARE));

    // Accessories
    public static final RegistryObject<Item> ZANITE_PENDANT = register("zanite_pendant", ZanitePendantItem::new);
    public static final RegistryObject<Item> ICESTONE_PENDANT = register("icestone_pendant", IcestonePendantItem::new);

    // Charms
    public static final RegistryObject<Item> CHARM_OF_EFFICIENCY_I = register("charm_of_efficiency_1", (properties) -> new CharmItem(properties, Charms.Type.TOOL, Charms.Tier.ONE, AetherIIStats.CHARM_MINING_EFFICIENCY_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> CHARM_OF_REACH_I = register("charm_of_reach_1", (properties) -> new CharmItem(properties, Charms.Type.TOOL, Charms.Tier.ONE, AetherIIStats.CHARM_BLOCK_INTERACTION_RANGE_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));

    public static final RegistryObject<Item> CHARM_OF_DAMAGE_I = register("charm_of_damage_1", (properties) -> new CharmItem(properties, Charms.Type.WEAPON, Charms.Tier.ONE, AetherIIStats.CHARM_DAMAGE_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> CHARM_OF_DEXTERITY_I = register("charm_of_dexterity_1", (properties) -> new CharmItem(properties, Charms.Type.WEAPON, Charms.Tier.ONE, AetherIIStats.CHARM_ATTACK_SPEED_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> CHARM_OF_KNOCKBACK_I = register("charm_of_knockback_1", (properties) -> new CharmItem(properties, Charms.Type.WEAPON, Charms.Tier.ONE, AetherIIStats.CHARM_ATTACK_KNOCKBACK_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));

    public static final RegistryObject<Item> CHARM_OF_HEALTH_I = register("charm_of_health_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_MAX_HEALTH_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> CHARM_OF_DEFENSE_I = register("charm_of_defense_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_ARMOR_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> CHARM_OF_TOUGHNESS_I = register("charm_of_toughness_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_ARMOR_TOUGHNESS_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> CHARM_OF_RESISTANCE_I = register("charm_of_resistance_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_KNOCKBACK_RESISTANCE_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> CHARM_OF_AGILITY_I = register("charm_of_agility_1", (properties) -> new CharmItem(properties, Charms.Type.ARMOR, Charms.Tier.ONE, AetherIIStats.CHARM_MOVEMENT_SPEED_BONUS), () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));

    // Materials
    public static final RegistryObject<Item> SKYROOT_STICK = register("skyroot_stick");
    public static final RegistryObject<Item> SKYROOT_PINECONE = register("skyroot_pinecone", SkyrootPineconeItem::new);
    public static final RegistryObject<Item> VALKYRIE_WINGS = register("valkyrie_wings");
    public static final RegistryObject<Item> SCATTERGLASS_SHARD = register("scatterglass_shard");
    public static final RegistryObject<Item> AMBROSIUM_SHARD = register("ambrosium_shard", AmbrosiumShardItem::new);
    public static final RegistryObject<Item> FOSSILIZED_ZANITE = register("fossilized_zanite");
    public static final RegistryObject<Item> ZANITE_GEMSTONE = register("zanite_gemstone");
    public static final RegistryObject<Item> INERT_ARKENIUM = register("inert_arkenium");
    public static final RegistryObject<Item> ARKENIUM_PLATE = register("arkenium_plate");
    public static final RegistryObject<Item> ARKENIUM_CHIP = register("arkenium_chip");
    public static final RegistryObject<Item> INERT_GRAVITITE = register("inert_gravitite");
    public static final RegistryObject<Item> GRAVITITE_PLATE = register("gravitite_plate");
    public static final RegistryObject<Item> FOSSILIZED_CORROBONITE = register("fossilized_corrobonite");
    public static final RegistryObject<Item> CORROBONITE_CRYSTAL = register("corrobonite_crystal");
    public static final RegistryObject<Item> NEPTUNE_SCALE = register("neptune_scale", () -> new AetherItemProperties().rarity(Rarity.RARE));
    public static final RegistryObject<Item> SENTRY_SERVO = register("sentry_servo", () -> new AetherItemProperties().rarity(Rarity.RARE));
    public static final RegistryObject<Item> RESONANT_STONE = register("resonant_stone", () -> new AetherItemProperties().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> FOSSILIZED_GLINT = register("fossilized_glint");
    public static final RegistryObject<Item> GLINT_GEMSTONE = register("glint_gemstone", (properties) -> new CurrencyItem(10, properties)); //todo
    public static final RegistryObject<Item> GOLDEN_AMBER = register("golden_amber");
    public static final RegistryObject<Item> CLOUDTWINE = register("cloudtwine");
    public static final RegistryObject<Item> BEAST_PELT = register("beast_pelt");
    public static final RegistryObject<Item> BURRUKAI_PLATE = register("burrukai_plate");
    public static final RegistryObject<Item> KIRRID_PLATE = register("kirrid_plate");
    public static final RegistryObject<Item> AECHOR_PETAL = register("aechor_petal");
    public static final RegistryObject<Item> BRETTL_CANE = register("brettl_cane", (properties) -> new BlockItem(AetherIIBlocks.BRETTL_PLANT_TIP.get(), properties.useItemDescriptionPrefix()));
    public static final RegistryObject<Item> BRETTL_GRASS = register("brettl_grass");
    public static final RegistryObject<Item> BRETTL_ROPE = register("brettl_rope");
    public static final RegistryObject<Item> ARILUM_BULBS = register("arilum_bulbs", (properties) -> new BlockItem(AetherIIBlocks.ARILUM_SHOOT.get(), properties.useItemDescriptionPrefix()));
    public static final RegistryObject<Item> ARCTIC_SNOWBALL = register("arctic_snowball", ArcticSnowballItem::new);
    public static final RegistryObject<Item> SWET_GEL = register("swet_gel", SwetGelItem::new);
    public static final RegistryObject<Item> SWET_SUGAR = register("swet_sugar");
    public static final RegistryObject<Item> PRISMALLARD_FEATHER = register("prismallard_feather");
    public static final RegistryObject<Item> MOA_FEATHER = register("moa_feather", MoaFeatherItem::new, () -> new AetherItemProperties().component(AetherIIDataComponents.FEATHER_COLOR.get(), Moa.FeatherColor.LIGHT_BLUE));
    public static final RegistryObject<Item> COCKATRICE_FEATHER = register("cockatrice_feather");

    public static final RegistryObject<Item> SCATTERGLASS_VIAL = register("scatterglass_vial", VialItem::new, () -> new AetherItemProperties().stacksTo(8));

    // Treasure
    public static final RegistryObject<Item> ZEPHYR_HUSK = register("zephyr_husk", TreasureItem::new);
    public static final RegistryObject<Item> CHARGE_CATALYST = register("charge_catalyst", TreasureItem::new);
    public static final RegistryObject<Item> ARKENIUM_CORE = register("arkenium_core", TreasureItem::new);
    public static final RegistryObject<Item> GRAVITITE_CORE = register("gravitite_core", TreasureItem::new);
    public static final RegistryObject<Item> EYE_OF_THE_MIMIC = register("eye_of_the_mimic", TreasureItem::new);

    // Irradiated Items
    public static final RegistryObject<Item> IRRADIATED_ARMOR = register("irradiated_armor", () -> new AetherItemProperties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> IRRADIATED_WEAPON = register("irradiated_weapon", () -> new AetherItemProperties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> IRRADIATED_TOOL = register("irradiated_tool", () -> new AetherItemProperties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> IRRADIATED_CHUNK = register("irradiated_chunk", () -> new AetherItemProperties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final RegistryObject<Item> IRRADIATED_DUST = register("irradiated_dust", IrradiatedDustItem::new);

    // Food
    public static final RegistryObject<Item> BLUEBERRY = register("blueberry", () -> new AetherItemProperties().food(AetherIIFoods.BLUEBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final RegistryObject<Item> ENCHANTED_BLUEBERRY = register("enchanted_blueberry", () -> new AetherItemProperties().food(AetherIIFoods.ENCHANTED_BLUEBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final RegistryObject<Item> ORANGE = register("orange", () -> new AetherItemProperties().food(AetherIIFoods.ORANGE));
    public static final RegistryObject<Item> ENCHANTED_ORANGE = register("enchanted_orange", () -> new AetherItemProperties().food(AetherIIFoods.ENCHANTED_ORANGE));
    public static final RegistryObject<Item> WYNDBERRY = register("wyndberry", () -> new AetherItemProperties().food(AetherIIFoods.WYNDBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final RegistryObject<Item> ENCHANTED_WYNDBERRY = register("enchanted_wyndberry", () -> new AetherItemProperties().food(AetherIIFoods.ENCHANTED_WYNDBERRY).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final RegistryObject<Item> GOLDEN_WYNDBERRY = register("golden_wyndberry");
    public static final RegistryObject<Item> SATIVAL_BULB = register("satival_bulb", () -> new AetherItemProperties().food(AetherIIFoods.SATIVAL_BULB).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final RegistryObject<Item> SWET_JELLY = register("swet_jelly", () -> new AetherItemProperties().food(AetherIIFoods.SWET_JELLY));
    public static final RegistryObject<Item> ENCHANTED_SWET_JELLY = register("enchanted_swet_jelly", () -> new AetherItemProperties().food(AetherIIFoods.ENCHANTED_SWET_JELLY));
    public static final RegistryObject<Item> FRIED_PRISMALLARD_EGG = register("fried_prismallard_egg", () -> new AetherItemProperties().food(AetherIIFoods.FRIED_PRISMALLARD_EGG));
    public static final RegistryObject<Item> PRISMALLARD_LEG = register("prismallard_leg", () -> new AetherItemProperties().food(AetherIIFoods.PRISMALLARD_LEG));
    public static final RegistryObject<Item> PRISMALLARD_ROAST = register("prismallard_roast", () -> new AetherItemProperties().food(AetherIIFoods.PRISMALLARD_ROAST));
    public static final RegistryObject<Item> BURRUKAI_RIB_CUT = register("burrukai_rib_cut", () -> new AetherItemProperties().food(AetherIIFoods.BURRUKAI_RIB_CUT));
    public static final RegistryObject<Item> BURRUKAI_RIBS = register("burrukai_ribs", () -> new AetherItemProperties().food(AetherIIFoods.BURRUKAI_RIBS));
    public static final RegistryObject<Item> KIRRID_LOIN = register("kirrid_loin", () -> new AetherItemProperties().food(AetherIIFoods.KIRRID_LOIN));
    public static final RegistryObject<Item> KIRRID_CUTLET = register("kirrid_cutlet", () -> new AetherItemProperties().food(AetherIIFoods.KIRRID_CUTLET));
    public static final RegistryObject<Item> RAW_TAEGORE_MEAT = register("raw_taegore_meat", () -> new AetherItemProperties().food(AetherIIFoods.RAW_TAEGORE_MEAT));
    public static final RegistryObject<Item> TAEGORE_STEAK = register("taegore_steak", () -> new AetherItemProperties().food(AetherIIFoods.TAEGORE_STEAK));
    public static final RegistryObject<Item> SKYROOT_LIZARD_ON_A_STICK = register("skyroot_lizard_on_a_stick", () -> new AetherItemProperties().food(AetherIIFoods.SKYROOT_LIZARD_ON_A_STICK).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));
    public static final RegistryObject<Item> ROASTED_SKYROOT_LIZARD_ON_A_STICK = register("roasted_skyroot_lizard_on_a_stick", () -> new AetherItemProperties().food(AetherIIFoods.ROASTED_SKYROOT_LIZARD_ON_A_STICK).component(DataComponents.CONSUMABLE, AetherIIConsumables.FAST));

    // Consumables
    public static final RegistryObject<Item> WATER_VIAL = register("water_vial", WaterVialItem::new, () -> new AetherItemProperties().stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.WATER_VIAL).usingConvertsTo(SCATTERGLASS_VIAL.get()));
    public static final RegistryObject<Item> BANDAGE = register("bandage", (properties) -> new SpecialTooltipItem(AetherIITooltips.CURATIVE, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.BANDAGE)));
    public static final RegistryObject<Item> SPLINT = register("splint", (properties) -> new SpecialTooltipItem(AetherIITooltips.CURATIVE, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.SPLINT)));
    public static final RegistryObject<Item> ANTITOXIN_VIAL = register("antitoxin_vial", (properties) -> new SpecialTooltipItem(AetherIITooltips.CURATIVE, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.ANTITOXIN_VIAL).usingConvertsTo(SCATTERGLASS_VIAL.get()), () -> new ItemStack(SCATTERGLASS_VIAL.get())));
    public static final RegistryObject<Item> ANTIVENOM_VIAL = register("antivenom_vial", (properties) -> new SpecialTooltipItem(AetherIITooltips.CURATIVE, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.ANTIVENOM_VIAL).usingConvertsTo(SCATTERGLASS_VIAL.get()), () -> new ItemStack(SCATTERGLASS_VIAL.get())));
    public static final RegistryObject<Item> VALKYRIE_TEA = register("valkyrie_tea", (properties) -> new SpecialTooltipItem(AetherIITooltips.TEA, properties.stacksTo(8).component(DataComponents.CONSUMABLE, AetherIIConsumables.VALKYRIE_TEA).usingConvertsTo(SCATTERGLASS_VIAL.get()), () -> new ItemStack(SCATTERGLASS_VIAL.get())));
    public static final RegistryObject<Item> HEALING_STONE = register("healing_stone", (properties) -> new HealingStoneItem(properties.stacksTo(1).component(AetherIIDataComponents.HEALING_STONE_CHARGES, 0)));

    // Utilities
    public static final RegistryObject<Item> SHIFTING_GLASS = register("shifting_glass", ShiftingGlassItem::new);

    // Companions
    public static final RegistryObject<Item> AERBUNNY_BELL = register("aerbunny_bell", (properties) -> new CompanionItem(AetherIIEntityTypes.AERBUNNY, RegistryObjectUtil.holder(AetherIISoundEvents.ITEM_AERBUNNY_BELL_RING), properties.rarity(Rarity.UNCOMMON)));

    // Gliders
    public static final RegistryObject<Item> COLD_AERCLOUD_GLIDER = register("cold_aercloud_glider", (properties) -> new AercloudGliderItem(properties.durability(5).setNoCombineRepair()));
    public static final RegistryObject<Item> GOLDEN_AERCLOUD_GLIDER = register("golden_aercloud_glider", (properties) -> new GoldenAercloudGliderItem(properties.durability(30).setNoCombineRepair()));
    public static final RegistryObject<Item> BLUE_AERCLOUD_GLIDER = register("blue_aercloud_glider", (properties) -> new BlueAercloudGliderItem(properties.durability(3).setNoCombineRepair()));
    public static final RegistryObject<Item> PURPLE_AERCLOUD_GLIDER = register("purple_aercloud_glider", (properties) -> new PurpleAercloudGliderItem(properties.durability(3).setNoCombineRepair()));

    // Skyroot Buckets
    public static final RegistryObject<Item> SKYROOT_BUCKET = register("skyroot_bucket", (properties) -> new SkyrootBucketItem(Fluids.EMPTY, properties.stacksTo(16)));
    public static final RegistryObject<Item> SKYROOT_WATER_BUCKET = register("skyroot_water_bucket", (properties) -> new SkyrootBucketItem(Fluids.WATER, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final RegistryObject<Item> SKYROOT_MILK_BUCKET = register("skyroot_milk_bucket", SkyrootMilkBucketItem::new, () -> new AetherItemProperties().craftRemainder(SKYROOT_BUCKET.get()).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(SKYROOT_BUCKET.get()).stacksTo(1));
    public static final RegistryObject<Item> SKYROOT_POWDER_SNOW_BUCKET = register("skyroot_powder_snow_bucket", (properties) -> new SkyrootSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final RegistryObject<Item> SKYROOT_COD_BUCKET = register("skyroot_cod_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final RegistryObject<Item> SKYROOT_SALMON_BUCKET = register("skyroot_salmon_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final RegistryObject<Item> SKYROOT_PUFFERFISH_BUCKET = register("skyroot_pufferfish_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final RegistryObject<Item> SKYROOT_TROPICAL_FISH_BUCKET = register("skyroot_tropical_fish_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final RegistryObject<Item> SKYROOT_AXOLOTL_BUCKET = register("skyroot_axolotl_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final RegistryObject<Item> SKYROOT_TADPOLE_BUCKET = register("skyroot_tadpole_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));

    // Arkenium Canisters
    public static final RegistryObject<Item> ARKENIUM_CANISTER = register("arkenium_canister", (properties) -> new ArkeniumCanisterItem(Fluids.EMPTY, properties.stacksTo(16)));
    public static final RegistryObject<Item> ARKENIUM_ALKAHEST_CANISTER = register("arkenium_alkahest_canister", (properties) -> new ArkeniumCanisterItem(AetherIIFluids.ALKAHEST.get(), properties.stacksTo(1).craftRemainder(ARKENIUM_CANISTER.get())));
    public static final RegistryObject<Item> ARKENIUM_HESTVEIL_CANISTER = register("arkenium_hestveil_canister", (properties) -> new SolidCanisterItem(AetherIIBlocks.HESTVEIL.get(), SoundEvents.BUCKET_EMPTY_POWDER_SNOW, properties.stacksTo(1).craftRemainder(ARKENIUM_CANISTER.get()))); //todo

    // Music Discs
    public static final RegistryObject<Item> ENGRAVED_DISC_ASCENDING_DAWN = register("engraved_disc_ascending_dawn", () -> new AetherItemProperties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.ASCENDING_DAWN))));
    public static final RegistryObject<Item> ENGRAVED_DISC_AERWHALE = register("engraved_disc_aerwhale", () -> new AetherItemProperties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.AERWHALE))));
    public static final RegistryObject<Item> ENGRAVED_DISC_APPROACHES = register("engraved_disc_approaches", () -> new AetherItemProperties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.APPROACHES))));
    public static final RegistryObject<Item> ENGRAVED_DISC_DEMISE = register("engraved_disc_demise", () -> new AetherItemProperties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.DEMISE))));
    public static final RegistryObject<Item> ENGRAVED_DISC_CHINCHILLA = register("engraved_disc_chinchilla", () -> new AetherItemProperties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.CHINCHILLA))));
    public static final RegistryObject<Item> ENGRAVED_DISC_HIGH = register("engraved_disc_high", () -> new AetherItemProperties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.HIGH))));
    public static final RegistryObject<Item> ENGRAVED_DISC_REVOLUTIONS = register("engraved_disc_revolutions", () -> new AetherItemProperties().rarity(Rarity.RARE).delayedComponent(AetherIIDataComponents.ENGRAVED_DISC.get(), context -> new EngravedDisc(context.getOrThrow(AetherIIJukeboxSongs.REVOLUTIONS))));

    // Spawn Eggs
    public static final RegistryObject<SpawnEggItem> FLYING_COW_SPAWN_EGG = register("flying_cow_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.FLYING_COW, 0xC2C2C2, 0xFFDD61, properties));
    public static final RegistryObject<SpawnEggItem> SHEEPUFF_SPAWN_EGG = register("sheepuff_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.SHEEPUFF, 0xE8F0F8, 0xA6D4FF, properties));
    public static final RegistryObject<SpawnEggItem> PHYG_SPAWN_EGG = register("phyg_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.PHYG, 0xF7A6B1, 0xFFDD61, properties));
    public static final RegistryObject<SpawnEggItem> AERBUNNY_SPAWN_EGG = register("aerbunny_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.AERBUNNY, 0xE8F7FF, 0xFFD6F9, properties));
    public static final RegistryObject<SpawnEggItem> AERWHALE_SPAWN_EGG = register("aerwhale_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.AERWHALE, 0x8BB7CF, 0xE8FCFF, properties));
    public static final RegistryObject<SpawnEggItem> HIGHFIELDS_TAEGORE_SPAWN_EGG = register("highfields_taegore_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.HIGHFIELDS_TAEGORE, "highfields", 0xB2CCF2, 0xFFDE96, properties));
    public static final RegistryObject<SpawnEggItem> MAGNETIC_TAEGORE_SPAWN_EGG = register("magnetic_taegore_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.MAGNETIC_TAEGORE, "magnetic", 0x9DC2BE, 0xDBAD88, properties));
    public static final RegistryObject<SpawnEggItem> ARCTIC_TAEGORE_SPAWN_EGG = register("arctic_taegore_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.ARCTIC_TAEGORE, "arctic", 0x797D97, 0xDEDEDE, properties));
    public static final RegistryObject<SpawnEggItem> HIGHFIELDS_BURRUKAI_SPAWN_EGG = register("highfields_burrukai_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI, "highfields", 0x4E7EA8, 0x6C7080, properties));
    public static final RegistryObject<SpawnEggItem> MAGNETIC_BURRUKAI_SPAWN_EGG = register("magnetic_burrukai_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.MAGNETIC_BURRUKAI, "magnetic", 0x858071, 0x4C5667, properties));
    public static final RegistryObject<SpawnEggItem> ARCTIC_BURRUKAI_SPAWN_EGG = register("arctic_burrukai_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.ARCTIC_BURRUKAI, "arctic", 0x786491, 0xB5C1E8, properties));
    public static final RegistryObject<SpawnEggItem> HIGHFIELDS_KIRRID_SPAWN_EGG = register("highfields_kirrid_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.HIGHFIELDS_KIRRID, "highfields", 0xADA896, 0xFFD787, properties));
    public static final RegistryObject<SpawnEggItem> MAGNETIC_KIRRID_SPAWN_EGG = register("magnetic_kirrid_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.MAGNETIC_KIRRID, "magnetic", 0x8788AF, 0xB1E0DC, properties));
    public static final RegistryObject<SpawnEggItem> ARCTIC_KIRRID_SPAWN_EGG = register("arctic_kirrid_spawn_egg", (properties) -> new BiomeMobSpawnEggItem(AetherIIEntityTypes.ARCTIC_KIRRID, "arctic", 0xC3C1BE, 0xAD9078, properties));
    public static final RegistryObject<SpawnEggItem> MOA_SPAWN_EGG = register("moa_spawn_egg", (properties) -> new MoaSpawnEggItem(AetherIIEntityTypes.MOA, 0x91B2DB, 0xE8FCFF, properties));
    public static final RegistryObject<SpawnEggItem> PRISMALLARD_SPAWN_EGG = register("prismallard_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.PRISMALLARD, 0x456F9C, 0xF2D08E, properties));
    public static final RegistryObject<SpawnEggItem> SKYROOT_LIZARD_SPAWN_EGG = register("skyroot_lizard_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.SKYROOT_LIZARD, 0x595844, 0xD1F79E, properties));
    public static final RegistryObject<SpawnEggItem> AECHOR_PLANT_SPAWN_EGG = register("aechor_plant_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.AECHOR_PLANT, 0xCF95E2, 0x7477AB, properties));
    public static final RegistryObject<SpawnEggItem> CARRION_SPROUT_SPAWN_EGG = register("carrion_sprout_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.CARRION_SPROUT, 0x725E42, 0xB55D60, properties));
    public static final RegistryObject<SpawnEggItem> GLITTERWING_SPAWN_EGG = register("glitterwing_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.GLITTERWING, 0xDDE9FF, 0xFFD987, properties));
    public static final RegistryObject<SpawnEggItem> SHROUDWING_SPAWN_EGG = register("shroudwing_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.SHROUDWING, 0x56576A, 0xA8B0D8, properties));
    public static final RegistryObject<SpawnEggItem> ZEPHYR_SPAWN_EGG = register("zephyr_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.ZEPHYR, 0xDEE6E7, 0xC4EFFF, properties));
    public static final RegistryObject<SpawnEggItem> TEMPEST_SPAWN_EGG = register("tempest_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.TEMPEST, 0x676A7A, 0xDEEDFF, properties));
    public static final RegistryObject<SpawnEggItem> COCKATRICE_SPAWN_EGG = register("cockatrice_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.COCKATRICE, 0x8363A6, 0xB8FFC3, properties));
    public static final RegistryObject<SpawnEggItem> BLUE_SWET_SPAWN_EGG = register("blue_swet_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.BLUE_SWET, 0xC3E3EF, 0xA2D0CC, properties));
    public static final RegistryObject<SpawnEggItem> GOLDEN_SWET_SPAWN_EGG = register("golden_swet_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.GOLDEN_SWET, 0xFFD87B, 0xFFF0A8, properties));
    public static final RegistryObject<SpawnEggItem> SKEPHID_SPAWN_EGG = register("skephid_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.SKEPHID, 0x7D96AB, 0xF7CC94, properties));
    public static final RegistryObject<SpawnEggItem> ARKENIUM_TALUTON_SPAWN_EGG = register("arkenium_taluton_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.ARKENIUM_TALUTON, 0xA58F76, 0x64C5B2, properties));
    public static final RegistryObject<SpawnEggItem> GRAVITITE_TALUTON_SPAWN_EGG = register("gravitite_taluton_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.GRAVITITE_TALUTON, 0x6D5B8C, 0xC7B7FF, properties));
    public static final RegistryObject<SpawnEggItem> DETONATION_SENTRY_SPAWN_EGG = register("detonation_sentry_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.DETONATION_SENTRY, 0x67727A, 0xE07A47, properties));
    public static final RegistryObject<SpawnEggItem> SENTRY_GOLEM_SPAWN_EGG = register("sentry_golem_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.SENTRY_GOLEM, 0x6C7480, 0xB8C5D1, properties));
    public static final RegistryObject<SpawnEggItem> SENTRY_CRATE_MIMIC_SPAWN_EGG = register("sentry_crate_mimic_spawn_egg", (properties) -> new AetherSpawnEggItem(AetherIIEntityTypes.MIMIC, 0x8D6B42, 0xD7BC82, properties));
    public static final RegistryObject<SpawnEggItem> SLIDER_SPAWN_EGG = register("slider_spawn_egg", (properties) -> new SliderSpawnEggItem(AetherIIEntityTypes.SLIDER, 0x7A7A80, 0xD8D8E2, properties));

    // Misc
    public static final RegistryObject<Item> MUSIC_PLAYER = register("music_player", MusicPlayerItem::new, () -> new AetherItemProperties().stacksTo(1));
    public static final RegistryObject<Item> BEAST_PELT_BUNDLE = register("beast_pelt_bundle", BeastPeltBundleItem::new, () -> new AetherItemProperties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY));
    public static final RegistryObject<Item> BRETTL_LASSO = register("lasso", LassoItem::new);
    public static final RegistryObject<Item> PRISMALLARD_EGG = register("prismallard_egg", PrismallardEggItem::new, () -> new AetherItemProperties().stacksTo(16));
    public static final RegistryObject<Item> MOA_EGG = register("moa_egg", MoaEggItem::new, () -> new AetherItemProperties().component(AetherIIDataComponents.MOA_EGG_TYPE.get(), MoaEggType.defaultType()));
    public static final RegistryObject<Item> MOA_FEED = register("moa_feed", MoaFeedItem::new);
    public static final RegistryObject<Item> BLUEBERRY_MOA_FEED = register("blueberry_moa_feed", MoaFeedItem::new);
    public static final RegistryObject<Item> ENCHANTED_MOA_FEED = register("enchanted_moa_feed", MoaFeedItem::new);
    public static final RegistryObject<Item> MOA_SADDLE = register("moa_saddle", () -> new AetherItemProperties().stacksTo(1));
    public static final RegistryObject<Item> MOA_SADDLEBAG = register("moa_saddlebag", (properties) -> new MoaSaddlebagItem(5, properties.stacksTo(1)));
    public static final RegistryObject<Item> LARGE_MOA_SADDLEBAG = register("large_moa_saddlebag", (properties) -> new MoaSaddlebagItem(8, properties.stacksTo(1)));
    public static final RegistryObject<Item> CLOUD_SKIFF = register("cloud_skiff", (properties) -> new CloudSkiffItem(AetherIIEntityTypes.CLOUD_SKIFF, properties.stacksTo(1)));
    public static final RegistryObject<Item> GLINT_COIN = register("glint_coin", (properties) -> new CurrencyItem(1, properties));
    public static final RegistryObject<Item> GUIDEBOOK_PAGE = register("guidebook_page", GuidebookPageItem::new, () -> new AetherItemProperties().stacksTo(1));
    public static final RegistryObject<Item> AETHER_PORTAL_FRAME = register("aether_portal_frame", AetherPortalItem::new, () -> new AetherItemProperties().stacksTo(1));
    public static final RegistryObject<Item> MURAL_ITEM = register("mural_item", (properties) -> new MuralItem(AetherIIBlocks.MURAL.get(), properties), () -> new AetherItemProperties().stacksTo(1));

    public static final RegistryObject<Item> BROKEN_ITEM = register("broken_item", BrokenItem::new, () -> new AetherItemProperties().stacksTo(1).component(AetherIIDataComponents.BROKEN_STACK, new BrokenStack(ItemStack.EMPTY)));

    private static <T extends Item> RegistryObject<Item> register(String name) {
        return register(name, Item::new);
    }

    private static <T extends Item> RegistryObject<T> register(String name, Function<AetherItemProperties, T> builder) {
        return baseRegister(name, createKey(name), builder, AetherItemProperties::new);
    }

    private static <T extends Item> RegistryObject<Item> register(String name, Supplier<AetherItemProperties> properties) {
        return register(name, Item::new, properties);
    }
    
    private static <T extends Item> RegistryObject<T> register(String name, Function<AetherItemProperties, T> builder, Supplier<AetherItemProperties> properties) {
        return baseRegister(name, createKey(name), builder, properties);
    }
    
    private static <T extends Item> RegistryObject<T> baseRegister(String name, ResourceKey<Item> key, Function<AetherItemProperties, T> builder, Supplier<AetherItemProperties> properties) {
        return ITEMS.register(name, () -> {
            AetherItemProperties itemProperties = properties.get();
            T item = builder.apply(itemProperties);
            AetherIIDataComponents.registerDefaults(item, itemProperties.defaultComponents());
            return item;
        });
    }
    
    private static ResourceKey<Item> createKey(String name) {
        return ResourceKey.create(Registries.ITEM, new ResourceLocation(AetherII.MODID, name));
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
}
