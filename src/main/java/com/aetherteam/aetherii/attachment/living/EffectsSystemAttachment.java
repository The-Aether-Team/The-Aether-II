package com.aetherteam.aetherii.attachment.living;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import java.util.HashMap;
import java.util.Map;

public class EffectsSystemAttachment {
    public static final MapCodec<EffectsSystemAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.unboundedMap(BuiltInRegistries.MOB_EFFECT.holderByNameCodec(), EffectBuildupInstance.CODEC).fieldOf("active_buildups").forGetter(EffectsSystemAttachment::getActiveBuildups)
    ).apply(instance, EffectsSystemAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, EffectsSystemAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT), EffectBuildupInstance.STREAM_CODEC), EffectsSystemAttachment::getActiveBuildups,
            EffectsSystemAttachment::new);

    private final Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups;

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
            needSync = true;
        }
        if (needSync) {
            needSync = false;
            livingEntity.syncData(AetherIIDataAttachments.EFFECTS_SYSTEM);
        }
    }

    public void addBuildup(LivingEntity livingEntity, EffectBuildupPresets.Preset buildup, int amount) {
        Holder<MobEffect> effect = buildup.type();
        if (!livingEntity.hasEffect(effect)) {
            double modifiedAmount = amount;
            for (Map.Entry<Holder<Attribute>, AttributeInstance> attributeEntries : ((AttributeMapAccessor) livingEntity.getAttributes()).aether_ii$getAttributes().entrySet()) {
                if (attributeEntries.getKey().value() instanceof EffectResistanceAttribute effectResistanceAttribute && effectResistanceAttribute.getEffect().is(effect)) {
                    modifiedAmount -= modifiedAmount * attributeEntries.getValue().getValue();
                }
            }
            if (!this.activeBuildups.containsKey(effect)) {
                this.activeBuildups.put(effect, new EffectBuildupInstance(buildup, (int) modifiedAmount));
            } else {
                this.activeBuildups.get(effect).increaseBuildup((int) modifiedAmount);
            }
            needSync = true;
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

    public Map<Holder<MobEffect>, EffectBuildupInstance> getActiveBuildups() {
        return ImmutableMap.copyOf(this.activeBuildups);
    }
}
