package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.fluid.AlkahestFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AlkahestBreakBlockPacket(BlockPos pos, boolean drop) implements CustomPacketPayload {
    public static final Type<AlkahestBreakBlockPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_break_block"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AlkahestBreakBlockPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            AlkahestBreakBlockPacket::pos,
            ByteBufCodecs.BOOL,
            AlkahestBreakBlockPacket::drop,
            AlkahestBreakBlockPacket::new);

    @Override
    public Type<AlkahestBreakBlockPacket> type() {
        return TYPE;
    }

    public static void execute(AlkahestBreakBlockPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.getServer() != null) {
            AlkahestFluid.fullyDestroyBlock(playerEntity.level(), payload.pos(), payload.drop());
        }
    }
}
