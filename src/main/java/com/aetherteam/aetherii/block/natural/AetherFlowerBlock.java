package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class AetherFlowerBlock extends FlowerBlock implements Snowable {
    public AetherFlowerBlock(Properties properties) {
        super(new SuspiciousStewEffects(List.of()), properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SNOW_LAYERS, 0));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return Snowable.super.useItemOn(itemStack, state, level, pos, player, hand, hitResult, super.useItemOn(itemStack, state, level, pos, player, hand, hitResult));
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return Snowable.super.isRandomlyTicking(state, super.isRandomlyTicking(state));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        Snowable.super.randomTick(state, level, pos, random);
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        Snowable.super.destroy(level, pos, state);
    }

    @Override
    public void onBlockExploded(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        Snowable.super.onBlockExploded(state, level, pos, explosion);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return Snowable.super.isPathfindable(state, type, super.isPathfindable(state, type));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Snowable.super.getShape(state, level, pos, context, super.getShape(state, level, pos, context));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Snowable.super.getCollisionShape(state, level, pos, context, super.getCollisionShape(state, level, pos, context));
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Snowable.super.getBlockSupportShape(state, level, pos, super.getBlockSupportShape(state, level, pos));
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Snowable.super.getVisualShape(state, level, pos, context, super.getVisualShape(state, level, pos, context));
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return Snowable.super.getShadeBrightness(state, level, pos);
    }

    @Override
    public boolean isSnowy(BlockState blockState) {
        return Snowable.super.isSnowy(blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SNOW_LAYERS);
    }
}
