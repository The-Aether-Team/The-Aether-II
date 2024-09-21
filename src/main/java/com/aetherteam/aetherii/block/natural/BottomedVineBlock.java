package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class BottomedVineBlock extends VineBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_25;

    public BottomedVineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, false)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            return state.setValue(AGE, context.getLevel().getRandom().nextInt(25));
        }
        return null;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (ItemAbilities.SHEARS_TRIM == itemAbility) {
            if (!this.isMaxAge(state)) {
                if (!simulate) {
                    context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return this.getMaxAgeState(state);
            }
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN) {
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            BlockState updatedState = this.getUpdatedState(state, level, currentPos);
            return !this.hasFaces(updatedState) ? Blocks.AIR.defaultBlockState() : updatedState;
        }
    }

    private BlockState getUpdatedState(BlockState state, BlockGetter level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        if (state.getValue(UP)) {
            state = state.setValue(UP, isAcceptableNeighbour(level, abovePos, Direction.DOWN));
        }
        BlockState otherState = null;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty property = getPropertyForFace(direction);
            if (state.getValue(property)) {
                boolean flag = this.canSupportAtFace(level, pos, direction);
                if (!flag) {
                    if (otherState == null) {
                        otherState = level.getBlockState(abovePos);
                    }
                    flag = otherState.is(this) && otherState.getValue(property) && otherState.getValue(AGE) < 25;
                }
                state = state.setValue(property, flag);
            }
        }
        return state;
    }

    private boolean hasFaces(BlockState state) {
        return this.countFaces(state) > 0;
    }

    private int countFaces(BlockState state) {
        int i = 0;
        for (BooleanProperty property : PROPERTY_BY_DIRECTION.values()) {
            if (state.getValue(property)) {
                i++;
            }
        }
        return i;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 25;
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (age < 25) {
            if (level.getGameRules().getBoolean(GameRules.RULE_DO_VINES_SPREAD)) {
                if (level.random.nextInt(4) == 0 && level.isAreaLoaded(pos, 4)) { // Forge: check area to prevent loading unloaded chunks
                    Direction randomDirection = Direction.getRandom(random);
                    BlockPos abovePos = pos.above();
                    if (randomDirection.getAxis().isHorizontal() && !state.getValue(getPropertyForFace(randomDirection))) {
                        if (this.canSpread(level, pos)) {
                            BlockPos directionPos = pos.relative(randomDirection);
                            BlockState directionState = level.getBlockState(directionPos);
                            if (directionState.isAir()) {
                                Direction clockwiseDirection = randomDirection.getClockWise();
                                Direction counterClockwiseDirection = randomDirection.getCounterClockWise();
                                boolean isClockwise = state.getValue(getPropertyForFace(clockwiseDirection));
                                boolean isCounterClockwise = state.getValue(getPropertyForFace(counterClockwiseDirection));
                                BlockPos clockwisePos = directionPos.relative(clockwiseDirection);
                                BlockPos counterClockwisePos = directionPos.relative(counterClockwiseDirection);
                                if (isClockwise && isAcceptableNeighbour(level, clockwisePos, clockwiseDirection)) {
                                    level.setBlock(directionPos, this.defaultBlockState().setValue(getPropertyForFace(clockwiseDirection), true).setValue(AGE, age), 2);
                                } else if (isCounterClockwise && isAcceptableNeighbour(level, counterClockwisePos, counterClockwiseDirection)) {
                                    level.setBlock(directionPos, this.defaultBlockState().setValue(getPropertyForFace(counterClockwiseDirection), true).setValue(AGE, age), 2);
                                } else {
                                    Direction oppositeDirection = randomDirection.getOpposite();
                                    if (isClockwise && level.isEmptyBlock(clockwisePos) && isAcceptableNeighbour(level, pos.relative(clockwiseDirection), oppositeDirection)) {
                                        level.setBlock(clockwisePos, this.defaultBlockState().setValue(getPropertyForFace(oppositeDirection), true).setValue(AGE, age), 2);
                                    } else if (isCounterClockwise && level.isEmptyBlock(counterClockwisePos) && isAcceptableNeighbour(level, pos.relative(counterClockwiseDirection), oppositeDirection)) {
                                        level.setBlock(counterClockwisePos, this.defaultBlockState().setValue(getPropertyForFace(oppositeDirection), true).setValue(AGE, age), 2);
                                    } else if ((double) random.nextFloat() < 0.05 && isAcceptableNeighbour(level, directionPos.above(), Direction.UP)) {
                                        level.setBlock(directionPos, this.defaultBlockState().setValue(UP, true).setValue(AGE, age), 2);
                                    }
                                }
                            } else if (isAcceptableNeighbour(level, directionPos, randomDirection)) {
                                level.setBlock(pos, state.setValue(getPropertyForFace(randomDirection), true).setValue(AGE, age), 2);
                            }
                        }
                    } else {
                        if (randomDirection == Direction.UP && pos.getY() < level.getMaxBuildHeight() - 1) {
                            if (this.canSupportAtFace(level, pos, randomDirection)) {
                                level.setBlock(pos, state.setValue(UP, true).setValue(AGE, age), 2);
                                return;
                            }
                            if (level.isEmptyBlock(abovePos)) {
                                if (!this.canSpread(level, pos)) {
                                    return;
                                }
                                BlockState newState = state;
                                for (Direction horizontalDirection : Direction.Plane.HORIZONTAL) {
                                    if (random.nextBoolean() || !isAcceptableNeighbour(level, abovePos.relative(horizontalDirection), horizontalDirection)) {
                                        newState = newState.setValue(getPropertyForFace(horizontalDirection), false);
                                    }
                                }
                                if (this.hasHorizontalConnection(newState)) {
                                    level.setBlock(abovePos, newState.setValue(AGE, age), 2);
                                }
                                return;
                            }
                        }

                        if (pos.getY() > level.getMinBuildHeight()) {
                            BlockPos belowPos = pos.below();
                            BlockState blockstate = level.getBlockState(belowPos);
                            if (blockstate.isAir() || blockstate.is(this)) {
                                BlockState newState = blockstate.isAir() ? this.defaultBlockState() : blockstate;
                                BlockState copiedState = this.copyRandomFaces(state, newState, random);
                                if (newState != copiedState && this.hasHorizontalConnection(copiedState)) {
                                    level.setBlock(belowPos, copiedState.setValue(AGE, level.getBlockState(belowPos).is(copiedState.getBlock()) ? level.getBlockState(belowPos).getValue(AGE) : age + 1), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean canSpread(BlockGetter blockReader, BlockPos pos) {
        Iterable<BlockPos> iterable = BlockPos.betweenClosed(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
        int j = 5;
        for (BlockPos blockpos : iterable) {
            if (blockReader.getBlockState(blockpos).is(this)) {
                if (--j <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) {
            return false;
        } else {
            BlockPos relativePos = pos.relative(direction);
            if (isAcceptableNeighbour(level, relativePos, direction)) {
                return true;
            } else if (direction.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanDirection = PROPERTY_BY_DIRECTION.get(direction);
                BlockState state = level.getBlockState(pos.above());
                return state.is(this) && state.getValue(booleanDirection) && state.getValue(AGE) < 25;
            }
        }
    }

    private boolean hasHorizontalConnection(BlockState state) {
        return state.getValue(NORTH) || state.getValue(EAST) || state.getValue(SOUTH) || state.getValue(WEST);
    }

    private BlockState copyRandomFaces(BlockState sourceState, BlockState spreadState, RandomSource random) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (random.nextBoolean()) {
                BooleanProperty booleanDirection = getPropertyForFace(direction);
                if (sourceState.getValue(booleanDirection)) {
                    spreadState = spreadState.setValue(booleanDirection, true);
                }
            }
        }
        return spreadState;
    }

    public BlockState getMaxAgeState(BlockState state) {
        return state.setValue(AGE, 25);
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) == 25;
    }
}
