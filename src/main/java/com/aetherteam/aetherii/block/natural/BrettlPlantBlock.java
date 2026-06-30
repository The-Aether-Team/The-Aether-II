package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.BlockUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BrettlPlantBlock extends GrowingPlantBodyBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty GROWN = AetherIIBlockStateProperties.BRETTL_GROWN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
public BrettlPlantBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, SHAPE, false);
        this.registerDefaultState(this.stateDefinition.any().setValue(GROWN, false).setValue(WATERLOGGED, false));
    }

    protected BlockState updateHeadAfterConvertedFromBody(BlockState head, BlockState body) {
        return head;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) AetherIIBlocks.BRETTL_PLANT_TIP.get();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState currentState, LevelAccessor level, BlockPos blockPos, BlockPos currentPos) {
        LevelAccessor scheduledTickAccess = level;
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, currentState, level, blockPos, currentPos);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getRawBrightness(pos.above(), 0) >= 9 && ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(25) == 0)) {
            BlockPos offsetPos = pos.relative(this.growthDirection);
            BlockState offsetState = level.getBlockState(offsetPos);
            if (offsetState.is(this.getHeadBlock()) || (offsetState.is(this) && offsetState.getValue(GROWN))) {
                if (!state.getValue(GROWN)) {
                    level.setBlockAndUpdate(pos, state.setValue(GROWN, true));
                }
            }
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!state.getValue(GROWN) && !level.isClientSide() && !player.isCreative()) {
            for (BlockPos abovePos = pos.above(); level.getBlockState(abovePos).is(AetherIIBlocks.BRETTL_PLANT.get()) || level.getBlockState(abovePos).is(AetherIIBlocks.BRETTL_PLANT_TIP.get()); abovePos = abovePos.above()) {
                BlockState aboveState = level.getBlockState(abovePos);
                dropResources(aboveState, level, abovePos, null, player, player.getMainHandItem());
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (state.getValue(GROWN)) {
            level.setBlock(pos, AetherIIBlocks.BRETTL_PLANT.get().defaultBlockState().setValue(GROWN, false), 1 | 2);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        if (state.getValue(GROWN)) {
            level.setBlock(pos, AetherIIBlocks.BRETTL_PLANT.get().defaultBlockState().setValue(GROWN, false), 1 | 2);
        }
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
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos checkedPos = pos.relative(this.growthDirection.getOpposite());
        BlockState checkedState = level.getBlockState(checkedPos);
        return this.canAttachTo(checkedState) && (checkedState.is(this.getHeadBlock()) || checkedState.is(this.getBodyBlock()) || checkedState.is(AetherIITags.Blocks.SUPPORTS_BRETTL_PLANT));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        Optional<BlockPos> optional = BlockUtil.getTopConnectedBlock(level, pos, state.getBlock(), this.growthDirection, this.getHeadBlock());
        boolean flag = false;
        if (optional.isPresent()) {
            BlockPos headPos = optional.get();
            BlockState headState = level.getBlockState(headPos);

            flag = this.getHeadBlock().isValidBonemealTarget(level, headPos, headState, false);

            if (!headState.getValue(GROWN)) {
                flag = flag || super.isValidBonemealTarget(level, pos, state, false);
            }
        }
        return flag;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() <= 0.5F;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GROWN, WATERLOGGED);
    }
}
