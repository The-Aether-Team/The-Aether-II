package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.companion.Companion;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.consumables.HealingStoneItem;
import com.aetherteam.aetherii.item.equipment.accessories.companions.CompanionAccessory;
import com.aetherteam.aetherii.item.miscellaneous.glider.AercloudGliderItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.*;
import java.util.function.Predicate;

public class AbilityBehaviorAttachment {
    private boolean canRefuelGlide;
    private int glidingTimer;
    private Map<Holder<Item>, Boolean> canRefuelAbilities = new HashMap<>(Map.of(
            AetherIIItems.BLUE_AERCLOUD_GLIDER, false,
            AetherIIItems.PURPLE_AERCLOUD_GLIDER, false
    ));

    private boolean gravititeHoldingFloatingBlock = false;
    private boolean gravititeJumpUsed = true;

    private double neptuneSubmergeLength;

    private List<Entity> companions = new ArrayList<>();

//    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
//            Map.entry("setGlidingTimer", Triple.of(Type.INT, (object) -> this.setGlidingTimer((int) object), this::getGlidingTimer)),
//            Map.entry("setGravititeJumpUsed", Triple.of(Type.BOOLEAN, (object) -> this.setGravititeJumpUsed((boolean) object), this::isGravititeJumpUsed)),
//            Map.entry("setShouldSyncBetweenClients", Triple.of(Type.BOOLEAN, (object) -> this.setShouldSyncBetweenClients((boolean) object), this::shouldSyncBetweenClients)) //todo ?
//    );

    public static final MapCodec<AbilityBehaviorAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.BOOL.fieldOf("can_refuel_glide").forGetter(AbilityBehaviorAttachment::getCanRefuelGlide),
            Codec.INT.fieldOf("gliding_timer").forGetter(AbilityBehaviorAttachment::getGlidingTimer),
            ExtraCodecs.strictUnboundedMap(BuiltInRegistries.ITEM.holderByNameCodec(), Codec.BOOL).fieldOf("can_refuel_abilities").forGetter(AbilityBehaviorAttachment::getCanRefuelAbilities),
            Codec.BOOL.fieldOf("gravitite_holding_floating_block").forGetter(AbilityBehaviorAttachment::isGravititeHoldingFloatingBlock),
            Codec.BOOL.fieldOf("gravitite_jump_used").forGetter(AbilityBehaviorAttachment::isGravititeJumpUsed)
    ).apply(instance, AbilityBehaviorAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AbilityBehaviorAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, AbilityBehaviorAttachment::getGlidingTimer,
            ByteBufCodecs.BOOL, AbilityBehaviorAttachment::isGravititeJumpUsed,
            AbilityBehaviorAttachment::new);

    private boolean shouldSyncAfterJoin;
    private boolean shouldSyncBetweenClients;

    protected AbilityBehaviorAttachment(boolean canRefuelGlide, int glidingTimer, Map<Holder<Item>, Boolean> canRefuelAbilities, boolean gravititeHoldingFloatingBlock, boolean gravititeJumpUsed) {
        this.canRefuelGlide = canRefuelGlide;
        this.glidingTimer = glidingTimer;
        this.canRefuelAbilities =  new HashMap<>(canRefuelAbilities);
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
        this.gravititeJumpUsed = gravititeJumpUsed;
    }

    protected AbilityBehaviorAttachment(int glidingTimer, boolean gravititeJumpUsed) {
        this.glidingTimer = glidingTimer;
        this.gravititeJumpUsed = gravititeJumpUsed;
    }

    public AbilityBehaviorAttachment() { }

    public void login(Player player) {
        this.shouldSyncAfterJoin = true;
    }

    public void logout(Player player) {
        this.clearCompanions();
    }

    public void onJoinLevel(Player player) {
        if (player.level().isClientSide() && player.isLocalPlayer()) {
            player.syncData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
        }
    }

    public void changeDimension(Player player) {
        this.shouldSyncAfterJoin = true;
//        accessories.getEquipped((itemStack) -> itemStack.getItem() instanceof CompanionAccessory<?>).forEach((slot) -> ((CompanionAccessory<?>) slot.stack().getItem()).equip(slot.stack(), slot.reference())); //todo
    }

    public void postTickUpdate(Player player) {
        this.syncAfterJoin(player);
        this.syncClients(player);
        this.handleHealingStoneHealth(player);
        this.resetGlideCheck(player);
        this.trackCompanions();
    }

    private void syncAfterJoin(Player player) {
        if (this.shouldSyncAfterJoin) {
            player.syncData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
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
                            player.syncData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
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

    public void setNeptuneSubmergeLength(double length) {
        this.neptuneSubmergeLength = length;
    }

    /**
     * @return A {@link Double} for how long the player has been submerged in water while wearing Neptune Armor.
     */
    public double getNeptuneSubmergeLength() {
        return this.neptuneSubmergeLength;
    }

    private void trackCompanions() {
        if (!this.getCompanions().isEmpty()) {
            this.getCompanions().removeIf(Entity::isRemoved);
        }
    }

    public void setCompanions(Player player, List<Entity> companions) {
        companions.stream().filter((entity) -> entity instanceof Companion<?>).forEach((entity) -> ((Companion<?>) entity).setOwner(Optional.of(new EntityReference<>(player.getUUID()))));
        this.companions = companions;
    }

    public void addCompanion(Player player, Entity companion) {
        if (companion instanceof Companion<?> companionEntity) {
            companionEntity.setOwner(Optional.of(new EntityReference<>(player.getUUID())));
        }
        this.companions.add(companion);
    }

    public void removeCompanion(Predicate<Entity> companionCheck) {
        this.companions.removeIf(companionCheck);
    }

    public void clearCompanions() {
        this.companions.forEach(Entity::discard);
        this.companions.clear();
    }

    /**
     * @return The {@link List} of companion {@link Entity Entities} that this player has active.
     */
    public List<Entity> getCompanions() {
        return this.companions;
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
