package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEquipmentAssets;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class AetherIIEquipmentAssetData extends EquipmentAssetProvider {
    private final PackOutput.PathProvider pathProvider;

    public AetherIIEquipmentAssetData(PackOutput packOutput) {
        super(packOutput);
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer) {
        consumer.accept(AetherIIEquipmentAssets.BEAST_PELT, EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "beast_pelt"), true).build());
        consumer.accept(AetherIIEquipmentAssets.BURRUKAI_PLATE, EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "burrukai_plate"), true).build());
        consumer.accept(AetherIIEquipmentAssets.ZANITE, EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "zanite")).build());
        consumer.accept(AetherIIEquipmentAssets.ARKENIUM, EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "arkenium")).build());
        consumer.accept(AetherIIEquipmentAssets.GRAVITITE, EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "gravitite")).build());
        consumer.accept(AetherIIEquipmentAssets.SENTRY, EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "sentry")).build());
        consumer.accept(AetherIIEquipmentAssets.NEPTUNE, EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "neptune")).build());
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> map = new HashMap<>();
        bootstrap((key, info) -> {
            if (map.putIfAbsent(key, info) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + key);
            }
        });
        return DataProvider.saveAll(output, EquipmentClientInfo.CODEC, this.pathProvider::json, map);
    }
}
