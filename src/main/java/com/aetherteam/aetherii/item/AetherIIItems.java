package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherII;
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

    // Materials
    public static final DeferredItem<Item> SKYROOT_STICK = ITEMS.register("skyroot_stick", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AMBROSIUM_SHARD = ITEMS.register("ambrosium_shard", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ZANITE_GEMSTONE = ITEMS.register("zanite_gemstone", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_ARKENIUM = ITEMS.register("raw_arkenium", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ARKENIUM_PLATE = ITEMS.register("arkenium_plate", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_GRAVITITE = ITEMS.register("raw_gravitite", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GRAVITITE_PLATE = ITEMS.register("gravitite_plate", () -> new Item(new Item.Properties()));
}
