package com.aetherteam.aetherii.block.natural;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingLeavesBlock extends Block implements BonemealableBlock {
    public static final MapCodec<CaveVinesBlock> CODEC = simpleCodec(CaveVinesBlock::new);
    private static final VoxelShape SHAPE_BASE = Block.column(14.0F, 0.0F, 16.0F);
    private static final VoxelShape SHAPE_TIP = Block.column(14.0F, 2.0F, 16.0F);
    public static final BooleanProperty TIP = BlockStateProperties.TIP;;

    @Override
    public MapCodec<CaveVinesBlock> codec() {
        return CODEC;
    }

    public HangingLeavesBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TIP, true));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return this.canStayAtPosition(level, pos);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (!this.canStayAtPosition(level, pos)) {
            ticks.scheduleTick(pos, this, 1);
        }
        return state.setValue(TIP, !level.getBlockState(pos.below()).is(this));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!this.canStayAtPosition(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    private boolean canStayAtPosition(BlockGetter level, BlockPos pos) {
        BlockPos neighbourPos = pos.relative(Direction.UP);
        BlockState blockState = level.getBlockState(neighbourPos);
        return MultifaceBlock.canAttachTo(level, Direction.UP, neighbourPos, blockState) || blockState.is(this);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(TIP) ? SHAPE_TIP : SHAPE_BASE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        BlockPos growPos = this.getTip(level, pos).below();
        return this.canGrowInto(level.getBlockState(growPos)) && level.isInsideBuildHeight(growPos);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos tipPos = this.getTip(level, pos).below();
        if (this.canGrowInto(level.getBlockState(tipPos))) {
            level.setBlockAndUpdate(tipPos, state.setValue(TIP, true));
        }
    }

    public BlockPos getTip(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos forwardPos = pos.mutable();

        BlockState forwardState;
        do {
            forwardPos.move(Direction.DOWN);
            forwardState = level.getBlockState(forwardPos);
        } while (forwardState.is(this));

        return forwardPos.relative(Direction.UP).immutable();
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir();
    }
}
