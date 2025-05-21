package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.consumables.HealingStoneItem;
import com.aetherteam.aetherii.item.miscellaneous.glider.AercloudGliderItem;
import com.aetherteam.aetherii.network.packet.AbilityBehaviorSyncPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AbilityBehaviorAttachment implements INBTSynchable { //todo map of tracked slowfall functions to use for aerbunny and glider
    private boolean canRefuelGlide;
    private int glidingTimer;
    private Map<Holder<Item>, Boolean> canRefuelAbilities = new HashMap<>(Map.of(
            AetherIIItems.BLUE_AERCLOUD_GLIDER, false,
            AetherIIItems.PURPLE_AERCLOUD_GLIDER, false
    ));

    private boolean gravititeHoldingFloatingBlock = false;
    private boolean gravititeJumpUsed = true;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setGlidingTimer", Triple.of(Type.INT, (object) -> this.setGlidingTimer((int) object), this::getGlidingTimer)),
            Map.entry("setGravititeJumpUsed", Triple.of(Type.BOOLEAN, (object) -> this.setGravititeJumpUsed((boolean) object), this::isGravititeJumpUsed)),
            Map.entry("setShouldSyncBetweenClients", Triple.of(Type.BOOLEAN, (object) -> this.setShouldSyncBetweenClients((boolean) object), this::shouldSyncBetweenClients))
    );

    public static final Codec<AbilityBehaviorAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("can_refuel_glide").forGetter(AbilityBehaviorAttachment::getCanRefuelGlide),
            Codec.INT.fieldOf("gliding_timer").forGetter(AbilityBehaviorAttachment::getGlidingTimer),
            ExtraCodecs.strictUnboundedMap(BuiltInRegistries.ITEM.holderByNameCodec(), Codec.BOOL).fieldOf("can_refuel_abilities").forGetter(AbilityBehaviorAttachment::getCanRefuelAbilities),
            Codec.BOOL.fieldOf("gravitite_holding_floating_block").forGetter(AbilityBehaviorAttachment::isGravititeHoldingFloatingBlock),
            Codec.BOOL.fieldOf("gravitite_jump_used").forGetter(AbilityBehaviorAttachment::isGravititeJumpUsed)
    ).apply(instance, AbilityBehaviorAttachment::new));

    private boolean shouldSyncAfterJoin;
    private boolean shouldSyncBetweenClients;

    protected AbilityBehaviorAttachment(boolean canRefuelGlide, int glidingTimer, Map<Holder<Item>, Boolean> canRefuelAbilities, boolean gravititeHoldingFloatingBlock, boolean gravititeJumpUsed) {
        this.canRefuelGlide = canRefuelGlide;
        this.glidingTimer = glidingTimer;
        this.canRefuelAbilities =  new HashMap<>(canRefuelAbilities);
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
        this.gravititeJumpUsed = gravititeJumpUsed;
    }

    public AbilityBehaviorAttachment() { }

    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    public void login(Player player) {
        this.shouldSyncAfterJoin = true;
    }

    public void onJoinLevel(Player player) {
        if (player.level().isClientSide() && player.isLocalPlayer()) {
            this.setSynched(player.getId(), Direction.SERVER, "setShouldSyncBetweenClients", true);
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

    private void handleHealingStoneHealth(Player player) {
        if (player.getAttribute(Attributes.MAX_ABSORPTION).hasModifier(HealingStoneItem.BONUS_ABSORPTION)) {
            double maxHealthWithAbsorption = player.getMaxHealth() + player.getMaxAbsorption();
            double maxHealthWithoutBonus = maxHealthWithAbsorption - player.getAttribute(Attributes.MAX_ABSORPTION).getModifier(HealingStoneItem.BONUS_ABSORPTION).amount();
            if (player.getHealth() < maxHealthWithoutBonus) {
                player.getAttribute(Attributes.MAX_ABSORPTION).removeModifier(HealingStoneItem.BONUS_ABSORPTION);
            }
        }
    }

    private void resetGlideCheck(Player player) {
        if (player.onGround()) {
            if (!this.getCanRefuelGlide()) {
                this.setGlidingTimer(AercloudGliderItem.GLIDING_MAX);
                this.setCanRefuelGlide(true);
                for (Iterator<Map.Entry<Holder<Item>, Boolean>> iterator = this.getCanRefuelAbilities().entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<Holder<Item>, Boolean> entry = iterator.next();
                    this.getCanRefuelAbilities().put(entry.getKey(), true);
                }
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
        return new AbilityBehaviorSyncPacket(entityID, key, type, value);
    }
}
