package com.aetherteam.aetherii.attachment;

public class DamageSystemAttachment {
    private float criticalDamageModifier = 1.0F;

    public DamageSystemAttachment() { }

    public void setCriticalDamageModifier(float criticalDamageModifier) {
        this.criticalDamageModifier = criticalDamageModifier;
    }

    public float getCriticalDamageModifier() {
        return this.criticalDamageModifier;
    }
}
