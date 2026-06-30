package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record AlkahestItemSmokePacket(Vec3 pos) implements AetherPacketPayload {
    public static final Type<AlkahestItemSmokePacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "alkahest_item_smoke"));

    public static final StreamCodec<FriendlyByteBuf, AlkahestItemSmokePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VEC3,
            AlkahestItemSmokePacket::pos,
            AlkahestItemSmokePacket::new);

    @Override
    public Type<AlkahestItemSmokePacket> type() {
        return TYPE;
    }

    public static void execute(AlkahestItemSmokePacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            for (int i = 0; i < 2; ++i) {
                double d0 = Minecraft.getInstance().level.getRandom().nextGaussian() * 0.02;
                double d1 = Minecraft.getInstance().level.getRandom().nextGaussian() * 0.02;
                double d2 = Minecraft.getInstance().level.getRandom().nextGaussian() * 0.02;
                Minecraft.getInstance().level.addParticle(ParticleTypes.SMOKE, payload.pos().x(), payload.pos().y(), payload.pos().z(), d0, d1, d2);
            }
        }
    }
}
