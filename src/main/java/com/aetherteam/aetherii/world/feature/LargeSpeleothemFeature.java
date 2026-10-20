package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.feature.configuration.LargeSpeleothemConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * [CODE COPY] - {@link net.minecraft.world.level.levelgen.feature.LargeDripstoneFeature}.
 */
//TODO: replace with the vanilla feature in 27.1

public class LargeSpeleothemFeature extends Feature<LargeSpeleothemConfiguration> {
    public LargeSpeleothemFeature(Codec<LargeSpeleothemConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<LargeSpeleothemConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        LargeSpeleothemConfiguration config = context.config();
        RandomSource random = context.random();
        Block block = config.block().getState(level, context.random(), origin).getBlock();
        if (!SpeleothemUtils.isEmptyOrWater(level, origin)) {
            return false;
        } else {
            Optional<Column> column = Column.scan(level, origin, config.floorToCeilingSearchRange(), SpeleothemUtils::isEmptyOrWater, SpeleothemUtils::isDripstoneBaseOrLava);
            if (!column.isEmpty() && column.get() instanceof Column.Range) {
                Column.Range columnRange = (Column.Range) column.get();
                if (columnRange.height() < 4) {
                    return false;
                } else {
                    int maxColumnRadiusBasedOnColumnHeight = (int) ((float) columnRange.height() * config.maxColumnRadiusToCaveHeightRatio());
                    int maxColumnRadius = Mth.clamp(maxColumnRadiusBasedOnColumnHeight, config.columnRadius().minInclusive(), config.columnRadius().maxInclusive());
                    int radius = Mth.randomBetweenInclusive(random, config.columnRadius().minInclusive(), maxColumnRadius);
                    LargeDripstone stalactite = makeDripstone(origin.atY(columnRange.ceiling() - 1), false, random, radius, config.stalactiteBluntness(), config.heightScale());
                    LargeDripstone stalagmite = makeDripstone(origin.atY(columnRange.floor() + 1), true, random, radius, config.stalagmiteBluntness(), config.heightScale());
                    WindOffsetter wind;
                    if (stalactite.isSuitableForWind(config) && stalagmite.isSuitableForWind(config)) {
                        wind = new WindOffsetter(origin.getY(), random, config.windSpeed());
                    } else {
                        wind = LargeSpeleothemFeature.WindOffsetter.noWind();
                    }

                    boolean stalactiteBaseEmbeddedInStone = stalactite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, wind);
                    boolean stalagmiteBaseEmbeddedInStone = stalagmite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, wind);
                    if (stalactiteBaseEmbeddedInStone) {
                        stalactite.placeBlocks(block, level, random, wind);
                    }

                    if (stalagmiteBaseEmbeddedInStone) {
                        stalagmite.placeBlocks(block, level, random, wind);
                    }

                    if (SharedConstants.DEBUG_LARGE_DRIPSTONE) {
                        this.placeDebugMarkers(block, level, origin, columnRange, wind);
                    }

                    return true;
                }
            } else {
                return false;
            }
        }
    }

    private static LargeDripstone makeDripstone(BlockPos root, boolean pointingUp, RandomSource random, int radius, FloatProvider bluntness, FloatProvider heightScale) {
        return new LargeDripstone(root, pointingUp, radius, bluntness.sample(random), heightScale.sample(random));
    }

    private void placeDebugMarkers(Block block, WorldGenLevel level, BlockPos origin, Column.Range range, WindOffsetter wind) {
        level.setBlock(wind.offset(origin.atY(range.ceiling() - 1)), Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
        level.setBlock(wind.offset(origin.atY(range.floor() + 1)), Blocks.GOLD_BLOCK.defaultBlockState(), 2);

        for (BlockPos.MutableBlockPos pos = origin.atY(range.floor() + 2).mutable(); pos.getY() < range.ceiling() - 1; pos.move(Direction.UP)) {
            BlockPos windAdjustedPos = wind.offset(pos);
            if (SpeleothemUtils.isEmptyOrWater(level, windAdjustedPos) || level.getBlockState(windAdjustedPos).is(block)) {
                level.setBlock(windAdjustedPos, Blocks.CREEPER_HEAD.defaultBlockState(), 2);
            }
        }

    }

    private static final class LargeDripstone {
        private BlockPos root;
        private final boolean pointingUp;
        private int radius;
        private final double bluntness;
        private final double scale;

        private LargeDripstone(BlockPos root, boolean pointingUp, int radius, double bluntness, double scale) {
            this.root = root;
            this.pointingUp = pointingUp;
            this.radius = radius;
            this.bluntness = bluntness;
            this.scale = scale;
        }

        private int getHeight() {
            return this.getHeightAtRadius(0.0F);
        }

        private int getMinY() {
            return this.pointingUp ? this.root.getY() : this.root.getY() - this.getHeight();
        }

        private int getMaxY() {
            return !this.pointingUp ? this.root.getY() : this.root.getY() + this.getHeight();
        }

        private boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel level, WindOffsetter wind) {
            while (this.radius > 1) {
                BlockPos.MutableBlockPos newRoot = this.root.mutable();
                int maxTries = Math.min(10, this.getHeight());

                for (int i = 0; i < maxTries; ++i) {
                    if (level.getBlockState(newRoot).is(Blocks.LAVA)) {
                        return false;
                    }

                    if (SpeleothemUtils.isCircleMostlyEmbeddedInStone(level, wind.offset(newRoot), this.radius)) {
                        this.root = newRoot;
                        return true;
                    }

                    newRoot.move(this.pointingUp ? Direction.DOWN : Direction.UP);
                }

                this.radius /= 2;
            }

            return false;
        }

        private int getHeightAtRadius(float checkRadius) {
            return (int) SpeleothemUtils.getDripstoneHeight(checkRadius, this.radius, this.scale, this.bluntness);
        }

        private void placeBlocks(Block block, WorldGenLevel level, RandomSource random, WindOffsetter wind) {
            for (int dx = -this.radius; dx <= this.radius; ++dx) {
                for (int dz = -this.radius; dz <= this.radius; ++dz) {
                    float currentRadius = Mth.sqrt((float) (dx * dx + dz * dz));
                    if (!(currentRadius > (float) this.radius)) {
                        int height = this.getHeightAtRadius(currentRadius);
                        if (height > 0) {
                            if ((double) random.nextFloat() < 0.2) {
                                height = (int) ((float) height * Mth.randomBetween(random, 0.8F, 1.0F));
                            }

                            BlockPos.MutableBlockPos pos = this.root.offset(dx, 0, dz).mutable();
                            boolean hasBeenOutOfStone = false;
                            int maxY = this.pointingUp ? level.getHeight(Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) : Integer.MAX_VALUE;

                            for (int i = 0; i < height && pos.getY() < maxY; ++i) {
                                BlockPos windAdjustedPos = wind.offset(pos);
                                if (SpeleothemUtils.isEmptyOrWaterOrLava(level, windAdjustedPos)) {
                                    hasBeenOutOfStone = true;
                                    Block speleothemBlock = SharedConstants.DEBUG_LARGE_DRIPSTONE ? Blocks.GLASS : block;
                                    level.setBlock(windAdjustedPos, speleothemBlock.defaultBlockState(), 2);
                                } else if (hasBeenOutOfStone && level.getBlockState(windAdjustedPos).is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS)) {
                                    break;
                                }

                                pos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
                            }
                        }
                    }
                }
            }

        }

        private boolean isSuitableForWind(LargeSpeleothemConfiguration config) {
            return this.radius >= config.minRadiusForWind() && this.bluntness >= (double) config.minBluntnessForWind();
        }
    }

    private static final class WindOffsetter {
        private final int originY;
        private final @Nullable Vec3 windSpeed;

        private WindOffsetter(int originY, RandomSource random, FloatProvider windSpeedRange) {
            this.originY = originY;
            float speed = windSpeedRange.sample(random);
            float direction = Mth.randomBetween(random, 0.0F, (float) Math.PI);
            this.windSpeed = new Vec3(Mth.cos(direction) * speed, 0.0F, Mth.sin(direction) * speed);
        }

        private WindOffsetter() {
            this.originY = 0;
            this.windSpeed = null;
        }

        private static WindOffsetter noWind() {
            return new WindOffsetter();
        }

        private BlockPos offset(BlockPos pos) {
            if (this.windSpeed == null) {
                return pos;
            } else {
                int dy = this.originY - pos.getY();
                Vec3 totalWindAdjust = this.windSpeed.scale(dy);
                return pos.offset(Mth.floor(totalWindAdjust.x), 0, Mth.floor(totalWindAdjust.z));
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.levelgen.feature.DripstoneUtils}.
     */

    public static class SpeleothemUtils {

        protected static double getDripstoneHeight(double xzDistanceFromCenter, double dripstoneRadius, double scale, double bluntness) {
            if (xzDistanceFromCenter < bluntness) {
                xzDistanceFromCenter = bluntness;
            }

            double cutoff = 0.384;
            double r = xzDistanceFromCenter / dripstoneRadius * 0.384;
            double part1 = (double) 0.75F * Math.pow(r, 1.3333333333333333);
            double part2 = Math.pow(r, 0.6666666666666666);
            double part3 = 0.3333333333333333 * Math.log(r);
            double heightRelativeToMaxRadius = scale * (part1 - part2 - part3);
            heightRelativeToMaxRadius = Math.max(heightRelativeToMaxRadius, 0.0F);
            return heightRelativeToMaxRadius / 0.384 * dripstoneRadius;
        }

        protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel level, BlockPos center, int xzRadius) {
            if (isEmptyOrWaterOrLava(level, center)) {
                return false;
            } else {
                float arcLength = 6.0F;
                float angleIncrement = 6.0F / (float) xzRadius;

                for (float angle = 0.0F; angle < ((float) Math.PI * 2F); angle += angleIncrement) {
                    int dx = (int) (Mth.cos(angle) * (float) xzRadius);
                    int dz = (int) (Mth.sin(angle) * (float) xzRadius);
                    if (isEmptyOrWaterOrLava(level, center.offset(dx, 0, dz))) {
                        return false;
                    }
                }

                return true;
            }
        }

        protected static boolean isEmptyOrWater(LevelAccessor level, BlockPos pos) {
            return level.isStateAtPosition(pos, SpeleothemUtils::isEmptyOrWater);
        }

        protected static boolean isEmptyOrWaterOrLava(LevelAccessor level, BlockPos pos) {
            return level.isStateAtPosition(pos, SpeleothemUtils::isEmptyOrWaterOrLava);
        }

        protected static void buildBaseToTipColumn(Block pointedBlock, Direction direction, int totalLength, boolean mergedTip, Consumer<BlockState> consumer) {
            if (totalLength >= 3) {
                consumer.accept(createPointedDripstone(pointedBlock, direction, DripstoneThickness.BASE));

                for (int i = 0; i < totalLength - 3; ++i) {
                    consumer.accept(createPointedDripstone(pointedBlock, direction, DripstoneThickness.MIDDLE));
                }
            }

            if (totalLength >= 2) {
                consumer.accept(createPointedDripstone(pointedBlock, direction, DripstoneThickness.FRUSTUM));
            }

            if (totalLength >= 1) {
                consumer.accept(createPointedDripstone(pointedBlock, direction, mergedTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
            }

        }

        private static BlockState createPointedDripstone(Block pointedBlock, Direction direction, DripstoneThickness thickness) {
            return pointedBlock.defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, direction).setValue(PointedDripstoneBlock.THICKNESS, thickness);
        }

        public static boolean isDripstoneBaseOrLava(BlockState state) {
            return isDripstoneBase(state) || state.is(Blocks.LAVA);
        }

        public static boolean isDripstoneBase(BlockState state) {
            return state.is(AetherIIBlocks.ICHORITE) || state.is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS);
        }

        public static boolean isEmptyOrWater(BlockState state) {
            return state.isAir() || state.is(Blocks.WATER);
        }

        public static boolean isEmptyOrWaterOrLava(BlockState state) {
            return state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA);
        }
    }
}