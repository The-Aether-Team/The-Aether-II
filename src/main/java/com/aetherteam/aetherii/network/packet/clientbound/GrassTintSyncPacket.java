package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.event.hooks.BiomeHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;

public record GrassTintSyncPacket(Map<ResourceKey<Biome>, Integer> types) implements CustomPacketPayload {

    public static final Type<GrassTintSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_grass_tint"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GrassTintSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            GrassTintSyncPacket::write,
            GrassTintSyncPacket::decode);

    public void write(FriendlyByteBuf buf) {
        buf.writeMap(types, FriendlyByteBuf::writeResourceKey, FriendlyByteBuf::writeInt);
    }


    public static GrassTintSyncPacket decode(FriendlyByteBuf buf) {
        Map<ResourceKey<Biome>, Integer> map = buf.readMap(b1 -> b1.readResourceKey(Registries.BIOME), FriendlyByteBuf::readInt);
        return new GrassTintSyncPacket(map);
    }

    public static void execute(GrassTintSyncPacket packet, IPayloadContext context) {
        if (Minecraft.getInstance().level != null) {
            BiomeHooks.acceptColors(Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.BIOME), packet.types);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
