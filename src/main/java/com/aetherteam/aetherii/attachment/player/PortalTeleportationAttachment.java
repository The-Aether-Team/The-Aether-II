package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.event.hooks.PortalTeleportationHooks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Capability class for handling portal and teleportation behavior for the Aether.
 *
 * @see PortalTeleportationHooks
 */
public class PortalTeleportationAttachment {
    private boolean canGetPortal = true;
    private boolean canSpawnInAether = true;

    public boolean isInAetherPortal = false;
    public int aetherPortalTimer = 0;
    public float prevPortalAnimTime, portalAnimTime = 0.0F;

    public static final Codec<PortalTeleportationAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("can_get_portal").forGetter(PortalTeleportationAttachment::canGetPortal),
            Codec.BOOL.fieldOf("can_spawn_in_aether").forGetter(PortalTeleportationAttachment::canSpawnInAether)
    ).apply(instance, PortalTeleportationAttachment::new));

    public PortalTeleportationAttachment() { }

    public PortalTeleportationAttachment(boolean portal, boolean spawnInAether) {
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
        if (player.level().isClientSide()) {
            this.prevPortalAnimTime = this.portalAnimTime;
            Minecraft minecraft = Minecraft.getInstance();
            if (this.isInAetherPortal) {
                if (minecraft.screen != null && !minecraft.screen.isPauseScreen()) {
                    if (minecraft.screen instanceof AbstractContainerScreen) {
                        player.closeContainer();
                    }
                    minecraft.setScreen(null);
                }

                if (this.getPortalAnimTime() == 0.0F) {
                    this.playPortalSound(minecraft, player);
                }
            }
        }

        if (this.isInPortal()) {
            ++this.aetherPortalTimer;
            if (player.level().isClientSide()) {
                this.portalAnimTime += 0.0125F;
                if (this.getPortalAnimTime() > 1.0F) {
                    this.portalAnimTime = 1.0F;
                }
            }
            this.isInAetherPortal = false;
        } else {
            if (player.level().isClientSide()) {
                if (this.getPortalAnimTime() > 0.0F) {
                    this.portalAnimTime -= 0.05F;
                }

                if (this.getPortalAnimTime() < 0.0F) {
                    this.portalAnimTime = 0.0F;
                }
            }
            if (this.getPortalTimer() > 0) {
                this.aetherPortalTimer -= 4;
            }
        }
    }

    /**
     * Plays the portal entry sound on the client.
     */
    @OnlyIn(Dist.CLIENT)
    private void playPortalSound(Minecraft minecraft, Player player) {
        minecraft.getSoundManager().play(SimpleSoundInstance.forLocalAmbience(AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get(), player.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
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

    public void setInPortal(boolean inPortal) {
        this.isInAetherPortal = inPortal;
    }

    /**
     * @return Whether the player is in an Aether Portal, as a {@link Boolean}.
     */
    public boolean isInPortal() {
        return this.isInAetherPortal;
    }

    public void setPortalTimer(int timer) {
        this.aetherPortalTimer = timer;
    }

    /**
     * @return The {@link Integer} timer for how long the player has stood in a portal.
     */
    public int getPortalTimer() {
        return this.aetherPortalTimer;
    }

    /**
     * @return The {@link Float} timer for the portal vignette animation time.
     */
    public float getPortalAnimTime() {
        return this.portalAnimTime;
    }

    /**
     * @return The previous {@link Float} for the portal animation timer.
     */
    public float getPrevPortalAnimTime() {
        return this.prevPortalAnimTime;
    }
}