package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.network.packet.CurrencySyncPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CurrencyAttachment implements INBTSynchable {
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

    public void onJoinLevel() {
        this.shouldSyncAfterJoin = true;
    }

    public void onUpdate(Player player) {
        this.syncAfterJoin(player);
    }

    private void syncAfterJoin(Player player) {
        if (this.shouldSyncAfterJoin) {
            this.forceSync(player.getId(), Direction.CLIENT);
            this.shouldSyncAfterJoin = false;
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
