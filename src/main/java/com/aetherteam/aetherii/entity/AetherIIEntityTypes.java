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
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, AetherII.MODID);

    // Passive
    public static final RegistryObject<EntityType<Phyg>> PHYG = ENTITY_TYPES.register("phyg",
            () -> EntityType.Builder.of(Phyg::new, MobCategory.CREATURE).sized(0.95F, 0.85F).clientTrackingRange(10).build(id(AetherIIEntityIds.PHYG)));
    public static final RegistryObject<EntityType<FlyingCow>> FLYING_COW = ENTITY_TYPES.register("flying_cow",
            () -> EntityType.Builder.of(FlyingCow::new, MobCategory.CREATURE).sized(0.95F, 1.1F).clientTrackingRange(10).build(id(AetherIIEntityIds.FLYING_COW)));
    public static final RegistryObject<EntityType<Sheepuff>> SHEEPUFF = ENTITY_TYPES.register("sheepuff",
            () -> EntityType.Builder.of(Sheepuff::new, MobCategory.CREATURE).sized(0.9F, 1.25F).clientTrackingRange(10).build(id(AetherIIEntityIds.SHEEPUFF)));

    public static final RegistryObject<EntityType<Aerbunny>> AERBUNNY = ENTITY_TYPES.register("aerbunny",
            () -> EntityType.Builder.of(Aerbunny::new, MobCategory.CREATURE).sized(0.55F, 0.45F).clientTrackingRange(10).build(id(AetherIIEntityIds.AERBUNNY)));

    public static final RegistryObject<EntityType<Aerwhale>> AERWHALE = ENTITY_TYPES.register("aerwhale",
            () -> EntityType.Builder.of(Aerwhale::new, AetherIIMobCategory.AETHER_AERWHALE).fireImmune().sized(3.0F, 3.0F).clientTrackingRange(10).build(id(AetherIIEntityIds.AERWHALE)));

    public static final RegistryObject<EntityType<Taegore>> HIGHFIELDS_TAEGORE = ENTITY_TYPES.register("highfields_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.3F, 1.7F).clientTrackingRange(10).build(id(AetherIIEntityIds.HIGHFIELDS_TAEGORE)));
    public static final RegistryObject<EntityType<Taegore>> MAGNETIC_TAEGORE = ENTITY_TYPES.register("magnetic_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.3F, 1.7F).clientTrackingRange(10).build(id(AetherIIEntityIds.MAGNETIC_TAEGORE)));
    public static final RegistryObject<EntityType<Taegore>> ARCTIC_TAEGORE = ENTITY_TYPES.register("arctic_taegore",
            () -> EntityType.Builder.of(Taegore::new, MobCategory.CREATURE).sized(1.3F, 1.7F).clientTrackingRange(10).build(id(AetherIIEntityIds.ARCTIC_TAEGORE)));

    public static final RegistryObject<EntityType<Burrukai>> HIGHFIELDS_BURRUKAI = ENTITY_TYPES.register("highfields_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).clientTrackingRange(10).build(id(AetherIIEntityIds.HIGHFIELDS_BURRUKAI)));
    public static final RegistryObject<EntityType<Burrukai>> MAGNETIC_BURRUKAI = ENTITY_TYPES.register("magnetic_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).clientTrackingRange(10).build(id(AetherIIEntityIds.MAGNETIC_BURRUKAI)));
    public static final RegistryObject<EntityType<Burrukai>> ARCTIC_BURRUKAI = ENTITY_TYPES.register("arctic_burrukai",
            () -> EntityType.Builder.of(Burrukai::new, MobCategory.CREATURE).sized(1.5F, 1.95F).clientTrackingRange(10).build(id(AetherIIEntityIds.ARCTIC_BURRUKAI)));

    public static final RegistryObject<EntityType<Kirrid>> HIGHFIELDS_KIRRID = ENTITY_TYPES.register("highfields_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.95F, 1.35F).clientTrackingRange(10).build(id(AetherIIEntityIds.HIGHFIELDS_KIRRID)));
    public static final RegistryObject<EntityType<Kirrid>> MAGNETIC_KIRRID = ENTITY_TYPES.register("magnetic_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.95F, 1.35F).clientTrackingRange(10).build(id(AetherIIEntityIds.MAGNETIC_KIRRID)));
    public static final RegistryObject<EntityType<Kirrid>> ARCTIC_KIRRID = ENTITY_TYPES.register("arctic_kirrid",
            () -> EntityType.Builder.of(Kirrid::new, MobCategory.CREATURE).sized(0.95F, 1.35F).clientTrackingRange(10).build(id(AetherIIEntityIds.ARCTIC_KIRRID)));

    public static final RegistryObject<EntityType<Moa>> MOA = ENTITY_TYPES.register("moa",
            () -> EntityType.Builder.of(Moa::new, AetherIIMobCategory.AETHER_MOA).sized(1.25F, 2.35F).clientTrackingRange(10).build(id(AetherIIEntityIds.MOA)));

    public static final RegistryObject<EntityType<Prismallard>> PRISMALLARD = ENTITY_TYPES.register("prismallard",
            () -> EntityType.Builder.of(Prismallard::new, AetherIIMobCategory.AETHER_WATER_SURFACE_CREATURE).sized(0.5F, 0.6F).clientTrackingRange(10).build(id(AetherIIEntityIds.PRISMALLARD)));


    public static final RegistryObject<EntityType<SkyrootLizard>> SKYROOT_LIZARD = ENTITY_TYPES.register("skyroot_lizard",
            () -> EntityType.Builder.of(SkyrootLizard::new, AetherIIMobCategory.AETHER_AMBIENT).sized(0.95F, 0.35F).clientTrackingRange(10).build(id(AetherIIEntityIds.SKYROOT_LIZARD)));

    public static final RegistryObject<EntityType<Glitterwing>> GLITTERWING = ENTITY_TYPES.register("glitterwing",
            () -> EntityType.Builder.<Glitterwing>of(Glitterwing::new, AetherIIMobCategory.AETHER_AMBIENT).sized(0.75F, 0.75F).clientTrackingRange(5).build(id(AetherIIEntityIds.GLITTERWING)));
    public static final RegistryObject<EntityType<Shroudwing>> SHROUDWING = ENTITY_TYPES.register("shroudwing",
            () -> EntityType.Builder.<Shroudwing>of(Shroudwing::new, AetherIIMobCategory.AETHER_AMBIENT).sized(0.5F, 0.5F).clientTrackingRange(5).build(id(AetherIIEntityIds.SHROUDWING)));

    // Hostile
    public static final RegistryObject<EntityType<AechorPlant>> AECHOR_PLANT = ENTITY_TYPES.register("aechor_plant",
            () -> EntityType.Builder.of(AechorPlant::new, AetherIIMobCategory.AETHER_PLANT_HAZARD).sized(0.9F, 0.6F).clientTrackingRange(8).build(id(AetherIIEntityIds.AECHOR_PLANT)));
    public static final RegistryObject<EntityType<CarrionSprout>> CARRION_SPROUT = ENTITY_TYPES.register("carrion_sprout",
            () -> EntityType.Builder.of(CarrionSprout::new, AetherIIMobCategory.AETHER_PLANT_HAZARD).sized(1.0F, 1.0F).clientTrackingRange(8).build(id(AetherIIEntityIds.CARRION_SPROUT)));

    public static final RegistryObject<EntityType<Zephyr>> ZEPHYR = ENTITY_TYPES.register("zephyr",
            () -> EntityType.Builder.of(Zephyr::new, AetherIIMobCategory.AETHER_SKY_HAZARD).sized(2.0F, 1.75F).clientTrackingRange(10).build(id(AetherIIEntityIds.ZEPHYR)));

    public static final RegistryObject<EntityType<Swet>> BLUE_SWET = ENTITY_TYPES.register("blue_swet",
            () -> EntityType.Builder.of(Swet::new, AetherIIMobCategory.AETHER_DARKNESS_HAZARD).sized(0.95F, 0.95F).clientTrackingRange(10).build(id(AetherIIEntityIds.BLUE_SWET)));
    public static final RegistryObject<EntityType<Swet>> GOLDEN_SWET = ENTITY_TYPES.register("golden_swet",
            () -> EntityType.Builder.of(Swet::new, AetherIIMobCategory.AETHER_DARKNESS_HAZARD).sized(0.95F, 0.95F).clientTrackingRange(10).build(id(AetherIIEntityIds.GOLDEN_SWET)));
    public static final RegistryObject<EntityType<Skephid>> SKEPHID = ENTITY_TYPES.register("skephid",
            () -> EntityType.Builder.of(Skephid::new, AetherIIMobCategory.AETHER_DARKNESS_HAZARD).sized(0.8F, 0.8F).clientTrackingRange(10).build(id(AetherIIEntityIds.SKEPHID)));

    public static final RegistryObject<EntityType<Tempest>> TEMPEST = ENTITY_TYPES.register("tempest",
            () -> EntityType.Builder.of(Tempest::new, AetherIIMobCategory.AETHER_BLIGHT_MONSTER).sized(1.5F, 1.4F).clientTrackingRange(10).build(id(AetherIIEntityIds.TEMPEST)));
    public static final RegistryObject<EntityType<Cockatrice>> COCKATRICE = ENTITY_TYPES.register("cockatrice",
            () -> EntityType.Builder.of(Cockatrice::new, AetherIIMobCategory.AETHER_BLIGHT_MONSTER).sized(0.9F, 2.15F).clientTrackingRange(10).build(id(AetherIIEntityIds.COCKATRICE)));

    public static final RegistryObject<EntityType<ArkeniumTaluton>> ARKENIUM_TALUTON = ENTITY_TYPES.register("arkenium_taluton",
            () -> EntityType.Builder.of(ArkeniumTaluton::new, AetherIIMobCategory.AETHER_DARKNESS_MONSTER).sized(1.0F, 1.65F).clientTrackingRange(10).build(id(AetherIIEntityIds.ARKENIUM_TALUTON)));
    public static final RegistryObject<EntityType<GravititeTaluton>> GRAVITITE_TALUTON = ENTITY_TYPES.register("gravitite_taluton",
            () -> EntityType.Builder.of(GravititeTaluton::new, AetherIIMobCategory.AETHER_DARKNESS_MONSTER).sized(0.75F, 1.9F).clientTrackingRange(10).build(id(AetherIIEntityIds.GRAVITITE_TALUTON)));

    public static final RegistryObject<EntityType<Mimic>> MIMIC = ENTITY_TYPES.register("mimic",
            () -> EntityType.Builder.of(Mimic::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(0.9F, 1.35F).clientTrackingRange(8).build(id(AetherIIEntityIds.MIMIC)));

    public static final RegistryObject<EntityType<DetonationSentry>> DETONATION_SENTRY = ENTITY_TYPES.register("detonation_sentry",
            () -> EntityType.Builder.of(DetonationSentry::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(0.9F, 0.9F).clientTrackingRange(10).build(id(AetherIIEntityIds.DETONATION_SENTRY)));
    public static final RegistryObject<EntityType<SentryGolem>> SENTRY_GOLEM = ENTITY_TYPES.register("sentry_golem",
            () -> EntityType.Builder.of(SentryGolem::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build(id(AetherIIEntityIds.SENTRY_GOLEM)));

    public static final RegistryObject<EntityType<Slider>> SLIDER = ENTITY_TYPES.register("slider",
            () -> EntityType.Builder.of(Slider::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(2.0F, 2.0F).fireImmune().clientTrackingRange(10).build(id(AetherIIEntityIds.SLIDER)));

    public static final RegistryObject<EntityType<BladeshroomHunter>> BLADESHROOM_HUNTER = ENTITY_TYPES.register("bladeshroom_hunter",
            () -> EntityType.Builder.of(BladeshroomHunter::new, AetherIIMobCategory.AETHER_DUNGEON_MONSTER).sized(0.9F, 1.3F).clientTrackingRange(10).build(id(AetherIIEntityIds.BLADESHROOM_HUNTER)));


    // NPCs
    public static final RegistryObject<EntityType<Edward>> EDWARD = ENTITY_TYPES.register("edward",
            () -> EntityType.Builder.of(Edward::new, MobCategory.MISC).sized(0.6F, 1.95F).clientTrackingRange(8).build(id(AetherIIEntityIds.EDWARD)));

    // Projectiles
    public static final RegistryObject<EntityType<HolystoneRock>> HOLYSTONE_ROCK = ENTITY_TYPES.register("holystone_rock",
            () -> EntityType.Builder.<HolystoneRock>of(HolystoneRock::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(id(AetherIIEntityIds.HOLYSTONE_ROCK)));
    public static final RegistryObject<EntityType<ArcticSnowball>> ARCTIC_SNOWBALL = ENTITY_TYPES.register("arctic_snowball",
            () -> EntityType.Builder.<ArcticSnowball>of(ArcticSnowball::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(id(AetherIIEntityIds.ARCTIC_SNOWBALL)));
    public static final RegistryObject<EntityType<SkyrootPinecone>> SKYROOT_PINECONE = ENTITY_TYPES.register("skyroot_pinecone",
            () -> EntityType.Builder.<SkyrootPinecone>of(SkyrootPinecone::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(id(AetherIIEntityIds.SKYROOT_PINECONE)));
    public static final RegistryObject<EntityType<ThrownPrismallardEgg>> PRISMALLARD_EGG = ENTITY_TYPES.register("prismallard_egg",
            () -> EntityType.Builder.<ThrownPrismallardEgg>of(ThrownPrismallardEgg::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(id(AetherIIEntityIds.PRISMALLARD_EGG)));
    public static final RegistryObject<EntityType<LassoLoop>> LASSO_LOOP = ENTITY_TYPES.register("lasso_loop",
            () -> EntityType.Builder.<LassoLoop>of(LassoLoop::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(id(AetherIIEntityIds.LASSO_LOOP)));

    public static final RegistryObject<EntityType<ScatterglassBolt>> SCATTERGLASS_BOLT = ENTITY_TYPES.register("scatterglass_bolt",
            () -> EntityType.Builder.<ScatterglassBolt>of(ScatterglassBolt::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(id(AetherIIEntityIds.SCATTERGLASS_BOLT)));
    public static final RegistryObject<EntityType<AmberDart>> AMBER_DART = ENTITY_TYPES.register("amber_dart",
            () -> EntityType.Builder.<AmberDart>of(AmberDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(id(AetherIIEntityIds.AMBER_DART)));

    public static final RegistryObject<EntityType<ToxicDart>> TOXIC_DART = ENTITY_TYPES.register("toxic_dart",
            () -> EntityType.Builder.<ToxicDart>of(ToxicDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).build(id(AetherIIEntityIds.TOXIC_DART)));
    public static final RegistryObject<EntityType<VenomousDart>> VENOMOUS_DART = ENTITY_TYPES.register("venomous_dart",
            () -> EntityType.Builder.<VenomousDart>of(VenomousDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).build(id(AetherIIEntityIds.VENOMOUS_DART)));

    public static final RegistryObject<EntityType<ZephyrWebbingBall>> ZEPHYR_WEBBING_BALL = ENTITY_TYPES.register("zephyr_webbing_ball",
            () -> EntityType.Builder.<ZephyrWebbingBall>of(ZephyrWebbingBall::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(id(AetherIIEntityIds.ZEPHYR_WEBBING_BALL)));
    public static final RegistryObject<EntityType<SkephidWebbingBall>> SKEPHID_WEBBING_BALL = ENTITY_TYPES.register("skephid_webbing_ball",
            () -> EntityType.Builder.<SkephidWebbingBall>of(SkephidWebbingBall::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(id(AetherIIEntityIds.SKEPHID_WEBBING_BALL)));

    public static final RegistryObject<EntityType<TempestThunderball>> TEMPEST_THUNDERBALL = ENTITY_TYPES.register("tempest_thunderball",
            () -> EntityType.Builder.<TempestThunderball>of(TempestThunderball::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(id(AetherIIEntityIds.TEMPEST_THUNDERBALL)));

    public static final RegistryObject<EntityType<GravititeDebrisShot>> GRAVITITE_DEBRIS_SHOT = ENTITY_TYPES.register("gravitite_debris_shot",
            () -> EntityType.Builder.<GravititeDebrisShot>of(GravititeDebrisShot::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(id(AetherIIEntityIds.GRAVITITE_DEBRIS_SHOT)));

    // Blocks
    public static final RegistryObject<EntityType<SittableEntity>> SITTABLE = ENTITY_TYPES.register("sittable",
            () -> EntityType.Builder.<SittableEntity>of(SittableEntity::new, MobCategory.MISC).sized(0.0F, 0.0F).build(id(AetherIIEntityIds.SITTABLE)));

    public static final RegistryObject<EntityType<HoveringBlockEntity>> HOVERING_BLOCK = ENTITY_TYPES.register("hovering_block",
            () -> EntityType.Builder.<HoveringBlockEntity>of(HoveringBlockEntity::new, MobCategory.MISC).sized(0.9F, 0.9F).clientTrackingRange(10).updateInterval(1).build(id(AetherIIEntityIds.HOVERING_BLOCK)));

    // Vehicles
    public static final RegistryObject<EntityType<CloudSkiff>> CLOUD_SKIFF = ENTITY_TYPES.register("cloud_skiff",
            () -> EntityType.Builder.<CloudSkiff>of(CloudSkiff::new, MobCategory.MISC).sized(1.75F, 0.2125F).clientTrackingRange(10).build(id(AetherIIEntityIds.CLOUD_SKIFF)));

    // Misc
    public static final RegistryObject<EntityType<ElectricField>> ELECTRIC_FIELD = ENTITY_TYPES.register("electric_field",
            () -> EntityType.Builder.<ElectricField>of(ElectricField::new, MobCategory.MISC).fireImmune().sized(6.0F, 1.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build(id(AetherIIEntityIds.ELECTRIC_FIELD)));
    public static final RegistryObject<EntityType<DemolitionProjectile>> DEMOLITION_PROJECTILE = ENTITY_TYPES.register("detonation_projectile",
            () -> EntityType.Builder.<DemolitionProjectile>of(DemolitionProjectile::new, MobCategory.MISC).clientTrackingRange(4).updateInterval(10).sized(0.9F, 0.9F).fireImmune().build(id(AetherIIEntityIds.DETONATION_PROJECTILE)));

    private static String id(ResourceKey<EntityType<?>> key) {
        return key.location().toString();
    }


    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        // Passive
        event.register(AetherIIEntityTypes.FLYING_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SHEEPUFF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.PHYG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.AERBUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherTamableAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.AERWHALE.get(), AetherIISpawnPlacementTypes.NOT_IN_LIQUID, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Aerwhale::checkAerwhaleSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ARCTIC_KIRRID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.MOA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.PRISMALLARD.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Prismallard::checkPrismallardSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.GLITTERWING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Insect::checkInsectSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SHROUDWING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Insect::checkInsectSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

        // Hostile
        event.register(AetherIIEntityTypes.AECHOR_PLANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AechorPlant::checkAechorPlantSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.CARRION_SPROUT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CarrionSprout::checkCarrionSproutSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ZEPHYR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zephyr::checkZephyrSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.TEMPEST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tempest::checkTempestSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.COCKATRICE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Cockatrice::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.BLUE_SWET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.GOLDEN_SWET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SKEPHID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Skephid::checkSkephidSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Taluton::checkTalutonSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Taluton::checkTalutonSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.DETONATION_SENTRY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.SENTRY_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(AetherIIEntityTypes.BLADESHROOM_HUNTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
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
