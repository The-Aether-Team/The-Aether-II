package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record ResistanceKnockbackPacket(int sourceID, int targetID) implements AetherPacketPayload {
    public static final Type<ResistanceKnockbackPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "resistance_knockback"));

    public static final StreamCodec<FriendlyByteBuf, ResistanceKnockbackPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ResistanceKnockbackPacket::sourceID,
            ByteBufCodecs.INT,
            ResistanceKnockbackPacket::targetID,
            ResistanceKnockbackPacket::new);

    @Override
    public Type<ResistanceKnockbackPacket> type() {
        return TYPE;
    }

    public static void execute(ResistanceKnockbackPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level level = Minecraft.getInstance().player.level();
            Entity entity = level.getEntity(payload.targetID());
            if (level.getEntity(payload.sourceID()) instanceof LivingEntity source && entity != null) {
                source.knockback(0.75 / (1 + source.distanceTo(entity)), -Mth.sin(source.getYRot() * Mth.DEG_TO_RAD), Mth.cos(source.getYRot() * Mth.DEG_TO_RAD));
            }
        }
    }
}
