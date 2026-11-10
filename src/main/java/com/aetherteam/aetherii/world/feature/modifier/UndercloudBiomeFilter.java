package com.aetherteam.aetherii.world.feature.modifier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.*;

public class UndercloudBiomeFilter extends PlacementFilter {
    public static final MapCodec<UndercloudBiomeFilter> CODEC = RecordCodecBuilder.mapCodec((codec) -> codec.group(
            ResourceKey.codec(Registries.BIOME).fieldOf("biome_exception").forGetter((filter) -> filter.biomeException)
    ).apply(codec, UndercloudBiomeFilter::new));
    private final ResourceKey<Biome> biomeException;

    public UndercloudBiomeFilter(ResourceKey<Biome> biomeException) {
        this.biomeException = biomeException;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos origin) {
        PlacedFeature feature = context.topFeature().orElseThrow(() -> new IllegalStateException("Tried to biome check an unregistered feature, or a feature that should not restrict the biome"));
        int lowestSurfaceY = 100;
        Holder<Biome> currentBiome = context.getLevel().getBiome(origin);
        if (currentBiome.is(this.biomeException)) {
            Holder<Biome> surfaceBiome = context.getLevel().getBiome(origin.atY(lowestSurfaceY));
            return context.generator().getBiomeGenerationSettings(surfaceBiome).hasFeature(feature);
        } else {
            return context.generator().getBiomeGenerationSettings(currentBiome).hasFeature(feature);
        }
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherIIPlacementModifierTypes.UNDERCLOUD_BIOME_FILTER.get();
    }
}
