package com.aetherteam.aetherii.effect.buildup;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EffectBuildupInstance implements Comparable<EffectBuildupInstance> {
    private final int buildupCap = 1000;

    private final Holder<MobEffect> type;
    private final MobEffectInstance instance;
    private final int buildupReductionRate;
    private final int initialInstanceDuration;
    private int buildup;
    private boolean triggerEffect = false;

    public EffectBuildupInstance(EffectBuildupPresets.Preset preset, int buildup) {
        this(preset.type(), preset.instanceBuilder().get(), preset.buildupReductionRate(), buildup);
    }

    public EffectBuildupInstance(Holder<MobEffect> type, MobEffectInstance instance, int buildupReductionRate, int buildup) {
        this.type = type;
        this.instance = instance;
        this.buildupReductionRate = buildupReductionRate;
        this.initialInstanceDuration = instance.getDuration();
        this.buildup = buildup;
    }

    public boolean tick(LivingEntity entity) {
        if (this.isBuildupFull()) {
            if (this.triggerEffect) {
                if (this.instance.getEffect().value().isInstantenous()) {
                    this.instance.getEffect().value().applyInstantenousEffect(null, null, entity, this.instance.getAmplifier(), 1.0);
                } else {
                    entity.addEffect(this.instance);
                }
                this.triggerEffect = false;
            }
            return (this.instance.isInfiniteDuration() || this.instance.getDuration() > 0) && entity.hasEffect(this.type);
        } else {
            if (entity.tickCount % 2 == 0) {
                this.buildup -= this.buildupReductionRate;
            }
            return this.buildup > 0;
        }
    }

    public boolean isBuildupFull() {
        return this.buildup >= this.buildupCap;
    }

    public void increaseBuildup(int amount) {
        this.buildup += amount;
        if (this.isBuildupFull()) {
            this.triggerEffect = true;
        }
    }

    public void decreaseBuildup(int amount) {
        this.buildup -= amount;
    }

    public Holder<MobEffect> getType() {
        return this.type;
    }

    public int getBuildupCap() {
        return this.buildupCap;
    }

    public int getBuildup() {
        return this.buildup;
    }

    public int getInitialInstanceDuration() {
        return this.initialInstanceDuration;
    }

    public CompoundTag save(CompoundTag tag) {
        tag.put("effect_instance", this.instance.save());
        tag.putInt("buildup_reduction_rate", this.buildupReductionRate);
        tag.putInt("buildup", this.buildup);
        return tag;
    }

    public static EffectBuildupInstance load(CompoundTag tag) {
        CompoundTag effectTag = (CompoundTag) tag.get("effect_instance");
        MobEffectInstance effect = MobEffectInstance.load(effectTag);
        int buildupReductionRate = tag.getInt("buildup_reduction_rate");
        int buildup = tag.getInt("buildup");
        return new EffectBuildupInstance(effect.getEffect(), effect, buildupReductionRate, buildup);
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
