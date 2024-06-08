package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.entity.monster.Zephyr;
import com.aetherteam.aetherii.entity.passive.*;
import com.aetherteam.aetherii.entity.projectile.ArcticSnowball;
import com.aetherteam.aetherii.entity.projectile.HolystoneRock;
import com.aetherteam.aetherii.entity.projectile.ScatterglassBolt;
import com.aetherteam.aetherii.entity.projectile.ZephyrSnowball;
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

    // Passive
    public static final DeferredHolder<EntityType<?>, EntityType<FlyingCow>> FLYING_COW = ENTITY_TYPES.register("flying_cow",
            () -> EntityType.Builder.of(FlyingCow::new, MobCategory.CREATURE).sized(0.95F, 1.5F).clientTrackingRange(10).build("flying_cow"));

    public static final DeferredHolder<EntityType<?>, EntityType<Sheepuff>> SHEEPUFF = ENTITY_TYPES.register("sheepuff",
            () -> EntityType.Builder.of(Sheepuff::new, MobCategory.CREATURE).sized(0.95F, 1.3F).clientTrackingRange(10).build("sheepuff"));


    public static final DeferredHolder<EntityType<?>, EntityType<Phyg>> PHYG = ENTITY_TYPES.register("phyg",
            () -> EntityType.Builder.of(Phyg::new, MobCategory.CREATURE).sized(0.95F, 0.95F).clientTrackingRange(10).build("phyg"));

    public static final DeferredHolder<EntityType<?>, EntityType<Aerbunny>> AERBUNNY = ENTITY_TYPES.register("aerbunny",
            () -> EntityType.Builder.of(Aerbunny::new, MobCategory.CREATURE).sized(0.6F, 0.5F).clientTrackingRange(10).build("aerbunny"));

    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> KIRRID = ENTITY_TYPES.register("kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.9F, 1.25F).clientTrackingRange(10).build("kirrid"));

    // Hostile
    public static final DeferredHolder<EntityType<?>, EntityType<Zephyr>> ZEPHYR = ENTITY_TYPES.register("zephyr",
            () -> EntityType.Builder.of(Zephyr::new, AetherIIMobCategory.AETHER_SKY_MONSTER).sized(4.5F, 3.5F).clientTrackingRange(10).build("zephyr"));

    // Projectiles
    public static final DeferredHolder<EntityType<?>, EntityType<HolystoneRock>> HOLYSTONE_ROCK = ENTITY_TYPES.register("holystone_rock",
            () -> EntityType.Builder.<HolystoneRock>of(HolystoneRock::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("holystone_rock"));

    public static final DeferredHolder<EntityType<?>, EntityType<ArcticSnowball>> ARCTIC_SNOWBALL = ENTITY_TYPES.register("arctic_snowball",
            () -> EntityType.Builder.<ArcticSnowball>of(ArcticSnowball::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("arctic_snowball"));

    public static final DeferredHolder<EntityType<?>, EntityType<ScatterglassBolt>> SCATTERGLASS_BOLT = ENTITY_TYPES.register("scatterglass_bolt",
            () -> EntityType.Builder.<ScatterglassBolt>of(ScatterglassBolt::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("scatterglass_bolt"));

    public static final DeferredHolder<EntityType<?>, EntityType<ZephyrSnowball>> ZEPHYR_SNOWBALL = ENTITY_TYPES.register("zephyr_snowball",
            () -> EntityType.Builder.<ZephyrSnowball>of(ZephyrSnowball::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).updateInterval(10).build("zephyr_snowball"));

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        // Passive
        event.register(AetherIIEntityTypes.FLYING_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SHEEPUFF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.PHYG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.AERBUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherTamableAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.KIRRID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

        // Hostile
        event.register(AetherIIEntityTypes.ZEPHYR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zephyr::checkZephyrSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        // Passive
        event.put(AetherIIEntityTypes.FLYING_COW.get(), FlyingCow.createMobAttributes().build());
        event.put(AetherIIEntityTypes.SHEEPUFF.get(), Sheepuff.createMobAttributes().build());
        event.put(AetherIIEntityTypes.PHYG.get(), Phyg.createMobAttributes().build());
        event.put(AetherIIEntityTypes.AERBUNNY.get(), Aerbunny.createMobAttributes().build());
        event.put(AetherIIEntityTypes.KIRRID.get(), Kirrid.createMobAttributes().build());

        // Hostile
        event.put(AetherIIEntityTypes.ZEPHYR.get(), Zephyr.createMobAttributes().build());
    }
}
