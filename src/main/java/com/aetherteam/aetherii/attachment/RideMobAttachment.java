package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.aetherteam.aetherii.network.packet.RideMobSyncPacket;
import com.aetherteam.aetherii.network.packet.clientbound.RemountAerbunnyPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Capability class for handling {@link Player} behavior of riding mob for the Aether.
 *
 * @see com.aetherteam.aetherii.event.hooks.RideMobHooks
 */
public class RideMobAttachment implements INBTSynchable {
    private boolean isMoving;
    private boolean isJumping;

    @Nullable
    private Aerbunny mountedAerbunny;
    private Optional<CompoundTag> mountedAerbunnyTag = Optional.empty();

    private static final int FLIGHT_TIMER_MAX = 52;
    private static final float FLIGHT_MODIFIER_MAX = 15.0F;
    private int flightTimer;
    private float flightModifier = 1.0F;


    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setMoving", Triple.of(Type.BOOLEAN, (object) -> this.setMoving((boolean) object), this::isMoving)),
            Map.entry("setJumping", Triple.of(Type.BOOLEAN, (object) -> this.setJumping((boolean) object), this::isJumping))
    );
    private boolean shouldSyncAfterJoin;
    private boolean shouldSyncBetweenClients;

    public static final Codec<RideMobAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CompoundTag.CODEC.optionalFieldOf("mounted_aerbunny").forGetter(RideMobAttachment::getMountedAerbunnyTag)
    ).apply(instance, RideMobAttachment::new));

    public RideMobAttachment() {
    }

    public RideMobAttachment(Optional<CompoundTag> mountedAerbunnyTag) {
        this.setMountedAerbunnyTag(mountedAerbunnyTag);
    }

    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    /**
     * Handles functions when the player logs out of a world from {@link net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent}.
     */
    public void onLogout(Player player) {
        this.removeAerbunny();
    }

    /**
     * Handles functions when the player logs in to a world from {@link net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent}.
     */
    public void onLogin(Player player) {
        this.remountAerbunny(player);
        this.shouldSyncAfterJoin = true;
    }

    /**
     * Handles functions when the player ticks from {@link net.neoforged.neoforge.event.entity.living.LivingEvent.LivingTickEvent}
     */
    public void onUpdate(Player player) {
        this.syncAfterJoin(player);
        this.syncClients(player);
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
                            player.getData(AetherIIDataAttachments.RIDE_MOB).forceSync(player.getId(), Direction.CLIENT);
                        }
                    }
                }
            }
            this.setShouldSyncBetweenClients(false);
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
                    PacketRelay.sendToPlayer(new RemountAerbunnyPacket(player.getId(), aerbunny.getId()), serverPlayer);
                }
            }
            this.setMountedAerbunnyTag(null);
        }
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

    /**
     * @return An {@link Integer} for the maximum flight duration.
     */
    public int getFlightTimerMax() {
        return FLIGHT_TIMER_MAX;
    }

    /**
     * @return A {@link Float} for the maximum flight speed modifier.
     */
    public float getFlightModifierMax() {
        return FLIGHT_MODIFIER_MAX;
    }

    public void setFlightTimer(int timer) {
        this.flightTimer = timer;
    }

    /**
     * @return The {@link Integer} timer for how long the player has to fly.
     */
    public int getFlightTimer() {
        return this.flightTimer;
    }

    public void setFlightModifier(float modifier) {
        this.flightModifier = modifier;
    }

    /**
     * @return The {@link Float} modifier for the player's flight speed.
     */
    public float getFlightModifier() {
        return this.flightModifier;
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
    public BasePacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new RideMobSyncPacket(entityID, key, type, value);
    }
}
