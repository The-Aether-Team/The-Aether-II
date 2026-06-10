package com.aetherteam.aetherii;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;

public class AetherIITags {
    public static class Blocks {
        public static final TagKey<Block> AETHER_GRASS_BLOCKS = tag("aether_grass_blocks");
        public static final TagKey<Block> AETHER_DIRT = tag("aether_dirt");
        public static final TagKey<Block> AETHER_GROUND_BLOCKS = tag("aether_ground_blocks");
        public static final TagKey<Block> AETHER_MOSS_BLOCKS = tag("aether_moss_blocks");
        public static final TagKey<Block> AETHER_MOSS_VINES = tag("aether_moss_vines");
        public static final TagKey<Block> AETHER_MOSS_CARPETS = tag("aether_moss_carpets");
        public static final TagKey<Block> HOLYSTONE = tag("holystone");
        public static final TagKey<Block> UNDERSHALE = tag("undershale");
        public static final TagKey<Block> ARCTIC_ICE = tag("arctic_ice");
        public static final TagKey<Block> FERROSITE = tag("ferrosite");
        public static final TagKey<Block> AETHER_SURFACE_STONES = tag("aether_surface_stones");
        public static final TagKey<Block> AETHER_UNDERCLOUD_STONES = tag("aether_undercloud_stones");
        public static final TagKey<Block> AETHER_STONES = tag("aether_stones");
        public static final TagKey<Block> AERCLOUDS = tag("aerclouds");
        public static final TagKey<Block> CLOUDWOOL = tag("cloudwool");
        public static final TagKey<Block> SKYROOT_LOGS = tag("skyroot_logs");
        public static final TagKey<Block> GREATROOT_LOGS = tag("greatroot_logs");
        public static final TagKey<Block> WISPROOT_LOGS = tag("wisproot_logs");
        public static final TagKey<Block> AMBEROOT_LOGS = tag("amberoot_logs");
        public static final TagKey<Block> GUARDIAN_LOGS = tag("guardian_logs");
        public static final TagKey<Block> AETHER_NATURAL_LOGS = tag("natural_logs/aether");
        public static final TagKey<Block> TRUNKS = tag("trunks");
        public static final TagKey<Block> LEAVES = tag("leaves");
        public static final TagKey<Block> LEAF_PILES = tag("leaf_piles");
        public static final TagKey<Block> SKYROOT_DECORATIVE_BLOCKS = tag("skyroot_decorative_blocks");
        public static final TagKey<Block> GREATROOT_DECORATIVE_BLOCKS = tag("greatroot_decorative_blocks");
        public static final TagKey<Block> WISPROOT_DECORATIVE_BLOCKS = tag("wisproot_decorative_blocks");
        public static final TagKey<Block> AMBEROOT_DECORATIVE_BLOCKS = tag("amberoot_decorative_blocks");
        public static final TagKey<Block> HOLYSTONE_DECORATIVE_BLOCKS = tag("holystone_decorative_blocks");
        public static final TagKey<Block> FADED_HOLYSTONE_DECORATIVE_BLOCKS = tag("faded_holystone_decorative_blocks");
        public static final TagKey<Block> UNDERSHALE_DECORATIVE_BLOCKS = tag("undershale_decorative_blocks");
        public static final TagKey<Block> SENTRY_DECORATIVE_BLOCKS = tag("sentry_decorative_blocks");
        public static final TagKey<Block> ICHORITE_DECORATIVE_BLOCKS = tag("ichorite_decorative_blocks");
        public static final TagKey<Block> MARBLED_ICHORITE_DECORATIVE_BLOCKS = tag("marbled_ichorite_decorative_blocks");
        public static final TagKey<Block> AGIOSITE_DECORATIVE_BLOCKS = tag("agiosite_decorative_blocks");
        public static final TagKey<Block> ICESTONE_DECORATIVE_BLOCKS = tag("icestone_decorative_blocks");
        public static final TagKey<Block> QUICKSOIL_GLASS_DECORATIVE_BLOCKS = tag("quicksoil_glass_decorative_blocks");
        public static final TagKey<Block> QUICKSOIL_GLASS_PANE_DECORATIVE_BLOCKS = tag("quicksoil_glass_pane_decorative_blocks");
        public static final TagKey<Block> CRUDE_SCATTERGLASS_DECORATIVE_BLOCKS = tag("crude_scatterglass_decorative_blocks");
        public static final TagKey<Block> CRUDE_SCATTERGLASS_PANE_DECORATIVE_BLOCKS = tag("crude_scatterglass_pane_decorative_blocks");
        public static final TagKey<Block> SCATTERGLASS_DECORATIVE_BLOCKS = tag("scatterglass_decorative_blocks");
        public static final TagKey<Block> SCATTERGLASS_PANE_DECORATIVE_BLOCKS = tag("scatterglass_pane_decorative_blocks");
        public static final TagKey<Block> ARKENIUM_BARS_DECORATIVE_BLOCKS = tag("arkenium_bars_decorative_blocks");
        public static final TagKey<Block> RUSTIC_ARKENIUM_BARS_DECORATIVE_BLOCKS = tag("rustic_arkenium_bars_decorative_blocks");
        public static final TagKey<Block> QUICKSOIL_GLASS = tag("quicksoil_glass");
        public static final TagKey<Block> CRUDE_SCATTERGLASS = tag("crude_scatterglass");
        public static final TagKey<Block> SCATTERGLASS = tag("scatterglass");
        public static final TagKey<Block> QUICKSOIL_GLASS_PANE = tag("quicksoil_glass_pane");
        public static final TagKey<Block> CRUDE_SCATTERGLASS_PANE = tag("crude_scatterglass_pane");
        public static final TagKey<Block> SCATTERGLASS_PANE = tag("scatterglass_pane");
        public static final TagKey<Block> ARKENIUM_BARS = tag("arkenium_bars");
        public static final TagKey<Block> ARILUM_LANTERN = tag("arilum_lantern");

