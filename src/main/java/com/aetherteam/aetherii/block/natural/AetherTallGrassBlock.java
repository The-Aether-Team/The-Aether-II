package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
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
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, GrassType.DEFAULT));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (level.getBlockState(pos.below()).is(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()) || level.getBlockState(pos.below()).is(AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get())) {
            return this.defaultBlockState().setValue(TYPE, GrassType.ENCHANTED);
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.is(AetherIIBlocks.SHORT_AETHER_GRASS.get())) {
            return SHORT_SHAPE;
        } else if (state.is(AetherIIBlocks.MEDIUM_AETHER_GRASS.get())) {
            return MEDIUM_SHAPE;
        }
        return LONG_SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return !state.is(AetherIIBlocks.TALL_AETHER_GRASS.get());
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.is(AetherIIBlocks.SHORT_AETHER_GRASS.get())) {
            level.setBlock(pos, AetherIIBlocks.MEDIUM_AETHER_GRASS.get().withPropertiesOf(state), 2);
        } else if (state.is(AetherIIBlocks.MEDIUM_AETHER_GRASS.get())) {
            level.setBlock(pos, AetherIIBlocks.TALL_AETHER_GRASS.get().withPropertiesOf(state), 2);
        }
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TYPE);
    }

    @Override
    public boolean isSnowy(BlockState blockState) {
        return blockState.getValue(TYPE) == GrassType.SNOWY;
    }

    @Override
    public BlockState setSnowy(BlockState blockState) {
        return blockState.setValue(TYPE, GrassType.SNOWY);
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
