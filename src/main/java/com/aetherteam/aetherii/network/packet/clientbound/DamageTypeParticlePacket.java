package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DamageTypeParticlePacket(int entityID, SimpleParticleType particleType) implements CustomPacketPayload {
    public static final Type<DamageTypeParticlePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "spawn_damage_type_particle"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DamageTypeParticlePacket> STREAM_CODEC = CustomPacketPayload.codec(
            DamageTypeParticlePacket::write,
            DamageTypeParticlePacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
        buf.writeResourceLocation(BuiltInRegistries.PARTICLE_TYPE.getKey(this.particleType()));
    }

    public static DamageTypeParticlePacket decode(RegistryFriendlyByteBuf buf) {
        int entityID = buf.readInt();
        SimpleParticleType particleType = (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.getValue(buf.readResourceLocation());
        return new DamageTypeParticlePacket(entityID, particleType);
    }

    @Override
    public Type<DamageTypeParticlePacket> type() {
        return TYPE;
    }

    public static void execute(DamageTypeParticlePacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(payload.entityID());
            if (entity != null) {
                Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, payload.particleType(), 1);
            }
        }
    }
}