        public static final TagKey<Block> AETHER_PORTAL_BLOCKS = tag("aether_portal_blocks");
        public static final TagKey<Block> AETHER_PORTAL_SPAWN_WHITELIST = tag("aether_portal_spawn_whitelist");
        public static final TagKey<Block> ALKAHEST_RESISTANT = tag("alkahest_resistant");
        public static final TagKey<Block> ALKAHEST_INSTANTLY_DESTROYS = tag("alkahest_instantly_destroys");
        public static final TagKey<Block> ALKAHEST_QUICKLY_DESTROYS = tag("alkahest_quickly_destroys");
        public static final TagKey<Block> ALKAHEST_SLOWLY_DESTROYS = tag("alkahest_slowly_destroys");
        public static final TagKey<Block> TRIGGERS_HESTVEIL = tag("triggers_hestveil");
        public static final TagKey<Block> CARRIES_SENTRY_CURRENT = tag("carries_sentry_current");
        public static final TagKey<Block> COPYABLE_DUNGEON_BLOCKS = tag("copyable_dungeon_blocks");
        public static final TagKey<Block> MIMIC_CONTAINERS = tag("mimic_containers");

        public static final TagKey<Block> ALLOWED_SKYROOT_BUCKET_PICKUP = tag("allowed_skyroot_bucket_pickup");
        public static final TagKey<Block> HOLYSTONE_ABILITY_GUARANTEED = tag("holystone_ability_guaranteed");
        public static final TagKey<Block> GRAVITITE_ABILITY_BLACKLIST = tag("gravitite_ability_blacklist");

        public static final TagKey<Block> AETHER_ANIMALS_SPAWNABLE_ON = tag("aether_animal_spawnable_on");
        public static final TagKey<Block> AECHOR_PLANT_SPAWNABLE_ON = tag("aechor_plant_spawnable_on");
        public static final TagKey<Block> CARRION_SPROUT_SPAWNABLE_ON = tag("carrion_sprout_spawnable_on");
        public static final TagKey<Block> SKEPHID_SPAWNABLE_ON = tag("skephid_spawnable_on");
        public static final TagKey<Block> SWET_SPAWNABLE_ON = tag("swet_spawnable_on");
        public static final TagKey<Block> TAEGORE_DIGGABLE_BLOCK = tag("taegore_diggable_block");
        public static final TagKey<Block> MOA_HATCH_BLOCK = tag("moa_hatch_block");
        public static final TagKey<Block> SLIDER_UNBREAKABLE = tag("slider_unbreakable");
        public static final TagKey<Block> NOT_DROPPED_BY_SLIDER_COLLISION = tag("not_dropped_by_slider_collision");
        public static final TagKey<Block> HOVERING_BLOCK_CANT_REPLACE = tag("hovering_block_cant_replace");

