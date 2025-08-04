package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.ServerPlayerAccessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CurrencyAttachment {
    private int amount;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setAmount", Triple.of(Type.INT, (object) -> this.setAmount((int) object), this::getAmount))
    );
    private boolean shouldSyncAfterJoin;

    public static final Codec<CurrencyAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("amount").forGetter(CurrencyAttachment::getAmount)
    ).apply(instance, CurrencyAttachment::new));

    public CurrencyAttachment() {
        this.amount = 0;
    }

    protected CurrencyAttachment(int amount) {
        this.amount = amount;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    public void login(Player player) {
        this.shouldSyncAfterJoin = true;
    }

    public void clone(Player original, Player player, boolean wasDeath) {
        if (wasDeath) {
            if (player.level() instanceof ServerLevel serverLevel) {
                GameRules gameRules = serverLevel.getGameRules();
                if (gameRules.getRule(GameRules.RULE_KEEPINVENTORY).get()) {
                    player.getData(AetherIIDataAttachments.CURRENCY).setAmount(original.getData(AetherIIDataAttachments.CURRENCY).getAmount());
                }
            }
        }
        this.shouldSyncAfterJoin = true;
    }

    public void postTickUpdate(Player player) {
        this.syncAfterJoin(player);
    }

    private void syncAfterJoin(Player player) {
        if (this.shouldSyncAfterJoin) {
            this.forceSync(player.getId(), Direction.CLIENT);
            this.shouldSyncAfterJoin = false;
        }
    }

    public void dropAll(Player player, Collection<ItemEntity> drops) {
        if (player instanceof ServerPlayer serverPlayer && player.level() instanceof ServerLevel serverLevel) {
            GameRules gameRules = serverLevel.getGameRules();
            if (!gameRules.getRule(GameRules.RULE_KEEPINVENTORY).get()) {
                int amount = player.getData(AetherIIDataAttachments.CURRENCY).getAmount();
                int fullStacks = Math.floorDiv(amount, 64);
                int leftoverStack = amount % 64;
                Collection<ItemEntity> newStacks = new ArrayList<>();
                for (int i = 0; i < fullStacks; i++) {
                    ItemStack itemStack = new ItemStack(AetherIIItems.GLINT_COIN.get(), 64);
                    ItemEntity itemEntity = ((ServerPlayerAccessor) serverPlayer).callCreateItemStackToDrop(itemStack, true, false);
                    newStacks.add(itemEntity);
                    if (itemEntity != null) {
                        newStacks.add(itemEntity);
                    }
                }
                ItemStack itemStack = new ItemStack(AetherIIItems.GLINT_COIN.get(), leftoverStack);
                ItemEntity itemEntity = ((ServerPlayerAccessor) serverPlayer).callCreateItemStackToDrop(itemStack, true, false);
                if (itemEntity != null) {
                    newStacks.add(itemEntity);
                }
                drops.addAll(newStacks);
            }
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new CurrencySyncPacket(entityID, key, type, value);
    }
}
