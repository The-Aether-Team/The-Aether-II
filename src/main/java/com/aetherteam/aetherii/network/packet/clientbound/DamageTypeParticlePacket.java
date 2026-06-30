package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record DamageTypeParticlePacket(int entityID, SimpleParticleType particleType) implements AetherPacketPayload {
    public static final Type<DamageTypeParticlePacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "spawn_damage_type_particle"));

    public static final StreamCodec<FriendlyByteBuf, DamageTypeParticlePacket> STREAM_CODEC = AetherPacketPayload.codec(
            DamageTypeParticlePacket::write,
            DamageTypeParticlePacket::decode);

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
        buf.writeResourceLocation(BuiltInRegistries.PARTICLE_TYPE.getKey(this.particleType()));
    }

    public static DamageTypeParticlePacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        SimpleParticleType particleType = (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(buf.readResourceLocation());
        return new DamageTypeParticlePacket(entityID, particleType);
    }

    @Override
    public Type<DamageTypeParticlePacket> type() {
        return TYPE;
    }

    public static void execute(DamageTypeParticlePacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(payload.entityID());
            if (entity != null) {
                Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, payload.particleType(), 1);
            }
        }
    }
}
