package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherIIBlockTagData extends BlockTagsProvider {
    public AetherIIBlockTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, AetherII.MODID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {
        // Aether II
        this.tag(AetherIITags.Blocks.AETHER_PORTAL_BLOCKS).add(
                Blocks.QUARTZ_BLOCK,
                Blocks.SMOOTH_QUARTZ,
                Blocks.QUARTZ_BRICKS,
                Blocks.QUARTZ_PILLAR,
                Blocks.CHISELED_QUARTZ_BLOCK
        );
        this.tag(AetherIITags.Blocks.AETHER_PORTAL_BLACKLIST).add(
                AetherIIBlocks.BLUE_AERCLOUD.get(),
                AetherIIBlocks.GREEN_AERCLOUD.get(),
                AetherIIBlocks.PURPLE_AERCLOUD.get(),
                AetherIIBlocks.STORM_AERCLOUD.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_DIRT).add(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.AETHER_DIRT.get(),
                AetherIIBlocks.COARSE_AETHER_DIRT.get()
        );
        this.tag(AetherIITags.Blocks.HOLYSTONE).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.UNDERSHALE.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_CARVER_REPLACEABLES).addTags(
                AetherIITags.Blocks.AETHER_DIRT,
                AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS
        );
        this.tag(AetherIITags.Blocks.FERROSITE).add(
                AetherIIBlocks.FERROSITE.get(),
                AetherIIBlocks.RUSTED_FERROSITE.get()
        );
        this.tag(AetherIITags.Blocks.AERCLOUDS).add(
                AetherIIBlocks.COLD_AERCLOUD.get(),
                AetherIIBlocks.BLUE_AERCLOUD.get(),
                AetherIIBlocks.GOLDEN_AERCLOUD.get(),
                AetherIIBlocks.GREEN_AERCLOUD.get(),
                AetherIIBlocks.PURPLE_AERCLOUD.get(),
                AetherIIBlocks.STORM_AERCLOUD.get()
        );
        this.tag(AetherIITags.Blocks.SKYROOT_LOGS).add(
                AetherIIBlocks.SKYROOT_LOG.get(),
                AetherIIBlocks.SKYROOT_WOOD.get(),
                AetherIIBlocks.STRIPPED_SKYROOT_LOG.get(),
                AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get()
        );
        this.tag(AetherIITags.Blocks.GREATROOT_LOGS).add(
                AetherIIBlocks.GREATROOT_LOG.get(),
                AetherIIBlocks.GREATROOT_WOOD.get()
        );
        this.tag(AetherIITags.Blocks.WISPROOT_LOGS).add(
                AetherIIBlocks.WISPROOT_LOG.get(),
                AetherIIBlocks.MOSSY_WISPROOT_LOG.get(),
                AetherIIBlocks.WISPROOT_WOOD.get()
        );
        this.tag(AetherIITags.Blocks.AMBEROOT_LOGS).add(
                AetherIIBlocks.AMBEROOT_LOG.get(),
                AetherIIBlocks.AMBEROOT_WOOD.get()
        );
        this.tag(AetherIITags.Blocks.SKYROOT_DECORATIVE_BLOCKS).add(
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
        this.tag(AetherIITags.Blocks.GREATROOT_DECORATIVE_BLOCKS).add(
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
        this.tag(AetherIITags.Blocks.WISPROOT_DECORATIVE_BLOCKS).add(
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
        this.tag(AetherIITags.Blocks.HOLYSTONE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.HOLYSTONE_FLAGSTONES.get(),
                AetherIIBlocks.HOLYSTONE_HEADSTONE.get(),
                AetherIIBlocks.HOLYSTONE_KEYSTONE.get(),
                AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get(),
                AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get(),
                AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.HOLYSTONE_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.FADED_HOLYSTONE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get(),
                AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get(),
                AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get(),
                AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get(),
                AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.AGIOSITE_DECORATIVE_BLOCKS).add(
                AetherIIBlocks.AGIOSITE_FLAGSTONES.get(),
                AetherIIBlocks.AGIOSITE_KEYSTONE.get(),
                AetherIIBlocks.AGIOSITE_BASE_BRICKS.get(),
                AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get(),
                AetherIIBlocks.AGIOSITE_BASE_PILLAR.get(),
                AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get(),
                AetherIIBlocks.AGIOSITE_PILLAR.get()
        );
        this.tag(AetherIITags.Blocks.AETHER_ANIMALS_SPAWNABLE_ON).add(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get()
        );
        this.tag(AetherIITags.Blocks.COCKATRICE_SPAWNABLE_BLACKLIST);

        this.tag(AetherIITags.Blocks.MOA_HATCH_BLOCK).add(
                AetherIIBlocks.WOVEN_SKYROOT_STICKS.get()
        );

        // Vanilla
        this.tag(BlockTags.WOOL).add(
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
        this.tag(BlockTags.WOODEN_STAIRS).add(
                AetherIIBlocks.SKYROOT_STAIRS.get(),
                AetherIIBlocks.GREATROOT_STAIRS.get(),
                AetherIIBlocks.WISPROOT_STAIRS.get()
        );
        this.tag(BlockTags.WOODEN_SLABS).add(
                AetherIIBlocks.SKYROOT_SLAB.get(),
                AetherIIBlocks.GREATROOT_STAIRS.get(),
                AetherIIBlocks.WISPROOT_STAIRS.get()
        );
        this.tag(BlockTags.WOODEN_FENCES).add(
                AetherIIBlocks.SKYROOT_FENCE.get(),
                AetherIIBlocks.GREATROOT_FENCE.get(),
                AetherIIBlocks.WISPROOT_FENCE.get()
        );
        this.tag(BlockTags.WOODEN_DOORS).add(
                AetherIIBlocks.SKYROOT_DOOR.get(),
                AetherIIBlocks.GREATROOT_DOOR.get(),
                AetherIIBlocks.WISPROOT_DOOR.get(),
                AetherIIBlocks.SECRET_SKYROOT_DOOR.get(),
                AetherIIBlocks.SECRET_GREATROOT_DOOR.get(),
                AetherIIBlocks.SECRET_WISPROOT_DOOR.get()
        );
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(
                AetherIIBlocks.SKYROOT_TRAPDOOR.get(),
                AetherIIBlocks.GREATROOT_TRAPDOOR.get(),
                AetherIIBlocks.WISPROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get()
        );
        this.tag(BlockTags.DOORS).add(
                AetherIIBlocks.ARKENIUM_DOOR.get()
        );
        this.tag(BlockTags.WOODEN_BUTTONS).add(
                AetherIIBlocks.SKYROOT_BUTTON.get(),
                AetherIIBlocks.GREATROOT_BUTTON.get(),
                AetherIIBlocks.WISPROOT_BUTTON.get()
        );
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get()
        );
        this.tag(BlockTags.BUTTONS).add(
                AetherIIBlocks.SKYROOT_BUTTON.get(),
                AetherIIBlocks.GREATROOT_BUTTON.get(),
                AetherIIBlocks.WISPROOT_BUTTON.get(),
                AetherIIBlocks.HOLYSTONE_BUTTON.get()
        );
        this.tag(BlockTags.STONE_BUTTONS).add(AetherIIBlocks.HOLYSTONE_BUTTON.get());
        this.tag(BlockTags.PRESSURE_PLATES).add(
                AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get()
        );
        this.tag(BlockTags.WOOL_CARPETS).add(
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
        this.tag(BlockTags.SAPLINGS).add(
                AetherIIBlocks.SKYROOT_SAPLING.get(),
                AetherIIBlocks.SKYBIRCH_SAPLING.get(),
                AetherIIBlocks.WISPROOT_SAPLING.get(),
                AetherIIBlocks.WISPTOP_SAPLING.get(),
                AetherIIBlocks.GREATROOT_SAPLING.get(),
                AetherIIBlocks.GREATOAK_SAPLING.get(),
                AetherIIBlocks.GREATBOA_SAPLING.get(),
                AetherIIBlocks.AMBEROOT_SAPLING.get()
        );
        this.tag(BlockTags.LOGS_THAT_BURN).addTags(
                AetherIITags.Blocks.SKYROOT_LOGS,
                AetherIITags.Blocks.GREATROOT_LOGS,
                AetherIITags.Blocks.WISPROOT_LOGS,
                AetherIITags.Blocks.AMBEROOT_LOGS
        );
        this.tag(BlockTags.STAIRS).add(
                AetherIIBlocks.HOLYSTONE_STAIRS.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get(),
                AetherIIBlocks.UNDERSHALE_STAIRS.get(),
                AetherIIBlocks.AGIOSITE_STAIRS.get(),
                AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get(),
                AetherIIBlocks.ICESTONE_STAIRS.get()
        );
        this.tag(BlockTags.SLABS).add(
                AetherIIBlocks.HOLYSTONE_SLAB.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(),
                AetherIIBlocks.UNDERSHALE_SLAB.get(),
                AetherIIBlocks.AGIOSITE_SLAB.get(),
                AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(),
                AetherIIBlocks.ICESTONE_SLAB.get()
        );
        this.tag(BlockTags.WALLS).add(
                AetherIIBlocks.HOLYSTONE_WALL.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(),
                AetherIIBlocks.UNDERSHALE_WALL.get(),
                AetherIIBlocks.AGIOSITE_WALL.get(),
                AetherIIBlocks.AGIOSITE_BRICK_WALL.get(),
                AetherIIBlocks.ICESTONE_WALL.get()
        );
        this.tag(BlockTags.LEAVES).add(
                AetherIIBlocks.SKYROOT_LEAVES.get(),
                AetherIIBlocks.SKYPLANE_LEAVES.get(),
                AetherIIBlocks.SKYBIRCH_LEAVES.get(),
                AetherIIBlocks.SKYPINE_LEAVES.get(),
                AetherIIBlocks.WISPROOT_LEAVES.get(),
                AetherIIBlocks.WISPTOP_LEAVES.get(),
                AetherIIBlocks.GREATROOT_LEAVES.get(),
                AetherIIBlocks.GREATOAK_LEAVES.get(),
                AetherIIBlocks.GREATBOA_LEAVES.get(),
                AetherIIBlocks.AMBEROOT_LEAVES.get()
        );
        this.tag(BlockTags.TRAPDOORS).add(
                AetherIIBlocks.ARKENIUM_TRAPDOOR.get()
        );
        this.tag(BlockTags.SMALL_FLOWERS).add(
                AetherIIBlocks.HESPEROSE.get(),
                AetherIIBlocks.TARABLOOM.get()
        );
        this.tag(BlockTags.BEDS).add(AetherIIBlocks.SKYROOT_BED.get());
        this.tag(BlockTags.DIRT).addTag(AetherIITags.Blocks.AETHER_DIRT);
        this.tag(BlockTags.FLOWER_POTS).add(
                AetherIIBlocks.POTTED_SKYROOT_SAPLING.get(),
                AetherIIBlocks.POTTED_SKYBIRCH_SAPLING.get(),
                AetherIIBlocks.POTTED_WISPROOT_SAPLING.get(),
                AetherIIBlocks.POTTED_WISPTOP_SAPLING.get(),
                AetherIIBlocks.POTTED_GREATROOT_SAPLING.get(),
                AetherIIBlocks.POTTED_GREATOAK_SAPLING.get(),
                AetherIIBlocks.POTTED_GREATBOA_SAPLING.get(),
                AetherIIBlocks.POTTED_AMBEROOT_SAPLING.get(),
                AetherIIBlocks.POTTED_HIGHLANDS_BUSH.get(),
                AetherIIBlocks.POTTED_BLUEBERRY_BUSH.get(),
                AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM.get(),
                AetherIIBlocks.POTTED_ORANGE_TREE.get(),
                AetherIIBlocks.POTTED_HESPEROSE.get(),
                AetherIIBlocks.POTTED_TARABLOOM.get()
        );
        this.tag(BlockTags.ENDERMAN_HOLDABLE).addTag(AetherIITags.Blocks.AETHER_DIRT).add(
                AetherIIBlocks.QUICKSOIL.get(),
                AetherIIBlocks.HESPEROSE.get(),
                AetherIIBlocks.TARABLOOM.get()
        );
        this.tag(BlockTags.ICE).add(
                AetherIIBlocks.ARCTIC_ICE.get(),
                AetherIIBlocks.ARCTIC_PACKED_ICE.get()
        );
        this.tag(BlockTags.VALID_SPAWN).addTag(AetherIITags.Blocks.AETHER_DIRT);
        this.tag(BlockTags.IMPERMEABLE).add(
                AetherIIBlocks.QUICKSOIL_GLASS.get(),
                AetherIIBlocks.CRUDE_SCATTERGLASS.get(),
                AetherIIBlocks.SCATTERGLASS.get()
        );
        this.tag(BlockTags.BAMBOO_PLANTABLE_ON).addTag(AetherIITags.Blocks.AETHER_DIRT);
        this.tag(BlockTags.SIGNS).add(
                AetherIIBlocks.SKYROOT_SIGN.get(),
                AetherIIBlocks.SKYROOT_WALL_SIGN.get(),
                AetherIIBlocks.GREATROOT_SIGN.get(),
                AetherIIBlocks.GREATROOT_WALL_SIGN.get(),
                AetherIIBlocks.WISPROOT_SIGN.get(),
                AetherIIBlocks.WISPROOT_WALL_SIGN.get()
        );
        this.tag(BlockTags.STANDING_SIGNS).add(
                AetherIIBlocks.SKYROOT_SIGN.get(),
                AetherIIBlocks.GREATROOT_SIGN.get(),
                AetherIIBlocks.WISPROOT_SIGN.get()
        );
        this.tag(BlockTags.WALL_SIGNS).add(
                AetherIIBlocks.SKYROOT_WALL_SIGN.get(),
                AetherIIBlocks.GREATROOT_WALL_SIGN.get(),
                AetherIIBlocks.WISPROOT_WALL_SIGN.get()
        );
        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(
                AetherIIBlocks.SKYROOT_HANGING_SIGN.get(),
                AetherIIBlocks.GREATROOT_HANGING_SIGN.get(),
                AetherIIBlocks.WISPROOT_HANGING_SIGN.get()
        );
        this.tag(BlockTags.WALL_HANGING_SIGNS).add(
                AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get(),
                AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get(),
                AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get()
        );
        this.tag(BlockTags.PORTALS).add(AetherIIBlocks.AETHER_PORTAL.get());
        this.tag(BlockTags.BEACON_BASE_BLOCKS).add(
                AetherIIBlocks.AMBROSIUM_BLOCK.get(),
                AetherIIBlocks.ZANITE_BLOCK.get(),
                AetherIIBlocks.GRAVITITE_BLOCK.get()
        );
        this.tag(BlockTags.WALL_POST_OVERRIDE).add(AetherIIBlocks.AMBROSIUM_TORCH.get());
        this.tag(BlockTags.CLIMBABLE).add(AetherIIBlocks.SKYROOT_LADDER.get());
        this.tag(BlockTags.FENCE_GATES).add(
                AetherIIBlocks.SKYROOT_FENCE_GATE.get(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.get(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.get()
        );
        this.tag(BlockTags.SNOW).add(
                AetherIIBlocks.ARCTIC_SNOW_BLOCK.get(),
                AetherIIBlocks.ARCTIC_SNOW.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE.get(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE.get(),
                AetherIIBlocks.UNDERSHALE.get(),
                AetherIIBlocks.AGIOSITE.get(),
                AetherIIBlocks.CRUDE_SCATTERGLASS.get(),
                AetherIIBlocks.FERROSITE.get(),
                AetherIIBlocks.RUSTED_FERROSITE.get(),
                AetherIIBlocks.ARCTIC_ICE.get(),
                AetherIIBlocks.ARCTIC_PACKED_ICE.get(),
                AetherIIBlocks.ICESTONE.get(),
                AetherIIBlocks.AMBROSIUM_ORE.get(),
                AetherIIBlocks.ZANITE_ORE.get(),
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get(),
                AetherIIBlocks.HOLYSTONE_STAIRS.get(),
                AetherIIBlocks.HOLYSTONE_SLAB.get(),
                AetherIIBlocks.HOLYSTONE_WALL.get(),
                AetherIIBlocks.HOLYSTONE_BUTTON.get(),
                AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(),
                AetherIIBlocks.HOLYSTONE_BRICKS.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(),
                AetherIIBlocks.UNDERSHALE_STAIRS.get(),
                AetherIIBlocks.UNDERSHALE_SLAB.get(),
                AetherIIBlocks.UNDERSHALE_WALL.get(),
                AetherIIBlocks.AGIOSITE_STAIRS.get(),
                AetherIIBlocks.AGIOSITE_SLAB.get(),
                AetherIIBlocks.AGIOSITE_WALL.get(),
                AetherIIBlocks.AGIOSITE_BRICKS.get(),
                AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get(),
                AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(),
                AetherIIBlocks.AGIOSITE_BRICK_WALL.get(),
                AetherIIBlocks.ICESTONE_STAIRS.get(),
                AetherIIBlocks.ICESTONE_SLAB.get(),
                AetherIIBlocks.ICESTONE_WALL.get(),
                AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(),
                AetherIIBlocks.SCATTERGLASS.get(),
                AetherIIBlocks.SCATTERGLASS_PANE.get(),
                AetherIIBlocks.AMBROSIUM_BLOCK.get(),
                AetherIIBlocks.ZANITE_BLOCK.get(),
                AetherIIBlocks.GRAVITITE_BLOCK.get(),
                AetherIIBlocks.HOLYSTONE_FURNACE.get(),
                AetherIIBlocks.ALTAR.get(),
                AetherIIBlocks.ARTISANS_BENCH.get(),
                AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(),
                AetherIIBlocks.ARKENIUM_DOOR.get(),
                AetherIIBlocks.ARKENIUM_TRAPDOOR.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                AetherIIBlocks.SKYROOT_LOG.get(),
                AetherIIBlocks.STRIPPED_SKYROOT_LOG.get(),
                AetherIIBlocks.GREATROOT_LOG.get(),
                AetherIIBlocks.WISPROOT_LOG.get(),
                AetherIIBlocks.MOSSY_WISPROOT_LOG.get(),
                AetherIIBlocks.AMBEROOT_LOG.get(),
                AetherIIBlocks.SKYROOT_WOOD.get(),
                AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get(),
                AetherIIBlocks.GREATROOT_WOOD.get(),
                AetherIIBlocks.WISPROOT_WOOD.get(),
                AetherIIBlocks.AMBEROOT_WOOD.get(),
                AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(),
                AetherIIBlocks.ORANGE_TREE.get(),
                AetherIIBlocks.SKYROOT_PLANKS.get(),
                AetherIIBlocks.SKYROOT_FENCE.get(),
                AetherIIBlocks.SKYROOT_FENCE_GATE.get(),
                AetherIIBlocks.SKYROOT_DOOR.get(),
                AetherIIBlocks.SKYROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_SKYROOT_DOOR.get(),
                AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get(),
                AetherIIBlocks.SKYROOT_BUTTON.get(),
                AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.SKYROOT_STAIRS.get(),
                AetherIIBlocks.SKYROOT_SLAB.get(),
                AetherIIBlocks.GREATROOT_PLANKS.get(),
                AetherIIBlocks.GREATROOT_FENCE.get(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.get(),
                AetherIIBlocks.GREATROOT_DOOR.get(),
                AetherIIBlocks.GREATROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_GREATROOT_DOOR.get(),
                AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get(),
                AetherIIBlocks.GREATROOT_BUTTON.get(),
                AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.GREATROOT_STAIRS.get(),
                AetherIIBlocks.GREATROOT_SLAB.get(),
                AetherIIBlocks.WISPROOT_PLANKS.get(),
                AetherIIBlocks.WISPROOT_FENCE.get(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.get(),
                AetherIIBlocks.WISPROOT_DOOR.get(),
                AetherIIBlocks.WISPROOT_TRAPDOOR.get(),
                AetherIIBlocks.SECRET_WISPROOT_DOOR.get(),
                AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get(),
                AetherIIBlocks.WISPROOT_BUTTON.get(),
                AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.WISPROOT_STAIRS.get(),
                AetherIIBlocks.WISPROOT_SLAB.get(),
                AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get(),
                AetherIIBlocks.SKYROOT_CHEST.get(),
                AetherIIBlocks.SKYROOT_BOOKSHELF.get(),
                AetherIIBlocks.SKYROOT_FLOORBOARDS.get(),
                AetherIIBlocks.SKYROOT_HIGHLIGHT.get(),
                AetherIIBlocks.SKYROOT_SHINGLES.get(),
                AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get(),
                AetherIIBlocks.SKYROOT_BASE_PLANKS.get(),
                AetherIIBlocks.SKYROOT_TOP_PLANKS.get(),
                AetherIIBlocks.SKYROOT_BASE_BEAM.get(),
                AetherIIBlocks.SKYROOT_TOP_BEAM.get(),
                AetherIIBlocks.SKYROOT_BEAM.get(),
                AetherIIBlocks.GREATROOT_FLOORBOARDS.get(),
                AetherIIBlocks.GREATROOT_HIGHLIGHT.get(),
                AetherIIBlocks.GREATROOT_SHINGLES.get(),
                AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get(),
                AetherIIBlocks.GREATROOT_BASE_PLANKS.get(),
                AetherIIBlocks.GREATROOT_TOP_PLANKS.get(),
                AetherIIBlocks.GREATROOT_BASE_BEAM.get(),
                AetherIIBlocks.GREATROOT_TOP_BEAM.get(),
                AetherIIBlocks.GREATROOT_BEAM.get(),
                AetherIIBlocks.WISPROOT_FLOORBOARDS.get(),
                AetherIIBlocks.WISPROOT_HIGHLIGHT.get(),
                AetherIIBlocks.WISPROOT_SHINGLES.get(),
                AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get(),
                AetherIIBlocks.WISPROOT_BASE_PLANKS.get(),
                AetherIIBlocks.WISPROOT_TOP_PLANKS.get(),
                AetherIIBlocks.WISPROOT_BASE_BEAM.get(),
                AetherIIBlocks.WISPROOT_TOP_BEAM.get(),
                AetherIIBlocks.WISPROOT_BEAM.get(),
                AetherIIBlocks.SKYROOT_BED.get(),
                AetherIIBlocks.SKYROOT_SIGN.get(),
                AetherIIBlocks.SKYROOT_WALL_SIGN.get(),
                AetherIIBlocks.SKYROOT_HANGING_SIGN.get(),
                AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get(),
                AetherIIBlocks.GREATROOT_SIGN.get(),
                AetherIIBlocks.GREATROOT_WALL_SIGN.get(),
                AetherIIBlocks.GREATROOT_HANGING_SIGN.get(),
                AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get(),
                AetherIIBlocks.WISPROOT_SIGN.get(),
                AetherIIBlocks.WISPROOT_WALL_SIGN.get(),
                AetherIIBlocks.WISPROOT_HANGING_SIGN.get(),
                AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.AETHER_DIRT_PATH.get(),
                AetherIIBlocks.AETHER_DIRT.get(),
                AetherIIBlocks.COARSE_AETHER_DIRT.get(),
                AetherIIBlocks.AETHER_FARMLAND.get(),
                AetherIIBlocks.QUICKSOIL.get(),
                AetherIIBlocks.FERROSITE_SAND.get(),
                AetherIIBlocks.ARCTIC_SNOW_BLOCK.get(),
                AetherIIBlocks.ARCTIC_SNOW.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                AetherIIBlocks.COLD_AERCLOUD.get(),
                AetherIIBlocks.BLUE_AERCLOUD.get(),
                AetherIIBlocks.GOLDEN_AERCLOUD.get(),
                AetherIIBlocks.GREEN_AERCLOUD.get(),
                AetherIIBlocks.PURPLE_AERCLOUD.get(),
                AetherIIBlocks.STORM_AERCLOUD.get(),
                AetherIIBlocks.SKYROOT_LEAF_PILE.get(),
                AetherIIBlocks.SKYPLANE_LEAF_PILE.get(),
                AetherIIBlocks.SKYBIRCH_LEAF_PILE.get(),
                AetherIIBlocks.SKYPINE_LEAF_PILE.get(),
                AetherIIBlocks.WISPROOT_LEAF_PILE.get(),
                AetherIIBlocks.WISPTOP_LEAF_PILE.get(),
                AetherIIBlocks.GREATROOT_LEAF_PILE.get(),
                AetherIIBlocks.GREATOAK_LEAF_PILE.get(),
                AetherIIBlocks.GREATBOA_LEAF_PILE.get(),
                AetherIIBlocks.AMBEROOT_LEAF_PILE.get(),
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
                AetherIIBlocks.WOVEN_SKYROOT_STICKS.get(),
                AetherIIBlocks.HIGHLANDS_BUSH.get(),
                AetherIIBlocks.BLUEBERRY_BUSH.get()
        );
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                AetherIIBlocks.ICESTONE.get(),
                AetherIIBlocks.ICESTONE_STAIRS.get(),
                AetherIIBlocks.ICESTONE_SLAB.get(),
                AetherIIBlocks.ICESTONE_WALL.get(),
                AetherIIBlocks.ZANITE_ORE.get(),
                AetherIIBlocks.ZANITE_BLOCK.get()
        );
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get(),
                AetherIIBlocks.GRAVITITE_BLOCK.get()
        );
        this.tag(BlockTags.CONVERTABLE_TO_MUD).add(AetherIIBlocks.AETHER_DIRT.get());
        this.tag(BlockTags.SCULK_REPLACEABLE).addTag(AetherIITags.Blocks.HOLYSTONE).add(
                AetherIIBlocks.AETHER_DIRT.get(),
                AetherIIBlocks.QUICKSOIL.get()
        );
        this.tag(BlockTags.SNAPS_GOAT_HORN).addTags(AetherIITags.Blocks.SKYROOT_LOGS, AetherIITags.Blocks.GREATROOT_LOGS, AetherIITags.Blocks.WISPROOT_LOGS, AetherIITags.Blocks.AMBEROOT_LOGS).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.ICESTONE.get(),
                AetherIIBlocks.AMBROSIUM_ORE.get(),
                AetherIIBlocks.ZANITE_ORE.get(),
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get()
        );
        this.tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(
                AetherIIBlocks.SKYROOT_BOOKSHELF.get(),
                AetherIIBlocks.HOLYSTONE_BOOKSHELF.get()
        );
        this.tag(BlockTags.SNIFFER_DIGGABLE_BLOCK).add(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.AETHER_DIRT.get(),
                AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()
        );

        // Forge
        this.tag(Tags.Blocks.BOOKSHELVES).add(
                AetherIIBlocks.SKYROOT_BOOKSHELF.get(),
                AetherIIBlocks.HOLYSTONE_BOOKSHELF.get()
        );
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(
                AetherIIBlocks.SKYROOT_FENCE_GATE.get(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.get(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.get()
        );
        this.tag(Tags.Blocks.FENCES_WOODEN).add(
                AetherIIBlocks.SKYROOT_FENCE.get(),
                AetherIIBlocks.GREATROOT_FENCE.get(),
                AetherIIBlocks.WISPROOT_FENCE.get()
        );
        this.tag(Tags.Blocks.GLASS_COLORLESS).add(
                AetherIIBlocks.QUICKSOIL_GLASS.get(),
                AetherIIBlocks.SCATTERGLASS.get()
        );
        this.tag(Tags.Blocks.GLASS_PANES_COLORLESS).add(
                AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(),
                AetherIIBlocks.SCATTERGLASS_PANE.get()
        );
        this.tag(Tags.Blocks.ORE_RATES_SINGULAR).add(
                AetherIIBlocks.AMBROSIUM_ORE.get(),
                AetherIIBlocks.ZANITE_ORE.get(),
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get()
        );
        this.tag(Tags.Blocks.ORES).add(
                AetherIIBlocks.AMBROSIUM_ORE.get(),
                AetherIIBlocks.ZANITE_ORE.get(),
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get()
        );
        this.tag(Tags.Blocks.STONE).addTag(AetherIITags.Blocks.HOLYSTONE).add(AetherIIBlocks.UNDERSHALE.get());
        this.tag(Tags.Blocks.STORAGE_BLOCKS).add(
                AetherIIBlocks.AMBROSIUM_BLOCK.get(),
                AetherIIBlocks.ZANITE_BLOCK.get(),
                AetherIIBlocks.GRAVITITE_BLOCK.get()
        );
        this.tag(Tags.Blocks.CHESTS_WOODEN).add(
                AetherIIBlocks.SKYROOT_CHEST.get()
        );
    }
}