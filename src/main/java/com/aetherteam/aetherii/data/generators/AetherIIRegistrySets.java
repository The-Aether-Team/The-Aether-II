package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.*;
import com.aetherteam.aetherii.data.resources.registries.features.AetherIIFeatureUtils;
import com.aetherteam.aetherii.data.resources.registries.placement.AetherIIPlacementUtils;
import com.aetherteam.aetherii.data.resources.registries.pools.AetherIIPools;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class AetherIIRegistrySets extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, AetherIIBiomes::bootstrap)
            .add(Registries.DENSITY_FUNCTION, AetherIIDensityFunctions::bootstrap)
            .add(Registries.NOISE, AetherIINoises::bootstrap)
            .add(Registries.NOISE_SETTINGS, AetherIINoiseSettings::bootstrap)
            .add(Registries.DIMENSION_TYPE, AetherIIDimensions::bootstrapDimensionType)
            .add(Registries.LEVEL_STEM, AetherIIDimensions::bootstrapLevelStem)
            .add(Registries.CONFIGURED_FEATURE, AetherIIFeatureUtils::bootstrap)
            .add(Registries.PLACED_FEATURE, AetherIIPlacementUtils::bootstrap)
            .add(Registries.STRUCTURE, AetherIIStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, AetherIIStructureSets::bootstrap)
            .add(Registries.TEMPLATE_POOL, AetherIIPools::bootstrap)
            .add(Registries.DAMAGE_TYPE, AetherIIDamageTypes::bootstrap)
            .add(AetherIIDamageInflictions.DAMAGE_INFLICTION_REGISTRY_KEY, AetherIIDamageInflictions::bootstrap)
            .add(AetherIIDamageResistances.DAMAGE_RESISTANCE_REGISTRY_KEY, AetherIIDamageResistances::bootstrap);

    public AetherIIRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(AetherII.MODID));
    }
}