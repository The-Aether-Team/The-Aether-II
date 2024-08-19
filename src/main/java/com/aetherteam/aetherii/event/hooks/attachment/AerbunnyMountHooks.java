package com.aetherteam.aetherii.event.hooks.attachment;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.event.listeners.WorldInteractionListener;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class AerbunnyMountHooks {

    /**
     * Spawns the player in the Aether dimension if the {@link AetherIIConfig.Server#spawn_in_aether} config is enabled.
     *
     * @param player The {@link Player}.
     * @see WorldInteractionListener#onPlayerLogin(PlayerEvent.PlayerLoggedInEvent)
     */
    public static void onPlayerLogin(Player player) {
        var aetherIIPlayer = player.getData(AetherIIDataAttachments.PLAYER);
        aetherIIPlayer.onLogin(player);
    }

    public static void onPlayerLogout(Player player) {
        var aetherIIPlayer = player.getData(AetherIIDataAttachments.PLAYER);
        aetherIIPlayer.onLogout(player);
    }

    public static void onUpdate(Player player) {
        var aetherIIPlayer = player.getData(AetherIIDataAttachments.PLAYER);
        aetherIIPlayer.onUpdate(player);
    }
}
