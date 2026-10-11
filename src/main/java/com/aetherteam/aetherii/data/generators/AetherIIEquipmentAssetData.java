package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIEquipmentLayerTypes;
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
        consumer.accept(AetherIIEquipmentAssets.BEAST_PELT, addGlovedHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "beast_pelt"), true).build());
        consumer.accept(AetherIIEquipmentAssets.BURRUKAI_PLATE, addGlovedHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "burrukai_plate"), true).build());
        consumer.accept(AetherIIEquipmentAssets.ZANITE, addGlovedHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "zanite"), false).build());
        consumer.accept(AetherIIEquipmentAssets.ARKENIUM, addGlovedHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "arkenium"), false).build());
        consumer.accept(AetherIIEquipmentAssets.GRAVITITE, addGlovedHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "gravitite"), false).build());
        consumer.accept(AetherIIEquipmentAssets.SENTRY, addGlovedHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "sentry"), false).build());
        consumer.accept(AetherIIEquipmentAssets.NEPTUNE, addGlovedHumanoidLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "neptune"), false).build());

        consumer.accept(AetherIIEquipmentAssets.ICESTONE_PENDANT, addHumanoidAccessoryLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "icestone_pendant")).build());
        consumer.accept(AetherIIEquipmentAssets.ZANITE_PENDANT, addHumanoidAccessoryLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "zanite_pendant")).build());

        consumer.accept(AetherIIEquipmentAssets.MOA_SADDLE, addMoaSaddleLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "saddle")).build());
        consumer.accept(AetherIIEquipmentAssets.MOA_SADDLEBAG, addMoaSaddlebagLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "saddlebag")).build());
        consumer.accept(AetherIIEquipmentAssets.LARGE_MOA_SADDLEBAG, addMoaSaddlebagLayers(Identifier.fromNamespaceAndPath(AetherII.MODID, "saddlebag_large")).build());
    }

    private static EquipmentClientInfo.Builder addGlovedHumanoidLayers(Identifier textureId, boolean dyeable) {
        EquipmentClientInfo.Builder builder = EquipmentClientInfo.builder().addHumanoidLayers(textureId, dyeable);
        builder.addLayers(AetherIIEquipmentLayerTypes.HUMANOID_GLOVES, EquipmentClientInfo.Layer.leatherDyeable(textureId, dyeable));
        return builder;
    }

    private static EquipmentClientInfo.Builder addHumanoidAccessoryLayers(Identifier textureId) {
        return EquipmentClientInfo.builder().addLayers(AetherIIEquipmentLayerTypes.HUMANOID_ACCESSORY, new EquipmentClientInfo.Layer(textureId));
    }

    private static EquipmentClientInfo.Builder addMoaSaddleLayers(Identifier textureId) {
        return EquipmentClientInfo.builder()
                .addLayers(AetherIIEquipmentLayerTypes.MOA_SADDLE,
                        EquipmentClientInfo.Layer.leatherDyeable(textureId, true),
                        EquipmentClientInfo.Layer.leatherDyeable(textureId.withSuffix("_overlay"), false));
    }

    private static EquipmentClientInfo.Builder addMoaSaddlebagLayers(Identifier textureId) {
        return EquipmentClientInfo.builder().addLayers(AetherIIEquipmentLayerTypes.MOA_SADDLEBAG, new EquipmentClientInfo.Layer(textureId));
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
