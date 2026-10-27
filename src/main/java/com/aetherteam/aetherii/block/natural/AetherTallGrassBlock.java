package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class AetherTallGrassBlock extends TallGrassBlock implements Snowable {
    protected static final VoxelShape SHORT_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);
    protected static final VoxelShape MEDIUM_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);
    protected static final VoxelShape LONG_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public static final EnumProperty<GrassType> TYPE = EnumProperty.create("grass_type", GrassType.class);

    public AetherTallGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, GrassType.DEFAULT).setValue(SNOW_LAYERS, 0));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (level.getBlockState(pos.below()).is(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK) || level.getBlockState(pos.below()).is(AetherIIBlocks.AMBRELINN_MOSS_BLOCK)) {
            return this.defaultBlockState().setValue(TYPE, GrassType.ENCHANTED);
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !state.is(AetherIIBlocks.TALL_AETHER_GRASS.get());
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.is(AetherIIBlocks.SHORT_AETHER_GRASS)) {
            level.setBlock(pos, AetherIIBlocks.MEDIUM_AETHER_GRASS.get().withPropertiesOf(state), 2);
        } else if (state.is(AetherIIBlocks.MEDIUM_AETHER_GRASS)) {
            level.setBlock(pos, AetherIIBlocks.TALL_AETHER_GRASS.get().withPropertiesOf(state), 2);
        }
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
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (context.getItemInHand().is(AetherIIBlocks.ARCTIC_SNOW.asItem()) && state.getValue(SNOW_LAYERS) > 0) {
            return false;
        } else {
            return super.canBeReplaced(state, context);
        }
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
        VoxelShape shape = LONG_SHAPE;
        if (state.is(AetherIIBlocks.SHORT_AETHER_GRASS)) {
            shape = SHORT_SHAPE;
        } else if (state.is(AetherIIBlocks.MEDIUM_AETHER_GRASS)) {
            shape = MEDIUM_SHAPE;
        }
        return Snowable.super.getShape(state, level, pos, context, shape);
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
        return Snowable.super.isSnowy(blockState) && blockState.getValue(TYPE) == GrassType.SNOWY;
    }

    @Override
    public BlockState setSnowy(BlockState blockState, int layer) {
        blockState = blockState.setValue(SNOW_LAYERS, layer);
        return blockState.setValue(TYPE, GrassType.SNOWY);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, SNOW_LAYERS);
    }

    public enum GrassType implements StringRepresentable {
        DEFAULT,
        SNOWY,
        ENCHANTED;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}