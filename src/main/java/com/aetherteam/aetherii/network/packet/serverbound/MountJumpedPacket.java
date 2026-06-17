package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.entity.passive.MountableAetherAnimal;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MountJumpedPacket(int entityID) implements CustomPacketPayload {
    public static final Type<MountJumpedPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "mount_jumped"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MountJumpedPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, MountJumpedPacket::entityID,
            MountJumpedPacket::new
    );

    @Override
    public Type<MountJumpedPacket> type() {
        return TYPE;
    }

    public static void execute(MountJumpedPacket payload, IPayloadContext context) {
        Player sender = context.player();
        if (sender.level().getServer() != null && sender.level().getEntity(payload.entityID()) instanceof MountableAetherAnimal mountableAetherAnimal) {
            mountableAetherAnimal.setMountJumping(true);
            mountableAetherAnimal.setEntityOnGround(false);
        }
    }
}
