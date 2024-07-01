package com.aetherteam.aetherii;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class AetherIITags {
    public static class Blocks {
        public static final TagKey<Block> AETHER_PORTAL_BLOCKS = tag("aether_portal_blocks");
        public static final TagKey<Block> AETHER_PORTAL_BLACKLIST = tag("aether_portal_blacklist");
        public static final TagKey<Block> AETHER_DIRT = tag("aether_dirt");
        public static final TagKey<Block> HOLYSTONE = tag("holystone");
        public static final TagKey<Block> AETHER_UNDERGROUND_BLOCKS = tag("aether_underground_blocks");
        public static final TagKey<Block> AETHER_CARVER_REPLACEABLES = tag("aether_carver_replaceables");
        public static final TagKey<Block> FERROSITE = tag("ferrosite");
        public static final TagKey<Block> AERCLOUDS = tag("aerclouds");
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

        public static final TagKey<Block> AETHER_ANIMALS_SPAWNABLE_ON = tag("aether_animal_spawnable_on");
        public static final TagKey<Block> COCKATRICE_SPAWNABLE_BLACKLIST = tag("cockatrice_spawnable_blacklist");
        public static final TagKey<Block> MOA_HATCH_BLOCK = tag("moa_hatch_block");

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
        public static final TagKey<Item> CLOUDWOOL = tag("cloudwool");

        public static final TagKey<Item> RODS_SKYROOT = tag("rods/skyroot");
        public static final TagKey<Item> GEMS_ZANITE = tag("gems/zanite");
        public static final TagKey<Item> PLATES_ARKENIUM = tag("plates/arkenium");
        public static final TagKey<Item> PLATES_GRAVITITE = tag("plates/gravitite");

        public static final TagKey<Item> TOOLS_SHORTSWORDS = tag("tools/shortswords");
        public static final TagKey<Item> TOOLS_HAMMERS = tag("tools/hammers");
        public static final TagKey<Item> TOOLS_SPEARS = tag("tools/spears");

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

        public static final TagKey<Item> TAEGORE_HIDE_REPAIRING = tag("taegore_hide_repairing");
        public static final TagKey<Item> BURRUKAI_PELT_REPAIRING = tag("burrukai_pelt_repairing");
        public static final TagKey<Item> SKYROOT_REPAIRING = tag("skyroot_repairing");
        public static final TagKey<Item> HOLYSTONE_REPAIRING = tag("holystone_repairing");
        public static final TagKey<Item> ZANITE_REPAIRING = tag("zanite_repairing");
        public static final TagKey<Item> ARKENIUM_REPAIRING = tag("arkenium_repairing");
        public static final TagKey<Item> GRAVITITE_REPAIRING = tag("gravitite_repairing");

        public static final TagKey<Item> AERBUNNY_TEMPTATION_ITEMS = tag("aerbunny_temptation_items");
        public static final TagKey<Item> PHYG_TEMPTATION_ITEMS = tag("phyg_temptation_items");
        public static final TagKey<Item> PHYG_CALM_ITEMS = tag("phyg_calm_items");
        public static final TagKey<Item> KIRRID_TEMPTATION_ITEMS = tag("kirrid_temptation_items");
        public static final TagKey<Item> BURRUKAI_TEMPTATION_ITEMS = tag("burrukai_temptation_items");
        public static final TagKey<Item> FLYING_COW_TEMPTATION_ITEMS = tag("flying_cow_temptation_items");
        public static final TagKey<Item> SHEEPUFF_TEMPTATION_ITEMS = tag("sheepuff_temptation_items");
        public static final TagKey<Item> MOA_FOOD_ITEMS = tag("moa_food_items");

        public static final TagKey<Item> AETHER_PORTAL_ACTIVATION_ITEMS = tag("aether_portal_activation_items");
        public static final TagKey<Item> SWET_GEL = tag("swet_gel");
        public static final TagKey<Item> GOLDEN_AMBER_HARVESTERS = tag("golden_amber_harvesters");
        public static final TagKey<Item> DOUBLE_DROPS = tag("double_drops");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> BURRUKAI = tag("burrukai");
        public static final TagKey<EntityType<?>> KIRRID = tag("kirrid");

        public static final TagKey<EntityType<?>> NO_DOUBLE_DROPS = tag("no_double_drops");
        public static final TagKey<EntityType<?>> NO_AMBROSIUM_DROPS = tag("no_ambrosium_drops");

        public static final TagKey<EntityType<?>> SPAWNING_ICE = tag("spawning/ice");
        public static final TagKey<EntityType<?>> SPAWNING_AERCLOUDS = tag("spawning/aerclouds");
        public static final TagKey<EntityType<?>> SPAWNING_LEAVES = tag("spawning/leaves");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_STRUCTURE_OUTPOST = tag("has_structure/outpost");

        public static final TagKey<Biome> MYCELIUM_CONVERSION = tag("mycelium_conversion");
        public static final TagKey<Biome> PODZOL_CONVERSION = tag("podzol_conversion");
        public static final TagKey<Biome> CRIMSON_NYLIUM_CONVERSION = tag("crimson_nylium_conversion");
        public static final TagKey<Biome> WARPED_NYLIUM_CONVERSION = tag("warped_nylium_conversion");

        public static final TagKey<Biome> ARCTIC_ICE = tag("arctic_ice");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> TYPED = tag("typed");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
        }
    }
}