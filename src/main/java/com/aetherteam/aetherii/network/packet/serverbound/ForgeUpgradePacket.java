package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ForgeUpgradePacket(ReinforcementTier tier) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ForgeUpgradePacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "forge_upgrade"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ForgeUpgradePacket> STREAM_CODEC = StreamCodec.composite(
            ReinforcementTier.STREAM_CODEC, ForgeUpgradePacket::tier,
            ForgeUpgradePacket::new);

    @Override
    public CustomPacketPayload.Type<ForgeUpgradePacket> type() {
        return TYPE;
    }

    public static void execute(ForgeUpgradePacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.containerMenu instanceof ArkeniumForgeMenu menu) {
            if (menu.stillValid(playerEntity)) {
                menu.upgradeItem(playerEntity.registryAccess(), payload.tier());
            }
        }
    }
}

