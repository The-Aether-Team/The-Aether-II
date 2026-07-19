package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEntityIds;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class AetherIICreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AetherII.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_BUILDING_BLOCKS = CREATIVE_MODE_TABS.register("building_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsAfter(Identifier.fromNamespaceAndPath(AetherII.MODID, "colored_blocks"))
            .icon(() -> new ItemStack(AetherIIBlocks.HOLYSTONE_BRICKS.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".building_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.SKYROOT_LOG.get());
                output.accept(AetherIIBlocks.SKYROOT_WOOD.get());
                output.accept(AetherIIBlocks.SKYROOT_TRUNK.get());
                output.accept(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
                output.accept(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get());
                output.accept(AetherIIBlocks.STRIPPED_SKYROOT_TRUNK.get());
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
                output.accept(AetherIIBlocks.GREATROOT_TRUNK.get());
                output.accept(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
                output.accept(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get());
                output.accept(AetherIIBlocks.STRIPPED_GREATROOT_TRUNK.get());
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
                output.accept(AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE.get());
                output.accept(AetherIIBlocks.WISPROOT_WOOD.get());
                output.accept(AetherIIBlocks.WISPROOT_TRUNK.get());
                output.accept(AetherIIBlocks.MOSSY_WISPROOT_WOOD.get());
                output.accept(AetherIIBlocks.MOSSY_WISPROOT_TRUNK.get());
                output.accept(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
                output.accept(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get());
                output.accept(AetherIIBlocks.STRIPPED_WISPROOT_TRUNK.get());
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
                output.accept(AetherIIBlocks.AMBEROOT_DEPOSIT.get());
                output.accept(AetherIIBlocks.AMBEROOT_WOOD.get());
                output.accept(AetherIIBlocks.AMBEROOT_TRUNK.get());
                output.accept(AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get());
                output.accept(AetherIIBlocks.STRIPPED_AMBEROOT_WOOD.get());
                output.accept(AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK.get());
                output.accept(AetherIIBlocks.AMBEROOT_PLANKS.get());
                output.accept(AetherIIBlocks.AMBEROOT_STAIRS.get());
                output.accept(AetherIIBlocks.AMBEROOT_SLAB.get());
                output.accept(AetherIIBlocks.AMBEROOT_FENCE.get());
                output.accept(AetherIIBlocks.AMBEROOT_FENCE_GATE.get());
                output.accept(AetherIIBlocks.AMBEROOT_DOOR.get());
                output.accept(AetherIIBlocks.AMBEROOT_TRAPDOOR.get());
                output.accept(AetherIIBlocks.SECRET_AMBEROOT_DOOR.get());
                output.accept(AetherIIBlocks.SECRET_AMBEROOT_TRAPDOOR.get());
                output.accept(AetherIIBlocks.AMBEROOT_PRESSURE_PLATE.get());
                output.accept(AetherIIBlocks.AMBEROOT_BUTTON.get());
                output.accept(AetherIIBlocks.AMBEROOT_FLOORBOARDS.get());
                output.accept(AetherIIBlocks.AMBEROOT_HIGHLIGHT.get());
                output.accept(AetherIIBlocks.AMBEROOT_SHINGLES.get());
                output.accept(AetherIIBlocks.AMBEROOT_SMALL_SHINGLES.get());
                output.accept(AetherIIBlocks.AMBEROOT_BASE_PLANKS.get());
                output.accept(AetherIIBlocks.AMBEROOT_TOP_PLANKS.get());
                output.accept(AetherIIBlocks.AMBEROOT_BASE_BEAM.get());
                output.accept(AetherIIBlocks.AMBEROOT_TOP_BEAM.get());
                output.accept(AetherIIBlocks.AMBEROOT_BEAM.get());
                output.accept(AetherIIBlocks.CLOUDWOOL.get());
                output.accept(AetherIIBlocks.CLOUDWOOL_ROOFING.get());
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
                output.accept(AetherIIBlocks.MOSSY_UNDERSHALE.get());
                output.accept(AetherIIBlocks.MOSSY_UNDERSHALE_STAIRS.get());
                output.accept(AetherIIBlocks.MOSSY_UNDERSHALE_SLAB.get());
                output.accept(AetherIIBlocks.MOSSY_UNDERSHALE_WALL.get());
                output.accept(AetherIIBlocks.UNDERSHALE_BRICKS.get());
                output.accept(AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get());
                output.accept(AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get());
                output.accept(AetherIIBlocks.UNDERSHALE_BRICK_WALL.get());
                output.accept(AetherIIBlocks.UNDERSHALE_BRICK_PRESSURE_PLATE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_BRICK_BUTTON.get());
                output.accept(AetherIIBlocks.UNDERSHALE_FLAGSTONES.get());
                output.accept(AetherIIBlocks.UNDERSHALE_TILE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get());
                output.accept(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get());
                output.accept(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.UNDERSHALE_PILLAR.get());
                output.accept(AetherIIBlocks.SENTRY_BRICKS.get());
                output.accept(AetherIIBlocks.SENTRY_BRICK_STAIRS.get());
                output.accept(AetherIIBlocks.SENTRY_BRICK_SLAB.get());
                output.accept(AetherIIBlocks.SENTRY_BRICK_WALL.get());
                output.accept(AetherIIBlocks.SENTRY_BUTTON.get());
                output.accept(AetherIIBlocks.SENTRY_LIGHTSTONE.get());
                output.accept(AetherIIBlocks.SENTRY_FLAGSTONES.get());
                output.accept(AetherIIBlocks.SENTRY_TILE.get());
                output.accept(AetherIIBlocks.SENTRY_BASE_BRICKS.get());
                output.accept(AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.SENTRY_BASE_PILLAR.get());
                output.accept(AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.SENTRY_PILLAR.get());
                output.accept(AetherIIBlocks.ICHORITE.get());
                output.accept(AetherIIBlocks.ICHORITE_STAIRS.get());
                output.accept(AetherIIBlocks.ICHORITE_SLAB.get());
                output.accept(AetherIIBlocks.ICHORITE_WALL.get());
                output.accept(AetherIIBlocks.SMOOTH_ICHORITE.get());
                output.accept(AetherIIBlocks.SMOOTH_ICHORITE_STAIRS.get());
                output.accept(AetherIIBlocks.SMOOTH_ICHORITE_SLAB.get());
                output.accept(AetherIIBlocks.SMOOTH_ICHORITE_WALL.get());
                output.accept(AetherIIBlocks.ICHORITE_BRICKS.get());
                output.accept(AetherIIBlocks.ICHORITE_BRICK_STAIRS.get());
                output.accept(AetherIIBlocks.ICHORITE_BRICK_SLAB.get());
                output.accept(AetherIIBlocks.ICHORITE_BRICK_WALL.get());
                output.accept(AetherIIBlocks.ICHORITE_FLAGSTONES.get());
                output.accept(AetherIIBlocks.ICHORITE_RUNESTONE.get());
                output.accept(AetherIIBlocks.ICHORITE_KEYSTONE.get());
                output.accept(AetherIIBlocks.ICHORITE_BASE_BRICKS.get());
                output.accept(AetherIIBlocks.ICHORITE_CAPSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.ICHORITE_BASE_PILLAR.get());
                output.accept(AetherIIBlocks.ICHORITE_CAPSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.ICHORITE_PILLAR.get());
                output.accept(AetherIIBlocks.MARBLED_ICHORITE.get());
                output.accept(AetherIIBlocks.MARBLED_ICHORITE_STAIRS.get());
                output.accept(AetherIIBlocks.MARBLED_ICHORITE_SLAB.get());
                output.accept(AetherIIBlocks.MARBLED_ICHORITE_WALL.get());
                output.accept(AetherIIBlocks.MARBLED_BRICKS.get());
                output.accept(AetherIIBlocks.MARBLED_BRICK_STAIRS.get());
                output.accept(AetherIIBlocks.MARBLED_BRICK_SLAB.get());
                output.accept(AetherIIBlocks.MARBLED_BRICK_WALL.get());
                output.accept(AetherIIBlocks.MARBLED_FLAGSTONES.get());
                output.accept(AetherIIBlocks.MARBLED_KEYSTONE.get());
                output.accept(AetherIIBlocks.MARBLED_BASE_BRICKS.get());
                output.accept(AetherIIBlocks.MARBLED_CAPSTONE_BRICKS.get());
                output.accept(AetherIIBlocks.MARBLED_BASE_PILLAR.get());
                output.accept(AetherIIBlocks.MARBLED_CAPSTONE_PILLAR.get());
                output.accept(AetherIIBlocks.MARBLED_PILLAR.get());
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
                output.accept(AetherIIBlocks.TILED_QUICKSOIL_GLASS.get());
                output.accept(AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS.get());
                output.accept(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());
                output.accept(AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE.get());
                output.accept(AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE.get());
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
                output.accept(AetherIIBlocks.ARKENIUM_DOOR.get());
                output.accept(AetherIIBlocks.ARKENIUM_TRAPDOOR.get());
                output.accept(AetherIIBlocks.ARKENIUM_BARS.get());
                output.accept(AetherIIBlocks.FLORAL_ARKENIUM_BARS.get());
                output.accept(AetherIIBlocks.PATTERNED_ARKENIUM_BARS.get());
                output.accept(AetherIIBlocks.CURVED_ARKENIUM_BARS.get());
                output.accept(AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get());
                output.accept(AetherIIBlocks.RUSTIC_FLORAL_ARKENIUM_BARS.get());
                output.accept(AetherIIBlocks.RUSTIC_PATTERNED_ARKENIUM_BARS.get());
                output.accept(AetherIIBlocks.RUSTIC_CURVED_ARKENIUM_BARS.get());
                output.accept(AetherIIBlocks.AMBROSIUM_BLOCK.get());
                output.accept(AetherIIBlocks.ZANITE_BLOCK.get());
                output.accept(AetherIIBlocks.ARKENIUM_BLOCK.get());
                output.accept(AetherIIBlocks.GRAVITITE_BLOCK.get());
                output.accept(AetherIIBlocks.GLINT_BLOCK.get());
                output.accept(AetherIIBlocks.CORROBONITE_BLOCK.get());
                output.accept(AetherIIBlocks.GOLDEN_AMBER_BLOCK.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_COLORED_BLOCKS = CREATIVE_MODE_TABS.register("colored_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(Identifier.fromNamespaceAndPath(AetherII.MODID, "building_blocks"))
            .withTabsAfter(Identifier.fromNamespaceAndPath(AetherII.MODID, "natural_blocks"))
            .icon(() -> new ItemStack(AetherIIBlocks.PURPLE_CLOUDWOOL.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".colored_blocks"))
            .displayItems((features, output) -> {
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
                output.accept(AetherIIBlocks.WHITE_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.LIGHT_GRAY_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.GRAY_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.BLACK_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.BROWN_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.RED_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.ORANGE_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.YELLOW_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.LIME_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.GREEN_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.CYAN_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.LIGHT_BLUE_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.BLUE_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.PURPLE_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.MAGENTA_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.PINK_ARILUM_LANTERN.get());
                output.accept(AetherIIBlocks.SKYROOT_BED.get());
                output.accept(AetherIIBlocks.WHITE_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.GRAY_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.BLACK_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.BROWN_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.RED_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.ORANGE_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.YELLOW_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.LIME_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.GREEN_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.CYAN_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.BLUE_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.PURPLE_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.MAGENTA_SKYROOT_BED.get());
                output.accept(AetherIIBlocks.PINK_SKYROOT_BED.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_NATURAL_BLOCKS = CREATIVE_MODE_TABS.register("natural_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(Identifier.fromNamespaceAndPath(AetherII.MODID, "colored_blocks"))
            .withTabsAfter(Identifier.fromNamespaceAndPath(AetherII.MODID, "functional_blocks"))
            .icon(() -> new ItemStack(AetherIIBlocks.AETHER_GRASS_BLOCK.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".natural_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
                output.accept(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
                output.accept(AetherIIBlocks.AETHER_DIRT_PATH.get());
                output.accept(AetherIIBlocks.AETHER_DIRT.get());
                output.accept(AetherIIBlocks.COARSE_AETHER_DIRT.get());
                output.accept(AetherIIBlocks.MYCELIAL_AETHER_DIRT.get());
                output.accept(AetherIIBlocks.AETHER_FARMLAND.get());
                output.accept(AetherIIBlocks.SHIMMERING_SILT.get());
                output.accept(AetherIIBlocks.QUICKSOIL.get());
                output.accept(AetherIIBlocks.FERROSITE_SAND.get());
                output.accept(AetherIIBlocks.FERROSITE_MUD.get());
                output.accept(AetherIIBlocks.ARCTIC_ICE.get());
                output.accept(AetherIIBlocks.FRAGILE_ARCTIC_ICE.get());
                output.accept(AetherIIBlocks.ARCTIC_PACKED_ICE.get());
                output.accept(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get());
                output.accept(AetherIIBlocks.ARCTIC_SNOW.get());
                output.accept(AetherIIBlocks.IRRADIATED_DUST_BLOCK.get());
                output.accept(AetherIIBlocks.HOLYSTONE.get());
                output.accept(AetherIIBlocks.UNSTABLE_HOLYSTONE.get());
                output.accept(AetherIIBlocks.MOSSY_HOLYSTONE.get());
                output.accept(AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
                output.accept(AetherIIBlocks.UNDERSHALE.get());
                output.accept(AetherIIBlocks.MOSSY_UNDERSHALE.get());
                output.accept(AetherIIBlocks.UNSTABLE_UNDERSHALE.get());
                output.accept(AetherIIBlocks.ICHORITE.get());
                output.accept(AetherIIBlocks.AGIOSITE.get());
                output.accept(AetherIIBlocks.FERROSITE.get());
                output.accept(AetherIIBlocks.RUSTED_FERROSITE.get());
                output.accept(AetherIIBlocks.ICESTONE.get());
                output.accept(AetherIIBlocks.CRUDE_SCATTERGLASS.get());
                output.accept(AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get());
                output.accept(AetherIIBlocks.AMBROSIUM_ORE.get());
                output.accept(AetherIIBlocks.ZANITE_ORE.get());
                output.accept(AetherIIBlocks.ARKENIUM_ORE.get());
                output.accept(AetherIIBlocks.GRAVITITE_ORE.get());
                output.accept(AetherIIBlocks.GLINT_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get());
                output.accept(AetherIIBlocks.UNDERSHALE_GLINT_ORE.get());
                output.accept(AetherIIBlocks.CORROBONITE_ORE.get());
                output.accept(AetherIIBlocks.CORROBONITE_CLUSTER.get());
                output.accept(AetherIIBlocks.INERT_ARKENIUM_BLOCK.get());
                output.accept(AetherIIBlocks.INERT_GRAVITITE_BLOCK.get());
                output.accept(AetherIIBlocks.COLD_AERCLOUD.get());
                output.accept(AetherIIBlocks.BLUE_AERCLOUD.get());
                output.accept(AetherIIBlocks.GREEN_AERCLOUD.get());
                output.accept(AetherIIBlocks.PURPLE_AERCLOUD.get());
                output.accept(AetherIIBlocks.GOLDEN_AERCLOUD.get());
                output.accept(AetherIIBlocks.STORM_AERCLOUD.get());
                output.accept(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());
                output.accept(AetherIIBlocks.ANIMAL_STASH.get());
                output.accept(AetherIIBlocks.TANGLED_BRANCHES.get());
                output.accept(AetherIIBlocks.SKYROOT_LOG.get());
                output.accept(AetherIIBlocks.SKYROOT_TRUNK.get());
                output.accept(AetherIIBlocks.GREATROOT_LOG.get());
                output.accept(AetherIIBlocks.GREATROOT_TRUNK.get());
                output.accept(AetherIIBlocks.WISPROOT_LOG.get());
                output.accept(AetherIIBlocks.WISPROOT_TRUNK.get());
                output.accept(AetherIIBlocks.MOSSY_WISPROOT_LOG.get());
                output.accept(AetherIIBlocks.MOSSY_WISPROOT_TRUNK.get());
                output.accept(AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE.get());
                output.accept(AetherIIBlocks.AMBEROOT_LOG.get());
                output.accept(AetherIIBlocks.AMBEROOT_DEPOSIT.get());
                output.accept(AetherIIBlocks.AMBEROOT_TRUNK.get());
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
                output.accept(AetherIIBlocks.SHORT_AETHER_GRASS.get());
                output.accept(AetherIIBlocks.MEDIUM_AETHER_GRASS.get());
                output.accept(AetherIIBlocks.TALL_AETHER_GRASS.get());
                output.accept(AetherIIBlocks.AETHER_FERN.get());
                output.accept(AetherIIBlocks.SHIELD_FERN.get());
                output.accept(AetherIIBlocks.BLADE_POA.get());
                output.accept(AetherIIBlocks.TARABLOOM.get());
                output.accept(AetherIIBlocks.HESPEROSE.get());
                output.accept(AetherIIBlocks.POASPROUT.get());
                output.accept(AetherIIBlocks.LILICHIME.get());
                output.accept(AetherIIBlocks.PLURACIAN.get());
                output.accept(AetherIIBlocks.SATIVAL_SHOOT.get());
                output.accept(AetherIIBlocks.BRETTL_FLOWER.get());
                output.accept(AetherIIBlocks.HOLPUPEA.get());
                output.accept(AetherIIBlocks.AECHOR_CUTTING.get());
                output.accept(AetherIIBlocks.CARRION_CUTTING.get());
                output.accept(AetherIIBlocks.AETHER_BUSH.get());
                output.accept(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get());
                output.accept(AetherIIBlocks.BLUEBERRY_BUSH.get());
                output.accept(AetherIIBlocks.ORANGE_TREE.get());
                output.accept(AetherIIBlocks.VALKYRIE_SPROUT.get());
                output.accept(AetherIIItems.ARILUM_BULBS.get());
                output.accept(AetherIIBlocks.ARILUM.get());
                output.accept(AetherIIBlocks.BLOOMING_ARILUM.get());
                output.accept(AetherIIBlocks.MAGNETIC_SHROOM_BLOCK.get());
                output.accept(AetherIIBlocks.SPOTTED_MAGNETIC_SHROOM_BLOCK.get());
                output.accept(AetherIIBlocks.MAGNETIC_SHROOM_STEM.get());
                output.accept(AetherIIBlocks.MAGNETIC_SHROOM.get());
                output.accept(AetherIIItems.BRETTL_CANE.get());
                output.accept(AetherIIBlocks.BRETTL_GRASS_BUNDLE.get());
                output.accept(AetherIIBlocks.GEL_BLOCK.get());
                output.accept(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get());
                output.accept(AetherIIBlocks.BRYALINN_MOSS_CARPET.get());
                output.accept(AetherIIBlocks.BRYALINN_MOSS_VINES.get());
                output.accept(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get());
                output.accept(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get());
                output.accept(AetherIIBlocks.SHAYELINN_MOSS_CARPET.get());
                output.accept(AetherIIBlocks.SHAYELINN_MOSS_VINES.get());
                output.accept(AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get());
                output.accept(AetherIIBlocks.AMBRELINN_MOSS_CARPET.get());
                output.accept(AetherIIBlocks.AMBRELINN_MOSS_VINES.get());
                output.accept(AetherIIBlocks.TARAHESP_FLOWERS.get());
                output.accept(AetherIIBlocks.SKY_ROOTS.get());
                output.accept(AetherIIBlocks.SKYROOT_TWIG.get());
                output.accept(AetherIIBlocks.HOLYSTONE_ROCK.get());
                output.accept(AetherIIBlocks.POINTED_HOLYSTONE.get());
                output.accept(AetherIIBlocks.POINTED_ICHORITE.get());
                output.accept(AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL.get());
                output.accept(AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL.get());
                output.accept(AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_FUNCTIONAL_BLOCKS = CREATIVE_MODE_TABS.register("functional_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(Identifier.fromNamespaceAndPath(AetherII.MODID, "natural_blocks"))
            .withTabsAfter(Identifier.fromNamespaceAndPath(AetherII.MODID, "tools_and_utilities"))
            .icon(() -> new ItemStack(AetherIIBlocks.ARTISANS_BENCH.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".functional_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherIIBlocks.AMBROSIUM_TORCH.get());
                output.accept(AetherIIBlocks.ARKENIUM_LANTERN.get());
                output.accept(AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN.get());
                output.accept(AetherIIBlocks.ARKENIUM_CHAIN.get());
                output.accept(AetherIIBlocks.HOLYSTONE_LEVER.get());
                output.accept(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
                output.accept(AetherIIBlocks.HOLYSTONE_FURNACE.get());
                output.accept(AetherIIBlocks.HOLYSTONE_SMOKER.get());
                output.accept(AetherIIBlocks.AMBER_HOURGLASS.get());
                output.accept(AetherIIBlocks.ALTAR.get());
                output.accept(AetherIIBlocks.ARTISANS_BENCH.get());
                output.accept(AetherIIBlocks.ARKENIUM_FORGE.get());
                output.accept(AetherIIBlocks.ALKAHEST_PURIFIER.get());
                output.accept(AetherIIBlocks.AMBROSIUM_CAMPFIRE.get());
                output.accept(AetherIIBlocks.SKYROOT_LADDER.get());
                output.accept(AetherIIBlocks.SKYROOT_BOOKSHELF.get());
                output.accept(AetherIIBlocks.GREATROOT_BOOKSHELF.get());
                output.accept(AetherIIBlocks.WISPROOT_BOOKSHELF.get());
                output.accept(AetherIIBlocks.AMBEROOT_BOOKSHELF.get());
                output.accept(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get());
                output.accept(AetherIIBlocks.SKYROOT_SHELF.get());
                output.accept(AetherIIBlocks.GREATROOT_SHELF.get());
                output.accept(AetherIIBlocks.WISPROOT_SHELF.get());
                output.accept(AetherIIBlocks.AMBEROOT_SHELF.get());
                output.accept(AetherIIBlocks.SKYROOT_CHEST.get());
                output.accept(AetherIIBlocks.SKYROOT_BARREL.get());
                output.accept(AetherIIBlocks.SENTRY_CRATE.get());
                output.accept(AetherIIBlocks.SENTRY_SPAWNER.get());
                output.accept(AetherIIBlocks.SENTRY_TRAP.get());
                output.accept(AetherIIBlocks.HOLYSTONE_VASE.get());
                output.accept(AetherIIBlocks.VERADEXIAN_VASE.get());
                output.accept(AetherIIBlocks.BREXALLEN_VASE.get());
                output.accept(AetherIIBlocks.ABANDONED_BAG.get());
                output.accept(AetherIIBlocks.SKYROOT_SIGN.get());
                output.accept(AetherIIBlocks.SKYROOT_HANGING_SIGN.get());
                output.accept(AetherIIBlocks.GREATROOT_SIGN.get());
                output.accept(AetherIIBlocks.GREATROOT_HANGING_SIGN.get());
                output.accept(AetherIIBlocks.WISPROOT_SIGN.get());
                output.accept(AetherIIBlocks.WISPROOT_HANGING_SIGN.get());
                output.accept(AetherIIBlocks.AMBEROOT_SIGN.get());
                output.accept(AetherIIBlocks.AMBEROOT_HANGING_SIGN.get());
                output.accept(AetherIIBlocks.CLOUDWOOL_BEDROLL.get());
                output.accept(AetherIIBlocks.SKYROOT_BED.get());
                output.accept(AetherIIBlocks.THERAN_GLOBE.get());
                output.accept(AetherIIBlocks.OUTPOST_CAMPFIRE.get());
                output.accept(AetherIIBlocks.UNSTABLE_HOLYSTONE.get());
                output.accept(AetherIIBlocks.UNSTABLE_UNDERSHALE.get());
                output.accept(AetherIIItems.AETHER_PORTAL_FRAME.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_DUNGEON_BLOCKS =
            CREATIVE_MODE_TABS.register("dungeon_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(Identifier.fromNamespaceAndPath(AetherII.MODID, "functional_blocks"))
            .withTabsAfter(Identifier.fromNamespaceAndPath(AetherII.MODID, "equipment_and_utilities"))
            .icon(() -> new ItemStack(AetherIIBlocks.GUARDIAN_LAMP.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".dungeon_blocks"))
            .displayItems((features, output) -> {
                if (AetherIIConfig.COMMON.experimental_dungeon_content.get()) {
                    output.accept(AetherIIBlocks.GUARDIAN_LOG.get());
                    output.accept(AetherIIBlocks.GUARDIAN_LOG_SLAB.get());
                    output.accept(AetherIIBlocks.GUARDIAN_WOOD.get());
                    output.accept(AetherIIBlocks.GUARDIAN_WOOD_SLAB.get());
                    output.accept(AetherIIBlocks.GUARDIAN_TRUNK.get());
                    output.accept(AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get());
                    output.accept(AetherIIBlocks.STRIPPED_GUARDIAN_LOG_SLAB.get());
                    output.accept(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get());
                    output.accept(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD_SLAB.get());
                    output.accept(AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK.get());
                    output.accept(AetherIIBlocks.INFECTED_LOG.get());
                    output.accept(AetherIIBlocks.INFECTED_LOG_SLAB.get());
                    output.accept(AetherIIBlocks.INFECTED_WOOD.get());
                    output.accept(AetherIIBlocks.INFECTED_WOOD_SLAB.get());
                    output.accept(AetherIIBlocks.INFECTED_TRUNK.get());
                    output.accept(AetherIIBlocks.STRIPPED_INFECTED_LOG.get());
                    output.accept(AetherIIBlocks.STRIPPED_INFECTED_LOG_SLAB.get());
                    output.accept(AetherIIBlocks.STRIPPED_INFECTED_WOOD.get());
                    output.accept(AetherIIBlocks.STRIPPED_INFECTED_WOOD_SLAB.get());
                    output.accept(AetherIIBlocks.STRIPPED_INFECTED_TRUNK.get());
                    output.accept(AetherIIBlocks.GUARDIAN_ROOTS.get());
                    output.accept(AetherIIBlocks.UNSTABLE_GUARDIAN_ROOTS.get());
                    output.accept(AetherIIBlocks.LUCENT_GUARDIAN_ROOTS.get());
                    output.accept(AetherIIBlocks.GUARDIAN_LAMP.get());
                    output.accept(AetherIIBlocks.UNDERGROWTH_LEAVES.get());
                    output.accept(AetherIIBlocks.UNDERGROWTH_VINES.get());
                    output.accept(AetherIIBlocks.HANGING_UNDERGROWTH.get());
                    output.accept(AetherIIBlocks.ROTSHROOM_BLOCK.get());
                    output.accept(AetherIIBlocks.ROTSHROOM_SLAB.get());
                    output.accept(AetherIIBlocks.ROTSHROOM_STEM.get());
                    output.accept(AetherIIBlocks.SHELF_ROTSHROOM_SLAB.get());
                    output.accept(AetherIIBlocks.ROTSHROOM.get());
                    output.accept(AetherIIBlocks.ROTSHROOM_CLUSTER.get());
                    output.accept(AetherIIBlocks.ROTSHROOM_TOADSTOOL.get());
                    output.accept(AetherIIBlocks.SHELF_ROTSHROOM.get());
                    output.accept(AetherIIBlocks.ROTGROWTH_VINES.get());
                    output.accept(AetherIIBlocks.PRAYER_CANDLE.get());
                    output.accept(AetherIIBlocks.GUARDIAN_PEW.get());
                    output.accept(AetherIIBlocks.GUARDIAN_DONATION_BOX.get());
                    output.accept(AetherIIBlocks.FUNGAL_CACHE.get());
                    output.accept(AetherIIBlocks.SAGE_CHEST.get());
                }
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_TOOLS_AND_UTILITIES = CREATIVE_MODE_TABS.register("tools_and_utilities", () -> CreativeModeTab.builder()
            .withTabsBefore(Identifier.fromNamespaceAndPath(AetherII.MODID, "functional_blocks"))
            .withTabsAfter(Identifier.fromNamespaceAndPath(AetherII.MODID, "combat_and_equipment"))
            .icon(() -> new ItemStack(AetherIIItems.GRAVITITE_PICKAXE.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".tools_and_utilities"))
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
                output.accept(AetherIIItems.ZANITE_SHEARS.get());
                output.accept(AetherIIItems.SKYROOT_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_WATER_BUCKET.get());
                output.accept(AetherIIItems.SKYROOT_MILK_BUCKET.get());
                output.accept(AetherIIItems.ARKENIUM_CANISTER.get());
                output.accept(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
                output.accept(AetherIIItems.ARKENIUM_HESTVEIL_CANISTER.get());
                output.accept(AetherIIItems.COLD_AERCLOUD_GLIDER.get());
                output.accept(AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get());
                output.accept(AetherIIItems.BLUE_AERCLOUD_GLIDER.get());
                output.accept(AetherIIItems.PURPLE_AERCLOUD_GLIDER.get());
                output.accept(AetherIIItems.SHIFTING_GLASS.get());
                output.accept(AetherIIItems.AERBUNNY_BELL.get());
                output.accept(AetherIIItems.BEAST_PELT_BUNDLE.get());
                output.accept(AetherIIItems.BRETTL_LASSO.get());
                output.accept(AetherIIItems.MOA_SADDLE.get());
                output.accept(AetherIIItems.MOA_SADDLEBAG.get());
                output.accept(AetherIIItems.LARGE_MOA_SADDLEBAG.get());
                output.accept(AetherIIItems.CLOUD_SKIFF.get());
                output.accept(AetherIIItems.MUSIC_PLAYER.get());
                output.accept(AetherIIItems.ENGRAVED_DISC_ASCENDING_DAWN.get());
                output.accept(AetherIIItems.ENGRAVED_DISC_AERWHALE.get());
                output.accept(AetherIIItems.ENGRAVED_DISC_APPROACHES.get());
                output.accept(AetherIIItems.ENGRAVED_DISC_DEMISE.get());
                output.accept(AetherIIItems.ENGRAVED_DISC_CHINCHILLA.get());
                output.accept(AetherIIItems.ENGRAVED_DISC_HIGH.get());
                output.accept(AetherIIItems.ENGRAVED_DISC_REVOLUTIONS.get());
                output.accept(AetherIIItems.GLINT_COIN.get());
                output.accept(AetherIIItems.GUIDEBOOK_PAGE.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_COMBAT_AND_EQUIPMENT = CREATIVE_MODE_TABS.register("combat_and_equipment", () -> CreativeModeTab.builder()
            .withTabsBefore(Identifier.fromNamespaceAndPath(AetherII.MODID, "tools_and_utilities"))
            .withTabsAfter(Identifier.fromNamespaceAndPath(AetherII.MODID, "consumables"))
            .icon(() -> new ItemStack(AetherIIItems.ARKENIUM_SHORTSWORD.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".combat_and_equipment"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.SKYROOT_SHORTSWORD.get());
                output.accept(AetherIIItems.HOLYSTONE_SHORTSWORD.get());
                output.accept(AetherIIItems.ZANITE_SHORTSWORD.get());
                output.accept(AetherIIItems.ARKENIUM_SHORTSWORD.get());
                output.accept(AetherIIItems.GRAVITITE_SHORTSWORD.get());
                output.accept(AetherIIItems.SKYROOT_PIKE.get());
                output.accept(AetherIIItems.HOLYSTONE_PIKE.get());
                output.accept(AetherIIItems.ZANITE_PIKE.get());
                output.accept(AetherIIItems.ARKENIUM_PIKE.get());
                output.accept(AetherIIItems.GRAVITITE_PIKE.get());
                output.accept(AetherIIItems.SKYROOT_HAMMER.get());
                output.accept(AetherIIItems.HOLYSTONE_HAMMER.get());
                output.accept(AetherIIItems.ZANITE_HAMMER.get());
                output.accept(AetherIIItems.ARKENIUM_HAMMER.get());
                output.accept(AetherIIItems.GRAVITITE_HAMMER.get());
                output.accept(AetherIIItems.SKYROOT_SHIELD.get());
                output.accept(AetherIIItems.BURRUKAI_PLATE_SHIELD.get());
                output.accept(AetherIIItems.ZANITE_SHIELD.get());
                output.accept(AetherIIItems.ARKENIUM_SHIELD.get());
                output.accept(AetherIIItems.GRAVITITE_SHIELD.get());
                output.accept(AetherIIItems.SKYROOT_CROSSBOW.get());
                output.accept(AetherIIItems.HOLYSTONE_CROSSBOW.get());
                output.accept(AetherIIItems.ZANITE_CROSSBOW.get());
                output.accept(AetherIIItems.ARKENIUM_CROSSBOW.get());
                output.accept(AetherIIItems.GRAVITITE_CROSSBOW.get());
                output.accept(AetherIIItems.SCATTERGLASS_BOLT.get());
                output.accept(AetherIIItems.DART_SHOOTER.get());
                output.accept(new ItemStack(AetherIIItems.AMBER_DARTS, 1, DataComponentPatch.builder().set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(EffectBuildupPresets.VULNERABILITY)).build()));
                output.accept(new ItemStack(AetherIIItems.AMBER_DARTS, 1, DataComponentPatch.builder().set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(EffectBuildupPresets.TOXIN)).build()));
                output.accept(new ItemStack(AetherIIItems.AMBER_DARTS, 1, DataComponentPatch.builder().set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(EffectBuildupPresets.VENOM)).build()));
                output.accept(new ItemStack(AetherIIItems.AMBER_DARTS, 1, DataComponentPatch.builder().set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(EffectBuildupPresets.AMBROSIUM_POISONING)).build()));
                output.accept(AetherIIItems.BEAST_PELT_HELMET.get());
                output.accept(AetherIIItems.BEAST_PELT_CHESTPLATE.get());
                output.accept(AetherIIItems.BEAST_PELT_LEGGINGS.get());
                output.accept(AetherIIItems.BEAST_PELT_BOOTS.get());
                output.accept(AetherIIItems.BEAST_PELT_GLOVES.get());
                output.accept(AetherIIItems.BURRUKAI_PLATE_HELMET.get());
                output.accept(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE.get());
                output.accept(AetherIIItems.BURRUKAI_PLATE_LEGGINGS.get());
                output.accept(AetherIIItems.BURRUKAI_PLATE_BOOTS.get());
                output.accept(AetherIIItems.BURRUKAI_PLATE_GLOVES.get());
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
                output.accept(AetherIIItems.HAMMER_OF_DEMOLITION.get());
                output.accept(AetherIIItems.NEPTUNE_HELMET.get());
                output.accept(AetherIIItems.NEPTUNE_CHESTPLATE.get());
                output.accept(AetherIIItems.NEPTUNE_LEGGINGS.get());
                output.accept(AetherIIItems.NEPTUNE_BOOTS.get());
                output.accept(AetherIIItems.NEPTUNE_GLOVES.get());
                output.accept(AetherIIItems.SENTRY_BOOTS.get());
                output.accept(AetherIIItems.KINETIC_THRUSTERS.get());
                output.accept(AetherIIItems.ZANITE_PENDANT.get());
                output.accept(AetherIIItems.ICESTONE_PENDANT.get());
                output.accept(AetherIIItems.CHARM_OF_DAMAGE_I.get());
                output.accept(AetherIIItems.CHARM_OF_DEXTERITY_I.get());
                output.accept(AetherIIItems.CHARM_OF_KNOCKBACK_I.get());
                output.accept(AetherIIItems.CHARM_OF_HEALTH_I.get());
                output.accept(AetherIIItems.CHARM_OF_DEFENSE_I.get());
                output.accept(AetherIIItems.CHARM_OF_TOUGHNESS_I.get());
                output.accept(AetherIIItems.CHARM_OF_RESISTANCE_I.get());
                output.accept(AetherIIItems.CHARM_OF_AGILITY_I.get());
                output.accept(AetherIIItems.CHARM_OF_EFFICIENCY_I.get());
                output.accept(AetherIIItems.CHARM_OF_REACH_I.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_CONSUMABLES = CREATIVE_MODE_TABS.register("consumables", () -> CreativeModeTab.builder()
            .withTabsBefore(Identifier.fromNamespaceAndPath(AetherII.MODID, "combat"))
            .withTabsAfter(Identifier.fromNamespaceAndPath(AetherII.MODID, "ingredients"))
            .icon(() -> new ItemStack(AetherIIItems.ORANGE.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".consumables"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.BLUEBERRY.get());
                output.accept(AetherIIItems.ENCHANTED_BLUEBERRY.get());
                output.accept(AetherIIItems.ORANGE.get());
                output.accept(AetherIIItems.ENCHANTED_ORANGE.get());
                output.accept(AetherIIItems.WYNDBERRY.get());
                output.accept(AetherIIItems.ENCHANTED_WYNDBERRY.get());
                output.accept(AetherIIItems.GOLDEN_WYNDBERRY.get());
                output.accept(AetherIIItems.SATIVAL_BULB.get());
                output.accept(AetherIIItems.SWET_JELLY.get());
                output.accept(AetherIIItems.ENCHANTED_SWET_JELLY.get());
                output.accept(AetherIIItems.FRIED_PRISMALLARD_EGG.get());
                output.accept(AetherIIItems.PRISMALLARD_LEG.get());
                output.accept(AetherIIItems.PRISMALLARD_ROAST.get());
                output.accept(AetherIIItems.BURRUKAI_RIB_CUT.get());
                output.accept(AetherIIItems.BURRUKAI_RIBS.get());
                output.accept(AetherIIItems.KIRRID_LOIN.get());
                output.accept(AetherIIItems.KIRRID_CUTLET.get());
                output.accept(AetherIIItems.RAW_TAEGORE_MEAT.get());
                output.accept(AetherIIItems.TAEGORE_STEAK.get());
                output.accept(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get());
                output.accept(AetherIIItems.ROASTED_SKYROOT_LIZARD_ON_A_STICK.get());
                output.accept(AetherIIItems.WATER_VIAL.get());
                output.accept(AetherIIItems.BANDAGE.get());
                output.accept(AetherIIItems.SPLINT.get());
                output.accept(AetherIIItems.ANTITOXIN_VIAL.get());
                output.accept(AetherIIItems.ANTIVENOM_VIAL.get());
                output.accept(AetherIIItems.VALKYRIE_TEA.get());
                output.accept(AetherIIItems.HEALING_STONE.get());
                output.accept(new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()));
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_INGREDIENTS = CREATIVE_MODE_TABS.register("ingredients", () -> CreativeModeTab.builder()
            .withTabsBefore(Identifier.fromNamespaceAndPath(AetherII.MODID, "consumables"))
            .withTabsAfter(Identifier.fromNamespaceAndPath(AetherII.MODID, "spawn_eggs"))
            .icon(() -> new ItemStack(AetherIIItems.AMBROSIUM_SHARD.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".ingredients"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.AMBROSIUM_SHARD.get());
                output.accept(AetherIIItems.FOSSILIZED_ZANITE.get());
                output.accept(AetherIIItems.INERT_ARKENIUM.get());
                output.accept(AetherIIItems.INERT_GRAVITITE.get());
                output.accept(AetherIIItems.FOSSILIZED_GLINT.get());
                output.accept(AetherIIItems.FOSSILIZED_CORROBONITE.get());
                output.accept(AetherIIItems.GOLDEN_AMBER.get());
                output.accept(AetherIIItems.ZANITE_GEMSTONE.get());
                output.accept(AetherIIItems.ARKENIUM_CHIP.get());
                output.accept(AetherIIItems.ARKENIUM_PLATE.get());
                output.accept(AetherIIItems.GRAVITITE_PLATE.get());
                output.accept(AetherIIItems.GLINT_GEMSTONE.get());
                output.accept(AetherIIItems.CORROBONITE_CRYSTAL.get());
                output.accept(AetherIIItems.NEPTUNE_SCALE.get());
                output.accept(AetherIIItems.SENTRY_SERVO.get());
                output.accept(AetherIIItems.RESONANT_STONE.get());
                output.accept(AetherIIItems.SKYROOT_STICK.get());
                output.accept(AetherIIItems.SCATTERGLASS_SHARD.get());
                output.accept(AetherIIItems.ARCTIC_SNOWBALL.get());
                output.accept(AetherIIItems.VALKYRIE_WINGS.get());
                output.accept(AetherIIItems.BRETTL_GRASS.get());
                output.accept(AetherIIItems.BRETTL_ROPE.get());
                output.accept(AetherIIItems.CLOUDTWINE.get());
                output.accept(AetherIIItems.BEAST_PELT.get());
                output.accept(AetherIIItems.BURRUKAI_PLATE.get());
//                output.accept(AetherIIItems.KIRRID_PLATE.get());
                output.accept(AetherIIItems.PRISMALLARD_FEATHER.get());
                output.accept(AetherIIItems.MOA_FEATHER.get());
                output.accept(AetherIIItems.COCKATRICE_FEATHER.get());
                output.accept(AetherIIItems.SWET_GEL.get());
                output.accept(AetherIIItems.SWET_SUGAR.get());
                output.accept(AetherIIItems.PRISMALLARD_EGG.get());
                output.accept(AetherIIBlocks.MOA_EGG.get());
                output.accept(AetherIIItems.AECHOR_PETAL.get());
                output.accept(AetherIIItems.SKYROOT_PINECONE.get());
                output.accept(AetherIIItems.MOA_FEED.get());
                output.accept(AetherIIItems.BLUEBERRY_MOA_FEED.get());
                output.accept(AetherIIItems.ENCHANTED_MOA_FEED.get());
                output.accept(AetherIIItems.SCATTERGLASS_VIAL.get());
                output.accept(AetherIIItems.IRRADIATED_ARMOR.get());
                output.accept(AetherIIItems.IRRADIATED_WEAPON.get());
                output.accept(AetherIIItems.IRRADIATED_TOOL.get());
                output.accept(AetherIIItems.IRRADIATED_CHUNK.get());
                output.accept(AetherIIItems.IRRADIATED_DUST.get());
//                output.accept(AetherIIItems.ZEPHYR_HUSK.get()); // TODO WIP ALPHA THINGS
//                output.accept(AetherIIItems.CHARGE_CATALYST.get());
//                output.accept(AetherIIItems.ARKENIUM_CORE.get());
//                output.accept(AetherIIItems.GRAVITITE_CORE.get());
//                output.accept(AetherIIItems.EYE_OF_THE_MIMIC.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_II_SPAWN_EGGS = CREATIVE_MODE_TABS.register("spawn_eggs", () -> CreativeModeTab.builder()
            .withTabsBefore(Identifier.fromNamespaceAndPath(AetherII.MODID, "ingredients"))
            .icon(() -> new ItemStack(AetherIIItems.AERBUNNY_SPAWN_EGG.get()))
            .title(Component.translatable("itemGroup." + AetherII.MODID + ".spawn_eggs"))
            .displayItems((features, output) -> {
                output.accept(AetherIIItems.HIGHFIELDS_TAEGORE_SPAWN_EGG.get());
                output.accept(AetherIIItems.MAGNETIC_TAEGORE_SPAWN_EGG.get());
                output.accept(AetherIIItems.ARCTIC_TAEGORE_SPAWN_EGG.get());
                output.accept(AetherIIItems.HIGHFIELDS_KIRRID_SPAWN_EGG.get());
                output.accept(AetherIIItems.MAGNETIC_KIRRID_SPAWN_EGG.get());
                output.accept(AetherIIItems.ARCTIC_KIRRID_SPAWN_EGG.get());
                output.accept(AetherIIItems.HIGHFIELDS_BURRUKAI_SPAWN_EGG.get());
                output.accept(AetherIIItems.MAGNETIC_BURRUKAI_SPAWN_EGG.get());
                output.accept(AetherIIItems.ARCTIC_BURRUKAI_SPAWN_EGG.get());
                output.accept(AetherIIItems.PHYG_SPAWN_EGG.get());
                output.accept(AetherIIItems.SHEEPUFF_SPAWN_EGG.get());
                output.accept(AetherIIItems.FLYING_COW_SPAWN_EGG.get());
                output.accept(AetherIIItems.AERBUNNY_SPAWN_EGG.get());
                output.accept(AetherIIItems.PRISMALLARD_SPAWN_EGG.get());
                output.accept(AetherIIItems.SKYROOT_LIZARD_SPAWN_EGG.get());
                output.accept(AetherIIItems.GLITTERWING_SPAWN_EGG.get());
                output.accept(AetherIIItems.SHROUDWING_SPAWN_EGG.get());
                output.accept(AetherIIItems.MOA_SPAWN_EGG.get());
                output.accept(AetherIIItems.AERWHALE_SPAWN_EGG.get());
                output.accept(AetherIIItems.BLUE_SWET_SPAWN_EGG.get());
                output.accept(AetherIIItems.GOLDEN_SWET_SPAWN_EGG.get());
                output.accept(AetherIIItems.AECHOR_PLANT_SPAWN_EGG.get());
                output.accept(AetherIIItems.CARRION_SPROUT_SPAWN_EGG.get());
                output.accept(AetherIIItems.SKEPHID_SPAWN_EGG.get());
                output.accept(AetherIIItems.ZEPHYR_SPAWN_EGG.get());
                output.accept(AetherIIItems.TEMPEST_SPAWN_EGG.get());
                output.accept(AetherIIItems.COCKATRICE_SPAWN_EGG.get());
                output.accept(AetherIIItems.ARKENIUM_TALUTON_SPAWN_EGG.get());
                output.accept(AetherIIItems.GRAVITITE_TALUTON_SPAWN_EGG.get());
                output.accept(AetherIIItems.SENTRY_CRATE_MIMIC_SPAWN_EGG.get());
                output.accept(AetherIIItems.DETONATION_SENTRY_SPAWN_EGG.get());
                output.accept(AetherIIItems.SENTRY_GOLEM_SPAWN_EGG.get());
            }).build());

    public static ItemStack getMoaBook() {
        final String selector = "entity @e[type=" + AetherIIEntityIds.MOA.identifier() + ",limit=1,sort=nearest] ";
        final String dataMerge = "data merge " + selector;
        final String dataRemove = "data remove " + selector;

        try {
            ItemStack book = new UtilityBookBuilder()
                    .author("Aether II")
                    .title("Moa Book")
                    .generation(3)
                    .section("Set Feather Color")
                        .entries(Moa.FeatherColor.values(),
                                (featherColor, section) -> section.translatableEntry(AetherII.MODID + ".tooltip.item.moa_egg.feather_color." + featherColor.getSerializedName())
                                        .withNameStyle((featherColor == Moa.FeatherColor.WHITE ? Style.EMPTY : featherColor == Moa.FeatherColor.YELLOW ? Style.EMPTY.withColor(ChatFormatting.GOLD) : Style.EMPTY.withColor(featherColor.dyeColor.getTextColor())).withItalic(featherColor.isSpecialColor))
                                        .command(dataMerge + "{FeatherColor:" + featherColor.getSerializedName() + "}"))
                    .section("Set Keratin Color")
                        .entries(Moa.KeratinColor.values(),
                                (keratinColor, section) -> section.translatableEntry(AetherII.MODID + ".tooltip.item.moa_egg.keratin_color." + keratinColor.getSerializedName())
                                        .withNameStyle(Style.EMPTY.withItalic(keratinColor.isSpecialColor))
                                        .command(dataMerge + "{KeratinColor:" + keratinColor.getSerializedName() + "}"))
                    .section("Set Eye Color")
                        .entries(Moa.EyeColor.values(),
                                (eyeColor, section) -> section.translatableEntry(AetherII.MODID + ".tooltip.item.moa_egg.eye_color." + eyeColor.getSerializedName())
                                        .withNameStyle(Style.EMPTY.withItalic(eyeColor.isSpecialColor))
                                        .command(dataMerge + "{EyeColor:" + eyeColor.getSerializedName() + "}"))
                    .section("Set Feather Shape")
                        .entries(Moa.FeatherShape.values(),
                                (featherShape, section) -> section.translatableEntry(AetherII.MODID + ".tooltip.item.moa_egg.feather_shape." + featherShape.getSerializedName())
                                        .withNameStyle(Style.EMPTY.withItalic(featherShape.isSpecialShape))
                                        .command(dataMerge + "{FeatherShape:" + featherShape.getSerializedName() + "}"))
                    .section("Set Special Variant")
                        .entry("Remove MoaVariant").command(dataRemove + "MoaVariant")
                        .entry("Remove CustomName").command(dataRemove + "CustomName")
                        .entries(Moa.SpecialVariant.values(),
                                (variant, section) -> section.entry(variant.getSerializedName())
                                        .command(dataMerge + "{MoaVariant:" + variant.id() + "}"))
                    .build();
            book.set(DataComponents.CUSTOM_NAME, Component.literal("Moa Book").withStyle(Rarity.EPIC.getStyleModifier()).withStyle(style -> style.withItalic(false)));
            book.set(DataComponents.LORE, new ItemLore(List.of(
                Component.literal("Contains buttons to modify"),
                Component.literal("the nearest Moa's features")
            )));
            book.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("minecraft", "knowledge_book"));
            book.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
            return book;
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCreativeModTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.hasPermissions() && event.getTabKey().compareTo(CreativeModeTabs.OP_BLOCKS) == 0) {
            event.accept(getMoaBook());
            event.accept(AetherIIBlocks.LOCKED_BLOCK.get());
            event.accept(AetherIIBlocks.BOSS_DOORWAY_BLOCK.get());
            event.accept(AetherIIBlocks.TREASURE_DOORWAY_BLOCK.get());
        }
    }
}