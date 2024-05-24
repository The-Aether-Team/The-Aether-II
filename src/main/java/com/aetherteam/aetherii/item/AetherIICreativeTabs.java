package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIICreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AetherII.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_BUILDING_BLOCKS = CREATIVE_MODE_TABS.register("building_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "dungeon_blocks"))
            .icon(() -> new ItemStack(AetherIIBlocks.SKYROOT_PLANKS.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".building_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.SKYROOT_LOG.get());
                output.accept(AetherIIBlocks.SKYROOT_WOOD.get());
                output.accept(AetherIIBlocks.SKYROOT_PLANKS.get());
                output.accept(AetherIIBlocks.SKYROOT_STAIRS.get());
                output.accept(AetherIIBlocks.SKYROOT_SLAB.get());
                output.accept(AetherIIBlocks.SKYROOT_FENCE.get());
                output.accept(AetherIIBlocks.SKYROOT_FENCE_GATE.get());
                output.accept(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get());
                output.accept(AetherIIBlocks.SKYROOT_BUTTON.get());
                output.accept(AetherIIBlocks.GREATROOT_LOG.get());
                output.accept(AetherIIBlocks.GREATROOT_WOOD.get());
                output.accept(AetherIIBlocks.GREATROOT_PLANKS.get());
                output.accept(AetherIIBlocks.GREATROOT_STAIRS.get());
                output.accept(AetherIIBlocks.GREATROOT_SLAB.get());
                output.accept(AetherIIBlocks.GREATROOT_FENCE.get());
                output.accept(AetherIIBlocks.GREATROOT_FENCE_GATE.get());
                output.accept(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get());
                output.accept(AetherIIBlocks.WISPROOT_LOG.get());
                output.accept(AetherIIBlocks.WISPROOT_WOOD.get());
                output.accept(AetherIIBlocks.WISPROOT_PLANKS.get());
                output.accept(AetherIIBlocks.WISPROOT_STAIRS.get());
                output.accept(AetherIIBlocks.WISPROOT_SLAB.get());
                output.accept(AetherIIBlocks.WISPROOT_FENCE.get());
                output.accept(AetherIIBlocks.WISPROOT_FENCE_GATE.get());
                output.accept(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get());
                output.accept(AetherIIBlocks.AMBEROOT_LOG.get());
                output.accept(AetherIIBlocks.AMBEROOT_WOOD.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BRICKS.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_DUNGEON_BLOCKS = CREATIVE_MODE_TABS.register("dungeon_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "building_blocks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "natural_blocks"))
            .icon(() -> new ItemStack(Blocks.STONE))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".dungeon_blocks"))
            .displayItems((features, output) -> {

            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_NATURAL_BLOCKS = CREATIVE_MODE_TABS.register("natural_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "dungeon_blocks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "functional_blocks"))
            .icon(() -> new ItemStack(AetherIIBlocks.AETHER_GRASS_BLOCK.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".natural_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
                output.accept(AetherIIBlocks.AETHER_DIRT.get());
                output.accept(AetherIIBlocks.QUICKSOIL.get());
                output.accept(AetherIIBlocks.HOLYSTONE.get());
                output.accept(AetherIIBlocks.UNDERSHALE.get());
                output.accept(AetherIIBlocks.AMBROSIUM_ORE.get());
                output.accept(AetherIIBlocks.ZANITE_ORE.get());
                output.accept(AetherIIBlocks.ARKENIUM_ORE.get());
                output.accept(AetherIIBlocks.GRAVITITE_ORE.get());
                output.accept(AetherIIBlocks.COLD_AERCLOUD.get());
                output.accept(AetherIIBlocks.SKYROOT_LOG.get());
                output.accept(AetherIIBlocks.GREATROOT_LOG.get());
                output.accept(AetherIIBlocks.WISPROOT_LOG.get());
                output.accept(AetherIIBlocks.AMBEROOT_LOG.get());
                output.accept(AetherIIBlocks.SKYROOT_LEAVES.get());
                output.accept(AetherIIBlocks.SKYPLANE_LEAVES.get());
                output.accept(AetherIIBlocks.SKYBIRCH_LEAVES.get());
                output.accept(AetherIIBlocks.SKYPINE_LEAVES.get());
                output.accept(AetherIIBlocks.WISPROOT_LEAVES.get());
                output.accept(AetherIIBlocks.WISPTOP_LEAVES.get());
                output.accept(AetherIIBlocks.GREATROOT_LEAVES.get());
                output.accept(AetherIIBlocks.GREATOAK_LEAVES.get());
                output.accept(AetherIIBlocks.GREATBOA_LEAVES.get());
                output.accept(AetherIIBlocks.AMBEROOT_LEAVES.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_FUNCTIONAL_BLOCKS = CREATIVE_MODE_TABS.register("functional_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "natural_blocks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "redstone_blocks"))
            .icon(() -> new ItemStack(Blocks.STONE))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".functional_blocks"))
            .displayItems((features, output) -> {

            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_REDSTONE_BLOCKS = CREATIVE_MODE_TABS.register("redstone_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "functional_blocks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "tools_and_utilities"))
            .icon(() -> new ItemStack(Blocks.STONE))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".redstone_blocks"))
            .displayItems((features, output) -> {

            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_EQUIPMENT_AND_UTILITIES = CREATIVE_MODE_TABS.register("equipment_and_utilities", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "redstone_blocks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "armor_and_accessories"))
            .icon(() -> new ItemStack(AetherIIItems.GRAVITITE_PICKAXE.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".equipment_and_utilities"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.SKYROOT_SHOVEL.get());
                output.accept(AetherIIItems.SKYROOT_PICKAXE.get());
                output.accept(AetherIIItems.SKYROOT_AXE.get());
                output.accept(AetherIIItems.SKYROOT_TROWEL.get());
                output.accept(AetherIIItems.HOLYSTONE_SHOVEL.get());
                output.accept(AetherIIItems.HOLYSTONE_PICKAXE.get());
                output.accept(AetherIIItems.HOLYSTONE_AXE.get());
                output.accept(AetherIIItems.HOLYSTONE_TROWEL.get());
                output.accept(AetherIIItems.ZANITE_SHOVEL.get());
                output.accept(AetherIIItems.ZANITE_PICKAXE.get());
                output.accept(AetherIIItems.ZANITE_AXE.get());
                output.accept(AetherIIItems.ZANITE_TROWEL.get());
                output.accept(AetherIIItems.ARKENIUM_SHOVEL.get());
                output.accept(AetherIIItems.ARKENIUM_PICKAXE.get());
                output.accept(AetherIIItems.ARKENIUM_AXE.get());
                output.accept(AetherIIItems.ARKENIUM_TROWEL.get());
                output.accept(AetherIIItems.GRAVITITE_SHOVEL.get());
                output.accept(AetherIIItems.GRAVITITE_PICKAXE.get());
                output.accept(AetherIIItems.GRAVITITE_AXE.get());
                output.accept(AetherIIItems.GRAVITITE_TROWEL.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_ARMOR_AND_ACCESSORIES = CREATIVE_MODE_TABS.register("armor_and_accessories", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "equipment_and_utilities"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "food_and_drinks"))
            .icon(() -> new ItemStack(Blocks.STONE))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".armor_and_accessories"))
            .displayItems((features, output) -> {

            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_FOOD_AND_DRINKS = CREATIVE_MODE_TABS.register("food_and_drinks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "combat"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "ingredients"))
            .icon(() -> new ItemStack(Blocks.STONE))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".food_and_drinks"))
            .displayItems((features, output) -> {

            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_INGREDIENTS = CREATIVE_MODE_TABS.register("ingredients", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "food_and_drinks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "spawn_eggs"))
            .icon(() -> new ItemStack(AetherIIItems.RAW_ARKENIUM.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".ingredients"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.SKYROOT_STICK.get());
                output.accept(AetherIIItems.AMBROSIUM_SHARD.get());
                output.accept(AetherIIItems.ZANITE_GEMSTONE.get());
                output.accept(AetherIIItems.RAW_ARKENIUM.get());
                output.accept(AetherIIItems.ARKENIUM_PLATE.get());
                output.accept(AetherIIItems.RAW_GRAVITITE.get());
                output.accept(AetherIIItems.GRAVITITE_PLATE.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_SPAWN_EGGS = CREATIVE_MODE_TABS.register("spawn_eggs", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "ingredients"))
            .icon(() -> new ItemStack(Blocks.STONE))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".spawn_eggs"))
            .displayItems((features, output) -> {

            }).build());
}
