package com.aetherteam.aetherii.block.portal;

import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.client.sound.instance.FadeOutSoundInstance;
import com.aetherteam.aetherii.client.sound.instance.PortalTriggerSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public final class PortalClientUtil {
    public static void handleAetherPortal(Player player, AetherIIPlayerAttachment attachment) {
        if (player instanceof LocalPlayer localPlayer) {
            if (!(Minecraft.getInstance().screen instanceof LevelLoadingScreen)) {
                attachment.oPortalIntensity = attachment.portalIntensity;
                float f = 0.0F;
                if (localPlayer.isInsidePortal && isInsideAetherPortal(localPlayer)) {
                    if (Minecraft.getInstance().screen != null
                            && !Minecraft.getInstance().screen.isPauseScreen()
                            && !(Minecraft.getInstance().screen instanceof DeathScreen)
                            && !(Minecraft.getInstance().screen instanceof WinScreen)) {
                        if (Minecraft.getInstance().screen instanceof AbstractContainerScreen) {
                            localPlayer.closeContainer();
                        }

                        Minecraft.getInstance().setScreen(null);
                    }

                    if (attachment.portalIntensity == 0.0F) {
                        playTriggerSound();
                    }

                    f = 0.0125F;
                    localPlayer.isInsidePortal = false;
                } else if (attachment.portalIntensity > 0.0F) {
                    f = -0.05F;
                }

                attachment.portalIntensity = Mth.clamp(attachment.portalIntensity + f, 0.0F, 1.0F);
            }
        }
    }

    private static boolean isInsideAetherPortal(LocalPlayer player) {
        BlockPos entrance = player.portalEntrancePos;
        return player.level().getBlockState(player.blockPosition()).is(AetherIIBlocks.AETHER_PORTAL.get())
                || entrance != null && player.level().getBlockState(entrance).is(AetherIIBlocks.AETHER_PORTAL.get());
    }

    public static void playAmbientSound(BlockPos pos) {
        Minecraft.getInstance().getSoundManager().play(new FadeOutSoundInstance(AetherIISoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.get(), SoundSource.BLOCKS, 0.5F, Minecraft.getInstance().level.getRandom().nextFloat() * 0.4F + 0.8F, RandomSource.create(Minecraft.getInstance().level.getRandom().nextLong()), false, 0, SoundInstance.Attenuation.LINEAR, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, false));
    }
    
    public static void playTriggerSound() {
        Minecraft.getInstance().getSoundManager().play(PortalTriggerSoundInstance.forLocalAmbience(Minecraft.getInstance().player, AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get(), Minecraft.getInstance().level.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
    }

    public static void playTravelSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.get(), Minecraft.getInstance().level.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
    }
}
