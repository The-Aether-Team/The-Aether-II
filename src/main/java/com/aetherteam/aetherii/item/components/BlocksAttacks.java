package com.aetherteam.aetherii.item.components;

import net.minecraft.core.HolderSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;

import java.util.List;
import java.util.Optional;

public record BlocksAttacks(
        float blockDelaySeconds,
        float disableCooldownScale,
        List<DamageReduction> damageReductions,
        ItemDamageFunction itemDamage,
        Optional<HolderSet<DamageType>> bypassedBy,
        Optional<SoundEvent> blockSound,
        Optional<SoundEvent> disabledSound) {
    public record DamageReduction(float horizontalBlockingAngle, Optional<HolderSet<DamageType>> type, float base, float factor) {
    }

    public record ItemDamageFunction(float threshold, float base, float factor) {
    }
}
