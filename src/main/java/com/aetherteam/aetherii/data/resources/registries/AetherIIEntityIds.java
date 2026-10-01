package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class AetherIIEntityIds {
    //Passive
    public static final ResourceKey<EntityType<?>> PHYG = createKey("phyg");
    public static final ResourceKey<EntityType<?>> FLYING_COW = createKey("flying_cow");
    public static final ResourceKey<EntityType<?>> SHEEPUFF = createKey("sheepuff");

    public static final ResourceKey<EntityType<?>> AERBUNNY = createKey("aerbunny");
    public static final ResourceKey<EntityType<?>> AERWHALE = createKey("aerwhale");

    public static final ResourceKey<EntityType<?>> HIGHFIELDS_TAEGORE = createKey("highfields_taegore");
    public static final ResourceKey<EntityType<?>> MAGNETIC_TAEGORE = createKey("magnetic_taegore");
    public static final ResourceKey<EntityType<?>> ARCTIC_TAEGORE = createKey("arctic_taegore");

    public static final ResourceKey<EntityType<?>> HIGHFIELDS_BURRUKAI = createKey("highfields_burrukai");
    public static final ResourceKey<EntityType<?>> MAGNETIC_BURRUKAI = createKey("magnetic_burrukai");
    public static final ResourceKey<EntityType<?>> ARCTIC_BURRUKAI = createKey("arctic_burrukai");

    public static final ResourceKey<EntityType<?>> HIGHFIELDS_KIRRID = createKey("highfields_kirrid");
    public static final ResourceKey<EntityType<?>> MAGNETIC_KIRRID = createKey("magnetic_kirrid");
    public static final ResourceKey<EntityType<?>> ARCTIC_KIRRID = createKey("arctic_kirrid");

    public static final ResourceKey<EntityType<?>> MOA = createKey("moa");
    public static final ResourceKey<EntityType<?>> PRISMALLARD = createKey("prismallard");

    public static final ResourceKey<EntityType<?>> SKYROOT_LIZARD = createKey("skyroot_lizard");

    public static final ResourceKey<EntityType<?>> GLITTERWING = createKey("glitterwing");
    public static final ResourceKey<EntityType<?>> SHROUDWING = createKey("shroudwing");

    //Hostile
    public static final ResourceKey<EntityType<?>> AECHOR_PLANT = createKey("aechor_plant");
    public static final ResourceKey<EntityType<?>> CARRION_SPROUT = createKey("carrion_sprout");
    public static final ResourceKey<EntityType<?>> ZEPHYR = createKey("zephyr");
    public static final ResourceKey<EntityType<?>> TEMPEST = createKey("tempest");
    public static final ResourceKey<EntityType<?>> COCKATRICE = createKey("cockatrice");
    public static final ResourceKey<EntityType<?>> BLUE_SWET = createKey("blue_swet");
    public static final ResourceKey<EntityType<?>> GOLDEN_SWET = createKey("golden_swet");
    public static final ResourceKey<EntityType<?>> SKEPHID = createKey("skephid");
    public static final ResourceKey<EntityType<?>> ARKENIUM_TALUTON = createKey("arkenium_taluton");
    public static final ResourceKey<EntityType<?>> GRAVITITE_TALUTON = createKey("gravitite_taluton");
    public static final ResourceKey<EntityType<?>> MIMIC = createKey("mimic");
    public static final ResourceKey<EntityType<?>> DETONATION_SENTRY = createKey("detonation_sentry");
    public static final ResourceKey<EntityType<?>> SENTRY_GOLEM = createKey("sentry_golem");
    public static final ResourceKey<EntityType<?>> SLIDER = createKey("slider");
    public static final ResourceKey<EntityType<?>> BLADESHROOM_HUNTER = createKey("bladeshroom_hunter");


    //NPCs
    public static final ResourceKey<EntityType<?>> EDWARD = createKey("edward");


    //Projectiles
    public static final ResourceKey<EntityType<?>> HOLYSTONE_ROCK = createKey("holystone_rock");
    public static final ResourceKey<EntityType<?>> ARCTIC_SNOWBALL = createKey("arctic_snowball");
    public static final ResourceKey<EntityType<?>> SKYROOT_PINECONE = createKey("skyroot_pinecone");
    public static final ResourceKey<EntityType<?>> PRISMALLARD_EGG = createKey("prismallard_egg");
    public static final ResourceKey<EntityType<?>> LASSO_LOOP = createKey("lasso_loop");

    public static final ResourceKey<EntityType<?>> SCATTERGLASS_BOLT = createKey("scatterglass_bolt");
    public static final ResourceKey<EntityType<?>> BRETTL_ROPE_BOLT = createKey("brettl_rope_bolt");
    public static final ResourceKey<EntityType<?>> AMBER_DART = createKey("amber_dart");

    public static final ResourceKey<EntityType<?>> TOXIC_DART = createKey("toxic_dart");
    public static final ResourceKey<EntityType<?>> VENOMOUS_DART = createKey("venomous_dart");

    public static final ResourceKey<EntityType<?>> ZEPHYR_WEBBING_BALL = createKey("zephyr_webbing_ball");
    public static final ResourceKey<EntityType<?>> SKEPHID_WEBBING_BALL = createKey("skephid_webbing_ball");

    public static final ResourceKey<EntityType<?>> TEMPEST_THUNDERBALL = createKey("skephid_thunderball");

    public static final ResourceKey<EntityType<?>> GRAVITITE_DEBRIS_SHOT = createKey("gravitite_debris_shot");

    public static final ResourceKey<EntityType<?>> DETONATION_PROJECTILE = createKey("detonation_projectile");

    //Blocks
    public static final ResourceKey<EntityType<?>> SITTABLE = createKey("sittable");
    public static final ResourceKey<EntityType<?>> HOVERING_BLOCK = createKey("hovering_block");

    //Vehicles
    public static final ResourceKey<EntityType<?>> CLOUD_SKIFF = createKey("cloud_skiff");

    //Misc
    public static final ResourceKey<EntityType<?>> ELECTRIC_FIELD = createKey("electric_field");

    private static ResourceKey<EntityType<?>> createKey(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }
}