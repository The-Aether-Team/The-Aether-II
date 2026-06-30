package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.fluid.AlkahestFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record AlkahestBreakBlockPacket(BlockPos pos, boolean drop) implements AetherPacketPayload {
    public static final Type<AlkahestBreakBlockPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "alkahest_break_block"));

    public static final StreamCodec<FriendlyByteBuf, AlkahestBreakBlockPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BLOCK_POS,
            AlkahestBreakBlockPacket::pos,
            ByteBufCodecs.BOOL,
            AlkahestBreakBlockPacket::drop,
            AlkahestBreakBlockPacket::new);

    @Override
    public Type<AlkahestBreakBlockPacket> type() {
        return TYPE;
    }

    public static void execute(AlkahestBreakBlockPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.level().getServer() != null) {
            AlkahestFluid.fullyDestroyBlock(playerEntity.level(), payload.pos(), payload.drop());
        }
    }
}
