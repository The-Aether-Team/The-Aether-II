package com.aetherteam.aetherii.event.hooks.attachment;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.SwetAttachment;
import net.minecraft.world.entity.player.Player;

public class SwetHooks {
    public static void onUpdate(Player player) {
        SwetAttachment attachment = player.getData(AetherIIDataAttachments.SWET.get());
        attachment.tick();
    }
}
