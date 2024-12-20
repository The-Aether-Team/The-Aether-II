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
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class AetherIIData {
    public static void dataSetup(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(true, new AetherIIBlockStateData(packOutput, fileHelper));
        generator.addProvider(true, new AetherIIItemModelData(packOutput, fileHelper));
        generator.addProvider(true, new AetherIIParticleData(packOutput, fileHelper));
        generator.addProvider(true, new AetherIILanguageData(packOutput));
        generator.addProvider(true, new AetherIISoundData(packOutput, fileHelper));

        // Server Data
        DatapackBuiltinEntriesProvider registrySets = new AetherIIRegistrySets(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> registryProvider = registrySets.getRegistryProvider();
        generator.addProvider(true, registrySets);
        generator.addProvider(true, new AetherIIRecipeData(packOutput, lookupProvider));
        generator.addProvider(true, AetherIILootTableData.create(packOutput, lookupProvider));
        generator.addProvider(true, new AetherIILootModifierData(packOutput, lookupProvider));
        generator.addProvider(true, new AetherIIAdvancementData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(true, new AetherIIDataMapData(packOutput, lookupProvider));

        // Tags
        AetherIIBlockTagData blockTags = new AetherIIBlockTagData(packOutput, lookupProvider, fileHelper);
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new AetherIIItemTagData(packOutput, lookupProvider, blockTags.contentsGetter(), fileHelper));
        generator.addProvider(true, new AetherIIEntityTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(true, new AetherIIFluidTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(true, new AetherIIBiomeTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(true, new AetherIIStructureTagData(packOutput, registryProvider, fileHelper));
        generator.addProvider(true, new AetherIIDamageTypeTagData(packOutput, registryProvider, fileHelper));

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_ii.mod.description"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    }
}