package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.level.HighlandsSpecialEffects;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.function.Supplier;

public class AetherLeavesBlock extends LeavesBlock {
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
    private final Supplier<SimpleParticleType> leavesParticle;
    private final Supplier<Block> leavesPile;

    public AetherLeavesBlock(Properties properties, Supplier<SimpleParticleType> leavesParticle, Supplier<Block> leavesPile) {
        super(properties);
        this.leavesParticle = leavesParticle;
        this.leavesPile = leavesPile;
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 7).setValue(PERSISTENT, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE).setValue(SNOWY, Boolean.FALSE));
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
                    if (Block.canSupportCenter(level, mutablePos, Direction.UP) && aboveState.isAir() && pileState.canSurvive(level, abovePos)) {
                        level.setBlock(mutablePos.above(), pileState, 2);
                        break;
                    }
                    if (level.isOutsideBuildHeight(mutablePos.getY()) || mutableState.isSolidRender(level, mutablePos) || !mutableState.getFluidState().isEmpty() || Shapes.joinIsNotEmpty(Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0), mutableState.getCollisionShape(level, mutablePos), BooleanOp.AND)) {
                        break;
                    }
                }
            }
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.UP && !pState.getValue(SNOWY) && (pFacingState.is(AetherIIBlocks.ARCTIC_SNOW) || pFacingState.is(AetherIIBlocks.ARCTIC_SNOW_BLOCK))
                ? pState.setValue(SNOWY, true)
                : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int bound = level.isRaining() ? 100 : 200;
        if (random.nextInt(bound) == 0) {
            this.spawnLeavesParticles(level, pos, random);
        }
        if (level instanceof ClientLevel clientLevel && clientLevel.effects() instanceof HighlandsSpecialEffects) {
            if (level.isRainingAt(pos.above())) {
                if (random.nextInt(15) == 1) {
                    BlockPos belowPos = pos.below();
                    BlockState belowState = level.getBlockState(belowPos);
                    if (!belowState.canOcclude() || !belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
                        ParticleUtils.spawnParticleBelow(level, pos, random, AetherIIParticleTypes.DRIPPING_WATER.get());
                    }
                }
            }
        } else {
            super.animateTick(state, level, pos, random);
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DISTANCE, PERSISTENT, WATERLOGGED, SNOWY);
    }
}