package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.portal.PortalSoundUtil;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public record PortalTravelSoundPacket() implements BasePacket {
    public static final ResourceLocation ID = new ResourceLocation(AetherII.MODID, "play_portal_travel_sound");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
    }

    public static PortalTravelSoundPacket decode(FriendlyByteBuf buf) {
        return new PortalTravelSoundPacket();
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            PortalSoundUtil.playPortalSound(Minecraft.getInstance().player);
        }
    }
}