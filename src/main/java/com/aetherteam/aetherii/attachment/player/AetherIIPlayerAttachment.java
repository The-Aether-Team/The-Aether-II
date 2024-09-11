package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.portal.PortalSoundUtil;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.consumables.HealingStoneItem;
import com.aetherteam.aetherii.network.packet.AetherIIPlayerSyncPacket;
import com.aetherteam.aetherii.network.packet.clientbound.RemountAerbunnyPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AetherIIPlayerAttachment implements INBTSynchable {
    private boolean isMoving;
    private boolean isJumping;

    private boolean canGetPortal = true;
    private boolean canSpawnInAether = true;

    public float portalIntensity;
    public float oPortalIntensity;

    @Nullable
    private Aerbunny mountedAerbunny;
    private Optional<CompoundTag> mountedAerbunnyTag = Optional.empty();

    private boolean gravititeHoldingFloatingBlock = false;
    private boolean gravititeJumpUsed = true;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setMoving", Triple.of(Type.BOOLEAN, (object) -> this.setMoving((boolean) object), this::isMoving)),
            Map.entry("setJumping", Triple.of(Type.BOOLEAN, (object) -> this.setJumping((boolean) object), this::isJumping))
    );

    public static final Codec<AetherIIPlayerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("can_get_portal").forGetter(AetherIIPlayerAttachment::canGetPortal),
            Codec.BOOL.fieldOf("can_spawn_in_aether").forGetter(AetherIIPlayerAttachment::canSpawnInAether),
            CompoundTag.CODEC.optionalFieldOf("mounted_aerbunny").forGetter(AetherIIPlayerAttachment::getMountedAerbunnyTag),
            Codec.BOOL.fieldOf("gravitite_holding_floating_block").forGetter(AetherIIPlayerAttachment::isGravititeHoldingFloatingBlock),
            Codec.BOOL.fieldOf("gravitite_jump_used").forGetter(AetherIIPlayerAttachment::isGravititeJumpUsed)
    ).apply(instance, AetherIIPlayerAttachment::new));

    private boolean shouldSyncAfterJoin;
    private boolean shouldSyncBetweenClients;

    protected AetherIIPlayerAttachment(boolean canGetPortal, boolean canSpawnInAether, Optional<CompoundTag> mountedAerbunnyTag, boolean gravititeHoldingFloatingBlock, boolean gravititeJumpUsed) {
        this.canGetPortal = canGetPortal;
        this.canSpawnInAether = canSpawnInAether;
        this.mountedAerbunnyTag = mountedAerbunnyTag;
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
        this.gravititeJumpUsed = gravititeJumpUsed;
    }

    public AetherIIPlayerAttachment() { }

    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    /**
     * Handles functions when the player logs out of a world from {@link net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent}.
     */
    public void logout(Player player) {
        this.removeAerbunny();
    }

    /**
     * Handles functions when the player logs in to a world from {@link net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent}.
     */
    public void login(Player player) {
        this.startInAether(player);
        this.remountAerbunny(player);
        this.shouldSyncAfterJoin = true;
    }

    /**
     * Handles functions when the player ticks from {@link net.neoforged.neoforge.event.entity.living.LivingEvent.LivingTickEvent}
     */
    public void postTickUpdate(Player player) {
        this.syncAfterJoin(player);
        this.syncClients(player);
        this.handleAetherPortal(player);
        this.handleHealingStoneHealth(player);
        this.checkToRemoveAerbunny(player);
    }

    private void syncAfterJoin(Player player) {
        if (this.shouldSyncAfterJoin) {
            this.forceSync(player.getId(), Direction.CLIENT);
            this.shouldSyncAfterJoin = false;
        }
    }

    private void syncClients(Player player) {
        if (this.shouldSyncBetweenClients()) {
            if (!player.level().isClientSide()) {
                MinecraftServer server = player.level().getServer();
                if (server != null) {
                    PlayerList playerList = server.getPlayerList();
                    for (ServerPlayer serverPlayer : playerList.getPlayers()) {
                        if (!serverPlayer.getUUID().equals(player.getUUID())) {
                            this.forceSync(player.getId(), Direction.CLIENT);
                        }
                    }
                }
            }
            this.setShouldSyncBetweenClients(false);
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
                        PortalSoundUtil.playTriggerSound();
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

    private void handleHealingStoneHealth(Player player) {
        if (player.getAttribute(Attributes.MAX_HEALTH).hasModifier(HealingStoneItem.BONUS_HEALTH)) {
            double maxHealthWithoutBonus = player.getAttributeValue(Attributes.MAX_HEALTH) - player.getAttribute(Attributes.MAX_HEALTH).getModifier(HealingStoneItem.BONUS_HEALTH).amount();
            if (player.getHealth() <= maxHealthWithoutBonus) {
                player.getAttribute(Attributes.MAX_HEALTH).removeModifier(HealingStoneItem.BONUS_HEALTH);
            }
        }
    }

    /**
     * Removes an Aerbunny from the world and stores it to NBT for the capability. This is used when a player logs out with an Aerbunny.
     */
    private void removeAerbunny() {
        if (this.getMountedAerbunny() != null) {
            Aerbunny aerbunny = this.getMountedAerbunny();
            CompoundTag nbt = new CompoundTag();
            aerbunny.save(nbt);
            this.setMountedAerbunnyTag(Optional.of(nbt));
            aerbunny.stopRiding();
            aerbunny.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
        }
    }

    /**
     * Remounts an Aerbunny to the player if there exists stored NBT when joining the world.
     */
    private void remountAerbunny(Player player) {
        if (this.getMountedAerbunnyTag().isPresent()) {
            if (!player.level().isClientSide()) {
                Aerbunny aerbunny = new Aerbunny(AetherIIEntityTypes.AERBUNNY.get(), player.level());
                aerbunny.load(this.getMountedAerbunnyTag().get());
                player.level().addFreshEntity(aerbunny);
                aerbunny.startRiding(player);
                this.setMountedAerbunny(aerbunny);
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayer(serverPlayer, new RemountAerbunnyPacket(player.getId(), aerbunny.getId()));
                }
            }
            this.setMountedAerbunnyTag(null);
        }
    }

    /**
     * Checks whether the capability should stop tracking a mounted Aerbunny.
     */
    private void checkToRemoveAerbunny(Player player) {
        if (this.getMountedAerbunny() != null && (!this.getMountedAerbunny().isAlive() || !player.isAlive())) {
            this.setMountedAerbunny(null);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void movementInput(Player player, Input input) {
        boolean isJumping = input.jumping;
        if (isJumping != this.isJumping()) {
            this.setSynched(player.getId(), INBTSynchable.Direction.SERVER, "setJumping", isJumping);
        }
        boolean isMoving = isJumping || input.up || input.down || input.left || input.right || player.isFallFlying();
        if (isMoving != this.isMoving()) {
            this.setSynched(player.getId(), INBTSynchable.Direction.SERVER, "setMoving", isMoving);
        }
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
     * Gives the player an Aether Portal Frame item.
     */
    private void givePortalItem(Player player) {
        if (this.canGetPortal()) {
            player.addItem(new ItemStack(AetherIIItems.AETHER_PORTAL_FRAME.get()));
            this.setCanGetPortal(false);
        }
    }

    public void startInAether(Player player) { //todo: port to new 1.21 portal system
//        var aetherIIPlayer = player.getData(AetherIIDataAttachments.PORTAL_TELEPORTATION);
//        if (AetherIIConfig.SERVER.spawn_in_aether.get()) {
//            if (aetherIIPlayer.canSpawnInAether()) { // Checks if the player has been set to spawn in the Aether.
//                if (player instanceof ServerPlayer serverPlayer) {
//                    MinecraftServer server = serverPlayer.level().getServer();
//                    if (server != null) {
//                        ServerLevel aetherLevel = server.getLevel(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL);
//                        if (aetherLevel != null && serverPlayer.level().dimension() != AetherIIDimensions.AETHER_HIGHLANDS_LEVEL) {
//                            if (player.changeDimension(aetherLevel, new AetherPortalForcer(aetherLevel, false, true)) != null) {
//                                serverPlayer.setRespawnPosition(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL, serverPlayer.blockPosition(), serverPlayer.getYRot(), true, false);
//                                aetherIIPlayer.setCanSpawnInAether(false); // Sets that the player has already spawned in the Aether.
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            aetherIIPlayer.setCanSpawnInAether(false);
//        }
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
     * @return Whether the player is moving, as a {@link Boolean}.
     */
    public boolean isMoving() {
        return this.isMoving;
    }

    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    /**
     * @return Whether the player is jumping, as a {@link Boolean}.
     */
    public boolean isJumping() {
        return this.isJumping;
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

    public void setCanSpawnInAether(boolean canSpawnInAether) {
        this.canSpawnInAether = canSpawnInAether;
    }

    /**
     * @return Whether the player will spawn in the Aether dimension on first join, as a {@link Boolean}.
     */
    public boolean canSpawnInAether() {
        return this.canSpawnInAether;
    }

    public float getPortalIntensity() {
        return this.portalIntensity;
    }

    public float getOldPortalIntensity() {
        return this.oPortalIntensity;
    }

    public void setMountedAerbunny(@Nullable Aerbunny mountedAerbunny) {
        this.mountedAerbunny = mountedAerbunny;
    }

    /**
     * @return The {@link Aerbunny} currently mounted to the player
     */
    @Nullable
    public Aerbunny getMountedAerbunny() {
        return this.mountedAerbunny;
    }

    public void setMountedAerbunnyTag(Optional<CompoundTag> mountedAerbunnyTag) {
        this.mountedAerbunnyTag = mountedAerbunnyTag;
    }

    /**
     * @return The {@link CompoundTag} data for the Aerbunny currently mounted to the player.
     */
    public Optional<CompoundTag> getMountedAerbunnyTag() {
        return this.mountedAerbunnyTag;
    }

    public void setGravititeHoldingFloatingBlock(boolean gravititeHoldingFloatingBlock) {
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
    }

    public boolean isGravititeHoldingFloatingBlock() {
        return this.gravititeHoldingFloatingBlock;
    }

    public void setGravititeJumpUsed(boolean gravititeJumpUsed) {
        this.gravititeJumpUsed = gravititeJumpUsed;
    }

    public boolean isGravititeJumpUsed() {
        return this.gravititeJumpUsed;
    }

    /**
     * @return Whether the capability should sync server values to nearby clients.
     */
    private boolean shouldSyncBetweenClients() {
        return this.shouldSyncBetweenClients;
    }

    private void setShouldSyncBetweenClients(boolean shouldSyncBetweenClients) {
        this.shouldSyncBetweenClients = shouldSyncBetweenClients;
    }

    @Override
    public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new AetherIIPlayerSyncPacket(entityID, key, type, value);
    }
}
