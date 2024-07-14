package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ForgeSoundPacket(BlockPos pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ForgeSoundPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "forge_sound"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ForgeSoundPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ForgeSoundPacket::pos,
            ForgeSoundPacket::new);

    @Override
    public CustomPacketPayload.Type<ForgeSoundPacket> type() {
        return TYPE;
    }

    public static void execute(ForgeSoundPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().level.playLocalSound(payload.pos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, Minecraft.getInstance().level.getRandom().nextFloat() * 0.1F + 0.9F, false); //todo sound
        }
    }
}

