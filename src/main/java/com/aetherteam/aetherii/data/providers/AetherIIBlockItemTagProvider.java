package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public abstract class AetherIIBlockItemTagProvider {
    public AetherIIBlockItemTagProvider() {
    }

    public void run() {
        this.tag(AetherIITags.Blocks.AETHER_DIRT, AetherIITags.Items.AETHER_DIRT).add(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.AETHER_DIRT.get(),
                AetherIIBlocks.COARSE_AETHER_DIRT.get(),
                AetherIIBlocks.FERROSITE_MUD.get()
        );
        this.tag(AetherIITags.Blocks.HOLYSTONE, AetherIITags.Items.HOLYSTONE).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE.get()
        );
        this.tag(AetherIITags.Blocks.HOLYSTONE, AetherIITags.Items.HOLYSTONE).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE.get()
        );
        this.tag(AetherIITags.Blocks.FERROSITE, AetherIITags.Items.FERROSITE).add(
                AetherIIBlocks.FERROSITE.get(),
                AetherIIBlocks.RUSTED_FERROSITE.get()
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
                AetherIIBlocks.AMBEROOT_TRUNK.get()
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
        this.tag(AetherIITags.Blocks.DENSE_GUARDIAN_LOGS, AetherIITags.Items.DENSE_GUARDIAN_LOGS).add(
                AetherIIBlocks.DENSE_GUARDIAN_LOG.get(),
                AetherIIBlocks.DENSE_GUARDIAN_WOOD.get(),
                AetherIIBlocks.DENSE_STRIPPED_GUARDIAN_LOG.get(),
                AetherIIBlocks.DENSE_STRIPPED_GUARDIAN_WOOD.get(),
                AetherIIBlocks.DENSE_INFECTED_LOG.get(),
                AetherIIBlocks.DENSE_INFECTED_WOOD.get(),
                AetherIIBlocks.DENSE_STRIPPED_INFECTED_LOG.get(),
                AetherIIBlocks.DENSE_STRIPPED_INFECTED_WOOD.get()
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
        this.tag(AetherIITags.Blocks.LOCKED_DUNGEON_BLOCKS, AetherIITags.Items.LOCKED_DUNGEON_BLOCKS).add(

        );
    }

    protected abstract TagAppender<Block, Block> tag(TagKey<Block> blockKey, TagKey<Item> itemKey);
}