package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.function.Supplier;

public class AetherLeavesBlock extends LeavesBlock {
    private final Supplier<SimpleParticleType> leavesParticle;
    private final Supplier<Block> leavesPile;

    public AetherLeavesBlock(Properties properties, Supplier<SimpleParticleType> leavesParticle, Supplier<Block> leavesPile) {
        super(properties);
        this.leavesParticle = leavesParticle;
        this.leavesPile = leavesPile;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(40) == 0) {
            if (level.isRaining()) {
                Direction direction = Direction.get(Direction.DOWN.getAxisDirection(), Direction.Axis.Y);
                BlockPos.MutableBlockPos mutablePos = pos.mutable();
                for (int i = 1; i < 20; ++i) {
                    mutablePos.move(direction);
                    BlockState mutableState = level.getBlockState(mutablePos);
                    BlockPos abovePos = mutablePos.above();
                    BlockState aboveState = level.getBlockState(abovePos);
                    BlockState pileState = this.leavesPile.get().defaultBlockState();
                    if (!mutableState.isAir() && aboveState.isAir() && pileState.canSurvive(level, abovePos)) {
                        level.setBlock(mutablePos.above(), pileState, 2);
                        break;
                    }
                    if (level.isOutsideBuildHeight(mutablePos.getY()) || mutableState.isSolidRender(level, pos) || !mutableState.getFluidState().isEmpty() || Shapes.joinIsNotEmpty(Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0), mutableState.getCollisionShape(level, pos), BooleanOp.AND)) {
                        break;
                    }
                }
            }
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        int bound = level.isRaining() ? 100 : 200;
        if (random.nextInt(bound) == 0) {
            this.spawnLeavesParticles(level, pos, random);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!entity.isCrouching() && entity.getX() != entity.xOld && entity.getZ() != entity.zOld) {
            if (level.getRandom().nextInt(10) == 0) {
                this.spawnLeavesParticles(level, pos, level.getRandom());
            }
        }
    }

    private void spawnLeavesParticles(Level level, BlockPos pos, RandomSource random) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP)) {
            ParticleUtils.spawnParticleBelow(level, pos, random, this.leavesParticle.get());
        }
    }
}