        public static final TagKey<Block> AETHER_UNDERGROUND_BLOCKS = tag("aether_underground_blocks");
        public static final TagKey<Block> AETHER_CARVER_REPLACEABLES = tag("aether_carver_replaceables");
        public static final TagKey<Block> SHAPES_COASTS = tag("shapes_coasts");
        public static final TagKey<Block> SUPPORTS_AETHER_PLANT = tag("supports_aether_plant");
        public static final TagKey<Block> SUPPORTS_SKYROOT_TWIG = tag("supports_skyroot_twig");
        public static final TagKey<Block> SUPPORTS_HOLYSTONE_ROCK = tag("supports_holystone_rock");
        public static final TagKey<Block> SUPPORTS_BOULDER = tag("supports_boulder");
        public static final TagKey<Block> SUPPORTS_FALLEN_LOG = tag("supports_fallen_log");
        public static final TagKey<Block> SUPPORTS_BRETTL_PLANT = tag("supports_brettl_plant");
        public static final TagKey<Block> SUPPORTS_SKY_ROOTS = tag("supports_sky_roots");
        public static final TagKey<Block> SUPPORTS_ICE_CRYSTAL = tag("supports_ice_crystal");
        public static final TagKey<Block> SUPPORTS_ARCTIC_TREE = tag("supports_arctic_tree");
        public static final TagKey<Block> SUPPORTS_ARILUM = tag("supports_arilum");
        public static final TagKey<Block> SUPPORTS_MAGNETIC_SHROOM = tag("supports_arilum");
        public static final TagKey<Block> GRASS_AND_DIRT_REPLACEABLE = tag("grass_and_dirt_replaceable");
        public static final TagKey<Block> COARSE_AETHER_DIRT_REPLACEABLE = tag("coarse_aether_dirt_replaceable");
        public static final TagKey<Block> MYCELIAL_AETHER_DIRT_REPLACEABLE = tag("mycelial_aether_dirt_replaceable");
        public static final TagKey<Block> BRYALINN_MOSS_REPLACEABLE = tag("bryalinn_moss_replaceable");
        public static final TagKey<Block> SHAYELINN_MOSS_REPLACEABLE = tag("shayelinn_moss_replaceable");
        public static final TagKey<Block> ARCTIC_ICE_REPLACEABLE = tag("arctic_ice_replaceable");
        public static final TagKey<Block> GRASS_SNOW_REPLACEABLE = tag("grass_snow_replaceable");
        public static final TagKey<Block> LAKE_VEGETATION_REPLACEABLES = tag("lake_vegetation_replaceables");
        public static final TagKey<Block> QUICKSOIL_COAST_GENERATES_ON = tag("quicksoil_coast_generates_on");
        public static final TagKey<Block> FERROSITE_COAST_GENERATES_ON = tag("ferrosite_coast_generates_on");
        public static final TagKey<Block> FERROSITE_PILLAR_COAST_GENERATES_ON = tag("ferrosite_pillar_coast_generates_on");
        public static final TagKey<Block> ARCTIC_COAST_GENERATES_ON = tag("arctic_coast_generates_on");
        public static final TagKey<Block> FERROSITE_PILLAR_GENERATES_ON = tag("ferrosite_pillar_generates_on");
        public static final TagKey<Block> FERROSITE_SPIKE_GENERATES_ON = tag("ferrosite_spike_generates_on");
        public static final TagKey<Block> ARCTIC_ICE_SPIKE_GENERATES_ON = tag("ferrosite_spike_generates_on");
        public static final TagKey<Block> GROWS_ON_MOSSY_LEAVES = tag("grows_on_mossy_leaves");

