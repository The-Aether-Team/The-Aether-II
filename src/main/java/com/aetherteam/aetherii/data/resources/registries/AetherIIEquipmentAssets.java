package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class AetherIIEquipmentAssets {
    public static ResourceKey<EquipmentAsset> BEAST_PELT = create("beast_pelt");
    public static ResourceKey<EquipmentAsset> BURRUKAI_PLATE = create("burrukai_plate");
    public static ResourceKey<EquipmentAsset> ZANITE = create("zanite");
    public static ResourceKey<EquipmentAsset> ARKENIUM = create("arkenium");
    public static ResourceKey<EquipmentAsset> GRAVITITE = create("gravitite");
    public static ResourceKey<EquipmentAsset> SENTRY = create("sentry");
    public static ResourceKey<EquipmentAsset> NEPTUNE = create("neptune");

    public static ResourceKey<EquipmentAsset> ICESTONE_PENDANT = create("icestone_pendant");
    public static ResourceKey<EquipmentAsset> ZANITE_PENDANT = create("zanite_pendant");

    public static ResourceKey<EquipmentAsset> MOA_SADDLE = create("moa_saddle");
    public static ResourceKey<EquipmentAsset> MOA_SADDLEBAG = create("moa_saddlebag");
    public static ResourceKey<EquipmentAsset> LARGE_MOA_SADDLEBAG = create("large_moa_saddlebag");

    private static ResourceKey<EquipmentAsset> create(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }
}
