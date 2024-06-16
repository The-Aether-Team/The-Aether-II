package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.network.packet.DamageSystemSyncPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DamageSystemAttachment implements INBTSynchable {
    public static final int MAX_SHIELD_STAMINA = 500;
    private float criticalDamageModifier = 1.0F;
    private int shieldStamina = MAX_SHIELD_STAMINA;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setShieldStamina", Triple.of(Type.INT, (object) -> this.setShieldStamina((int) object), this::getShieldStamina))
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

    public void setShieldStamina(int shieldStamina) {
        this.shieldStamina = shieldStamina;
    }

    public int getShieldStamina() {
        return this.shieldStamina;
    }

    @Override
    public BasePacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new DamageSystemSyncPacket(entityID, key, type, value);
    }
}
