package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ForgeTriggerSoundPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ForgeTriggerSoundPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "forge_trigger_sound"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ForgeTriggerSoundPacket> STREAM_CODEC = CustomPacketPayload.codec(
            ForgeTriggerSoundPacket::write,
            ForgeTriggerSoundPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {

    }

    public static ForgeTriggerSoundPacket decode(RegistryFriendlyByteBuf buf) {
        return new ForgeTriggerSoundPacket();
    }

    @Override
    public CustomPacketPayload.Type<ForgeTriggerSoundPacket> type() {
        return TYPE;
    }

    public static void execute(ForgeTriggerSoundPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.containerMenu instanceof ArkeniumForgeMenu menu) {
            if (menu.stillValid(playerEntity)) {
                menu.playSound();
            }
        }
    }
}

