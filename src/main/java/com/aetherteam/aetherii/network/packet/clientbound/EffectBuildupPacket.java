package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class EffectBuildupPacket {
    public record Set(int entityId, Map<MobEffect, EffectBuildupInstance> activeBuildups) implements BasePacket {
        public static final ResourceLocation ID = new ResourceLocation(AetherII.MODID, "effect_buildup_add");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeInt(this.entityId());
            buf.writeMap(this.activeBuildups(),
                    (innerBuf, mobEffect) -> innerBuf.writeResourceLocation(BuiltInRegistries.MOB_EFFECT.getKey(mobEffect)),
                    (innerBuf, instance) -> innerBuf.writeNbt(instance.save(new CompoundTag())));
        }

        public static EffectBuildupPacket.Set decode(FriendlyByteBuf buf) {
            int entityId = buf.readInt();
            Map<MobEffect, EffectBuildupInstance> activeBuildups = buf.readMap(
                    (innerBuf) -> BuiltInRegistries.MOB_EFFECT.get(innerBuf.readResourceLocation()),
                    (innerBuf) -> EffectBuildupInstance.load(innerBuf.readNbt()));

            return new EffectBuildupPacket.Set(entityId, activeBuildups);
        }

        @Override
        public void execute(@Nullable Player player) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level().getEntity(this.entityId()) instanceof LivingEntity entity) {
                entity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).setBuildups(this.activeBuildups());
            }
        }
    }

    public record Remove(int entityId, MobEffect mobEffect) implements BasePacket {
        public static final ResourceLocation ID = new ResourceLocation(AetherII.MODID, "effect_buildup_remove");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeInt(this.entityId());
            buf.writeResourceLocation(BuiltInRegistries.MOB_EFFECT.getKey(this.mobEffect()));
        }

        public static EffectBuildupPacket.Remove decode(FriendlyByteBuf buf) {
            int entityId = buf.readInt();
            MobEffect mobEffect = BuiltInRegistries.MOB_EFFECT.get(buf.readResourceLocation());
            return new EffectBuildupPacket.Remove(entityId, mobEffect);
        }

        @Override
        public void execute(@Nullable Player player) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level().getEntity(this.entityId()) instanceof LivingEntity entity) {
                entity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).removeBuildup(this.mobEffect());
            }
        }
    }
}
