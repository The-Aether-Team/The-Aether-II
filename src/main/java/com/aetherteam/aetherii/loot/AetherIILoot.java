package com.aetherteam.aetherii.loot;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AetherIILoot {
    private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceLocation> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

    // Entities
    public static final ResourceLocation HIGHFIELDS_KIRRID_FUR = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entities/kirrid/highfields_wool");
    public static final ResourceLocation MAGNETIC_KIRRID_FUR = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entities/kirrid/magnetic_wool");
    public static final ResourceLocation ARCTIC_KIRRID_FUR = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entities/kirrid/arctic_wool");

    public static final ResourceLocation ENTITIES_SHEEPUFF_WHITE = register("entities/sheepuff/white");
    public static final ResourceLocation ENTITIES_SHEEPUFF_ORANGE = register("entities/sheepuff/orange");
    public static final ResourceLocation ENTITIES_SHEEPUFF_MAGENTA = register("entities/sheepuff/magenta");
    public static final ResourceLocation ENTITIES_SHEEPUFF_LIGHT_BLUE = register("entities/sheepuff/light_blue");
    public static final ResourceLocation ENTITIES_SHEEPUFF_YELLOW = register("entities/sheepuff/yellow");
    public static final ResourceLocation ENTITIES_SHEEPUFF_LIME = register("entities/sheepuff/lime");
    public static final ResourceLocation ENTITIES_SHEEPUFF_PINK = register("entities/sheepuff/pink");
    public static final ResourceLocation ENTITIES_SHEEPUFF_GRAY = register("entities/sheepuff/gray");
    public static final ResourceLocation ENTITIES_SHEEPUFF_LIGHT_GRAY = register("entities/sheepuff/light_gray");
    public static final ResourceLocation ENTITIES_SHEEPUFF_CYAN = register("entities/sheepuff/cyan");
    public static final ResourceLocation ENTITIES_SHEEPUFF_PURPLE = register("entities/sheepuff/purple");
    public static final ResourceLocation ENTITIES_SHEEPUFF_BLUE = register("entities/sheepuff/blue");
    public static final ResourceLocation ENTITIES_SHEEPUFF_BROWN = register("entities/sheepuff/brown");
    public static final ResourceLocation ENTITIES_SHEEPUFF_GREEN = register("entities/sheepuff/green");
    public static final ResourceLocation ENTITIES_SHEEPUFF_RED = register("entities/sheepuff/red");
    public static final ResourceLocation ENTITIES_SHEEPUFF_BLACK = register("entities/sheepuff/black");


    // Stripping
    public static final ResourceLocation STRIP_MOSSY_WISPROOT = register("stripping/strip_mossy_wisproot");
    public static final ResourceLocation STRIP_AMBEROOT = register("stripping/strip_amberoot");

    private static ResourceLocation register(String id) {
        return register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, id));
    }

    private static ResourceLocation register(ResourceLocation id) {
        if (LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }
}
