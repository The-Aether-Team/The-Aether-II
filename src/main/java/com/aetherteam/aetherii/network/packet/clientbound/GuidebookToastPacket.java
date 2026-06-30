package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.GuidebookUtil;
import com.aetherteam.aetherii.client.gui.component.toast.GuidebookToast;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record GuidebookToastPacket(GuidebookToast.Type toastType, GuidebookToast.Icons toastIcon) implements AetherPacketPayload {
    public static final Type<GuidebookToastPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "guidebook_toast"));

    public static final StreamCodec<FriendlyByteBuf, GuidebookToastPacket> STREAM_CODEC = StreamCodec.composite(
            GuidebookToast.Type.STREAM_CODEC,
            GuidebookToastPacket::toastType,
            GuidebookToast.Icons.STREAM_CODEC,
            GuidebookToastPacket::toastIcon,
            GuidebookToastPacket::new);

    @Override
    public Type<GuidebookToastPacket> type() {
        return TYPE;
    }

    public static void execute(GuidebookToastPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            GuidebookUtil.addGuidebookToast(payload.toastType(), payload.toastIcon());
        }
    }
}
