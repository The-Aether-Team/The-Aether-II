package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import com.aetherteam.aetherii.network.AetherPayloadContext;

/**
 * Mounts an Aerbunny to the player using stored NBT data if the player previously logged out with a mounted Aerbunny. This is called by {@link AerbunnyMountAttachment#remountAerbunny(Player)} (Player)}.<br><br>
 * This also stores the summoned Aerbunny back into the capability so the player is tracked as having a mounted Aerbunny.
 */
public record RemountAerbunnyPacket(int vehicleID, int aerbunnyID) implements AetherPacketPayload {
    public static final Type<RemountAerbunnyPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "remount_aerbunny"));

    public static final StreamCodec<FriendlyByteBuf, RemountAerbunnyPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            RemountAerbunnyPacket::vehicleID,
            ByteBufCodecs.INT,
            RemountAerbunnyPacket::aerbunnyID,
            RemountAerbunnyPacket::new);

    @Override
    public Type<RemountAerbunnyPacket> type() {
        return TYPE;
    }

    public static void execute(RemountAerbunnyPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level level = Minecraft.getInstance().player.level();
            if (level.getEntity(payload.vehicleID()) instanceof Player player && level.getEntity(payload.aerbunnyID()) instanceof Aerbunny aerbunny) {
                aerbunny.startRiding(player, true);
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.AERBUNNY_MOUNT).setMountedAerbunny(aerbunny);
            }
        }
    }
}
