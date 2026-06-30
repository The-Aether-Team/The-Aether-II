package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.AetherIIColorResolvers;
import com.aetherteam.aetherii.network.AetherIINetwork;
import com.aetherteam.aetherii.network.packet.clientbound.GrassTintSyncPacket;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class BiomeHooks {
    private static Map<Biome, Integer> COLORS = Map.of();

    public static void acceptColors(HolderGetter<Biome> getter, Map<ResourceKey<Biome>, Integer> map) {
        Map<Biome, Integer> colors = new HashMap<>();
        map.forEach((key, color) -> getter.get(key)
                .filter(Holder::isBound)
                .map(Holder::value)
                .ifPresent((biome) -> colors.put(biome, sanitizeColor(color))));
        COLORS = Map.copyOf(colors);
    }

    public static int getColor(Biome biome, double x, double z) {
        int color = COLORS.getOrDefault(biome, AetherIIColorResolvers.AETHER_GRASS_COLOR);
        int modifiedColor = biome.getModifiedSpecialEffects().getGrassColorModifier().modifyColor(x, z, sanitizeColor(color));
        return sanitizeColor(modifiedColor);
    }

    public static void sendColors(Player player) {
        if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
            Registry<Biome> registry = player.level().registryAccess().registryOrThrow(Registries.BIOME);
            AetherIINetwork.sendToPlayer(serverPlayer, new GrassTintSyncPacket(createColorMap(registry)));
        }
    }

    private static Map<ResourceKey<Biome>, Integer> createColorMap(Registry<Biome> registry) {
        Map<ResourceKey<Biome>, Integer> colors = new LinkedHashMap<>();
        addTaggedColors(registry, colors, AetherIITags.Biomes.HIGHFIELDS, 0xb5ffd0);
        addTaggedColors(registry, colors, AetherIITags.Biomes.MAGNETIC, 0xc9ffd1);
        addTaggedColors(registry, colors, AetherIITags.Biomes.ARCTIC, 0xbdf9ff);
        addTaggedColors(registry, colors, AetherIITags.Biomes.IRRADIATED, 0xffdd99);
        addTaggedColors(registry, colors, AetherIITags.Biomes.EXPANSE, 0xb5ffd0);
        return Map.copyOf(colors);
    }

    private static void addTaggedColors(Registry<Biome> registry, Map<ResourceKey<Biome>, Integer> colors, TagKey<Biome> tag, int color) {
        Optional<? extends Iterable<Holder<Biome>>> holders = registry.getTag(tag).map((holderSet) -> holderSet);
        holders.ifPresent((set) -> {
            for (Holder<Biome> holder : set) {
                holder.unwrapKey().ifPresent((key) -> colors.put(key, sanitizeColor(color)));
            }
        });
    }

    private static int sanitizeColor(int color) {
        int rgb = color & 0x00FFFFFF;
        return rgb == 0 ? AetherIIColorResolvers.AETHER_GRASS_COLOR : rgb;
    }
}
