package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CheckGuidebookEntryPacket(EntityType<?> entityType) implements CustomPacketPayload {
    public static final Type<CheckGuidebookEntryPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "check_guidebook_entry"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CheckGuidebookEntryPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.ENTITY_TYPE),
            CheckGuidebookEntryPacket::entityType,
            CheckGuidebookEntryPacket::new);

    @Override
    public Type<CheckGuidebookEntryPacket> type() {
        return TYPE;
    }

    public static void execute(CheckGuidebookEntryPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            GuidebookDiscoveryAttachment attachment = serverPlayer.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            attachment.getUncheckedBestiaryEntries().removeIf((holder) -> holder.value().entityType().value() == payload.entityType());
        }
    }
}
