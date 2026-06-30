package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record ForgeTriggerSoundPacket() implements AetherPacketPayload {
    public static final AetherPacketPayload.Type<ForgeTriggerSoundPacket> TYPE = new AetherPacketPayload.Type<>(new ResourceLocation(AetherII.MODID, "forge_trigger_sound"));

    public static final StreamCodec<FriendlyByteBuf, ForgeTriggerSoundPacket> STREAM_CODEC = AetherPacketPayload.codec(
            ForgeTriggerSoundPacket::write,
            ForgeTriggerSoundPacket::decode);

    public void write(FriendlyByteBuf buf) {

    }

    public static ForgeTriggerSoundPacket decode(FriendlyByteBuf buf) {
        return new ForgeTriggerSoundPacket();
    }

    @Override
    public AetherPacketPayload.Type<ForgeTriggerSoundPacket> type() {
        return TYPE;
    }

    public static void execute(ForgeTriggerSoundPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.containerMenu instanceof ArkeniumForgeMenu menu) {
            if (menu.stillValid(playerEntity)) {
                menu.playSound();
            }
        }
    }
}

