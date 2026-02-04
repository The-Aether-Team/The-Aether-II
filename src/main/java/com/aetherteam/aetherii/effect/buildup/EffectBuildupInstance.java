package com.aetherteam.aetherii.effect.buildup;

import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EffectBuildupInstance implements Comparable<EffectBuildupInstance> {
    public static final Codec<EffectBuildupInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect_type").forGetter(effect -> effect.type),
            MobEffectInstance.CODEC.fieldOf("effect_instance").forGetter(effect -> effect.instance),
            Codec.INT.fieldOf("initial_instance_duration").forGetter(effect -> effect.initialInstanceDuration),
            Codec.INT.fieldOf("buildup_reduction_rate").forGetter(effect -> effect.buildupReductionRate),
            Codec.INT.fieldOf("buildup").forGetter(effect -> effect.buildup)
    ).apply(instance, EffectBuildupInstance::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, EffectBuildupInstance> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT), (effect) -> effect.type,
            MobEffectInstance.STREAM_CODEC, (effect) -> effect.instance,
            ByteBufCodecs.INT, (effect) -> effect.initialInstanceDuration,
            ByteBufCodecs.INT, (effect) -> effect.buildupReductionRate,
            ByteBufCodecs.INT, (effect) -> effect.buildup,
            EffectBuildupInstance::new);

    private final Holder<MobEffect> type;
    private final MobEffectInstance instance;
    private final int initialInstanceDuration;
    private final int buildupReductionRate;
    private int buildup;
    private boolean triggerEffect = false;

    public EffectBuildupInstance(EffectBuildupPresets.Preset preset, int buildup) {
        this(preset.type(), preset.createMobEffectInstance(), preset.buildupReductionRate(), buildup);
    }

    public EffectBuildupInstance(Holder<MobEffect> type, MobEffectInstance instance, int buildupReductionRate, int buildup) {
        this(type, instance, instance.getDuration(), buildupReductionRate, buildup);
    }

    public EffectBuildupInstance(Holder<MobEffect> type, MobEffectInstance instance, int initialInstanceDuration, int buildupReductionRate, int buildup) {
        this.type = type;
        this.instance = instance;
        this.initialInstanceDuration = initialInstanceDuration;
        this.buildupReductionRate = buildupReductionRate;
        this.buildup = buildup;
    }

    public boolean tick(LivingEntity entity) {
        if (this.isBuildupFull()) {
            if (this.triggerEffect) {
                if (this.instance.getEffect().value().isInstantenous()) {
                    if (entity.level() instanceof ServerLevel serverLevel) {
                        this.instance.getEffect().value().applyInstantenousEffect(serverLevel, null, null, entity, this.instance.getAmplifier(), 1.0);
                    }
                } else {
                    if (!entity.level().isClientSide()) {
                        entity.addEffect(this.instance);
                    }
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
        return this.buildup >= EffectsSystemAttachment.BUILDUP_CAP;
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

    public int getBuildup() {
        return this.buildup;
    }

    public int getInitialInstanceDuration() {
        return this.initialInstanceDuration;
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