        public static final TagKey<Block> NON_SENTRY_RUINS_SPAWNABLE = tag("non_sentry_ruins_spawnable");
        public static final TagKey<Block> NON_TUNNEL_REPLACEABLE = tag("non_tunnel_replaceable");
        public static final TagKey<Block> STRUCTURE_MOSS_REPLACEABLES = tag("structure_moss_replaceables");
        public static final TagKey<Block> UNDERGROWTH_PATCH_GENERATES_ON = tag("undergrowth_patch_generates_on");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> AETHER_GRASS_BLOCKS = tag("aether_grass_blocks");
        public static final TagKey<Item> AETHER_DIRT = tag("aether_dirt");
        public static final TagKey<Item> AETHER_GROUND_BLOCKS = tag("aether_ground_blocks");
        public static final TagKey<Item> AETHER_MOSS_BLOCKS = tag("aether_moss_blocks");
        public static final TagKey<Item> AETHER_MOSS_VINES = tag("aether_moss_vines");
        public static final TagKey<Item> AETHER_MOSS_CARPETS = tag("aether_moss_carpets");
        public static final TagKey<Item> HOLYSTONE = tag("holystone");
        public static final TagKey<Item> UNDERSHALE = tag("undershale");
        public static final TagKey<Item> ARCTIC_ICE = tag("arctic_ice");
        public static final TagKey<Item> FERROSITE = tag("ferrosite");
        public static final TagKey<Item> AETHER_SURFACE_STONES = tag("aether_surface_stones");
        public static final TagKey<Item> AETHER_UNDERCLOUD_STONES = tag("aether_undercloud_stones");
        public static final TagKey<Item> AETHER_STONES = tag("aether_stones");
        public static final TagKey<Item> AERCLOUDS = tag("aerclouds");
        public static final TagKey<Item> CLOUDWOOL = tag("cloudwool");
        public static final TagKey<Item> SKYROOT_LOGS = tag("skyroot_logs");
        public static final TagKey<Item> GREATROOT_LOGS = tag("greatroot_logs");
        public static final TagKey<Item> WISPROOT_LOGS = tag("wisproot_logs");
        public static final TagKey<Item> AMBEROOT_LOGS = tag("amberoot_logs");
        public static final TagKey<Item> GUARDIAN_LOGS = tag("guardian_logs");
        public static final TagKey<Item> AETHER_NATURAL_LOGS = tag("natural_logs/aether");
        public static final TagKey<Item> TRUNKS = tag("trunks");
        public static final TagKey<Item> LEAVES = tag("leaves");
        public static final TagKey<Item> LEAF_PILES = tag("leaf_piles");
        public static final TagKey<Item> SKYROOT_DECORATIVE_BLOCKS = tag("skyroot_decorative_blocks");
        public static final TagKey<Item> GREATROOT_DECORATIVE_BLOCKS = tag("greatroot_decorative_blocks");
        public static final TagKey<Item> WISPROOT_DECORATIVE_BLOCKS = tag("wisproot_decorative_blocks");
        public static final TagKey<Item> AMBEROOT_DECORATIVE_BLOCKS = tag("amberoot_decorative_blocks");
        public static final TagKey<Item> HOLYSTONE_DECORATIVE_BLOCKS = tag("holystone_decorative_blocks");
        public static final TagKey<Item> FADED_HOLYSTONE_DECORATIVE_BLOCKS = tag("faded_holystone_decorative_blocks");
        public static final TagKey<Item> UNDERSHALE_DECORATIVE_BLOCKS = tag("undershale_decorative_blocks");
        public static final TagKey<Item> SENTRY_DECORATIVE_BLOCKS = tag("sentry_decorative_blocks");
        public static final TagKey<Item> ICHORITE_DECORATIVE_BLOCKS = tag("ichorite_decorative_blocks");
        public static final TagKey<Item> MARBLED_ICHORITE_DECORATIVE_BLOCKS = tag("marbled_ichorite_decorative_blocks");
        public static final TagKey<Item> AGIOSITE_DECORATIVE_BLOCKS = tag("agiosite_decorative_blocks");
        public static final TagKey<Item> ICESTONE_DECORATIVE_BLOCKS = tag("icestone_decorative_blocks");
        public static final TagKey<Item> QUICKSOIL_GLASS_DECORATIVE_BLOCKS = tag("quicksoil_glass_decorative_blocks");
        public static final TagKey<Item> QUICKSOIL_GLASS_PANE_DECORATIVE_BLOCKS = tag("quicksoil_glass_pane_decorative_blocks");
        public static final TagKey<Item> CRUDE_SCATTERGLASS_DECORATIVE_BLOCKS = tag("crude_scatterglass_decorative_blocks");
        public static final TagKey<Item> CRUDE_SCATTERGLASS_PANE_DECORATIVE_BLOCKS = tag("crude_scatterglass_pane_decorative_blocks");
        public static final TagKey<Item> SCATTERGLASS_DECORATIVE_BLOCKS = tag("scatterglass_decorative_blocks");
        public static final TagKey<Item> SCATTERGLASS_PANE_DECORATIVE_BLOCKS = tag("scatterglass_pane_decorative_blocks");
        public static final TagKey<Item> ARKENIUM_BARS_DECORATIVE_BLOCKS = tag("arkenium_bars_decorative_blocks");
        public static final TagKey<Item> RUSTIC_ARKENIUM_BARS_DECORATIVE_BLOCKS = tag("rustic_arkenium_bars_decorative_blocks");
        public static final TagKey<Item> QUICKSOIL_GLASS = tag("quicksoil_glass");
        public static final TagKey<Item> CRUDE_SCATTERGLASS = tag("crude_scatterglass");
        public static final TagKey<Item> SCATTERGLASS = tag("scatterglass");
        public static final TagKey<Item> QUICKSOIL_GLASS_PANE = tag("quicksoil_glass_pane");
        public static final TagKey<Item> CRUDE_SCATTERGLASS_PANE = tag("crude_scatterglass_pane");
        public static final TagKey<Item> SCATTERGLASS_PANE = tag("scatterglass_pane");
        public static final TagKey<Item> ARKENIUM_BARS = tag("arkenium_bars");
        public static final TagKey<Item> ARILUM_LANTERN = tag("arilum_lantern");

