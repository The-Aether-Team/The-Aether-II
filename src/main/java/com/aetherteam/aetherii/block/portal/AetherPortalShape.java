package com.aetherteam.aetherii.block.portal;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Based on {@link net.minecraft.world.level.portal.PortalShape}.
 */
public class AetherPortalShape {
    private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) -> state.is(AetherIITags.Blocks.AETHER_PORTAL_BLOCKS);
    private static final float SAFE_TRAVEL_MAX_ENTITY_XY = 4.0F;
    private static final double SAFE_TRAVEL_MAX_VERTICAL_DELTA = (double) 1.0F;
    private final Direction.Axis axis;
    private final Direction rightDir;
    private final int numPortalBlocks;
    private final BlockPos bottomLeft;
    private final int height;
    private final int width;

    private AetherPortalShape(Direction.Axis p_77697_, int p_374222_, Direction p_374407_, BlockPos p_77696_, int p_374218_, int p_374477_) {
        this.axis = p_77697_;
        this.numPortalBlocks = p_374222_;
        this.rightDir = p_374407_;
        this.bottomLeft = p_77696_;
        this.width = p_374218_;
        this.height = p_374477_;
    }

    public static Optional<AetherPortalShape> findEmptyAetherPortalShape(LevelAccessor level, BlockPos bottomLeft, Direction.Axis axis) {
        return findAetherPortalShape(level, bottomLeft, (p_77727_) -> p_77727_.isValid() && p_77727_.numPortalBlocks == 0, axis);
    }

    public static Optional<AetherPortalShape> findAetherPortalShape(LevelAccessor level, BlockPos bottomLeft, Predicate<AetherPortalShape> predicate, Direction.Axis axis) {
        Optional<AetherPortalShape> optional = Optional.of(findAnyShape(level, bottomLeft, axis)).filter(predicate);
        if (optional.isPresent()) {
            return optional;
        } else {
            Direction.Axis direction$axis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(findAnyShape(level, bottomLeft, direction$axis)).filter(predicate);
        }
    }

    public static AetherPortalShape findAnyShape(BlockGetter p_374054_, BlockPos p_374346_, Direction.Axis p_374516_) {
        Direction direction = p_374516_ == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        BlockPos blockpos = calculateBottomLeft(p_374054_, direction, p_374346_);
        if (blockpos == null) {
            return new AetherPortalShape(p_374516_, 0, direction, p_374346_, 0, 0);
        } else {
            int i = calculateWidth(p_374054_, blockpos, direction);
            if (i == 0) {
                return new AetherPortalShape(p_374516_, 0, direction, blockpos, 0, 0);
            } else {
                MutableInt mutableint = new MutableInt();
                int j = calculateHeight(p_374054_, blockpos, direction, i, mutableint);
                return new AetherPortalShape(p_374516_, mutableint.getValue(), direction, blockpos, i, j);
            }
        }
    }

    @Nullable
    private static BlockPos calculateBottomLeft(BlockGetter p_374347_, Direction p_374365_, BlockPos p_77734_) {
        for (int i = Math.max(p_374347_.getMinY(), p_77734_.getY() - 21); p_77734_.getY() > i && isEmpty(p_374347_.getBlockState(p_77734_.below())); p_77734_ = p_77734_.below()) {
        }

        Direction direction = p_374365_.getOpposite();
        int j = getDistanceUntilEdgeAboveFrame(p_374347_, p_77734_, direction) - 1;
        return j < 0 ? null : p_77734_.relative(direction, j);
    }

    private static int calculateWidth(BlockGetter p_374528_, BlockPos p_374039_, Direction p_374180_) {
        int i = getDistanceUntilEdgeAboveFrame(p_374528_, p_374039_, p_374180_);
        return i >= 2 && i <= 21 ? i : 0;
    }

    private static int getDistanceUntilEdgeAboveFrame(BlockGetter p_374084_, BlockPos p_77736_, Direction p_77737_) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int i = 0; i <= 21; ++i) {
            blockpos$mutableblockpos.set(p_77736_).move(p_77737_, i);
            BlockState blockstate = p_374084_.getBlockState(blockpos$mutableblockpos);
            if (!isEmpty(blockstate)) {
                if (FRAME.test(blockstate, p_374084_, blockpos$mutableblockpos)) {
                    return i;
                }
                break;
            }

            BlockState blockstate1 = p_374084_.getBlockState(blockpos$mutableblockpos.move(Direction.DOWN));
            if (!FRAME.test(blockstate1, p_374084_, blockpos$mutableblockpos)) {
                break;
            }
        }

        return 0;
    }

    private static int calculateHeight(BlockGetter p_374198_, BlockPos p_374414_, Direction p_374486_, int p_374126_, MutableInt p_374165_) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int i = getDistanceUntilTop(p_374198_, p_374414_, p_374486_, blockpos$mutableblockpos, p_374126_, p_374165_);
        return i >= 3 && i <= 21 && hasTopFrame(p_374198_, p_374414_, p_374486_, blockpos$mutableblockpos, p_374126_, i) ? i : 0;
    }

    private static boolean hasTopFrame(BlockGetter p_374223_, BlockPos p_374398_, Direction p_374129_, BlockPos.MutableBlockPos p_77731_, int p_77732_, int p_374112_) {
        for (int i = 0; i < p_77732_; ++i) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = p_77731_.set(p_374398_).move(Direction.UP, p_374112_).move(p_374129_, i);
            if (!FRAME.test(p_374223_.getBlockState(blockpos$mutableblockpos), p_374223_, blockpos$mutableblockpos)) {
                return false;
            }
        }

        return true;
    }

    private static int getDistanceUntilTop(BlockGetter p_374443_, BlockPos p_374231_, Direction p_374062_, BlockPos.MutableBlockPos p_77729_, int p_374313_, MutableInt p_374330_) {
        for (int i = 0; i < 21; ++i) {
            p_77729_.set(p_374231_).move(Direction.UP, i).move(p_374062_, -1);
            if (!FRAME.test(p_374443_.getBlockState(p_77729_), p_374443_, p_77729_)) {
                return i;
            }

            p_77729_.set(p_374231_).move(Direction.UP, i).move(p_374062_, p_374313_);
            if (!FRAME.test(p_374443_.getBlockState(p_77729_), p_374443_, p_77729_)) {
                return i;
            }

            for (int j = 0; j < p_374313_; ++j) {
                p_77729_.set(p_374231_).move(Direction.UP, i).move(p_374062_, j);
                BlockState blockstate = p_374443_.getBlockState(p_77729_);
                if (!isEmpty(blockstate)) {
                    return i;
                }

                if (blockstate.is(AetherIIBlocks.AETHER_PORTAL)) {
                    p_374330_.increment();
                }
            }
        }

        return 21;
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) || state.is(AetherIIBlocks.AETHER_PORTAL);
    }

    public boolean isValid() {
        return this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    public void createPortalBlocks(LevelAccessor p_374419_) {
        BlockState blockstate = AetherIIBlocks.AETHER_PORTAL.get().defaultBlockState().setValue(AetherPortalBlock.AXIS, this.axis);
        BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach((p_374024_) -> p_374419_.setBlock(p_374024_, blockstate, 18));
    }

    public boolean isComplete() {
        return this.isValid() && this.numPortalBlocks == this.width * this.height;
    }

    public static Vec3 getRelativePosition(BlockUtil.FoundRectangle foundRectangle, Direction.Axis axis, Vec3 pos, EntityDimensions entityDimensions) {
        double d0 = (double) foundRectangle.axis1Size - (double) entityDimensions.width();
        double d1 = (double) foundRectangle.axis2Size - (double) entityDimensions.height();
        BlockPos blockpos = foundRectangle.minCorner;
        double d2;
        if (d0 > (double) 0.0F) {
            double d3 = (double) blockpos.get(axis) + (double) entityDimensions.width() / (double) 2.0F;
            d2 = Mth.clamp(Mth.inverseLerp(pos.get(axis) - d3, 0.0F, d0), 0.0F, 1.0F);
        } else {
            d2 = 0.5F;
        }

        double d5;
        if (d1 > (double) 0.0F) {
            Direction.Axis direction$axis = Direction.Axis.Y;
            d5 = Mth.clamp(Mth.inverseLerp(pos.get(direction$axis) - (double) blockpos.get(direction$axis), 0.0F, d1), 0.0F, 1.0F);
        } else {
            d5 = 0.0F;
        }

        Direction.Axis direction$axis1 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        double d4 = pos.get(direction$axis1) - ((double) blockpos.get(direction$axis1) + (double) 0.5F);
        return new Vec3(d2, d5, d4);
    }

    public static Vec3 findCollisionFreePosition(Vec3 pos, ServerLevel level, Entity entity, EntityDimensions dimensions) {
        if (!(dimensions.width() > 4.0F) && !(dimensions.height() > 4.0F)) {
            double d0 = (double) dimensions.height() / (double) 2.0F;
            Vec3 vec3 = pos.add(0.0F, d0, 0.0F);
            VoxelShape voxelshape = Shapes.create(AABB.ofSize(vec3, dimensions.width(), 0.0F, dimensions.width()).expandTowards(0.0F, 1.0F, 0.0F).inflate(1.0E-6));
            Optional<Vec3> optional = level.findFreePosition(entity, voxelshape, vec3, dimensions.width(), dimensions.height(), dimensions.width());
            Optional<Vec3> optional1 = optional.map((p_259019_) -> p_259019_.subtract(0.0F, d0, 0.0F));
            return optional1.orElse(pos);
        } else {
            return pos;
        }
    }
}