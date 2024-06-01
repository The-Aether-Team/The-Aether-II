package com.aetherteam.aetherii.world.tree.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class AmberootTrunkPlacer extends StraightTrunkPlacer {
    public static final Codec<AmberootTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).apply(instance, AmberootTrunkPlacer::new));

    public AmberootTrunkPlacer(int height, int heightRandA, int heightRandB) {
        super(height, heightRandA, heightRandB);
    }

    protected TrunkPlacerType<?> type() {
        return AetherIITrunkPlacerTypes.AMBEROOT_TRUNK_PLACER.get();
    }


    @Override  //TODO: Code Clean-Up
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int height, BlockPos pos, TreeConfiguration config) {
        TrunkPlacer.setDirtAt(level, blockSetter, random, pos.below(), config);
        super.placeTrunk(level, blockSetter, random, height, pos, config);

        if (random.nextInt(2) == 0) {
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX() + 1, pos.getY() + height - 1, pos.getZ()), config, state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX() - 1, pos.getY() + height - 1, pos.getZ()), config, state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX() + 2, pos.getY() + height - 1, pos.getZ()), config, state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX() - 2, pos.getY() + height - 1, pos.getZ()), config, state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX() + 3, pos.getY() + height, pos.getZ()), config);
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX() - 3, pos.getY() + height, pos.getZ()), config);
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX() + 3, pos.getY() + height + 1, pos.getZ()), config);
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX() - 3, pos.getY() + height + 1, pos.getZ()), config);
        } else {
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX(), pos.getY() + height - 1, pos.getZ() + 1), config, state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX(), pos.getY() + height - 1, pos.getZ() - 1), config, state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX(), pos.getY() + height - 1, pos.getZ() + 2), config, state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX(), pos.getY() + height - 1, pos.getZ() - 2), config, state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX(), pos.getY() + height, pos.getZ() + 3), config);
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX(), pos.getY() + height, pos.getZ() - 3), config);
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX(), pos.getY() + height + 1, pos.getZ() + 3), config);
            this.placeLog(level, blockSetter, random, new BlockPos(pos.getX(), pos.getY() + height + 1, pos.getZ() - 3), config);
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(height), 0, false));
    }
}