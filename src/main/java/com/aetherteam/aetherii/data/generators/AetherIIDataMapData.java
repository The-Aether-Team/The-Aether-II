package com.aetherteam.aetherii.data.generators;

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
        var compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES); //todo
//        this.addCompost(compostables, AetherBlocks.SKYROOT_LEAVES, 0.3F);
//        this.addCompost(compostables, AetherBlocks.SKYROOT_SAPLING, 0.3F);
//        this.addCompost(compostables, AetherBlocks.GOLDEN_OAK_LEAVES, 0.3F);
//        this.addCompost(compostables, AetherBlocks.GOLDEN_OAK_SAPLING, 0.3F);
//        this.addCompost(compostables, AetherBlocks.CRYSTAL_LEAVES, 0.3F);
//        this.addCompost(compostables, AetherBlocks.CRYSTAL_FRUIT_LEAVES, 0.3F);
//        this.addCompost(compostables, AetherBlocks.HOLIDAY_LEAVES, 0.3F);
//        this.addCompost(compostables, AetherBlocks.DECORATED_HOLIDAY_LEAVES, 0.3F);
//        this.addCompost(compostables, AetherItems.BLUE_BERRY, 0.3F);
//        this.addCompost(compostables, AetherItems.ENCHANTED_BERRY, 0.5F);
//        this.addCompost(compostables, AetherBlocks.BERRY_BUSH, 0.5F);
//        this.addCompost(compostables, AetherBlocks.BERRY_BUSH_STEM, 0.5F);
//        this.addCompost(compostables, AetherBlocks.WHITE_FLOWER, 0.65F);
//        this.addCompost(compostables, AetherBlocks.PURPLE_FLOWER, 0.65F);
//        this.addCompost(compostables, AetherItems.WHITE_APPLE, 0.65F);
    }

    private void addCompost(DataMapProvider.Builder<Compostable, Item> map, ItemLike item, float chance) {
        map.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false);
    }
}
