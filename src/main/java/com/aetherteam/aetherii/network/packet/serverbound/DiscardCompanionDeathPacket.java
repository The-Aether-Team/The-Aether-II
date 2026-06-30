package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.util.ItemStackCodecs;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record DiscardCompanionDeathPacket(int entityID, ItemStack stack) implements AetherPacketPayload {
    public static final Type<DiscardCompanionDeathPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "discard_companion_death"));

    public static final StreamCodec<FriendlyByteBuf, DiscardCompanionDeathPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiscardCompanionDeathPacket::entityID,
            ItemStackCodecs.STREAM_CODEC, DiscardCompanionDeathPacket::stack,
            DiscardCompanionDeathPacket::new);

    @Override
    public Type<DiscardCompanionDeathPacket> type() {
        return TYPE;
    }

    public static void execute(DiscardCompanionDeathPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof LivingEntity companion) {
            playerEntity.getCooldowns().addCooldown(payload.stack().getItem(), 1000);
            if (companion.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && playerEntity instanceof ServerPlayer serverPlayer) {
                serverPlayer.sendSystemMessage(Component.translatable("death.attack.aether_ii.retreat", companion.getDisplayName()));
            }
            companion.discard();
        }
    }
}
