package com.aetherteam.aetherii;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class AetherIIConfig {
    public static class Server {
        public final ConfigValue<Boolean> disable_aether_portal;
        public final ConfigValue<String> portal_destination_dimension_ID;
        public final ConfigValue<String> portal_return_dimension_ID;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("Modpack");
            disable_aether_portal = builder
                    .comment("Prevents the Aether Portal from being created normally in the mod")
                    .translation("config.aether_ii.server.modpack.disable_aether_portal")
                    .define("Disables Aether Portal creation", false);
            portal_destination_dimension_ID = builder
                    .comment("Sets the ID of the dimension that the Aether Portal will send the player to")
                    .translation("config.aether_ii.server.modpack.portal_destination_dimension_ID")
                    .define("Sets portal destination dimension", AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL.location().toString());
            portal_return_dimension_ID = builder
                    .comment("Sets the ID of the dimension that the Aether Portal will return the player to")
                    .translation("config.aether_ii.server.modpack.portal_return_dimension_ID")
                    .define("Sets portal return dimension", Level.OVERWORLD.location().toString());
            builder.pop();
        }
    }

    public static class Common {
        public final ConfigValue<Boolean> start_with_portal;
        public final ConfigValue<Boolean> spawn_in_aether;
        public final ConfigValue<Boolean> show_alpha_message;
        public final ConfigValue<Boolean> yellow_alpha_button;
        public final ConfigValue<Boolean> experimental_dungeon_content;
        public final ConfigValue<Boolean> use_quartz_aether_portal_frame;
        public final ConfigValue<Boolean> allow_vanilla_equipment_in_aether;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Gameplay");
            start_with_portal = builder
                    .comment("On world creation, the player is given an Aether Portal Frame item to automatically go to the Aether with")
                    .translation("config.aether_ii.common.gameplay.start_with_portal")
                    .define("Gives player Aether Portal Frame item", false);
            spawn_in_aether = builder
                    .comment("Spawns the player in the Aether dimension; this is best enabled alongside other modpack configuration to avoid issues")
                    .translation("config.aether_ii.common.gameplay.spawn_in_aether")
                    .define("Spawns the player in the Aether", false);
            show_alpha_message = builder
                    .comment("Displays info about the Aether II's alpha on world join (goes away after the first time a world is join in a modded instance)")
                    .translation("config.aether_ii.common.gameplay.show_alpha_message")
                    .define("Alpha Message", true);
            yellow_alpha_button = builder
                    .comment("Makes the alpha info button in the Guidebook have a yellow icon to make it stand out (turns to white after the first time its clicked)")
                    .translation("config.aether_ii.common.gameplay.yellow_alpha_button")
                    .define("Yellow Alpha Button", true);
            experimental_dungeon_content = builder
                    .comment("Enables currently disabled Infected Guardian Tree content. At the moment this only includes enabling the dungeon's blocks in the creative inventory")
                    .translation("config.aether_ii.common.gameplay.experimental_dungeon_content")
                    .define("Enables experimental dungeon content", false);
            builder.pop();

            builder.push("Modpack");
            use_quartz_aether_portal_frame = builder
                    .comment("When enabled, Aether portals use quartz frames instead of the original glowstone frame. Default false matches 26.1.2.")
                    .translation("config.aether_ii.common.modpack.use_quartz_aether_portal_frame")
                    .define("Use quartz Aether Portal frame", false);
            allow_vanilla_equipment_in_aether = builder
                    .comment("When enabled, non-Aether weapons, armor, and tools are not penalized by Aether II's dimension equipment rules. Default false matches 26.1.2.")
                    .translation("config.aether_ii.common.modpack.allow_vanilla_equipment_in_aether")
                    .define("Allow vanilla equipment in Aether", false);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;


    static {
        final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();

        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }
}
