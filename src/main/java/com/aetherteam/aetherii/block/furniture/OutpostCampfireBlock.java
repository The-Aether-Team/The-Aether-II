package com.aetherteam.aetherii.block.furniture;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.OutpostTrackerAttachment;
import com.aetherteam.aetherii.blockentity.OutpostCampfireBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

public class OutpostCampfireBlock extends MultiBlock {
    public static final MapCodec<OutpostCampfireBlock> CODEC = simpleCodec(OutpostCampfireBlock::new);
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0);

    public OutpostCampfireBlock(BlockBehaviour.Properties properties) {
        super(2, 2, 1, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.WEST));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createPostBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createPostBlockStateDefinition(builder);
        builder.add(HORIZONTAL_FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            state = state.setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
        }
        return state;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OutpostCampfireBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockPos origin = this.locateOriginFrom(state, pos);
        if (level.getBlockEntity(origin) instanceof OutpostCampfireBlockEntity) {
            var data = player.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
            if (!data.getCampfirePositions().stream().map(OutpostTrackerAttachment.CampfirePosition::pos).collect(Collectors.toSet()).contains(origin)) {
                data.addCampfirePosition(new OutpostTrackerAttachment.CampfirePosition(level.dimension(), pos));
                player.displayClientMessage(Component.translatable("aether_ii.message.campfire_added"), true);
                this.multiBlockPositions(state.getValue(X_DIRECTION_FROM_ORIGIN), state.getValue(Z_DIRECTION_FROM_ORIGIN)).forEach((loopedPos) -> {
                    BlockEntity blockEntity = level.getBlockEntity(loopedPos.offset(origin));
                    if (blockEntity instanceof OutpostCampfireBlockEntity outpostCampfireBlockEntity && !outpostCampfireBlockEntity.isLit()) {
                        outpostCampfireBlockEntity.setLit(true);
                    }
                });
                Vec3 originVec = Vec3.atBottomCenterOf(this.locateOriginFrom(state, pos));
                this.activationParticles(level, new Vec3(originVec.x() + (state.getValue(X_DIRECTION_FROM_ORIGIN).getStepX() / 2.0), originVec.y(), originVec.z() + (state.getValue(Z_DIRECTION_FROM_ORIGIN).getStepZ() / 2.0)), level.getRandom());
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof OutpostCampfireBlockEntity outpostCampfireBlockEntity && outpostCampfireBlockEntity.isLit()) {
            Vec3 originVec = Vec3.atBottomCenterOf(this.locateOriginFrom(state, pos));
            this.tickParticles(level, new Vec3(originVec.x() + (state.getValue(X_DIRECTION_FROM_ORIGIN).getStepX() / 2.0), originVec.y(), originVec.z() + (state.getValue(Z_DIRECTION_FROM_ORIGIN).getStepZ() / 2.0)), level.getRandom());
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
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof OutpostCampfireBlockEntity outpostCampfireBlockEntity && outpostCampfireBlockEntity.isLit()) {
            return 15;
        }
        return 0;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
