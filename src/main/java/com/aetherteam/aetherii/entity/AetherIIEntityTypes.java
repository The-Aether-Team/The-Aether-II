package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.entity.block.HoveringBlockEntity;
import com.aetherteam.aetherii.entity.monster.AechorPlant;
import com.aetherteam.aetherii.entity.monster.Cockatrice;
import com.aetherteam.aetherii.entity.monster.Tempest;
import com.aetherteam.aetherii.entity.monster.Zephyr;
import com.aetherteam.aetherii.entity.passive.*;
import com.aetherteam.aetherii.entity.projectile.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
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
            () -> EntityType.Builder.of(Aerbunny::new, MobCategory.CREATURE).sized(0.6F, 0.5F).clientTrackingRange(10).ridingOffset(0).build("aerbunny"));

    public static final DeferredHolder<EntityType<?>, EntityType<Taegore>> HIGHFIELDS_TAEGORE = ENTITY_TYPES.register("highfields_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.2F, 1.5F).clientTrackingRange(10).build("highfields_taegore"));
    public static final DeferredHolder<EntityType<?>, EntityType<Taegore>> MAGNETIC_TAEGORE = ENTITY_TYPES.register("magnetic_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.2F, 1.5F).clientTrackingRange(10).build("magnetic_taegore"));
    public static final DeferredHolder<EntityType<?>, EntityType<Taegore>> ARCTIC_TAEGORE = ENTITY_TYPES.register("arctic_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.2F, 1.5F).clientTrackingRange(10).build("arctic_taegore"));

    public static final DeferredHolder<EntityType<?>, EntityType<Burrukai>> HIGHFIELDS_BURRUKAI = ENTITY_TYPES.register("highfields_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).clientTrackingRange(10).build("highfields_burrukai"));
    public static final DeferredHolder<EntityType<?>, EntityType<Burrukai>> MAGNETIC_BURRUKAI = ENTITY_TYPES.register("magnetic_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).clientTrackingRange(10).build("magnetic_burrukai"));
    public static final DeferredHolder<EntityType<?>, EntityType<Burrukai>> ARCTIC_BURRUKAI = ENTITY_TYPES.register("arctic_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).clientTrackingRange(10).build("arctic_burrukai"));

    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> HIGHFIELDS_KIRRID = ENTITY_TYPES.register("highfields_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.9F, 1.25F).clientTrackingRange(10).build("highfields_kirrid"));
    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> MAGNETIC_KIRRID = ENTITY_TYPES.register("magnetic_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.9F, 1.25F).clientTrackingRange(10).build("magnetic_kirrid"));
    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> ARCTIC_KIRRID = ENTITY_TYPES.register("arctic_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.9F, 1.25F).clientTrackingRange(10).build("arctic_kirrid"));

    public static final DeferredHolder<EntityType<?>, EntityType<Moa>> MOA = ENTITY_TYPES.register("moa",
            () -> EntityType.Builder.of(Moa::new, MobCategory.CREATURE).sized(0.95F, 2.15F).clientTrackingRange(10).build("moa"));

    public static final DeferredHolder<EntityType<?>, EntityType<SkyrootLizard>> SKYROOT_LIZARD = ENTITY_TYPES.register("skyroot_lizard",
            () -> EntityType.Builder.of(SkyrootLizard::new, MobCategory.CREATURE).sized(0.9F, 0.5F).clientTrackingRange(10).ridingOffset(0).build("skyroot_lizard"));

    // Hostile
    public static final DeferredHolder<EntityType<?>, EntityType<AechorPlant>> AECHOR_PLANT = ENTITY_TYPES.register("aechor_plant",
            () -> EntityType.Builder.of(AechorPlant::new, AetherIIMobCategory.AETHER_SURFACE_MONSTER).sized(1.0F, 1.0F).clientTrackingRange(8).build("aechor_plant"));

    public static final DeferredHolder<EntityType<?>, EntityType<Zephyr>> ZEPHYR = ENTITY_TYPES.register("zephyr",
            () -> EntityType.Builder.of(Zephyr::new, AetherIIMobCategory.AETHER_SKY_MONSTER).sized(4.5F, 3.5F).clientTrackingRange(10).build("zephyr"));

    public static final DeferredHolder<EntityType<?>, EntityType<Tempest>> TEMPEST = ENTITY_TYPES.register("tempest",
            () -> EntityType.Builder.of(Tempest::new, AetherIIMobCategory.AETHER_SKY_MONSTER).sized(1.5F, 1.5F).clientTrackingRange(10).build("tempest"));
  
    public static final DeferredHolder<EntityType<?>, EntityType<Cockatrice>> COCKATRICE = ENTITY_TYPES.register("cockatrice",
            () -> EntityType.Builder.of(Cockatrice::new, AetherIIMobCategory.AETHER_SURFACE_MONSTER).sized(0.9F, 2.15F).clientTrackingRange(10).build("cockatrice"));


    // Projectiles
    public static final DeferredHolder<EntityType<?>, EntityType<HolystoneRock>> HOLYSTONE_ROCK = ENTITY_TYPES.register("holystone_rock",
            () -> EntityType.Builder.<HolystoneRock>of(HolystoneRock::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("holystone_rock"));

    public static final DeferredHolder<EntityType<?>, EntityType<ArcticSnowball>> ARCTIC_SNOWBALL = ENTITY_TYPES.register("arctic_snowball",
            () -> EntityType.Builder.<ArcticSnowball>of(ArcticSnowball::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("arctic_snowball"));

    public static final DeferredHolder<EntityType<?>, EntityType<SkyrootPinecone>> SKYROOT_PINECONE = ENTITY_TYPES.register("skyroot_pinecone",
            () -> EntityType.Builder.<SkyrootPinecone>of(SkyrootPinecone::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("skyroot_pinecone"));

    public static final DeferredHolder<EntityType<?>, EntityType<ScatterglassBolt>> SCATTERGLASS_BOLT = ENTITY_TYPES.register("scatterglass_bolt",
            () -> EntityType.Builder.<ScatterglassBolt>of(ScatterglassBolt::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("scatterglass_bolt"));

    public static final DeferredHolder<EntityType<?>, EntityType<ToxicDart>> TOXIC_DART = ENTITY_TYPES.register("toxic_dart",
            () -> EntityType.Builder.<ToxicDart>of(ToxicDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("toxic_dart"));

    public static final DeferredHolder<EntityType<?>, EntityType<ZephyrWebbingBall>> ZEPHYR_WEBBING_BALL = ENTITY_TYPES.register("zephyr_webbing_ball",
            () -> EntityType.Builder.<ZephyrWebbingBall>of(ZephyrWebbingBall::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("zephyr_webbing_ball"));

    public static final DeferredHolder<EntityType<?>, EntityType<TempestThunderball>> TEMPEST_THUNDERBALL = ENTITY_TYPES.register("tempest_thunderball",
            () -> EntityType.Builder.<TempestThunderball>of(TempestThunderball::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("tempest_thunderball"));


    // Blocks
    public static final DeferredHolder<EntityType<?>, EntityType<HoveringBlockEntity>> HOVERING_BLOCK = ENTITY_TYPES.register("hovering_block",
            () -> EntityType.Builder.<HoveringBlockEntity>of(HoveringBlockEntity::new, MobCategory.MISC).sized(0.9F, 0.9F).clientTrackingRange(10).updateInterval(20).build("hovering_block"));

    // Misc
    public static final DeferredHolder<EntityType<?>, EntityType<ElectricField>> ELECTRIC_FIELD = ENTITY_TYPES.register("electric_field",
            () -> EntityType.Builder.<ElectricField>of(ElectricField::new, MobCategory.MISC).fireImmune().sized(6.0F, 1.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("electric_field"));


    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        // Passive
        event.register(AetherIIEntityTypes.FLYING_COW.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SHEEPUFF.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.PHYG.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.AERBUNNY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherTamableAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ARCTIC_KIRRID.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.MOA.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);

        // Hostile
        event.register(AetherIIEntityTypes.AECHOR_PLANT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AechorPlant::checkAechorPlantSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ZEPHYR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zephyr::checkZephyrSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.TEMPEST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tempest::checkTempestSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.COCKATRICE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Cockatrice::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);

    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        // Passive
        event.put(AetherIIEntityTypes.FLYING_COW.get(), FlyingCow.createMobAttributes().build());
        event.put(AetherIIEntityTypes.SHEEPUFF.get(), Sheepuff.createMobAttributes().build());
        event.put(AetherIIEntityTypes.PHYG.get(), Phyg.createMobAttributes().build());
        event.put(AetherIIEntityTypes.AERBUNNY.get(), Aerbunny.createMobAttributes().build());
        event.put(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), Taegore.createMobAttributes().build());
        event.put(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), Taegore.createMobAttributes().build());
        event.put(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), Taegore.createMobAttributes().build());
        event.put(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), Burrukai.createMobAttributes().build());
        event.put(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), Burrukai.createMobAttributes().build());
        event.put(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), Burrukai.createMobAttributes().build());
        event.put(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), Kirrid.createMobAttributes().build());
        event.put(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), Kirrid.createMobAttributes().build());
        event.put(AetherIIEntityTypes.ARCTIC_KIRRID.get(), Kirrid.createMobAttributes().build());
        event.put(AetherIIEntityTypes.MOA.get(), Moa.createMobAttributes().build());
        event.put(AetherIIEntityTypes.SKYROOT_LIZARD.get(), SkyrootLizard.createMobAttributes().build());

        // Hostile
        event.put(AetherIIEntityTypes.AECHOR_PLANT.get(), AechorPlant.createMobAttributes().build());
        event.put(AetherIIEntityTypes.ZEPHYR.get(), Zephyr.createMobAttributes().build());
        event.put(AetherIIEntityTypes.TEMPEST.get(), Tempest.createMobAttributes().build());
        event.put(AetherIIEntityTypes.COCKATRICE.get(), Cockatrice.createMobAttributes().build());
    }
}