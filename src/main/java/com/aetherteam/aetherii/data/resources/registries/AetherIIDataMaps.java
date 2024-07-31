package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.maps.BucketReplacement;
import com.aetherteam.aetherii.data.resources.maps.DamageInfliction;
import com.aetherteam.aetherii.data.resources.maps.DamageResistance;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class AetherIIDataMaps {
    public static final DataMapType<Item, BucketReplacement> BUCKET_REPLACEMENT = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "bucket_replacement"), Registries.ITEM, BucketReplacement.CODEC).synced(BucketReplacement.ITEM_CODEC, false).build();
    public static final DataMapType<Item, DamageInfliction> DAMAGE_INFLICTION = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "damage_infliction"), Registries.ITEM, DamageInfliction.CODEC).synced(DamageInfliction.INFLICTION_CODEC, false).build();
    public static final DataMapType<EntityType<?>, DamageResistance> DAMAGE_RESISTANCE = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "damage_resistance"), Registries.ENTITY_TYPE, DamageResistance.CODEC).synced(DamageResistance.RESISTANCE_CODEC, false).build();

    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(BUCKET_REPLACEMENT);
        event.register(DAMAGE_INFLICTION);
        event.register(DAMAGE_RESISTANCE);
    }
}