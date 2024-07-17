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
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_PLAIN = register("entities/kirrid/highfields/plain");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_WHITE = register("entities/kirrid/highfields/white");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_ORANGE = register("entities/kirrid/highfields/orange");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_MAGENTA = register("entities/kirrid/highfields/magenta");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_LIGHT_BLUE = register("entities/kirrid/highfields/light_blue");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_YELLOW = register("entities/kirrid/highfields/yellow");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_LIME = register("entities/kirrid/highfields/lime");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_PINK = register("entities/kirrid/highfields/pink");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_GRAY = register("entities/kirrid/highfields/gray");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_LIGHT_GRAY = register("entities/kirrid/highfields/light_gray");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_CYAN = register("entities/kirrid/highfields/cyan");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_PURPLE = register("entities/kirrid/highfields/purple");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_BLUE = register("entities/kirrid/highfields/blue");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_BROWN = register("entities/kirrid/highfields/brown");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_GREEN = register("entities/kirrid/highfields/green");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_RED = register("entities/kirrid/highfields/red");
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_BLACK = register("entities/kirrid/highfields/black");

    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_PLAIN = register("entities/kirrid/magnetic/plain");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_WHITE = register("entities/kirrid/magnetic/white");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_ORANGE = register("entities/kirrid/magnetic/orange");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_MAGENTA = register("entities/kirrid/magnetic/magenta");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_LIGHT_BLUE = register("entities/kirrid/magnetic/light_blue");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_YELLOW = register("entities/kirrid/magnetic/yellow");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_LIME = register("entities/kirrid/magnetic/lime");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_PINK = register("entities/kirrid/magnetic/pink");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_GRAY = register("entities/kirrid/magnetic/gray");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_LIGHT_GRAY = register("entities/kirrid/magnetic/light_gray");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_CYAN = register("entities/kirrid/magnetic/cyan");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_PURPLE = register("entities/kirrid/magnetic/purple");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_BLUE = register("entities/kirrid/magnetic/blue");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_BROWN = register("entities/kirrid/magnetic/brown");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_GREEN = register("entities/kirrid/magnetic/green");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_RED = register("entities/kirrid/magnetic/red");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_BLACK = register("entities/kirrid/magnetic/black");

    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_PLAIN = register("entities/kirrid/arctic/plain");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_WHITE = register("entities/kirrid/arctic/white");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_ORANGE = register("entities/kirrid/arctic/orange");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_MAGENTA = register("entities/kirrid/arctic/magenta");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_LIGHT_BLUE = register("entities/kirrid/arctic/light_blue");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_YELLOW = register("entities/kirrid/arctic/yellow");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_LIME = register("entities/kirrid/arctic/lime");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_PINK = register("entities/kirrid/arctic/pink");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_GRAY = register("entities/kirrid/arctic/gray");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_LIGHT_GRAY = register("entities/kirrid/arctic/light_gray");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_CYAN = register("entities/kirrid/arctic/cyan");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_PURPLE = register("entities/kirrid/arctic/purple");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_BLUE = register("entities/kirrid/arctic/blue");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_BROWN = register("entities/kirrid/arctic/brown");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_GREEN = register("entities/kirrid/arctic/green");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_RED = register("entities/kirrid/arctic/red");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_BLACK = register("entities/kirrid/arctic/black");

    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_PLAIN = register("entities/sheepuff/plain");
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
