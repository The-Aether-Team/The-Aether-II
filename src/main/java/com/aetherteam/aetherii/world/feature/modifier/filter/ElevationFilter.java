package com.aetherteam.aetherii.world.feature.modifier.filter;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ElevationFilter extends PlacementFilter {
    public static final MapCodec<ElevationFilter> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    VerticalAnchor.CODEC.fieldOf("min").forGetter(filter -> filter.min),
                    VerticalAnchor.CODEC.fieldOf("max").forGetter(filter -> filter.max)
            ).apply(instance, ElevationFilter::new)
    );
    private final VerticalAnchor min;
    private final VerticalAnchor max;

    public ElevationFilter(VerticalAnchor min, VerticalAnchor max) {
        this.min = min;
        this.max = max;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return pos.getY() >= this.min.resolveY(context) && pos.getY() <= this.max.resolveY(context);
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherIIPlacementModifierTypes.ELEVATION_FILTER.get();
    }
}
