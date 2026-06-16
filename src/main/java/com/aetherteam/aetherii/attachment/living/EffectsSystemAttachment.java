package com.aetherteam.aetherii.attachment.living;

import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.attributes.EffectResistanceAttribute;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AttributeMapAccessor;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ARGB;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectsSystemAttachment {
    public static final MapCodec<EffectsSystemAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.unboundedMap(BuiltInRegistries.MOB_EFFECT.holderByNameCodec(), EffectBuildupInstance.CODEC).fieldOf("active_buildups").forGetter(EffectsSystemAttachment::getActiveBuildups)
    ).apply(instance, EffectsSystemAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, EffectsSystemAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT), EffectBuildupInstance.STREAM_CODEC), EffectsSystemAttachment::getActiveBuildups,
            EffectsSystemAttachment::new);
    public static final int BUILDUP_CAP = 1000;

    private final Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups;
    private Vec3 motionMultiplier = new Vec3(1, 1, 1);

    private boolean needSync;

    protected EffectsSystemAttachment(Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups) {
        Map<Holder<MobEffect>, EffectBuildupInstance> map = Maps.newHashMap();
        map.putAll(activeBuildups);
        this.activeBuildups = map;
    }

    public EffectsSystemAttachment() {
        this.activeBuildups = new HashMap<>();
    }

    public void postTickUpdate(LivingEntity livingEntity) {
        final Holder<MobEffect>[] removableEffect = new Holder[]{null};
        this.activeBuildups.forEach((holder, instance) -> {
            if (!instance.tick(livingEntity)) {
                removableEffect[0] = holder;
            }
        });
        if (removableEffect[0] != null) {
            this.activeBuildups.remove(removableEffect[0]);
            this.needSync = true;
        }
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            List<EffectBuildupInstance> list = this.activeBuildups.values().stream().toList();
            if (!list.isEmpty()) {
                EffectBuildupInstance effect = Util.getRandom(list, livingEntity.getRandom());
                int j = livingEntity.isInvisible() ? 15 : 4;
                int i = (EffectsSystemAttachment.BUILDUP_CAP - effect.getBuildup()) / 100;
                if (i != 0) {
                    if (livingEntity.getRandom().nextInt(Math.max(j * i, 1)) == 0) {
                        serverLevel.sendParticles(ColorParticleOption.create(AetherIIParticleTypes.EFFECT_BUILDUP.get(), ARGB.opaque(effect.getType().value().getColor())), livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 1, 0, 0, 0, 1.0);
                    }
                }
            }
        }

        if (this.needSync) {
            this.needSync = false;
            livingEntity.syncData(AetherIIDataAttachments.EFFECTS_SYSTEM);
        }

        this.setMotionMultiplier(new Vec3(1, 1, 1));
    }

    public void addBuildup(LivingEntity target, EffectBuildupPresets.Preset buildup, int amount) {
        this.addBuildup(target, null, null, buildup, amount);
    }

    public void addBuildup(LivingEntity target, @Nullable Entity source, EffectBuildupPresets.Preset buildup, int amount) {
        this.addBuildup(target, source, source, buildup, amount);
    }

    public void addBuildup(LivingEntity target, @Nullable Entity directSource, @Nullable Entity source, EffectBuildupPresets.Preset buildup, int amount) {
        Holder<MobEffect> effect = buildup.type();
        if (!target.hasEffect(effect)) {
            double modifiedAmount = amount;
            for (Map.Entry<Holder<Attribute>, AttributeInstance> attributeEntries : ((AttributeMapAccessor) target.getAttributes()).aether_ii$getAttributes().entrySet()) {
                if (attributeEntries.getKey().value() instanceof EffectResistanceAttribute effectResistanceAttribute && effectResistanceAttribute.getEffect().is(effect)) {
                    modifiedAmount -= modifiedAmount * attributeEntries.getValue().getValue();
                }
            }
            if (!this.activeBuildups.containsKey(effect)) {
                this.activeBuildups.put(effect, new EffectBuildupInstance(buildup, (int) modifiedAmount));
            } else {
                this.activeBuildups.get(effect).increaseBuildup((int) modifiedAmount);
            }
            if (source instanceof ServerPlayer serverPlayer) {
                AetherIIAdvancementTriggers.EFFECT_BUILDUP.get().trigger(serverPlayer, directSource, target, buildup.type(), this.activeBuildups.get(effect).isBuildupFull());
            } else if (target instanceof ServerPlayer serverPlayer) {
                AetherIIAdvancementTriggers.EFFECT_BUILDUP.get().trigger(serverPlayer, directSource, target, buildup.type(), this.activeBuildups.get(effect).isBuildupFull());
            }
            this.needSync = true;
        }
    }

    public void reduceBuildup(Holder<MobEffect> effect, int amount) {
        if (this.activeBuildups.containsKey(effect)) {
            this.activeBuildups.get(effect).decreaseBuildup(amount);
            needSync = true;
        }
    }

    public void setBuildups(Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups) {
        this.activeBuildups.clear();
        this.activeBuildups.putAll(activeBuildups);
        needSync = true;
    }

    public void removeBuildup(Holder<MobEffect> effect) {
        this.activeBuildups.remove(effect);
        needSync = true;
    }

    public boolean hasBuildup(Holder<MobEffect> effect) {
        return this.activeBuildups.containsKey(effect) && this.activeBuildups.get(effect).getBuildup() > 0;
    }

    public Map<Holder<MobEffect>, EffectBuildupInstance> getActiveBuildups() {
        return ImmutableMap.copyOf(this.activeBuildups);
    }

    public Vec3 getMotionMultiplier() {
        return this.motionMultiplier;
    }

    public void setMotionMultiplier(Vec3 motionMultiplier) {
        this.motionMultiplier = motionMultiplier;
    }

    public void trySetMultiplier(Vec3 motionMultiplier) {
        if (motionMultiplier.length() < this.motionMultiplier.length()) {
            this.motionMultiplier = motionMultiplier;
        }
    }
}
