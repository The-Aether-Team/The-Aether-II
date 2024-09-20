package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.feature.configuration.AetherLakeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

/**
 * [CODE COPY] - {@link net.minecraft.world.level.levelgen.feature.LakeFeature}.<br><br>
 * Modified to be for water only.
 */
@SuppressWarnings("deprecation")
public class AetherLakeFeature extends Feature<AetherLakeConfiguration> {
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public AetherLakeFeature(Codec<AetherLakeConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<AetherLakeConfiguration> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        AetherLakeConfiguration aetherLakeConfiguration = context.config();
        if (blockPos.getY() <= level.getMinBuildHeight() + 4) {
            return false;
        } else {
            blockPos = blockPos.below(4);
            boolean[] booleans = new boolean[2048];
            int i = random.nextInt(4) + 4;

            for (int j = 0; j < i; ++j) {
                double xWidth = (random.nextInt(3) == 0 ? (0.5 + (random.nextDouble() / context.config().shrinkScale().sample(random))) : random.nextDouble()) * 6.0 + 3.0;
                double yDepth = (random.nextInt(3) == 0 ? (0.5 + (random.nextDouble() / context.config().shrinkScale().sample(random))) : random.nextDouble()) * 4.0 + 2.0;
                double zWidth = (random.nextInt(3) == 0 ? (0.5 + (random.nextDouble() / context.config().shrinkScale().sample(random))) : random.nextDouble()) * 6.0 + 3.0;
                double xSquish = (random.nextInt(3) == 0 ? (0.5 + (random.nextDouble() / 2)) : random.nextDouble()) * (16.0 - xWidth - 2.0) + 1.0 + xWidth / 2.0;
                double ySquish = (random.nextInt(3) == 0 ? (0.5 + (random.nextDouble() / 2)) : random.nextDouble()) * (8.0 - yDepth - 4.0) + 2.0 + yDepth / 2.0;
                double zSquish = (random.nextInt(3) == 0 ? (0.5 + (random.nextDouble() / 2)) : random.nextDouble()) * (16.0 - zWidth - 2.0) + 1.0 + zWidth / 2.0;

                for (int l = 1; l < 15; ++l) {
                    for (int i1 = 1; i1 < 15; ++i1) {
                        for (int j1 = 1; j1 < 7; ++j1) {
                            double d6 = ((double) l - xSquish) / (xWidth / 2.0);
                            double d7 = ((double) j1 - ySquish) / (yDepth / 2.0);
                            double d8 = ((double) i1 - zSquish) / (zWidth / 2.0);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0) {
                                booleans[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            BlockState fluidBlockState = aetherLakeConfiguration.fluid().getState(random, blockPos);

            for (int k1 = 0; k1 < 16; ++k1) {
                for (int k = 0; k < 16; ++k) {
                    for (int l2 = 0; l2 < 8; ++l2) {
                        boolean flag = !booleans[(k1 * 16 + k) * 8 + l2] && (k1 < 15 && booleans[((k1 + 1) * 16 + k) * 8 + l2] || k1 > 0 && booleans[((k1 - 1) * 16 + k) * 8 + l2] || k < 15 && booleans[(k1 * 16 + k + 1) * 8 + l2] || k > 0 && booleans[(k1 * 16 + (k - 1)) * 8 + l2] || l2 < 7 && booleans[(k1 * 16 + k) * 8 + l2 + 1] || l2 > 0 && booleans[(k1 * 16 + k) * 8 + (l2 - 1)]);
                        if (flag) {
                            BlockState offsetState = level.getBlockState(blockPos.offset(k1, l2, k));
                            if (l2 >= 4 && offsetState.liquid()) {
                                return false;
                            }
                            if (l2 < 4 && !offsetState.isSolid() && level.getBlockState(blockPos.offset(k1, l2, k)) != fluidBlockState) {
                                return false;
                            }
                        }
                    }
                }
            }

            for (int l1 = 0; l1 < 16; ++l1) {
                for (int i2 = 0; i2 < 16; ++i2) {
                    for (int i3 = 0; i3 < 8; ++i3) {
                        if (booleans[(l1 * 16 + i2) * 8 + i3]) {
                            BlockPos offsetPos = blockPos.offset(l1, i3, i2);
                            if (this.canReplaceBlock(level.getBlockState(offsetPos))) {
                                boolean flag1 = i3 >= 4;
                                level.setBlock(offsetPos, flag1 ? AIR : fluidBlockState, 2);
                                if (flag1) {
                                    level.scheduleTick(offsetPos, AIR.getBlock(), 0);
                                    this.markAboveForPostProcessing(level, offsetPos);
                                }
                            }
                        }
                    }
                }
            }

            for (int i2 = 0; i2 < 16; ++i2) {
                for (int j3 = 0; j3 < 16; ++j3) {
                    for (int j4 = 0; j4 < 8; ++j4) {
                        if (booleans[(i2 * 16 + j3) * 8 + j4]) {
                            BlockPos offsetPos = blockPos.offset(i2, j4 - 1, j3);
                            BlockState offsetState = level.getBlockState(offsetPos);
                            if (offsetState.is(AetherIITags.Blocks.AETHER_DIRT)) {
                                level.setBlock(offsetPos, aetherLakeConfiguration.top().getState(random, offsetPos), 2);
                            }
                            if (random.nextBoolean()) {
                                for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                                    BlockPos offsetPos2 = offsetPos.relative(direction);
                                    if ((level.getBlockState(offsetPos2).is(AetherIITags.Blocks.AETHER_DIRT) || level.getBlockState(offsetPos2).is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS))) {
                                        if ((!level.getBlockState(offsetPos2.above()).liquid() && !level.getBlockState(offsetPos2).is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS)) || random.nextInt(3) != 0) {
                                            level.setBlock(offsetPos2, aetherLakeConfiguration.top().getState(random, offsetPos2), 2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (fluidBlockState.getFluidState().is(FluidTags.WATER)) {
                for (int k2 = 0; k2 < 16; ++k2) {
                    for (int k3 = 0; k3 < 16; ++k3) {
                        BlockPos offsetPos = blockPos.offset(k2, 4, k3);
                        if (level.getBiome(offsetPos).value().shouldFreeze(level, offsetPos, false) && this.canReplaceBlock(level.getBlockState(offsetPos))) {
                            level.setBlock(offsetPos, AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState(), 2);
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean canReplaceBlock(BlockState state) {
        return !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }
}