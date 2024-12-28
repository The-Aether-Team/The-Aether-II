package com.aetherteam.aetherii.loot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.*;

public class AetherIILoot {
    private static final Set<ResourceKey<LootTable>> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceKey<LootTable>> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

    // Entities
    public static final Map<Kirrid.KirridColor, ResourceKey<LootTable>> ENTITIES_HIGHFIELDS_KIRRID_WOOL_BY_DYE = Util.make(new EnumMap<>(Kirrid.KirridColor.class), (map) -> makeKirridColorMap(map, "entities/kirrid/highfields"));
    public static final Map<Kirrid.KirridColor, ResourceKey<LootTable>> ENTITIES_MAGNETIC_KIRRID_WOOL_BY_DYE = Util.make(new EnumMap<>(Kirrid.KirridColor.class), (map) -> makeKirridColorMap(map, "entities/kirrid/magnetic"));
    public static final Map<Kirrid.KirridColor, ResourceKey<LootTable>> ENTITIES_ARCTIC_KIRRID_WOOL_BY_DYE = Util.make(new EnumMap<>(Kirrid.KirridColor.class), (map) -> makeKirridColorMap(map, "entities/kirrid/arctic"));

    public static final Map<Sheepuff.SheepuffColor, ResourceKey<LootTable>> ENTITIES_SHEEPUFF_WOOL_BY_DYE = Util.make(new EnumMap<>(Sheepuff.SheepuffColor.class), (map) -> makeSheepuffColorMap(map, "entities/sheepuff"));

    // Shearing
    public static final Map<Kirrid.KirridColor, ResourceKey<LootTable>> SHEARING_HIGHFIELDS_KIRRID_WOOL_BY_DYE = Util.make(new EnumMap<>(Kirrid.KirridColor.class), (map) -> makeKirridColorMap(map, "shearing/kirrid/highfields"));
    public static final Map<Kirrid.KirridColor, ResourceKey<LootTable>> SHEARING_MAGNETIC_KIRRID_WOOL_BY_DYE = Util.make(new EnumMap<>(Kirrid.KirridColor.class), (map) -> makeKirridColorMap(map, "shearing/kirrid/magnetic"));
    public static final Map<Kirrid.KirridColor, ResourceKey<LootTable>> SHEARING_ARCTIC_KIRRID_WOOL_BY_DYE = Util.make(new EnumMap<>(Kirrid.KirridColor.class), (map) -> makeKirridColorMap(map, "shearing/kirrid/arctic"));

    public static final Map<Sheepuff.SheepuffColor, ResourceKey<LootTable>> SHEARING_SHEEPUFF_WOOL_BY_DYE = Util.make(new EnumMap<>(Sheepuff.SheepuffColor.class), (map) -> makeSheepuffColorMap(map, "shearing/sheepuff"));

    public static final ResourceKey<LootTable> SHEARING_HIGHFIELDS_KIRRID = register("shearing/kirrid/highfields");
    public static final ResourceKey<LootTable> SHEARING_MAGNETIC_KIRRID = register("shearing/kirrid/magnetic");
    public static final ResourceKey<LootTable> SHEARING_ARCTIC_KIRRID = register("shearing/kirrid/arctic");

    public static final ResourceKey<LootTable> SHEARING_SHEEPUFF = register("shearing/sheepuff");

    // Chests
    public static final ResourceKey<LootTable> CHESTS_CAMP_HIGHFIELDS = register("chests/camp_highfields");
    public static final ResourceKey<LootTable> CHESTS_CAMP_HIGHFIELDS_FARMER = register("chests/camp_highfields_farmer");
    public static final ResourceKey<LootTable> CHESTS_CAMP_HIGHFIELDS_HUNTER = register("chests/camp_highfields_hunter");
    public static final ResourceKey<LootTable> CHESTS_CAMP_HIGHFIELDS_FLETCHER = register("chests/camp_highfields_fletcher");

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

    private static void makeKirridColorMap(EnumMap<Kirrid.KirridColor, ResourceKey<LootTable>> map, String name) {
        for (Kirrid.KirridColor color : Kirrid.KirridColor.values()) {
            map.put(color, register(name + "/" + color.name().toLowerCase()));
        }
    }

    private static void makeSheepuffColorMap(EnumMap<Sheepuff.SheepuffColor, ResourceKey<LootTable>> map, String name) {
        for (Sheepuff.SheepuffColor color : Sheepuff.SheepuffColor.values()) {
            map.put(color, register(name + "/" + color.name().toLowerCase()));
        }
    }
}
