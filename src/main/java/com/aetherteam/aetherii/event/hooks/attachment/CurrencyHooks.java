package com.aetherteam.aetherii.event.hooks.attachment;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class CurrencyHooks {
    public static void onPlayerJoinLevel(Entity entity) {
        if (entity instanceof Player player) {
            var currencyAttachment = player.getData(AetherIIDataAttachments.CURRENCY);
            currencyAttachment.onJoinLevel();
        }
    }

    public static void onUpdate(Player player) {
        var currencyAttachment = player.getData(AetherIIDataAttachments.CURRENCY);
        currencyAttachment.onUpdate(player);
    }
}
