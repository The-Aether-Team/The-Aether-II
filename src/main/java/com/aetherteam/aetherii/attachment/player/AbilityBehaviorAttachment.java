package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class AbilityBehaviorAttachment {
    private boolean canRefuelGlide;
    private int glidingTimer = -1;
    private Map<Holder<Item>, Boolean> canRefuelAbilities = new HashMap<>(Map.of(
            AetherIIItems.BLUE_AERCLOUD_GLIDER.get().builtInRegistryHolder(), false,
            AetherIIItems.PURPLE_AERCLOUD_GLIDER.get().builtInRegistryHolder(), false
    ));

    private boolean crossbowSpecial;

    private boolean canRefreshShiftingGlass;
    private int shiftingGlassBoostTime;

    private boolean gravititeHoldingFloatingBlock = false;
    private boolean gravititeJumpUsed = true;

    public static final MapCodec<AbilityBehaviorAttachment> CODEC = MapCodec.unit(new AbilityBehaviorAttachment());
    public static final StreamCodec<FriendlyByteBuf, AbilityBehaviorAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, AbilityBehaviorAttachment::getGlidingTimer,
            ByteBufCodecs.BOOL, AbilityBehaviorAttachment::isCanRefreshShiftingGlass,
            ByteBufCodecs.INT, AbilityBehaviorAttachment::getShiftingGlassBoostTime,
            ByteBufCodecs.BOOL, AbilityBehaviorAttachment::isCrossbowSpecial,
            ByteBufCodecs.BOOL, AbilityBehaviorAttachment::isGravititeHoldingFloatingBlock,
            ByteBufCodecs.BOOL, AbilityBehaviorAttachment::isGravititeJumpUsed,
            AbilityBehaviorAttachment::new);

    private boolean shouldSyncAfterJoin;
    private boolean shouldSyncBetweenClients;

    protected AbilityBehaviorAttachment(boolean canRefuelGlide, int glidingTimer, boolean canRefreshShiftingGlass, int shiftingGlassBoostTime, Map<Holder<Item>, Boolean> canRefuelAbilities, boolean crossbowSpecial, boolean gravititeHoldingFloatingBlock, boolean gravititeJumpUsed) {
        this.canRefuelGlide = canRefuelGlide;
        this.glidingTimer = glidingTimer;
        this.canRefreshShiftingGlass = canRefreshShiftingGlass;
        this.shiftingGlassBoostTime = shiftingGlassBoostTime;
        this.canRefuelAbilities =  new HashMap<>(canRefuelAbilities);
        this.crossbowSpecial = crossbowSpecial;
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
        this.gravititeJumpUsed = gravititeJumpUsed;
    }

    protected AbilityBehaviorAttachment(int glidingTimer, boolean canRefreshShiftingGlass, int shiftingGlassBoostTime, boolean crossbowSpecial, boolean gravititeHoldingFloatingBlock, boolean gravititeJumpUsed) {
        this.glidingTimer = glidingTimer;
        this.canRefreshShiftingGlass = canRefreshShiftingGlass;
        this.shiftingGlassBoostTime = shiftingGlassBoostTime;
        this.crossbowSpecial = crossbowSpecial;
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
        this.gravititeJumpUsed = gravititeJumpUsed;
    }

    public AbilityBehaviorAttachment() { }

    public void login(Player player) {
        this.shouldSyncAfterJoin = true;
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).gravititeHoldingFloatingBlock = false;
    }

    public void logout(Player player) {
    }

    public void onJoinLevel(Player player) {
        if (player.level().isClientSide() && player.isLocalPlayer()) {
        }
    }

    public void changeDimension(Player player) {
        this.shouldSyncAfterJoin = true;
    }

    public void postTickUpdate(Player player) {
        this.syncAfterJoin(player);
        this.syncClients(player);
        this.handleHealingStoneHealth(player);
        this.resetGlideCheck(player);
        this.tickShiftingGlassTimer(player);
    }

    private void syncAfterJoin(Player player) {
        if (this.shouldSyncAfterJoin) {
            AetherIIDataAttachments.sync(player, AetherIIDataAttachments.ABILITY_BEHAVIOR);
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
                            AetherIIDataAttachments.sync(player, AetherIIDataAttachments.ABILITY_BEHAVIOR);
                            break;
                        }
                    }
                }
            }
            this.setShouldSyncBetweenClients(false);
        }
    }

    private void handleHealingStoneHealth(Player player) {
    }

    private void resetGlideCheck(Player player) {
        if (player.onGround()) {
            if (!this.getCanRefuelGlide()) {
                this.setGlidingTimer(-1);
                this.setCanRefuelGlide(true);
                for (Iterator<Map.Entry<Holder<Item>, Boolean>> iterator = this.getCanRefuelAbilities().entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<Holder<Item>, Boolean> entry = iterator.next();
                    this.getCanRefuelAbilities().put(entry.getKey(), true);
                }
            }
        }
    }

    private void tickShiftingGlassTimer(Player player) {
        if (this.getShiftingGlassBoostTime() > 0) {
            if (!player.level().isClientSide()) {
                this.setShiftingGlassBoostTime(this.getShiftingGlassBoostTime() - 1);
                AetherIIDataAttachments.sync(player, AetherIIDataAttachments.ABILITY_BEHAVIOR);
            } else {
                if (player.tickCount % 2 == 0) {
                    Vec3 particleDirection = player.getDeltaMovement().reverse();
                    int particleCount = 3;
                    float interval = 1 / (float) particleCount;
                    double variance = player.getRandom().nextDouble() * 0.15F;
                    for (int i = 1; i < particleCount; i++) {
                        player.level().addParticle(AetherIIParticleTypes.GLASS_FEATHERS.get(),
                                player.getX() + particleDirection.x(),
                                player.getY() + ((player.getBbHeight() - 0.25F) * i * interval) + variance,
                                player.getZ() + particleDirection.z(),
                                particleDirection.x() * player.getRandom().nextFloat() * 1.25F,
                                0.0F,
                                particleDirection.z() * player.getRandom().nextFloat() * 1.25F);
                    }
                }
            }
        }
        if (!this.isCanRefreshShiftingGlass()) {
            if (!player.getAbilities().instabuild) {
                player.getCooldowns().addCooldown(AetherIIItems.SHIFTING_GLASS.get(), 25);
            }
            if (!player.level().isClientSide() && player.onGround()) {
                this.setCanRefreshShiftingGlass(true);
                AetherIIDataAttachments.sync(player, AetherIIDataAttachments.ABILITY_BEHAVIOR);
            }
        }
    }

    public void setCanRefuelGlide(boolean canRefuelGlide) {
        this.canRefuelGlide = canRefuelGlide;
    }

    public boolean getCanRefuelGlide() {
        return this.canRefuelGlide;
    }

    public void setGlidingTimer(int glidingTimer) {
        this.glidingTimer = glidingTimer;
    }

    public int getGlidingTimer() {
        return this.glidingTimer;
    }

    public Map<Holder<Item>, Boolean> getCanRefuelAbilities() {
        return this.canRefuelAbilities;
    }

    public void setCrossbowSpecial(boolean crossbowSpecial) {
        this.crossbowSpecial = crossbowSpecial;
    }

    public boolean isCrossbowSpecial() {
        return this.crossbowSpecial;
    }

    public void setCanRefreshShiftingGlass(boolean canRefreshShiftingGlass) {
        this.canRefreshShiftingGlass = canRefreshShiftingGlass;
    }

    public boolean isCanRefreshShiftingGlass() {
        return this.canRefreshShiftingGlass;
    }

    public void setShiftingGlassBoostTime(int shiftingGlassBoostTime) {
        this.shiftingGlassBoostTime = shiftingGlassBoostTime;
    }

    public int getShiftingGlassBoostTime() {
        return this.shiftingGlassBoostTime;
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
}
