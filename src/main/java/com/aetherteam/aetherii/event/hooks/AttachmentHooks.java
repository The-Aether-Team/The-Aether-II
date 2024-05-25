package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class AttachmentHooks {
    public static class AetherIIPlayerHooks {

        /**
         * @see com.aetherteam.aetherii.attachment.AetherIIPlayerAttachment#onUpdate(Player)
         * @see com.aetherteam.aetherii.event.listeners.attachment.AetherIIPlayerListener#onPlayerUpdate(LivingEvent.LivingTickEvent)
         */
        public static void update(LivingEntity entity) {
            if (entity instanceof Player player) {
                player.getData(AetherIIDataAttachments.AETHER_II_PLAYER).onUpdate(player);
            }
        }
    }
}