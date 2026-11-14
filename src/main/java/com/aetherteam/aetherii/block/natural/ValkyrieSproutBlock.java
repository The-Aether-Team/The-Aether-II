package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

public class ValkyrieSproutBlock extends AetherBushBlock implements BonemealableBlock, Snowable {
    private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

    public ValkyrieSproutBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(SNOW_LAYERS, 0));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return Snowable.super.useItemOn(itemStack, state, level, pos, player, hand, hitResult, super.useItemOn(itemStack, state, level, pos, player, hand, hitResult));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return Snowable.super.isRandomlyTicking(state, state.getValue(AGE) < 2);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(level, pos, state, random.nextInt(20) == 0)) {
            level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(state.getValue(AGE) + 1, 2)));
            CommonHooks.fireCropGrowPost(level, pos, state);
        }
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
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Snowable.super.getShape(state, level, pos, context, SHAPE);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Snowable.super.getCollisionShape(state, level, pos, context, Shapes.empty());
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
        builder.add(AGE, SNOW_LAYERS);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(state.getValue(AGE) + 1, 2)));
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() <= 0.65;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(AGE) < 2;
    }

    @Override
    protected float getMaxHorizontalOffset() {
        return 0.175F;
    }
}
