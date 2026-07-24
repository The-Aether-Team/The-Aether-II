package com.aetherteam.aetherii.loot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.*;

public class AetherIILoot {
    private static final Set<ResourceKey<LootTable>> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceKey<LootTable>> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

    // Entities
    public static final ResourceKey<LootTable> ENTITIES_HIGHFIELDS_KIRRID_WOOL_UNDYED = register("entities/kirrid/highfields/undyed");
    public static final ResourceKey<LootTable> ENTITIES_MAGNETIC_KIRRID_WOOL_UNDYED = register("entities/kirrid/magnetic/undyed");
    public static final ResourceKey<LootTable> ENTITIES_ARCTIC_KIRRID_WOOL_UNDYED = register("entities/kirrid/arctic/undyed");

    public static final Map<Kirrid.KirridColor, ResourceKey<LootTable>> ENTITIES_HIGHFIELDS_KIRRID_WOOL_BY_DYE = Util.make(new EnumMap<>(Kirrid.KirridColor.class), (map) -> makeKirridColorMap(map, "entities/kirrid/highfields"));
    public static final Map<Kirrid.KirridColor, ResourceKey<LootTable>> ENTITIES_MAGNETIC_KIRRID_WOOL_BY_DYE = Util.make(new EnumMap<>(Kirrid.KirridColor.class), (map) -> makeKirridColorMap(map, "entities/kirrid/magnetic"));
    public static final Map<Kirrid.KirridColor, ResourceKey<LootTable>> ENTITIES_ARCTIC_KIRRID_WOOL_BY_DYE = Util.make(new EnumMap<>(Kirrid.KirridColor.class), (map) -> makeKirridColorMap(map, "entities/kirrid/arctic"));

    public static final Map<Sheepuff.SheepuffColor, ResourceKey<LootTable>> ENTITIES_SHEEPUFF_WOOL_BY_DYE = Util.make(new EnumMap<>(Sheepuff.SheepuffColor.class), (map) -> makeSheepuffColorMap(map, "entities/sheepuff"));

    public static final ResourceKey<LootTable> SHEARING_HIGHFIELDS_KIRRID_WOOL_UNDYED = register("shearing/kirrid/highfields/undyed");
    public static final ResourceKey<LootTable> SHEARING_MAGNETIC_KIRRID_WOOL_UNDYED = register("shearing/kirrid/magnetic/undyed");
    public static final ResourceKey<LootTable> SHEARING_ARCTIC_KIRRID_WOOL_UNDYED = register("shearing/kirrid/arctic/undyed");
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
    public static final ResourceKey<LootTable> CHESTS_MOA_FEATHERS = register("chests/moa_feathers");

    public static final ResourceKey<LootTable> CHESTS_CAMP_SELECTOR = register("chests/camp/selector");
    public static final ResourceKey<LootTable> CHESTS_CAMP_FARMER = register("chests/camp/farmer");
    public static final ResourceKey<LootTable> CHESTS_CAMP_HUNTER = register("chests/camp/hunter");
    public static final ResourceKey<LootTable> CHESTS_CAMP_FLETCHER = register("chests/camp/fletcher");

    public static final ResourceKey<LootTable> CHESTS_WATCHTOWER = register("chests/watchtower/common");

    public static final ResourceKey<LootTable> CHESTS_ANIMAL_DEN = register("chests/animal_den");

    public static final ResourceKey<LootTable> VASES_VERADEXIAN_RUINS = register("vases/veradexian_ruins/common");
    public static final ResourceKey<LootTable> CHESTS_VERADEXIAN_LIBRARY = register("chests/veradexian_library/common");
    public static final ResourceKey<LootTable> CHESTS_VERADEXIAN_LIBRARY_ABANDONED_BAGS = register("chests/veradexian_library/abandoned_bags");
    public static final ResourceKey<LootTable> CHESTS_VERADEXIAN_LIBRARY_COCKATRICE_NESTS = register("chests/veradexian_library/cockatrice_nests");
    public static final ResourceKey<LootTable> CHESTS_VERADEXIAN_LIBRARY_VAULTS = register("chests/veradexian_library/vaults");
    public static final ResourceKey<LootTable> CHESTS_VERADEXIAN_LIBRARY_VAULTS_TREASURE = register("chests/veradexian_library/vaults_treasure");

