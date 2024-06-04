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

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_BUILDING_BLOCKS = CREATIVE_MODE_TABS.register("building_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "dungeon_blocks"))
            .icon(() -> new ItemStack(AetherIIBlocks.HOLYSTONE_BRICKS.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".building_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.SKYROOT_LOG.get());
                output.accept(AetherIIBlocks.SKYROOT_WOOD.get());
                output.accept(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
                output.accept(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get());
                output.accept(AetherIIBlocks.SKYROOT_PLANKS.get());
                output.accept(AetherIIBlocks.SKYROOT_STAIRS.get());
                output.accept(AetherIIBlocks.SKYROOT_SLAB.get());
                output.accept(AetherIIBlocks.SKYROOT_FENCE.get());
                output.accept(AetherIIBlocks.SKYROOT_FENCE_GATE.get());
                output.accept(AetherIIBlocks.SKYROOT_DOOR.get());
                output.accept(AetherIIBlocks.SKYROOT_TRAPDOOR.get());
                output.accept(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get());
                output.accept(AetherIIBlocks.SKYROOT_BUTTON.get());
                output.accept(AetherIIBlocks.SKYROOT_FLOORBOARDS.get());
                output.accept(AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
                output.accept(AetherIIBlocks.SKYROOT_SHINGLES.get());
                output.accept(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get());
                output.accept(AetherIIBlocks.SKYROOT_BASE_PLANKS.get());
                output.accept(AetherIIBlocks.SKYROOT_TOP_PLANKS.get());
                output.accept(AetherIIBlocks.SKYROOT_BASE_BEAM.get());
                output.accept(AetherIIBlocks.SKYROOT_TOP_BEAM.get());
                output.accept(AetherIIBlocks.SKYROOT_BEAM.get());
                output.accept(AetherIIBlocks.GREATROOT_LOG.get());
                output.accept(AetherIIBlocks.GREATROOT_WOOD.get());
                output.accept(AetherIIBlocks.GREATROOT_PLANKS.get());
                output.accept(AetherIIBlocks.GREATROOT_STAIRS.get());
                output.accept(AetherIIBlocks.GREATROOT_SLAB.get());
                output.accept(AetherIIBlocks.GREATROOT_FENCE.get());
                output.accept(AetherIIBlocks.GREATROOT_FENCE_GATE.get());
                output.accept(AetherIIBlocks.GREATROOT_DOOR.get());
                output.accept(AetherIIBlocks.GREATROOT_TRAPDOOR.get());
                output.accept(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get());
                output.accept(AetherIIBlocks.GREATROOT_BUTTON.get());
                output.accept(AetherIIBlocks.GREATROOT_FLOORBOARDS.get());
                output.accept(AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
                output.accept(AetherIIBlocks.GREATROOT_SHINGLES.get());
                output.accept(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get());
                output.accept(AetherIIBlocks.GREATROOT_BASE_PLANKS.get());
                output.accept(AetherIIBlocks.GREATROOT_TOP_PLANKS.get());
                output.accept(AetherIIBlocks.GREATROOT_BASE_BEAM.get());
                output.accept(AetherIIBlocks.GREATROOT_TOP_BEAM.get());
                output.accept(AetherIIBlocks.GREATROOT_BEAM.get());
                output.accept(AetherIIBlocks.WISPROOT_LOG.get());
                output.accept(AetherIIBlocks.MOSSY_WISPROOT_LOG.get());
                output.accept(AetherIIBlocks.WISPROOT_WOOD.get());
                output.accept(AetherIIBlocks.WISPROOT_PLANKS.get());
                output.accept(AetherIIBlocks.WISPROOT_STAIRS.get());
                output.accept(AetherIIBlocks.WISPROOT_SLAB.get());
                output.accept(AetherIIBlocks.WISPROOT_FENCE.get());
                output.accept(AetherIIBlocks.WISPROOT_FENCE_GATE.get());
                output.accept(AetherIIBlocks.WISPROOT_DOOR.get());
                output.accept(AetherIIBlocks.WISPROOT_TRAPDOOR.get());
                output.accept(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get());
                output.accept(AetherIIBlocks.WISPROOT_BUTTON.get());
                output.accept(AetherIIBlocks.WISPROOT_FLOORBOARDS.get());
                output.accept(AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
                output.accept(AetherIIBlocks.WISPROOT_SHINGLES.get());
                output.accept(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get());
                output.accept(AetherIIBlocks.WISPROOT_BASE_PLANKS.get());
                output.accept(AetherIIBlocks.WISPROOT_TOP_PLANKS.get());
                output.accept(AetherIIBlocks.WISPROOT_BASE_BEAM.get());
                output.accept(AetherIIBlocks.WISPROOT_TOP_BEAM.get());
                output.accept(AetherIIBlocks.WISPROOT_BEAM.get());
                output.accept(AetherIIBlocks.AMBEROOT_LOG.get());
                output.accept(AetherIIBlocks.AMBEROOT_WOOD.get());
                output.accept(AetherIIBlocks.HOLYSTONE.get());
                output.accept(AetherIIBlocks.HOLYSTONE_STAIRS.get());
                output.accept(AetherIIBlocks.HOLYSTONE_SLAB.get());
                output.accept(AetherIIBlocks.HOLYSTONE_WALL.get());
                output.accept(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BUTTON.get());
                output.accept(AetherIIBlocks.MOSSY_HOLYSTONE.get());
                output.accept(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get());
                output.accept(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get());
                output.accept(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get());
                output.accept(AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
                output.accept(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get());
                output.accept(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get());
                output.accept(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get());
                output.accept(AetherIIBlocks.HOLYSTONE_FLAGSTONES.get());
                output.accept(AetherIIBlocks.HOLYSTONE_HEADSTONE.get());
                output.accept(AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get());
                output.accept(AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get());
                output.accept(AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.HOLYSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.UNDERSHALE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_STAIRS.get());
                output.accept(AetherIIBlocks.UNDERSHALE_SLAB.get());
                output.accept(AetherIIBlocks.UNDERSHALE_WALL.get());
                output.accept(AetherIIBlocks.AGIOSITE.get());
                output.accept(AetherIIBlocks.AGIOSITE_STAIRS.get());
                output.accept(AetherIIBlocks.AGIOSITE_SLAB.get());
                output.accept(AetherIIBlocks.AGIOSITE_WALL.get());
                output.accept(AetherIIBlocks.AGIOSITE_BRICKS.get());
                output.accept(AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get());
                output.accept(AetherIIBlocks.AGIOSITE_BRICK_SLAB.get());
                output.accept(AetherIIBlocks.AGIOSITE_BRICK_WALL.get());
                output.accept(AetherIIBlocks.AGIOSITE_FLAGSTONES.get());
                output.accept(AetherIIBlocks.AGIOSITE_KEYSTONE.get());
                output.accept(AetherIIBlocks.AGIOSITE_BASE_BRICKS.get());
                output.accept(AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.AGIOSITE_BASE_PILLAR.get());
                output.accept(AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.AGIOSITE_PILLAR.get());
                output.accept(AetherIIBlocks.ICESTONE.get());
                output.accept(AetherIIBlocks.ICESTONE_STAIRS.get());
                output.accept(AetherIIBlocks.ICESTONE_SLAB.get());
                output.accept(AetherIIBlocks.ICESTONE_WALL.get());
                output.accept(AetherIIBlocks.QUICKSOIL_GLASS.get());
                output.accept(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());
                output.accept(AetherIIBlocks.CRUDE_SCATTERGLASS.get());
                output.accept(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get());
                output.accept(AetherIIBlocks.SCATTERGLASS.get());
                output.accept(AetherIIBlocks.SCATTERGLASS_PANE.get());
                output.accept(AetherIIBlocks.CLOUDWOOL.get());
                output.accept(AetherIIBlocks.CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.AMBROSIUM_BLOCK.get());
                output.accept(AetherIIBlocks.ZANITE_BLOCK.get());
                output.accept(AetherIIBlocks.GRAVITITE_BLOCK.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_DUNGEON_BLOCKS = CREATIVE_MODE_TABS.register("dungeon_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "building_blocks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "natural_blocks"))
            .icon(() -> new ItemStack(Blocks.STONE))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".dungeon_blocks"))
            .displayItems((features, output) -> {

            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_NATURAL_BLOCKS = CREATIVE_MODE_TABS.register("natural_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "dungeon_blocks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "functional_blocks"))
            .icon(() -> new ItemStack(AetherIIBlocks.AETHER_GRASS_BLOCK.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".natural_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
                output.accept(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
                output.accept(AetherIIBlocks.AETHER_DIRT_PATH.get());
                output.accept(AetherIIBlocks.AETHER_DIRT.get());
                output.accept(AetherIIBlocks.COARSE_AETHER_DIRT.get());
                output.accept(AetherIIBlocks.AETHER_FARMLAND.get());
                output.accept(AetherIIBlocks.QUICKSOIL.get());
                output.accept(AetherIIBlocks.FERROSITE_SAND.get());
                output.accept(AetherIIBlocks.ARCTIC_ICE.get());
                output.accept(AetherIIBlocks.ARCTIC_PACKED_ICE.get());
                output.accept(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get());
                output.accept(AetherIIBlocks.ARCTIC_SNOW.get());
                output.accept(AetherIIBlocks.HOLYSTONE.get());
                output.accept(AetherIIBlocks.MOSSY_HOLYSTONE.get());
                output.accept(AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
                output.accept(AetherIIBlocks.UNDERSHALE.get());
                output.accept(AetherIIBlocks.AGIOSITE.get());
                output.accept(AetherIIBlocks.FERROSITE.get());
                output.accept(AetherIIBlocks.RUSTED_FERROSITE.get());
                output.accept(AetherIIBlocks.ICESTONE.get());
                output.accept(AetherIIBlocks.CRUDE_SCATTERGLASS.get());
                output.accept(AetherIIBlocks.AMBROSIUM_ORE.get());
                output.accept(AetherIIBlocks.ZANITE_ORE.get());
                output.accept(AetherIIBlocks.ARKENIUM_ORE.get());
                output.accept(AetherIIBlocks.GRAVITITE_ORE.get());
                output.accept(AetherIIBlocks.COLD_AERCLOUD.get());
                output.accept(AetherIIBlocks.BLUE_AERCLOUD.get());
                output.accept(AetherIIBlocks.GOLDEN_AERCLOUD.get());
                output.accept(AetherIIBlocks.GREEN_AERCLOUD.get());
                output.accept(AetherIIBlocks.PURPLE_AERCLOUD.get());
                output.accept(AetherIIBlocks.STORM_AERCLOUD.get());
                output.accept(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());
                output.accept(AetherIIBlocks.SKYROOT_LOG.get());
                output.accept(AetherIIBlocks.GREATROOT_LOG.get());
                output.accept(AetherIIBlocks.WISPROOT_LOG.get());
                output.accept(AetherIIBlocks.MOSSY_WISPROOT_LOG.get());
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
                output.accept(AetherIIBlocks.SKYROOT_LEAF_PILE.get());
                output.accept(AetherIIBlocks.SKYPLANE_LEAF_PILE.get());
                output.accept(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get());
                output.accept(AetherIIBlocks.SKYPINE_LEAF_PILE.get());
                output.accept(AetherIIBlocks.WISPROOT_LEAF_PILE.get());
                output.accept(AetherIIBlocks.WISPTOP_LEAF_PILE.get());
                output.accept(AetherIIBlocks.GREATROOT_LEAF_PILE.get());
                output.accept(AetherIIBlocks.GREATOAK_LEAF_PILE.get());
                output.accept(AetherIIBlocks.GREATBOA_LEAF_PILE.get());
                output.accept(AetherIIBlocks.AMBEROOT_LEAF_PILE.get());
                output.accept(AetherIIBlocks.SKYROOT_SAPLING.get());
                output.accept(AetherIIBlocks.WISPROOT_SAPLING.get());
                output.accept(AetherIIBlocks.WISPTOP_SAPLING.get());
                output.accept(AetherIIBlocks.GREATROOT_SAPLING.get());
                output.accept(AetherIIBlocks.GREATOAK_SAPLING.get());
                output.accept(AetherIIBlocks.GREATBOA_SAPLING.get());
                output.accept(AetherIIBlocks.AMBEROOT_SAPLING.get());
                output.accept(AetherIIBlocks.AETHER_SHORT_GRASS.get());
                output.accept(AetherIIBlocks.AETHER_MEDIUM_GRASS.get());
                output.accept(AetherIIBlocks.AETHER_LONG_GRASS.get());
                output.accept(AetherIIBlocks.HIGHLANDS_BUSH.get());
                output.accept(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get());
                output.accept(AetherIIBlocks.BLUEBERRY_BUSH.get());
                output.accept(AetherIIBlocks.ORANGE_TREE.get());
                output.accept(AetherIIBlocks.SKYROOT_TWIG.get());
                output.accept(AetherIIBlocks.HOLYSTONE_ROCK.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_FUNCTIONAL_BLOCKS = CREATIVE_MODE_TABS.register("functional_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "natural_blocks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "equipment_and_utilities"))
            .icon(() -> new ItemStack(AetherIIBlocks.ARTISANRY_BENCH.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".functional_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.AMBROSIUM_TORCH.get());
                output.accept(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
                output.accept(AetherIIBlocks.HOLYSTONE_FURNACE.get());
                output.accept(AetherIIBlocks.ARTISANRY_BENCH.get());
                output.accept(AetherIIBlocks.SKYROOT_LADDER.get());
                output.accept(AetherIIBlocks.SKYROOT_BOOKSHELF.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get());
                output.accept(AetherIIBlocks.SKYROOT_CHEST.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_EQUIPMENT_AND_UTILITIES = CREATIVE_MODE_TABS.register("equipment_and_utilities", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "functional_blocks"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "armor_and_accessories"))
            .icon(() -> new ItemStack(AetherIIItems.GRAVITITE_PICKAXE.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".equipment_and_utilities"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.SKYROOT_SHORTSWORD.get());
                output.accept(AetherIIItems.SKYROOT_HAMMER.get());
                output.accept(AetherIIItems.SKYROOT_SPEAR.get());
                output.accept(AetherIIItems.SKYROOT_SHOVEL.get());
                output.accept(AetherIIItems.SKYROOT_PICKAXE.get());
                output.accept(AetherIIItems.SKYROOT_AXE.get());
                output.accept(AetherIIItems.SKYROOT_TROWEL.get());
                output.accept(AetherIIItems.HOLYSTONE_SHORTSWORD.get());
                output.accept(AetherIIItems.HOLYSTONE_HAMMER.get());
                output.accept(AetherIIItems.HOLYSTONE_SPEAR.get());
                output.accept(AetherIIItems.HOLYSTONE_SHOVEL.get());
                output.accept(AetherIIItems.HOLYSTONE_PICKAXE.get());
                output.accept(AetherIIItems.HOLYSTONE_AXE.get());
                output.accept(AetherIIItems.HOLYSTONE_TROWEL.get());
                output.accept(AetherIIItems.ZANITE_SHORTSWORD.get());
                output.accept(AetherIIItems.ZANITE_HAMMER.get());
                output.accept(AetherIIItems.ZANITE_SPEAR.get());
                output.accept(AetherIIItems.ZANITE_SHOVEL.get());
                output.accept(AetherIIItems.ZANITE_PICKAXE.get());
                output.accept(AetherIIItems.ZANITE_AXE.get());
                output.accept(AetherIIItems.ZANITE_TROWEL.get());
                output.accept(AetherIIItems.ARKENIUM_SHORTSWORD.get());
                output.accept(AetherIIItems.ARKENIUM_HAMMER.get());
                output.accept(AetherIIItems.ARKENIUM_SPEAR.get());
                output.accept(AetherIIItems.ARKENIUM_SHOVEL.get());
                output.accept(AetherIIItems.ARKENIUM_PICKAXE.get());
                output.accept(AetherIIItems.ARKENIUM_AXE.get());
                output.accept(AetherIIItems.ARKENIUM_TROWEL.get());
                output.accept(AetherIIItems.GRAVITITE_SHORTSWORD.get());
                output.accept(AetherIIItems.GRAVITITE_HAMMER.get());
                output.accept(AetherIIItems.GRAVITITE_SPEAR.get());
                output.accept(AetherIIItems.GRAVITITE_SHOVEL.get());
                output.accept(AetherIIItems.GRAVITITE_PICKAXE.get());
                output.accept(AetherIIItems.GRAVITITE_AXE.get());
                output.accept(AetherIIItems.GRAVITITE_TROWEL.get());
                output.accept(AetherIIItems.MUSIC_DISC_AETHER_TUNE.get());
                output.accept(AetherIIItems.MUSIC_DISC_ASCENDING_DAWN.get());
                output.accept(AetherIIItems.MUSIC_DISC_AERWHALE.get());
                output.accept(AetherIIItems.MUSIC_DISC_APPROACHES.get());
                output.accept(AetherIIItems.MUSIC_DISC_DEMISE.get());
                output.accept(AetherIIItems.MUSIC_DISC_AETHER_TUNE.get());
                output.accept(AetherIIItems.RECORDING_892.get());
                output.accept(AetherIIItems.AETHER_PORTAL_FRAME.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_ARMOR_AND_ACCESSORIES = CREATIVE_MODE_TABS.register("armor_and_accessories", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "equipment_and_utilities"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "food_and_drinks"))
            .icon(() -> new ItemStack(Blocks.STONE))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".armor_and_accessories"))
            .displayItems((features, output) -> {

            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_FOOD_AND_DRINKS = CREATIVE_MODE_TABS.register("food_and_drinks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "combat"))
            .withTabsAfter(new ResourceLocation(AetherII.MODID, "ingredients"))
            .icon(() -> new ItemStack(AetherIIItems.ORANGE.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".food_and_drinks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.BLUEBERRY.get());
                output.accept(AetherIIItems.ENCHANTED_BERRY.get());
                output.accept(AetherIIItems.ORANGE.get());
                output.accept(AetherIIItems.WYNDBERRY.get());
                output.accept(AetherIIItems.BLUE_SWET_JELLY.get());
                output.accept(AetherIIItems.GOLDEN_SWET_JELLY.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_INGREDIENTS = CREATIVE_MODE_TABS.register("ingredients", () -> CreativeModeTab.builder()
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
                output.accept(AetherIIItems.ARKENIUM_STRIP.get());
                output.accept(AetherIIItems.RAW_GRAVITITE.get());
                output.accept(AetherIIItems.GRAVITITE_PLATE.get());
                output.accept(AetherIIItems.GOLDEN_AMBER.get());
                output.accept(AetherIIItems.TAEGORE_HIDE.get());
                output.accept(AetherIIItems.BURRUKAI_PELT.get());
                output.accept(AetherIIItems.AECHOR_PETAL.get());
                output.accept(AetherIIItems.ARCTIC_SNOWBALL.get());
                output.accept(AetherIIItems.GREEN_SWET_GEL.get());
                output.accept(AetherIIItems.BLUE_SWET_GEL.get());
                output.accept(AetherIIItems.PURPLE_SWET_GEL.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_SPAWN_EGGS = CREATIVE_MODE_TABS.register("spawn_eggs", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(AetherII.MODID, "ingredients"))
            .icon(() -> new ItemStack(AetherIIItems.AERBUNNY_SPAWN_EGG.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".spawn_eggs"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.AERBUNNY_SPAWN_EGG.get());
                output.accept(AetherIIItems.FLYING_COW_SPAWN_EGG.get());
                output.accept(AetherIIItems.SHEEPUFF_SPAWN_EGG.get());
                output.accept(AetherIIItems.PHYG_SPAWN_EGG.get());
                output.accept(AetherIIItems.KIRRID_SPAWN_EGG.get());
                output.accept(AetherIIItems.ZEPHYR_SPAWN_EGG.get());
            }).build());
}