package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.entity.passive.MountableAetherAnimal;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record MountJumpedPacket(int entityID) implements AetherPacketPayload {
    public static final Type<MountJumpedPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "mount_jumped"));

    public static final StreamCodec<FriendlyByteBuf, MountJumpedPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, MountJumpedPacket::entityID,
            MountJumpedPacket::new
    );

    @Override
    public Type<MountJumpedPacket> type() {
        return TYPE;
    }

    public static void execute(MountJumpedPacket payload, AetherPayloadContext context) {
        Player sender = context.player();
        if (sender.level().getServer() != null && sender.level().getEntity(payload.entityID()) instanceof MountableAetherAnimal mountableAetherAnimal) {
            mountableAetherAnimal.setMountJumping(true);
            mountableAetherAnimal.setEntityOnGround(false);
        }
    }
}
