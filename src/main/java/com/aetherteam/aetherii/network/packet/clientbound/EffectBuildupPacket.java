package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;

public class EffectBuildupPacket {
    public record Set(int entityId, Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups) implements CustomPacketPayload {
        public static final Type<EffectBuildupPacket.Set> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect_buildup_add"));

        public static final StreamCodec<RegistryFriendlyByteBuf, EffectBuildupPacket.Set> STREAM_CODEC = CustomPacketPayload.codec(
                EffectBuildupPacket.Set::write,
                EffectBuildupPacket.Set::decode);

        public void write(RegistryFriendlyByteBuf buf) {
            buf.writeInt(this.entityId());
            buf.writeMap(this.activeBuildups(),
                    (innerBuf, mobEffect) -> innerBuf.writeResourceKey(mobEffect.unwrapKey().get()),
                    (innerBuf, instance) -> innerBuf.writeNbt(instance.save(new CompoundTag())));
        }

        public static EffectBuildupPacket.Set decode(RegistryFriendlyByteBuf buf) {
            int entityId = buf.readInt();
            Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups = buf.readMap(
                    (innerBuf) -> BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(innerBuf.readResourceKey(BuiltInRegistries.MOB_EFFECT.key())),
                    (innerBuf) -> EffectBuildupInstance.load(innerBuf.readNbt()));
            return new EffectBuildupPacket.Set(entityId, activeBuildups);
        }

        @Override
        public Type<EffectBuildupPacket.Set> type() {
            return TYPE;
        }

        public static void execute(EffectBuildupPacket.Set payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level().getEntity(payload.entityId()) instanceof LivingEntity entity) {
                entity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).setBuildups(payload.activeBuildups());
            }
        }
    }

    public record Remove(int entityId, Holder<MobEffect> mobEffect) implements CustomPacketPayload {
        public static final Type<EffectBuildupPacket.Remove> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect_buildup_remove"));

        public static final StreamCodec<RegistryFriendlyByteBuf, EffectBuildupPacket.Remove> STREAM_CODEC = CustomPacketPayload.codec(
                EffectBuildupPacket.Remove::write,
                EffectBuildupPacket.Remove::decode);

        public void write(RegistryFriendlyByteBuf buf) {
            buf.writeInt(this.entityId());
            buf.writeResourceKey(this.mobEffect().unwrapKey().get());
        }

        public static EffectBuildupPacket.Remove decode(RegistryFriendlyByteBuf buf) {
            int entityId = buf.readInt();
            Holder.Reference<MobEffect> mobEffect = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(buf.readResourceKey(BuiltInRegistries.MOB_EFFECT.key()));
            return new EffectBuildupPacket.Remove(entityId, mobEffect);
        }

        @Override
        public Type<EffectBuildupPacket.Remove> type() {
            return TYPE;
        }

        public static void execute(EffectBuildupPacket.Remove payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level().getEntity(payload.entityId()) instanceof LivingEntity entity) {
                entity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).removeBuildup(payload.mobEffect());
            }
        }
    }
}
