package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ForgeSlotCharmsPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ForgeSlotCharmsPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "forge_slot_charms"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ForgeSlotCharmsPacket> STREAM_CODEC = CustomPacketPayload.codec(
            ForgeSlotCharmsPacket::write,
            ForgeSlotCharmsPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {

    }

    public static ForgeSlotCharmsPacket decode(RegistryFriendlyByteBuf buf) {
        return new ForgeSlotCharmsPacket();
    }

    @Override
    public CustomPacketPayload.Type<ForgeSlotCharmsPacket> type() {
        return TYPE;
    }

    public static void execute(ForgeSlotCharmsPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.containerMenu instanceof ArkeniumForgeMenu menu) {
            if (menu.stillValid(playerEntity)) {
                menu.slotCharms();
            }
        }
    }
}
