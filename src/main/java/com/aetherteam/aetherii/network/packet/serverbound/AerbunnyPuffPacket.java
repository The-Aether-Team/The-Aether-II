package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Sets the {@link Aerbunny#DATA_PUFFINESS_ID} value to 11. This is needed in a packet for precise animation syncing.
 */
public record AerbunnyPuffPacket(int entityID) implements CustomPacketPayload {
    public static final Type<AerbunnyPuffPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "aerbunny_puff"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AerbunnyPuffPacket> STREAM_CODEC = CustomPacketPayload.codec(
            AerbunnyPuffPacket::write,
            AerbunnyPuffPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
    }

    public static AerbunnyPuffPacket decode(RegistryFriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new AerbunnyPuffPacket(entityID);
    }

    @Override
    public Type<AerbunnyPuffPacket> type() {
        return TYPE;
    }

    public static void execute(AerbunnyPuffPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof Aerbunny aerbunny) {
            aerbunny.puff();
        }
    }
}
