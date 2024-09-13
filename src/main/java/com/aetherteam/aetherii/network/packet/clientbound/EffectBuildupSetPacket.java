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

public record EffectBuildupSetPacket(int entityId, Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups) implements CustomPacketPayload {
    public static final Type<EffectBuildupSetPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect_buildup_add"));

    public static final StreamCodec<RegistryFriendlyByteBuf, EffectBuildupSetPacket> STREAM_CODEC = CustomPacketPayload.codec(
            EffectBuildupSetPacket::write,
            EffectBuildupSetPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.entityId());
        buf.writeMap(this.activeBuildups(),
                (innerBuf, mobEffect) -> innerBuf.writeResourceKey(mobEffect.unwrapKey().get()),
                (innerBuf, instance) -> innerBuf.writeNbt(instance.save(new CompoundTag())));
    }

    public static EffectBuildupSetPacket decode(RegistryFriendlyByteBuf buf) {
        int entityId = buf.readInt();
        Map<Holder<MobEffect>, EffectBuildupInstance> activeBuildups = buf.readMap(
                (innerBuf) -> BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(innerBuf.readResourceKey(BuiltInRegistries.MOB_EFFECT.key())),
                (innerBuf) -> EffectBuildupInstance.load(innerBuf.readNbt()));
        return new EffectBuildupSetPacket(entityId, activeBuildups);
    }

    @Override
    public Type<EffectBuildupSetPacket> type() {
        return TYPE;
    }

    public static void execute(EffectBuildupSetPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level().getEntity(payload.entityId()) instanceof LivingEntity entity) {
            entity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).setBuildups(payload.activeBuildups());
        }
    }
}