    public static final ResourceKey<LootTable> VASES_BREXALLEN_RUINS = register("vases/brexallen_ruins/common");
    public static final ResourceKey<LootTable> CHESTS_BREXALLEN_RUINS = register("chests/brexallen_ruins/common");

    public static final ResourceKey<LootTable> VASES_UNDERCLOUD_MINESHAFT = register("vases/undercloud_mineshaft/common");
    public static final ResourceKey<LootTable> CHESTS_UNDERCLOUD_MINESHAFT = register("chests/undercloud_mineshaft/common");
    public static final ResourceKey<LootTable> CHESTS_UNDERCLOUD_MINESHAFT_STORAGE = register("chests/undercloud_mineshaft/storage");
    public static final ResourceKey<LootTable> CHESTS_UNDERCLOUD_MINESHAFT_SUPPLIES = register("chests/undercloud_mineshaft/supplies");
    public static final ResourceKey<LootTable> CHESTS_UNDERCLOUD_MINESHAFT_EQUIPMENT = register("chests/undercloud_mineshaft/equipment");
   // public static final ResourceKey<LootTable> CHESTS_UNDERCLOUD_MINESHAFT_BRIDGES = register("chests/undercloud_mineshaft/bridges");
    public static final ResourceKey<LootTable> CHESTS_UNDERCLOUD_MINESHAFT_RARE = register("chests/undercloud_mineshaft/rare");
    public static final ResourceKey<LootTable> CHESTS_UNDERCLOUD_MINESHAFT_ABANDONED_BAGS = register("chests/undercloud_mineshaft/abandoned_bags");
    public static final ResourceKey<LootTable> CHESTS_UNDERCLOUD_MINESHAFT_COCKATRICE_NESTS = register("chests/undercloud_mineshaft/cockatrice_nests");

    public static final ResourceKey<LootTable> VASES_ANCIENT_HENGE = register("vases/ancient_henge/common");

    public static final ResourceKey<LootTable> CHESTS_IRRADIATED_REMNANTS = register("chests/irradiated_remnants");

    public static final ResourceKey<LootTable> CHESTS_DUNGEONS_IRRADIATED_ITEMS = register("chests/dungeons/irradiated_items");
    public static final ResourceKey<LootTable> CHESTS_DUNGEONS_MUSIC_DISCS = register("chests/dungeons/music_discs");

    public static final ResourceKey<LootTable> CHESTS_DUNGEONS_SENTRY_RUINS_COMMON = register("chests/dungeons/sentry_ruins/common");
    public static final ResourceKey<LootTable> CHESTS_DUNGEONS_SENTRY_RUINS_RARE = register("chests/dungeons/sentry_ruins/rare");
    public static final ResourceKey<LootTable> CHESTS_DUNGEONS_SENTRY_RUINS_MATERIAL_DEPOSIT = register("chests/dungeons/sentry_ruins/material_deposit");
    public static final ResourceKey<LootTable> CHESTS_DUNGEONS_SENTRY_RUINS_COLD_STORAGE = register("chests/dungeons/sentry_ruins/cold_storage");
    public static final ResourceKey<LootTable> CHESTS_DUNGEONS_SENTRY_RUINS_BOSS = register("chests/dungeons/sentry_ruins/boss");

    // Gift
    public static final ResourceKey<LootTable> TAEGORE_DIGGING = register("gameplay/taegore_digging");
    public static final ResourceKey<LootTable> PRISMALLARD_LAY = register("gameplay/prismallard_lay");

    // Stripping
    public static final ResourceKey<LootTable> STRIP_MOSSY_WISPROOT_BASE = register("stripping/strip_mossy_wisproot_base");
    public static final ResourceKey<LootTable> STRIP_MOSSY_WISPROOT = register("stripping/strip_mossy_wisproot");
    public static final ResourceKey<LootTable> STRIP_AMBEROOT_DEPOSIT = register("stripping/strip_amberoot_deposit");

    private static ResourceKey<LootTable> register(String id) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(AetherII.MODID, id)));
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
            map.put(color, register(name + "/" + color.name().toLowerCase(Locale.ROOT)));
        }
    }

    private static void makeSheepuffColorMap(EnumMap<Sheepuff.SheepuffColor, ResourceKey<LootTable>> map, String name) {
        for (Sheepuff.SheepuffColor color : Sheepuff.SheepuffColor.values()) {
            map.put(color, register(name + "/" + color.name().toLowerCase(Locale.ROOT)));
        }
    }
}
