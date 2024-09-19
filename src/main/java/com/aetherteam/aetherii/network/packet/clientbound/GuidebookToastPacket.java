package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.component.toast.GuidebookToast;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GuidebookToastPacket(GuidebookToast.Type toastType, GuidebookToast.Icons toastIcon) implements CustomPacketPayload {
    public static final Type<GuidebookToastPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook_toast"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GuidebookToastPacket> STREAM_CODEC = StreamCodec.composite(
            GuidebookToast.Type.STREAM_CODEC,
            GuidebookToastPacket::toastType,
            GuidebookToast.Icons.STREAM_CODEC,
            GuidebookToastPacket::toastIcon,
            GuidebookToastPacket::new);

    @Override
    public Type<GuidebookToastPacket> type() {
        return TYPE;
    }

    public static void execute(GuidebookToastPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            GuidebookToast toast = new GuidebookToast(payload.toastType(), payload.toastIcon());
            Minecraft.getInstance().getToasts().addToast(toast);
        }
    }
}
