package com.aetherteam.aetherii.effect.buildup;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EffectBuildupInstance implements Comparable<EffectBuildupInstance> {
    private final MobEffectInstance effect;
    private final int buildupCap;
    private int buildup;

    public EffectBuildupInstance(EffectBuildupPresets.Preset preset, int buildup) {
        this(preset.effect(), preset.buildupCap(), buildup);
    }

    public EffectBuildupInstance(MobEffectInstance effect, int buildupCap, int buildup) {
        this.effect = effect;
        this.buildupCap = buildupCap;
        this.buildup = buildup;
    }

    public boolean tick(LivingEntity entity) {
        if (this.buildup >= this.buildupCap) {
            entity.addEffect(this.effect);
            return false;
        }
        this.buildup--;
        return true;
    }

    public void increaseBuildup(int amount) {
        this.buildup += amount;
    }

    public void decreaseBuildup(int amount) {
        this.buildup -= amount;
    }

    public MobEffectInstance getEffect() {
        return this.effect;
    }

    public int getBuildupCap() {
        return this.buildupCap;
    }

    public int getBuildup() {
        return this.buildup;
    }

    public CompoundTag save(CompoundTag tag) {
        tag.put("effect_instance", this.effect.save(tag));
        tag.putInt("buildup_cap", this.buildupCap);
        tag.putInt("buildup", this.buildup);
        return tag;
    }

    public static EffectBuildupInstance load(CompoundTag tag) {
        MobEffectInstance effect = MobEffectInstance.load(tag);
        int buildupCap = tag.getInt("buildup_cap");
        int buildup = tag.getInt("buildup");
        return new EffectBuildupInstance(effect, buildupCap, buildup);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof EffectBuildupInstance instance)) {
            return false;
        } else {
            return this.effect.equals(instance.effect);
        }
    }

    @Override
    public int compareTo(EffectBuildupInstance other) {
        return this.effect.compareTo(other.effect);
    }
}
