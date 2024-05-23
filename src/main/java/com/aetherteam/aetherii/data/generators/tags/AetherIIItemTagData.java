package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherIIItemTagData extends ItemTagsProvider {
    public AetherIIItemTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, AetherII.MODID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.copy(AetherIITags.Blocks.SKYROOT_LOGS, AetherIITags.Items.SKYROOT_LOGS);
        this.copy(AetherIITags.Blocks.WISPROOT_LOGS, AetherIITags.Items.WISPROOT_LOGS);
        this.copy(AetherIITags.Blocks.GREATROOT_LOGS, AetherIITags.Items.GREATROOT_LOGS);
        this.copy(AetherIITags.Blocks.AMBEROOT_LOGS, AetherIITags.Items.AMBEROOT_LOGS);

        this.tag(AetherIITags.Items.CRAFTS_SKYROOT_PLANKS).addTags(
                AetherIITags.Items.SKYROOT_LOGS,
                AetherIITags.Items.AMBEROOT_LOGS);
        this.tag(AetherIITags.Items.CRAFTS_GREATROOT_PLANKS).addTag(
                AetherIITags.Items.GREATROOT_LOGS);
        this.tag(AetherIITags.Items.CRAFTS_WISPROOT_PLANKS).addTag(
                AetherIITags.Items.WISPROOT_LOGS);
        this.tag(AetherIITags.Items.CRAFTS_SKYROOT_STICKS).add(
                AetherIIBlocks.SKYROOT_PLANKS.asItem(),
                AetherIIBlocks.GREATROOT_PLANKS.asItem(),
                AetherIIBlocks.WISPROOT_PLANKS.asItem());
    }
}
