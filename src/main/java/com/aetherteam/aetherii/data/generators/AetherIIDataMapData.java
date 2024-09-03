package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.maps.BucketReplacement;
import com.aetherteam.aetherii.data.resources.maps.DamageInfliction;
import com.aetherteam.aetherii.data.resources.maps.DamageResistance;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
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
        this.addCompost(compostables, AetherIIBlocks.HIGHLAND_FERN, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.SHIELD_FERN, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.HESPEROSE, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.TARABLOOM, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.POASPROUT, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.SATIVAL_SHOOT, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.LILICHIME, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.BLADE_POA, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.AECHOR_CUTTING, 0.65F);
        this.addCompost(compostables, AetherIIBlocks.HIGHLANDS_BUSH, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.BLUEBERRY_BUSH, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.BLUEBERRY_BUSH_STEM, 0.5F);
        this.addCompost(compostables, AetherIIBlocks.ORANGE_TREE, 0.5F);
        this.addCompost(compostables, AetherIIItems.BLUEBERRY, 0.3F);
        this.addCompost(compostables, AetherIIItems.ENCHANTED_BLUEBERRY, 0.5F);
        this.addCompost(compostables, AetherIIItems.WYNDBERRY, 0.3F);
        this.addCompost(compostables, AetherIIItems.ENCHANTED_WYNDBERRY, 0.5F);
        this.addCompost(compostables, AetherIIItems.ORANGE, 0.3F);

        var fuels = this.builder(NeoForgeDataMaps.FURNACE_FUELS);
        fuels.add(AetherIIBlocks.AMBROSIUM_BLOCK.asItem().builtInRegistryHolder(), new FurnaceFuel(16000), false);
        fuels.add(AetherIIItems.AMBROSIUM_SHARD, new FurnaceFuel(1600), false);
        fuels.add(AetherIIBlocks.HIGHLANDS_BUSH.asItem().builtInRegistryHolder(), new FurnaceFuel(100), false);
        fuels.add(AetherIIBlocks.SKYROOT_PLANKS.asItem().builtInRegistryHolder(), new FurnaceFuel(300), false);
        fuels.add(AetherIITags.Items.SKYROOT_DECORATIVE_BLOCKS, new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.GREATROOT_PLANKS.asItem().builtInRegistryHolder(), new FurnaceFuel(300), false);
        fuels.add(AetherIITags.Items.GREATROOT_DECORATIVE_BLOCKS, new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.WISPROOT_PLANKS.asItem().builtInRegistryHolder(), new FurnaceFuel(300), false);
        fuels.add(AetherIITags.Items.WISPROOT_DECORATIVE_BLOCKS, new FurnaceFuel(300), false);
        fuels.add(AetherIIBlocks.SKYROOT_BOOKSHELF.asItem().builtInRegistryHolder(), new FurnaceFuel(300), false);
        fuels.add(AetherIIItems.SKYROOT_SHORTSWORD, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_HAMMER, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_SPEAR, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_CROSSBOW, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_PICKAXE, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_AXE, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_SHOVEL, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_TROWEL, new FurnaceFuel(200), false);
        fuels.add(AetherIIItems.SKYROOT_STICK, new FurnaceFuel(100), false);

        var buckets = this.builder(AetherIIDataMaps.BUCKET_REPLACEMENT);
        buckets.add(Items.WATER_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_WATER_BUCKET.getKey()), false);
        buckets.add(Items.POWDER_SNOW_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.getKey()), false);
        buckets.add(Items.COD_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_COD_BUCKET.getKey()), false);
        buckets.add(Items.SALMON_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_SALMON_BUCKET.getKey()), false);
        buckets.add(Items.PUFFERFISH_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_PUFFERFISH_BUCKET.getKey()), false);
        buckets.add(Items.TROPICAL_FISH_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_TROPICAL_FISH_BUCKET.getKey()), false);
        buckets.add(Items.AXOLOTL_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_AXOLOTL_BUCKET.getKey()), false);
        buckets.add(Items.TADPOLE_BUCKET.builtInRegistryHolder(), new BucketReplacement(AetherIIItems.SKYROOT_TADPOLE_BUCKET.getKey()), false);
        
        var inflictions = this.builder(AetherIIDataMaps.DAMAGE_INFLICTION);
        inflictions.add(AetherIIItems.SKYROOT_SHORTSWORD, new DamageInfliction(4.0, 0, 0), false);
        inflictions.add(AetherIIItems.SKYROOT_HAMMER, new DamageInfliction(0, 4.0, 0), false);
        inflictions.add(AetherIIItems.SKYROOT_SPEAR, new DamageInfliction(0, 0, 4.0), false);
        inflictions.add(AetherIIItems.HOLYSTONE_SHORTSWORD, new DamageInfliction(5.0, 0, 0), false);
        inflictions.add(AetherIIItems.HOLYSTONE_HAMMER, new DamageInfliction(0, 5.0, 0), false);
        inflictions.add(AetherIIItems.HOLYSTONE_SPEAR, new DamageInfliction(0, 0, 5.0), false);
        inflictions.add(AetherIIItems.ZANITE_SHORTSWORD, new DamageInfliction(6.0, 0.0, 0), false);
        inflictions.add(AetherIIItems.ZANITE_HAMMER, new DamageInfliction(0, 6.0, 0), false);
        inflictions.add(AetherIIItems.ZANITE_SPEAR, new DamageInfliction(0, 0, 6.0), false);
        inflictions.add(AetherIIItems.ARKENIUM_SHORTSWORD, new DamageInfliction(6.0, 0.0, 0), false);
        inflictions.add(AetherIIItems.ARKENIUM_HAMMER, new DamageInfliction(0, 6.0, 0), false);
        inflictions.add(AetherIIItems.ARKENIUM_SPEAR, new DamageInfliction(0, 0, 6.0), false);
        inflictions.add(AetherIIItems.GRAVITITE_SHORTSWORD, new DamageInfliction(7.0, 0.0, 0), false);
        inflictions.add(AetherIIItems.GRAVITITE_HAMMER, new DamageInfliction(0, 7.0, 0), false);
        inflictions.add(AetherIIItems.GRAVITITE_SPEAR, new DamageInfliction(0, 0, 7.0), false);
        
        var resistances = this.builder(AetherIIDataMaps.DAMAGE_RESISTANCE);
        resistances.add(AetherIIEntityTypes.FLYING_COW, new DamageResistance(0, 0, 0), false);
        resistances.add(AetherIIEntityTypes.SHEEPUFF, new DamageResistance(0, 0, 0), false);
        resistances.add(AetherIIEntityTypes.PHYG, new DamageResistance(0, 0, 0), false);
        resistances.add(AetherIIEntityTypes.AERBUNNY, new DamageResistance(0, 2, -2), false);
        resistances.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID, new DamageResistance(-2, 0, 0), false);
        resistances.add(AetherIIEntityTypes.MAGNETIC_TAEGORE, new DamageResistance(-2, 0, 0), false);
        resistances.add(AetherIIEntityTypes.ARCTIC_TAEGORE, new DamageResistance(-2, 0, 0), false);
        resistances.add(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI, new DamageResistance(2, -2, 0), false);
        resistances.add(AetherIIEntityTypes.MAGNETIC_BURRUKAI, new DamageResistance(2, -2, 0), false);
        resistances.add(AetherIIEntityTypes.ARCTIC_BURRUKAI, new DamageResistance(2, -2, 0), false);
        resistances.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID, new DamageResistance(0, 2, -2), false);
        resistances.add(AetherIIEntityTypes.MAGNETIC_KIRRID, new DamageResistance(0, 2, -2), false);
        resistances.add(AetherIIEntityTypes.ARCTIC_KIRRID, new DamageResistance(0, 2, -2), false);
        resistances.add(AetherIIEntityTypes.MOA, new DamageResistance(2, 2, -2), false);

        resistances.add(AetherIIEntityTypes.AECHOR_PLANT, new DamageResistance(-2, 2, 0), false);
        resistances.add(AetherIIEntityTypes.ZEPHYR, new DamageResistance(0, 2, -2), false);
        resistances.add(AetherIIEntityTypes.TEMPEST, new DamageResistance(2, 2, -2), false);
        resistances.add(AetherIIEntityTypes.COCKATRICE, new DamageResistance(-2, 0, 2), false);
        resistances.add(AetherIIEntityTypes.SWET, new DamageResistance(2, 0, -2), false);
    }

    private void addCompost(DataMapProvider.Builder<Compostable, Item> map, ItemLike item, float chance) {
        map.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false);
    }
}