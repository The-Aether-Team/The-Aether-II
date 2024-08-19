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
    public static void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new AetherIIBlockStateData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new AetherIIItemModelData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new AetherIIParticleData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new AetherIILanguageData(packOutput));
        generator.addProvider(event.includeClient(), new AetherIISoundData(packOutput, fileHelper));

        // Server Data
        DatapackBuiltinEntriesProvider registrySets = new AetherIIRegistrySets(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> registryProvider = registrySets.getRegistryProvider();
        generator.addProvider(event.includeServer(), registrySets);
        generator.addProvider(event.includeServer(), new AetherIIRecipeData(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), AetherIILootTableData.create(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new AetherIILootModifierData(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new AetherIIAdvancementData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new AetherIIDataMapData(packOutput, lookupProvider));

        // Tags
        AetherIIBlockTagData blockTags = new AetherIIBlockTagData(packOutput, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new AetherIIItemTagData(packOutput, lookupProvider, blockTags.contentsGetter(), fileHelper));
        generator.addProvider(event.includeServer(), new AetherIIEntityTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new AetherIIFluidTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new AetherIIBiomeTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new AetherIIStructureTagData(packOutput, registryProvider, fileHelper));
        generator.addProvider(event.includeServer(), new AetherIIDamageTypeTagData(packOutput, registryProvider, fileHelper));

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_ii.mod.description"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    }
}