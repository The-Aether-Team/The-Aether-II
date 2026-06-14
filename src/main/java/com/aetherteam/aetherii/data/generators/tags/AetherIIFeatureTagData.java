package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.tags.FeatureTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.concurrent.CompletableFuture;

public class AetherIIFeatureTagData extends KeyTagProvider<ConfiguredFeature<?, ?>> {
    public AetherIIFeatureTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.CONFIGURED_FEATURE, registries, AetherII.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Vanilla
        this.tag(FeatureTags.CAN_SPAWN_FROM_BONE_MEAL).add(
                HolyIslesConfiguredFeatures.VALKYRIE_SPROUT,
                HolyIslesConfiguredFeatures.AETHER_BUSH,
                HolyIslesConfiguredFeatures.BLUEBERRY_BUSH,
                HolyIslesConfiguredFeatures.ORANGE_TREE,
                HolyIslesConfiguredFeatures.HOLY_ISLES_FLOWER_PATCH,
                HolyIslesConfiguredFeatures.HIGHFIELDS_FLOWER_PATCH,
                HolyIslesConfiguredFeatures.MAGNETIC_FLOWER_PATCH,
                HolyIslesConfiguredFeatures.ARCTIC_FLOWER_PATCH
        );
    }
}
