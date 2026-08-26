package com.aetherteam.aetherii.data;

import com.aetherteam.aetherii.data.generators.*;
import com.aetherteam.aetherii.data.generators.tags.*;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class AetherIIData {
    public static void data(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(true, new AetherIIModelData(packOutput));
        generator.addProvider(true, new AetherIIParticleData(packOutput));
        generator.addProvider(true, new AetherIILanguageData(packOutput));
        generator.addProvider(true, new AetherIISoundData(packOutput));
        generator.addProvider(true, new AetherIIEquipmentAssetData(packOutput));

        // Server Data
        DatapackBuiltinEntriesProvider registrySets = new AetherIIRegistrySets(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> registryProvider = registrySets.getRegistryProvider();
        generator.addProvider(true, registrySets);
        generator.addProvider(true, new AetherIIRecipeData.Runner(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIRecipePrioritiesData(packOutput, registryProvider));
        generator.addProvider(true, AetherIILootTableData.create(packOutput, registryProvider));
        generator.addProvider(true, new AetherIILootModifierData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIAdvancementData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIDataMapData(packOutput, registryProvider));

        // Tags
        AetherIIBlockTagData blockTags = new AetherIIBlockTagData(packOutput, registryProvider);
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new AetherIIItemTagData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIEntityTypeTagData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIFluidTagData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIBiomeTagData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIFeatureTagData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIStructureTagData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIDamageTypeTagData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIIMobEffectTagData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIISoundEventTagData(packOutput, registryProvider));
        generator.addProvider(true, new AetherIITimelineTagData(packOutput, registryProvider));

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.CLIENT_TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_ii.mod.description"),
                new InclusiveRange<>(DetectedVersion.BUILT_IN.packVersion(PackType.CLIENT_RESOURCES)))));
    }
}