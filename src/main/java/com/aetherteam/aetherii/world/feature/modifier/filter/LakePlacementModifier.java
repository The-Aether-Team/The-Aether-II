package com.aetherteam.aetherii.world.feature.modifier.filter;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class LakePlacementModifier extends PlacementModifier {
    public static final MapCodec<LakePlacementModifier> CODEC = MapCodec.unit(LakePlacementModifier::new);

    @Override
    public Stream<BlockPos> getPositions(PlacementContext placementContext, RandomSource randomSource, BlockPos blockPos) {
        Set<BlockPos> positions = new TreeSet<>();

        int chunkX = blockPos.getX() - (blockPos.getX() % 16);
        int chunkZ = blockPos.getZ() - (blockPos.getZ() % 16);
        int height = ConstantInt.of(124).getValue();
        double noiseStartValue = 0.3;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;

                BlockPos layerPos = new BlockPos(xCoord, height, zCoord);
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(1), noiseStartValue + 0.025, 0.8));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(2), noiseStartValue + 0.04, 0.75));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(3), noiseStartValue + 0.045, 0.7));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(4), noiseStartValue + 0.05, 0.625));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(5), noiseStartValue + 0.055, 0.55));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(6), noiseStartValue + 0.06, 0.475));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(7), noiseStartValue + 0.065, 0.4));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(8), noiseStartValue + 0.07, 0.3));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(9), noiseStartValue + 0.075, 0.2));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(10), noiseStartValue + 0.082, 0.1));
                positions.addAll(this.gatherLakeLayer(placementContext.getLevel(), layerPos.below(11), noiseStartValue + 0.05, 0.035));
            }
        }
        return positions.stream();
    }

    public Set<BlockPos> gatherLakeLayer(WorldGenLevel level, BlockPos pos, double noiseValue, double floorNoiseValue) {
        Set<BlockPos> positions = new TreeSet<>();

        HolderGetter<DensityFunction> function = level.registryAccess().lookupOrThrow(Registries.DENSITY_FUNCTION);
        DensityFunction lakeNoise = AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_NOISE);
        DensityFunction lakeFloorNoise = AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_FLOOR);
        DensityFunction lakeBarrierNoise = AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_BARRIER);

        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());

        lakeNoise.mapAll(visitor);
        lakeFloorNoise.mapAll(visitor);
        lakeBarrierNoise.mapAll(visitor);

        double density = lakeNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        double floor = lakeFloorNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        double barrier = lakeBarrierNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        int thickness = calculateThickness(barrier, pos.getY(), ConstantInt.of(124).getValue());

        // Determines the block to place at specific noise values
        if (density > noiseValue && density < 1.5) {
            if (floor < floorNoiseValue) {
                for (int i = 0; i < barrier; i++) {
                    if (!level.isEmptyBlock(pos)
                            && !level.isEmptyBlock(pos.east(thickness))
                            && !level.isEmptyBlock(pos.north(thickness))
                            && !level.isEmptyBlock(pos.south(thickness))
                            && !level.isEmptyBlock(pos.west(thickness))
                            && !level.isEmptyBlock(pos.below(2))
                            && !level.getBlockState(pos.above()).isSolid()
                    ) {
                        positions.add(pos);
                    }
                }
            }
        }
        return positions;
    }

    public int calculateThickness(double barrier, int y, int height) {
        return (int) (y == height ? barrier / 2 : barrier);
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherIIPlacementModifierTypes.LAKE_PLACEMENT.get();
    }
}
