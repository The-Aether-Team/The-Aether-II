package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class AetherIIEquipmentAssets {
    public static ResourceKey<EquipmentAsset> BEAST_PELT = create("beast_pelt");
    public static ResourceKey<EquipmentAsset> BURRUKAI_PLATE = create("burrukai_plate");
    public static ResourceKey<EquipmentAsset> ZANITE = create("zanite");
    public static ResourceKey<EquipmentAsset> ARKENIUM = create("arkenium");
    public static ResourceKey<EquipmentAsset> GRAVITITE = create("gravitite");
    public static ResourceKey<EquipmentAsset> SENTRY = create("sentry");
    public static ResourceKey<EquipmentAsset> NEPTUNE = create("neptune");

    private static ResourceKey<EquipmentAsset> create(String name) {
        return ResourceKey.create(ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset")), Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }
}
