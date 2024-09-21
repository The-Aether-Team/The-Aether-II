package com.aetherteam.aetherii;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;

public class AetherIITags {
    public static class Blocks {
        public static final TagKey<Block> AETHER_PORTAL_BLOCKS = tag("aether_portal_blocks");
        public static final TagKey<Block> AETHER_PORTAL_BLACKLIST = tag("aether_portal_blacklist");
        public static final TagKey<Block> AETHER_DIRT = tag("aether_dirt");
        public static final TagKey<Block> HOLYSTONE = tag("holystone");
        public static final TagKey<Block> AETHER_UNDERGROUND_BLOCKS = tag("aether_underground_blocks");
        public static final TagKey<Block> AETHER_CARVER_REPLACEABLES = tag("aether_carver_replaceables");
        public static final TagKey<Block> SHAPES_COASTS = tag("shapes_coasts");
        public static final TagKey<Block> FERROSITE = tag("ferrosite");
        public static final TagKey<Block> AERCLOUDS = tag("aerclouds");
        public static final TagKey<Block> CLOUDWOOL = tag("cloudwool");
        public static final TagKey<Block> SKYROOT_LOGS = tag("skyroot_logs");
        public static final TagKey<Block> GREATROOT_LOGS = tag("greatroot_logs");
        public static final TagKey<Block> WISPROOT_LOGS = tag("wisproot_logs");
        public static final TagKey<Block> AMBEROOT_LOGS = tag("amberoot_logs");
        public static final TagKey<Block> SKYROOT_DECORATIVE_BLOCKS = tag("skyroot_decorative_blocks");
        public static final TagKey<Block> GREATROOT_DECORATIVE_BLOCKS = tag("greatroot_decorative_blocks");
        public static final TagKey<Block> WISPROOT_DECORATIVE_BLOCKS = tag("wisproot_decorative_blocks");
        public static final TagKey<Block> HOLYSTONE_DECORATIVE_BLOCKS = tag("holystone_decorative_blocks");
        public static final TagKey<Block> FADED_HOLYSTONE_DECORATIVE_BLOCKS = tag("faded_holystone_decorative_blocks");
        public static final TagKey<Block> AGIOSITE_DECORATIVE_BLOCKS = tag("agiosite_decorative_blocks");
        public static final TagKey<Block> ICESTONE_DECORATIVE_BLOCKS = tag("icestone_decorative_blocks");
        public static final TagKey<Block> ALLOWED_BUCKET_PICKUP = tag("allowed_bucket_pickup");

        public static final TagKey<Block> AETHER_PLANT_SURVIVES_ON = tag("aether_plant_survives_on");
        public static final TagKey<Block> SKYROOT_TWIG_SURVIVES_ON = tag("skyroot_twig_survives_on");
        public static final TagKey<Block> HOLYSTONE_ROCK_SURVIVES_ON = tag("holystone_rock_survives_on");
        public static final TagKey<Block> BOULDER_SURVIVES_ON = tag("boulder_survives_on");
        public static final TagKey<Block> FALLEN_LOG_SURVIVES_ON = tag("fallen_log_survives_on");
        public static final TagKey<Block> BRETTL_PLANT_SURVIVES_ON = tag("brettl_plant_survives_on");
        public static final TagKey<Block> SKY_ROOTS_SURVIVES_ON = tag("sky_roots_survives_on");
        public static final TagKey<Block> ICE_CRYSTAL_SURVIVES_ON = tag("ice_crystal_survives_on");
        public static final TagKey<Block> ARCTIC_TREE_SURVIVES_ON = tag("arctic_tree_survives_on");
        public static final TagKey<Block> GRASS_AND_DIRT_REPLACEABLE = tag("grass_and_dirt_replaceable");
        public static final TagKey<Block> COARSE_AETHER_DIRT_REPLACEABLE = tag("coarse_aether_dirt_replaceable");
        public static final TagKey<Block> ARCTIC_ICE_REPLACEABLE = tag("arctic_ice_replaceable");
        public static final TagKey<Block> GRASS_SNOW_REPLACEABLE = tag("grass_snow_replaceable");
        public static final TagKey<Block> QUICKSOIL_COAST_GENERATES_ON = tag("quicksoil_coast_generates_on");
        public static final TagKey<Block> FERROSITE_COAST_GENERATES_ON = tag("ferrosite_coast_generates_on");
        public static final TagKey<Block> ARCTIC_COAST_GENERATES_ON = tag("arctic_coast_generates_on");
        public static final TagKey<Block> FERROSITE_PILLAR_GENERATES_ON = tag("ferrosite_pillar_generates_on");
        public static final TagKey<Block> FERROSITE_SPIKE_GENERATES_ON = tag("ferrosite_spike_generates_on");
        public static final TagKey<Block> ARCTIC_ICE_SPIKE_GENERATES_ON = tag("ferrosite_spike_generates_on");

