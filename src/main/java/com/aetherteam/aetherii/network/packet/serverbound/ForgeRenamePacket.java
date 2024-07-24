package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ForgeRenamePacket(String name) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ForgeRenamePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "forge_rename"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ForgeRenamePacket> STREAM_CODEC = CustomPacketPayload.codec(
            ForgeRenamePacket::write,
            ForgeRenamePacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeUtf(this.name);
    }

    public static ForgeRenamePacket decode(RegistryFriendlyByteBuf buf) {
        String name = buf.readUtf();
        return new ForgeRenamePacket(name);
    }

    @Override
    public CustomPacketPayload.Type<ForgeRenamePacket> type() {
        return TYPE;
    }

    public static void execute(ForgeRenamePacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.containerMenu instanceof ArkeniumForgeMenu menu) {
            if (menu.stillValid(playerEntity)) {
                menu.setItemName(payload.name());
            }
        }
    }
}
