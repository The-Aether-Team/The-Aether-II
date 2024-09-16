package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nullable;

public class OrangeTreeBlock extends AetherBushBlock implements BonemealableBlock {
    public static final int SINGLE_AGE_MAX = 1;
    public static final int DOUBLE_AGE_MAX = 4;

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;

    private static final VoxelShape AGE_0_BOTTOM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    private static final VoxelShape AGE_1_BOTTOM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
    private static final VoxelShape AGE_2_TOP_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    private static final VoxelShape GENERAL_TOP_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);
    private static final VoxelShape GENERAL_BOTTOM_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public OrangeTreeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, AGE);
    }

    /**
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int age = state.getValue(AGE);
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.LOWER) { // Handles age-dependent shape for the bottom block of an Orange Tree.
            switch(age) {
                case 0 -> {
                    return AGE_0_BOTTOM_SHAPE;
                }
                case 1 -> {
                    return AGE_1_BOTTOM_SHAPE;
                }
                default -> {
                    return GENERAL_BOTTOM_SHAPE;
                }
            }
        } else { // Handles age-dependent shape for the top block of an Orange Tree.
            if (age == 2) {
                return AGE_2_TOP_SHAPE;
            } else {
                return GENERAL_TOP_SHAPE;
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.DoublePlantBlock#updateShape(BlockState, Direction, BlockState, LevelAccessor, BlockPos, BlockPos)}.
     */
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (facing.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.is(this) && facingState.getValue(HALF) != doubleBlockHalf) {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    /**
     * Only ticks the Orange Tree when it is growing before it has reached its max age.
     * @param state The {@link BlockState} of the block.
     * @return Whether the block should be ticking, as a {@link Boolean}.
     */
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < DOUBLE_AGE_MAX;
    }

    /**
     * Ages the Orange Tree up a state with a chance from a random tick.<br><br>
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        int age = state.getValue(AGE);
        if (age < DOUBLE_AGE_MAX && level.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(level, pos, state, random.nextInt(85) == 0)) { // Whether the Orange Tree is able to grow.
            age += 1;
            BlockState blockState = state.setValue(AGE, age);
            if (age > SINGLE_AGE_MAX && doubleBlockHalf == DoubleBlockHalf.LOWER) { // Growing for the double block state.
                OrangeTreeBlock.placeAt(level, blockState, pos, 2);
            } else { // Growing for the single block state.
                level.setBlock(pos, blockState, 2);
            }
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
            CommonHooks.fireCropGrowPost(level, pos, state);
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.DoublePlantBlock#canSurvive(BlockState, LevelReader, BlockPos)}.
     */
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return super.canSurvive(state, level, pos);
        } else {
            BlockState blockstate = level.getBlockState(pos.below());
            if (state.getBlock() != this) return super.canSurvive(state, level, pos); // Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.DoublePlantBlock#playerWillDestroy(Level, BlockPos, BlockState, Player)}.<br><br>
     * Behavior depends on the Orange Tree's age being at the point of it being a double tall block instead of a single block.
     */
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        int age = state.getValue(AGE);
        if (age > SINGLE_AGE_MAX) {
            if (!level.isClientSide()) {
                if (player.isCreative()) {
                    preventCreativeDropFromBottomPart(level, pos, state, player);
                } else {
                    if (doubleBlockHalf == DoubleBlockHalf.LOWER) {
                        pos = pos.above();
                    }
                    dropResources(state.setValue(HALF, DoubleBlockHalf.LOWER), level, pos, null, player, player.getMainHandItem());
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    /**
     * Handles destroying the entire Orange Tree when broken by a player.
     * @param level The {@link Level} the block is in.
     * @param player The {@link Player} destroying the block.
     * @param pos The {@link BlockPos} of the block.
     * @param state The {@link BlockState} of the block.
     * @param blockEntity The {@link BlockEntity} that the block has.
     * @param tool The {@link ItemStack} of the tool used to destroy the block.
     */
    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        int age = state.getValue(AGE);
        if (age > SINGLE_AGE_MAX) { // Destroying for the double block state.
            super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), blockEntity, tool); // Replaces the entire blocks with air.
            if (age == DOUBLE_AGE_MAX) { // If the Orange Tree had Oranges, the age is reverted to represent harvesting, instead of the entire block being broken.
                if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
                    pos = pos.below();
                }
                OrangeTreeBlock.placeAt(level, state.setValue(AGE, age - 1), pos, 2);
            }
        } else { // Destroying for the single block state.
            super.playerDestroy(level, player, pos, state, blockEntity, tool);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            return true;
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    /**
     * Reverts the Orange Tree age state to not have Oranges when exploded.
     * @param state The {@link BlockState} of the block.
     * @param level The {@link Level} the block is in.
     * @param pos The {@link BlockPos} of the block.
     * @param explosion The {@link Explosion} affecting the block.
     */
    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        int age = state.getValue(AGE);
        if (age == DOUBLE_AGE_MAX) {
            OrangeTreeBlock.placeAt(level, state.setValue(AGE, age - 1), pos, 2);
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.DoublePlantBlock#preventCreativeDropFromBottomPart(Level, BlockPos, BlockState, Player)}.
     */
    protected static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            BlockPos blockPos = pos.below();
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(state.getBlock()) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                level.setBlock(blockPos, blockState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, blockPos, Block.getId(blockState));
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.DoublePlantBlock#placeAt(LevelAccessor, BlockState, BlockPos, int)}.<br><br>
     * Without waterlogging behavior.
     */
    public static void placeAt(LevelAccessor level, BlockState state, BlockPos pos, int flags) {
        BlockPos abovePos = pos.above();
        level.setBlock(pos, state.setValue(HALF, DoubleBlockHalf.LOWER), flags);
        level.setBlock(abovePos, state.setValue(HALF, DoubleBlockHalf.UPPER), flags);
    }

    /**
     * Grows an Orange Tree to the next age state.
     * @param level The {@link Level} the block is in.
     * @param random The {@link RandomSource} from the level.
     * @param pos The {@link BlockPos} of the block.
     * @param state The {@link BlockState} of the block.
     */
    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        int i = Math.min(DOUBLE_AGE_MAX, state.getValue(AGE) + 1);
        if (i > SINGLE_AGE_MAX && (level.isEmptyBlock(pos.above()) || level.getBlockState(pos.above()).is(this))) { // Growing for the double block state.
            if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
                pos = pos.below();
            }
            OrangeTreeBlock.placeAt(level, state.setValue(AGE, i), pos, 2);
        } else { // Growing for the single block state.
            level.setBlock(pos, state.setValue(AGE, i), 2);
        }
    }

    /**
     * Whether the behavior for using bone meal should run. A 100% success rate.
     * @param level The {@link Level} the block is in.
     * @param random The {@link RandomSource} from the level.
     * @param pos The {@link BlockPos} of the block.
     * @param state The {@link BlockState} of the block.
     * @return A {@link Boolean} of whether using Bone Meal was successful.
     */
    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true; //todo balance
    }

    /**
     * Allows bone meal to be used on Orange Trees when not yet fully grown.
     * @param level The {@link Level} the block is in.
     * @param pos The {@link BlockPos} of the block.
     * @param state The {@link BlockState} of the block.
     * @return Whether this block is valid to use bone meal on, as a {@link Boolean}.
     */
    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(AGE) < DOUBLE_AGE_MAX;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.DoublePlantBlock#getSeed(BlockState, BlockPos)}.<br><br>
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }
}

