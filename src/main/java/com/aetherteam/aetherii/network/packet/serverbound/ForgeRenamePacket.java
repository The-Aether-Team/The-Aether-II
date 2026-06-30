package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record ForgeRenamePacket(String name) implements AetherPacketPayload {
    public static final AetherPacketPayload.Type<ForgeRenamePacket> TYPE = new AetherPacketPayload.Type<>(new ResourceLocation(AetherII.MODID, "forge_rename"));

    public static final StreamCodec<FriendlyByteBuf, ForgeRenamePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ForgeRenamePacket::name,
            ForgeRenamePacket::new);

    @Override
    public AetherPacketPayload.Type<ForgeRenamePacket> type() {
        return TYPE;
    }

    public static void execute(ForgeRenamePacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.containerMenu instanceof ArkeniumForgeMenu menu) {
            if (menu.stillValid(playerEntity)) {
                menu.setItemName(payload.name());
            }
        }
    }
}
