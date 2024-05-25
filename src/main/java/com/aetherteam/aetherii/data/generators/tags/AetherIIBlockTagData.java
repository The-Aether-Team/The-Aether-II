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
        this.tag(AetherIITags.Blocks.AETHER_PORTAL_BLOCKS).add(
                Blocks.QUARTZ_BLOCK,
                Blocks.SMOOTH_QUARTZ,
                Blocks.QUARTZ_BRICKS,
                Blocks.QUARTZ_PILLAR,
                Blocks.CHISELED_QUARTZ_BLOCK);
        this.tag(AetherIITags.Blocks.AETHER_PORTAL_BLACKLIST);
        this.tag(AetherIITags.Blocks.AETHER_DIRT).add(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.AETHER_DIRT.get());
        this.tag(AetherIITags.Blocks.AERCLOUDS).add(
                AetherIIBlocks.COLD_AERCLOUD.get());
        this.tag(AetherIITags.Blocks.SKYROOT_LOGS).add(
                AetherIIBlocks.SKYROOT_LOG.get(),
                AetherIIBlocks.SKYROOT_WOOD.get());
        this.tag(AetherIITags.Blocks.GREATROOT_LOGS).add(
                AetherIIBlocks.GREATROOT_LOG.get(),
                AetherIIBlocks.GREATROOT_WOOD.get());
        this.tag(AetherIITags.Blocks.WISPROOT_LOGS).add(
                AetherIIBlocks.WISPROOT_LOG.get(),
                AetherIIBlocks.WISPROOT_WOOD.get());
        this.tag(AetherIITags.Blocks.AMBEROOT_LOGS).add(
                AetherIIBlocks.AMBEROOT_LOG.get(),
                AetherIIBlocks.AMBEROOT_WOOD.get());

        // Vanilla
        this.tag(BlockTags.WOODEN_STAIRS).add(
                AetherIIBlocks.SKYROOT_STAIRS.get(),
                AetherIIBlocks.GREATROOT_STAIRS.get(),
                AetherIIBlocks.WISPROOT_STAIRS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(
                AetherIIBlocks.SKYROOT_SLAB.get(),
                AetherIIBlocks.GREATROOT_SLAB.get(),
                AetherIIBlocks.WISPROOT_SLAB.get());
        this.tag(BlockTags.WOODEN_FENCES).add(
                AetherIIBlocks.SKYROOT_FENCE.get(),
                AetherIIBlocks.GREATROOT_FENCE.get(),
                AetherIIBlocks.WISPROOT_FENCE.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(
                AetherIIBlocks.SKYROOT_BUTTON.get(),
                AetherIIBlocks.GREATROOT_BUTTON.get(),
                AetherIIBlocks.WISPROOT_BUTTON.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get());
        this.tag(BlockTags.BUTTONS).add(
                AetherIIBlocks.SKYROOT_BUTTON.get(),
                AetherIIBlocks.GREATROOT_BUTTON.get(),
                AetherIIBlocks.WISPROOT_BUTTON.get(),
                AetherIIBlocks.HOLYSTONE_BUTTON.get());
        this.tag(BlockTags.STONE_BUTTONS).add(AetherIIBlocks.HOLYSTONE_BUTTON.get());
        this.tag(BlockTags.PRESSURE_PLATES).add(
                AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get());
        this.tag(BlockTags.FENCE_GATES).add(
                AetherIIBlocks.SKYROOT_FENCE_GATE.get(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.get(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.get());
        this.tag(BlockTags.LOGS_THAT_BURN).addTags(
                AetherIITags.Blocks.SKYROOT_LOGS,
                AetherIITags.Blocks.GREATROOT_LOGS,
                AetherIITags.Blocks.WISPROOT_LOGS,
                AetherIITags.Blocks.AMBEROOT_LOGS);
        this.tag(BlockTags.STAIRS).add(
                AetherIIBlocks.SKYROOT_STAIRS.get(),
                AetherIIBlocks.GREATROOT_STAIRS.get(),
                AetherIIBlocks.WISPROOT_STAIRS.get());
        this.tag(BlockTags.SLABS).add(
                AetherIIBlocks.SKYROOT_SLAB.get(),
                AetherIIBlocks.GREATROOT_STAIRS.get(),
                AetherIIBlocks.WISPROOT_STAIRS.get());
        this.tag(BlockTags.WALLS).add(
                AetherIIBlocks.HOLYSTONE_WALL.get(),
                AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_WALL.get());
        this.tag(BlockTags.WOODEN_DOORS).add(
                AetherIIBlocks.SKYROOT_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(
                AetherIIBlocks.SKYROOT_TRAPDOOR.get());
        this.tag(BlockTags.WOOL).add(
                AetherIIBlocks.CLOUDWOOL.get()
        );
        this.tag(BlockTags.WOOL_CARPETS).add(
                AetherIIBlocks.CLOUDWOOL_CARPET.get()
        );
        this.tag(BlockTags.CLIMBABLE).add(
                AetherIIBlocks.SKYROOT_LADDER.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.UNDERSHALE.get(),
                AetherIIBlocks.AMBROSIUM_ORE.get(),
                AetherIIBlocks.ZANITE_ORE.get(),
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get(),
                AetherIIBlocks.HOLYSTONE_BUTTON.get(),
                AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get(),
                AetherIIBlocks.HOLYSTONE_WALL.get(),
                AetherIIBlocks.HOLYSTONE_STAIRS.get(),
                AetherIIBlocks.HOLYSTONE_SLAB.get(),
                AetherIIBlocks.HOLYSTONE_BRICKS.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get(),
                AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                AetherIIBlocks.SKYROOT_LOG.get(),
                AetherIIBlocks.GREATROOT_LOG.get(),
                AetherIIBlocks.WISPROOT_LOG.get(),
                AetherIIBlocks.AMBEROOT_LOG.get(),
                AetherIIBlocks.SKYROOT_WOOD.get(),
                AetherIIBlocks.GREATROOT_WOOD.get(),
                AetherIIBlocks.WISPROOT_WOOD.get(),
                AetherIIBlocks.AMBEROOT_WOOD.get(),
                AetherIIBlocks.SKYROOT_PLANKS.get(),
                AetherIIBlocks.SKYROOT_FENCE.get(),
                AetherIIBlocks.SKYROOT_FENCE_GATE.get(),
                AetherIIBlocks.SKYROOT_BUTTON.get(),
                AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.SKYROOT_STAIRS.get(),
                AetherIIBlocks.SKYROOT_SLAB.get(),
                AetherIIBlocks.GREATROOT_PLANKS.get(),
                AetherIIBlocks.GREATROOT_FENCE.get(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.get(),
                AetherIIBlocks.GREATROOT_BUTTON.get(),
                AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.GREATROOT_STAIRS.get(),
                AetherIIBlocks.GREATROOT_SLAB.get(),
                AetherIIBlocks.WISPROOT_PLANKS.get(),
                AetherIIBlocks.WISPROOT_FENCE.get(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.get(),
                AetherIIBlocks.WISPROOT_BUTTON.get(),
                AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(),
                AetherIIBlocks.WISPROOT_STAIRS.get(),
                AetherIIBlocks.WISPROOT_SLAB.get(),
                AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get(),
                AetherIIBlocks.AETHER_DIRT.get(),
                AetherIIBlocks.QUICKSOIL.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                AetherIIBlocks.COLD_AERCLOUD.get(),
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
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                AetherIIBlocks.ZANITE_ORE.get()
        );
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get()
        );

        // Forge
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(
                AetherIIBlocks.SKYROOT_FENCE_GATE.get(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.get(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.get());
        this.tag(Tags.Blocks.FENCES_WOODEN).add(
                AetherIIBlocks.SKYROOT_FENCE.get(),
                AetherIIBlocks.GREATROOT_FENCE.get(),
                AetherIIBlocks.WISPROOT_FENCE.get());
    }
}