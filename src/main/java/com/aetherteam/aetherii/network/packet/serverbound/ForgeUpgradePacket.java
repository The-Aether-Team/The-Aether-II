package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ForgeUpgradePacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ForgeUpgradePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "forge_upgrade"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ForgeUpgradePacket> STREAM_CODEC = CustomPacketPayload.codec(
            ForgeUpgradePacket::write,
            ForgeUpgradePacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {

    }

    public static ForgeUpgradePacket decode(RegistryFriendlyByteBuf buf) {
        return new ForgeUpgradePacket();
    }

    @Override
    public CustomPacketPayload.Type<ForgeUpgradePacket> type() {
        return TYPE;
    }

    public static void execute(ForgeUpgradePacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.containerMenu instanceof ArkeniumForgeMenu menu) {
            if (menu.stillValid(playerEntity)) {
                menu.upgradeItem();
            }
        }
    }
}

