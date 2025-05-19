package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEntities;
import com.aetherteam.aetherii.entity.block.HoveringBlockEntity;
import com.aetherteam.aetherii.entity.monster.*;
import com.aetherteam.aetherii.entity.npc.outpost.Edward;
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
    public static final DeferredHolder<EntityType<?>, EntityType<Phyg>> PHYG = ENTITY_TYPES.register("phyg",
            () -> EntityType.Builder.of(Phyg::new, MobCategory.CREATURE).sized(0.95F, 0.95F).clientTrackingRange(10).build(AetherIIEntities.PHYG));
    public static final DeferredHolder<EntityType<?>, EntityType<FlyingCow>> FLYING_COW = ENTITY_TYPES.register("flying_cow",
            () -> EntityType.Builder.of(FlyingCow::new, MobCategory.CREATURE).sized(0.95F, 1.5F).clientTrackingRange(10).build(AetherIIEntities.FLYING_COW));
    public static final DeferredHolder<EntityType<?>, EntityType<Sheepuff>> SHEEPUFF = ENTITY_TYPES.register("sheepuff",
            () -> EntityType.Builder.of(Sheepuff::new, MobCategory.CREATURE).sized(0.95F, 1.3F).clientTrackingRange(10).build(AetherIIEntities.SHEEPUFF));

    public static final DeferredHolder<EntityType<?>, EntityType<Aerbunny>> AERBUNNY = ENTITY_TYPES.register("aerbunny",
            () -> EntityType.Builder.of(Aerbunny::new, MobCategory.CREATURE).sized(0.6F, 0.5F).clientTrackingRange(10).ridingOffset(0).build(AetherIIEntities.AERBUNNY));

    public static final DeferredHolder<EntityType<?>, EntityType<Taegore>> HIGHFIELDS_TAEGORE = ENTITY_TYPES.register("highfields_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.2F, 1.5F).clientTrackingRange(10).build(AetherIIEntities.HIGHFIELDS_TAEGORE));
    public static final DeferredHolder<EntityType<?>, EntityType<Taegore>> MAGNETIC_TAEGORE = ENTITY_TYPES.register("magnetic_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.2F, 1.5F).clientTrackingRange(10).build(AetherIIEntities.MAGNETIC_TAEGORE));
    public static final DeferredHolder<EntityType<?>, EntityType<Taegore>> ARCTIC_TAEGORE = ENTITY_TYPES.register("arctic_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.2F, 1.5F).clientTrackingRange(10).build(AetherIIEntities.ARCTIC_TAEGORE));

    public static final DeferredHolder<EntityType<?>, EntityType<Burrukai>> HIGHFIELDS_BURRUKAI = ENTITY_TYPES.register("highfields_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).clientTrackingRange(10).build(AetherIIEntities.HIGHFIELDS_BURRUKAI));
    public static final DeferredHolder<EntityType<?>, EntityType<Burrukai>> MAGNETIC_BURRUKAI = ENTITY_TYPES.register("magnetic_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).clientTrackingRange(10).build(AetherIIEntities.MAGNETIC_BURRUKAI));
    public static final DeferredHolder<EntityType<?>, EntityType<Burrukai>> ARCTIC_BURRUKAI = ENTITY_TYPES.register("arctic_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).clientTrackingRange(10).build(AetherIIEntities.ARCTIC_BURRUKAI));

    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> HIGHFIELDS_KIRRID = ENTITY_TYPES.register("highfields_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.9F, 1.25F).clientTrackingRange(10).build(AetherIIEntities.HIGHFIELDS_KIRRID));
    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> MAGNETIC_KIRRID = ENTITY_TYPES.register("magnetic_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.9F, 1.25F).clientTrackingRange(10).build(AetherIIEntities.MAGNETIC_KIRRID));
    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> ARCTIC_KIRRID = ENTITY_TYPES.register("arctic_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.9F, 1.25F).clientTrackingRange(10).build(AetherIIEntities.ARCTIC_KIRRID));

    public static final DeferredHolder<EntityType<?>, EntityType<Moa>> MOA = ENTITY_TYPES.register("moa",
            () -> EntityType.Builder.of(Moa::new, MobCategory.CREATURE).sized(0.95F, 2.15F).clientTrackingRange(10).build(AetherIIEntities.MOA));

    public static final DeferredHolder<EntityType<?>, EntityType<SkyrootLizard>> SKYROOT_LIZARD = ENTITY_TYPES.register("skyroot_lizard",
            () -> EntityType.Builder.of(SkyrootLizard::new, MobCategory.CREATURE).sized(0.9F, 0.5F).clientTrackingRange(10).ridingOffset(0).build(AetherIIEntities.SKYROOT_LIZARD));

    // Hostile
    public static final DeferredHolder<EntityType<?>, EntityType<AechorPlant>> AECHOR_PLANT = ENTITY_TYPES.register("aechor_plant",
            () -> EntityType.Builder.of(AechorPlant::new, AetherIIMobCategory.AETHER_SURFACE_MONSTER).sized(1.0F, 1.0F).clientTrackingRange(8).build(AetherIIEntities.AECHOR_PLANT));
    public static final DeferredHolder<EntityType<?>, EntityType<CarrionSprout>> CARRION_SPROUT = ENTITY_TYPES.register("carrion_sprout",
            () -> EntityType.Builder.of(CarrionSprout::new, AetherIIMobCategory.AETHER_SURFACE_MONSTER).sized(1.0F, 1.0F).clientTrackingRange(8).build(AetherIIEntities.CARRION_SPROUT));

    public static final DeferredHolder<EntityType<?>, EntityType<Zephyr>> ZEPHYR = ENTITY_TYPES.register("zephyr",
            () -> EntityType.Builder.of(Zephyr::new, AetherIIMobCategory.AETHER_SKY_MONSTER).sized(2.0F, 1.75F).clientTrackingRange(10).build(AetherIIEntities.ZEPHYR));
    public static final DeferredHolder<EntityType<?>, EntityType<Tempest>> TEMPEST = ENTITY_TYPES.register("tempest",
            () -> EntityType.Builder.of(Tempest::new, AetherIIMobCategory.AETHER_SKY_MONSTER).sized(1.5F, 1.5F).clientTrackingRange(10).build(AetherIIEntities.TEMPEST));
  
    public static final DeferredHolder<EntityType<?>, EntityType<Cockatrice>> COCKATRICE = ENTITY_TYPES.register("cockatrice",
            () -> EntityType.Builder.of(Cockatrice::new, MobCategory.MONSTER).sized(0.9F, 2.15F).clientTrackingRange(10).build(AetherIIEntities.COCKATRICE));
    public static final DeferredHolder<EntityType<?>, EntityType<Swet>> SWET = ENTITY_TYPES.register("swet",
            () -> EntityType.Builder.of(Swet::new, MobCategory.MONSTER).sized(0.9F, 0.9F).clientTrackingRange(10).build(AetherIIEntities.SWET));
    public static final DeferredHolder<EntityType<?>, EntityType<Skephid>> SKEPHID = ENTITY_TYPES.register("skephid",
            () -> EntityType.Builder.of(Skephid::new, MobCategory.MONSTER).sized(0.8F, 0.8F).clientTrackingRange(10).build(AetherIIEntities.SKEPHID));
    public static final DeferredHolder<EntityType<?>, EntityType<ArkeniumTaluton>> ARKENIUM_TALUTON = ENTITY_TYPES.register("arkenium_taluton",
            () -> EntityType.Builder.of(ArkeniumTaluton::new, MobCategory.MONSTER).sized(1.0F, 1.65F).eyeHeight(1.25F).clientTrackingRange(10).build(AetherIIEntities.ARKENIUM_TALUTON));
    public static final DeferredHolder<EntityType<?>, EntityType<GravititeTaluton>> GRAVITITE_TALUTON = ENTITY_TYPES.register("gravitite_taluton",
            () -> EntityType.Builder.of(GravititeTaluton::new, MobCategory.MONSTER).sized(0.75F, 1.9F).eyeHeight(1.4F).clientTrackingRange(10).build(AetherIIEntities.GRAVITITE_TALUTON));

    // NPCs
    public static final DeferredHolder<EntityType<?>, EntityType<Edward>> EDWARD = ENTITY_TYPES.register("edward",
            () -> EntityType.Builder.of(Edward::new, MobCategory.CREATURE).sized(0.6F, 1.95F).eyeHeight(1.74F).passengerAttachments(2.0125F).ridingOffset(-0.7F).clientTrackingRange(8).build(AetherIIEntities.EDWARD));

    // Projectiles
    public static final DeferredHolder<EntityType<?>, EntityType<HolystoneRock>> HOLYSTONE_ROCK = ENTITY_TYPES.register("holystone_rock",
            () -> EntityType.Builder.<HolystoneRock>of(HolystoneRock::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntities.HOLYSTONE_ROCK));

    public static final DeferredHolder<EntityType<?>, EntityType<ArcticSnowball>> ARCTIC_SNOWBALL = ENTITY_TYPES.register("arctic_snowball",
            () -> EntityType.Builder.<ArcticSnowball>of(ArcticSnowball::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntities.ARCTIC_SNOWBALL));

    public static final DeferredHolder<EntityType<?>, EntityType<SkyrootPinecone>> SKYROOT_PINECONE = ENTITY_TYPES.register("skyroot_pinecone",
            () -> EntityType.Builder.<SkyrootPinecone>of(SkyrootPinecone::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntities.SKYROOT_PINECONE));

    public static final DeferredHolder<EntityType<?>, EntityType<ScatterglassBolt>> SCATTERGLASS_BOLT = ENTITY_TYPES.register("scatterglass_bolt",
            () -> EntityType.Builder.<ScatterglassBolt>of(ScatterglassBolt::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).noLootTable().build(AetherIIEntities.SCATTERGLASS_BOLT));

    public static final DeferredHolder<EntityType<?>, EntityType<ToxicDart>> TOXIC_DART = ENTITY_TYPES.register("toxic_dart",
            () -> EntityType.Builder.<ToxicDart>of(ToxicDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).noLootTable().build(AetherIIEntities.TOXIC_DART));
    public static final DeferredHolder<EntityType<?>, EntityType<VenomousDart>> VENOMOUS_DART = ENTITY_TYPES.register("venomous_dart",
            () -> EntityType.Builder.<VenomousDart>of(VenomousDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).noLootTable().build(AetherIIEntities.VENOMOUS_DART));

    public static final DeferredHolder<EntityType<?>, EntityType<ZephyrWebbingBall>> ZEPHYR_WEBBING_BALL = ENTITY_TYPES.register("zephyr_webbing_ball",
            () -> EntityType.Builder.<ZephyrWebbingBall>of(ZephyrWebbingBall::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntities.ZEPHYR_WEBBING_BALL));
    public static final DeferredHolder<EntityType<?>, EntityType<SkephidWebbingBall>> SKEPHID_WEBBING_BALL = ENTITY_TYPES.register("skephid_webbing_ball",
            () -> EntityType.Builder.<SkephidWebbingBall>of(SkephidWebbingBall::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntities.SKEPHID_WEBBING_BALL));

    public static final DeferredHolder<EntityType<?>, EntityType<TempestThunderball>> TEMPEST_THUNDERBALL = ENTITY_TYPES.register("tempest_thunderball",
            () -> EntityType.Builder.<TempestThunderball>of(TempestThunderball::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntities.TEMPEST_THUNDERBALL));

    public static final DeferredHolder<EntityType<?>, EntityType<GravititeDebrisShot>> GRAVITITE_DEBRIS_SHOT = ENTITY_TYPES.register("gravitite_debris_shot",
            () -> EntityType.Builder.<GravititeDebrisShot>of(GravititeDebrisShot::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).noLootTable().build(AetherIIEntities.GRAVITITE_DEBRIS_SHOT));

    // Blocks
    public static final DeferredHolder<EntityType<?>, EntityType<HoveringBlockEntity>> HOVERING_BLOCK = ENTITY_TYPES.register("hovering_block",
            () -> EntityType.Builder.<HoveringBlockEntity>of(HoveringBlockEntity::new, MobCategory.MISC).sized(0.9F, 0.9F).clientTrackingRange(10).updateInterval(20).noLootTable().build(AetherIIEntities.HOVERING_BLOCK));

    // Misc
    public static final DeferredHolder<EntityType<?>, EntityType<ElectricField>> ELECTRIC_FIELD = ENTITY_TYPES.register("electric_field",
            () -> EntityType.Builder.<ElectricField>of(ElectricField::new, MobCategory.MISC).fireImmune().sized(6.0F, 1.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).noLootTable().build(AetherIIEntities.ELECTRIC_FIELD));


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
        event.register(AetherIIEntityTypes.CARRION_SPROUT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CarrionSprout::checkCarrionSproutSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);

        // Hostile
        event.register(AetherIIEntityTypes.AECHOR_PLANT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AechorPlant::checkAechorPlantSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ZEPHYR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zephyr::checkZephyrSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.TEMPEST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tempest::checkTempestSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.COCKATRICE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Cockatrice::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SWET.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SKEPHID.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Skephid::checkSkephidSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Taluton::checkTalutonSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Taluton::checkTalutonSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        // Passive
        event.put(AetherIIEntityTypes.FLYING_COW.get(), FlyingCow.createMobAttributes().build());
        event.put(AetherIIEntityTypes.SHEEPUFF.get(), Sheepuff.createMobAttributes().build());
        event.put(AetherIIEntityTypes.PHYG.get(), Phyg.createMobAttributes().build());
        event.put(AetherIIEntityTypes.AERBUNNY.get(), AetherIIStats.merge(Aerbunny.createMobAttributes(), AetherIIStats.AERBUNNY).build());
        event.put(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), AetherIIStats.merge(Taegore.createMobAttributes(), AetherIIStats.HIGHFIELDS_TAEGORE).build());
        event.put(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), AetherIIStats.merge(Taegore.createMobAttributes(), AetherIIStats.MAGNETIC_TAEGORE).build());
        event.put(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), AetherIIStats.merge(Taegore.createMobAttributes(), AetherIIStats.ARCTIC_TAEGORE).build());
        event.put(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), AetherIIStats.merge(Burrukai.createMobAttributes(), AetherIIStats.HIGHFIELDS_BURRUKAI).build());
        event.put(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), AetherIIStats.merge(Burrukai.createMobAttributes(), AetherIIStats.MAGNETIC_BURRUKAI).build());
        event.put(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), AetherIIStats.merge(Burrukai.createMobAttributes(), AetherIIStats.ARCTIC_BURRUKAI).build());
        event.put(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIStats.merge(Kirrid.createMobAttributes(), AetherIIStats.HIGHFIELDS_KIRRID).build());
        event.put(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIStats.merge(Kirrid.createMobAttributes(), AetherIIStats.MAGNETIC_KIRRID).build());
        event.put(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIStats.merge(Kirrid.createMobAttributes(), AetherIIStats.ARCTIC_KIRRID).build());
        event.put(AetherIIEntityTypes.MOA.get(), AetherIIStats.merge(Moa.createMobAttributes(), AetherIIStats.MOA).build());
        event.put(AetherIIEntityTypes.SKYROOT_LIZARD.get(), SkyrootLizard.createMobAttributes().build());
        event.put(AetherIIEntityTypes.CARRION_SPROUT.get(), AetherIIStats.merge(CarrionSprout.createMobAttributes(), AetherIIStats.CARRION_SPROUT).build());

        // Hostile
        event.put(AetherIIEntityTypes.AECHOR_PLANT.get(), AetherIIStats.merge(AechorPlant.createMobAttributes(), AetherIIStats.AECHOR_PLANT).build());
        event.put(AetherIIEntityTypes.ZEPHYR.get(), AetherIIStats.merge(Zephyr.createMobAttributes(), AetherIIStats.ZEPHYR).build());
        event.put(AetherIIEntityTypes.TEMPEST.get(), AetherIIStats.merge(Tempest.createMobAttributes(), AetherIIStats.TEMPEST).build());
        event.put(AetherIIEntityTypes.COCKATRICE.get(), AetherIIStats.merge(Cockatrice.createMobAttributes(), AetherIIStats.COCKATRICE).build());
        event.put(AetherIIEntityTypes.SWET.get(), AetherIIStats.merge(Swet.createMobAttributes(), AetherIIStats.SWET).build());
        event.put(AetherIIEntityTypes.SKEPHID.get(), AetherIIStats.merge(Skephid.createMobAttributes(), AetherIIStats.SKEPHID).build());
        event.put(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), AetherIIStats.merge(ArkeniumTaluton.createMobAttributes(), AetherIIStats.ARKENIUM_TALUTON).build());
        event.put(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), AetherIIStats.merge(GravititeTaluton.createMobAttributes(), AetherIIStats.GRAVITITE_TALUTON).build());

        // NPCs
        event.put(AetherIIEntityTypes.EDWARD.get(), Edward.createMobAttributes().build());
    }
}