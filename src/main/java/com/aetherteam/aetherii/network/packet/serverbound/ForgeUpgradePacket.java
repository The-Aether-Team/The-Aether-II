package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record ForgeUpgradePacket(ReinforcementTier tier) implements AetherPacketPayload {
    public static final AetherPacketPayload.Type<ForgeUpgradePacket> TYPE = new AetherPacketPayload.Type<>(new ResourceLocation(AetherII.MODID, "forge_upgrade"));

    public static final StreamCodec<FriendlyByteBuf, ForgeUpgradePacket> STREAM_CODEC = StreamCodec.composite(
            ReinforcementTier.STREAM_CODEC, ForgeUpgradePacket::tier,
            ForgeUpgradePacket::new);

    @Override
    public AetherPacketPayload.Type<ForgeUpgradePacket> type() {
        return TYPE;
    }

    public static void execute(ForgeUpgradePacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.containerMenu instanceof ArkeniumForgeMenu menu) {
            if (menu.stillValid(playerEntity)) {
                menu.upgradeItem(playerEntity.level().registryAccess(), payload.tier());
            }
        }
    }
}
