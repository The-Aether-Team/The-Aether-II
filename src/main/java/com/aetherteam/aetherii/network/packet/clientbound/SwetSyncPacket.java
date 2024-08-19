package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
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
public record SwetSyncPacket(int entityID, net.minecraft.nbt.CompoundTag compoundTag) implements CustomPacketPayload {
    public static final Type<SwetSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "swet_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SwetSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            SwetSyncPacket::write,
            SwetSyncPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
        buf.writeNbt(this.compoundTag());
    }

    public static SwetSyncPacket decode(RegistryFriendlyByteBuf buf) {
        int vehicleID = buf.readInt();
        CompoundTag nbt = buf.readNbt();
        return new SwetSyncPacket(vehicleID, nbt);
    }

    @Override
    public Type<SwetSyncPacket> type() {
        return TYPE;
    }

    public static void execute(SwetSyncPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level world = Minecraft.getInstance().player.level();
            if (world.getEntity(payload.entityID()) instanceof Player player) {
                player.getData(AetherIIDataAttachments.SWET.get()).deserializeNBT(player.level().registryAccess(), payload.compoundTag());
            }
        }
    }
}
