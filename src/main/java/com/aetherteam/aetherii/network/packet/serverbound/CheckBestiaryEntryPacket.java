package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record CheckBestiaryEntryPacket(EntityType<?> entityType) implements AetherPacketPayload {
    public static final Type<CheckBestiaryEntryPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "check_bestiary_entry"));

    public static final StreamCodec<FriendlyByteBuf, CheckBestiaryEntryPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.ENTITY_TYPE),
            CheckBestiaryEntryPacket::entityType,
            CheckBestiaryEntryPacket::new);

    @Override
    public Type<CheckBestiaryEntryPacket> type() {
        return TYPE;
    }

    public static void execute(CheckBestiaryEntryPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            GuidebookDiscoveryAttachment attachment = AetherIIDataAttachments.get(serverPlayer, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            attachment.getBestiaryEntries().forEach((entry) -> {
                if (entry.getEntityType().value() == payload.entityType()) {
                    entry.getClientValues().values().forEach((info) -> {
                        if (info.isVisible()) {
                            info.view();
                        }
                    });
                }
            });
        }
    }
}