        public static final TagKey<Item> RODS_SKYROOT = tag("rods/skyroot");
        public static final TagKey<Item> RAW_MATERIALS_ZANITE = tag("raw_materials/zanite");
        public static final TagKey<Item> RAW_MATERIALS_ARKENIUM = tag("raw_materials/arkenium");
        public static final TagKey<Item> RAW_MATERIALS_GRAVITITE = tag("raw_materials/gravitite");
        public static final TagKey<Item> RAW_MATERIALS_GLINT = tag("raw_materials/glint");
        public static final TagKey<Item> RAW_MATERIALS_CORROBONITE = tag("raw_materials/corrobonite");
        public static final TagKey<Item> GEMS_AMBROSIUM = tag("gems/ambrosium");
        public static final TagKey<Item> GEMS_AMBER = tag("gems/amber");
        public static final TagKey<Item> GEMS_ZANITE = tag("gems/zanite");
        public static final TagKey<Item> GEMS_GLINT = tag("gems/glint");
        public static final TagKey<Item> GEMS_CORROBONITE = tag("gems/corrobonite");
        public static final TagKey<Item> INGOTS_ARKENIUM = tag("ingots/arkenium");
        public static final TagKey<Item> INGOTS_GRAVITITE = tag("ingots/gravitite");
        public static final TagKey<Item> NUGGETS_ARKENIUM = tag("nuggets/arkenium");

        public static final TagKey<Item> PLANKS_CRAFTING = tag("planks_crafting");
        public static final TagKey<Item> STONE_CRAFTING = tag("stone_crafting");
        public static final TagKey<Item> CRAFTS_SKYROOT_STICKS = tag("crafts_skyroot_sticks");
        public static final TagKey<Item> CRAFTS_SKYROOT_TOOLS = tag("crafts_skyroot_tools");
        public static final TagKey<Item> CRAFTS_HOLYSTONE_TOOLS = tag("crafts_holystone_tools");
        public static final TagKey<Item> ALTAR_FUEL = tag("altar_fuel");
        public static final TagKey<Item> FORGE_PRIMARY_MATERIAL = tag("forge_primary_material");
        public static final TagKey<Item> FORGE_SECONDARY_MATERIAL = tag("forge_secondary_material");

        public static final TagKey<Item> TOOLS_TROWELS = tag("tools/trowels");
        public static final TagKey<Item> TOOLS_SHORTSWORDS = tag("tools/shortswords");
        public static final TagKey<Item> TOOLS_HAMMERS = tag("tools/hammers");
        public static final TagKey<Item> TOOLS_PIKES = tag("tools/pikes");
        public static final TagKey<Item> TOOLS_GLIDERS = tag("tools/gliders");

        public static final TagKey<Item> SKYROOT_TOOL = tag("tool/skyroot");
        public static final TagKey<Item> HOLYSTONE_TOOL = tag("tool/holystone");
        public static final TagKey<Item> ZANITE_TOOL = tag("tool/zanite");
        public static final TagKey<Item> ARKENIUM_TOOL = tag("tool/arkenium");
        public static final TagKey<Item> GRAVITITE_TOOL = tag("tool/gravitite");

