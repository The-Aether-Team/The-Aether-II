package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
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
    public static final MapCodec<BedrollBlock> CODEC = simpleCodec(BedrollBlock::new);
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
    private static final VoxelShape SHAPE = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F);

    @Override
    protected MapCodec<BedrollBlock> codec() {
        return CODEC;
    }

    public BedrollBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            if (state.getValue(PART) != BedPart.HEAD) {
                pos = pos.relative(state.getValue(FACING));
                state = level.getBlockState(pos);
                if (!state.is(this)) {
                    return InteractionResult.CONSUME;
                }
            }

            BedRule bedrule = level.environmentAttributes().getValue(EnvironmentAttributes.BED_RULE, pos);

            if (bedrule.explodes()) {
                level.removeBlock(pos, false);
                BlockPos relativePos = pos.relative(state.getValue(FACING).getOpposite());
                if (level.getBlockState(relativePos).is(this)) {
                    level.removeBlock(relativePos, false);
                }
                Vec3 center = pos.getCenter();
                level.explode(null, level.damageSources().badRespawnPointExplosion(center), null, center, 5.0F, true, Level.ExplosionInteraction.BLOCK);
                return InteractionResult.SUCCESS_SERVER;
            } else if (state.getValue(OCCUPIED)) {
                if (!this.kickVillagerOutOfBed(level, pos)) {
                    player.sendOverlayMessage(Component.translatable("block.minecraft.bed.occupied"));
                }
            } else {
                player.startSleepInBed(pos).ifLeft((problem) -> {
                    if (problem.message() != null) {
                        player.sendOverlayMessage(problem.message());
                    }
                }).ifRight((unit) -> {
                    if (player instanceof ServerPlayer serverPlayer) {
                        AetherIIAdvancementTriggers.SLEPT_IN_BEDROLL.get().trigger(serverPlayer);
                    }
                });
            }
        }
        return InteractionResult.SUCCESS_SERVER;
    }

    private boolean kickVillagerOutOfBed(Level level, BlockPos pos) {
        List<Villager> list = level.getEntitiesOfClass(Villager.class, new AABB(pos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        } else {
            list.getFirst().stopSleeping();
            return true;
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (direction != getNeighbourDirection(state.getValue(PART), state.getValue(FACING))) {
            return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, randomSource);
        } else {
            return neighborState.is(this) && neighborState.getValue(PART) != state.getValue(PART) ? state.setValue(OCCUPIED, neighborState.getValue(OCCUPIED)) : Blocks.AIR.defaultBlockState();
        }
    }

    private static Direction getNeighbourDirection(BedPart part, Direction direction) {
        return part == BedPart.FOOT ? direction : direction.getOpposite();
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.preventsBlockDrops()) {
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
        return super.playerWillDestroy(level, pos, state, player);
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
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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
    protected long getSeed(BlockState state, BlockPos pos) {
        BlockPos relativePos = pos.relative(state.getValue(FACING), state.getValue(PART) == BedPart.HEAD ? 0 : 1);
        return Mth.getSeed(relativePos.getX(), pos.getY(), relativePos.getZ());
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, OCCUPIED);
    }

    @Override
    public boolean isBed(BlockState state, BlockGetter level, BlockPos pos, LivingEntity sleeper) {
        return true;
    }
}
