package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AttachmentHooks {
    public static void playerLogin(Player player) {
        player.getData(AetherIIDataAttachments.PLAYER).login(player);
        player.getData(AetherIIDataAttachments.CURRENCY).login(player); //todo verify
        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).login(player);
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).login(player); //todo verify
    }

    public static void playerLogout(Player player) {
        player.getData(AetherIIDataAttachments.PLAYER).logout(player);
    }

    public static void playerRespawn(Player player) {
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).handleRespawn(player);
    }

    public static void playerPostTickUpdate(Player player) {
        player.getData(AetherIIDataAttachments.PLAYER).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.CURRENCY).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.SWET).playerPostTickUpdate(player);
    }

    public static void livingPostTickUpdate(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).postTickUpdate(livingEntity);
        }
    }
}
