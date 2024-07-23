package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Capability class for handling portal and teleportation behavior for the Aether.
 *
 * @see DimensionTeleportationHooks
 */
public class DimensionTeleportationAttachment {
    private boolean canGetPortal = true;
    private boolean canSpawnInAether = true;

    public float portalIntensity;
    public float oPortalIntensity;

    public static final Codec<DimensionTeleportationAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("can_get_portal").forGetter(DimensionTeleportationAttachment::canGetPortal),
            Codec.BOOL.fieldOf("can_spawn_in_aether").forGetter(DimensionTeleportationAttachment::canSpawnInAether)
    ).apply(instance, DimensionTeleportationAttachment::new));

    public DimensionTeleportationAttachment() { }

    public DimensionTeleportationAttachment(boolean portal, boolean spawnInAether) {
        this.setCanGetPortal(portal);
        this.setCanSpawnInAether(spawnInAether);
    }

    /**
     * Handles functions when the player ticks from {@link net.neoforged.neoforge.event.entity.living.LivingEvent.LivingTickEvent}
     */
    public void onUpdate(Player player) {
        this.handleAetherPortal(player);
    }

    public void setCanSpawnInAether(boolean canSpawnInAether) {
        this.canSpawnInAether = canSpawnInAether;
    }

    /**
     * @return Whether the player will spawn in the Aether dimension on first join, as a {@link Boolean}.
     */
    public boolean canSpawnInAether() {
        return this.canSpawnInAether;
    }

    /**
     * Gives the player an Aether Portal Frame item on login if the {@link AetherIIConfig.Common#start_with_portal} config is enabled.
     */
    private void handleGivePortal(Player player) {
        if (AetherIIConfig.COMMON.start_with_portal.get()) {
            this.givePortalItem(player);
        } else {
            this.setCanGetPortal(false);
        }
    }

    /**
     * Increments or decrements the Aether portal timer depending on if the player is inside an Aether portal.
     * On the client, this will also help to set the portal overlay.
     */
    private void handleAetherPortal(Player player) {
        if (player instanceof LocalPlayer localPlayer) {
            if (!(Minecraft.getInstance().screen instanceof ReceivingLevelScreen)) {
                this.oPortalIntensity = this.portalIntensity;
                float f = 0.0F;
                if (localPlayer.portalProcess != null && localPlayer.portalProcess.isInsidePortalThisTick() && localPlayer.portalProcess.isSamePortal(AetherIIBlocks.AETHER_PORTAL.get())) {
                    if (Minecraft.getInstance().screen != null
                            && !Minecraft.getInstance().screen.isPauseScreen()
                            && !(Minecraft.getInstance().screen instanceof DeathScreen)
                            && !(Minecraft.getInstance().screen instanceof WinScreen)) {
                        if (Minecraft.getInstance().screen instanceof AbstractContainerScreen) {
                            localPlayer.closeContainer();
                        }

                        Minecraft.getInstance().setScreen(null);
                    }

                    if (this.portalIntensity == 0.0F) {
                        Minecraft.getInstance()
                                .getSoundManager()
                                .play(SimpleSoundInstance.forLocalAmbience(AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get(), localPlayer.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
                    }

                    f = 0.0125F;
                    localPlayer.portalProcess.setAsInsidePortalThisTick(false);
                } else if (this.portalIntensity > 0.0F) {
                    f = -0.05F;
                }

                this.portalIntensity = Mth.clamp(this.portalIntensity + f, 0.0F, 1.0F);
            }
        }
    }

    /**
     * Gives the player an Aether Portal Frame item.
     */
    public void givePortalItem(Player player) {
        if (this.canGetPortal()) {
            player.addItem(new ItemStack(AetherIIItems.AETHER_PORTAL_FRAME.get()));
            this.setCanGetPortal(false);
        }
    }

    public void setCanGetPortal(boolean canGetPortal) {
        this.canGetPortal = canGetPortal;
    }

    /**
     * @return Whether the player can get the Aether Portal Frame item, as a {@link Boolean}.
     */
    public boolean canGetPortal() {
        return this.canGetPortal;
    }

    public float getPortalIntensity() {
        return this.portalIntensity;
    }

    public float getOldPortalIntensity() {
        return this.oPortalIntensity;
    }
}