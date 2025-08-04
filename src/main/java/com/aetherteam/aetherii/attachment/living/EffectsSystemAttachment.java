package com.aetherteam.aetherii.attachment.living;

import com.aetherteam.aetherii.attachment.entity.DroppedItemAttachment;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.attributes.EffectResistanceAttribute;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AttributeMapAccessor;
import com.aetherteam.aetherii.network.packet.clientbound.EffectBuildupRemovePacket;
import com.aetherteam.aetherii.network.packet.clientbound.EffectBuildupSetPacket;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.neoforge.network.PacketDistributor;

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
    private boolean loadingSync = false;

    protected EffectsSystemAttachment(Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups) {
        this.activeBuildups = activeBuildups;
    }

    public EffectsSystemAttachment() {
        this.activeBuildups = new HashMap<>();
    }

    public void postTickUpdate(LivingEntity livingEntity) {
        if (this.loadingSync) {
            if (!this.entity.level().isClientSide()) {
                PacketDistributor.sendToAllPlayers(new EffectBuildupSetPacket(this.entity.getId(), this.activeBuildups));
            }
            this.loadingSync = false;
        }
        this.activeBuildups.values().removeIf(instance -> !instance.tick(this.entity));
    }

    public void addBuildup(EffectBuildupPresets.Preset buildup, int amount) {
        Holder<MobEffect> effect = buildup.type();
        if (!this.entity.hasEffect(effect)) {
            double modifiedAmount = amount;
            for (Map.Entry<Holder<Attribute>, AttributeInstance> attributeEntries : ((AttributeMapAccessor) this.entity.getAttributes()).aether_ii$getAttributes().entrySet()) {
                if (attributeEntries.getKey().value() instanceof EffectResistanceAttribute effectResistanceAttribute && effectResistanceAttribute.getEffect().is(effect)) {
                    modifiedAmount -= modifiedAmount * attributeEntries.getValue().getValue();
                }
            }
            if (!this.activeBuildups.containsKey(effect)) {
                this.activeBuildups.put(effect, new EffectBuildupInstance(buildup, (int) modifiedAmount));
            } else {
                this.activeBuildups.get(effect).increaseBuildup((int) modifiedAmount);
            }
            this.loadingSync = true;
        }
    }

    public void reduceBuildup(Holder<MobEffect> effect, int amount) {
        if (this.activeBuildups.containsKey(effect)) {
            this.activeBuildups.get(effect).decreaseBuildup(amount);
        }
    }

    public void setBuildups(Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups) {
        this.activeBuildups.clear();
        this.activeBuildups.putAll(activeBuildups);
    }

    public void removeBuildup(Holder<MobEffect> effect) {
        if (!this.entity.level().isClientSide()) {
            PacketDistributor.sendToAllPlayers(new EffectBuildupRemovePacket(this.entity.getId(), effect));
        }
        this.activeBuildups.remove(effect);
    }

    public Map<Holder<MobEffect>, EffectBuildupInstance> getActiveBuildups() {
        return ImmutableMap.copyOf(this.activeBuildups);
    }
}
