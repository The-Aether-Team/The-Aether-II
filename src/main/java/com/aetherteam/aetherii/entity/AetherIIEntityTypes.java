package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.aetherteam.aetherii.entity.passive.AetherAnimal;
import com.aetherteam.aetherii.entity.passive.AetherTamableAnimal;
import com.aetherteam.aetherii.entity.passive.Phyg;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, AetherII.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<Phyg>> PHYG = ENTITY_TYPES.register("phyg",
            () -> EntityType.Builder.of(Phyg::new, MobCategory.CREATURE).sized(0.95F, 0.95F).clientTrackingRange(10).build("phyg"));

    public static final DeferredHolder<EntityType<?>, EntityType<Aerbunny>> AERBUNNY = ENTITY_TYPES.register("aerbunny",
            () -> EntityType.Builder.of(Aerbunny::new, MobCategory.CREATURE).sized(0.6F, 0.5F).clientTrackingRange(10).build("aerbunny"));

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(AetherIIEntityTypes.PHYG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.AERBUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherTamableAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(AetherIIEntityTypes.PHYG.get(), Phyg.createMobAttributes().build());
        event.put(AetherIIEntityTypes.AERBUNNY.get(), Aerbunny.createMobAttributes().build());
    }
}
