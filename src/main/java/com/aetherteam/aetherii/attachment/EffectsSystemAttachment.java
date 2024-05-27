package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.network.packet.clientbound.EffectBuildupPacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.Map;

public class EffectsSystemAttachment implements INBTSerializable<CompoundTag> {
    private final Map<MobEffect, EffectBuildupInstance> activeBuildups = Maps.newHashMap();
    private final LivingEntity entity;

    public EffectsSystemAttachment(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public CompoundTag serializeNBT() {
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
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("active_buildups", 9)) {
            ListTag listTag = tag.getList("active_buildups", 10);
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                EffectBuildupInstance instance = EffectBuildupInstance.load(compoundTag);
                this.activeBuildups.put(instance.getEffect().getEffect(), instance);
                PacketRelay.sendToAll(new EffectBuildupPacket.Set(this.entity.getId(), this.activeBuildups)); //todo see if this is sent too soon for proper sync
            }
        }
    }

    public void tick() {
        this.activeBuildups.values().removeIf(instance -> !instance.tick(this.entity));

        AetherII.LOGGER.info(this.activeBuildups.toString());
    }

    public void addBuildup(EffectBuildupPresets.Preset buildup, int amount) {
        MobEffect effect = buildup.type();
        if (!this.entity.hasEffect(effect)) {
            if (!this.activeBuildups.containsKey(effect)) {
                this.activeBuildups.put(effect, new EffectBuildupInstance(buildup, amount));
            } else {
                this.activeBuildups.get(effect).increaseBuildup(amount);
            }
        }
    }

    public void reduceBuildup(MobEffect effect, int amount) {
        if (this.activeBuildups.containsKey(effect)) {
            this.activeBuildups.get(effect).decreaseBuildup(amount);
        }
    }

    public void setBuildups(Map<MobEffect, EffectBuildupInstance> activeBuildups) {
        this.activeBuildups.clear();
        this.activeBuildups.putAll(activeBuildups);
    }

    public void removeBuildup(MobEffect effect) {
        if (!this.entity.level().isClientSide()) {
            PacketRelay.sendToAll(new EffectBuildupPacket.Remove(this.entity.getId(), effect));
        }
        this.activeBuildups.remove(effect);
    }

    public Map<MobEffect, EffectBuildupInstance> getActiveBuildups() {
        return ImmutableMap.copyOf(this.activeBuildups);
    }
}
