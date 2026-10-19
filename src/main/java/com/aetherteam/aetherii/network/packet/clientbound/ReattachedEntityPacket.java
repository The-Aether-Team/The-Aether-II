package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Attached An Entity to the Target Entity using stored NBT data if the Target Entity previously unloaded with a Attached Entity. This is called by {@link ReattachedEntityPacket#reattachedEntity(net.minecraft.world.entity.LivingEntity)} (Player)}.<br><br>
 * This also stores the summoned Entity back into the capability so the Target Entity is tracked as having a Attached Entity.
 */
public record ReattachedEntityPacket(int vehicleID, int entityID) implements CustomPacketPayload {
    public static final Type<ReattachedEntityPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "reattached_entity"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ReattachedEntityPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ReattachedEntityPacket::vehicleID,
            ByteBufCodecs.INT,
            ReattachedEntityPacket::entityID,
            ReattachedEntityPacket::new);

    @Override
    public Type<ReattachedEntityPacket> type() {
        return TYPE;
    }

    public static void execute(ReattachedEntityPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level level = Minecraft.getInstance().player.level();
            if (level.getEntity(payload.vehicleID()) instanceof LivingEntity player && level.getEntity(payload.entityID()) instanceof LivingEntity attachedEntity) {
                attachedEntity.startRiding(player, true, false);
                player.getData(AetherIIDataAttachments.ATTACH_LIVING).setAttachedEntity(attachedEntity);
            }
        }
    }
}