        public static final TagKey<Block> AETHER_ANIMALS_SPAWNABLE_ON = tag("aether_animal_spawnable_on");
        public static final TagKey<Block> AECHOR_PLANT_SPAWNABLE_ON = tag("aechor_plant_spawnable_on");
        public static final TagKey<Block> COCKATRICE_SPAWNABLE_BLACKLIST = tag("cockatrice_spawnable_blacklist");
        public static final TagKey<Block> SWET_SPAWNABLE_ON = tag("swet_spawnable_on");

        public static final TagKey<Block> INFECTED_GUARDIAN_TREE_REPLACEABLES = tag("infected_guardian_tree_replaceables");

        public static final TagKey<Block> MOA_HATCH_BLOCK = tag("moa_hatch_block");

        public static final TagKey<Block> HOLYSTONE_ABILITY_GUARANTEED = tag("holystone_ability_guaranteed");
        public static final TagKey<Block> GRAVITITE_ABILITY_BLACKLIST = tag("gravitite_ability_blacklist");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> AETHER_DIRT = tag("aether_dirt");
        public static final TagKey<Item> HOLYSTONE = tag("holystone");
        public static final TagKey<Item> FERROSITE = tag("ferrosite");
        public static final TagKey<Item> AERCLOUDS = tag("aerclouds");
        public static final TagKey<Item> CLOUDWOOL = tag("cloudwool");
        public static final TagKey<Item> SKYROOT_LOGS = tag("skyroot_logs");
        public static final TagKey<Item> GREATROOT_LOGS = tag("greatroot_logs");
        public static final TagKey<Item> WISPROOT_LOGS = tag("wisproot_logs");
        public static final TagKey<Item> AMBEROOT_LOGS = tag("amberoot_logs");
        public static final TagKey<Item> SKYROOT_DECORATIVE_BLOCKS = tag("skyroot_decorative_blocks");
        public static final TagKey<Item> GREATROOT_DECORATIVE_BLOCKS = tag("greatroot_decorative_blocks");
        public static final TagKey<Item> WISPROOT_DECORATIVE_BLOCKS = tag("wisproot_decorative_blocks");
        public static final TagKey<Item> HOLYSTONE_DECORATIVE_BLOCKS = tag("holystone_decorative_blocks");
        public static final TagKey<Item> FADED_HOLYSTONE_DECORATIVE_BLOCKS = tag("faded_holystone_decorative_blocks");
        public static final TagKey<Item> AGIOSITE_DECORATIVE_BLOCKS = tag("agiosite_decorative_blocks");
        public static final TagKey<Item> ICESTONE_DECORATIVE_BLOCKS = tag("icestone_decorative_blocks");

        public static final TagKey<Item> RODS_SKYROOT = tag("rods/skyroot");
        public static final TagKey<Item> GEMS_ZANITE = tag("gems/zanite");
        public static final TagKey<Item> PLATES_ARKENIUM = tag("plates/arkenium");
        public static final TagKey<Item> PLATES_GRAVITITE = tag("plates/gravitite");

        public static final TagKey<Item> TOOLS_TROWELS = tag("tools/trowels");
        public static final TagKey<Item> TOOLS_SHORTSWORDS = tag("tools/shortswords");
        public static final TagKey<Item> TOOLS_HAMMERS = tag("tools/hammers");
        public static final TagKey<Item> TOOLS_SPEARS = tag("tools/spears");

        public static final TagKey<Item> EQUIPMENT_RELICS = tag("equipment/relics");
        public static final TagKey<Item> EQUIPMENT_HANDWEAR = tag("equipment/handwear");
        public static final TagKey<Item> EQUIPMENT_ACCESSORIES = tag("equipment/accessories");
        public static final TagKey<Item> EQUIPABLE = tag("equipable");

        public static final TagKey<Item> UNIQUE_TOOLTIP_COLOR = tag("unique_tooltip_color");

        public static final TagKey<Item> CRAFTS_SKYROOT_PLANKS = tag("crafts_skyroot_planks");
        public static final TagKey<Item> CRAFTS_GREATROOT_PLANKS = tag("crafts_greatroot_planks");
        public static final TagKey<Item> CRAFTS_WISPROOT_PLANKS = tag("crafts_wisproot_planks");
        public static final TagKey<Item> PLANKS_CRAFTING = tag("planks_crafting");
        public static final TagKey<Item> STONE_CRAFTING = tag("stone_crafting");
        public static final TagKey<Item> CRAFTS_SKYROOT_STICKS = tag("crafts_skyroot_sticks");
        public static final TagKey<Item> CRAFTS_SKYROOT_TOOLS = tag("crafts_skyroot_tools");
        public static final TagKey<Item> CRAFTS_HOLYSTONE_TOOLS = tag("crafts_holystone_tools");
        public static final TagKey<Item> ALTAR_FUEL = tag("altar_fuel");
        public static final TagKey<Item> CAN_BE_REINFORCED = tag("can_be_reinforced");
        public static final TagKey<Item> FORGE_PRIMARY_MATERIAL = tag("forge_primary_material");
        public static final TagKey<Item> FORGE_SECONDARY_MATERIAL = tag("forge_secondary_material");

