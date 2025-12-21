package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class AetherIIModelLayers {
    public static final ModelLayerLocation SKYROOT_BED_FOOT = register("skyroot_bed_foot");
    public static final ModelLayerLocation SKYROOT_BED_HEAD = register("skyroot_bed_head");
    public static final ModelLayerLocation MOA_EGG = register("moa_egg");
    public static final ModelLayerLocation ALKAHEST_PURIFIER = register("alkahest_purifier");
    public static final ModelLayerLocation SENTRY_CRATE = register("sentry_crate");
    public static final ModelLayerLocation SENTRY_SPAWNER = register("sentry_spawner");
    public static final ModelLayerLocation SENTRY_SPAWNER_PISTON = register("sentry_spawner_piston");
    public static final ModelLayerLocation DOUBLE_SENTRY_CRATE_LEFT = register("double_sentry_crate_left");
    public static final ModelLayerLocation DOUBLE_SENTRY_CRATE_RIGHT = register("double_sentry_crate_right");

    public static final ModelLayerLocation PHYG = register("phyg");
    public static final ModelLayerLocation AERBUNNY = register("aerbunny");
    public static final ModelLayerLocation AERBUNNY_COLLAR = register("aerbunny", "collar");
    public static final ModelLayerLocation FLYING_COW = register("flying_cow");
    public static final ModelLayerLocation SHEEPUFF = register("sheepuff");
    public static final ModelLayerLocation HIGHFIELDS_TAEGORE = register("highfields_taegore");
    public static final ModelLayerLocation HIGHFIELDS_TAEGORE_BABY = register("highfields_taegore_baby");
    public static final ModelLayerLocation MAGNETIC_TAEGORE = register("magnetic_taegore");
    public static final ModelLayerLocation MAGNETIC_TAEGORE_BABY = register("magnetic_taegore_baby");
    public static final ModelLayerLocation ARCTIC_TAEGORE = register("arctic_taegore");
    public static final ModelLayerLocation ARCTIC_TAEGORE_BABY = register("arctic_taegore_baby");
    public static final ModelLayerLocation HIGHFIELDS_BURRUKAI = register("highfields_burrukai");
    public static final ModelLayerLocation HIGHFIELDS_BURRUKAI_BABY = register("highfields_burrukai_baby");
    public static final ModelLayerLocation MAGNETIC_BURRUKAI = register("magnetic_burrukai");
    public static final ModelLayerLocation MAGNETIC_BURRUKAI_BABY = register("magnetic_burrukai_baby");
    public static final ModelLayerLocation ARCTIC_BURRUKAI = register("arctic_burrukai");
    public static final ModelLayerLocation ARCTIC_BURRUKAI_BABY = register("arctic_burrukai_baby");
    public static final ModelLayerLocation HIGHFIELDS_KIRRID = register("highfields_kirrid");
    public static final ModelLayerLocation HIGHFIELDS_KIRRID_BABY = register("highfields_kirrid_baby");
    public static final ModelLayerLocation MAGNETIC_KIRRID = register("magnetic_kirrid");
    public static final ModelLayerLocation MAGNETIC_KIRRID_BABY = register("magnetic_kirrid_baby");
    public static final ModelLayerLocation ARCTIC_KIRRID = register("arctic_kirrid");
    public static final ModelLayerLocation ARCTIC_KIRRID_BABY = register("arctic_kirrid_baby");
    public static final ModelLayerLocation MOA = register("moa");
    public static final ModelLayerLocation MOA_BABY = register("moa_baby");
    public static final ModelLayerLocation MOA_SADDLE = register("moa", "saddle");
    public static final ModelLayerLocation MOA_SADDLEBAG = register("moa", "saddlebag");
    public static final ModelLayerLocation MOA_LARGE_SADDLEBAG = register("moa", "large_saddlebag");
    public static final ModelLayerLocation SKYROOT_LIZARD = register("skyroot_lizard");

    public static final ModelLayerLocation AECHOR_PLANT = register("aechor_plant");
    public static final ModelLayerLocation ZEPHYR = register("zephyr");
    public static final ModelLayerLocation AERWHALE = register("aerwhale");
    public static final ModelLayerLocation TEMPEST = register("tempest");
    public static final ModelLayerLocation COCKATRICE = register("cockatrice");
    public static final ModelLayerLocation BLUE_SWET = register("blue_swet");
    public static final ModelLayerLocation GOLDEN_SWET = register("golden_swet");
    public static final ModelLayerLocation SKEPHID = register("skephid");
    public static final ModelLayerLocation CARRION_SPROUT = register("carrion_sprout");
    public static final ModelLayerLocation ARKENIUM_TALUTON = register("arkenium_taluton");
    public static final ModelLayerLocation GRAVITITE_TALUTON = register("gravitite_taluton");
    public static final ModelLayerLocation BLADESHROOM_HUNTER = register("bladeshroom_hunter");

    public static final ModelLayerLocation MIMIC = register("mimic");
    public static final ModelLayerLocation DETONATION_SENTRY = register("detonation_sentry");
    public static final ModelLayerLocation DEMOLITION_PROJECTILE = register("detonation_projectile");
    public static final ModelLayerLocation SENTRY_GOLEM = register("sentry_golem");

    public static final ModelLayerLocation SLIDER = register("slider");

    public static final ModelLayerLocation EDWARD = register("edward");

    public static final ModelLayerLocation GRAVITITE_DEBRIS_SHOT = register("gravitite_debris_shot");

    public static final ModelLayerLocation CLOUD_SKIFF = register("cloud_skiff");

    public static final ModelLayerLocation GLOVES = register("gloves");
    public static final ModelLayerLocation GLOVES_SLIM = register("gloves_slim");
    public static final ModelLayerLocation GLOVES_FIRST_PERSON = register("gloves_first_person");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String type) {
        return register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name), type);
    }

    private static ModelLayerLocation register(ResourceLocation location, String type) {
        return new ModelLayerLocation(location, type);
    }
}