package com.aetherteam.aetherii.effect.buildup;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EffectBuildupInstance implements Comparable<EffectBuildupInstance> {
    private final MobEffect type;
    private final MobEffectInstance instance;
    private final int buildupCap;
    private int buildup;

    public EffectBuildupInstance(EffectBuildupPresets.Preset preset, int buildup) {
        this(preset.type(), preset.instanceBuilder().get(), preset.buildupCap(), buildup);
    }

    public EffectBuildupInstance(MobEffect type, MobEffectInstance instance, int buildupCap, int buildup) {
        this.type = type;
        this.instance = instance;
        this.buildupCap = buildupCap;
        this.buildup = buildup;
    }

    public boolean tick(LivingEntity entity) {
        if (this.buildup >= this.buildupCap) {
            entity.addEffect(this.instance);
            return false;
        } else if (this.buildup <= 0) {
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

    public MobEffect getType() {
        return this.type;
    }

    public int getBuildupCap() {
        return this.buildupCap;
    }

    public int getBuildup() {
        return this.buildup;
    }

    public CompoundTag save(CompoundTag tag) {
        tag.put("effect_instance", this.instance.save(new CompoundTag()));
        tag.putInt("buildup_cap", this.buildupCap);
        tag.putInt("buildup", this.buildup);
        return tag;
    }

    public static EffectBuildupInstance load(CompoundTag tag) {
        CompoundTag effectTag = (CompoundTag) tag.get("effect_instance");
        MobEffectInstance effect = MobEffectInstance.load(effectTag);
        int buildupCap = tag.getInt("buildup_cap");
        int buildup = tag.getInt("buildup");
        return new EffectBuildupInstance(effect.getEffect(), effect, buildupCap, buildup);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof EffectBuildupInstance instance)) {
            return false;
        } else {
            return this.instance.equals(instance.instance);
        }
    }

    @Override
    public int compareTo(EffectBuildupInstance other) {
        return this.instance.compareTo(other.instance);
    }
}
