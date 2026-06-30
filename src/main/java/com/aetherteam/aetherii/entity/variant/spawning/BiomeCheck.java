package com.aetherteam.aetherii.entity.variant.spawning;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import com.aetherteam.aetherii.entity.variant.SpawnCondition;
import com.aetherteam.aetherii.entity.variant.SpawnContext;
import net.minecraft.world.level.biome.Biome;

public record BiomeCheck(HolderSet<Biome> requiredBiomes) implements SpawnCondition {
    public static final MapCodec<BiomeCheck> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(BiomeCheck::requiredBiomes)
    ).apply(instance, BiomeCheck::new));

    @Override
    public boolean test(SpawnContext context) {
        return this.requiredBiomes.contains(context.level().getBiome(context.pos()));
    }

    @Override
    public MapCodec<BiomeCheck> codec() {
        return MAP_CODEC;
    }
}
