package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.maps.AmberHourglassFuel;
import com.aetherteam.aetherii.data.resources.maps.BlockInfection;
import com.aetherteam.aetherii.data.resources.maps.BucketReplacement;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class AetherIIDataMapData extends DataMapProvider {
    public AetherIIDataMapData(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
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
        this.addCompost(compostables, AetherIIBlocks.SHORT_AETHER_GRASS, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.MEDIUM_AETHER_GRASS, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.TALL_AETHER_GRASS, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.BRETTL_FLOWER, 0.3F);
        this.addCompost(compostables, AetherIIBlocks.AETHER_FERN, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.SHIELD_FERN, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.HESPEROSE, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.TARABLOOM, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.POASPROUT, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.SATIVAL_SHOOT, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.LILICHIME, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.BLADE_POA, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.AECHOR_CUTTING, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.CARRION_CUTTING, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.AETHER_BUSH, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.BLUEBERRY_BUSH, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.BLUEBERRY_BUSH_STEM, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.ORANGE_TREE, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.BRETTL_GRASS_BUNDLE, 0.85F);
        this.addCompost(compostables, AetherIIItems.BLUEBERRY, 0.3F);
        this.addCompost(compostables, AetherIIItems.ENCHANTED_BLUEBERRY, 0.5F);
        this.addCompost(compostables, AetherIIItems.ORANGE, 0.3F);
        this.addCompost(compostables, AetherIIItems.ENCHANTED_ORANGE, 0.5F);
        this.addCompost(compostables, AetherIIItems.WYNDBERRY, 0.3F);
        this.addCompost(compostables, AetherIIItems.ENCHANTED_WYNDBERRY, 0.5F);
        this.addCompost(compostables, AetherIIItems.GOLDEN_WYNDBERRY, 0.65F);
        this.addCompost(compostables, AetherIIItems.SATIVAL_BULB, 0.3F);

        var fuels = this.builder(NeoForgeDataMaps.FURNACE_FUELS);
        fuels.add(AetherIIItems.ARKENIUM_HESTVEIL_CANISTER, new FurnaceFuel(5000), false);
        fuels.add(AetherIIBlocks.AMBROSIUM_BLOCK.getId(), new FurnaceFuel(16000), false);
        fuels.add(AetherIIItems.IRRADIATED_DUST, new FurnaceFuel(3500), false);
        fuels.add(AetherIIItems.AMBROSIUM_SHARD, new FurnaceFuel(1600), false);
        fuels.add(AetherIIItems.SKYROOT_PINECONE, new FurnaceFuel(400), false);
        fuels.add(AetherIIBlocks.AETHER_BUSH.getId(), new FurnaceFuel(100), false);
        fuels.add(AetherIIBlocks.SKYROOT_PLANKS.getId(), new FurnaceFuel(300), false);
        fuels.add(AetherIITags.Items.SKYROOT_DECORATIVE_BLOCKS, new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.GREATROOT_PLANKS.getId(), new FurnaceFuel(300), false);
        fuels.add(AetherIITags.Items.GREATROOT_DECORATIVE_BLOCKS, new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.WISPROOT_PLANKS.getId(), new FurnaceFuel(300), false);
        fuels.add(AetherIITags.Items.WISPROOT_DECORATIVE_BLOCKS, new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.AMBEROOT_PLANKS.getId(), new FurnaceFuel(300), false);
        fuels.add(AetherIITags.Items.AMBEROOT_DECORATIVE_BLOCKS, new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.SKYROOT_BOOKSHELF.getId(), new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.GREATROOT_BOOKSHELF.getId(), new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.WISPROOT_BOOKSHELF.getId(), new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.AMBEROOT_BOOKSHELF.getId(), new FurnaceFuel(300), false);
        fuels.add(AetherIIItems.SKYROOT_SHORTSWORD, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_HAMMER, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_PIKE, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_CROSSBOW, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_PICKAXE, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_AXE, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_SHOVEL, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_TROWEL, new FurnaceFuel(200), false);
        fuels.add(AetherIIBlocks.SKYROOT_TWIG.getId(), new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_STICK, new FurnaceFuel(100), false);

        var strippables = this.builder(NeoForgeDataMaps.STRIPPABLES);
        strippables.add(AetherIIBlocks.SKYROOT_LOG, new Strippable(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get()), false);
        strippables.add(AetherIIBlocks.SKYROOT_WOOD, new Strippable(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get()), false);
        strippables.add(AetherIIBlocks.SKYROOT_TRUNK, new Strippable(AetherIIBlocks.STRIPPED_SKYROOT_TRUNK.get()), false);
        strippables.add(AetherIIBlocks.GREATROOT_LOG, new Strippable(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get()), false);
        strippables.add(AetherIIBlocks.GREATROOT_WOOD, new Strippable(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get()), false);
        strippables.add(AetherIIBlocks.GREATROOT_TRUNK, new Strippable(AetherIIBlocks.STRIPPED_GREATROOT_TRUNK.get()), false);
        strippables.add(AetherIIBlocks.WISPROOT_LOG, new Strippable(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get()), false);
        strippables.add(AetherIIBlocks.WISPROOT_WOOD, new Strippable(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get()), false);
        strippables.add(AetherIIBlocks.WISPROOT_TRUNK, new Strippable(AetherIIBlocks.STRIPPED_WISPROOT_TRUNK.get()), false);
        strippables.add(AetherIIBlocks.MOSSY_WISPROOT_LOG, new Strippable(AetherIIBlocks.WISPROOT_LOG.get()), false);
        strippables.add(AetherIIBlocks.MOSSY_WISPROOT_WOOD, new Strippable(AetherIIBlocks.WISPROOT_WOOD.get()), false);
        strippables.add(AetherIIBlocks.MOSSY_WISPROOT_TRUNK, new Strippable(AetherIIBlocks.WISPROOT_TRUNK.get()), false);
        strippables.add(AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE, new Strippable(AetherIIBlocks.WISPROOT_LOG.get()), false);
        strippables.add(AetherIIBlocks.AMBEROOT_LOG, new Strippable(AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get()), false);
        strippables.add(AetherIIBlocks.AMBEROOT_DEPOSIT, new Strippable(AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get()), false);
        strippables.add(AetherIIBlocks.AMBEROOT_WOOD, new Strippable(AetherIIBlocks.STRIPPED_AMBEROOT_WOOD.get()), false);
        strippables.add(AetherIIBlocks.AMBEROOT_TRUNK, new Strippable(AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK.get()), false);
        strippables.add(AetherIIBlocks.GUARDIAN_LOG, new Strippable(AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get()), false);
        strippables.add(AetherIIBlocks.GUARDIAN_LOG_SLAB, new Strippable(AetherIIBlocks.STRIPPED_GUARDIAN_LOG_SLAB.get()), false);
        strippables.add(AetherIIBlocks.GUARDIAN_WOOD, new Strippable(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get()), false);
        strippables.add(AetherIIBlocks.GUARDIAN_WOOD_SLAB, new Strippable(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD_SLAB.get()), false);
        strippables.add(AetherIIBlocks.GUARDIAN_TRUNK, new Strippable(AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK.get()), false);
        strippables.add(AetherIIBlocks.INFECTED_LOG, new Strippable(AetherIIBlocks.STRIPPED_INFECTED_LOG.get()), false);
        strippables.add(AetherIIBlocks.INFECTED_LOG_SLAB, new Strippable(AetherIIBlocks.STRIPPED_INFECTED_LOG_SLAB.get()), false);
        strippables.add(AetherIIBlocks.INFECTED_WOOD, new Strippable(AetherIIBlocks.STRIPPED_INFECTED_WOOD.get()), false);
        strippables.add(AetherIIBlocks.INFECTED_WOOD_SLAB, new Strippable(AetherIIBlocks.STRIPPED_INFECTED_WOOD_SLAB.get()), false);
        strippables.add(AetherIIBlocks.INFECTED_TRUNK, new Strippable(AetherIIBlocks.STRIPPED_INFECTED_TRUNK.get()), false);

        var amberHourglassFuels = this.builder(AetherIIDataMaps.AMBER_HOURGLASS_FUELS);
        amberHourglassFuels.add(AetherIIItems.GOLDEN_AMBER, new AmberHourglassFuel(400), false);
        amberHourglassFuels.add(AetherIIBlocks.GOLDEN_AMBER_BLOCK.getId(), new AmberHourglassFuel(4000), false);

        var buckets = this.builder(AetherIIDataMaps.BUCKET_REPLACEMENT);
        buckets.add(Items.WATER_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_WATER_BUCKET.getKey()), false);
        buckets.add(Items.POWDER_SNOW_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.getKey()), false);
        buckets.add(Items.COD_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_COD_BUCKET.getKey()), false);
        buckets.add(Items.SALMON_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_SALMON_BUCKET.getKey()), false);
        buckets.add(Items.PUFFERFISH_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_PUFFERFISH_BUCKET.getKey()), false);
        buckets.add(Items.TROPICAL_FISH_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_TROPICAL_FISH_BUCKET.getKey()), false);
        buckets.add(Items.AXOLOTL_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_AXOLOTL_BUCKET.getKey()), false);
        buckets.add(Items.TADPOLE_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_TADPOLE_BUCKET.getKey()), false);

        var blocks = this.builder(AetherIIDataMaps.INFECTED_BLOCKS);
        blocks.add(AetherIIBlocks.GUARDIAN_LOG, new BlockInfection(AetherIIBlocks.INFECTED_LOG.getKey()), false);
        blocks.add(AetherIIBlocks.GUARDIAN_WOOD, new BlockInfection(AetherIIBlocks.INFECTED_WOOD.getKey()), false);
        blocks.add(AetherIIBlocks.STRIPPED_GUARDIAN_LOG, new BlockInfection(AetherIIBlocks.STRIPPED_INFECTED_LOG.getKey()), false);
        blocks.add(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD, new BlockInfection(AetherIIBlocks.STRIPPED_INFECTED_WOOD.getKey()), false);

        var colors = this.builder(AetherIIDataMaps.AETHER_GRASS_COLORS);
        colors.add(AetherIITags.Biomes.HIGHFIELDS, 0xb5ffd0, false);
        colors.add(AetherIITags.Biomes.MAGNETIC, 0xc9ffd1, false);
        colors.add(AetherIITags.Biomes.ARCTIC, 0xbdf9ff, false);
        colors.add(AetherIITags.Biomes.IRRADIATED, 0xffdd99, false);
        colors.add(HolyIslesBiomes.EXPANSE, 0xb5ffd0, false);
    }

    private void addCompost(DataMapProvider.Builder<Compostable, Item> map, DeferredHolder<?, ?> item, float chance) {
        map.add(item.getId(), new Compostable(chance), false);
    }
}