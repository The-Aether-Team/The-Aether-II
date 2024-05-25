package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
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
        this.tag(AetherIITags.Biomes.HAS_STRUCTURE_OUTPOST).add(
                AetherIIBiomes.FLOURISHING_FIELD,
                AetherIIBiomes.VERDANT_WOODS,
                AetherIIBiomes.SHROUDED_FOREST,
                AetherIIBiomes.MAGNETIC_SCAR,
                AetherIIBiomes.TURQUOISE_FOREST,
                AetherIIBiomes.GLISTENING_SWAMP,
                AetherIIBiomes.VIOLET_HIGHWOODS,
                AetherIIBiomes.ENDURING_WOODLAND,
                AetherIIBiomes.SHEER_TUNDRA
        );
    }
}