        public static final TagKey<Item> BEAST_PELT_ARMOR = tag("armor/beast_pelt");
        public static final TagKey<Item> BURRUKAI_PLATE_ARMOR = tag("armor/burrukai_plate");
        public static final TagKey<Item> ZANITE_ARMOR = tag("armor/zanite");
        public static final TagKey<Item> ARKENIUM_ARMOR = tag("armor/arkenium");
        public static final TagKey<Item> GRAVITITE_ARMOR = tag("armor/gravitite");
        public static final TagKey<Item> SENTRY_ARMOR = tag("armor/sentry");
        public static final TagKey<Item> NEPTUNE_ARMOR = tag("armor/neptune");

        public static final TagKey<Item> EQUIPMENT_RELICS = tag("equipment/relics");
        public static final TagKey<Item> EQUIPMENT_HANDWEAR = tag("equipment/handwear");
        public static final TagKey<Item> EQUIPMENT_ACCESSORIES = tag("equipment/accessories");
        public static final TagKey<Item> EQUIPABLE = tag("equipable");

        public static final TagKey<Item> PENDANT_ACCESSORY = tag("accessory/pendant");

        public static final TagKey<Item> ACCEPTS_CHARMS_TOOLS = tag("accepts_charms/tools");
        public static final TagKey<Item> ACCEPTS_CHARMS_WEAPONS = tag("accepts_charms/weapons");
        public static final TagKey<Item> ACCEPTS_CHARMS_ARMOR = tag("accepts_charms/armor");

        public static final TagKey<Item> BEAST_PELT_REPAIRING = tag("beast_pelt_repairing");
        public static final TagKey<Item> BURRUKAI_PLATE_REPAIRING = tag("burrukai_plate_repairing");
        public static final TagKey<Item> SKYROOT_REPAIRING = tag("skyroot_repairing");
        public static final TagKey<Item> HOLYSTONE_REPAIRING = tag("holystone_repairing");
        public static final TagKey<Item> ZANITE_REPAIRING = tag("zanite_repairing");
        public static final TagKey<Item> ARKENIUM_REPAIRING = tag("arkenium_repairing");
        public static final TagKey<Item> GRAVITITE_REPAIRING = tag("gravitite_repairing");
        public static final TagKey<Item> SENTRY_BOOTS_REPAIRING = tag("sentry_boots_repairing");
        public static final TagKey<Item> HAMMER_OF_DEMOLITION_REPAIRING = tag("hammer_of_demolition_repairing");
        public static final TagKey<Item> NEPTUNE_REPAIRING = tag("neptune_repairing");

        public static final TagKey<Item> AERBUNNY_FOOD = tag("aerbunny_food");
        public static final TagKey<Item> AERBUNNY_TAME_ITEMS = tag("aerbunny_tame_items");
        public static final TagKey<Item> PHYG_FOOD = tag("phyg_food");
        public static final TagKey<Item> PHYG_CALM_ITEMS = tag("phyg_calm_items");
        public static final TagKey<Item> FLYING_COW_FOOD = tag("flying_food");
        public static final TagKey<Item> FLYING_COW_CALM_ITEMS = tag("flying_cow_calm_items");
        public static final TagKey<Item> SHEEPUFF_FOOD = tag("sheepuff_food");
        public static final TagKey<Item> TAEGORE_FOOD = tag("taegore_food");
        public static final TagKey<Item> BURRUKAI_FOOD = tag("burrukai_food");
        public static final TagKey<Item> KIRRID_FOOD = tag("kirrid_food");
        public static final TagKey<Item> MOA_FOOD = tag("moa_food");
        public static final TagKey<Item> PRISMALLARD_FOOD = tag("prismallard_food");

