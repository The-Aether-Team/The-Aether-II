package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherIIBiomeTagData extends BiomeTagsProvider {
    public AetherIIBiomeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, AetherII.MODID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.Biomes.HIGHFIELDS).add(
                AetherIIBiomes.FLOURISHING_FIELD,
                AetherIIBiomes.VERDANT_WOODS,
                AetherIIBiomes.SHROUDED_FOREST,
                AetherIIBiomes.SHIMMERING_BASIN);
        this.tag(AetherIITags.Biomes.MAGNETIC).add(
                AetherIIBiomes.MAGNETIC_SCAR,
                AetherIIBiomes.TURQUOISE_FOREST,
                AetherIIBiomes.GLISTENING_SWAMP,
                AetherIIBiomes.VIOLET_HIGHWOODS);
        this.tag(AetherIITags.Biomes.ARCTIC).add(
                AetherIIBiomes.FRIGID_SIERRA,
                AetherIIBiomes.ENDURING_WOODLAND,
                AetherIIBiomes.FROZEN_LAKES,
                AetherIIBiomes.SHEER_TUNDRA);
        this.tag(AetherIITags.Biomes.IRRADIATED).add(
                AetherIIBiomes.CONTAMINATED_JUNGLE,
                AetherIIBiomes.BATTLEGROUND_WASTES);
        this.tag(AetherIITags.Biomes.EXPANSE).add(AetherIIBiomes.EXPANSE);

        this.tag(AetherIITags.Biomes.MYCELIUM_CONVERSION).add(Biomes.MUSHROOM_FIELDS);
        this.tag(AetherIITags.Biomes.PODZOL_CONVERSION).add(
                Biomes.OLD_GROWTH_PINE_TAIGA,
                Biomes.OLD_GROWTH_SPRUCE_TAIGA,
                Biomes.BAMBOO_JUNGLE);
        this.tag(AetherIITags.Biomes.CRIMSON_NYLIUM_CONVERSION).add(Biomes.CRIMSON_FOREST);
        this.tag(AetherIITags.Biomes.WARPED_NYLIUM_CONVERSION).add(Biomes.WARPED_FOREST);

        this.tag(AetherIITags.Biomes.ARCTIC_ICE).add(
                AetherIIBiomes.FLOURISHING_FIELD,
                AetherIIBiomes.VERDANT_WOODS,
                AetherIIBiomes.SHROUDED_FOREST,
                AetherIIBiomes.SHIMMERING_BASIN,
                AetherIIBiomes.MAGNETIC_SCAR,
                AetherIIBiomes.TURQUOISE_FOREST,
                AetherIIBiomes.GLISTENING_SWAMP,
                AetherIIBiomes.VIOLET_HIGHWOODS,
                AetherIIBiomes.FRIGID_SIERRA,
                AetherIIBiomes.ENDURING_WOODLAND,
                AetherIIBiomes.FROZEN_LAKES,
                AetherIIBiomes.SHEER_TUNDRA,
                AetherIIBiomes.CONTAMINATED_JUNGLE,
                AetherIIBiomes.BATTLEGROUND_WASTES,
                AetherIIBiomes.EXPANSE
        );

        this.tag(AetherIITags.Biomes.AETHER_MUSIC).add(
                AetherIIBiomes.FLOURISHING_FIELD,
                AetherIIBiomes.VERDANT_WOODS,
                AetherIIBiomes.SHROUDED_FOREST,
                AetherIIBiomes.SHIMMERING_BASIN,
                AetherIIBiomes.MAGNETIC_SCAR,
                AetherIIBiomes.TURQUOISE_FOREST,
                AetherIIBiomes.GLISTENING_SWAMP,
                AetherIIBiomes.VIOLET_HIGHWOODS,
                AetherIIBiomes.FRIGID_SIERRA,
                AetherIIBiomes.ENDURING_WOODLAND,
                AetherIIBiomes.FROZEN_LAKES,
                AetherIIBiomes.SHEER_TUNDRA,
                AetherIIBiomes.CONTAMINATED_JUNGLE,
                AetherIIBiomes.BATTLEGROUND_WASTES,
                AetherIIBiomes.EXPANSE
        );

        this.tag(AetherIITags.Biomes.HAS_STRUCTURE_OUTPOST).add(
                AetherIIBiomes.FLOURISHING_FIELD,
                AetherIIBiomes.VERDANT_WOODS,
                AetherIIBiomes.SHROUDED_FOREST,
                AetherIIBiomes.MAGNETIC_SCAR,
                AetherIIBiomes.TURQUOISE_FOREST,
                AetherIIBiomes.GLISTENING_SWAMP,
                AetherIIBiomes.VIOLET_HIGHWOODS,
                AetherIIBiomes.FRIGID_SIERRA,
                AetherIIBiomes.ENDURING_WOODLAND,
                AetherIIBiomes.SHEER_TUNDRA
        );
    }
}