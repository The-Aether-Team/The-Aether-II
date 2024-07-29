package com.aetherteam.aetherii.block.furniture;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.OutpostTrackerAttachment;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.MultiBlockEntity;
import com.aetherteam.aetherii.blockentity.OutpostCampfireBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

public class OutpostCampfireBlock extends MultiBlock {
    public static final MapCodec<OutpostCampfireBlock> CODEC = simpleCodec(OutpostCampfireBlock::new);
    public static final DirectionProperty PART_FACING = DirectionProperty.create("part_facing", Direction.Plane.HORIZONTAL);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0);

    public OutpostCampfireBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART_FACING, Direction.SOUTH).setValue(LIT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART_FACING).add(LIT);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OutpostCampfireBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == AetherIIBlockEntityTypes.OUTPOST_CAMPFIRE.get() ? OutpostCampfireBlockEntity::tick : null;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            state = state.setValue(PART_FACING, context.getHorizontalDirection());
        }
        return state;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Direction direction = state.getValue(PART_FACING);
        BlockPos relativePos = pos;
        for (int i = 0; i < 4; i++) {
            level.setBlock(relativePos, state.setValue(PART_FACING, direction), 3);
            relativePos = relativePos.relative(direction);
            direction = direction.getCounterClockWise();
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof MultiBlockEntity multiblock) {
            BlockPos origin = multiblock.getLevelOriginPos();
            if (origin != null) {
                BlockState originState = level.getBlockState(origin);
                if (originState.is(state.getBlock()) && !originState.getValue(LIT)) {
                    Direction direction = originState.getValue(PART_FACING);
                    BlockPos relativePos = origin;
                    for (int i = 0; i < 4; i++) {
                        level.setBlock(relativePos, level.getBlockState(relativePos).setValue(LIT, true), 3);
                        relativePos = relativePos.relative(direction);
                        direction = direction.getCounterClockWise();
                    }

                    var data = player.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
                    if (!data.getCampfirePositions().stream().map(OutpostTrackerAttachment.CampfirePosition::pos).collect(Collectors.toSet()).contains(origin)) {
                        data.addCampfirePosition(new OutpostTrackerAttachment.CampfirePosition(level.dimension(), pos));
                        player.displayClientMessage(Component.translatable("aether_ii.message.campfire_added"), false);
                    }

                    Vec3 originVec = Vec3.atBottomCenterOf(origin);
                    Direction xDir = originState.getValue(PART_FACING).getAxis() == Direction.Axis.X ? originState.getValue(PART_FACING) : originState.getValue(PART_FACING).getCounterClockWise();
                    Direction zDir = originState.getValue(PART_FACING).getAxis() == Direction.Axis.Z ? originState.getValue(PART_FACING) : originState.getValue(PART_FACING).getCounterClockWise();
                    this.activationParticles(level, new Vec3(originVec.x() + (xDir.getStepX() / 2.0), originVec.y(), originVec.z() + (zDir.getStepZ() / 2.0)), level.getRandom());
                }
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            if (level.getBlockEntity(pos) instanceof MultiBlockEntity multiBlockEntity) {
                BlockPos origin = multiBlockEntity.getLevelOriginPos();
                if (origin != null) {
                    BlockState originState = level.getBlockState(origin);
                    if (originState.is(state.getBlock())) {
                        Vec3 originVec = Vec3.atBottomCenterOf(origin);
                        Direction xDir = originState.getValue(PART_FACING).getAxis() == Direction.Axis.X ? originState.getValue(PART_FACING) : originState.getValue(PART_FACING).getCounterClockWise();
                        Direction zDir = originState.getValue(PART_FACING).getAxis() == Direction.Axis.Z ? originState.getValue(PART_FACING) : originState.getValue(PART_FACING).getCounterClockWise();
                        this.tickParticles(level, new Vec3(originVec.x() + (xDir.getStepX() / 2.0), originVec.y(), originVec.z() + (zDir.getStepZ() / 2.0)), level.getRandom());
                    }
                }
            }
        }
    }

    private void activationParticles(Level level, Vec3 pos, RandomSource random) {
        for (int i = 0; i < 50; i++) {
            double range = random.nextDouble() * 0.9;

            if (random.nextInt(10) == 0) {
                level.addParticle(ParticleTypes.LAVA, pos.x() + (random.nextDouble() * (random.nextBoolean() ? range : -range)), pos.y(),
                        pos.z() + (random.nextDouble() * (random.nextBoolean() ? range : -range)),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.2, 0.075 * random.nextDouble(),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.2);
            }
            if (random.nextInt(4) == 0) {
                level.addParticle(ParticleTypes.FLAME, pos.x() + (random.nextDouble() * (random.nextBoolean() ? range : -range)), pos.y(),
                        pos.z() + (random.nextDouble() * (random.nextBoolean() ? range : -range)),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.1, 0.1 * random.nextDouble(),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.1);
            }
            if (random.nextInt(4) == 0) {
                level.addParticle(ParticleTypes.SMOKE, pos.x() + (random.nextDouble() * (random.nextBoolean() ? range : -range)), pos.y(),
                        pos.z() + (random.nextDouble() * (random.nextBoolean() ? range : -range)),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.1, 0.1 * random.nextDouble(),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.1);
            }
        }
    }

    private void tickParticles(Level level, Vec3 pos, RandomSource random) {
        for (int i = 0; i < 10; i++) {
            double range = random.nextDouble() * 0.75;

            if (random.nextInt(800) == 0) {
                level.addParticle(ParticleTypes.LAVA, (random.nextDouble() * (random.nextBoolean() ? range : -range)), pos.y(),
                        pos.z() + (random.nextDouble() * (random.nextBoolean() ? range : -range)),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.001, 0.075 * random.nextDouble(),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.001);
            }
            if (random.nextInt(4) == 0) {
                level.addParticle(ParticleTypes.FLAME, pos.x() + (random.nextDouble() * (random.nextBoolean() ? range : -range)), pos.y(),
                        pos.z() + (random.nextDouble() * (random.nextBoolean() ? range : -range)),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.001, 0.04 * random.nextDouble(),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.001);
            }
            if (random.nextInt(4) == 0) {
                level.addParticle(ParticleTypes.SMOKE, pos.x() + (random.nextDouble() * (random.nextBoolean() ? range : -range)), pos.y(),
                        pos.z() + (random.nextDouble() * (random.nextBoolean() ? range : -range)),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.001, 0.075 * random.nextDouble(),
                        (random.nextDouble() * (random.nextBoolean() ? range : -range)) * 0.001);
            }
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(PART_FACING, rotation.rotate(state.getValue(PART_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(PART_FACING)));
    }
}
