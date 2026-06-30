package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.entity.block.SittableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class SittableBlock extends Block {
    public SittableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hitResult) {
        if (!this.seatBlocked(level, pos) && !this.seatExists(state, level, pos)) {
            SittableEntity sittable = new SittableEntity(level, Vec3.atBottomCenterOf(pos).add(this.offsetSeatFromBottom(state, pos)), this.getFacing(state, pos).toYRot(), state);
            if (!level.isClientSide()) {
                level.addFreshEntity(sittable);
            }
            player.startRiding(sittable);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    public boolean seatBlocked(Level level, BlockPos pos) {
        return !level.getBlockState(pos.above()).getCollisionShape(level, pos).isEmpty();
    }

    private boolean seatExists(BlockState state, Level level, BlockPos pos) {
        return !level.getEntitiesOfClass(SittableEntity.class, new AABB(pos)).isEmpty();
    }

    public abstract Vec3 offsetSeatFromBottom(BlockState state, BlockPos pos);

    public abstract Direction getFacing(BlockState state, BlockPos pos);

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathComputationType) {
        return false;
    }
}
