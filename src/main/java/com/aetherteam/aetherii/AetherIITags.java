package com.aetherteam.aetherii;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class AetherIITags {
    public static class Blocks {
        public static final TagKey<Block> SKYROOT_LOGS = tag("skyroot_logs");
        public static final TagKey<Block> GREATROOT_LOGS = tag("greatroot_logs");
        public static final TagKey<Block> WISPROOT_LOGS = tag("wisproot_logs");
        public static final TagKey<Block> AMBEROOT_LOGS = tag("amberoot_logs");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(AetherII.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> SKYROOT_LOGS = tag("skyroot_logs");
        public static final TagKey<Item> GREATROOT_LOGS = tag("greatroot_logs");
        public static final TagKey<Item> WISPROOT_LOGS = tag("wisproot_logs");
        public static final TagKey<Item> AMBEROOT_LOGS = tag("amberoot_logs");

        public static final TagKey<Item> RODS_SKYROOT = tag("rods/skyroot");
        public static final TagKey<Item> GEMS_ZANITE = tag("gems/zanite");
        public static final TagKey<Item> PLATES_ARKENIUM = tag("plates/arkenium");
        public static final TagKey<Item> PLATES_GRAVITITE = tag("plates/gravitite");

        public static final TagKey<Item> CRAFTS_SKYROOT_PLANKS = tag("crafts_skyroot_planks");
        public static final TagKey<Item> CRAFTS_GREATROOT_PLANKS = tag("crafts_greatroot_planks");
        public static final TagKey<Item> CRAFTS_WISPROOT_PLANKS = tag("crafts_wisproot_planks");
        public static final TagKey<Item> CRAFTS_SKYROOT_STICKS = tag("crafts_skyroot_sticks");
        public static final TagKey<Item> CRAFTS_SKYROOT_TOOLS = tag("crafts_skyroot_tools");
        public static final TagKey<Item> CRAFTS_HOLYSTONE_TOOLS = tag("crafts_holystone_tools");

        public static final TagKey<Item> SKYROOT_REPAIRING = tag("skyroot_repairing");
        public static final TagKey<Item> HOLYSTONE_REPAIRING = tag("holystone_repairing");
        public static final TagKey<Item> ZANITE_REPAIRING = tag("zanite_repairing");
        public static final TagKey<Item> ARKENIUM_REPAIRING = tag("arkenium_repairing");
        public static final TagKey<Item> GRAVITITE_REPAIRING = tag("gravitite_repairing");

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
}