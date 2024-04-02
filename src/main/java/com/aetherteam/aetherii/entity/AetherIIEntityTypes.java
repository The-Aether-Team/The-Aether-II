package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, AetherII.MODID);

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {

    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {

    }
}
