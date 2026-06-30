package com.aetherteam.aetherii.data;

import com.aetherteam.aetherii.data.generators.AetherIIAdvancementData;
import com.aetherteam.aetherii.data.generators.AetherIIDataMapData;
import com.aetherteam.aetherii.data.generators.AetherIIEquipmentAssetData;
import com.aetherteam.aetherii.data.generators.AetherIILanguageData;
import com.aetherteam.aetherii.data.generators.AetherIILootModifierData;
import com.aetherteam.aetherii.data.generators.AetherIILootTableData;
import com.aetherteam.aetherii.data.generators.AetherIIModelData;
import com.aetherteam.aetherii.data.generators.AetherIIParticleData;
import com.aetherteam.aetherii.data.generators.AetherIIRecipeData;
import com.aetherteam.aetherii.data.generators.AetherIIRecipePrioritiesData;
import com.aetherteam.aetherii.data.generators.AetherIIRegistrySets;
import com.aetherteam.aetherii.data.generators.AetherIISoundData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIBiomeTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIBlockTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIDamageTypeTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIEntityTypeTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIFeatureTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIFluidTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIItemTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIMobEffectTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIISoundEventTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIStructureTagData;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class AetherIIData {
    public static void data(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(true, new AetherIIModelData(packOutput));
            generator.addProvider(true, new AetherIIParticleData(packOutput, existingFileHelper));
            generator.addProvider(true, new AetherIILanguageData(packOutput));
            generator.addProvider(true, new AetherIISoundData(packOutput, existingFileHelper));
            generator.addProvider(true, new AetherIIEquipmentAssetData(packOutput));
        }

        if (event.includeServer()) {
            DatapackBuiltinEntriesProvider registrySets = new AetherIIRegistrySets(packOutput, lookupProvider);
            CompletableFuture<HolderLookup.Provider> registryProvider = registrySets.getRegistryProvider();
            generator.addProvider(true, registrySets);
            generator.addProvider(true, new AetherIIRecipeData.Runner(packOutput, registryProvider));
            generator.addProvider(true, new AetherIIRecipePrioritiesData(packOutput, registryProvider));
            generator.addProvider(true, AetherIILootTableData.create(packOutput));
            generator.addProvider(true, new AetherIILootModifierData(packOutput));
            generator.addProvider(true, new AetherIIAdvancementData(packOutput, registryProvider));
            generator.addProvider(true, new AetherIIDataMapData(packOutput, registryProvider));

            AetherIIBlockTagData blockTags = new AetherIIBlockTagData(packOutput, registryProvider, existingFileHelper);
            generator.addProvider(true, blockTags);
            generator.addProvider(true, new AetherIIItemTagData(packOutput, registryProvider, blockTags.contentsGetter(), existingFileHelper));
            generator.addProvider(true, new AetherIIEntityTypeTagData(packOutput, registryProvider, existingFileHelper));
            generator.addProvider(true, new AetherIIFluidTagData(packOutput, registryProvider, existingFileHelper));
            generator.addProvider(true, new AetherIIBiomeTagData(packOutput, registryProvider, existingFileHelper));
            generator.addProvider(true, new AetherIIFeatureTagData(packOutput, registryProvider));
            generator.addProvider(true, new AetherIIStructureTagData(packOutput, registryProvider, existingFileHelper));
            generator.addProvider(true, new AetherIIDamageTypeTagData(packOutput, registryProvider, existingFileHelper));
            generator.addProvider(true, new AetherIIMobEffectTagData(packOutput, registryProvider, existingFileHelper));
            generator.addProvider(true, new AetherIISoundEventTagData(packOutput, registryProvider, existingFileHelper));
        }

        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_ii.mod.description"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES))));
    }
}
