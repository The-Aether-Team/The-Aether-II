package com.aetherteam.aetherii.block.portal;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;

public final class PortalSoundUtil {
    public static void playTravelSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.get(), Minecraft.getInstance().level.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
    }
    public static void playTriggerSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get(), Minecraft.getInstance().level.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
    }
}