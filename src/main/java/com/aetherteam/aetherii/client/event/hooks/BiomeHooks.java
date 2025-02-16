package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.client.AetherIIColorResolvers;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.network.packet.clientbound.GrassTintSyncPacket;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BiomeHooks {

    private static Map<Biome, Integer> COLORS = ImmutableMap.of();

    public static void acceptColors(HolderGetter<Biome> getter, Map<ResourceKey<Biome>, Integer> map) {
        COLORS = map.entrySet().stream()
                .map(entry -> Pair.of(getter.get(entry.getKey()), entry.getValue()))
                .filter(pair -> pair.getFirst().isPresent())
                .map(pair -> pair.mapFirst(Optional::get))
                .filter(pair -> pair.getFirst().isBound())
                .map(pair -> pair.mapFirst(Holder::value))
                .collect(Collectors.toUnmodifiableMap(Pair::getFirst, Pair::getSecond));
    }

    public static int getColor(Biome biome, double x, double z) {
        return biome.getModifiedSpecialEffects().getGrassColorModifier().modifyColor(
                x, z, COLORS.getOrDefault(biome, AetherIIColorResolvers.AETHER_GRASS_COLOR));
    }

    public static void sendColors(Player player) {
        if (!player.level().isClientSide() && player instanceof ServerPlayer sp) {
            Registry<Biome> registry = player.registryAccess().lookupOrThrow(Registries.BIOME);
            PacketDistributor.sendToPlayer(sp, new GrassTintSyncPacket(registry.getDataMap(AetherIIDataMaps.AETHER_GRASS_COLORS)));
        }
    }
}
