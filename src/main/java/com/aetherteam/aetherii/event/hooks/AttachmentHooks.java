package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.advancements.AdvancementHolder;
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
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).respawn(player);
    }

    public static void playerClone(Player player) {
        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).clone(player);
    }

    public static void playerPostTickUpdate(Player player) {
        player.getData(AetherIIDataAttachments.PLAYER).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.CURRENCY).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.SWET).postTickUpdate(player);
    }

    public static void playerProgressAdvancement(Player player, AdvancementHolder advancement) {
        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).progressAdvancement(player, advancement);
    }

    public static void livingPostTickUpdate(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).postTickUpdate(livingEntity);
        }
    }
}
