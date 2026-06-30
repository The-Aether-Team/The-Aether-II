package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import com.aetherteam.aetherii.util.nbt.ValueInput;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record SwetSyncPacket(int entityID, CompoundTag compoundTag) implements AetherPacketPayload {
    public static final Type<SwetSyncPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "swet_sync"));

    public static final StreamCodec<FriendlyByteBuf, SwetSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SwetSyncPacket::entityID,
            ByteBufCodecs.COMPOUND_TAG,
            SwetSyncPacket::compoundTag,
            SwetSyncPacket::new);

    @Override
    public Type<SwetSyncPacket> type() {
        return TYPE;
    }

    public static void execute(SwetSyncPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level level = Minecraft.getInstance().player.level();
            if (level.getEntity(payload.entityID()) instanceof Player player) {
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.SWET_LATCH).deserialize(new ValueInput(payload.compoundTag()));
            }
        }
    }
}
