package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

public abstract class AetherIIBlockItemTagProvider {
    public AetherIIBlockItemTagProvider() {
    }

    public void run() {
        // Aether II
        this.tag(AetherIITags.Blocks.AETHER_GRASS_BLOCKS, AetherIITags.Items.AETHER_GRASS_BLOCKS).add(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_DIRT, AetherIITags.Items.AETHER_DIRT).add(
                AetherIIBlocks.AETHER_DIRT.get(),
                AetherIIBlocks.COARSE_AETHER_DIRT.get(),
                AetherIIBlocks.MYCELIAL_AETHER_DIRT.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_MOSS_BLOCKS, AetherIITags.Items.AETHER_MOSS_BLOCKS).add(
                AetherIIBlocks.BRYALINN_MOSS_BLOCK.get(),
                AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get(),
                AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_MOSS_VINES, AetherIITags.Items.AETHER_MOSS_VINES).add(
                AetherIIBlocks.BRYALINN_MOSS_VINES.get(),
                AetherIIBlocks.SHAYELINN_MOSS_VINES.get(),
                AetherIIBlocks.AMBRELINN_MOSS_VINES.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_MOSS_CARPETS, AetherIITags.Items.AETHER_MOSS_CARPETS).add(
                AetherIIBlocks.BRYALINN_MOSS_CARPET.get(),
                AetherIIBlocks.SHAYELINN_MOSS_CARPET.get(),
                AetherIIBlocks.AMBRELINN_MOSS_CARPET.get()
        );
        this.tag(AetherIITags.Blocks.HOLYSTONE, AetherIITags.Items.HOLYSTONE).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE.get()
        );
        this.tag(AetherIITags.Blocks.UNDERSHALE, AetherIITags.Items.UNDERSHALE).add(
                AetherIIBlocks.UNDERSHALE.get()
        );
        this.tag(AetherIITags.Blocks.ARCTIC_ICE, AetherIITags.Items.ARCTIC_ICE).add(
                AetherIIBlocks.ARCTIC_ICE.get(),
                AetherIIBlocks.FRAGILE_ARCTIC_ICE.get(),
                AetherIIBlocks.ARCTIC_PACKED_ICE.get()
        );
        this.tag(AetherIITags.Blocks.FERROSITE, AetherIITags.Items.FERROSITE).add(
                AetherIIBlocks.FERROSITE.get(),
                AetherIIBlocks.RUSTED_FERROSITE.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_SURFACE_STONES, AetherIITags.Items.AETHER_SURFACE_STONES).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.UNSTABLE_HOLYSTONE.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE.get(),
                AetherIIBlocks.ICESTONE.get(),
                AetherIIBlocks.FERROSITE.get(),
                AetherIIBlocks.RUSTED_FERROSITE.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_UNDERCLOUD_STONES, AetherIITags.Items.AETHER_UNDERCLOUD_STONES).add(
                AetherIIBlocks.UNDERSHALE.get(),
                AetherIIBlocks.UNSTABLE_UNDERSHALE.get(),
                AetherIIBlocks.ICESTONE.get(),
                AetherIIBlocks.AGIOSITE.get(),
                AetherIIBlocks.ICHORITE.get()
        );
        this.tag(AetherIITags.Blocks.AERCLOUDS, AetherIITags.Items.AERCLOUDS).add(
                AetherIIBlocks.COLD_AERCLOUD.get(),
                AetherIIBlocks.BLUE_AERCLOUD.get(),
                AetherIIBlocks.GOLDEN_AERCLOUD.get(),
                AetherIIBlocks.GREEN_AERCLOUD.get(),
                AetherIIBlocks.PURPLE_AERCLOUD.get(),
                AetherIIBlocks.STORM_AERCLOUD.get()
        );
        this.tag(AetherIITags.Blocks.CLOUDWOOL, AetherIITags.Items.CLOUDWOOL).add(
                AetherIIBlocks.CLOUDWOOL.get(),
                AetherIIBlocks.WHITE_CLOUDWOOL.get(),
                AetherIIBlocks.ORANGE_CLOUDWOOL.get(),
                AetherIIBlocks.MAGENTA_CLOUDWOOL.get(),
                AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get(),
                AetherIIBlocks.YELLOW_CLOUDWOOL.get(),
                AetherIIBlocks.LIME_CLOUDWOOL.get(),
                AetherIIBlocks.PINK_CLOUDWOOL.get(),
                AetherIIBlocks.GRAY_CLOUDWOOL.get(),
                AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get(),
                AetherIIBlocks.CYAN_CLOUDWOOL.get(),
                AetherIIBlocks.PURPLE_CLOUDWOOL.get(),
                AetherIIBlocks.BLUE_CLOUDWOOL.get(),
                AetherIIBlocks.BROWN_CLOUDWOOL.get(),
                AetherIIBlocks.GREEN_CLOUDWOOL.get(),
                AetherIIBlocks.RED_CLOUDWOOL.get(),
                AetherIIBlocks.BLACK_CLOUDWOOL.get()
        );
        this.tag(AetherIITags.Blocks.SKYROOT_LOGS, AetherIITags.Items.SKYROOT_LOGS).add(
                AetherIIBlocks.SKYROOT_LOG.get(),
                AetherIIBlocks.SKYROOT_WOOD.get(),
                AetherIIBlocks.SKYROOT_TRUNK.get(),
                AetherIIBlocks.STRIPPED_SKYROOT_LOG.get(),
                AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get(),
                AetherIIBlocks.STRIPPED_SKYROOT_TRUNK.get()
        );
        this.tag(AetherIITags.Blocks.GREATROOT_LOGS, AetherIITags.Items.GREATROOT_LOGS).add(
                AetherIIBlocks.GREATROOT_LOG.get(),
                AetherIIBlocks.GREATROOT_WOOD.get(),
                AetherIIBlocks.GREATROOT_TRUNK.get(),
                AetherIIBlocks.STRIPPED_GREATROOT_LOG.get(),
                AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get(),
                AetherIIBlocks.STRIPPED_GREATROOT_TRUNK.get()
        );
        this.tag(AetherIITags.Blocks.WISPROOT_LOGS, AetherIITags.Items.WISPROOT_LOGS).add(
                AetherIIBlocks.WISPROOT_LOG.get(),
                AetherIIBlocks.MOSSY_WISPROOT_LOG.get(),
                AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE.get(),
                AetherIIBlocks.WISPROOT_WOOD.get(),
                AetherIIBlocks.WISPROOT_TRUNK.get(),
                AetherIIBlocks.MOSSY_WISPROOT_WOOD.get(),
                AetherIIBlocks.MOSSY_WISPROOT_TRUNK.get(),
                AetherIIBlocks.STRIPPED_WISPROOT_LOG.get(),
                AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get(),
                AetherIIBlocks.STRIPPED_WISPROOT_TRUNK.get()
        );
        this.tag(AetherIITags.Blocks.AMBEROOT_LOGS, AetherIITags.Items.AMBEROOT_LOGS).add(
                AetherIIBlocks.AMBEROOT_LOG.get(),
                AetherIIBlocks.AMBEROOT_WOOD.get(),
                AetherIIBlocks.AMBEROOT_TRUNK.get(),
                AetherIIBlocks.AMBEROOT_DEPOSIT.get(),
                AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get(),
                AetherIIBlocks.STRIPPED_AMBEROOT_WOOD.get(),
                AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK.get()
        );
        this.tag(AetherIITags.Blocks.GUARDIAN_LOGS, AetherIITags.Items.GUARDIAN_LOGS).add(
                AetherIIBlocks.GUARDIAN_LOG.get(),
                AetherIIBlocks.GUARDIAN_WOOD.get(),
                AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get(),
                AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get(),
                AetherIIBlocks.INFECTED_LOG.get(),
                AetherIIBlocks.INFECTED_WOOD.get(),
                AetherIIBlocks.STRIPPED_INFECTED_LOG.get(),
                AetherIIBlocks.STRIPPED_INFECTED_WOOD.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_NATURAL_LOGS, AetherIITags.Items.AETHER_NATURAL_LOGS).add(
                AetherIIBlocks.SKYROOT_LOG.get(),
                AetherIIBlocks.GREATROOT_LOG.get(),
                AetherIIBlocks.WISPROOT_LOG.get(),
                AetherIIBlocks.MOSSY_WISPROOT_LOG.get(),
                AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE.get(),
                AetherIIBlocks.AMBEROOT_LOG.get(),
                AetherIIBlocks.AMBEROOT_DEPOSIT.get()
        );
        this.tag(AetherIITags.Blocks.TRUNKS, AetherIITags.Items.TRUNKS).add(
                AetherIIBlocks.SKYROOT_TRUNK.get(),
                AetherIIBlocks.STRIPPED_SKYROOT_TRUNK.get(),
                AetherIIBlocks.GREATROOT_TRUNK.get(),
                AetherIIBlocks.STRIPPED_GREATROOT_TRUNK.get(),
                AetherIIBlocks.WISPROOT_TRUNK.get(),
                AetherIIBlocks.MOSSY_WISPROOT_TRUNK.get(),
                AetherIIBlocks.STRIPPED_WISPROOT_TRUNK.get(),
                AetherIIBlocks.AMBEROOT_TRUNK.get(),
                AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK.get(),
                AetherIIBlocks.GUARDIAN_TRUNK.get(),
                AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK.get(),
                AetherIIBlocks.INFECTED_TRUNK.get(),
                AetherIIBlocks.STRIPPED_INFECTED_TRUNK.get()
        );
        this.tag(AetherIITags.Blocks.LEAVES, AetherIITags.Items.LEAVES).add(
                AetherIIBlocks.SKYROOT_LEAVES.get(),
                AetherIIBlocks.SKYPLANE_LEAVES.get(),
                AetherIIBlocks.SKYBIRCH_LEAVES.get(),
                AetherIIBlocks.SKYPINE_LEAVES.get(),
                AetherIIBlocks.WISPROOT_LEAVES.get(),
                AetherIIBlocks.WISPTOP_LEAVES.get(),
                AetherIIBlocks.GREATROOT_LEAVES.get(),
                AetherIIBlocks.GREATOAK_LEAVES.get(),
                AetherIIBlocks.GREATBOA_LEAVES.get(),
                AetherIIBlocks.AMBEROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get()
        );
        this.tag(AetherIITags.Blocks.LEAF_LITTER, AetherIITags.Items.LEAF_LITTER).add(
                AetherIIBlocks.SKYROOT_LEAF_LITTER.get(),
                AetherIIBlocks.SKYPLANE_LEAF_LITTER.get(),
                AetherIIBlocks.SKYBIRCH_LEAF_LITTER.get(),
                AetherIIBlocks.SKYPINE_LEAF_LITTER.get(),
                AetherIIBlocks.WISPROOT_LEAF_LITTER.get(),
                AetherIIBlocks.WISPTOP_LEAF_LITTER.get(),
                AetherIIBlocks.GREATROOT_LEAF_LITTER.get(),
                AetherIIBlocks.GREATOAK_LEAF_LITTER.get(),
                AetherIIBlocks.GREATBOA_LEAF_LITTER.get(),
                AetherIIBlocks.AMBEROOT_LEAF_LITTER.get(),
                AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_LITTER.get(),
                AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_LITTER.get(),
                AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_LITTER.get(),
                AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_LITTER.get(),
                AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_LITTER.get(),
                AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_LITTER.get(),
                AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_LITTER.get(),
                AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_LITTER.get(),
                AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_LITTER.get()
        );
        this.tag(AetherIITags.Blocks.SKYROOT_DECORATIVE_BLOCKS, AetherIITags.Items.SKYROOT_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.SKYROOT_FLOORBOARDS.get(),
                AetherIIBlocks.SKYROOT_HIGHLIGHT.get(),
                AetherIIBlocks.SKYROOT_SHINGLES.get(),
                AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get(),
                AetherIIBlocks.SKYROOT_BASE_PLANKS.get(),
                AetherIIBlocks.SKYROOT_TOP_PLANKS.get(),
                AetherIIBlocks.SKYROOT_BASE_BEAM.get(),
                AetherIIBlocks.SKYROOT_TOP_BEAM.get(),
                AetherIIBlocks.SKYROOT_BEAM.get()
        );
        this.tag(AetherIITags.Blocks.GREATROOT_DECORATIVE_BLOCKS, AetherIITags.Items.GREATROOT_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.GREATROOT_FLOORBOARDS.get(),
                AetherIIBlocks.GREATROOT_HIGHLIGHT.get(),
                AetherIIBlocks.GREATROOT_SHINGLES.get(),
                AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get(),
                AetherIIBlocks.GREATROOT_BASE_PLANKS.get(),
                AetherIIBlocks.GREATROOT_TOP_PLANKS.get(),
                AetherIIBlocks.GREATROOT_BASE_BEAM.get(),
                AetherIIBlocks.GREATROOT_TOP_BEAM.get(),
                AetherIIBlocks.GREATROOT_BEAM.get()
        );
        this.tag(AetherIITags.Blocks.WISPROOT_DECORATIVE_BLOCKS, AetherIITags.Items.WISPROOT_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.WISPROOT_FLOORBOARDS.get(),
                AetherIIBlocks.WISPROOT_HIGHLIGHT.get(),
                AetherIIBlocks.WISPROOT_SHINGLES.get(),
                AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get(),
                AetherIIBlocks.WISPROOT_BASE_PLANKS.get(),
                AetherIIBlocks.WISPROOT_TOP_PLANKS.get(),
                AetherIIBlocks.WISPROOT_BASE_BEAM.get(),
                AetherIIBlocks.WISPROOT_TOP_BEAM.get(),
                AetherIIBlocks.WISPROOT_BEAM.get()
        );
        this.tag(AetherIITags.Blocks.AMBEROOT_DECORATIVE_BLOCKS, AetherIITags.Items.AMBEROOT_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.AMBEROOT_FLOORBOARDS.get(),
                AetherIIBlocks.AMBEROOT_HIGHLIGHT.get(),
                AetherIIBlocks.AMBEROOT_SHINGLES.get(),
                AetherIIBlocks.AMBEROOT_SMALL_SHINGLES.get(),
                AetherIIBlocks.AMBEROOT_BASE_PLANKS.get(),
                AetherIIBlocks.AMBEROOT_TOP_PLANKS.get(),
                AetherIIBlocks.AMBEROOT_BASE_BEAM.get(),
                AetherIIBlocks.AMBEROOT_TOP_BEAM.get(),
                AetherIIBlocks.AMBEROOT_BEAM.get()
        );
        this.tag(AetherIITags.Blocks.HOLYSTONE_DECORATIVE_BLOCKS, AetherIITags.Items.HOLYSTONE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.HOLYSTONE_FLAGSTONES.get(),
                AetherIIBlocks.HOLYSTONE_HEADSTONE.get(),
                AetherIIBlocks.HOLYSTONE_KEYSTONE.get(),
                AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get(),
                AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get(),
                AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.HOLYSTONE_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.FADED_HOLYSTONE_DECORATIVE_BLOCKS, AetherIITags.Items.FADED_HOLYSTONE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get(),
                AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get(),
                AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get(),
                AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get(),
                AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.UNDERSHALE_DECORATIVE_BLOCKS, AetherIITags.Items.UNDERSHALE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(),
                AetherIIBlocks.UNDERSHALE_TILE.get(),
                AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(),
                AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get(),
                AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.UNDERSHALE_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.SENTRY_DECORATIVE_BLOCKS, AetherIITags.Items.SENTRY_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.SENTRY_LIGHTSTONE.get(),
                AetherIIBlocks.SENTRY_FLAGSTONES.get(),
                AetherIIBlocks.SENTRY_TILE.get(),
                AetherIIBlocks.SENTRY_BASE_BRICKS.get(),
                AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.SENTRY_BASE_PILLAR.get(),
                AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.SENTRY_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.ICHORITE_DECORATIVE_BLOCKS, AetherIITags.Items.ICHORITE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.ICHORITE_FLAGSTONES.get(),
                AetherIIBlocks.ICHORITE_RUNESTONE.get(),
                AetherIIBlocks.ICHORITE_KEYSTONE.get(),
                AetherIIBlocks.ICHORITE_BASE_BRICKS.get(),
                AetherIIBlocks.ICHORITE_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.ICHORITE_BASE_PILLAR.get(),
                AetherIIBlocks.ICHORITE_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.ICHORITE_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.MARBLED_ICHORITE_DECORATIVE_BLOCKS, AetherIITags.Items.MARBLED_ICHORITE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.MARBLED_FLAGSTONES.get(),
                AetherIIBlocks.MARBLED_KEYSTONE.get(),
                AetherIIBlocks.MARBLED_BASE_BRICKS.get(),
                AetherIIBlocks.MARBLED_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.MARBLED_BASE_PILLAR.get(),
                AetherIIBlocks.MARBLED_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.MARBLED_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.AGIOSITE_DECORATIVE_BLOCKS, AetherIITags.Items.AGIOSITE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.AGIOSITE_FLAGSTONES.get(),
                AetherIIBlocks.AGIOSITE_KEYSTONE.get(),
                AetherIIBlocks.AGIOSITE_BASE_BRICKS.get(),
                AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.AGIOSITE_BASE_PILLAR.get(),
                AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.AGIOSITE_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.ICESTONE_DECORATIVE_BLOCKS, AetherIITags.Items.ICESTONE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.ICESTONE_FLAGSTONES.get(),
                AetherIIBlocks.ICESTONE_KEYSTONE.get(),
                AetherIIBlocks.ICESTONE_BASE_BRICKS.get(),
                AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.ICESTONE_BASE_PILLAR.get(),
                AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.ICESTONE_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.QUICKSOIL_GLASS_DECORATIVE_BLOCKS, AetherIITags.Items.QUICKSOIL_GLASS_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.TILED_QUICKSOIL_GLASS.get(),
                AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS.get()
        );
        this.tag(AetherIITags.Blocks.QUICKSOIL_GLASS_PANE_DECORATIVE_BLOCKS, AetherIITags.Items.QUICKSOIL_GLASS_PANE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE.get(),
                AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE.get()
        );
        this.tag(AetherIITags.Blocks.CRUDE_SCATTERGLASS_DECORATIVE_BLOCKS, AetherIITags.Items.CRUDE_SCATTERGLASS_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get()
        );
        this.tag(AetherIITags.Blocks.CRUDE_SCATTERGLASS_PANE_DECORATIVE_BLOCKS, AetherIITags.Items.CRUDE_SCATTERGLASS_PANE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get()
        );
        this.tag(AetherIITags.Blocks.SCATTERGLASS_DECORATIVE_BLOCKS, AetherIITags.Items.SCATTERGLASS_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get()
        );
        this.tag(AetherIITags.Blocks.SCATTERGLASS_PANE_DECORATIVE_BLOCKS, AetherIITags.Items.SCATTERGLASS_PANE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get()
        );
        this.tag(AetherIITags.Blocks.ARKENIUM_BARS_DECORATIVE_BLOCKS, AetherIITags.Items.ARKENIUM_BARS_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.FLORAL_ARKENIUM_BARS.get(),
                AetherIIBlocks.PATTERNED_ARKENIUM_BARS.get(),
                AetherIIBlocks.CURVED_ARKENIUM_BARS.get(),
                AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get()
        );
        this.tag(AetherIITags.Blocks.RUSTIC_ARKENIUM_BARS_DECORATIVE_BLOCKS, AetherIITags.Items.RUSTIC_ARKENIUM_BARS_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.RUSTIC_FLORAL_ARKENIUM_BARS.get(),
                AetherIIBlocks.RUSTIC_PATTERNED_ARKENIUM_BARS.get(),
                AetherIIBlocks.RUSTIC_CURVED_ARKENIUM_BARS.get()
        );
        this.tag(AetherIITags.Blocks.QUICKSOIL_GLASS, AetherIITags.Items.QUICKSOIL_GLASS).add(
                AetherIIBlocks.QUICKSOIL_GLASS.get(),
                AetherIIBlocks.TILED_QUICKSOIL_GLASS.get(),
                AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS.get()
        );
        this.tag(AetherIITags.Blocks.CRUDE_SCATTERGLASS, AetherIITags.Items.CRUDE_SCATTERGLASS).add(
                AetherIIBlocks.CRUDE_SCATTERGLASS.get(),
                AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get()
        );
        this.tag(AetherIITags.Blocks.SCATTERGLASS, AetherIITags.Items.SCATTERGLASS).add(
                AetherIIBlocks.SCATTERGLASS.get(),
                AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get()
        );
        this.tag(AetherIITags.Blocks.QUICKSOIL_GLASS_PANE, AetherIITags.Items.QUICKSOIL_GLASS_PANE).add(
                AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(),
                AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE.get(),
                AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE.get()
        );
        this.tag(AetherIITags.Blocks.CRUDE_SCATTERGLASS_PANE, AetherIITags.Items.CRUDE_SCATTERGLASS_PANE).add(
                AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(),
                AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get()
        );
        this.tag(AetherIITags.Blocks.SCATTERGLASS_PANE, AetherIITags.Items.SCATTERGLASS_PANE).add(
                AetherIIBlocks.SCATTERGLASS_PANE.get(),
                AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get()
        );
        this.tag(AetherIITags.Blocks.ARKENIUM_BARS, AetherIITags.Items.ARKENIUM_BARS).add(
                AetherIIBlocks.ARKENIUM_BARS.get(),
                AetherIIBlocks.FLORAL_ARKENIUM_BARS.get(),
                AetherIIBlocks.PATTERNED_ARKENIUM_BARS.get(),
                AetherIIBlocks.CURVED_ARKENIUM_BARS.get()
        );
        this.tag(AetherIITags.Blocks.ARILUM_LANTERN, AetherIITags.Items.ARILUM_LANTERN).add(
                AetherIIBlocks.WHITE_ARILUM_LANTERN.get(),
                AetherIIBlocks.ORANGE_ARILUM_LANTERN.get(),
                AetherIIBlocks.MAGENTA_ARILUM_LANTERN.get(),
                AetherIIBlocks.LIGHT_BLUE_ARILUM_LANTERN.get(),
                AetherIIBlocks.YELLOW_ARILUM_LANTERN.get(),
                AetherIIBlocks.LIME_ARILUM_LANTERN.get(),
                AetherIIBlocks.PINK_CLOUDWOOL.get(),
                AetherIIBlocks.GRAY_ARILUM_LANTERN.get(),
                AetherIIBlocks.LIGHT_GRAY_ARILUM_LANTERN.get(),
                AetherIIBlocks.CYAN_ARILUM_LANTERN.get(),
                AetherIIBlocks.PURPLE_ARILUM_LANTERN.get(),
                AetherIIBlocks.BLUE_ARILUM_LANTERN.get(),
                AetherIIBlocks.BROWN_ARILUM_LANTERN.get(),
                AetherIIBlocks.GREEN_ARILUM_LANTERN.get(),
                AetherIIBlocks.RED_ARILUM_LANTERN.get(),
                AetherIIBlocks.BLACK_ARILUM_LANTERN.get()
        );

        // Vanilla
        this.tag(BlockTags.WOOL, ItemTags.WOOL).add(
                AetherIIBlocks.CLOUDWOOL.get(),
                AetherIIBlocks.WHITE_CLOUDWOOL.get(),
                AetherIIBlocks.ORANGE_CLOUDWOOL.get(),
                AetherIIBlocks.MAGENTA_CLOUDWOOL.get(),
                AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get(),
                AetherIIBlocks.YELLOW_CLOUDWOOL.get(),
                AetherIIBlocks.LIME_CLOUDWOOL.get(),
                AetherIIBlocks.PINK_CLOUDWOOL.get(),
                AetherIIBlocks.GRAY_CLOUDWOOL.get(),
                AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get(),
                AetherIIBlocks.CYAN_CLOUDWOOL.get(),
                AetherIIBlocks.PURPLE_CLOUDWOOL.get(),
                AetherIIBlocks.BLUE_CLOUDWOOL.get(),
                AetherIIBlocks.BROWN_CLOUDWOOL.get(),
                AetherIIBlocks.GREEN_CLOUDWOOL.get(),
                AetherIIBlocks.RED_CLOUDWOOL.get(),
                AetherIIBlocks.BLACK_CLOUDWOOL.get()
        );
        this.tag(BlockTags.PLANKS, ItemTags.PLANKS).add(
                AetherIIBlocks.SKYROOT_PLANKS.get(),
                AetherIIBlocks.GREATROOT_PLANKS.get(),
                AetherIIBlocks.WISPROOT_PLANKS.get()
        );
        this.tag(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS).add(
                AetherIIBlocks.SKYROOT_BUTTON.get(),
                AetherIIBlocks.GREATROOT_BUTTON.get(),
                AetherIIBlocks.WISPROOT_BUTTON.get(),
                AetherIIBlocks.AMBEROOT_BUTTON.get()
        );
        this.tag(BlockTags.STONE_BUTTONS, ItemTags.STONE_BUTTONS).add(
                AetherIIBlocks.HOLYSTONE_BUTTON.get(),
                AetherIIBlocks.UNDERSHALE_BRICK_BUTTON.get(),
                AetherIIBlocks.SENTRY_BUTTON.get()
        );
        this.tag(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS).add(
                AetherIIBlocks.CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.LIME_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.PINK_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.GRAY_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.CYAN_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.BLUE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.BROWN_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.GREEN_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.RED_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.BLACK_CLOUDWOOL_CARPET.get()
        );
        this.tag(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS).add(
                AetherIIBlocks.SKYROOT_DOOR.get(),
                AetherIIBlocks.GREATROOT_DOOR.get(),
                AetherIIBlocks.WISPROOT_DOOR.get(),
                AetherIIBlocks.SECRET_SKYROOT_DOOR.get(),
                AetherIIBlocks.SECRET_GREATROOT_DOOR.get(),
                AetherIIBlocks.SECRET_WISPROOT_DOOR.get(),
                AetherIIBlocks.SECRET_AMBEROOT_DOOR.get()
        );
        this.tag(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS).add(
                AetherIIBlocks.SKYROOT_STAIRS.get(),
                AetherIIBlocks.GREATROOT_STAIRS.get(),
                AetherIIBlocks.WISPROOT_STAIRS.get(),
                AetherIIBlocks.AMBEROOT_STAIRS.get()
        );
        this.tag(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS).add(
                AetherIIBlocks.SKYROOT_SLAB.get(),
                AetherIIBlocks.GREATROOT_SLAB.get(),
                AetherIIBlocks.WISPROOT_SLAB.get(),
                AetherIIBlocks.WISPROOT_SLAB.get()
        );
        this.tag(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES).add(
                AetherIIBlocks.SKYROOT_FENCE.get(),
                AetherIIBlocks.GREATROOT_FENCE.get(),
                AetherIIBlocks.WISPROOT_FENCE.get(),
                AetherIIBlocks.AMBEROOT_FENCE.get()
        );
        this.tag(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES).add(
                AetherIIBlocks.SKYROOT_FENCE_GATE.get(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.get(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.get(),
                AetherIIBlocks.AMBEROOT_FENCE_GATE.get()
        );
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES).add(
                AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.AMBEROOT_PRESSURE_PLATE.get()
        );
        this.tag(BlockTags.WOODEN_SHELVES, ItemTags.WOODEN_SHELVES).add(
                AetherIIBlocks.SKYROOT_SHELF.get(),
                AetherIIBlocks.GREATROOT_SHELF.get(),
                AetherIIBlocks.WISPROOT_SHELF.get(),
                AetherIIBlocks.AMBEROOT_SHELF.get()
        );
        this.tag(BlockTags.DOORS, ItemTags.DOORS).add(
                AetherIIBlocks.ARKENIUM_DOOR.get()
        );
        this.tag(BlockTags.SAPLINGS, ItemTags.SAPLINGS).add(
                AetherIIBlocks.SKYROOT_SAPLING.get(),
                AetherIIBlocks.SKYPLANE_SAPLING.get(),
                AetherIIBlocks.SKYBIRCH_SAPLING.get(),
                AetherIIBlocks.SKYPINE_SAPLING.get(),
                AetherIIBlocks.WISPROOT_SAPLING.get(),
                AetherIIBlocks.WISPTOP_SAPLING.get(),
                AetherIIBlocks.GREATROOT_SAPLING.get(),
                AetherIIBlocks.GREATOAK_SAPLING.get(),
                AetherIIBlocks.GREATBOA_SAPLING.get(),
                AetherIIBlocks.AMBEROOT_SAPLING.get()
        );
        this.tag(BlockTags.SAND, ItemTags.SAND).add(
                AetherIIBlocks.QUICKSOIL.get(),
                AetherIIBlocks.SHIMMERING_SILT.get(),
                AetherIIBlocks.FERROSITE_SAND.get()
        );
        this.tag(BlockTags.SLABS, ItemTags.SLABS).add(
                AetherIIBlocks.HOLYSTONE_SLAB.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(),
                AetherIIBlocks.UNDERSHALE_SLAB.get(),
                AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(),
                AetherIIBlocks.SENTRY_BRICK_SLAB.get(),
                AetherIIBlocks.AGIOSITE_SLAB.get(),
                AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(),
                AetherIIBlocks.ICESTONE_SLAB.get(),
                AetherIIBlocks.ICESTONE_BRICK_SLAB.get(),
                AetherIIBlocks.ICHORITE_SLAB.get(),
                AetherIIBlocks.SMOOTH_ICHORITE_SLAB.get(),
                AetherIIBlocks.ICHORITE_BRICK_SLAB.get(),
                AetherIIBlocks.MARBLED_ICHORITE_SLAB.get(),
                AetherIIBlocks.MARBLED_BRICK_SLAB.get()
        );
        this.tag(BlockTags.WALLS, ItemTags.WALLS).add(
                AetherIIBlocks.HOLYSTONE_WALL.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(),
                AetherIIBlocks.UNDERSHALE_WALL.get(),
                AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(),
                AetherIIBlocks.SENTRY_BRICK_WALL.get(),
                AetherIIBlocks.AGIOSITE_WALL.get(),
                AetherIIBlocks.AGIOSITE_BRICK_WALL.get(),
                AetherIIBlocks.ICESTONE_WALL.get(),
                AetherIIBlocks.ICESTONE_BRICK_WALL.get(),
                AetherIIBlocks.ICHORITE_WALL.get(),
                AetherIIBlocks.SMOOTH_ICHORITE_WALL.get(),
                AetherIIBlocks.ICHORITE_BRICK_WALL.get(),
                AetherIIBlocks.MARBLED_ICHORITE_WALL.get(),
                AetherIIBlocks.MARBLED_BRICK_WALL.get()
        );
        this.tag(BlockTags.STAIRS, ItemTags.STAIRS).add(
                AetherIIBlocks.HOLYSTONE_STAIRS.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get(),
                AetherIIBlocks.UNDERSHALE_STAIRS.get(),
                AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get(),
                AetherIIBlocks.SENTRY_BRICK_STAIRS.get(),
                AetherIIBlocks.AGIOSITE_STAIRS.get(),
                AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get(),
                AetherIIBlocks.ICESTONE_STAIRS.get(),
                AetherIIBlocks.ICESTONE_BRICK_STAIRS.get(),
                AetherIIBlocks.ICHORITE_STAIRS.get(),
                AetherIIBlocks.SMOOTH_ICHORITE_STAIRS.get(),
                AetherIIBlocks.ICHORITE_BRICK_STAIRS.get(),
                AetherIIBlocks.MARBLED_ICHORITE_STAIRS.get(),
                AetherIIBlocks.MARBLED_BRICK_STAIRS.get()
        );
        this.tag(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS).add(
                AetherIIBlocks.SKYROOT_TRAPDOOR.get(),
                AetherIIBlocks.GREATROOT_TRAPDOOR.get(),
                AetherIIBlocks.WISPROOT_TRAPDOOR.get(),
                AetherIIBlocks.AMBEROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_AMBEROOT_TRAPDOOR.get()
        );
        this.tag(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS).add(
                AetherIIBlocks.ARKENIUM_TRAPDOOR.get()
        );
        this.tag(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS).add(
                AetherIIBlocks.BLADE_POA.get(),
                AetherIIBlocks.HESPEROSE.get(),
                AetherIIBlocks.TARABLOOM.get(),
                AetherIIBlocks.POASPROUT.get(),
                AetherIIBlocks.LILICHIME.get(),
                AetherIIBlocks.PLURACIAN.get(),
                AetherIIBlocks.SATIVAL_SHOOT.get(),
                AetherIIBlocks.AECHOR_CUTTING.get(),
                AetherIIBlocks.CARRION_CUTTING.get()
        );
        this.tag(BlockTags.FLOWERS, ItemTags.FLOWERS).add(
                AetherIIBlocks.BRETTL_FLOWER.get(),
                AetherIIBlocks.HOLPUPEA.get(),
                AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(),
                AetherIIBlocks.TARAHESP_FLOWERS.get()
        );
        this.tag(BlockTags.BEDS, ItemTags.BEDS).add(
                AetherIIBlocks.CLOUDWOOL_BEDROLL.get(),
                AetherIIBlocks.SKYROOT_BED.get(),
                AetherIIBlocks.WHITE_SKYROOT_BED.get(),
                AetherIIBlocks.ORANGE_SKYROOT_BED.get(),
                AetherIIBlocks.MAGENTA_SKYROOT_BED.get(),
                AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED.get(),
                AetherIIBlocks.YELLOW_SKYROOT_BED.get(),
                AetherIIBlocks.LIME_SKYROOT_BED.get(),
                AetherIIBlocks.PINK_SKYROOT_BED.get(),
                AetherIIBlocks.GRAY_SKYROOT_BED.get(),
                AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED.get(),
                AetherIIBlocks.CYAN_SKYROOT_BED.get(),
                AetherIIBlocks.PURPLE_SKYROOT_BED.get(),
                AetherIIBlocks.BLUE_SKYROOT_BED.get(),
                AetherIIBlocks.BROWN_SKYROOT_BED.get(),
                AetherIIBlocks.GREEN_SKYROOT_BED.get(),
                AetherIIBlocks.RED_SKYROOT_BED.get(),
                AetherIIBlocks.BLACK_SKYROOT_BED.get()
        );
        this.tag(BlockTags.MUD, ItemTags.MUD).add(
                AetherIIBlocks.FERROSITE_MUD.get()
        );
        this.tag(BlockTags.CHAINS, ItemTags.CHAINS).add(
                AetherIIBlocks.ARKENIUM_CHAIN.get()
        );
        this.tag(BlockTags.LANTERNS, ItemTags.LANTERNS).add(
                AetherIIBlocks.ARKENIUM_LANTERN.get(),
                AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN.get()
        );
        this.tag(BlockTags.SIGNS, ItemTags.SIGNS).add(
                AetherIIBlocks.SKYROOT_SIGN.get(),
                AetherIIBlocks.GREATROOT_SIGN.get(),
                AetherIIBlocks.WISPROOT_SIGN.get(),
                AetherIIBlocks.AMBEROOT_SIGN.get()
        );

        // NeoForge
        this.tag(Tags.Blocks.BARRELS_WOODEN, Tags.Items.BARRELS_WOODEN).add(
                AetherIIBlocks.SKYROOT_BARREL.get()
        );
        this.tag(Tags.Blocks.BOOKSHELVES, Tags.Items.BOOKSHELVES).add(
                AetherIIBlocks.SKYROOT_BOOKSHELF.get(),
                AetherIIBlocks.GREATROOT_BOOKSHELF.get(),
                AetherIIBlocks.WISPROOT_BOOKSHELF.get(),
                AetherIIBlocks.AMBEROOT_BOOKSHELF.get(),
                AetherIIBlocks.HOLYSTONE_BOOKSHELF.get()
        );
        this.tag(Tags.Blocks.CHAINS, Tags.Items.CHAINS).add(
                AetherIIBlocks.ARKENIUM_CHAIN.get()
        );
        this.tag(Tags.Blocks.CHESTS, Tags.Items.CHESTS).add(
                AetherIIBlocks.SENTRY_CRATE.get()
        );
        this.tag(Tags.Blocks.CHESTS_WOODEN, Tags.Items.CHESTS_WOODEN).add(
                AetherIIBlocks.SKYROOT_CHEST.get(),
                AetherIIBlocks.SAGE_CHEST.get()
        );
        this.tag(Tags.Blocks.DYED_BLACK, Tags.Items.DYED_BLACK).add(
                AetherIIBlocks.BLACK_CLOUDWOOL.get(),
                AetherIIBlocks.BLACK_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.BLACK_ARILUM_LANTERN.get(),
                AetherIIBlocks.BLACK_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_BLUE, Tags.Items.DYED_BLUE).add(
                AetherIIBlocks.BLUE_CLOUDWOOL.get(),
                AetherIIBlocks.BLUE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.BLUE_ARILUM_LANTERN.get(),
                AetherIIBlocks.BLUE_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_BROWN, Tags.Items.DYED_BROWN).add(
                AetherIIBlocks.BROWN_CLOUDWOOL.get(),
                AetherIIBlocks.BROWN_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.BROWN_ARILUM_LANTERN.get(),
                AetherIIBlocks.BROWN_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_CYAN, Tags.Items.DYED_CYAN).add(
                AetherIIBlocks.CYAN_CLOUDWOOL.get(),
                AetherIIBlocks.CYAN_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.CYAN_ARILUM_LANTERN.get(),
                AetherIIBlocks.CYAN_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_GRAY, Tags.Items.DYED_GRAY).add(
                AetherIIBlocks.GRAY_CLOUDWOOL.get(),
                AetherIIBlocks.GRAY_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.GRAY_ARILUM_LANTERN.get(),
                AetherIIBlocks.GRAY_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_GREEN, Tags.Items.DYED_GREEN).add(
                AetherIIBlocks.GREEN_CLOUDWOOL.get(),
                AetherIIBlocks.GREEN_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.GREEN_ARILUM_LANTERN.get(),
                AetherIIBlocks.GREEN_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_LIGHT_BLUE, Tags.Items.DYED_LIGHT_BLUE).add(
                AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get(),
                AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.LIGHT_BLUE_ARILUM_LANTERN.get(),
                AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_LIGHT_GRAY, Tags.Items.DYED_LIGHT_GRAY).add(
                AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get(),
                AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.LIGHT_GRAY_ARILUM_LANTERN.get(),
                AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_LIME, Tags.Items.DYED_LIME).add(
                AetherIIBlocks.LIME_CLOUDWOOL.get(),
                AetherIIBlocks.LIME_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.LIME_ARILUM_LANTERN.get(),
                AetherIIBlocks.LIME_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_MAGENTA, Tags.Items.DYED_MAGENTA).add(
                AetherIIBlocks.MAGENTA_CLOUDWOOL.get(),
                AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.MAGENTA_ARILUM_LANTERN.get(),
                AetherIIBlocks.MAGENTA_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_ORANGE, Tags.Items.DYED_ORANGE).add(
                AetherIIBlocks.ORANGE_CLOUDWOOL.get(),
                AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.ORANGE_ARILUM_LANTERN.get(),
                AetherIIBlocks.ORANGE_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_PINK, Tags.Items.DYED_PINK).add(
                AetherIIBlocks.PINK_CLOUDWOOL.get(),
                AetherIIBlocks.PINK_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.PINK_ARILUM_LANTERN.get(),
                AetherIIBlocks.PINK_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_PURPLE, Tags.Items.DYED_PURPLE).add(
                AetherIIBlocks.PURPLE_CLOUDWOOL.get(),
                AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.PURPLE_ARILUM_LANTERN.get(),
                AetherIIBlocks.PURPLE_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_RED, Tags.Items.DYED_RED).add(
                AetherIIBlocks.RED_CLOUDWOOL.get(),
                AetherIIBlocks.RED_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.RED_ARILUM_LANTERN.get(),
                AetherIIBlocks.RED_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_WHITE, Tags.Items.DYED_WHITE).add(
                AetherIIBlocks.WHITE_CLOUDWOOL.get(),
                AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.WHITE_ARILUM_LANTERN.get(),
                AetherIIBlocks.WHITE_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.DYED_YELLOW, Tags.Items.DYED_YELLOW).add(
                AetherIIBlocks.YELLOW_CLOUDWOOL.get(),
                AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET.get(),
                AetherIIBlocks.YELLOW_ARILUM_LANTERN.get(),
                AetherIIBlocks.YELLOW_SKYROOT_BED.get()
        );
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN).add(
                AetherIIBlocks.SKYROOT_FENCE_GATE.get(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.get(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.get(),
                AetherIIBlocks.AMBEROOT_FENCE_GATE.get()
        );
        this.tag(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN).add(
                AetherIIBlocks.SKYROOT_FENCE.get(),
                AetherIIBlocks.GREATROOT_FENCE.get(),
                AetherIIBlocks.WISPROOT_FENCE.get(),
                AetherIIBlocks.AMBEROOT_FENCE.get()
        );
        this.tag(Tags.Blocks.GLASS_BLOCKS_COLORLESS, Tags.Items.GLASS_BLOCKS_COLORLESS).add(
                AetherIIBlocks.QUICKSOIL_GLASS.get(),
                AetherIIBlocks.TILED_QUICKSOIL_GLASS.get(),
                AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS.get(),
                AetherIIBlocks.SCATTERGLASS.get(),
                AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get()
        );
        this.tag(Tags.Blocks.GLASS_PANES_COLORLESS, Tags.Items.GLASS_PANES_COLORLESS).add(
                AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(),
                AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE.get(),
                AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE.get(),
                AetherIIBlocks.SCATTERGLASS_PANE.get(),
                AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get(),
                AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get()
        );
        this.tag(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR).add(
                AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get(),
                AetherIIBlocks.AMBROSIUM_ORE.get(),
                AetherIIBlocks.ZANITE_ORE.get(),
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get(),
                AetherIIBlocks.GLINT_ORE.get(),
                AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get(),
                AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get(),
                AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get(),
                AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get(),
                AetherIIBlocks.UNDERSHALE_GLINT_ORE.get(),
                AetherIIBlocks.CORROBONITE_ORE.get()
        );
        this.tag(Tags.Blocks.ORES, Tags.Items.ORES).add(
                AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get(),
                AetherIIBlocks.AMBROSIUM_ORE.get(),
                AetherIIBlocks.ZANITE_ORE.get(),
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get(),
                AetherIIBlocks.GLINT_ORE.get(),
                AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get(),
                AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get(),
                AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get(),
                AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get(),
                AetherIIBlocks.UNDERSHALE_GLINT_ORE.get(),
                AetherIIBlocks.CORROBONITE_ORE.get()
        );
        this.tag(Tags.Blocks.STONES, Tags.Items.STONES).addTags(
                AetherIITags.Blocks.HOLYSTONE,
                AetherIITags.Blocks.UNDERSHALE
        );
        this.tag(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS).add(
                AetherIIBlocks.INERT_ARKENIUM_BLOCK.get(),
                AetherIIBlocks.INERT_GRAVITITE_BLOCK.get(),
                AetherIIBlocks.AMBROSIUM_BLOCK.get(),
                AetherIIBlocks.ZANITE_BLOCK.get(),
                AetherIIBlocks.ARKENIUM_BLOCK.get(),
                AetherIIBlocks.GRAVITITE_BLOCK.get(),
                AetherIIBlocks.GLINT_BLOCK.get(),
                AetherIIBlocks.CORROBONITE_BLOCK.get(),
                AetherIIBlocks.GOLDEN_AMBER_BLOCK.get(),
                AetherIIBlocks.BRETTL_GRASS_BUNDLE.get()
        );
        this.tag(Tags.Blocks.NATURAL_WOODS, Tags.Items.NATURAL_WOODS).add(
                AetherIIBlocks.SKYROOT_WOOD.get(),
                AetherIIBlocks.GREATROOT_WOOD.get(),
                AetherIIBlocks.WISPROOT_WOOD.get(),
                AetherIIBlocks.MOSSY_WISPROOT_WOOD.get(),
                AetherIIBlocks.AMBEROOT_WOOD.get()
        );
        this.tag(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS).add(
                AetherIIBlocks.STRIPPED_SKYROOT_LOG.get(),
                AetherIIBlocks.STRIPPED_GREATROOT_LOG.get(),
                AetherIIBlocks.STRIPPED_WISPROOT_LOG.get(),
                AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get()
        );
        this.tag(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS).add(
                AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get(),
                AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get(),
                AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get(),
                AetherIIBlocks.STRIPPED_AMBEROOT_WOOD.get()
        );
    }

    protected abstract TagAppender<Block, Block> tag(TagKey<Block> blockKey, TagKey<Item> itemKey);
}