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
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dungeon_blocks"))
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
                output.accept(AetherIIBlocks.SECRET_SKYROOT_DOOR.get());
                output.accept(AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get());
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
                output.accept(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
                output.accept(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get());
                output.accept(AetherIIBlocks.GREATROOT_PLANKS.get());
                output.accept(AetherIIBlocks.GREATROOT_STAIRS.get());
                output.accept(AetherIIBlocks.GREATROOT_SLAB.get());
                output.accept(AetherIIBlocks.GREATROOT_FENCE.get());
                output.accept(AetherIIBlocks.GREATROOT_FENCE_GATE.get());
                output.accept(AetherIIBlocks.GREATROOT_DOOR.get());
                output.accept(AetherIIBlocks.GREATROOT_TRAPDOOR.get());
                output.accept(AetherIIBlocks.SECRET_GREATROOT_DOOR.get());
                output.accept(AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get());
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
                output.accept(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
                output.accept(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get());
                output.accept(AetherIIBlocks.WISPROOT_PLANKS.get());
                output.accept(AetherIIBlocks.WISPROOT_STAIRS.get());
                output.accept(AetherIIBlocks.WISPROOT_SLAB.get());
                output.accept(AetherIIBlocks.WISPROOT_FENCE.get());
                output.accept(AetherIIBlocks.WISPROOT_FENCE_GATE.get());
                output.accept(AetherIIBlocks.WISPROOT_DOOR.get());
                output.accept(AetherIIBlocks.WISPROOT_TRAPDOOR.get());
                output.accept(AetherIIBlocks.SECRET_WISPROOT_DOOR.get());
                output.accept(AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get());
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
                output.accept(AetherIIBlocks.ICESTONE_BRICKS.get());
                output.accept(AetherIIBlocks.ICESTONE_BRICK_STAIRS.get());
                output.accept(AetherIIBlocks.ICESTONE_BRICK_SLAB.get());
                output.accept(AetherIIBlocks.ICESTONE_BRICK_WALL.get());
                output.accept(AetherIIBlocks.ICESTONE_FLAGSTONES.get());
                output.accept(AetherIIBlocks.ICESTONE_KEYSTONE.get());
                output.accept(AetherIIBlocks.ICESTONE_BASE_BRICKS.get());
                output.accept(AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.ICESTONE_BASE_PILLAR.get());
                output.accept(AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.ICESTONE_PILLAR.get());
                output.accept(AetherIIBlocks.QUICKSOIL_GLASS.get());
                output.accept(AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS.get());
                output.accept(AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS.get());
                output.accept(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());
                output.accept(AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS_PANE.get());
                output.accept(AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS_PANE.get());
                output.accept(AetherIIBlocks.CRUDE_SCATTERGLASS.get());
                output.accept(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get());
                output.accept(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get());
                output.accept(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get());
                output.accept(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
                output.accept(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
                output.accept(AetherIIBlocks.SCATTERGLASS.get());
                output.accept(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get());
                output.accept(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get());
                output.accept(AetherIIBlocks.SCATTERGLASS_PANE.get());
                output.accept(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get());
                output.accept(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get());
                output.accept(AetherIIBlocks.CLOUDWOOL.get());
                output.accept(AetherIIBlocks.WHITE_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.GRAY_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.BLACK_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.BROWN_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.RED_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.ORANGE_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.YELLOW_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.LIME_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.GREEN_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.CYAN_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.BLUE_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.PURPLE_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.MAGENTA_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.PINK_CLOUDWOOL.get());
                output.accept(AetherIIBlocks.CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.GRAY_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.BLACK_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.BROWN_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.RED_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.LIME_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.GREEN_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.CYAN_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.BLUE_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.PINK_CLOUDWOOL_CARPET.get());
                output.accept(AetherIIBlocks.ARKENIUM_DOOR.get());
                output.accept(AetherIIBlocks.ARKENIUM_TRAPDOOR.get());
                output.accept(AetherIIBlocks.AMBROSIUM_BLOCK.get());
                output.accept(AetherIIBlocks.ZANITE_BLOCK.get());
                output.accept(AetherIIBlocks.ARKENIUM_BLOCK.get());
                output.accept(AetherIIBlocks.GRAVITITE_BLOCK.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_DUNGEON_BLOCKS = CREATIVE_MODE_TABS.register("dungeon_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "building_blocks"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "natural_blocks"))
            .icon(() -> new ItemStack(Blocks.STONE))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".dungeon_blocks"))
            .displayItems((features, output) -> {

            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_NATURAL_BLOCKS = CREATIVE_MODE_TABS.register("natural_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dungeon_blocks"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "functional_blocks"))
            .icon(() -> new ItemStack(AetherIIBlocks.AETHER_GRASS_BLOCK.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".natural_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
                output.accept(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
                output.accept(AetherIIBlocks.AETHER_DIRT_PATH.get());
                output.accept(AetherIIBlocks.AETHER_DIRT.get());
                output.accept(AetherIIBlocks.COARSE_AETHER_DIRT.get());
                output.accept(AetherIIBlocks.AETHER_FARMLAND.get());
                output.accept(AetherIIBlocks.SHIMMERING_SILT.get());
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
                output.accept(AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get());
                output.accept(AetherIIBlocks.AMBROSIUM_ORE.get());
                output.accept(AetherIIBlocks.ZANITE_ORE.get());
                output.accept(AetherIIBlocks.GLINT_ORE.get());
                output.accept(AetherIIBlocks.ARKENIUM_ORE.get());
                output.accept(AetherIIBlocks.GRAVITITE_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_GLINT_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get());
                output.accept(AetherIIBlocks.CORROBONITE_ORE.get());
                output.accept(AetherIIBlocks.CORROBONITE_CLUSTER.get());
                output.accept(AetherIIBlocks.COLD_AERCLOUD.get());
                output.accept(AetherIIBlocks.BLUE_AERCLOUD.get());
                output.accept(AetherIIBlocks.GOLDEN_AERCLOUD.get());
                output.accept(AetherIIBlocks.GREEN_AERCLOUD.get());
                output.accept(AetherIIBlocks.PURPLE_AERCLOUD.get());
                output.accept(AetherIIBlocks.STORM_AERCLOUD.get());
                output.accept(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());
                output.accept(AetherIIBlocks.TANGLED_BRANCHES.get());
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
                output.accept(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get());
                output.accept(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get());
                output.accept(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get());
                output.accept(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get());
                output.accept(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get());
                output.accept(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get());
                output.accept(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get());
                output.accept(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get());
                output.accept(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());
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
                output.accept(AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE.get());
                output.accept(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE.get());
                output.accept(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE.get());
                output.accept(AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE.get());
                output.accept(AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE.get());
                output.accept(AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE.get());
                output.accept(AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE.get());
                output.accept(AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE.get());
                output.accept(AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE.get());
                output.accept(AetherIIBlocks.SKYROOT_SAPLING.get());
                output.accept(AetherIIBlocks.SKYPLANE_SAPLING.get());
                output.accept(AetherIIBlocks.SKYBIRCH_SAPLING.get());
                output.accept(AetherIIBlocks.SKYPINE_SAPLING.get());
                output.accept(AetherIIBlocks.WISPROOT_SAPLING.get());
                output.accept(AetherIIBlocks.WISPTOP_SAPLING.get());
                output.accept(AetherIIBlocks.GREATROOT_SAPLING.get());
                output.accept(AetherIIBlocks.GREATOAK_SAPLING.get());
                output.accept(AetherIIBlocks.GREATBOA_SAPLING.get());
                output.accept(AetherIIBlocks.AMBEROOT_SAPLING.get());
                output.accept(AetherIIBlocks.AETHER_SHORT_GRASS.get());
                output.accept(AetherIIBlocks.AETHER_MEDIUM_GRASS.get());
                output.accept(AetherIIBlocks.AETHER_LONG_GRASS.get());
                output.accept(AetherIIBlocks.HIGHLAND_FERN.get());
                output.accept(AetherIIBlocks.SHIELD_FERN.get());
                output.accept(AetherIIBlocks.BLADE_POA.get());
                output.accept(AetherIIBlocks.TARABLOOM.get());
                output.accept(AetherIIBlocks.HESPEROSE.get());
                output.accept(AetherIIBlocks.POASPROUT.get());
                output.accept(AetherIIBlocks.LILICHIME.get());
                output.accept(AetherIIBlocks.SATIVAL_SHOOT.get());
                output.accept(AetherIIBlocks.AECHOR_CUTTING.get());
                output.accept(AetherIIBlocks.HIGHLANDS_BUSH.get());
                output.accept(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get());
                output.accept(AetherIIBlocks.BLUEBERRY_BUSH.get());
                output.accept(AetherIIBlocks.ORANGE_TREE.get());
                output.accept(AetherIIBlocks.VALKYRIE_SPROUT.get());
                output.accept(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get());
                output.accept(AetherIIBlocks.BRYALINN_MOSS_CARPET.get());
                output.accept(AetherIIBlocks.BRYALINN_MOSS_VINES.get());
                output.accept(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get());
                output.accept(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get());
                output.accept(AetherIIBlocks.SHAYELINN_MOSS_CARPET.get());
                output.accept(AetherIIBlocks.SHAYELINN_MOSS_VINES.get());
                output.accept(AetherIIBlocks.SKY_ROOTS.get());
                output.accept(AetherIIBlocks.SKYROOT_TWIG.get());
                output.accept(AetherIIBlocks.HOLYSTONE_ROCK.get());
                output.accept(AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get());
                output.accept(AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL.get());
                output.accept(AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL.get());
                output.accept(AetherIIBlocks.MOA_EGG.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_FUNCTIONAL_BLOCKS = CREATIVE_MODE_TABS.register("functional_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "natural_blocks"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "equipment_and_utilities"))
            .icon(() -> new ItemStack(AetherIIBlocks.ARTISANS_BENCH.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".functional_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.AMBROSIUM_TORCH.get());
                output.accept(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
                output.accept(AetherIIBlocks.HOLYSTONE_FURNACE.get());
                output.accept(AetherIIBlocks.ALTAR.get());
                output.accept(AetherIIBlocks.ARTISANS_BENCH.get());
                output.accept(AetherIIBlocks.ARKENIUM_FORGE.get());
                output.accept(AetherIIBlocks.SKYROOT_LADDER.get());
                output.accept(AetherIIBlocks.SKYROOT_BOOKSHELF.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get());
                output.accept(AetherIIBlocks.SKYROOT_CHEST.get());
                output.accept(AetherIIBlocks.SKYROOT_SIGN.get());
                output.accept(AetherIIBlocks.SKYROOT_HANGING_SIGN.get());
                output.accept(AetherIIBlocks.GREATROOT_SIGN.get());
                output.accept(AetherIIBlocks.GREATROOT_HANGING_SIGN.get());
                output.accept(AetherIIBlocks.WISPROOT_SIGN.get());
                output.accept(AetherIIBlocks.WISPROOT_HANGING_SIGN.get());
                output.accept(AetherIIBlocks.SKYROOT_BED.get());
                output.accept(AetherIIBlocks.OUTPOST_CAMPFIRE.get());
                output.accept(AetherIIItems.AETHER_PORTAL_FRAME.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_EQUIPMENT_AND_UTILITIES = CREATIVE_MODE_TABS.register("equipment_and_utilities", () -> CreativeModeTab.builder()
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "functional_blocks"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_and_accessories"))
            .icon(() -> new ItemStack(AetherIIItems.GRAVITITE_PICKAXE.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".equipment_and_utilities"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.SKYROOT_SHORTSWORD.get());
                output.accept(AetherIIItems.SKYROOT_HAMMER.get());
                output.accept(AetherIIItems.SKYROOT_SPEAR.get());
                output.accept(AetherIIItems.SKYROOT_CROSSBOW.get());
                output.accept(AetherIIItems.SKYROOT_SHIELD.get());
                output.accept(AetherIIItems.SKYROOT_SHOVEL.get());
                output.accept(AetherIIItems.SKYROOT_PICKAXE.get());
                output.accept(AetherIIItems.SKYROOT_AXE.get());
                output.accept(AetherIIItems.SKYROOT_TROWEL.get());
                output.accept(AetherIIItems.HOLYSTONE_SHORTSWORD.get());
                output.accept(AetherIIItems.HOLYSTONE_HAMMER.get());
                output.accept(AetherIIItems.HOLYSTONE_SPEAR.get());
                output.accept(AetherIIItems.HOLYSTONE_CROSSBOW.get());
                output.accept(AetherIIItems.HOLYSTONE_SHIELD.get());
                output.accept(AetherIIItems.HOLYSTONE_SHOVEL.get());
                output.accept(AetherIIItems.HOLYSTONE_PICKAXE.get());
                output.accept(AetherIIItems.HOLYSTONE_AXE.get());
                output.accept(AetherIIItems.HOLYSTONE_TROWEL.get());
                output.accept(AetherIIItems.ZANITE_SHORTSWORD.get());
                output.accept(AetherIIItems.ZANITE_HAMMER.get());
                output.accept(AetherIIItems.ZANITE_SPEAR.get());
                output.accept(AetherIIItems.ZANITE_CROSSBOW.get());
                output.accept(AetherIIItems.ZANITE_SHIELD.get());
                output.accept(AetherIIItems.ZANITE_SHOVEL.get());
                output.accept(AetherIIItems.ZANITE_PICKAXE.get());
                output.accept(AetherIIItems.ZANITE_AXE.get());
                output.accept(AetherIIItems.ZANITE_TROWEL.get());
                output.accept(AetherIIItems.ARKENIUM_SHORTSWORD.get());
                output.accept(AetherIIItems.ARKENIUM_HAMMER.get());
                output.accept(AetherIIItems.ARKENIUM_SPEAR.get());
                output.accept(AetherIIItems.ARKENIUM_CROSSBOW.get());
                output.accept(AetherIIItems.ARKENIUM_SHIELD.get());
                output.accept(AetherIIItems.ARKENIUM_SHOVEL.get());
                output.accept(AetherIIItems.ARKENIUM_PICKAXE.get());
                output.accept(AetherIIItems.ARKENIUM_AXE.get());
                output.accept(AetherIIItems.ARKENIUM_TROWEL.get());
                output.accept(AetherIIItems.GRAVITITE_SHORTSWORD.get());
                output.accept(AetherIIItems.GRAVITITE_HAMMER.get());
                output.accept(AetherIIItems.GRAVITITE_SPEAR.get());
                output.accept(AetherIIItems.GRAVITITE_CROSSBOW.get());
                output.accept(AetherIIItems.GRAVITITE_SHIELD.get());
                output.accept(AetherIIItems.GRAVITITE_SHOVEL.get());
                output.accept(AetherIIItems.GRAVITITE_PICKAXE.get());
                output.accept(AetherIIItems.GRAVITITE_AXE.get());
                output.accept(AetherIIItems.GRAVITITE_TROWEL.get());
                output.accept(AetherIIItems.ARKENIUM_SHEARS.get());
                output.accept(AetherIIItems.SCATTERGLASS_BOLT.get());
                output.accept(AetherIIItems.SKYROOT_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_WATER_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_PUFFERFISH_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_SALMON_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_COD_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_TROPICAL_FISH_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_AXOLOTL_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_TADPOLE_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_MILK_BUCKET.get());
                output.accept(AetherIIItems.MUSIC_DISC_AETHER_TUNE.get());
                output.accept(AetherIIItems.MUSIC_DISC_ASCENDING_DAWN.get());
                output.accept(AetherIIItems.MUSIC_DISC_AERWHALE.get());
                output.accept(AetherIIItems.MUSIC_DISC_APPROACHES.get());
                output.accept(AetherIIItems.MUSIC_DISC_DEMISE.get());
                output.accept(AetherIIItems.RECORDING_892.get());
                output.accept(AetherIIItems.GLINT_COIN.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_ARMOR_AND_ACCESSORIES = CREATIVE_MODE_TABS.register("armor_and_accessories", () -> CreativeModeTab.builder()
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "equipment_and_utilities"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "food_and_drinks"))
            .icon(() -> new ItemStack(AetherIIItems.GRAVITITE_HELMET.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".armor_and_accessories"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.TAEGORE_HIDE_HELMET.get());
                output.accept(AetherIIItems.TAEGORE_HIDE_CHESTPLATE.get());
                output.accept(AetherIIItems.TAEGORE_HIDE_LEGGINGS.get());
                output.accept(AetherIIItems.TAEGORE_HIDE_BOOTS.get());
                output.accept(AetherIIItems.TAEGORE_HIDE_GLOVES.get());
                output.accept(AetherIIItems.BURRUKAI_PELT_HELMET.get());
                output.accept(AetherIIItems.BURRUKAI_PELT_CHESTPLATE.get());
                output.accept(AetherIIItems.BURRUKAI_PELT_LEGGINGS.get());
                output.accept(AetherIIItems.BURRUKAI_PELT_BOOTS.get());
                output.accept(AetherIIItems.BURRUKAI_PELT_GLOVES.get());
                output.accept(AetherIIItems.ZANITE_HELMET.get());
                output.accept(AetherIIItems.ZANITE_CHESTPLATE.get());
                output.accept(AetherIIItems.ZANITE_LEGGINGS.get());
                output.accept(AetherIIItems.ZANITE_BOOTS.get());
                output.accept(AetherIIItems.ZANITE_GLOVES.get());
                output.accept(AetherIIItems.ARKENIUM_HELMET.get());
                output.accept(AetherIIItems.ARKENIUM_CHESTPLATE.get());
                output.accept(AetherIIItems.ARKENIUM_LEGGINGS.get());
                output.accept(AetherIIItems.ARKENIUM_BOOTS.get());
                output.accept(AetherIIItems.ARKENIUM_GLOVES.get());
                output.accept(AetherIIItems.GRAVITITE_HELMET.get());
                output.accept(AetherIIItems.GRAVITITE_CHESTPLATE.get());
                output.accept(AetherIIItems.GRAVITITE_LEGGINGS.get());
                output.accept(AetherIIItems.GRAVITITE_BOOTS.get());
                output.accept(AetherIIItems.GRAVITITE_GLOVES.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_FOOD_AND_DRINKS = CREATIVE_MODE_TABS.register("food_and_drinks", () -> CreativeModeTab.builder()
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "combat"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "ingredients"))
            .icon(() -> new ItemStack(AetherIIItems.ORANGE.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".food_and_drinks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.BLUEBERRY.get());
                output.accept(AetherIIItems.ENCHANTED_BLUEBERRY.get());
                output.accept(AetherIIItems.ORANGE.get());
                output.accept(AetherIIItems.WYNDBERRY.get());
                output.accept(AetherIIItems.ENCHANTED_WYNDBERRY.get());
                output.accept(AetherIIItems.GREEN_SWET_JELLY.get());
                output.accept(AetherIIItems.BLUE_SWET_JELLY.get());
                output.accept(AetherIIItems.PURPLE_SWET_JELLY.get());
                output.accept(AetherIIItems.GOLDEN_SWET_JELLY.get());
                output.accept(AetherIIItems.WHITE_SWET_JELLY.get());
                output.accept(AetherIIItems.BURRUKAI_RIB_CUT.get());
                output.accept(AetherIIItems.BURRUKAI_RIBS.get());
                output.accept(AetherIIItems.KIRRID_LOIN.get());
                output.accept(AetherIIItems.KIRRID_CUTLET.get());
                output.accept(AetherIIItems.RAW_TAEGORE_MEAT.get());
                output.accept(AetherIIItems.TAEGORE_STEAK.get());
                output.accept(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get());
                output.accept(AetherIIItems.ROASTED_SKYROOT_LIZARD_ON_A_STICK.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_INGREDIENTS = CREATIVE_MODE_TABS.register("ingredients", () -> CreativeModeTab.builder()
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "food_and_drinks"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "spawn_eggs"))
            .icon(() -> new ItemStack(AetherIIItems.INERT_ARKENIUM.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".ingredients"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.SKYROOT_STICK.get());
                output.accept(AetherIIItems.SCATTERGLASS_SHARD.get());
                output.accept(AetherIIItems.AMBROSIUM_SHARD.get());
                output.accept(AetherIIItems.ZANITE_GEMSTONE.get());
                output.accept(AetherIIItems.INERT_ARKENIUM.get());
                output.accept(AetherIIItems.ARKENIUM_PLATES.get());
                output.accept(AetherIIItems.INERT_GRAVITITE.get());
                output.accept(AetherIIItems.GRAVITITE_PLATE.get());
                output.accept(AetherIIItems.CORROBONITE_CRYSTAL.get());
                output.accept(AetherIIItems.GLINT_GEMSTONE.get());
                output.accept(AetherIIItems.GOLDEN_AMBER.get());
                output.accept(AetherIIItems.CLOUDTWINE.get());
                output.accept(AetherIIItems.TAEGORE_HIDE.get());
                output.accept(AetherIIItems.BURRUKAI_PELT.get());
                output.accept(AetherIIItems.AECHOR_PETAL.get());
                output.accept(AetherIIItems.VALKYRIE_WINGS.get());
                output.accept(AetherIIItems.BRETTL_CANE.get());
                output.accept(AetherIIItems.BRETTL_GRASS.get());
                output.accept(AetherIIItems.BRETTL_ROPE.get());
                output.accept(AetherIIItems.BRETTL_FLOWER.get());
                output.accept(AetherIIItems.SKYROOT_PINECONE.get());
                output.accept(AetherIIItems.MOA_FEED.get());
                output.accept(AetherIIItems.BLUEBERRY_MOA_FEED.get());
                output.accept(AetherIIItems.ENCHANTED_MOA_FEED.get());
                output.accept(AetherIIItems.ARCTIC_SNOWBALL.get());
                output.accept(AetherIIItems.GREEN_SWET_GEL.get());
                output.accept(AetherIIItems.BLUE_SWET_GEL.get());
                output.accept(AetherIIItems.PURPLE_SWET_GEL.get());
                output.accept(AetherIIItems.GOLDEN_SWET_GEL.get());
                output.accept(AetherIIItems.WHITE_SWET_GEL.get());
                output.accept(AetherIIItems.CHARGE_CORE.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_SPAWN_EGGS = CREATIVE_MODE_TABS.register("spawn_eggs", () -> CreativeModeTab.builder()
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "ingredients"))
            .icon(() -> new ItemStack(AetherIIItems.AERBUNNY_SPAWN_EGG.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".spawn_eggs"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.AECHOR_PLANT_SPAWN_EGG.get());
                output.accept(AetherIIItems.AERBUNNY_SPAWN_EGG.get());
                output.accept(AetherIIItems.HIGHFIELDS_BURRUKAI_SPAWN_EGG.get());
                output.accept(AetherIIItems.MAGNETIC_BURRUKAI_SPAWN_EGG.get());
                output.accept(AetherIIItems.ARCTIC_BURRUKAI_SPAWN_EGG.get());
                output.accept(AetherIIItems.COCKATRICE_SPAWN_EGG.get());
                output.accept(AetherIIItems.SWET_SPAWN_EGG.get());
                output.accept(AetherIIItems.FLYING_COW_SPAWN_EGG.get());
                output.accept(AetherIIItems.HIGHFIELDS_KIRRID_SPAWN_EGG.get());
                output.accept(AetherIIItems.MAGNETIC_KIRRID_SPAWN_EGG.get());
                output.accept(AetherIIItems.ARCTIC_KIRRID_SPAWN_EGG.get());
                output.accept(AetherIIItems.MOA_SPAWN_EGG.get());
                output.accept(AetherIIItems.PHYG_SPAWN_EGG.get());
                output.accept(AetherIIItems.SHEEPUFF_SPAWN_EGG.get());
                output.accept(AetherIIItems.HIGHFIELDS_TAEGORE_SPAWN_EGG.get());
                output.accept(AetherIIItems.MAGNETIC_TAEGORE_SPAWN_EGG.get());
                output.accept(AetherIIItems.ARCTIC_TAEGORE_SPAWN_EGG.get());
                output.accept(AetherIIItems.ZEPHYR_SPAWN_EGG.get());
                output.accept(AetherIIItems.SKYROOT_LIZARD_SPAWN_EGG.get());
                output.accept(AetherIIItems.TEMPEST_SPAWN_EGG.get());
            }).build());
}