package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.world.entity.player.Player;

public class GuidebookDiscoveryHooks {

    public static void onPlayerClone(Player player) {
        GuidebookDiscoveryAttachment attachment = player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
        attachment.clone(player);
    }


    public static void progressAdvancement(Player player, AdvancementHolder advancement) {
        GuidebookDiscoveryAttachment attachment = player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
        attachment.trackDiscoveries(player, advancement);
    }
}
