package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EffectBuildupRemovePacket(int entityId, Holder<MobEffect> mobEffect) implements CustomPacketPayload {
    public static final Type<EffectBuildupRemovePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect_buildup_remove"));

    public static final StreamCodec<RegistryFriendlyByteBuf, EffectBuildupRemovePacket> STREAM_CODEC = CustomPacketPayload.codec(
            EffectBuildupRemovePacket::write,
            EffectBuildupRemovePacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.entityId());
        buf.writeResourceKey(this.mobEffect().unwrapKey().get());
    }

    public static EffectBuildupRemovePacket decode(RegistryFriendlyByteBuf buf) {
        int entityId = buf.readInt();
        Holder.Reference<MobEffect> mobEffect = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(buf.readResourceKey(BuiltInRegistries.MOB_EFFECT.key()));
        return new EffectBuildupRemovePacket(entityId, mobEffect);
    }

    @Override
    public Type<EffectBuildupRemovePacket> type() {
        return TYPE;
    }

    public static void execute(EffectBuildupRemovePacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level().getEntity(payload.entityId()) instanceof LivingEntity entity) {
            entity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).removeBuildup(payload.mobEffect());
        }
    }
}
