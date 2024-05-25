package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.combat.HammerItem;
import com.aetherteam.aetherii.item.combat.SpearItem;
import com.aetherteam.aetherii.item.combat.arkenium.ArkeniumHammerItem;
import com.aetherteam.aetherii.item.combat.arkenium.ArkeniumSpearItem;
import com.aetherteam.aetherii.item.combat.arkenium.ArkeniumSwordItem;
import com.aetherteam.aetherii.item.combat.gravitite.GravititeHammerItem;
import com.aetherteam.aetherii.item.combat.gravitite.GravititeSpearItem;
import com.aetherteam.aetherii.item.combat.gravitite.GravititeSwordItem;
import com.aetherteam.aetherii.item.combat.holystone.HolystoneHammerItem;
import com.aetherteam.aetherii.item.combat.holystone.HolystoneSpearItem;
import com.aetherteam.aetherii.item.combat.holystone.HolystoneSwordItem;
import com.aetherteam.aetherii.item.combat.skyroot.SkyrootHammerItem;
import com.aetherteam.aetherii.item.combat.skyroot.SkyrootSpearItem;
import com.aetherteam.aetherii.item.combat.skyroot.SkyrootSwordItem;
import com.aetherteam.aetherii.item.combat.zanite.ZaniteHammerItem;
import com.aetherteam.aetherii.item.combat.zanite.ZaniteSpearItem;
import com.aetherteam.aetherii.item.combat.zanite.ZaniteSwordItem;
import com.aetherteam.aetherii.item.miscellaneous.AetherPortalItem;
import com.aetherteam.aetherii.item.tools.skyroot.*;
import com.aetherteam.aetherii.item.tools.holystone.*;
import com.aetherteam.aetherii.item.tools.zanite.*;
import com.aetherteam.aetherii.item.tools.gravitite.*;
import com.aetherteam.aetherii.item.tools.arkenium.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AetherII.MODID);

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

    // Weapons
    public static final DeferredItem<SwordItem> SKYROOT_SWORD = ITEMS.register("skyroot_sword", SkyrootSwordItem::new);
    public static final DeferredItem<HammerItem> SKYROOT_HAMMER = ITEMS.register("skyroot_hammer", SkyrootHammerItem::new);
    public static final DeferredItem<SpearItem> SKYROOT_SPEAR = ITEMS.register("skyroot_spear", SkyrootSpearItem::new);

    public static final DeferredItem<SwordItem> HOLYSTONE_SWORD = ITEMS.register("holystone_sword", HolystoneSwordItem::new);
    public static final DeferredItem<HammerItem> HOLYSTONE_HAMMER = ITEMS.register("holystone_hammer", HolystoneHammerItem::new);
    public static final DeferredItem<SpearItem> HOLYSTONE_SPEAR = ITEMS.register("holystone_spear", HolystoneSpearItem::new);

    public static final DeferredItem<SwordItem> ZANITE_SWORD = ITEMS.register("zanite_sword", ZaniteSwordItem::new);
    public static final DeferredItem<HammerItem> ZANITE_HAMMER = ITEMS.register("zanite_hammer", ZaniteHammerItem::new);
    public static final DeferredItem<SpearItem> ZANITE_SPEAR = ITEMS.register("zanite_spear", ZaniteSpearItem::new);

    public static final DeferredItem<SwordItem> ARKENIUM_SWORD = ITEMS.register("arkenium_sword", ArkeniumSwordItem::new);
    public static final DeferredItem<HammerItem> ARKENIUM_HAMMER = ITEMS.register("arkenium_hammer", ArkeniumHammerItem::new);
    public static final DeferredItem<SpearItem> ARKENIUM_SPEAR = ITEMS.register("arkenium_spear", ArkeniumSpearItem::new);

    public static final DeferredItem<SwordItem> GRAVITITE_SWORD = ITEMS.register("gravitite_sword", GravititeSwordItem::new);
    public static final DeferredItem<HammerItem> GRAVITITE_HAMMER = ITEMS.register("gravitite_hammer", GravititeHammerItem::new);
    public static final DeferredItem<SpearItem> GRAVITITE_SPEAR = ITEMS.register("gravitite_spear", GravititeSpearItem::new);

    // Materials
    public static final DeferredItem<Item> SKYROOT_STICK = ITEMS.register("skyroot_stick", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AMBROSIUM_SHARD = ITEMS.register("ambrosium_shard", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ZANITE_GEMSTONE = ITEMS.register("zanite_gemstone", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_ARKENIUM = ITEMS.register("raw_arkenium", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ARKENIUM_PLATE = ITEMS.register("arkenium_plate", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_GRAVITITE = ITEMS.register("raw_gravitite", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GRAVITITE_PLATE = ITEMS.register("gravitite_plate", () -> new Item(new Item.Properties()));

    // Misc
    public static final DeferredItem<Item> AETHER_PORTAL_FRAME = ITEMS.register("aether_portal_frame", () -> new AetherPortalItem(new Item.Properties().stacksTo(1)));
}