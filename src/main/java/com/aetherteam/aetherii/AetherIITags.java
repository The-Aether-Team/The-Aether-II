package com.aetherteam.aetherii;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class AetherIITags {
    public static class Blocks {
        public static final TagKey<Block> AETHER_PORTAL_BLOCKS = tag("aether_portal_blocks");
        public static final TagKey<Block> AETHER_PORTAL_BLACKLIST = tag("aether_portal_blacklist");
        public static final TagKey<Block> AETHER_DIRT = tag("aether_dirt");
        public static final TagKey<Block> AETHER_ANIMALS_SPAWNABLE_ON = tag("aether_animal_spawnable_on");

        public static final TagKey<Block> HOLYSTONE = tag("holystone");
        public static final TagKey<Block> AERCLOUDS = tag("aerclouds");
        public static final TagKey<Block> SKYROOT_LOGS = tag("skyroot_logs");
        public static final TagKey<Block> GREATROOT_LOGS = tag("greatroot_logs");
        public static final TagKey<Block> WISPROOT_LOGS = tag("wisproot_logs");
        public static final TagKey<Block> AMBEROOT_LOGS = tag("amberoot_logs");
        public static final TagKey<Block> SKYROOT_MASONRY_BLOCKS = tag("skyroot_masonry_blocks");
        public static final TagKey<Block> GREATROOT_MASONRY_BLOCKS = tag("greatroot_masonry_blocks");
        public static final TagKey<Block> WISPROOT_MASONRY_BLOCKS = tag("wisproot_masonry_blocks");
        public static final TagKey<Block> COCKATRICE_SPAWNABLE_BLACKLIST = tag("cockatrice_spawnable_blacklist");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(AetherII.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> AETHER_DIRT = tag("aether_dirt");
        public static final TagKey<Item> HOLYSTONE = tag("holystone");
        public static final TagKey<Item> AERCLOUDS = tag("aerclouds");
        public static final TagKey<Item> SKYROOT_LOGS = tag("skyroot_logs");
        public static final TagKey<Item> GREATROOT_LOGS = tag("greatroot_logs");
        public static final TagKey<Item> WISPROOT_LOGS = tag("wisproot_logs");
        public static final TagKey<Item> AMBEROOT_LOGS = tag("amberoot_logs");
        public static final TagKey<Item> SKYROOT_MASONRY_BLOCKS = tag("skyroot_masonry_blocks");
        public static final TagKey<Item> GREATROOT_MASONRY_BLOCKS = tag("greatroot_masonry_blocks");
        public static final TagKey<Item> WISPROOT_MASONRY_BLOCKS = tag("wisproot_masonry_blocks");

        public static final TagKey<Item> RODS_SKYROOT = tag("rods/skyroot");
        public static final TagKey<Item> GEMS_ZANITE = tag("gems/zanite");
        public static final TagKey<Item> PLATES_ARKENIUM = tag("plates/arkenium");
        public static final TagKey<Item> PLATES_GRAVITITE = tag("plates/gravitite");

        public static final TagKey<Item> UNIQUE_TOOLTIP_COLOR = tag("unique_tooltip_color");

        public static final TagKey<Item> CRAFTS_SKYROOT_PLANKS = tag("crafts_skyroot_planks");
        public static final TagKey<Item> CRAFTS_GREATROOT_PLANKS = tag("crafts_greatroot_planks");
        public static final TagKey<Item> CRAFTS_WISPROOT_PLANKS = tag("crafts_wisproot_planks");
        public static final TagKey<Item> PLANKS_CRAFTING = tag("planks_crafting");
        public static final TagKey<Item> STONE_CRAFTING = tag("stone_crafting");
        public static final TagKey<Item> CRAFTS_SKYROOT_STICKS = tag("crafts_skyroot_sticks");
        public static final TagKey<Item> CRAFTS_SKYROOT_TOOLS = tag("crafts_skyroot_tools");
        public static final TagKey<Item> CRAFTS_HOLYSTONE_TOOLS = tag("crafts_holystone_tools");

        public static final TagKey<Item> SKYROOT_REPAIRING = tag("skyroot_repairing");
        public static final TagKey<Item> HOLYSTONE_REPAIRING = tag("holystone_repairing");
        public static final TagKey<Item> ZANITE_REPAIRING = tag("zanite_repairing");
        public static final TagKey<Item> ARKENIUM_REPAIRING = tag("arkenium_repairing");
        public static final TagKey<Item> GRAVITITE_REPAIRING = tag("gravitite_repairing");

        public static final TagKey<Item> AERBUNNY_TEMPTATION_ITEMS = tag("aerbunny_temptation_items");
        public static final TagKey<Item> PHYG_TEMPTATION_ITEMS = tag("phyg_temptation_items");
        public static final TagKey<Item> PHYG_CALM_ITEMS = tag("phyg_calm_items");
        public static final TagKey<Item> FLYING_COW_TEMPTATION_ITEMS = tag("flying_cow_temptation_items");


        public static final TagKey<Item> AETHER_PORTAL_ACTIVATION_ITEMS = tag("aether_portal_activation_items");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(AetherII.MODID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_STRUCTURE_OUTPOST = tag("has_structure/outpost");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(AetherII.MODID, name));
        }
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> TYPED = tag("typed");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AetherII.MODID, name));
        }
    }
}