        public static final TagKey<Item> AETHER_PORTAL_ACTIVATION_ITEMS = tag("aether_portal_activation_items");
        public static final TagKey<Item> CAN_USE_ON_AERCLOUD = tag("can_use_on_aercloud");
        public static final TagKey<Item> GOLDEN_AMBER_HARVESTERS = tag("golden_amber_harvesters");
        public static final TagKey<Item> DOUBLE_DROPS = tag("double_drops");
        public static final TagKey<Item> IRRADIATED_ITEM = tag("irradiated_item");
        public static final TagKey<Item> ENGRAVED_DISCS = tag("engraved_discs");
        public static final TagKey<Item> ALKAHEST_RESISTANT_ITEM = tag("alkahest_resistant_item");
        public static final TagKey<Item> PLANT_DAMAGING_ITEMS = tag("plant_damaging_items");
        public static final TagKey<Item> SLIDER_DAMAGING_ITEMS = tag("slider_damaging_items");
        public static final TagKey<Item> UNBREAKABLE_LOOT = tag("unbreakable_loot");
        public static final TagKey<Item> UNIQUE_TOOLTIP_COLOR = tag("unique_tooltip_color");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> AETHER_MOBS = tag("aether_mobs");
        public static final TagKey<EntityType<?>> TAEGORE = tag("taegore");
        public static final TagKey<EntityType<?>> BURRUKAI = tag("burrukai");
        public static final TagKey<EntityType<?>> KIRRID = tag("kirrid");
        public static final TagKey<EntityType<?>> SWETS = tag("swets");
        public static final TagKey<EntityType<?>> TALUTONS = tag("talutons");
        public static final TagKey<EntityType<?>> PLANT_MOBS = tag("talutons");
        public static final TagKey<EntityType<?>> SENTRY_RUINS_MOBS = tag("sentry_ruins_mobs");
        public static final TagKey<EntityType<?>> DUNGEON_MOBS = tag("dungeon_mobs");

        public static final TagKey<EntityType<?>> NO_DOUBLE_DROPS = tag("no_double_drops");
        public static final TagKey<EntityType<?>> NO_AMBROSIUM_DROPS = tag("no_ambrosium_drops");
        public static final TagKey<EntityType<?>> ZEPHYR_BLOW_BLACKLIST = tag("zephyr_blow_blacklist");

        public static final TagKey<EntityType<?>> PLANT_DAMAGING_PROJECTILES = tag("plant_damaging_projectiles");
        public static final TagKey<EntityType<?>> SLIDER_DAMAGING_PROJECTILES = tag("slider_damaging_projectiles");
        public static final TagKey<EntityType<?>> STICKABLE_PROJECTILES = tag("stickable_projectiles");
        public static final TagKey<EntityType<?>> STICKABLE_PROJECTILES_EMISSIVE = tag("stickable_projectiles_emissive");

        public static final TagKey<EntityType<?>> SPAWNING_ICE = tag("spawning/ice");
        public static final TagKey<EntityType<?>> SPAWNING_AERCLOUDS = tag("spawning/aerclouds");
        public static final TagKey<EntityType<?>> SPAWNING_LEAVES = tag("spawning/leaves");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> ALKAHEST = tag("alkahest");

        public static final TagKey<Fluid> ALLOWED_SKYROOT_BUCKET_PICKUP = tag("allowed_skyroot_bucket_pickup");

