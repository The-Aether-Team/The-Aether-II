package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.event.hooks.BiomeHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import com.aetherteam.aetherii.network.AetherPayloadContext;

import java.util.Map;

public record GrassTintSyncPacket(Map<ResourceKey<Biome>, Integer> types) implements AetherPacketPayload {

    public static final Type<GrassTintSyncPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "sync_grass_tint"));

    public static final StreamCodec<FriendlyByteBuf, GrassTintSyncPacket> STREAM_CODEC = AetherPacketPayload.codec(
            GrassTintSyncPacket::write,
            GrassTintSyncPacket::decode);

    public void write(FriendlyByteBuf buf) {
        buf.writeMap(types, FriendlyByteBuf::writeResourceKey, FriendlyByteBuf::writeInt);
    }


    public static GrassTintSyncPacket decode(FriendlyByteBuf buf) {
        Map<ResourceKey<Biome>, Integer> map = buf.readMap(b1 -> b1.readResourceKey(Registries.BIOME), FriendlyByteBuf::readInt);
        return new GrassTintSyncPacket(map);
    }

    public static void execute(GrassTintSyncPacket packet, AetherPayloadContext context) {
        if (Minecraft.getInstance().level != null) {
            BiomeHooks.acceptColors(Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.BIOME), packet.types);
        }
    }

    @Override
    public Type<? extends AetherPacketPayload> type() {
        return TYPE;
    }
}
