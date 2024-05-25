package com.aetherteam.aetherii;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class AetherIIConfig {
    public static class Server {
        public final ConfigValue<Boolean> spawn_in_aether;
        public final ConfigValue<Boolean> disable_aether_portal;
        public final ConfigValue<String> portal_destination_dimension_ID;
        public final ConfigValue<String> portal_return_dimension_ID;

        public Server(ModConfigSpec.Builder builder) {
            builder.push("Modpack");
            spawn_in_aether = builder
                    .comment("Spawns the player in the Aether dimension; this is best enabled alongside other modpack configuration to avoid issues")
                    .translation("config.aether.server.modpack.spawn_in_aether")
                    .define("Spawns the player in the Aether", false);
            disable_aether_portal = builder
                    .comment("Prevents the Aether Portal from being created normally in the mod")
                    .translation("config.aether.server.modpack.disable_aether_portal")
                    .define("Disables Aether Portal creation", false);
            portal_destination_dimension_ID = builder
                    .comment("Sets the ID of the dimension that the Aether Portal will send the player to")
                    .translation("config.aether.server.modpack.portal_destination_dimension_ID")
                    .define("Sets portal destination dimension", AetherIIDimensions.AETHER_HIGHLANDS_LEVEL.location().toString());
            portal_return_dimension_ID = builder
                    .comment("Sets the ID of the dimension that the Aether Portal will return the player to")
                    .translation("config.aether.server.modpack.portal_return_dimension_ID")
                    .define("Sets portal return dimension", Level.OVERWORLD.location().toString());
            builder.pop();
        }
    }

    public static class Common {
        public final ConfigValue<Boolean> start_with_portal;

        public Common(ModConfigSpec.Builder builder) {
            builder.push("Gameplay");
            start_with_portal = builder
                    .comment("On world creation, the player is given an Aether Portal Frame item to automatically go to the Aether with")
                    .translation("config.aether.common.gameplay.start_with_portal")
                    .define("Gives player Aether Portal Frame item", false);
            builder.pop();
        }
    }

    public static final ModConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;


    static {
        final Pair<Server, ModConfigSpec> serverSpecPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();

        final Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }
}