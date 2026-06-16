package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DiscardCompanionDeathPacket(int entityID, ItemStack stack) implements CustomPacketPayload {
    public static final Type<DiscardCompanionDeathPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "discard_companion_death"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DiscardCompanionDeathPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiscardCompanionDeathPacket::entityID,
            ItemStack.STREAM_CODEC, DiscardCompanionDeathPacket::stack,
            DiscardCompanionDeathPacket::new);

    @Override
    public Type<DiscardCompanionDeathPacket> type() {
        return TYPE;
    }

    public static void execute(DiscardCompanionDeathPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof LivingEntity companion) {
            playerEntity.getCooldowns().addCooldown(payload.stack(), 1000);
            if (companion.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(GameRules.SHOW_DEATH_MESSAGES) && playerEntity instanceof ServerPlayer serverPlayer) {
                serverPlayer.sendSystemMessage(Component.translatable("death.attack.aether_ii.retreat", companion.getDisplayName()));
            }
            companion.discard();
        }
    }
}
