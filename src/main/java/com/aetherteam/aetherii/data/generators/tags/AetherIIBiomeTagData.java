package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsBiomes;
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
                HighlandsBiomes.FLOURISHING_FIELD,
                HighlandsBiomes.VERDANT_WOODS,
                HighlandsBiomes.SHROUDED_FOREST,
                HighlandsBiomes.SHIMMERING_BASIN);
        this.tag(AetherIITags.Biomes.MAGNETIC).add(
                HighlandsBiomes.MAGNETIC_SCAR,
                HighlandsBiomes.TURQUOISE_FOREST,
                HighlandsBiomes.GLISTENING_SWAMP,
                HighlandsBiomes.VIOLET_HIGHWOODS);
        this.tag(AetherIITags.Biomes.MAGNETIC_FOG).add(HighlandsBiomes.GLISTENING_SWAMP);
        this.tag(AetherIITags.Biomes.ARCTIC).add(
                HighlandsBiomes.FRIGID_SIERRA,
                HighlandsBiomes.ENDURING_WOODLAND,
                HighlandsBiomes.FROZEN_LAKES,
                HighlandsBiomes.SHEER_TUNDRA);
        this.tag(AetherIITags.Biomes.IRRADIATED).add(
                HighlandsBiomes.CONTAMINATED_JUNGLE,
                HighlandsBiomes.BATTLEGROUND_WASTES);
        this.tag(AetherIITags.Biomes.EXPANSE).add(HighlandsBiomes.EXPANSE);

        this.tag(AetherIITags.Biomes.MYCELIUM_CONVERSION).add(Biomes.MUSHROOM_FIELDS);
        this.tag(AetherIITags.Biomes.PODZOL_CONVERSION).add(
                Biomes.OLD_GROWTH_PINE_TAIGA,
                Biomes.OLD_GROWTH_SPRUCE_TAIGA,
                Biomes.BAMBOO_JUNGLE);
        this.tag(AetherIITags.Biomes.CRIMSON_NYLIUM_CONVERSION).add(Biomes.CRIMSON_FOREST);
        this.tag(AetherIITags.Biomes.WARPED_NYLIUM_CONVERSION).add(Biomes.WARPED_FOREST);

        this.tag(AetherIITags.Biomes.ARCTIC_ICE).add(
                HighlandsBiomes.FLOURISHING_FIELD,
                HighlandsBiomes.VERDANT_WOODS,
                HighlandsBiomes.SHROUDED_FOREST,
                HighlandsBiomes.SHIMMERING_BASIN,
                HighlandsBiomes.MAGNETIC_SCAR,
                HighlandsBiomes.TURQUOISE_FOREST,
                HighlandsBiomes.GLISTENING_SWAMP,
                HighlandsBiomes.VIOLET_HIGHWOODS,
                HighlandsBiomes.FRIGID_SIERRA,
                HighlandsBiomes.ENDURING_WOODLAND,
                HighlandsBiomes.FROZEN_LAKES,
                HighlandsBiomes.SHEER_TUNDRA,
                HighlandsBiomes.CONTAMINATED_JUNGLE,
                HighlandsBiomes.BATTLEGROUND_WASTES,
                HighlandsBiomes.EXPANSE
        );

        this.tag(AetherIITags.Biomes.AETHER_MUSIC).add(
                HighlandsBiomes.FLOURISHING_FIELD,
                HighlandsBiomes.VERDANT_WOODS,
                HighlandsBiomes.SHROUDED_FOREST,
                HighlandsBiomes.SHIMMERING_BASIN,
                HighlandsBiomes.MAGNETIC_SCAR,
                HighlandsBiomes.TURQUOISE_FOREST,
                HighlandsBiomes.GLISTENING_SWAMP,
                HighlandsBiomes.VIOLET_HIGHWOODS,
                HighlandsBiomes.FRIGID_SIERRA,
                HighlandsBiomes.ENDURING_WOODLAND,
                HighlandsBiomes.FROZEN_LAKES,
                HighlandsBiomes.SHEER_TUNDRA,
                HighlandsBiomes.CONTAMINATED_JUNGLE,
                HighlandsBiomes.BATTLEGROUND_WASTES,
                HighlandsBiomes.EXPANSE
        );

        this.tag(AetherIITags.Biomes.HAS_STRUCTURE_OUTPOST).add(
                HighlandsBiomes.FLOURISHING_FIELD,
                HighlandsBiomes.VERDANT_WOODS,
                HighlandsBiomes.SHROUDED_FOREST,
                HighlandsBiomes.MAGNETIC_SCAR,
                HighlandsBiomes.TURQUOISE_FOREST,
                HighlandsBiomes.GLISTENING_SWAMP,
                HighlandsBiomes.VIOLET_HIGHWOODS,
                HighlandsBiomes.FRIGID_SIERRA,
                HighlandsBiomes.ENDURING_WOODLAND,
                HighlandsBiomes.SHEER_TUNDRA
        );

        this.tag(AetherIITags.Biomes.HAS_STRUCTURE_CAMP_HIGHFIELDS).add(
                HighlandsBiomes.FLOURISHING_FIELD,
                HighlandsBiomes.VERDANT_WOODS
        );
    }
}