package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CheckEffectsEntryPacket(MobEffect effect) implements CustomPacketPayload {
    public static final Type<CheckEffectsEntryPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "check_effects_entry"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CheckEffectsEntryPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.MOB_EFFECT),
            CheckEffectsEntryPacket::effect,
            CheckEffectsEntryPacket::new);

    @Override
    public Type<CheckEffectsEntryPacket> type() {
        return TYPE;
    }

    public static void execute(CheckEffectsEntryPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            GuidebookDiscoveryAttachment attachment = serverPlayer.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            attachment.getEffectsEntries().forEach((entry) -> {
                if (entry.getEffect().value() == payload.effect()) {
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
