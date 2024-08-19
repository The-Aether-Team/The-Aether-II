package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Mounts an Aerbunny to the player using stored NBT data if the player previously logged out with a mounted Aerbunny. This is called by {@link AerbunnyMountAttachment#remountAerbunny(Player)} (Player)}.<br><br>
 * This also stores the summoned Aerbunny back into the capability so the player is tracked as having a mounted Aerbunny.
 */
public record RemountAerbunnyPacket(int vehicleID, int aerbunnyID) implements CustomPacketPayload {
    public static final Type<RemountAerbunnyPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "remount_aerbunny"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RemountAerbunnyPacket> STREAM_CODEC = CustomPacketPayload.codec(
            RemountAerbunnyPacket::write,
            RemountAerbunnyPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.vehicleID());
        buf.writeInt(this.aerbunnyID());
    }

    public static RemountAerbunnyPacket decode(RegistryFriendlyByteBuf buf) {
        int vehicleID = buf.readInt();
        int aerbunnyID = buf.readInt();
        return new RemountAerbunnyPacket(vehicleID, aerbunnyID);
    }

    @Override
    public Type<RemountAerbunnyPacket> type() {
        return TYPE;
    }

    public static void execute(RemountAerbunnyPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level world = Minecraft.getInstance().player.level();
            if (world.getEntity(payload.vehicleID()) instanceof Player player && world.getEntity(payload.aerbunnyID()) instanceof Aerbunny aerbunny) {
                aerbunny.startRiding(player);
                player.getData(AetherIIDataAttachments.PLAYER).setMountedAerbunny(aerbunny);
            }
        }
    }
}
