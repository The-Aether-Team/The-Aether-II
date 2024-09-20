package com.aetherteam.aetherii.attachment.living;

import com.aetherteam.aetherii.effect.AetherIIEffectResistances;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.network.packet.clientbound.EffectBuildupRemovePacket;
import com.aetherteam.aetherii.network.packet.clientbound.EffectBuildupSetPacket;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;

public class EffectsSystemAttachment implements INBTSerializable<CompoundTag> {
    private final Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups = Maps.newHashMap();
    private final LivingEntity entity;
    private boolean loadingSync = false;

    public EffectsSystemAttachment(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        if (!this.activeBuildups.isEmpty()) {
            ListTag listTag = new ListTag();
            for (EffectBuildupInstance instance : this.activeBuildups.values()) {
                listTag.add(instance.save(new CompoundTag()));
            }
            compoundTag.put("active_buildups", listTag);
        }
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        if (tag.contains("active_buildups", 9)) {
            ListTag listTag = tag.getList("active_buildups", 10);
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                EffectBuildupInstance instance = EffectBuildupInstance.load(compoundTag);
                this.activeBuildups.put(instance.getType(), instance);
                this.loadingSync = true;
            }
        }
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
            if (AetherIIEffectResistances.RESISTANCES.containsKey(effect) && AetherIIEffectResistances.RESISTANCES.get(effect) != null) {
                if (this.entity.getAttributes().hasAttribute(AetherIIEffectResistances.RESISTANCES.get(effect))) {
                    modifiedAmount -= modifiedAmount * this.entity.getAttributeValue(AetherIIEffectResistances.RESISTANCES.get(effect));
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
