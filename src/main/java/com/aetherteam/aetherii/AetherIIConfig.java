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

    public static class Client {
        public final ConfigValue<Integer> music_backup_min_delay;
        public final ConfigValue<Integer> music_backup_max_delay;
        public final ConfigValue<Boolean> disable_music_manager;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("Audio");
            music_backup_min_delay = builder
                    .comment("Sets the minimum delay for the Aether's music manager to use if needing to reset the song delay outside the Aether")
                    .translation("config.aether.client.audio.music_backup_min_delay")
                    .define("Set backup minimum music delay", 12000);
            music_backup_max_delay = builder
                    .comment("Sets the maximum delay for the Aether's music manager to use if needing to reset the song delay outside the Aether")
                    .translation("config.aether.client.audio.music_backup_max_delay")
                    .define("Set backup maximum music delay", 24000);
            disable_music_manager = builder
                    .comment("Disables the Aether's internal music manager, if true, this overrides all other audio configs")
                    .translation("config.aether.client.audio.disable_music_manager")
                    .define("Disables Aether music manager", false);
            builder.pop();

        }
    }

    public static final ModConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Server, ModConfigSpec> serverSpecPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();

        final Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<Client, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}