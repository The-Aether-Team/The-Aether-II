package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.GasBlock;
import com.aetherteam.aetherii.world.feature.configuration.AcidPoolConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.function.Consumer;

public class AcidPoolFeature extends Feature<AcidPoolConfiguration> {
    public AcidPoolFeature(Codec<AcidPoolConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<AcidPoolConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        AcidPoolConfiguration config = context.config();

        int count = config.count().sample(random);
        for (int i = 0; i < count; i++) {
            this.placePool(pos.offset(config.offset().sample(random), 0, config.offset().sample(random)), level, random, config);
        }

        return true;
    }

    private void placePool(BlockPos pos, WorldGenLevel level, RandomSource random, AcidPoolConfiguration config) {
        int radius = config.radius().sample(random);
        for (int x = -radius; x < radius; x++) {
            for (int z = -radius; z < radius; z++) {
                for (int y = -radius; y < radius; y++) {
                    int volume = x * x + y * y + z * z;
                    int radiusSquared = (radius - 1) * (radius - 1);
                    int radiusOutlineSquared = radius * radius;
                    if (volume <= radiusOutlineSquared) {
                        BlockPos offsetPos = pos.offset(x, y, z);
                        if (y < 0) {
                            if (volume >= radiusSquared) {
                                if (!level.getBlockState(offsetPos).is(AetherIIBlocks.ACID)) {
                                    level.setBlock(offsetPos, AetherIIBlocks.ICHORITE.get().defaultBlockState(), 3);
                                    int limit = 1 + random.nextInt(2) + random.nextInt(5);
                                    for (int i = 1; i < limit; i++) {
                                        level.setBlock(offsetPos.below(i), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 3);
                                        if (i == limit - 1 && random.nextBoolean()) {
                                            if (!level.getBlockState(offsetPos.below(i + 1)).isSolid()) {
                                                growPointedIchorite(level, offsetPos.below(i + 1), Direction.DOWN, 1 + random.nextInt(2), false);
                                            }
                                        }
                                    }
                                }
                            } else {
                                level.setBlock(offsetPos, AetherIIBlocks.ACID.get().defaultBlockState(), 3);
                            }
                        } else {
                            level.setBlock(offsetPos, Blocks.CAVE_AIR.defaultBlockState(), 3);
                        }
                    }
                }
                for (int y = 0; y < GasBlock.MAX_VERTICAL_DISTANCE; y++) {
                    BlockPos offsetPos = pos.offset(x, y, z);
                    if (level.getBlockState(offsetPos).isAir()) {
                        BlockState gasState = GasBlock.updateDistance(AetherIIBlocks.GAS.get().defaultBlockState(), level, offsetPos);
                        if (gasState.getValue(GasBlock.HORIZONTAL_DISTANCE) < GasBlock.MAX_HORIZONTAL_DISTANCE && gasState.getValue(GasBlock.VERTICAL_DISTANCE) < GasBlock.MAX_VERTICAL_DISTANCE) {
                            level.setBlock(offsetPos, GasBlock.updateDistance(gasState, level, offsetPos), 3);
                        }
                        if (!level.getBlockState(offsetPos.above()).isAir()) {
                            level.scheduleTick(offsetPos, AetherIIBlocks.GAS.get(), 1);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }

    protected static void growPointedIchorite(LevelAccessor level, BlockPos pos, Direction direction, int height, boolean mergeTip) {
        if (isDripstoneBase(level.getBlockState(pos.relative(direction.getOpposite())))) {
            BlockPos.MutableBlockPos mutablePos = pos.mutable();
            buildBaseToTipColumn(direction, height, mergeTip, state -> {
                if (state.is(AetherIIBlocks.POINTED_ICHORITE.get())) {
                    state = state.setValue(PointedDripstoneBlock.WATERLOGGED, level.isWaterAt(mutablePos));
                }
                level.setBlock(mutablePos, state, 2);
                mutablePos.move(direction);
            });
        }
    }

    protected static void buildBaseToTipColumn(Direction direction, int height, boolean mergeTip, Consumer<BlockState> blockSetter) {
        if (height >= 3) {
            blockSetter.accept(createPointedIchorite(direction, DripstoneThickness.BASE));

            for (int i = 0; i < height - 3; i++) {
                blockSetter.accept(createPointedIchorite(direction, DripstoneThickness.MIDDLE));
            }
        }

        if (height >= 2) {
            blockSetter.accept(createPointedIchorite(direction, DripstoneThickness.FRUSTUM));
        }

        if (height >= 1) {
            blockSetter.accept(createPointedIchorite(direction, mergeTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }
    }

    private static BlockState createPointedIchorite(Direction direction, DripstoneThickness dripstoneThickness) {
        return AetherIIBlocks.POINTED_ICHORITE.get().defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, direction).setValue(PointedDripstoneBlock.THICKNESS, dripstoneThickness);
    }

    public static boolean isDripstoneBase(BlockState state) {
        return state.is(AetherIIBlocks.ICHORITE);
    }
}
