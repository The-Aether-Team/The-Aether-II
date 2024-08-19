package com.aetherteam.aetherii.client.event.hooks.attachment;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;

public class AerbunnyMountClientHooks {
    /**
     * Tracks whether the player is jumping or moving on the client to the {@link AerbunnyMountAttachment}.
     *
     * @param player The {@link Player}.
     * @param input  The {@link Input}.
     * @see com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener#onMove(MovementInputUpdateEvent)
     */
    public static void movementInput(Player player, Input input) {
        var aetherPlayer = player.getData(AetherIIDataAttachments.PLAYER);
        boolean isJumping = input.jumping;
        if (isJumping != aetherPlayer.isJumping()) {
            aetherPlayer.setSynched(player.getId(), INBTSynchable.Direction.SERVER, "setJumping", isJumping);
        }
        boolean isMoving = isJumping || input.up || input.down || input.left || input.right || player.isFallFlying();
        if (isMoving != aetherPlayer.isMoving()) {
            aetherPlayer.setSynched(player.getId(), INBTSynchable.Direction.SERVER, "setMoving", isMoving);
        }
    }
}