        public static final TagKey<Item> TAEGORE_HIDE_REPAIRING = tag("taegore_hide_repairing");
        public static final TagKey<Item> BURRUKAI_PELT_REPAIRING = tag("burrukai_pelt_repairing");
        public static final TagKey<Item> SKYROOT_REPAIRING = tag("skyroot_repairing");
        public static final TagKey<Item> HOLYSTONE_REPAIRING = tag("holystone_repairing");
        public static final TagKey<Item> ZANITE_REPAIRING = tag("zanite_repairing");
        public static final TagKey<Item> ARKENIUM_REPAIRING = tag("arkenium_repairing");
        public static final TagKey<Item> GRAVITITE_REPAIRING = tag("gravitite_repairing");

        public static final TagKey<Item> AERBUNNY_FOOD = tag("aerbunny_food");
        public static final TagKey<Item> PHYG_FOOD = tag("phyg_food");
        public static final TagKey<Item> PHYG_CALM_ITEMS = tag("phyg_calm_items");
        public static final TagKey<Item> FLYING_COW_FOOD = tag("flying_food");
        public static final TagKey<Item> SHEEPUFF_FOOD = tag("sheepuff_food");
        public static final TagKey<Item> TAEGORE_FOOD = tag("taegore_food");
        public static final TagKey<Item> BURRUKAI_FOOD = tag("burrukai_food");
        public static final TagKey<Item> KIRRID_FOOD = tag("kirrid_food");
        public static final TagKey<Item> MOA_FOOD = tag("moa_food");

        public static final TagKey<Item> AETHER_PORTAL_ACTIVATION_ITEMS = tag("aether_portal_activation_items");
        public static final TagKey<Item> SWET_GEL = tag("swet_gel");
        public static final TagKey<Item> GOLDEN_AMBER_HARVESTERS = tag("golden_amber_harvesters");
        public static final TagKey<Item> DOUBLE_DROPS = tag("double_drops");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> TAEGORE = tag("taegore");
        public static final TagKey<EntityType<?>> BURRUKAI = tag("burrukai");
        public static final TagKey<EntityType<?>> KIRRID = tag("kirrid");

        public static final TagKey<EntityType<?>> AETHER_MOBS = tag("aether_mobs");
        public static final TagKey<EntityType<?>> NO_DOUBLE_DROPS = tag("no_double_drops");
        public static final TagKey<EntityType<?>> NO_AMBROSIUM_DROPS = tag("no_ambrosium_drops");

        public static final TagKey<EntityType<?>> SPAWNING_ICE = tag("spawning/ice");
        public static final TagKey<EntityType<?>> SPAWNING_AERCLOUDS = tag("spawning/aerclouds");
        public static final TagKey<EntityType<?>> SPAWNING_LEAVES = tag("spawning/leaves");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> ALLOWED_BUCKET_PICKUP = tag("allowed_bucket_pickup");

        private static TagKey<Fluid> tag(String name) {
            return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HIGHFIELDS = tag("highfields");
        public static final TagKey<Biome> MAGNETIC = tag("magnetic");
        public static final TagKey<Biome> ARCTIC = tag("arctic");
        public static final TagKey<Biome> IRRADIATED = tag("irradiated");
        public static final TagKey<Biome> EXPANSE = tag("expanse");

        public static final TagKey<Biome> HAS_STRUCTURE_OUTPOST = tag("has_structure/outpost");
        public static final TagKey<Biome> HAS_STRUCTURE_CAMP_HIGHFIELDS = tag("has_structure/camp_highfields");
        public static final TagKey<Biome> HAS_STRUCTURE_INFECTED_GUARDIAN_TREE = tag("has_structure/infected_guardian_tree");

        public static final TagKey<Biome> MYCELIUM_CONVERSION = tag("mycelium_conversion");
        public static final TagKey<Biome> PODZOL_CONVERSION = tag("podzol_conversion");
        public static final TagKey<Biome> CRIMSON_NYLIUM_CONVERSION = tag("crimson_nylium_conversion");
        public static final TagKey<Biome> WARPED_NYLIUM_CONVERSION = tag("warped_nylium_conversion");

        public static final TagKey<Biome> ARCTIC_ICE = tag("arctic_ice");

        public static final TagKey<Biome> AETHER_MUSIC = tag("aether_music");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Structures {
        public static final TagKey<Structure> STRUCTURE_BLACKLIST_FILTER = tag("structure_blacklist_filter");

        private static TagKey<Structure> tag(String name) {
            return TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> TYPED = tag("typed");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }
}