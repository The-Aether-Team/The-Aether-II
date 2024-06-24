package com.aetherteam.aetherii.loot;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AetherIILoot {
    private static final Set<ResourceKey<LootTable>> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceKey<LootTable>> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

    // Entities
    public static final ResourceKey<LootTable> HIGHFIELDS_KIRRID_FUR = register("entities/kirrid/highfields_wool");
    public static final ResourceKey<LootTable> MAGNETIC_KIRRID_FUR = register("entities/kirrid/magnetic_wool");
    public static final ResourceKey<LootTable> ARCTIC_KIRRID_FUR = register("entities/kirrid/arctic_wool");

    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_WHITE = register("entities/sheepuff/white");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_ORANGE = register("entities/sheepuff/orange");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_MAGENTA = register("entities/sheepuff/magenta");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_LIGHT_BLUE = register("entities/sheepuff/light_blue");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_YELLOW = register("entities/sheepuff/yellow");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_LIME = register("entities/sheepuff/lime");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_PINK = register("entities/sheepuff/pink");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_GRAY = register("entities/sheepuff/gray");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_LIGHT_GRAY = register("entities/sheepuff/light_gray");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_CYAN = register("entities/sheepuff/cyan");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_PURPLE = register("entities/sheepuff/purple");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_BLUE = register("entities/sheepuff/blue");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_BROWN = register("entities/sheepuff/brown");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_GREEN = register("entities/sheepuff/green");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_RED = register("entities/sheepuff/red");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_BLACK = register("entities/sheepuff/black");


    // Stripping
    public static final ResourceKey<LootTable> STRIP_MOSSY_WISPROOT = register("stripping/strip_mossy_wisproot");
    public static final ResourceKey<LootTable> STRIP_AMBEROOT = register("stripping/strip_amberoot");

    private static ResourceKey<LootTable> register(String id) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, id)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> id) {
        if (LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }
}
