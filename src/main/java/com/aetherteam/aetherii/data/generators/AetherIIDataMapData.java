package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class AetherIIDataMapData extends DataMapProvider {
    public AetherIIDataMapData(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void gather() {
        var compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        this.addCompost(compostables, AetherIIBlocks.SKYROOT_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.SKYPLANE_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.SKYBIRCH_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.SKYPINE_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.WISPROOT_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.WISPTOP_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.GREATROOT_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.GREATOAK_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.GREATBOA_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.AMBEROOT_LEAF_PILE, 0.018F);
        this.addCompost(compostables, AetherIIBlocks.SKYROOT_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.SKYPLANE_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.SKYBIRCH_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.SKYPINE_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.WISPROOT_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.WISPTOP_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.GREATROOT_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.GREATOAK_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.GREATBOA_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.AMBEROOT_LEAVES, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.SKYROOT_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.SKYPLANE_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.SKYBIRCH_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.SKYPINE_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.WISPROOT_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.WISPTOP_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.GREATROOT_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.GREATOAK_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.GREATBOA_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.AMBEROOT_SAPLING, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.AETHER_SHORT_GRASS, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.AETHER_MEDIUM_GRASS, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.AETHER_LONG_GRASS, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.HESPEROSE, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.TARABLOOM, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.HIGHLANDS_BUSH, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.BLUEBERRY_BUSH, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.BLUEBERRY_BUSH_STEM, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.ORANGE_TREE, 0.5F);
        this.addCompost(compostables, AetherIIItems.BLUEBERRY, 0.3F);
        this.addCompost(compostables, AetherIIItems.ENCHANTED_BLUEBERRY, 0.5F);
        this.addCompost(compostables, AetherIIItems.WYNDBERRY, 0.3F);
        this.addCompost(compostables, AetherIIItems.ENCHANTED_WYNDBERRY, 0.5F);
        this.addCompost(compostables, AetherIIItems.ORANGE, 0.3F);
    }

    @SuppressWarnings("deprecation")
    private void addCompost(DataMapProvider.Builder<Compostable, Item> map, ItemLike item, float chance) {
        map.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false);
    }
}