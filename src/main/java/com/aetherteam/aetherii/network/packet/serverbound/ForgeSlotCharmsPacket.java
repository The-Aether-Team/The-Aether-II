package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record ForgeSlotCharmsPacket() implements AetherPacketPayload {
    public static final AetherPacketPayload.Type<ForgeSlotCharmsPacket> TYPE = new AetherPacketPayload.Type<>(new ResourceLocation(AetherII.MODID, "forge_slot_charms"));

    public static final StreamCodec<FriendlyByteBuf, ForgeSlotCharmsPacket> STREAM_CODEC = StreamCodec.unit(new ForgeSlotCharmsPacket());

    @Override
    public AetherPacketPayload.Type<ForgeSlotCharmsPacket> type() {
        return TYPE;
    }

    public static void execute(ForgeSlotCharmsPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.containerMenu instanceof ArkeniumForgeMenu menu) {
            if (menu.stillValid(playerEntity)) {
                menu.slotCharms(playerEntity);
            }
        }
    }
}
