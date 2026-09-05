package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEntityIds;
import com.aetherteam.aetherii.entity.block.HoveringBlockEntity;
import com.aetherteam.aetherii.entity.block.SittableEntity;
import com.aetherteam.aetherii.entity.monster.*;
import com.aetherteam.aetherii.entity.monster.dungeon.DetonationSentry;
import com.aetherteam.aetherii.entity.monster.dungeon.Mimic;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryGolem;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import com.aetherteam.aetherii.entity.npc.outpost.Edward;
import com.aetherteam.aetherii.entity.passive.*;
import com.aetherteam.aetherii.entity.projectile.*;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, AetherII.MODID);

    // Passive
    public static final DeferredHolder<EntityType<?>, EntityType<Phyg>> PHYG = ENTITY_TYPES.register("phyg",
            () -> EntityType.Builder.of(Phyg::new, MobCategory.CREATURE).sized(0.95F, 0.85F).clientTrackingRange(10).build(AetherIIEntityIds.PHYG));
    public static final DeferredHolder<EntityType<?>, EntityType<FlyingCow>> FLYING_COW = ENTITY_TYPES.register("flying_cow",
            () -> EntityType.Builder.of(FlyingCow::new, MobCategory.CREATURE).sized(0.95F, 1.1F).eyeHeight(1.05F).clientTrackingRange(10).build(AetherIIEntityIds.FLYING_COW));
    public static final DeferredHolder<EntityType<?>, EntityType<Sheepuff>> SHEEPUFF = ENTITY_TYPES.register("sheepuff",
            () -> EntityType.Builder.of(Sheepuff::new, MobCategory.CREATURE).sized(0.9F, 1.25F).eyeHeight(0.85F).clientTrackingRange(10).build(AetherIIEntityIds.SHEEPUFF));

    public static final DeferredHolder<EntityType<?>, EntityType<Aerbunny>> AERBUNNY = ENTITY_TYPES.register("aerbunny",
            () -> EntityType.Builder.of(Aerbunny::new, MobCategory.CREATURE).sized(0.55F, 0.45F).eyeHeight(0.25F).clientTrackingRange(10).ridingOffset(0).build(AetherIIEntityIds.AERBUNNY));

    public static final DeferredHolder<EntityType<?>, EntityType<Aerwhale>> AERWHALE = ENTITY_TYPES.register("aerwhale",
            () -> EntityType.Builder.of(Aerwhale::new, AetherIIMobCategory.AETHER_AERWHALE).fireImmune().sized(3.0F, 3.0F).clientTrackingRange(10).build(AetherIIEntityIds.AERWHALE));

    public static final DeferredHolder<EntityType<?>, EntityType<Taegore>> HIGHFIELDS_TAEGORE = ENTITY_TYPES.register("highfields_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.3F, 1.7F).eyeHeight(0.85F).clientTrackingRange(10).build(AetherIIEntityIds.HIGHFIELDS_TAEGORE));
    public static final DeferredHolder<EntityType<?>, EntityType<Taegore>> MAGNETIC_TAEGORE = ENTITY_TYPES.register("magnetic_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.3F, 1.7F).eyeHeight(0.85F).clientTrackingRange(10).build(AetherIIEntityIds.MAGNETIC_TAEGORE));
    public static final DeferredHolder<EntityType<?>, EntityType<Taegore>> ARCTIC_TAEGORE = ENTITY_TYPES.register("arctic_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.3F, 1.7F).eyeHeight(0.85F).clientTrackingRange(10).build(AetherIIEntityIds.ARCTIC_TAEGORE));

    public static final DeferredHolder<EntityType<?>, EntityType<Burrukai>> HIGHFIELDS_BURRUKAI = ENTITY_TYPES.register("highfields_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).eyeHeight(1.2F).clientTrackingRange(10).build(AetherIIEntityIds.HIGHFIELDS_BURRUKAI));
    public static final DeferredHolder<EntityType<?>, EntityType<Burrukai>> MAGNETIC_BURRUKAI = ENTITY_TYPES.register("magnetic_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).eyeHeight(1.2F).clientTrackingRange(10).build(AetherIIEntityIds.MAGNETIC_BURRUKAI));
    public static final DeferredHolder<EntityType<?>, EntityType<Burrukai>> ARCTIC_BURRUKAI = ENTITY_TYPES.register("arctic_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).eyeHeight(1.2F).clientTrackingRange(10).build(AetherIIEntityIds.ARCTIC_BURRUKAI));

    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> HIGHFIELDS_KIRRID = ENTITY_TYPES.register("highfields_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.95F, 1.35F).eyeHeight(1.1F).clientTrackingRange(10).build(AetherIIEntityIds.HIGHFIELDS_KIRRID));
    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> MAGNETIC_KIRRID = ENTITY_TYPES.register("magnetic_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.95F, 1.35F).eyeHeight(1.1F).clientTrackingRange(10).build(AetherIIEntityIds.MAGNETIC_KIRRID));
    public static final DeferredHolder<EntityType<?>, EntityType<Kirrid>> ARCTIC_KIRRID = ENTITY_TYPES.register("arctic_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.95F, 1.35F).eyeHeight(1.1F).clientTrackingRange(10).build(AetherIIEntityIds.ARCTIC_KIRRID));

    public static final DeferredHolder<EntityType<?>, EntityType<Moa>> MOA = ENTITY_TYPES.register("moa",
            () -> EntityType.Builder.of(Moa::new, AetherIIMobCategory.AETHER_MOA).sized(1.25F, 2.35F).eyeHeight(2.1F).clientTrackingRange(10).build(AetherIIEntityIds.MOA));

    public static final DeferredHolder<EntityType<?>, EntityType<Prismallard>> PRISMALLARD = ENTITY_TYPES.register("prismallard",
            () -> EntityType.Builder.of(Prismallard::new, AetherIIMobCategory.AETHER_WATER_SURFACE_CREATURE).sized(0.5F, 0.6F).eyeHeight(0.55F).clientTrackingRange(10).build(AetherIIEntityIds.PRISMALLARD));


    public static final DeferredHolder<EntityType<?>, EntityType<SkyrootLizard>> SKYROOT_LIZARD = ENTITY_TYPES.register("skyroot_lizard",
            () -> EntityType.Builder.of(SkyrootLizard::new, AetherIIMobCategory.AETHER_AMBIENT).sized(0.95F, 0.35F).clientTrackingRange(10).build(AetherIIEntityIds.SKYROOT_LIZARD));

    public static final DeferredHolder<EntityType<?>, EntityType<Glitterwing>> GLITTERWING = ENTITY_TYPES.register("glitterwing",
            () -> EntityType.Builder.<Glitterwing>of(Glitterwing::new, AetherIIMobCategory.AETHER_AMBIENT).sized(0.75F, 0.75F).eyeHeight(0.25F).clientTrackingRange(5).build(AetherIIEntityIds.GLITTERWING));
    public static final DeferredHolder<EntityType<?>, EntityType<Shroudwing>> SHROUDWING = ENTITY_TYPES.register("shroudwing",
            () -> EntityType.Builder.<Shroudwing>of(Shroudwing::new, AetherIIMobCategory.AETHER_AMBIENT).sized(0.5F, 0.5F).eyeHeight(0.25F).clientTrackingRange(5).build(AetherIIEntityIds.SHROUDWING));
    public static final DeferredHolder<EntityType<?>, EntityType<Bird>> BIRD = ENTITY_TYPES.register("bird",
            () -> EntityType.Builder.<Bird>of(Bird::new, AetherIIMobCategory.AETHER_AMBIENT).sized(0.5F, 0.9F).eyeHeight(0.54F).clientTrackingRange(8).build(AetherIIEntityIds.BIRD));

    // Hostile
    public static final DeferredHolder<EntityType<?>, EntityType<AechorPlant>> AECHOR_PLANT = ENTITY_TYPES.register("aechor_plant",
            () -> EntityType.Builder.of(AechorPlant::new, AetherIIMobCategory.AETHER_PLANT_HAZARD).sized(0.9F, 0.6F).notInPeaceful().eyeHeight(0.25F).clientTrackingRange(8).build(AetherIIEntityIds.AECHOR_PLANT));
    public static final DeferredHolder<EntityType<?>, EntityType<CarrionSprout>> CARRION_SPROUT = ENTITY_TYPES.register("carrion_sprout",
            () -> EntityType.Builder.of(CarrionSprout::new, AetherIIMobCategory.AETHER_PLANT_HAZARD).sized(1.0F, 1.0F).clientTrackingRange(8).build(AetherIIEntityIds.CARRION_SPROUT));

    public static final DeferredHolder<EntityType<?>, EntityType<Zephyr>> ZEPHYR = ENTITY_TYPES.register("zephyr",
            () -> EntityType.Builder.of(Zephyr::new, AetherIIMobCategory.AETHER_SKY_HAZARD).sized(2.0F, 1.75F).notInPeaceful().eyeHeight(1.2F).clientTrackingRange(10).build(AetherIIEntityIds.ZEPHYR));

    public static final DeferredHolder<EntityType<?>, EntityType<Swet>> BLUE_SWET = ENTITY_TYPES.register("blue_swet",
            () -> EntityType.Builder.of(Swet::new, AetherIIMobCategory.AETHER_DARKNESS_HAZARD).sized(0.95F, 0.95F).notInPeaceful().clientTrackingRange(10).build(AetherIIEntityIds.BLUE_SWET));
    public static final DeferredHolder<EntityType<?>, EntityType<Swet>> GOLDEN_SWET = ENTITY_TYPES.register("golden_swet",
            () -> EntityType.Builder.of(Swet::new, AetherIIMobCategory.AETHER_DARKNESS_HAZARD).sized(0.95F, 0.95F).notInPeaceful().clientTrackingRange(10).build(AetherIIEntityIds.GOLDEN_SWET));
    public static final DeferredHolder<EntityType<?>, EntityType<Skephid>> SKEPHID = ENTITY_TYPES.register("skephid",
            () -> EntityType.Builder.of(Skephid::new, AetherIIMobCategory.AETHER_DARKNESS_HAZARD).sized(0.8F, 0.8F).notInPeaceful().clientTrackingRange(10).build(AetherIIEntityIds.SKEPHID));

    public static final DeferredHolder<EntityType<?>, EntityType<Tempest>> TEMPEST = ENTITY_TYPES.register("tempest",
            () -> EntityType.Builder.of(Tempest::new, AetherIIMobCategory.AETHER_BLIGHT_MONSTER).sized(1.5F, 1.4F).notInPeaceful().eyeHeight(0.85F).clientTrackingRange(10).build(AetherIIEntityIds.TEMPEST));
    public static final DeferredHolder<EntityType<?>, EntityType<Cockatrice>> COCKATRICE = ENTITY_TYPES.register("cockatrice",
            () -> EntityType.Builder.of(Cockatrice::new, AetherIIMobCategory.AETHER_BLIGHT_MONSTER).sized(0.9F, 2.15F).notInPeaceful().clientTrackingRange(10).build(AetherIIEntityIds.COCKATRICE));

    public static final DeferredHolder<EntityType<?>, EntityType<ArkeniumTaluton>> ARKENIUM_TALUTON = ENTITY_TYPES.register("arkenium_taluton",
            () -> EntityType.Builder.of(ArkeniumTaluton::new, AetherIIMobCategory.AETHER_DARKNESS_MONSTER).sized(1.0F, 1.65F).notInPeaceful().eyeHeight(1.25F).clientTrackingRange(10).build(AetherIIEntityIds.ARKENIUM_TALUTON));
    public static final DeferredHolder<EntityType<?>, EntityType<GravititeTaluton>> GRAVITITE_TALUTON = ENTITY_TYPES.register("gravitite_taluton",
            () -> EntityType.Builder.of(GravititeTaluton::new, AetherIIMobCategory.AETHER_DARKNESS_MONSTER).sized(0.75F, 1.9F).notInPeaceful().eyeHeight(1.4F).clientTrackingRange(10).build(AetherIIEntityIds.GRAVITITE_TALUTON));

    public static final DeferredHolder<EntityType<?>, EntityType<Mimic>> MIMIC = ENTITY_TYPES.register("mimic",
            () -> EntityType.Builder.of(Mimic::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(0.9F, 1.35F).notInPeaceful().clientTrackingRange(8).build(AetherIIEntityIds.MIMIC));

    public static final DeferredHolder<EntityType<?>, EntityType<DetonationSentry>> DETONATION_SENTRY = ENTITY_TYPES.register("detonation_sentry",
            () -> EntityType.Builder.of(DetonationSentry::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(0.9F, 0.9F).notInPeaceful().eyeHeight(0.45F).clientTrackingRange(10).build(AetherIIEntityIds.DETONATION_SENTRY));
    public static final DeferredHolder<EntityType<?>, EntityType<SentryGolem>> SENTRY_GOLEM = ENTITY_TYPES.register("sentry_golem",
            () -> EntityType.Builder.of(SentryGolem::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(0.6F, 1.95F).notInPeaceful().eyeHeight(1.8F).clientTrackingRange(8).build(AetherIIEntityIds.SENTRY_GOLEM));

    public static final DeferredHolder<EntityType<?>, EntityType<Slider>> SLIDER = ENTITY_TYPES.register("slider",
            () -> EntityType.Builder.of(Slider::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(2.0F, 2.0F).notInPeaceful().fireImmune().clientTrackingRange(10).build(AetherIIEntityIds.SLIDER));

    public static final DeferredHolder<EntityType<?>, EntityType<BladeshroomHunter>> BLADESHROOM_HUNTER = ENTITY_TYPES.register("bladeshroom_hunter",
            () -> EntityType.Builder.of(BladeshroomHunter::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(0.9F, 1.3F).notInPeaceful().eyeHeight(1.2F).clientTrackingRange(10).build(AetherIIEntityIds.BLADESHROOM_HUNTER));


    // NPCs
    public static final DeferredHolder<EntityType<?>, EntityType<Edward>> EDWARD = ENTITY_TYPES.register("edward",
            () -> EntityType.Builder.of(Edward::new, MobCategory.MISC).sized(0.6F, 1.95F).eyeHeight(1.75F).clientTrackingRange(8).build(AetherIIEntityIds.EDWARD));

    // Projectiles
    public static final DeferredHolder<EntityType<?>, EntityType<HolystoneRock>> HOLYSTONE_ROCK = ENTITY_TYPES.register("holystone_rock",
            () -> EntityType.Builder.<HolystoneRock>of(HolystoneRock::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntityIds.HOLYSTONE_ROCK));
    public static final DeferredHolder<EntityType<?>, EntityType<ArcticSnowball>> ARCTIC_SNOWBALL = ENTITY_TYPES.register("arctic_snowball",
            () -> EntityType.Builder.<ArcticSnowball>of(ArcticSnowball::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntityIds.ARCTIC_SNOWBALL));
    public static final DeferredHolder<EntityType<?>, EntityType<SkyrootPinecone>> SKYROOT_PINECONE = ENTITY_TYPES.register("skyroot_pinecone",
            () -> EntityType.Builder.<SkyrootPinecone>of(SkyrootPinecone::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntityIds.SKYROOT_PINECONE));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownPrismallardEgg>> PRISMALLARD_EGG = ENTITY_TYPES.register("prismallard_egg",
            () -> EntityType.Builder.<ThrownPrismallardEgg>of(ThrownPrismallardEgg::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntityIds.PRISMALLARD_EGG));
    public static final DeferredHolder<EntityType<?>, EntityType<LassoLoop>> LASSO_LOOP = ENTITY_TYPES.register("lasso_loop",
            () -> EntityType.Builder.<LassoLoop>of(LassoLoop::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntityIds.LASSO_LOOP));

    public static final DeferredHolder<EntityType<?>, EntityType<ScatterglassBolt>> SCATTERGLASS_BOLT = ENTITY_TYPES.register("scatterglass_bolt",
            () -> EntityType.Builder.<ScatterglassBolt>of(ScatterglassBolt::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).noLootTable().build(AetherIIEntityIds.SCATTERGLASS_BOLT));
    public static final DeferredHolder<EntityType<?>, EntityType<AmberDart>> AMBER_DART = ENTITY_TYPES.register("amber_dart",
            () -> EntityType.Builder.<AmberDart>of(AmberDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).noLootTable().build(AetherIIEntityIds.AMBER_DART));

    public static final DeferredHolder<EntityType<?>, EntityType<ToxicDart>> TOXIC_DART = ENTITY_TYPES.register("toxic_dart",
            () -> EntityType.Builder.<ToxicDart>of(ToxicDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).noLootTable().build(AetherIIEntityIds.TOXIC_DART));
    public static final DeferredHolder<EntityType<?>, EntityType<VenomousDart>> VENOMOUS_DART = ENTITY_TYPES.register("venomous_dart",
            () -> EntityType.Builder.<VenomousDart>of(VenomousDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).noLootTable().build(AetherIIEntityIds.VENOMOUS_DART));

    public static final DeferredHolder<EntityType<?>, EntityType<ZephyrWebbingBall>> ZEPHYR_WEBBING_BALL = ENTITY_TYPES.register("zephyr_webbing_ball",
            () -> EntityType.Builder.<ZephyrWebbingBall>of(ZephyrWebbingBall::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntityIds.ZEPHYR_WEBBING_BALL));
    public static final DeferredHolder<EntityType<?>, EntityType<SkephidWebbingBall>> SKEPHID_WEBBING_BALL = ENTITY_TYPES.register("skephid_webbing_ball",
            () -> EntityType.Builder.<SkephidWebbingBall>of(SkephidWebbingBall::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntityIds.SKEPHID_WEBBING_BALL));

    public static final DeferredHolder<EntityType<?>, EntityType<TempestThunderball>> TEMPEST_THUNDERBALL = ENTITY_TYPES.register("tempest_thunderball",
            () -> EntityType.Builder.<TempestThunderball>of(TempestThunderball::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).noLootTable().build(AetherIIEntityIds.TEMPEST_THUNDERBALL));

    public static final DeferredHolder<EntityType<?>, EntityType<GravititeDebrisShot>> GRAVITITE_DEBRIS_SHOT = ENTITY_TYPES.register("gravitite_debris_shot",
            () -> EntityType.Builder.<GravititeDebrisShot>of(GravititeDebrisShot::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).noLootTable().build(AetherIIEntityIds.GRAVITITE_DEBRIS_SHOT));

    // Blocks
    public static final DeferredHolder<EntityType<?>, EntityType<SittableEntity>> SITTABLE = ENTITY_TYPES.register("sittable",
            () -> EntityType.Builder.<SittableEntity>of(SittableEntity::new, MobCategory.MISC).sized(0.0F, 0.0F).noLootTable().build(AetherIIEntityIds.SITTABLE));

    public static final DeferredHolder<EntityType<?>, EntityType<HoveringBlockEntity>> HOVERING_BLOCK = ENTITY_TYPES.register("hovering_block",
            () -> EntityType.Builder.<HoveringBlockEntity>of(HoveringBlockEntity::new, MobCategory.MISC).sized(0.9F, 0.9F).clientTrackingRange(10).updateInterval(1).noLootTable().build(AetherIIEntityIds.HOVERING_BLOCK));

    // Vehicles
    public static final DeferredHolder<EntityType<?>, EntityType<CloudSkiff>> CLOUD_SKIFF = ENTITY_TYPES.register("cloud_skiff",
            () -> EntityType.Builder.<CloudSkiff>of(CloudSkiff::new, MobCategory.MISC).noLootTable().sized(1.75F, 0.2125F).eyeHeight(0.5625F).clientTrackingRange(10).build(AetherIIEntityIds.CLOUD_SKIFF));

    // Misc
    public static final DeferredHolder<EntityType<?>, EntityType<ElectricField>> ELECTRIC_FIELD = ENTITY_TYPES.register("electric_field",
            () -> EntityType.Builder.<ElectricField>of(ElectricField::new, MobCategory.MISC).fireImmune().sized(6.0F, 1.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).noLootTable().build(AetherIIEntityIds.ELECTRIC_FIELD));
    public static final DeferredHolder<EntityType<?>, EntityType<DemolitionProjectile>> DEMOLITION_PROJECTILE = ENTITY_TYPES.register("detonation_projectile",
            () -> EntityType.Builder.<DemolitionProjectile>of(DemolitionProjectile::new, MobCategory.MISC).clientTrackingRange(4).updateInterval(10).sized(0.9F, 0.9F).noLootTable().fireImmune().build(AetherIIEntityIds.DETONATION_PROJECTILE));


    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        // Passive
        event.register(AetherIIEntityTypes.FLYING_COW.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SHEEPUFF.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.PHYG.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.AERBUNNY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherTamableAnimal::checkAetherAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.AERWHALE.get(), AetherIISpawnPlacementTypes.NOT_IN_LIQUID, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Aerwhale::checkAerwhaleSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
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
        event.register(AetherIIEntityTypes.PRISMALLARD.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Prismallard::checkPrismallardSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.GLITTERWING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Insect::checkInsectSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SHROUDWING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Insect::checkInsectSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.BIRD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Bird::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);

        // Hostile
        event.register(AetherIIEntityTypes.AECHOR_PLANT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AechorPlant::checkAechorPlantSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.CARRION_SPROUT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CarrionSprout::checkCarrionSproutSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ZEPHYR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zephyr::checkZephyrSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.TEMPEST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tempest::checkTempestSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.COCKATRICE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Cockatrice::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.BLUE_SWET.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.GOLDEN_SWET.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SKEPHID.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Skephid::checkSkephidSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Taluton::checkTalutonSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Taluton::checkTalutonSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.DETONATION_SENTRY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SENTRY_GOLEM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(AetherIIEntityTypes.BLADESHROOM_HUNTER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        // Passive
        event.put(AetherIIEntityTypes.FLYING_COW.get(), AetherIIStats.merge(FlyingCow.createMobAttributes(), AetherIIStats.FLYING_COW).build());
        event.put(AetherIIEntityTypes.SHEEPUFF.get(), AetherIIStats.merge(Sheepuff.createMobAttributes(), AetherIIStats.SHEEPUFF).build());
        event.put(AetherIIEntityTypes.PHYG.get(), AetherIIStats.merge(Phyg.createMobAttributes(), AetherIIStats.PHYG).build());
        event.put(AetherIIEntityTypes.AERBUNNY.get(), AetherIIStats.merge(Aerbunny.createMobAttributes(), AetherIIStats.AERBUNNY).build());
        event.put(AetherIIEntityTypes.AERWHALE.get(), AetherIIStats.merge(Aerwhale.createMobAttributes(), AetherIIStats.AERWHALE).build());
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
        event.put(AetherIIEntityTypes.PRISMALLARD.get(), AetherIIStats.merge(Prismallard.createAttributes(), AetherIIStats.PRISMALLARD).build());
        event.put(AetherIIEntityTypes.SKYROOT_LIZARD.get(), AetherIIStats.merge(SkyrootLizard.createMobAttributes(), AetherIIStats.SKYROOT_LIZARD).build());
        event.put(AetherIIEntityTypes.GLITTERWING.get(), Glitterwing.createMobAttributes().build());
        event.put(AetherIIEntityTypes.SHROUDWING.get(), Shroudwing.createMobAttributes().build());
        event.put(AetherIIEntityTypes.BIRD.get(), Bird.createMobAttributes().build());

        // Hostile
        event.put(AetherIIEntityTypes.AECHOR_PLANT.get(), AetherIIStats.merge(AechorPlant.createMobAttributes(), AetherIIStats.AECHOR_PLANT).build());
        event.put(AetherIIEntityTypes.CARRION_SPROUT.get(), AetherIIStats.merge(CarrionSprout.createMobAttributes(), AetherIIStats.CARRION_SPROUT).build());
        event.put(AetherIIEntityTypes.ZEPHYR.get(), AetherIIStats.merge(Zephyr.createMobAttributes(), AetherIIStats.ZEPHYR).build());
        event.put(AetherIIEntityTypes.TEMPEST.get(), AetherIIStats.merge(Tempest.createMobAttributes(), AetherIIStats.TEMPEST).build());
        event.put(AetherIIEntityTypes.COCKATRICE.get(), AetherIIStats.merge(Cockatrice.createMobAttributes(), AetherIIStats.COCKATRICE).build());
        event.put(AetherIIEntityTypes.BLUE_SWET.get(), AetherIIStats.merge(Swet.createMobAttributes(), AetherIIStats.SWET).build());
        event.put(AetherIIEntityTypes.GOLDEN_SWET.get(), AetherIIStats.merge(Swet.createMobAttributes(), AetherIIStats.SWET).build());
        event.put(AetherIIEntityTypes.SKEPHID.get(), AetherIIStats.merge(Skephid.createMobAttributes(), AetherIIStats.SKEPHID).build());
        event.put(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), AetherIIStats.merge(ArkeniumTaluton.createMobAttributes(), AetherIIStats.ARKENIUM_TALUTON).build());
        event.put(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), AetherIIStats.merge(GravititeTaluton.createMobAttributes(), AetherIIStats.GRAVITITE_TALUTON).build());
        event.put(AetherIIEntityTypes.MIMIC.get(), AetherIIStats.merge(Mimic.createMobAttributes(), AetherIIStats.MIMIC).build());
        event.put(AetherIIEntityTypes.DETONATION_SENTRY.get(), AetherIIStats.merge(DetonationSentry.createMobAttributes(), AetherIIStats.DETONATION_SENTRY).build());
        event.put(AetherIIEntityTypes.SENTRY_GOLEM.get(), AetherIIStats.merge(SentryGolem.createMobAttributes(), AetherIIStats.SENTRY_GOLEM).build());
        event.put(AetherIIEntityTypes.SLIDER.get(), Slider.createMobAttributes().build());
        event.put(AetherIIEntityTypes.BLADESHROOM_HUNTER.get(), AetherIIStats.merge(BladeshroomHunter.createMobAttributes(), AetherIIStats.BLADESHROOM_HUNTER).build());

        // NPCs
        event.put(AetherIIEntityTypes.EDWARD.get(), Edward.createMobAttributes().build());
    }
}