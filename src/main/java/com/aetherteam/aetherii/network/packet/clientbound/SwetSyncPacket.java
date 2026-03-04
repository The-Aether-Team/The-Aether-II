package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueInput;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SwetSyncPacket(int entityID, CompoundTag compoundTag) implements CustomPacketPayload {
    public static final Type<SwetSyncPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "swet_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SwetSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SwetSyncPacket::entityID,
            ByteBufCodecs.COMPOUND_TAG,
            SwetSyncPacket::compoundTag,
            SwetSyncPacket::new);

    @Override
    public Type<SwetSyncPacket> type() {
        return TYPE;
    }

    public static void execute(SwetSyncPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level level = Minecraft.getInstance().player.level();
            if (level.getEntity(payload.entityID()) instanceof Player player) {
                try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(player.problemPath(), AetherII.LOGGER)) {

                    player.getData(AetherIIDataAttachments.SWET_LATCH.get()).deserialize(TagValueInput.create(problemreporter$scopedcollector, player.level().registryAccess(), payload.compoundTag()));
                }
            }
        }
    }
}
