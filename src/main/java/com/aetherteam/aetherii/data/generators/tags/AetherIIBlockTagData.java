package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherIIBlockTagData extends BlockTagsProvider {
    public AetherIIBlockTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, AetherII.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
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

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                AetherIIBlocks.HOLYSTONE.get(),
                AetherIIBlocks.UNDERSHALE.get(),
                AetherIIBlocks.AMBROSIUM_ORE.get(),
                AetherIIBlocks.ZANITE_ORE.get(),
                AetherIIBlocks.ARKENIUM_ORE.get(),
                AetherIIBlocks.GRAVITITE_ORE.get()
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
                AetherIIBlocks.GREATROOT_PLANKS.get(),
                AetherIIBlocks.WISPROOT_PLANKS.get()
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
    }
}