        private static TagKey<Fluid> tag(String name) {
            return TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> THE_AETHER = tag("the_aether");

        public static final TagKey<Biome> HOLY_ISLES = tag("holy_isles");
        public static final TagKey<Biome> HIGHFIELDS = tag("highfields");
        public static final TagKey<Biome> MAGNETIC = tag("magnetic");
        public static final TagKey<Biome> MAGNETIC_FOG = tag("magnetic_fog");
        public static final TagKey<Biome> ARCTIC = tag("arctic");
        public static final TagKey<Biome> IRRADIATED = tag("irradiated");
        public static final TagKey<Biome> EXPANSE = tag("expanse");

        public static final TagKey<Biome> LUSH = tag("lush");
        public static final TagKey<Biome> WET = tag("wet");

        public static final TagKey<Biome> HAS_STRUCTURE_OUTPOST = tag("has_structure/outpost");
        public static final TagKey<Biome> HAS_STRUCTURE_CAMP_HIGHFIELDS = tag("has_structure/camp_highfields");
        public static final TagKey<Biome> HAS_STRUCTURE_CAMP_MAGNETIC = tag("has_structure/camp_magnetic");
        public static final TagKey<Biome> HAS_STRUCTURE_CAMP_ARCTIC = tag("has_structure/camp_arctic");
        public static final TagKey<Biome> HAS_STRUCTURE_WATCHTOWER = tag("has_structure/watchtower");
        public static final TagKey<Biome> HAS_STRUCTURE_ANIMAL_DEN = tag("has_structure/animal_den");
        public static final TagKey<Biome> HAS_STRUCTURE_VERADEXIAN_RUINS_TEMPERATE = tag("has_structure/veradexian_ruins_temperate");
        public static final TagKey<Biome> HAS_STRUCTURE_VERADEXIAN_RUINS_ARCTIC = tag("has_structure/veradexian_ruins_arctic");
        public static final TagKey<Biome> HAS_STRUCTURE_VERADEXIAN_LIBRARY_TEMPERATE = tag("has_structure/veradexian_library_temperate");
        public static final TagKey<Biome> HAS_STRUCTURE_VERADEXIAN_LIBRARY_ARCTIC = tag("has_structure/veradexian_library_arctic");
        public static final TagKey<Biome> HAS_STRUCTURE_VERADEXIAN_AQUEDUCT = tag("has_structure/veradexian_aqueduct");
        public static final TagKey<Biome> HAS_STRUCTURE_BREXALLEN_RUINS = tag("has_structure/brexallen_ruins");
        public static final TagKey<Biome> HAS_STRUCTURE_UNDERCLOUD_MINESHAFT = tag("has_structure/undercloud_mineshaft");
        public static final TagKey<Biome> HAS_STRUCTURE_ANCIENT_HENGE = tag("has_structure/ancient_henge");
        public static final TagKey<Biome> HAS_STRUCTURE_IRRADIATED_REMNANTS = tag("has_structure/irradiated_remnants");
        public static final TagKey<Biome> HAS_STRUCTURE_SENTRY_RUINS = tag("has_structure/sentry_ruins");
        public static final TagKey<Biome> HAS_STRUCTURE_INFECTED_GUARDIAN_TREE = tag("has_structure/infected_guardian_tree");

        public static final TagKey<Biome> MYCELIUM_CONVERSION = tag("mycelium_conversion");
        public static final TagKey<Biome> PODZOL_CONVERSION = tag("podzol_conversion");
        public static final TagKey<Biome> CRIMSON_NYLIUM_CONVERSION = tag("crimson_nylium_conversion");
        public static final TagKey<Biome> WARPED_NYLIUM_CONVERSION = tag("warped_nylium_conversion");

        public static final TagKey<Biome> ARCTIC_ICE = tag("arctic_ice");

        public static final TagKey<Biome> AETHER_MUSIC = tag("aether_music");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Structures {
        public static final TagKey<Structure> OUTPOSTS = tag("outposts");
        public static final TagKey<Structure> CAMPS = tag("camps");
        public static final TagKey<Structure> WATCHTOWERS = tag("watchtowers");
        public static final TagKey<Structure> SURFACE_RUINS = tag("surface_ruins");
        public static final TagKey<Structure> DUNGEONS = tag("dungeons");

        public static final TagKey<Structure> TREE_BLACKLIST_FILTER = tag("tree_blacklist_filter");
        public static final TagKey<Structure> ALKAHEST_POOL_BLACKLIST_FILTER = tag("alkahest_pool_blacklist_filter");
        public static final TagKey<Structure> COAST_BLACKLIST_FILTER = tag("coast_blacklist_filter");
        public static final TagKey<Structure> FERROSITE_SPIKE_BLACKLIST_FILTER = tag("ferrosite_spike_blacklist_filter");
        public static final TagKey<Structure> ARCTIC_ICE_SPIKE_BLACKLIST_FILTER = tag("arctic_ice_spike_blacklist_filter");
        public static final TagKey<Structure> AERCLOUD_BLACKLIST_FILTER = tag("aercloud_blacklist_filter");

        private static TagKey<Structure> tag(String name) {
            return TagKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> TYPED = tag("typed");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class MobEffects {
        public static final TagKey<MobEffect> DART_EFFECTS = tag("dart_effects");
        public static final TagKey<MobEffect> MILK_DOESNT_CLEAR = tag("milk_doesnt_clear");

        private static TagKey<MobEffect> tag(String name) {
            return TagKey.create(Registries.MOB_EFFECT, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class SoundEvents {
        public static final TagKey<SoundEvent> PORTAL_SOUNDS = tag("portal_sounds");
        public static final TagKey<SoundEvent> AMBIENT_PORTAL_SOUNDS = tag("ambient_portal_sounds");
        public static final TagKey<SoundEvent> ACTIVATED_PORTAL_SOUNDS = tag("activated_portal_sounds");
        public static final TagKey<SoundEvent> ACHIEVEMENT_SOUNDS = tag("achievement_sounds");
        public static final TagKey<SoundEvent> MUSIC = tag("music");
        public static final TagKey<SoundEvent> BOSS_MUSIC = tag("boss_music");

        private static TagKey<SoundEvent> tag(String name) {
            return TagKey.create(Registries.SOUND_EVENT, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }
}