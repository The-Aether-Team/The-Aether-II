package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class BedrollBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
    private static final VoxelShape SHAPE = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F);

    public BedrollBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (level.isClientSide()) {
            return InteractionResult.CONSUME;
        } else {
            if (state.getValue(PART) != BedPart.HEAD) {
                pos = pos.relative(state.getValue(FACING));
                state = level.getBlockState(pos);
                if (!state.is(this)) {
                    return InteractionResult.CONSUME;
                }
            }

            if (!level.dimensionType().bedWorks() && level.dimension() != AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL) {
                level.removeBlock(pos, false);
                BlockPos relativePos = pos.relative(state.getValue(FACING).getOpposite());
                if (level.getBlockState(relativePos).is(this)) {
                    level.removeBlock(relativePos, false);
                }
                Vec3 center = pos.getCenter();
                level.explode(null, level.damageSources().badRespawnPointExplosion(center), null, center, 5.0F, true, Level.ExplosionInteraction.BLOCK);
                return InteractionResult.SUCCESS;
            } else if (state.getValue(OCCUPIED)) {
                if (!this.kickVillagerOutOfBed(level, pos)) {
                    player.displayClientMessage(Component.translatable("block.minecraft.bed.occupied"), true);
                }
            } else {
                player.startSleepInBed(pos).ifLeft((problem) -> {
                    if (problem.getMessage() != null) {
                        player.displayClientMessage(problem.getMessage(), true);
                    }
                }).ifRight((unit) -> {
                    if (player instanceof ServerPlayer serverPlayer) {
                        AetherIIAdvancementTriggers.SLEPT_IN_BEDROLL.get().trigger(serverPlayer);
                    }
                });
            }
        }
        return InteractionResult.SUCCESS;
    }

    private boolean kickVillagerOutOfBed(Level level, BlockPos pos) {
        List<Villager> list = level.getEntitiesOfClass(Villager.class, new AABB(pos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        } else {
            list.get(0).stopSleeping();
            return true;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor tickAccess = level;
        if (direction != getNeighbourDirection(state.getValue(PART), state.getValue(FACING))) {
            return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        } else {
            return neighborState.is(this) && neighborState.getValue(PART) != state.getValue(PART) ? state.setValue(OCCUPIED, neighborState.getValue(OCCUPIED)) : Blocks.AIR.defaultBlockState();
        }
    }

    private static Direction getNeighbourDirection(BedPart part, Direction direction) {
        return part == BedPart.FOOT ? direction : direction.getOpposite();
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            BedPart part = state.getValue(PART);
            if (part == BedPart.FOOT) {
                BlockPos neighbourPos = pos.relative(getNeighbourDirection(part, state.getValue(FACING)));
                BlockState neighbourState = level.getBlockState(neighbourPos);
                if (neighbourState.is(this) && neighbourState.getValue(PART) == BedPart.HEAD) {
                    level.setBlock(neighbourPos, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, neighbourPos, Block.getId(neighbourState));
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        BlockPos clickedPos = context.getClickedPos();
        BlockPos relativePos = clickedPos.relative(direction);
        Level level = context.getLevel();
        return level.getBlockState(relativePos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(relativePos) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide()) {
            BlockPos relativePos = pos.relative(state.getValue(FACING));
            level.setBlock(relativePos, state.setValue(PART, BedPart.HEAD), 3);
            level.updateNeighborsAt(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);
        }
    }

    @Override
    public long getSeed(BlockState state, BlockPos pos) {
        BlockPos relativePos = pos.relative(state.getValue(FACING), state.getValue(PART) == BedPart.HEAD ? 0 : 1);
        return Mth.getSeed(relativePos.getX(), pos.getY(), relativePos.getZ());
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, OCCUPIED);
    }

    @Override
    public boolean isBed(BlockState state, BlockGetter level, BlockPos pos, Entity sleeper) {
        return true;
    }
}
