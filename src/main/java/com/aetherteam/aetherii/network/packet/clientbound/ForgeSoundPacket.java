package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record ForgeSoundPacket(BlockPos pos) implements AetherPacketPayload {
    public static final AetherPacketPayload.Type<ForgeSoundPacket> TYPE = new AetherPacketPayload.Type<>(new ResourceLocation(AetherII.MODID, "forge_sound"));

    public static final StreamCodec<FriendlyByteBuf, ForgeSoundPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BLOCK_POS,
            ForgeSoundPacket::pos,
            ForgeSoundPacket::new);

    @Override
    public AetherPacketPayload.Type<ForgeSoundPacket> type() {
        return TYPE;
    }

    public static void execute(ForgeSoundPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().level.playLocalSound(payload.pos(), AetherIISoundEvents.BLOCK_ARKENIUM_FORGE_USE.get(), SoundSource.BLOCKS, 1.0F, Minecraft.getInstance().level.getRandom().nextFloat() * 0.1F + 0.9F, false);
        }
    }
}
