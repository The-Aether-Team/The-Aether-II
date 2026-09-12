package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.data.resources.registries.*;
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
            .add(Registries.CONFIGURED_FEATURE, AetherIIConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, AetherIIPlacedFeatures::bootstrap)
            .add(Registries.CONFIGURED_CARVER, AetherIICarvers::bootstrap)
            .add(Registries.STRUCTURE, AetherIIStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, AetherIIStructureSets::bootstrap)
            .add(Registries.TEMPLATE_POOL, AetherIIPools::bootstrap)
            .add(Registries.PROCESSOR_LIST, AetherIIProcessorLists::bootstrap)
            .add(Registries.DAMAGE_TYPE, AetherIIDamageTypes::bootstrap)
            .add(Registries.JUKEBOX_SONG, AetherIIJukeboxSongs::bootstrap)
            .add(Registries.PAINTING_VARIANT, AetherIIPaintingVariants::bootstrap)
            .add(Registries.TIMELINE, AetherIITimelines::bootstrap)
            .add(AetherIIRegistries.BESTIARY_ENTRY, AetherIIBestiaryEntries::bootstrap)
            .add(AetherIIRegistries.EFFECTS_ENTRY, AetherIIEffectsEntries::bootstrap)
            .add(AetherIIRegistries.EXPLORATION_ENTRY, AetherIIExplorationEntries::bootstrap)
            .add(AetherIIRegistries.STYLE_DESIGN, AetherIIStyleDesigns::bootstrap)
            .add(AetherIIRegistries.STYLE_MATERIAL, AetherIIStyleMaterials::bootstrap)
            .add(AetherIIRegistries.ITEM_REINFORCEMENT, AetherIIItemReinforcements::bootstrap)
            .add(AetherIIRegistries.SKYROOT_LIZARD_VARIANT, AetherIISkyrootLizardVariants::bootstrap)
            .add(AetherIIRegistries.GLITTERWING_VARIANT, AetherIIGlitterwingVariants::bootstrap)
            .add(AetherIIRegistries.SHROUDWING_VARIANT, AetherIIShroudwingVariants::bootstrap)
            .add(AetherIIRegistries.REWARD_WRAPPER, AetherIIRewardWrappers::bootstrap);

    public AetherIIRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(AetherII.MODID));
    }
}