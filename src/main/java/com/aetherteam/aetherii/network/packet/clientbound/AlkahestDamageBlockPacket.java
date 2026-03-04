package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.fluid.AlkahestFluid;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AlkahestDamageBlockPacket(BlockPos pos, int destroySpeed, boolean drop) implements CustomPacketPayload {
    public static final Type<AlkahestDamageBlockPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_damage_block"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AlkahestDamageBlockPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
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

    public static void execute(AlkahestDamageBlockPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AlkahestFluid.progressivelyDestroyBlock(context.player().level(), payload.pos(), payload.destroySpeed(), payload.drop());
        }
    }
}
