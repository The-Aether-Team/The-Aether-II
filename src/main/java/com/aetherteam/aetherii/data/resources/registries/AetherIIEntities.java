package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

//TODO: figure out proper naming for the class or we should merge it with AetherIIEntityTypes
public class AetherIIEntities {

    //Passive
    public static final ResourceKey<EntityType<?>> PHYG = createKey("phyg");
    public static final ResourceKey<EntityType<?>> FLYING_COW = createKey("flying_cow");
    public static final ResourceKey<EntityType<?>> SHEEPUFF = createKey("sheepuff");

    public static final ResourceKey<EntityType<?>> AERBUNNY = createKey("aerbunny");

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

    public static final ResourceKey<EntityType<?>> SKYROOT_LIZARD = createKey("skyroot_lizard");


    //Hostile
    public static final ResourceKey<EntityType<?>> AECHOR_PLANT = createKey("aechor_plant");
    public static final ResourceKey<EntityType<?>> ZEPHYR = createKey("zephyr");
    public static final ResourceKey<EntityType<?>> TEMPEST = createKey("tempest");
    public static final ResourceKey<EntityType<?>> COCKATRICE = createKey("cockatrice");
    public static final ResourceKey<EntityType<?>> SWET = createKey("swet");
    public static final ResourceKey<EntityType<?>> SKEPHID = createKey("skephid");


    //Projectiles
    public static final ResourceKey<EntityType<?>> HOLYSTONE_ROCK = createKey("holystone_rock");
    public static final ResourceKey<EntityType<?>> ARCTIC_SNOWBALL = createKey("arctic_snowball");
    public static final ResourceKey<EntityType<?>> SKYROOT_PINECONE = createKey("skyroot_pinecone");
    public static final ResourceKey<EntityType<?>> SCATTERGLASS_BOLT = createKey("scatterglass_bolt");

    public static final ResourceKey<EntityType<?>> TOXIC_DART = createKey("toxic_dart");
    public static final ResourceKey<EntityType<?>> VENOMOUS_DART = createKey("venomous_dart");

    public static final ResourceKey<EntityType<?>> ZEPHYR_WEBBING_BALL = createKey("zephyr_webbing_ball");
    public static final ResourceKey<EntityType<?>> SKEPHID_WEBBING_BALL = createKey("skephid_webbing_ball");

    public static final ResourceKey<EntityType<?>> TEMPEST_THUNDERBALL = createKey("skephid_thunderball");


    //Blocks
    public static final ResourceKey<EntityType<?>> HOVERING_BLOCK = createKey("hovering_block");


    //Misc
    public static final ResourceKey<EntityType<?>> ELECTRIC_FIELD = createKey("electric_field");

    private static ResourceKey<EntityType<?>> createKey(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }
}