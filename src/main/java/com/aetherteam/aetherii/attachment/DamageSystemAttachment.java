package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.network.packet.DamageSystemSyncPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DamageSystemAttachment implements INBTSynchable {
    public static final int MAX_BLOCK_STATUS = 500;
    private float criticalDamageModifier = 1.0F;
    private int blockStatus = MAX_BLOCK_STATUS;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setBlockStatus", Triple.of(Type.INT, (object) -> this.setBlockStatus((int) object), this::getBlockStatus))
    );

    public DamageSystemAttachment() { }

    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    public void setCriticalDamageModifier(float criticalDamageModifier) {
        this.criticalDamageModifier = criticalDamageModifier;
    }

    public float getCriticalDamageModifier() {
        return this.criticalDamageModifier;
    }

    public void setBlockStatus(int blockStatus) {
        this.blockStatus = blockStatus;
    }

    public int getBlockStatus() {
        return this.blockStatus;
    }

    @Override
    public BasePacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new DamageSystemSyncPacket(entityID, key, type, value);
    }
}
