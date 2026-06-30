package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.fluid.AlkahestFluid;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record AlkahestDamageBlockPacket(BlockPos pos, int destroySpeed, boolean drop) implements AetherPacketPayload {
    public static final Type<AlkahestDamageBlockPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "alkahest_damage_block"));

    public static final StreamCodec<FriendlyByteBuf, AlkahestDamageBlockPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BLOCK_POS,
            AlkahestDamageBlockPacket::pos,
            ByteBufCodecs.INT,
            AlkahestDamageBlockPacket::destroySpeed,
            ByteBufCodecs.BOOL,
            AlkahestDamageBlockPacket::drop,
            AlkahestDamageBlockPacket::new);

    @Override
    public Type<AlkahestDamageBlockPacket> type() {
        return TYPE;
    }

    public static void execute(AlkahestDamageBlockPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AlkahestFluid.progressivelyDestroyBlock(context.player().level(), payload.pos(), payload.destroySpeed(), payload.drop());
        }
    }
}
