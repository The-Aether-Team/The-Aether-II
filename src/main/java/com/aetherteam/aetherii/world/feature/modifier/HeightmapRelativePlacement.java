package com.aetherteam.aetherii.world.feature.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.stream.Stream;

public class HeightmapRelativePlacement extends PlacementModifier {
    public static final MapCodec<HeightmapRelativePlacement> CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(
            Heightmap.Types.CODEC.fieldOf("heightmap").forGetter((c) -> c.heightmap),
            VerticalAnchor.CODEC.fieldOf("limit").forGetter((c) -> c.limit),
            Codec.INT.optionalFieldOf("min_inclusive", Integer.MIN_VALUE).forGetter((c) -> c.minInclusive),
            Codec.INT.optionalFieldOf("max_inclusive", Integer.MAX_VALUE).forGetter((c) -> c.maxInclusive),
            Codec.BOOL.optionalFieldOf("avoid_heightmap", false).forGetter((c) -> c.avoidHeightmap)
    ).apply(i, HeightmapRelativePlacement::new));
    private final Heightmap.Types heightmap;
    private final VerticalAnchor limit;
    private final int minInclusive;
    private final int maxInclusive;
    private final boolean avoidHeightmap;

    private HeightmapRelativePlacement(Heightmap.Types heightmap, VerticalAnchor limit, int minInclusive, int maxInclusive, boolean avoidHeightmap) {
        this.heightmap = heightmap;
        this.limit = limit;
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
        this.avoidHeightmap = avoidHeightmap;
    }

    public static HeightmapRelativePlacement of(Heightmap.Types heightmap, VerticalAnchor limit, int minInclusive, int maxInclusive, boolean avoidHeightmap) {
        return new HeightmapRelativePlacement(heightmap, limit, minInclusive, maxInclusive, avoidHeightmap);
    }

    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos origin) {
        int surfaceY = context.getHeight(this.heightmap, origin.getX(), origin.getZ());
        if (surfaceY > this.limit.resolveY(context) || (surfaceY < this.limit.resolveY(context) && this.avoidHeightmap)) {
            int distance = this.maxInclusive - this.minInclusive;
            int y = surfaceY + this.minInclusive + random.nextInt(distance);
            return Stream.of(origin.atY(y));
        }
        return Stream.of();
    }

    public PlacementModifierType<?> type() {
        return AetherIIPlacementModifierTypes.HEIGHTMAP_RELATIVE_PLACEMENT.get();
    }
}
