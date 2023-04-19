package com.aetherteam.aetherii;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.generators.*;
import com.aetherteam.aetherii.data.generators.tags.AetherIIBlockTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIEntityTagData;
import com.aetherteam.aetherii.data.generators.tags.AetherIIItemTagData;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.logging.LogUtils;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Mod(AetherII.MODID)
public class AetherII {
    public static final String MODID = "aether_ii";
    private static final Logger LOGGER = LogUtils.getLogger();

    public AetherII() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::curiosSetup);
        modEventBus.addListener(this::dataSetup);

        DeferredRegister<?>[] registers = {
                AetherIIBlocks.BLOCKS,
                AetherIIItems.ITEMS,
                AetherIIEntityTypes.ENTITY_TYPES
        };

        for (DeferredRegister<?> register : registers) {
            register.register(modEventBus);
        }
    }

    public void commonSetup(FMLCommonSetupEvent event) {

    }

    public void curiosSetup(InterModEnqueueEvent event) {

    }


    public void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new AetherIIBlockStateData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new AetherIIItemModelData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new AetherIILanguageData(packOutput));
        generator.addProvider(event.includeClient(), new AetherIISoundData(packOutput, fileHelper));

        // Server Data
        generator.addProvider(event.includeServer(), new AetherIIRegistrySets(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new AetherIIRecipeData(packOutput));
        generator.addProvider(event.includeServer(), AetherIILootTableData.create(packOutput));
//        generator.addProvider(event.includeServer(), new AetherIIAdvancementData(packOutput, lookupProvider, fileHelper));
        AetherIIBlockTagData blockTags = new AetherIIBlockTagData(packOutput, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new AetherIIItemTagData(packOutput, lookupProvider, blockTags.contentsGetter(), fileHelper));
        generator.addProvider(event.includeServer(), new AetherIIEntityTagData(packOutput, lookupProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new AetherIIFluidTagData(packOutput, lookupProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new AetherIIBiomeTagData(packOutput, lookupProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new AetherIIStructureTagData(packOutput, lookupProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new AetherIIDamageTypeTagData(packOutput, lookupProvider, fileHelper));

        // pack.mcmeta
        PackMetadataGenerator packMeta = new PackMetadataGenerator(packOutput);
        Map<PackType, Integer> packTypes = Map.of(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("pack.aether.mod.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), packTypes));
        generator.addProvider(true, packMeta);
    }
}
