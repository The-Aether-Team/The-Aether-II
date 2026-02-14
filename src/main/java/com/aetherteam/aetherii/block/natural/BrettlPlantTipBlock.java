package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

public class BrettlPlantTipBlock extends GrowingPlantHeadBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<BrettlPlantTipBlock> CODEC = simpleCodec(BrettlPlantTipBlock::new);
    public static final BooleanProperty GROWN = AetherIIBlockStateProperties.BRETTL_GROWN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);
    public static final VoxelShape SHAPE_FLOWER = Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0);

    @Override
    public MapCodec<BrettlPlantTipBlock> codec() {
        return CODEC;
    }

    public BrettlPlantTipBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.1);
        this.registerDefaultState(this.stateDefinition.any().setValue(GROWN, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1;
    }

    @Override
    protected Block getBodyBlock() {
        return AetherIIBlocks.BRETTL_PLANT.get();
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos currentPos, BlockState currentState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, scheduledTickAccess, blockPos, direction, currentPos, currentState, randomSource);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            state = state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        }
        return state;
    }

    @Override
    public BlockState getStateForPlacement(RandomSource randomSource) {
        return this.defaultBlockState().setValue(AGE, 0);
    }

    @Override
    protected BlockState updateBodyAfterConvertedFromHead(BlockState head, BlockState body) {
        return super.updateBodyAfterConvertedFromHead(head, body).setValue(WATERLOGGED, head.getValue(WATERLOGGED));
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(AGE) < 2 && CommonHooks.canCropGrow(level, pos.relative(this.growthDirection), state, random.nextDouble() < 0.1)) {
            BlockPos blockpos = pos.relative(this.growthDirection);
            if (this.canGrowInto(level.getBlockState(blockpos))) {
                FluidState fluidstate = level.getFluidState(blockpos);
                if (state.getValue(AGE) == 1) {
                    level.setBlockAndUpdate(blockpos, this.getGrowIntoState(state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER).setValue(GROWN, true), level.random));
                } else {
                    level.setBlockAndUpdate(blockpos, this.getGrowIntoState(state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER), level.random));
                }
                CommonHooks.fireCropGrowPost(level, blockpos, level.getBlockState(blockpos));
            }
        }
        if (state.getValue(GROWN)) {
            if (level.getBlockState(pos.below()).hasProperty(GROWN) && level.getBlockState(pos.below(2)).hasProperty(GROWN)) {
                if (level.getFluidState(pos.below()).isEmpty()) {
                    level.setBlockAndUpdate(pos.below(), AetherIIBlocks.BRETTL_PLANT.get().defaultBlockState().setValue(GROWN, true));
                }
                if (level.getFluidState(pos.below(2)).isEmpty()) {
                    level.setBlockAndUpdate(pos.below(2), AetherIIBlocks.BRETTL_PLANT.get().defaultBlockState().setValue(GROWN, true));
                }
            }
        }
    }

    @Override
    public BlockState getMaxAgeState(BlockState state) {
        return state.setValue(AGE, 2);
    }

    @Override
    public boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) == 2;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        boolean flag = false;
        int height = state.getValue(BrettlPlantTipBlock.AGE);
        for (int i = 1; i <= height; i++) {
            BlockPos bodyPos = pos.below(i);
            BlockState bodyState = level.getBlockState(bodyPos);
            flag = flag || !bodyState.getValue(GROWN);
        }
        if (!state.getValue(GROWN)) {
            flag = flag || super.isValidBonemealTarget(level, pos, state);
        }
        return flag;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() <= 0.5F;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.getValue(AGE) < 2) {
            BlockPos blockpos = pos.relative(this.growthDirection);
            int i = Math.min(state.getValue(AGE) + 1, 2);
            int j = this.getBlocksToGrowWhenBonemealed(random);

            for (int k = 0; k < j && this.canGrowInto(level.getBlockState(blockpos)); k++) {
                BlockState newState = state.setValue(AGE, i);
                if (i == 2) {
                    newState = newState.setValue(GROWN, true);
                }
                FluidState fluidstate = level.getFluidState(blockpos);
                level.setBlockAndUpdate(blockpos, newState.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER));

                blockpos = blockpos.relative(this.growthDirection);
                i = Math.min(i + 1, 2);
            }
        } else {
            for (int i = 0; i <= 2; i++) {
                BlockPos blockpos = pos.relative(this.growthDirection.getOpposite(), i);
                if (level.getFluidState(blockpos).isEmpty()) {
                    level.setBlockAndUpdate(blockpos, level.getBlockState(blockpos).setValue(GROWN, true));
                }
            }
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos checkedPos = pos.relative(this.growthDirection.getOpposite());
        BlockState checkedState = level.getBlockState(checkedPos);
        return this.canAttachTo(checkedState) && (checkedState.is(this.getHeadBlock()) || checkedState.is(this.getBodyBlock()) || checkedState.is(AetherIITags.Blocks.BRETTL_PLANT_SURVIVES_ON));
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GrowingPlantHeadBlock.AGE, GROWN, WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(GROWN) ? SHAPE_FLOWER : SHAPE;
    }
}