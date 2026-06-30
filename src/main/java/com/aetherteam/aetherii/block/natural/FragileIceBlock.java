package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FragileIceBlock extends IceBlock {
public FragileIceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            level.levelEvent(2001, pos, Block.getId(state));
        }
        if (level.getBlockState(pos.below()).isAir() || level.getBlockState(pos.below()).is(AetherIITags.Blocks.ARCTIC_ICE)) {
            level.removeBlock(pos, false);
        } else {
            this.melt(state, level, pos);
        }
        super.tick(state, level, pos, random);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor scheduledTickAccess = level;
        if (neighborState.is(Blocks.AIR) || neighborState.is(Blocks.WATER)) {
            scheduledTickAccess.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof Player player && !player.isCrouching()) {
            if (entity.tickCount % 5 == 0) {
                level.levelEvent(2001, pos, Block.getId(state));
            }
            level.scheduleTick(pos, this, 6);
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (projectile.getType().is(EntityTypeTags.IMPACT_PROJECTILES)) {
            BlockPos pos = hit.getBlockPos();
            level.levelEvent(2001, pos, Block.getId(state));
            level.scheduleTick(pos, this, 2);
        }
        super.onProjectileHit(level, state, hit, projectile);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.is(AetherIIBlocks.ARCTIC_ICE.get()) || super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        return neighborState.is(AetherIIBlocks.ARCTIC_ICE.get()) || super.hidesNeighborFace(level, pos, state, neighborState, dir);
    }
}
