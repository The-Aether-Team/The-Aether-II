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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record CheckEffectsEntryPacket(MobEffect effect) implements AetherPacketPayload {
    public static final Type<CheckEffectsEntryPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "check_effects_entry"));

    public static final StreamCodec<FriendlyByteBuf, CheckEffectsEntryPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.MOB_EFFECT),
            CheckEffectsEntryPacket::effect,
            CheckEffectsEntryPacket::new);

    @Override
    public Type<CheckEffectsEntryPacket> type() {
        return TYPE;
    }

    public static void execute(CheckEffectsEntryPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            GuidebookDiscoveryAttachment attachment = AetherIIDataAttachments.get(serverPlayer, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
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
